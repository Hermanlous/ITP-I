package app;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class AppController {

    @FXML
    private CheckBox checkBox;

    @FXML
    private Label messageLabel;

    // Method to handle the checkbox action
    @FXML
    private void handleCheckBoxAction() {
        if (checkBox.isSelected()) {
            messageLabel.setText("Checked!");
        } else {
            messageLabel.setText("");
        }
    }
}
