package swimming.competition.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import swimming.competition.domain.Participant;
import swimming.competition.domain.ParticipantDTO;
import swimming.competition.domain.Proba;
import swimming.competition.service.ParticipantService;
import swimming.competition.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ParticipantController {
	
	ObservableList<ParticipantDTO> model = FXCollections.observableArrayList();
	
	@FXML
	private RadioButton m50;
	
	@FXML
	private ToggleGroup distanceGroup;
	
	@FXML
	private ToggleGroup styleGroup;
	
	private String distance = "50m";
	private String style = "liber";
	
	@FXML
	private TableView<ParticipantDTO> table;
	
	@FXML
	private TextField count;
	
	@FXML
	private TextField nameTextField;
	
	@FXML
	private TextField ageTextField;
	
	@FXML
	private Button addButton;
	
	private ParticipantService participantService;
	private UserService userService;
	@FXML
	private Button btnLogOut;
	
	public void setAll(ParticipantService participantService, UserService userService){
		this.participantService = participantService;
		this.userService = userService;
		Stage stageBtn = (Stage) btnLogOut.getScene().getWindow();
		stageBtn.show();
		initAll();
		
	}
	
	private void initAll(){
		List<Participant> lists = null;
		try {
			lists = participantService.find(style, distance);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		count.setText(Integer.toString(lists.size()));
		
		model.setAll(getParticipantDTO(lists));
	}
	
	private List<ParticipantDTO> getParticipantDTO(List<Participant> lists){
		List<ParticipantDTO> participantDTOS = new ArrayList<>();
		for(Participant participant: lists){
			ParticipantDTO participantDTO = new ParticipantDTO();
			participantDTO.setName(participant.getName());
			participantDTO.setAge(participant.getAge());
			String probe = "";
			for(Proba proba: participant.getProbe()){
				probe += proba.getDistance() + " " + proba.getStyle() + "\n";
			}
			participantDTO.setProbe(probe);
			participantDTOS.add(participantDTO);
		}
		return participantDTOS;
	}
	
	private void showError(String error){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error!!!");
		alert.setHeaderText(error);
		alert.showAndWait();
	}
	
	@FXML
	void initialize(){
		table.setItems(model);
		table.getSelectionModel().selectedItemProperty().addListener((observableValue, participant, t1) -> loadTextFields(t1));
		
		addButton.setOnAction(event -> {
			String name = nameTextField.getText();
			int age = Integer.parseInt(ageTextField.getText());
			Participant participant = new Participant(name, age);
			Proba proba = new Proba(distance, style);
			participant.addProba(proba);
			try {
				int a = participantService.save(participant);
				if(a != 0){
					initAll();
				}else{
					showError("proba este deja adaugata.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		
		styleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				RadioButton radioButton = (RadioButton) styleGroup.getSelectedToggle();
				style = radioButton.getText();
				List<Participant> lists = null;
				try {
					lists = participantService.find(style, distance);
					model.setAll(getParticipantDTO(lists));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				count.setText(Integer.toString(lists.size()));
			}
		});
		
		distanceGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				RadioButton radioButton = (RadioButton) distanceGroup.getSelectedToggle();
				distance = radioButton.getText();
				List<Participant> lists = null;
				try {
					lists = participantService.find(style, distance);
					model.setAll(getParticipantDTO(lists));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				count.setText(Integer.toString(lists.size()));
			}
		});
		
		btnLogOut.setOnAction(event -> {
			btnLogOut.getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/javafx/login.fxml"));
			
			try {
				loader.load();
			} catch (IOException e) {
				showError(e.getMessage());
			}
			
			Parent root = loader.getRoot();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			LoginController loginController =loader.getController();
			loginController.setAll(userService, participantService);
		});
	}
	
	private void loadTextFields(ParticipantDTO participant){
		if(participant==null){
		}
		else{
			nameTextField.setText(participant.getName());
			ageTextField.setText(Integer.toString(participant.getAge()));
		}
	}
}
