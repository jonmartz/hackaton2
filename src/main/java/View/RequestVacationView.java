package View;

import Controller.RequestVacationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * View for the request vacation screen
 */
public class RequestVacationView extends AbstractView{

    @FXML
    public Button sendButton;
    public ComboBox typeBox;
    public TextArea textBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RequestVacationController requestVacationController = new RequestVacationController();
        this.setController(requestVacationController);
        requestVacationController.setView(this);
        typeBox.getItems().addAll("Chat message", "Request message");
    }

    public void sendMessage() {
        /* todo: this is activated when the send message button is pressed. Must now save the message to
        the database. All the fields needed are in the database object: the currentUser is
        the user that sends, checkingUser is the user that is receiving, and also the course ID, year
        and semester are being hold by the database as fields.
        */
    }
}
