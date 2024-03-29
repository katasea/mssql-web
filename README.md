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
乱码问题 需要加入 logback.xml 具体可以百度配置。

```java
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <contextName>component-report</contextName>
    <property name="LOG_PATH" value="log" />

    <!-- 日志记录器，日期滚动记录 -->
    <appender name="FILEERROR" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文件的路径及文件名 -->
        <file>${LOG_PATH}/comrep_log_error.log</file>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 归档的日志文件的路径，例如今天是2013-12-21日志，当前写的日志文件路径为file节点指定，可以将此文件与file指定文件路径设置为不同路径，从而将当前日志文件或归档日志文件置不同的目录。
            而2013-12-21的日志文件在由fileNamePattern指定。%d{yyyy-MM-dd}指定日期格式，%i指定索引 -->
            <fileNamePattern>${LOG_PATH}/error/comrep_log-error-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <!-- 除按日志记录之外，还配置了日志文件不能超过2M，若超过2M，日志文件会以索引0开始，
            命名日志文件，例如log-error-2013-12-21.0.log -->
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <!-- 追加方式记录日志 -->
        <append>true</append>
        <!-- 日志文件的格式 -->
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>===%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %logger Line:%-3L - %msg%n</pattern>
            <charset>utf-8</charset>
        </encoder>
        <!-- 此日志文件只记录info级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>error</level>
            <!--<onMatch>ACCEPT</onMatch>-->
            <!--<onMismatch>DENY</onMismatch>-->
        </filter>
    </appender>

    <!-- 日志记录器，日期滚动记录 -->
    <appender name="FILEDEBUG" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文件的路径及文件名 -->
        <file>${LOG_PATH}/comrep_log_debug.log</file>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 归档的日志文件的路径，例如今天是2013-12-21日志，当前写的日志文件路径为file节点指定，可以将此文件与file指定文件路径设置为不同路径，从而将当前日志文件或归档日志文件置不同的目录。
            而2013-12-21的日志文件在由fileNamePattern指定。%d{yyyy-MM-dd}指定日期格式，%i指定索引 -->
            <fileNamePattern>${LOG_PATH}/info/comrep_log-debug-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <!-- 除按日志记录之外，还配置了日志文件不能超过2M，若超过2M，日志文件会以索引0开始，
            命名日志文件，例如log-error-2013-12-21.0.log -->
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <!-- 追加方式记录日志 -->
        <append>true</append>
        <!-- 日志文件的格式 -->
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %logger Line:%-3L - %msg%n</pattern>
            <charset>utf-8</charset>
        </encoder>
        <!-- 此日志文件只记录debug级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>debug</level>
            <!--<onMatch>ACCEPT</onMatch>-->
            <!--<onMismatch>DENY</onMismatch>-->
        </filter>
    </appender>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <!--encoder 默认配置为PatternLayoutEncoder-->
        <encoder>
            <!--<pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %logger Line:%-3L - %msg%n</pattern>-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} : %msg%n</pattern>
            <!--<charset>gbk</charset>-->
        </encoder>
        <!--此日志appender是为开发使用，只配置最底级别，控制台输出的日志级别是大于或等于此级别的日志信息-->
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>debug</level>
        </filter>
    </appender>

    <logger name="org.springframework" level="WARN" />
    <logger name="com.main.dao" level="DEBUG" />

    <!-- 生产环境下，将此级别配置为适合的级别，以免日志文件太多或影响程序性能 -->
    <root level="INFO">
        <!--<appender-ref ref="FILEERROR" />-->
        <!--<appender-ref ref="FILEWARN" />-->
        <appender-ref ref="FILEDEBUG" />

        <!-- 生产环境将请stdout,testfile去掉 -->
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```


声明
---------


协议
---------
GPLv3

