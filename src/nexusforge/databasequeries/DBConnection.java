/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nexusforge.databasequeries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/employee_management";

    private static final String USER = "root";

    private static final String PASSWORD = "NoStOnIr";

    public static Connection connect() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Database Connected Successfully");

            return conn;

        } catch (ClassNotFoundException | SQLException e) {

            System.out.println("Connection Failed");
            e.printStackTrace();

            return null;
        }
    }
}
