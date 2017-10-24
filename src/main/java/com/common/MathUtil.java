package com.common;

import java.util.HashSet;
import java.util.Set;

/**
 ****************************************************************************************
 * 						解析字符串并计算字符串的结果
 * 
 * @author 工具类
 ****************************************************************************************
 */
public class MathUtil {
private static MathBasic ps = new MathBasic();
	
	/**
	 * 调用入口方法
	 * @param str 传入字符串计算表达式
	 * @return    计算完的结果
	 */
	public static String cacComplex(String str) {
		if(str == null || "".equals(str)) {
			return "0";
		}else {
			str = str.replace("null", "0");
			if(str.charAt(0)=='-') {
				str = "0" + str;
			}
		}
//		if(str!=null&&str.charAt(0)=='-'){
//			str = "0" + str;
//		}
		/**
		 * 去除连续两个符号
		 * 
		 */
//		for(int i=0; i<str.length(); i++) {
//			if(str.charAt(i)=='-'&&str.charAt(i+1)=='-') {
//				str = str.substring(0,i)+"+"+str.substring(i+2);
//			}
//			if((str.charAt(i)>'9'||str.charAt(i)<'0')&&(str.charAt(i)!=')')&&(str.charAt(i+1)>'9'||str.charAt(i+1)<'0')) {
//				str = str.substring(0,i+1)+"0"+str.substring(i+1);
//			}
//		}
		/**
		 * 处理-78350.08*100/-78350.08
		 */
		
		
		
//		String right = str.substring(str.indexOf("/")+1);
//		if(str.indexOf("/") != -1 && right.charAt(0) == '-') {
//			//System.out.println("特殊处理代码22 _ MathUtil");
//			String  rightResult = MathUtil.cacComplex(right);
//			String left = str.substring(0,str.indexOf("/"));
//			String leftResult = MathUtil.cacComplex(left);
//			if(leftResult.charAt(0) == '-' && rightResult.charAt(0) == '-') {
//				str = leftResult.substring(1) + "/" + rightResult.substring(1);
//			}else if(leftResult.charAt(0) != '-'&&rightResult.charAt(0) == '-' ){
//				str = "0-(" + leftResult + "/" + rightResult.substring(1)+")";
//			}else if(leftResult.charAt(0) == '-'&&rightResult.charAt(0) != '-'){
//				str = "0-(" + leftResult + "/" + rightResult+")";
//			}
//			
//		}
//		
		
		int has = str.indexOf("[");
		int have = str.indexOf("{");
		if(has!=-1||have!=1){
			str = str.replaceAll("[\\[\\{]", "(").replaceAll("[\\]\\}]",")");
			
		}
		int c1 = str.lastIndexOf('(');
		if(c1 == -1){
			return myTransetFunction(cac(str));
		}
		int cr = str.indexOf(')',c1);
		String left = "";
		String right = "";
		String middle = "";
		try{
			left = str.substring(0,c1);
			right = str.substring(cr+1);
			middle = str.substring(c1+1,cr);
			
			return myTransetFunction(cacComplex(left+cac(middle)+right));
		}catch(Exception e) {
			return null;
		}
	}
	
	private static String cac(String str) {
		if(str.equals("")) return "0";
		int m1 = str.indexOf('*');
		int d1 = str.indexOf('/');
		if(m1 == -1 && d1 == -1) {
			return myTransetFunction(cacNoMD(str));
		}
		int index = 0;
		if(m1 < d1 ){
			index = m1 == -1 ? d1:m1;
		}else {
			index = d1 == -1 ? m1:d1; 
		}
		
		String left = str.substring(0,index);
		String m11 = lastNumber(left);
		left = left.substring(0,left.length()-m11.length());
		String right = str.substring(index+1);
		String m2 = firstNumber(right);
		right = right.substring(m2.length());
		String tmp = "0";
		try {
			if(index == m1) {
				tmp = ps.mul(m11,m2);
			}else if(index == d1) {
				tmp = ps.div(m11, m2, 4);
			}
		}catch(Exception e) {
			e.printStackTrace();
			tmp = "0";
		}
		tmp = myTransetFunction(tmp);
		return myTransetFunction(cac(left + tmp + right));
	}
	public static String myTransetFunction(String tmp) {
		if(tmp==null||"".equals(tmp)) {
			tmp = "0";
		}
		try{
			if(tmp.indexOf("E")!=-1) {
				String t1 = tmp.substring(0, tmp.indexOf("E"));
				String t2 = tmp.substring(tmp.indexOf("E")+1);
				if("0".equals(t1)) {
					return "0";
				}else {
					return String.valueOf(Math.pow(Double.parseDouble(t1), Double.parseDouble(t2)));
				}
			}else {
				return tmp;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return "0";
		}
		
	}
	private static String lastNumber(String str) {
		StringBuilder sb = new StringBuilder();
		for(int i=str.length()-1;i>=0;i--){
			char c = str.charAt(i);
			if(!Character.isDigit(c) && c!='.') {
				//Mod By CFQ
				if(i == 0) {
					sb.append(c);
				}else if((i-1)>=0) {
					if(!Character.isDigit(str.charAt(i-1))&&str.charAt(i-1)!='.') {
						sb.append(c);
					}
				}
				///
				break;
			}
			sb.append(c);
		}
		return sb.reverse().toString();
	}
	
	private static String firstNumber(String str) {
		StringBuilder sb = new StringBuilder();
		for(char c : str.toCharArray()) {
			//Mod BY CFQ 20141103
			if(str.charAt(0)=='-') {
				sb.append(c);
				continue;
			}
			if(!Character.isDigit(c)&& c!='.') break;
			sb.append(c);
		}
		return sb.toString();
	}
	
	private static String cacNoMD(String str) {
		String ret = "0";
		StringBuilder sb = new StringBuilder();
		char sign = '+';
		for(char c: (str+"+").toCharArray()){
			if(!Character.isDigit(c)&& c!='.'){
				if(sb.length()==0) {
					sb = new StringBuilder();
					sb.append(c);
//					sign = c;
					continue;
				}else {   
					// mod by cfq 
					
					///
					if(sign == '+') {
						ret = ps.add(ret, sb.toString());
					}else {
						ret = ps.sub(ret, sb.toString());
					}
					sb = new StringBuilder();
					sign = c;
				}
			}else {
				sb.append(c);
			}
		}
		return myTransetFunction(ret);
	}


	
//	/**
//	 * 通过给定的公式列表获取数据库表对应的值 （废弃）
//	 * 目前只能解析公式           JGR(KS)(X)SJ( 数据类型，年，月，日，部门)
//	 * @param yeWuBean
//	 * @param myOrders 指令
//	 * @return
//	 */
//	public static List<Map<String, Map<String, String>>> getDatasByOrders(
//			YeWuBean yeWuBean, Set<String> myOrders) {
//		//个人公式数据
//		Map<String, Map<String,String>> grDatas = new HashMap<String, Map<String,String>>();
//		//部门公式数据
//		Map<String, Map<String,String>> bmDatas = new HashMap<String, Map<String,String>>();
//		
//		for(String order : myOrders) {
//			//获取参数列表
//			Map<String,String> params = MathUtil.getParamsByOrder(yeWuBean,order);
//			if(order.indexOf("GR")!=-1||order.indexOf("XX")!=-1) {
//				Map<String,String> datas = DeptDataOptImpl.getDataByOrder(yeWuBean,order,params,"GR");
//				grDatas.put(order, datas);
//			}
//			if(order.indexOf("BM")!=-1||order.indexOf("XX")!=-1) {
//				Map<String,String> datas = DeptDataOptImpl.getDataByOrder(yeWuBean, order, params,"BM");
//				bmDatas.put(order, datas);
//			}
//		}
//		List<Map<String,Map<String,String>>> result = new ArrayList<Map<String,Map<String,String>>>();
//		result.add(grDatas);
//		result.add(bmDatas);
//		return result;
//	}
//
//	/**
//	 * 解析出自定义公式里面的参数列表
//	 * JBMSJ(101,0,0,0,1) 参数列表为 101,0,0,0,1
//	 * @param order
//	 * @return
//	 */
//	private static Map<String,String> getParamsByOrder(YeWuBean yeWuBean,String order) {
//		if(order.indexOf("(")!=-1&&order.indexOf(")")!=-1) {
//			Map<String,String> params = new HashMap<String, String>();
//			params.put("sjlxbh","0");
//			params.put("year",yeWuBean.getTheYear());
//			params.put("month1",yeWuBean.getTheMonth());
//			params.put("month2",yeWuBean.getTheMonth());
//			params.put("bmid","0");
//			order = order.substring(order.indexOf("(")+1,order.indexOf(")"));
//			StringBuffer sb = new StringBuffer();
//			List<String> temp = new ArrayList<String>();
//			for(int i=0; i<order.length();) {
//				sb.setLength(0);
//				for(int j=i; j<order.length(); j++) {
//					if(order.charAt(j)==',') {
//						break;
//					}
//					sb.append(""+order.charAt(j));
//				}
//				if(sb.length()!=0) {
//					temp.add(sb.toString());
//					i = i + sb.length()+1;
//				}else {
//					i = i + 1;
//				}
//			}
//			for(int i=0 ; i<temp.size(); i++) {
//				switch(i) {
//				case 0: 
//					params.put("sjlxbh", temp.get(i));
//					break;
//				case 1:
//					String year = temp.get(i);
//					if(year.length()<4) {
//						if(year.charAt(0)=='-') {
//							year = MathUtil.cacComplex(yeWuBean.getTheYear()+year);
//						}else {
//							year = MathUtil.cacComplex(yeWuBean.getTheYear()+"+"+year);
//						}
//					}
//					params.put("year", year);
//					break;
//				case 2:
//					String month1 = temp.get(i);
//					if(month1.charAt(0)=='-') {
//						month1 = MathUtil.cacComplex(yeWuBean.getTheMonth()+month1);
//					}else if("0".equals(month1)) {
//						month1 = yeWuBean.getTheMonth();
//					}
//					params.put("month1", month1);
//					break;
//				case 3:
//					String month2 = temp.get(i);
//					if(month2.charAt(0)=='-') {
//						month2 = MathUtil.cacComplex(yeWuBean.getTheMonth()+month2);
//					}else if("0".equals(month2)) {
//						month2 = yeWuBean.getTheMonth();
//					}
//					params.put("month2", month2);
//					break;
//				case 4:
//					params.put("bmid", temp.get(i));
//					break;
//				}
//			}
//			
//			return params;
//		}else {
//			return null;
//		}
//	}

	/**
	 * 获取公式中的单个公式
	 */
	public static Set<String> getMyOrders(String formular) {
		//解析出单个公式
		Set<String> myOrders = new HashSet<String>();
		StringBuffer temp = new StringBuffer();
		for(int i=0; i<formular.length(); ) {
			char t = formular.charAt(i);
			if(t>='A'&&t<='Z') {
				temp.setLength(0);
				for(int j=i; j<formular.length(); j++) {
					temp.append(""+formular.charAt(j));
					if(formular.charAt(j) == ')') {
						break;
					}
				}
				myOrders.add(temp.toString());
				i = i+temp.length();
			}else {
				i = i+1;
			}
		}
		return myOrders;
	}
	
}
