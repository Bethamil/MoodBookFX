package emiel.controller;

import emiel.App;
import emiel.databases.sql.AllMoodsDAO;
import emiel.databases.sql.MoodsDAO;
import emiel.model.DailyMood;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Emiel Bloem
 * <p>
 * Add a mood
 */

public class AddMoodController implements Initializable {


    @FXML private TextArea extraText;
    @FXML private DatePicker selectDate;
    @FXML private Label labelMood;
    @FXML private TextArea addModeTextField;
    @FXML private ChoiceBox<String> selectMood;

    public void addMood() {
        AllMoodsDAO allMoodsDAO = new AllMoodsDAO(App.getDbAccess());
        try {
            allMoodsDAO.saveMood(new DailyMood(selectDate.getValue(), selectMood.getValue(), extraText.getText()));
            labelMood.setText("Mood has been added");
            extraText.clear();
        }

        catch (NullPointerException n){
            System.err.println("SQL Error " + n);
        }catch (Exception e) {
            System.err.println("SQL Error: " + e);
            labelMood.setText("You already added a mood on this date. Delete the old mood first in My Book");
        }
    }

    public void goToMenu() throws IOException {
        App.getDbAccess().closeConnection();
        App.setRoot("menu");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        App.getDbAccess().openConnection();
        MoodsDAO moodsDAO = new MoodsDAO(App.getDbAccess());
        selectMood.getItems().addAll(moodsDAO.getAllMoods());
    }

    public void addThisToo() {
        MoodsDAO moodsDAO = new MoodsDAO(App.getDbAccess());
        try {
            if (!addModeTextField.getText().trim().equals("")) {
                moodsDAO.saveMood(addModeTextField.getText().trim());
            }
        } catch (Exception e) {
            labelMood.setText("Mood already exists");
            System.err.println("SQL Error:" +e);

        }
        selectMood.getItems().clear();
        selectMood.getItems().addAll(moodsDAO.getAllMoods());
        selectMood.setValue(addModeTextField.getText());
    }
}
