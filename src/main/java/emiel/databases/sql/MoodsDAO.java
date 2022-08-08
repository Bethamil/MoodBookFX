package emiel.databases.sql;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Emiel Bloem
 * <p>
 * Get Moods
 */
public class MoodsDAO extends AbstractDAO{
    public MoodsDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    public String[] getAllMoods(){
        String sql = "SELECT * FROM moodbookfx.moods;";
        List<String> listMoods = new ArrayList<>();
        try {
            setupPreparedStatement(sql);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                String mood = resultSet.getString("mood");
                listMoods.add(mood);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listMoods.toArray(String[]::new);
    }

    public void saveMood(String moodString) {
        String sql = "INSERT INTO moodbookfx.moods (`mood`) VALUES (?)";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, moodString);
            executeManipulateStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
