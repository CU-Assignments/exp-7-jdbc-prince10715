import java.sql.*;
import java.util.Scanner;

public class ProductManager {
    static final String URL = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql5768109";
    static final String USER = "sql5768109";
    static final String PASSWORD = "RkP3iffeUE";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n1. Insert Product\n2. View Products\n3. Update Product\n4. Delete Product\n5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1: insertProduct(con, scanner); break;
                    case 2: viewProducts(con); break;
                    case 3: updateProduct(con, scanner); break;
                    case 4: deleteProduct(con, scanner); break;
                    case 5: return;
                    default: System.out.println("Invalid choice!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void insertProduct(Connection con, Scanner scanner) throws SQLException {
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        String query = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
            System.out.println("Product Added Successfully!");
        }
    }

    static void viewProducts(Connection con) throws SQLException {
        String query = "SELECT * FROM Product";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("ProductID | Name | Price | Quantity");
            while (rs.next()) {
                System.out.println(rs.getInt("ProductID") + " | " + rs.getString("ProductName") + " | " +
                                   rs.getDouble("Price") + " | " + rs.getInt("Quantity"));
            }
        }
    }

    static void updateProduct(Connection con, Scanner scanner) throws SQLException {
        System.out.print("Enter ProductID to update: ");
        int id = scanner.nextInt();
        System.out.print("Enter New Price: ");
        double price = scanner.nextDouble();

        String query = "UPDATE Product SET Price = ? WHERE ProductID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setDouble(1, price);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Product Updated Successfully!");
        }
    }

    static void deleteProduct(Connection con, Scanner scanner) throws SQLException {
        System.out.print("Enter ProductID to delete: ");
        int id = scanner.nextInt();

        String query = "DELETE FROM Product WHERE ProductID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Product Deleted Successfully!");
        }
    }
}





### sql
CREATE TABLE Product (
    ProductID INT PRIMARY KEY AUTO_INCREMENT,
    ProductName VARCHAR(50),
    Price DECIMAL(10,2),
    Quantity INT
);

