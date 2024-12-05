//package org.example;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
//
//public class login {
//    if (userlogin){
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://cis3270project.mysql.database.azure.com:3306/project3270", "username", "password123$");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("Select * from flights where username = " + username);
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString("flight number") + " " + resultSet.getString("departure city") + " " + resultSet.getString("arrival city") + " " + resultSet.getString("departure date") + " " + resultSet.getString("arrival date") + " " + resultSet.getString("departure time") + " " + resultSet.getString("arrival time") + " " + resultSet.getString("ticket ID") + " " + resultSet.getString("ticket price"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//
//
//    }
//}

