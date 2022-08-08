package emiel.controller;

import emiel.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * @author Emiel Bloem
 * <p>
 * Menu
 */
public class MenuController {
    @FXML private Label errorLabel;

    public void addMood() throws IOException {
        try {
            App.setRoot("addMood");
        } catch (IOException e) {
            System.err.println("SQL error: " + e);
            errorLabel.setText("SQL Error");
        }
    }
    public void viewMoods() throws IOException {
        try {
            App.setRoot("viewBook");
        } catch (IOException e) {
            System.err.println("SQL error: " + e);
            errorLabel.setText("SQL Error");
        }


    }
}
