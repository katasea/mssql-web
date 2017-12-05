# mssql-web[SiQ]
MSSQLSERVER数据库网页客户端，主要实现网页备份还原数据库以及查询语句和数据库自动备份等客户端功能。
<br>
启动命令
<br>
```java 
java -jar 编译后的JAR.jar [-Dfile.encoding=utf-8 乱码情况下使用]
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
![手机浏览器登录](http://upload.ouliu.net/i/2017113016511036t87.jpeg)

环境依赖
---------
JDK1.7+

部署步骤
---------
下载版本的jar包。启动命令
<br>
```java 
java -jar 编译后的JAR.jar [-Dfile.encoding=utf-8 乱码情况下使用]
     --spring.datasource.username=数据库用户 默认sa
     --spring.datasource.password=数据库密码 默认123
     --druid.loginUsername=Druid登陆用户名 默认slo
     --druid.loginPassword=Druid登陆密码 默认slo
```

乱码问题
---------
win10 cmd 设置Dfile.encoding 无效的需要更改cmd的编码格式。以下是百度步骤

1. 运行CMD；
2. 输入CHCP，回车查看当前的编码；【记住它万一你想改回来】
3. 输入CHCP 65001，回车；
4. 仅如此，还是不能支持UTF8的正常显示，你还要在窗体上右键，选择属性，来设置字体；
5. 操作完上面几步后，即使你原来的字体里面没有显示Lucida Console这个字体，现在应该也能看到了。选择它。如果原来就有，可以选上它先试试，不行在执行上述步骤；
6. 选择只应用到本窗体，确认。


声明
---------


协议
---------
GPLv3

