package emiel.databases.sql;

import emiel.model.DailyMood;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Emiel Bloem
 * <p>
 * Get all moods from DB
 */
public class AllMoodsDAO extends AbstractDAO{


    public AllMoodsDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    public void saveMood(DailyMood dailyMood) {
        String sql = "INSERT INTO moodbookfx.allmoods (`date`, `mood`, `extra_info`) VALUES (?,?,?)";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, dailyMood.getDate().toString());
            preparedStatement.setString(2, dailyMood.getMood());
            preparedStatement.setString(3, dailyMood.getExtraInfo());
            executeManipulateStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<DailyMood> getAllMoods() {
        String sql = "SELECT * FROM moodbookfx.allmoods ORDER BY `date` DESC;";
        List<DailyMood> dailyMoodsList = new ArrayList<>();
        try {
            setupPreparedStatement(sql);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                LocalDate date = LocalDate.parse(resultSet.getString(2));
                String mood = resultSet.getString(3);
                String extra= resultSet.getString(4);
                dailyMoodsList.add(new DailyMood(date, mood, extra));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dailyMoodsList;
    }

    public List<DailyMood> getMoodWithDate(LocalDate date) {
        String sql = "SELECT * FROM moodbookfx.allmoods WHERE `date` = ?;";
        List<DailyMood> dailyMoodsList = new ArrayList<>();
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, String.valueOf(date));
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                String mood = resultSet.getString(3);
                String extra= resultSet.getString(4);
                dailyMoodsList.add(new DailyMood(date, mood, extra));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dailyMoodsList;
    }

    public List<DailyMood> getMoodWithMood(String mood) {
        String sql = "SELECT * FROM moodbookfx.allmoods WHERE `mood` = ? ORDER BY `date` DESC;";
        List<DailyMood> dailyMoodsList = new ArrayList<>();
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, mood);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                LocalDate localDate = LocalDate.parse(resultSet.getString(2));
                String extra= resultSet.getString(4);
                dailyMoodsList.add(new DailyMood(localDate, mood, extra));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dailyMoodsList;
    }

    public void deleteMood(LocalDate date) {
        String sql = "DELETE FROM moodbookfx.allmoods WHERE `date` = ?;";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, date.toString());
            executeManipulateStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
