package swimming.competition;

import org.springframework.context.annotation.*;
import swimming.competition.config.DatabaseConfig;

@Configuration
@ComponentScan(basePackages = {"swimming.competition"})
@PropertySource(value = "application.yml")
public class Application{
	
	@Bean
	public DatabaseConfig databaseConfig(){
		return new DatabaseConfig();
	}
	
	public static AnnotationConfigApplicationContext getContext(){
		return new AnnotationConfigApplicationContext(Application.class);
	}
}
