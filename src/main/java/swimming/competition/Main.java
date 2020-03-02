package swimming.competition;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import swimming.competition.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = Application.getContext();
		DatabaseConfig databaseConfig = context.getBean("databaseConfig", DatabaseConfig.class);
		try {
			Connection con = databaseConfig.getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from account");
			while(rs.next())
				System.out.println(rs.getInt(1));
			con.close();
		}catch(Exception e){ System.out.println(e);}
	}
}
