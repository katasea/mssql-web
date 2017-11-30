# mssql-web[SiQ]
MSSQLSERVER数据库网页客户端，主要实现网页备份还原数据库以及查询语句和数据库自动备份等客户端功能。
<br>
启动命令
<br>
```java 
java -Dfile.encoding=utf-8 -jar 编译后的JAR.jar
     --spring.datasource.username=数据库用户 默认sa
     --spring.datasource.password=数据库密码 默认123
     --druid.loginUsername=Druid登陆用户名 默认slo
     --druid.loginPassword=Druid登陆密码 默认slo
```


项目简介
---------
MSSQLSERVER数据库网页客户端，主要实现网页备份还原数据库以及查询语句和数据库自动备份等客户端功能

功能特性
---------
支持在线还原，备份，上传备份文件和下载备份文件功能。
![登录页面](http://chuantu.biz/t6/162/1512031636x-1566638301.jpg)
![主页面](http://chuantu.biz/t6/162/1512031730x-1404793361.jpg)
![手机浏览器登录](http://s6.sinaimg.cn/middle/001Ymsvuzy7gdf7YjY1c5&690)
![手机浏览器主界面](http://s5.sinaimg.cn/middle/001Ymsvuzy7gdf7SD0Uf4&690)

环境依赖
---------
JDK1.7+

部署步骤
---------
下载版本的jar包。启动命令
<br>
```java 
java -Dfile.encoding=utf-8 -jar 编译后的JAR.jar
     --spring.datasource.username=数据库用户 默认sa
     --spring.datasource.password=数据库密码 默认123
     --druid.loginUsername=Druid登陆用户名 默认slo
     --druid.loginPassword=Druid登陆密码 默认slo
```

声明
---------


协议
---------
GPLv3

