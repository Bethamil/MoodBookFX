package emiel.controller;

import emiel.App;
import emiel.databases.sql.AllMoodsDAO;
import emiel.databases.sql.MoodsDAO;
import emiel.model.DailyMood;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Emiel Bloem
 * <p>
 * View all moods
 */
public class ViewBookController implements Initializable {

    @FXML private ListView<String> listViewMoods;
    @FXML private DatePicker datePickerThing;
    @FXML private Label extraInfoLabel;
    @FXML private Button deleteForRealButton;
    @FXML private ChoiceBox<String> selectedMood;

    private String currentItem;

    private List<DailyMood> dailyMoodList;

    public void pickedDate() {
        AllMoodsDAO allMoodsDAO = new AllMoodsDAO(App.getDbAccess());
        dailyMoodList = allMoodsDAO.getMoodWithDate(datePickerThing.getValue());
        setTheList();
    }
    private void setListViewMoods() {
        AllMoodsDAO allMoodsDAO = new AllMoodsDAO(App.getDbAccess());
        dailyMoodList = allMoodsDAO.getAllMoods();
        setTheList();
    }

    private void setTheList() {
        String[] allMoodsArray = new String[dailyMoodList.size()];
        for (int i = 0; i < allMoodsArray.length; i++) {
            allMoodsArray[i] = dailyMoodList.get(i).toString();
        }
        listViewMoods.getItems().clear();
        listViewMoods.getItems().addAll(allMoodsArray);
    }

    public void menuButton() throws IOException {
        App.getDbAccess().closeConnection();
        App.setRoot("menu");
    }

    public void wantToDelete() {
        if (currentItem != null) {
            deleteForRealButton.setVisible(true);
        } else {
            extraInfoLabel.setText("Select an item first");
        }
    }


    public void deleteThis() {
        AllMoodsDAO allMoodsDAO = new AllMoodsDAO(App.getDbAccess());
        try {
            allMoodsDAO.deleteMood(LocalDate.parse(currentItem.split(" ")[0]));
            listViewMoods.getItems().clear();
            setListViewMoods();
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
        deleteForRealButton.setVisible(false);
        extraInfoLabel.setText("");
    }


    public void showAllModes() {
        listViewMoods.getItems().clear();
        datePickerThing.getEditor().clear();
        selectedMood.setValue("");
        setListViewMoods();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        App.getDbAccess().openConnection();
        MoodsDAO moodsDAO = new MoodsDAO(App.getDbAccess());
        AllMoodsDAO allMoodsDAO = new AllMoodsDAO(App.getDbAccess());

        selectedMood.getItems().setAll(moodsDAO.getAllMoods());
        setListViewMoods();

        selectedMood.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
                    dailyMoodList = allMoodsDAO.getMoodWithMood(selectedMood.getValue());
                    setTheList();
                    datePickerThing.getEditor().clear();
                });


        listViewMoods.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            currentItem = listViewMoods.getSelectionModel().getSelectedItem();


            for (DailyMood dailyMood : dailyMoodList) {
                try {
                    if (dailyMood.getDate().equals(LocalDate.parse(currentItem.split(" ")[0]))) {
                        deleteForRealButton.setVisible(false);
                        extraInfoLabel.setText(dailyMood.getExtraInfo());
                    }
                } catch (Exception ignored) {
                }
            }
        });
    }


}
