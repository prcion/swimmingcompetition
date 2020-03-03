package swimming.competition.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import swimming.competition.domain.Participant;
import swimming.competition.domain.User;
import swimming.competition.service.ParticipantService;
import swimming.competition.service.UserService;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
	@FXML
	private TextField loginUsername;
	
	@FXML
	private PasswordField loginPassword;
	
	@FXML
	private Button loginButton;
	
	private UserService userService;
	
	private void showError(String error){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error!!!");
		alert.setHeaderText(error);
		alert.showAndWait();
	}
	
	@FXML
	void initialize(){
		loginButton.setOnAction(event -> {
			
			String loginTextUserName = loginUsername.getText().trim();
			String loginTextPassword = loginPassword.getText().trim();
			
			try {
				boolean a = userService.verify(loginTextUserName,loginTextPassword);
				if(a == true) {
					showParticipantScreen();
				}
				else{
					showError("wrong password or username!");
				}
			} catch (SQLException e) {
				showError(e.getMessage().toString());
			}
		});
	}
	
	public void setAll(UserService userService) {
		this.userService = userService;
	}
	
	private void showParticipantScreen(){
		AnnotationConfigApplicationContext context = swimming.competition.Application.getContext();
		ParticipantService participantService = context.getBean("participantService", ParticipantService.class);
		loginButton.getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/participant.fxml"));
		
		try {
			loader.load();
		} catch (IOException e) {
			showError(e.getMessage());
		}
		
		Parent root = loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		ParticipantController participant =loader.getController();
		participant.setAll(participantService);
		stage.showAndWait();
	}
}
