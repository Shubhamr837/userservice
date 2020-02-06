package co.hackerscode.userservice.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static co.hackerscode.userservice.dao.UserDaoConstants.*;

@Configuration
public class JpaConfig {

    private Connection con = null;
    private PreparedStatement ps = null;
    private String createtablequery = "create table if not exists Users( "
    + COLUMN_ID +
    " INT NOT NULL AUTO_INCREMENT PRIMARY KEY , "
    +COLUMN_FIRSTNAME+" VARCHAR(100) NOT NULL, "
    +COLUMN_LASTNAME + " VARCHAR(100) NOT NULL , "
    + COLUMN_PASSWORD+" VARCHAR(100) NOT NULL , "
    + COLUMN_EMAILID + " VARCHAR(100) NOT NULL);";
    @Bean
    public DataSource getDataSource() throws SQLException {


        DataSource dataSource ;
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/default");
        dataSourceBuilder.username("shubham");
        dataSourceBuilder.password("Dtbranger1@");


        dataSource = dataSourceBuilder.build();
        con = dataSource.getConnection();
        ps = con.prepareStatement(createtablequery);
        ps.execute();
        return dataSource;
    }
}