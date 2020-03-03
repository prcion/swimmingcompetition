package swimming.competition;

import org.springframework.context.annotation.*;
import swimming.competition.service.UserService;
import swimming.competition.config.DatabaseConfig;
import swimming.competition.service.ParticipantService;

@Configuration
@ComponentScan(basePackages = {"swimming.competition"})
@PropertySource(value = "application.yml")
public class Application{
	
	@Bean
	public DatabaseConfig databaseConfig(){
		return new DatabaseConfig();
	}
	
	@Bean
	public UserService userService(){return new UserService();}
	
	@Bean
	public ParticipantService participantService(){return new ParticipantService();}
	
	public static AnnotationConfigApplicationContext getContext(){
		return new AnnotationConfigApplicationContext(Application.class);
	}
}
