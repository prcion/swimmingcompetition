package swimming.competition.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
@Configuration
public class DatabaseConfig {
	
	@Value("${datasource.driver}")
	private String driver;
	
	@Value("${jdbcUrl}")
	private String jdbcUrl;
	
	@Value("${user}")
	private String username;
	
	@Value("${password}")
	private String password;
	
	public DataSource getDataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
	
	public Connection getConnection() throws SQLException{
		return getDataSource().getConnection();
	}
}
