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
登陆页面<br>
![image](https://gitee.com/kkillala/mssql-web/attach_files/download?i=106711&u=http%3A%2F%2Ffiles.git.oschina.net%2Fgroup1%2FM00%2F02%2F6A%2FPaAvDFoeKzyAJLvjAABKWrG2oLg740.jpg%3Ftoken%3D2d356bd97c570f64ee822317c8ff1c86%26ts%3D1511926598%26attname%3Dimg-login-page.jpg)
<br>
主界面<br>
![image](https://gitee.com/kkillala/mssql-web/attach_files/download?i=106712&u=http%3A%2F%2Ffiles.git.oschina.net%2Fgroup1%2FM00%2F02%2F6A%2FPaAvDFoeK0aAQxJYAAE1aGlJFi4835.jpg%3Ftoken%3D7d309aae474e4378b48ea4f92501f7e3%26ts%3D1511926682%26attname%3Dimg-main-page.jpg)


项目简介
---------
MSSQLSERVER数据库网页客户端，主要实现网页备份还原数据库以及查询语句和数据库自动备份等客户端功能

功能特性
---------
支持在线还原，备份，上传备份文件和下载备份文件功能。

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

