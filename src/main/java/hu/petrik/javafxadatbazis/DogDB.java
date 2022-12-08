package hu.petrik.javafxadatbazis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DogDB {
    Connection conn;
    public static String DB_DRIVER = "mysql";
    public static String DB_HOST = "localhost";
    public static String DB_PORT = "3306";
    public static String DB_DBNAME = "java";
    public static String DB_USER = "root";
    public static String DB_PASS = "";

    public DogDB() throws SQLException {
        String url = String.format("jdbc:%s://%s:%s/%s", DB_DRIVER, DB_HOST, DB_PORT, DB_DBNAME);
        conn = DriverManager.getConnection(url, DB_USER, DB_PASS);
    }

    public boolean createDog(Dog dog) throws SQLException {
        String sql = "INSERT INTO dogs (name, age, breed) VALUES (?,?,?)";
        PreparedStatement stat = conn.prepareStatement(sql);
        stat.setString(1, dog.getName());
        stat.setInt(2, dog.getAge());
        stat.setString(3, dog.getBreed());
        return stat.executeUpdate() > 0;
    }

    public List<Dog> readDogs() throws SQLException {
        List<Dog> dogs = new ArrayList<>();
        String sql = "SELECT * FROM dogs";
        Statement stat = conn.createStatement();
        ResultSet result = stat.executeQuery(sql);
        while (result.next()){
            int id=result.getInt("id");
            String name=result.getString("name");
            int age =result.getInt("age");
            String breed=result.getString("breed");
            Dog dog=new Dog(id,name,age,breed);
            dogs.add(dog);
        }
        return dogs;
    }

    public void updateDogs() {

    }

    public boolean deleteDogs(int id) throws SQLException {
        String sql="DELETE FROM dogs WHERE id = ?";
        PreparedStatement  stat =conn.prepareStatement(sql);
        stat.setInt(1,id);
        return stat.executeUpdate()>0;
    }
}
