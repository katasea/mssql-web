package com.auto.db;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.auto.annotation.Column;
import com.auto.annotation.Table;
import com.common.ArrayUtils;
import com.common.ClassTools;
import com.common.CommonUtil;
import com.common.Global;
import com.common.Log4JUtil;
import com.main.dao.CommonDao;
import com.main.pojo.StateInfo;

/**
 * 自动扫描ebg.pojo包下的实体类，进行注解解析生成表结构语句并维护表结构
 * @author chenfuqiang
 *
 */
@Service("autoFixTable")
@Transactional
public class AutoFixTableSQLServerImpl implements AutoFixTable{
	/**
	 * 要扫描的model所在的pack
	 */
	private String pack = Global.PACKAGEOFPOJO;
	@Resource
	private CommonDao commonDao;
	
	@Override
	public StateInfo run(int year) {
		long begin = System.currentTimeMillis();
		Log4JUtil.info("---开始自动修复表结构---");
		Set<Class<?>> classes = ClassTools.getClasses(pack);
		StateInfo stateInfo = dealClassSQL(classes,year);
		long end = System.currentTimeMillis();
		Log4JUtil.info("---结束自动修复表结构---");
		Log4JUtil.info("总共花费了"+((end-begin)*0.001)+"秒");
		return stateInfo;
	}
	
	public StateInfo dealClassSQL(Set<Class<?>> classes,int year) {
		StateInfo stateInfo = new StateInfo();
		List<String> sqlAdd = new ArrayList<String>();
		List<String> sqlUpt = new ArrayList<String>();
		List<String> sqlPK = new ArrayList<String>();
 		List<String> sqlEtc = new ArrayList<String>();
		Map<String,String> table_indexName = this.getKeyName();
		StringBuffer keyBuf = new StringBuffer();
		StringBuffer keyEditBuf = new StringBuffer();
		StringBuffer allBuf = new StringBuffer();
		StringBuffer addBuf = new StringBuffer();
		StringBuffer editBuf = new StringBuffer();
		StringBuffer pkBuf = new StringBuffer();
		StringBuffer dvBuf = new StringBuffer();

		if(year == 0 ) {
			year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
		}
		
		for (Class<?> clas : classes){
			addBuf.setLength(0);
			keyBuf.setLength(0);
			keyEditBuf.setLength(0);
			pkBuf.setLength(0);
			editBuf.setLength(0);
			dvBuf.setLength(0);
			allBuf.setLength(0);
			
			Table table = clas.getAnnotation(Table.class);
			if(table == null) {
				continue;
			}
			String tablename = table.name();
			if(CommonUtil.isEmpty(tablename)) {
				tablename = clas.getSimpleName();
			}
			tablename = tablename.toUpperCase().replace("@YEAR", String.valueOf(year));
			//==================================================================================//
			//去掉索引，然后再修改列
			String indexName = table_indexName.get(tablename.toUpperCase());
			if(!CommonUtil.isEmpty(indexName)){
				pkBuf.append("ALTER TABLE "+tablename+" drop CONSTRAINT "+indexName+" \r\n");
				sqlPK.add(pkBuf.toString());
			}
			addBuf.append("CREATE TABLE [dbo].[").append(tablename).append("]( \r\n");
			
			Field[] fields = clas.getDeclaredFields();

// 			这里支持集成的父类，要支持只要把下面的fields 附加到子类的fields即可。
			if(table.includeSupperClass()) {
				if(clas.getSuperclass()!=null){
					Class<?> clsSup = clas.getSuperclass();
					fields = (Field[]) ArrayUtils.addAll(fields,clsSup.getDeclaredFields());
				}
			}
			for (Field field : fields){
				Column column = field.getAnnotation(Column.class);
				if(column == null) {continue;}
				String columnname = CommonUtil.isEmpty(column.name())?field.getName():column.name();
				String flag = column.flag();
				String dv = column.defaultValue();
				String oth = column.oth();//identity(1,1)
				if(!CommonUtil.isEmpty(dv)) {
					dv = dv.toUpperCase().replace("@YEAR", String.valueOf(year));
				}
				String type = column.type();
				
				addBuf.append("[").append(columnname).append("] ").append(type).append(" ");
				if(!CommonUtil.isEmpty(oth)) {
					addBuf.append(" "+oth+" ");
				}
				if("PRIMARY".equals(CommonUtil.nullToStr(flag).toUpperCase())) {
					keyBuf.append("[").append(columnname).append("] ASC,\r\n");
					keyEditBuf.append(columnname).append(",");
					
					addBuf.append(" NOT NULL ");
				}
				if(!CommonUtil.isEmpty(dv)) {
					addBuf.append(" DEFAULT (").append(dv).append(") ");
					dvBuf.append("Update ").append(tablename).append(" Set ").append(columnname).append("=").append(dv).append(" ");
					dvBuf.append("Where ").append(columnname).append(" is null").append(" \r\n");
				}
				addBuf.append(",\r\n");
				//===================================UPDATE FIELDS=================================//
				editBuf.append("IF EXISTS(SELECT * FROM syscolumns WHERE ID=OBJECT_ID('"+tablename+"') AND NAME='"+columnname+"') \r\n");
				editBuf.append("BEGIN \r\n");
				editBuf.append("ALTER TABLE ").append(tablename).append(" ALTER column ").append(columnname).append(" ").append(type).append(" ");
				if("PRIMARY".equals(CommonUtil.nullToStr(flag).toUpperCase())) {
					editBuf.append(" NOT NULL ");
				}else {
					if(!CommonUtil.isEmpty(oth)) {
						editBuf.append(" NOT NULL ");
					}
				}
				editBuf.append(" \r\n");
				editBuf.append("END \r\n");
				editBuf.append("IF NOT EXISTS(SELECT * FROM syscolumns WHERE ID=OBJECT_ID('"+tablename+"') AND NAME='"+columnname+"') \r\n");
				editBuf.append("BEGIN \r\n");
				editBuf.append("ALTER TABLE ").append(tablename).append(" add ").append(columnname).append(" ").append(type).append(" ");
				if("PRIMARY".equals(CommonUtil.nullToStr(flag).toUpperCase())) {
					editBuf.append(" NOT NULL ");
				}else {
					if(!CommonUtil.isEmpty(oth)) {
						editBuf.append(" NOT NULL ");
					}
				}
				editBuf.append(" \r\n");
				editBuf.append("END \r\n");
				//=================================================================================//
			}
			if(keyBuf.length() != 0) {
				addBuf.append("CONSTRAINT [PK_" + tablename+ "] PRIMARY KEY CLUSTERED ( \r\n");
				addBuf.append(keyBuf.substring(0,keyBuf.length()-3));
				addBuf.append(") ON [PRIMARY] \r\n");
			}else {
				addBuf.delete(addBuf.length()-3, addBuf.length()-1);
			}
			//__________________________TEST_____________________________//
//				if(tablename.equals("TYS_XMYS_FIELDS")) {
//					System.out.println(editBuf.toString());
//				}
			
			//___________________________END_____________________________//
			addBuf.append(") ON [PRIMARY] \r\n");
			
			allBuf.append("IF EXISTS (SELECT * FROM sysobjects WHERE id = OBJECT_ID(N'[dbo].["+ tablename+ "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1) \r\n");
			allBuf.append("BEGIN \r\n");
			allBuf.append(editBuf.toString());
			allBuf.append("END \r\n");
			sqlUpt.add(allBuf.toString());
			
			allBuf.append("IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = OBJECT_ID(N'[dbo].["+ tablename+ "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1) \r\n");
			allBuf.append("BEGIN \r\n");
			allBuf.append(addBuf.toString());
			allBuf.append("END \r\n");
			sqlAdd.add(allBuf.toString());
			
			//修改主键需要在列都修改完执行完之后再修改主键，因为有些列是NULL,修改完列后就是NOT NULL
			//=====================================UPDATE TABLE ===============================//
			//修改默认值
			if(dvBuf.length() != 0) {
				sqlEtc.add(dvBuf.toString());
			}
			//修改主键
			if(keyBuf.length() != 0) {
				allBuf.setLength(0);
				allBuf.append("IF EXISTS (SELECT * FROM sysobjects WHERE id = OBJECT_ID(N'[dbo].["+ tablename+ "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1) \r\n");
				allBuf.append("BEGIN \r\n");
				allBuf.append("alter table "+tablename+" add constraint pk_"+tablename+" primary key ("+keyEditBuf.substring(0,keyEditBuf.length()-1)+") \r\n");
				allBuf.append("END \r\n");
				sqlEtc.add(allBuf.toString());
			}
		}
		//======================================JDBC===============================================//
		try {
			
			if(sqlAdd.size()>0) {
				commonDao.transactionUpdate(sqlAdd);
				Log4JUtil.info("--新增数据库表完成--");
			}
			if(sqlUpt.size()>0) {
				commonDao.transactionUpdate(sqlUpt);
				Log4JUtil.info("--修改数据库表完成--");
			}
			if(sqlPK.size()>0) {
				commonDao.transactionUpdate(sqlPK);
				Log4JUtil.info("--主键删除完成--");
			}
			if(sqlEtc.size()>0) {
				commonDao.transactionUpdate(sqlEtc);
				Log4JUtil.info("--其他操作完成--");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Log4JUtil.error(e.getMessage());
			stateInfo.setFlag(false);
			stateInfo.setMsg(e.getMessage());
		}
		return stateInfo;
	}

	
	public Map<String,String> getKeyName() {
//		select a.name,b.name from sysobjects a left join sysobjects b on a.parent_obj = b.id where 1=1 
//		and a.xtype='pk'
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select b.name AS [表名], ");
		sqlBuffer.append("a.name  AS [主键名称] ");
		sqlBuffer.append("from  dbo.sysobjects a left join  dbo.sysobjects b on a.parent_obj = b.id where 1=1 ");
		sqlBuffer.append("and a.xtype='pk' ");
		List<Map<String,Object>> list = commonDao.getListForMap(sqlBuffer.toString());
		Map<String,String> result = new HashMap<String, String>();
		for(Map<String,Object> m : list) {
			result.put(String.valueOf(m.get("表名")).toUpperCase(), String.valueOf(m.get("主键名称")));
		}
		return result;
	}
	
}
