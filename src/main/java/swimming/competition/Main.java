package swimming.competition;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import swimming.competition.config.DatabaseConfig;
import swimming.competition.repository.UserRepository;

import java.sql.SQLException;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = Application.getContext();
		DatabaseConfig databaseConfig = context.getBean("databaseConfig", DatabaseConfig.class);
		UserRepository userRepository = new UserRepository(databaseConfig.getDataSource());
		try {
			System.out.println(userRepository.findUser("ion"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
