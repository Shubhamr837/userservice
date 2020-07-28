package com.debugshark.userservice.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static com.debugshark.userservice.dao.UserDaoConstants.*;

@Configuration
public class JpaConfig {

    private Connection con = null;
    private PreparedStatement ps = null;
    private String createUserTableQuery = "create table if not exists Users( "
    + COLUMN_ID +
    " INT NOT NULL AUTO_INCREMENT PRIMARY KEY , "
    +COLUMN_FIRSTNAME+" VARCHAR(100) NOT NULL, "
    +COLUMN_LASTNAME + " VARCHAR(100) NOT NULL , "
    + COLUMN_PASSWORD+" VARCHAR(100) NOT NULL , "
    + COLUMN_EMAILID + " VARCHAR(100) NOT NULL);";
    private String createUserQuestionsSolvedTableQuery = "create table if not exists Solved( "
            + COLUMN_ID + " INT NOT NULL REFERENCES Users("+ COLUMN_ID +")"+ ", "
            + COLUMN_QUESTION_ID + " INT NOT NULL REFERENCES Questions("+ COLUMN_ID +")," +
            "PRIMARY KEY ("+COLUMN_ID+","+COLUMN_QUESTION_ID+" )) ; "
            ;
    private String createQuestiontablequery = "create table if not exists Questions(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY , title VARCHAR(200) NOT NULL, category VARCHAR(50) NOT NULL ,subcategory VARCHAR(30),exampleinputurl1 VARCHAR(300) , exampleoutputurl1 VARCHAR(300),exampleinputurl2 VARCHAR(300),exampleoutputurl2 VARCHAR(300) , questionurl VARCHAR(300) NOT NULL , imageurl VARCHAR(400) , difficulty VARCHAR(10));";
    @Bean
    public DataSource getDataSource() throws SQLException {


        DataSource dataSource ;
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/default");
        dataSourceBuilder.username("shubham");
        dataSourceBuilder.password("");


        dataSource = dataSourceBuilder.build();
        con = dataSource.getConnection();
        ps = con.prepareStatement(createUserTableQuery);
        ps.execute();
        con = dataSource.getConnection();
        ps = con.prepareStatement(createQuestiontablequery);
        ps.execute();
        con = dataSource.getConnection();
        ps = con.prepareStatement(createUserQuestionsSolvedTableQuery);
        ps.execute();
        return dataSource;
    }
}
