package ru.svetkin.config;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class DbConfig extends AbstractJdbcConfiguration{
    @Bean
    DataSource dataSource(){
        SQLServerDataSource ds=new SQLServerDataSource();
        ds.setURL("jdbc:sqlserver://WINDOWS-G9327M3\\SQLEXPRESS;"
                + "databaseName=EduPlatform;"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "user=Rooh;"
                + "password=1111");
        return ds;
    }
    @Bean 
    PlatformTransactionManager transactionManager(DataSource ds){
        return new JdbcTransactionManager(ds);
    } 
    @Bean 
    NamedParameterJdbcOperations namedParameter(DataSource ds){
        return new NamedParameterJdbcTemplate(ds);
    } 
}
