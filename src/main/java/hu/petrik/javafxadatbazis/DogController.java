package hu.petrik.javafxadatbazis;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class DogController {

    @FXML
    private TableColumn<Dog, Integer> ageCol;
    @FXML
    private TableColumn<Dog, String> nameCol;
    @FXML
    private TableView<Dog> dogTable;
    @FXML
    private TableColumn<Dog,String> breedCol;
    private DogDB db;
    @FXML
    private Button deleteButton;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField breedInput;
    @FXML
    private Button updateButton;
    @FXML
    private TextField nameInput;
    @FXML
    private Spinner<Integer> ageInput;

    public void initialize(){
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        breedCol.setCellValueFactory(new PropertyValueFactory<>("breed"));
        ageInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50,0));
        try {
            db=new DogDB();
            readDogs();
        }catch (SQLException e){
            Platform.runLater(()->{
                SQLAlert(e);
                Platform.exit();
            });
        }
    }

    private void SQLAlert(SQLException e) {
        alert(Alert.AlertType.ERROR,"Hiba történt az adatbáziskapcsolat kialakításakor",e.getMessage());
    }

    private void readDogs() throws SQLException {
        List<Dog> dogs =db.readDogs();
        dogTable.getItems().clear();
        dogTable.getItems().addAll(dogs);
    }

    private void alert(Alert.AlertType alertType, String headerText, String contentText){
        Alert alert=new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    public void submitClick(ActionEvent actionEvent) {
        String name=nameInput.getText().trim();
        int age=ageInput.getValue();
        String breed=breedInput.getText().trim();
        if (name.isEmpty()){
            alert(Alert.AlertType.WARNING, "Név megadása kötelező","");
            return;
        }
        if (breed.isEmpty()){
            alert(Alert.AlertType.WARNING, "Fajta megadása kötelező","");
            return;
        }
        Dog dog =new Dog(name,age,breed);
        try {
            if (db.createDog(dog)) {
                alert(Alert.AlertType.WARNING,"Sikeres felvétel","");
                resetform();
            } else {
                alert(Alert.AlertType.WARNING,"Sikertelen felvétel","");
            }
        } catch (SQLException e) {
            SQLAlert(e);
        }
    }

    private void resetform() {
        nameInput.setText("");
        breedInput.setText("");
        ageInput.getValueFactory().setValue(0);
    }

    @FXML
    public void deleteClick(ActionEvent actionEvent) {

    }

    @FXML
    public void cancelClick(ActionEvent actionEvent) {

    }

    @FXML
    public void updateClick(ActionEvent actionEvent) {

    }
}