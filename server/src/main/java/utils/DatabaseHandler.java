package utils;

import ch.qos.logback.classic.Logger;
import data.*;
import exceptions.DatabaseException;
import org.slf4j.LoggerFactory;
import requests.Request;
import utils.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;

public class DatabaseHandler {


    Connection connection = null;
    String user = "s332890";
    String password = "McClHCMnPH2uM2Rb";
    String url = "jdbc:postgresql://localhost:5432/studs";
    private static final Logger logger = (Logger) LoggerFactory.getLogger(DatabaseHandler.class);
    private CollectionHandler collectionHandler;

    public Connection connect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            logger.info("Connected to the PostgreSQL server successfully.");
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public long saveMarine(SpaceMarine spaceMarine) {
        long generatedId = -1;
        if (!spaceMarine.getSaved()) {
            try {
                Connection connection = connect();
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO " + "space_marine" +
                                "(name, creation_date, coordinates_x, coordinates_y, health, height, weapon_type, melee_weapon, chapter_name, chapter_world, chapter_marines_count, chapter_parent_legion, creator)" +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                statement.setString(1, spaceMarine.getName());
                // LocalDate creationDate = LocalDate.from(spaceMarine.getCreationDate());
                // LocalTime creationTime = LocalTime.of(23, 0, 0);
                //LocalDateTime dateTime = LocalDateTime.of(creationDate, creationTime);
                LocalDateTime dateTime = spaceMarine.getCreationDate();
                statement.setTimestamp(2, Timestamp.valueOf(dateTime));
                statement.setFloat(3, spaceMarine.getCoordinates().getX());
                statement.setFloat(4, spaceMarine.getCoordinates().getY());
                statement.setFloat(5, spaceMarine.getHealth());
                statement.setFloat(6, spaceMarine.getHeight());
                statement.setObject(7, spaceMarine.getWeaponType(), Types.OTHER);
                statement.setObject(8, spaceMarine.getMeleeWeapon(), Types.OTHER);
                statement.setString(9, spaceMarine.getChapter().getName());
                statement.setString(10, spaceMarine.getChapter().getWorld());
                statement.setInt(11, spaceMarine.getChapter().getMarinesCount());
                statement.setString(12, spaceMarine.getChapter().getParentLegion());
                statement.setString(13, spaceMarine.getCreator());

                spaceMarine.setSaved();
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Inserting space_marine failed, no rows affected");
                }
                Statement selectStatement = connection.createStatement();
                try (ResultSet rs = selectStatement.executeQuery("SELECT MAX(id) FROM space_marine")) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return generatedId;
    }

    public HashSet<SpaceMarine> getAllSpaceMarines() throws SQLException {
        Connection connection = connect();
        HashSet<SpaceMarine> spaceMarines = new HashSet<SpaceMarine>();
        final String SELECT_ALL_SPACE_MARINES = "SELECT * FROM space_marine";
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SPACE_MARINES);
        {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                float coordinates_x = resultSet.getFloat("coordinates_x");
                float coordinates_y = resultSet.getFloat("coordinates_y");
                LocalDateTime creationDate = resultSet.getTimestamp("creation_date").toLocalDateTime().toLocalDate().atStartOfDay();
                float health = resultSet.getFloat("health");
                float height = resultSet.getFloat("height");
                MeleeWeapon meleeWeapon = MeleeWeapon.valueOf(resultSet.getString("melee_weapon"));
                Weapon weapon = Weapon.valueOf(resultSet.getString("weapon_type"));
                String chapter_name = resultSet.getString("chapter_name");
                String chapter_world = resultSet.getString("chapter_world");
                int chapter_marines_count = resultSet.getInt("chapter_marines_count");
                String chapter_parent_legion = resultSet.getString("chapter_parent_legion");
                String creator = resultSet.getString("creator");
                Coordinates coordinates = new Coordinates(coordinates_x, coordinates_y);
                Chapter chapter = new Chapter(chapter_name, chapter_parent_legion, chapter_marines_count, chapter_world);
                SpaceMarine spaceMarine = new SpaceMarine(id, name, creationDate, coordinates, health, height, weapon, meleeWeapon, chapter, creator);
                spaceMarine.setSaved();
                spaceMarines.add(spaceMarine);
            }
        }
        return spaceMarines;
    }

    public void deleteSpaceMarine(long id) {
        try {
            Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM space_marine WHERE id = ?");
            statement.setLong(1, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Inserting space marine failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteSpaceMarine(Request request) throws SQLException {
        HashSet<SpaceMarine> marineList = getAllSpaceMarines();
        for (SpaceMarine marine : marineList) {
            if (marine.getCreator().equals(request.getUser().getLogin()) && marine.getId() <= Long.parseLong(request.getArguments())) {
                deleteSpaceMarine(marine.getId());
            }
        }
    }
    public void register(String login, String pwd) throws SQLException {
        Connection conn = connect();
        PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO users" +
                        "(login, password)" +
                        "VALUES (?, ?)");

        Hasher hasher = new Hasher("SHA-224");
        statement.setString(1, login);
        statement.setString(2, hasher.encode(pwd));

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Registering failed, no rows affected.");
        }
    }

    public boolean checkIfUserExists(String login, String pwd) {
        try {
            Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            statement.setString(1, login);
            statement.setString(2, pwd);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }

    public void clearCollection(Request request) throws SQLException {
        HashSet<SpaceMarine> marineList = getAllSpaceMarines();
        for (SpaceMarine marine : marineList) {
            if (marine.getCreator().equals(request.getUser().getLogin())) {
                deleteSpaceMarine(marine.getId());
            }
        }
    }

    public boolean checkIfLoginExists(String login) {
        try {
            Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }
}
