package com.example.demo.postgree.config;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.AbstractDriverBasedDataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "SecondentityManagerFactor",
        basePackages = {"com.example.demo.postgree.repo"},
        transactionManagerRef = "SecondtransactionManagerBean"
)
public class PostgreeConfig {
	@Autowired
    private Environment environment;
    @Bean(name = "secondDataSource")
    @Primary
    public DataSource dataSource(){
    	
    	
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("postgree.datasource.url"));
        System.out.println("PostgreeSql Url is "+environment.getProperty("postgree.datasource.url"));
        dataSource.setDriverClassName(environment.getProperty("postgree.datasource.driver-class-name"));
        dataSource.setUsername(environment.getProperty("postgree.datasource.username"));
        dataSource.setPassword(environment.getProperty("postgree.datasource.password"));
        dataSource.setConnectionProperties(hibernateProperties());
        return dataSource;
    }
    @Bean(name = "SecondentityManagerFactor")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("com.example.demo.postgree.entity");
        JpaVendorAdapter adaptor=new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(adaptor);
        entityManagerFactoryBean.setJpaProperties(hibernateProperties());
//        Map<String, String> props=new HashMap<>();
//        props.put("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
//        props.put("hibernate.hbm2ddl.auto","true");
//        props.put("hibernate.ddl-auto","update");
//        entityManagerFactoryBean.setJpaPropertyMap(props);
        return entityManagerFactoryBean;
    }
    @Bean(name = "SecondtransactionManagerBean")
    @Primary
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager manager=new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return manager;
    }
    @Primary
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.show_sql", "true");
        return properties;
    }
}
