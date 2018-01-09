package org.study.rest.jdbc;


import org.study.rest.Product;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static javax.swing.UIManager.getString;

public class JDBCExample {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String dburl = "jdbc:mysql://localhost:3306/web1";
    private static final String dbuser = "root";
    private static final String dbpass = "";
    static Connection con;
    static Statement stmt;


    public static boolean insert(Product product) {
        try {
            Class.forName(JDBC_DRIVER);

            //Step 1 : Connecting to server and database
            con = DriverManager.getConnection(dburl, dbuser, dbpass);

            //Step 2 : Initialize Statement
            stmt = con.createStatement();

            //Step 2.1 :  Run Query In initial ResultSet size
            int initialSize = getInitialRecordsCount(stmt);

            //Step 3 : SQL Query
            String insertQuery = "INSERT INTO product (name,qty) VALUES('" + product.getName() + "','" + product.getQty() + "')";

            //Step 4 : Run Query
            stmt.executeUpdate(insertQuery);

            //Step 4.1 : Run Query In ResultSet after Deleting
            String selectQuery = "SELECT * FROM product";
            ResultSet rset = stmt.executeQuery(selectQuery);

            if (initialSize == transformToList(rset).size()) {
                System.out.println("No element inserted");
                return false;
            }
            System.out.println("Element " + product + " was successfully inserted");
            return true;
        } catch (SQLException e) {
            System.err.println("Cannot connect ! ");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Missing driver ! ");
            e.printStackTrace();
        } finally {
            System.out.println("Closing the connection.");
            if (con != null) try {
                con.close();
            } catch (SQLException ignore) {
            }
        }

        return false;
    }

    public static List<Product> select() {
        try {
            Class.forName(JDBC_DRIVER);

            //Step 1 : Connecting to server and database
            con = DriverManager.getConnection(dburl, dbuser, dbpass);

            //Step 2 : Initialize Statement
            stmt = con.createStatement();

            //Step 3 : SQL Query
            String query = "SELECT * FROM product";

            List<Product> products = new LinkedList<Product>();
            //Step 4 : Run Query In ResultSet
            ResultSet rset = stmt.executeQuery(query);

            System.out.println("Found in DB");

            return transformToList(rset);
        } catch (SQLException e) {
            System.err.println("Cannot connect ! ");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        } finally {
            System.out.println("Closing the connection.");
            if (con != null) try {
                con.close();
            } catch (SQLException ignore) {
            }
        }
        return null;
    }

    private static int getInitialRecordsCount(Statement stmt) throws SQLException {
        String selectQuery = "SELECT * FROM product";
        ResultSet rsetInitial = stmt.executeQuery(selectQuery);
        rsetInitial.last();
        return rsetInitial.getRow();
    }

    public static boolean delete(Product product) {

        try {
            Class.forName(JDBC_DRIVER);

            //Step 1 : Connecting to server and database
            con = DriverManager.getConnection(dburl, dbuser, dbpass);

            //Step 2 : Initialize Statement
            stmt = con.createStatement();

            //Step 3 : SQL Query
            String deleteQuery = "DELETE FROM product WHERE name='" + product.getName() + "'";

            //Step 3.1 : Run Query In initial ResultSet size
            int initialSize = getInitialRecordsCount(stmt);

            //Step 4 : Run Query
            stmt.executeUpdate(deleteQuery);

            //Step 4 : Run Query In ResultSet after Deleting
            String selectQuery = "SELECT * FROM product";
            ResultSet rset = stmt.executeQuery(selectQuery);

            if (initialSize == transformToList(rset).size()) {
                System.out.println("No element deleted");
                return false;
            }
            System.out.println("Element " + product + " was successfully deleted");
            return true;

        } catch (SQLException e) {
            System.err.println("Cannot connect ! ");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        } finally {
            System.out.println("Closing the connection.");
            if (con != null) try {
                con.close();
            } catch (SQLException ignore) {
            }
        }
        return false;
    }

    public static boolean update(Product product) {

        try {
            Class.forName(JDBC_DRIVER);

            //Step 1 : Connecting to server and database
            con = DriverManager.getConnection(dburl, dbuser, dbpass);

            //Step 2 : Initialize Statement
            stmt = con.createStatement();

            //Step 3 : SQL Query
            String name = product.getName();
            int qty = product.getQty();
            String updatequery = "UPDATE product SET name='" + name + "',qty='" + qty + "' WHERE name='" + name + "'";

            //Step 4 : Run Query
            stmt.executeUpdate(updatequery);
            System.out.println("Table Updated Successfully");

            System.out.println("******** Records Are *********");
            String updateQuerry = "SELECT * FROM product";

            //Step 4 : Run Query In ResultSet
            ResultSet rset = stmt.executeQuery(updateQuerry);
            while (rset.next()) {
                System.out.print("Name: " + rset.getString(1));
                System.out.println(" Qty : " + rset.getInt(2));
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Cannot connect ! ");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        } finally {
            System.out.println("Closing the connection.");
            if (con != null) try {
                con.close();
            } catch (SQLException ignore) {
            }
        }
        return false;
    }


    private static List<Product> transformToList(ResultSet rset) throws SQLException {
        List<Product> products = new LinkedList<Product>();
        System.out.println("******** Records Are *********");
        while (rset.next()) {
            Product product = new Product();
            product.setName(rset.getString(1));
            product.setQty(rset.getInt(2));

            System.out.println(product);
            products.add(product);
        }
        System.out.println();
        return products;
    }

}