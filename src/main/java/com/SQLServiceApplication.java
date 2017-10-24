package com;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan("com.main.dao")
@EnableTransactionManagement
public class SQLServiceApplication {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(SQLServiceApplication.class);
	 
    //DataSource配置
    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        return new org.apache.tomcat.jdbc.pool.DataSource();
    }

    //提供SqlSeesion
//    @Bean
//    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
//
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource());
//
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/com/main/mapping/*.xml"));
//
//        return sqlSessionFactoryBean.getObject();
//    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    public static void main(String[] args) {
        SpringApplication.run(SQLServiceApplication.class, args);
    }

    @Value("${server.port}")
    String port;
    @Value("${foo}")
    String foo;
    @RequestMapping("/hi")
    public String home(@RequestParam String name) {
        return "HI "+name+",I am from port:" +port+ ",I got foo="+foo;
    }
    @RequestMapping("/no")
    public String no(@RequestParam String name) {
    	return "Bye "+name+",i am from port:" +port+",I got foo="+foo;
    }
    @RequestMapping("/info")
    public String info() {
    	return "I DON'T KNOW PLEASE !";
    }

}
