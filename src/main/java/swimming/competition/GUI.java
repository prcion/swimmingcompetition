package swimming.competition;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import swimming.competition.controller.LoginController;
import swimming.competition.service.ParticipantService;
import swimming.competition.service.UserService;

import java.io.IOException;


public class GUI extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException{
		primaryStage.setTitle("Login");
		init(primaryStage);
		
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void init(Stage primaryStage) throws IOException {
		AnnotationConfigApplicationContext context = swimming.competition.Application.getContext();
		UserService userService = context.getBean("userService", UserService.class);
		ParticipantService participantService = context.getBean("participantService", ParticipantService.class);
		FXMLLoader loginLoader = new FXMLLoader();
		loginLoader.setLocation(getClass().getResource("/javafx/login.fxml"));
		
		AnchorPane loginLayot = loginLoader.load();
		primaryStage.setScene(new Scene(loginLayot));
		
			LoginController loginController = loginLoader.getController();
			loginController.setAll(userService, participantService);
	}
}
