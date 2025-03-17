#student.java
public class Student {
    private int studentID;
    private String name;
    private String department;
    private int marks;

    public Student(int studentID, String name, String department, int marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    public int getStudentID() { return studentID; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public int getMarks() { return marks; }
}
#dbconnection.java
import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/StudentDB";
    private static final String USER = "yourUsername";
    private static final String PASSWORD = "yourPassword";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}


#studentcontroller.java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentController {
    private Connection con;

    public StudentController() {
        try {
            con = DBConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addStudent(Student student) throws SQLException {
        String query = "INSERT INTO Student (Name, Department, Marks) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getDepartment());
            pstmt.setInt(3, student.getMarks());
            pstmt.executeUpdate();
            System.out.println(" Student Added Successfully!");
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM Student";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("StudentID"),
                        rs.getString("Name"),
                        rs.getString("Department"),
                        rs.getInt("Marks")
                ));
            }
        }
        return students;
    }

    public void updateStudentMarks(int studentID, int newMarks) throws SQLException {
        String query = "UPDATE Student SET Marks = ? WHERE StudentID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, newMarks);
            pstmt.setInt(2, studentID);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println(" Marks Updated Successfully!");
            else System.out.println(" Student Not Found!");
        }
    }

    public void deleteStudent(int studentID) throws SQLException {
        String query = "DELETE FROM Student WHERE StudentID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, studentID);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println(" Student Deleted Successfully!");
            else System.out.println(" Student Not Found!");
        }
    }
}


#mysql
CREATE DATABASE StudentDB;
USE StudentDB;

CREATE TABLE Student (
    StudentID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50),
    Department VARCHAR(50),
    Marks INT
);

