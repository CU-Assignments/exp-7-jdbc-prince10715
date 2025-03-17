import java.sql.*;

public class EmployeeData {
    public static void main(String[] args) {
        String url = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql5768109";
        String user = "sql5768109";
        String password = "RkP3iffeUE";
        String query = "SELECT EmpID, Name, Salary FROM Employee";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("EmpID\tName\tSalary");
            System.out.println("--------------------------");

            while (rs.next()) {
                int id = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                System.out.println(id + "\t" + name + "\t" + salary);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}






## database 
  CREATE TABLE Employee (
    EmpID INT PRIMARY KEY,
    Name VARCHAR(50),
    Salary DECIMAL(10,2)
);

INSERT INTO Employee VALUES (1, 'Prince', 50000), (2, 'Buta', 60000), (3, 'Anubhav', 55000);
