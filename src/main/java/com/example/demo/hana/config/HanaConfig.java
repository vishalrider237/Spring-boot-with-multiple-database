package com.example.demo.hana.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org
.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
      entityManagerFactoryRef = "entityManagerFactor",
      basePackages = {"com.example.demo.hana.repo"},
      transactionManagerRef = "transactionManagerBean"
)
public class HanaConfig {
	
   @Autowired
   private Environment environment;
   @Bean
   @Primary
   public DataSource dataSource(){
       DriverManagerDataSource dataSource=new DriverManagerDataSource();
       dataSource.setUrl(environment.getProperty("spring.datasource.url"));
       dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
       dataSource.setUsername(environment.getProperty("spring.datasource.username"));
       dataSource.setPassword(environment.getProperty("spring.datasource.password"));
       System.out.println("Hana url is:"+environment.getProperty("spring.datasource.url"));
       dataSource.setConnectionProperties(hibernateProperties());
     return dataSource;
   }
   @Bean(name = "entityManagerFactor")
 @Primary
  public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
      LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
      entityManagerFactoryBean.setDataSource(dataSource());
      entityManagerFactoryBean.setPackagesToScan("com.example.demo.hana.entity");
     JpaVendorAdapter adaptor=new HibernateJpaVendorAdapter();
      entityManagerFactoryBean.setJpaVendorAdapter(adaptor);
      entityManagerFactoryBean.setJpaProperties(hibernateProperties());
//     Map<String, String>props=new HashMap<>();
//     props.put("hibernate.dialect","org.hibernate.dialect.HANAColumnStoreDialect");
//     props.put("hibernate.show_sql","true");
//     props.put("hibernate.hbm2ddl.auto","update");
//     entityManagerFactoryBean.setJpaPropertyMap(props);
      return entityManagerFactoryBean;
  }
  @Bean(name = "transactionManagerBean")
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
      properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HANAColumnStoreDialect");
      properties.setProperty("hibernate.show_sql", "true");
      return properties;
  }
}
