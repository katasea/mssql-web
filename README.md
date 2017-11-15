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
![image](https://github.com/kkillala/mssql-web/blob/master/src/main/resources/static/inspinia/img/readme/img-login-page.jpg)
主界面<br>
![image](https://github.com/kkillala/mssql-web/blob/master/src/main/resources/static/inspinia/img/readme/img-main-page.jpg)
