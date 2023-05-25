package com.alejandro.jdbcexample;

import java.sql.*;
import java.util.Properties;

public class Main {
     static Connection conn;
    public static void main(String[] args) throws Exception {

        String url = "jdbc:postgresql://localhost:5432/SupplierDataBase";
        Properties props = new Properties();
        props.put("user", "postgres");
        props.put("password", "1234");
        props.put("client_encoding", "UTF8");

        conn = DriverManager.getConnection(url, props);

        getLastIdOfTheTable("customer");

        conn.close();
    }
    static void removerTheFirstID(String table, String campoId) throws SQLException {

        String query = "SELECT id FROM " + table + " ORDER BY id ASC limit 1";

        PreparedStatement prs = conn.prepareStatement(query);

        ResultSet resultSet = prs.executeQuery();

        resultSet.next();
        int id = resultSet.getInt(campoId);

        System.out.println(id);

        prs.close();
        resultSet.close();

        PreparedStatement delete = conn.prepareStatement("delete from " + table + " where id = ?");
        delete.setInt(1, id);

        delete.executeUpdate();

    }

    static void modifyTheFirstElementAndConvertItToUppercase() throws SQLException {

        // select id, firstname, lastname, city, country from customers order by id asc limit 1
        // update customers set firstname = FIRSTNAME, lastname = LASTNAME, city=CITY, country=COUNTRY where id = X

        String query = "SELECT id, companyname, contactname, contacttitle, city, country FROM supplier ORDER BY id ASC LIMIT 1";

        PreparedStatement selectQuery = conn.prepareStatement(query);

        ResultSet resultSet = selectQuery.executeQuery();

        resultSet.next();

        int id = resultSet.getInt("id");

        System.out.println(resultSet.getString("companyname"));
        System.out.println(resultSet.getString("contactname"));
        System.out.println(resultSet.getString("contacttitle"));
        System.out.println(resultSet.getString("city"));
        System.out.println(resultSet.getString("country"));

        DTOforSupplier supplier = new DTOforSupplier();

        supplier.companyname = resultSet.getString("companyname").toUpperCase();
        supplier.contactname = resultSet.getString("contactname").toUpperCase();
        supplier.contacttitle = resultSet.getString("contacttitle").toUpperCase();
        supplier.city = resultSet.getString("city").toUpperCase();
        supplier.country = resultSet.getString("country").toUpperCase();

        String upperOperation = "UPDATE supplier SET companyname = '" + supplier.companyname + "', contactname = '" + supplier.contactname + "', contacttitle = '"
                + supplier.contacttitle + "', city = '" + supplier.city + "', country = '" + supplier.country +
                "' WHERE id = " + id;

        PreparedStatement updateQuery = conn.prepareStatement(upperOperation);

        updateQuery.executeUpdate();

        System.out.println(resultSet.getString("companyname"));
        System.out.println(resultSet.getString("contactname"));
        System.out.println(resultSet.getString("contacttitle"));
        System.out.println(resultSet.getString("city"));
        System.out.println(resultSet.getString("country"));

        updateQuery.close();
        resultSet.close();
    }
    public static int getLastIdOfTheTable(String table) throws SQLException {
        String query = "SELECT id FROM " + table + " ORDER BY id DESC limit 1";

        PreparedStatement prs = conn.prepareStatement(query);

        ResultSet resultSet = prs.executeQuery();

        resultSet.next();
        int id = resultSet.getInt(1);

        System.out.println(id);
        resultSet.close();
        prs.close();
        return id;
    }
    static void insert() throws SQLException {

        int ultimoId = getLastIdOfTheTable("customer");

        PreparedStatement ps = conn.prepareStatement("insert into customer (id, firstname, lastname, city, country, phone) values (?,?,?,?,?,?)");
        ps.setInt(1, ++ultimoId);
        ps.setString(2,"David");
        ps.setString(3,"Hofmann");
        ps.setString(4,"Atlanta");
        ps.setString(5,"USA");
        ps.setString(6,"205616");

        int rowsAffected = ps.executeUpdate();
        // TODO imprimir
        ps.setInt(1, ++ultimoId);
        ps.setString(2,"Daniel");
        rowsAffected = ps.executeUpdate();
        // TODO imprimir

        ps.close();

    }

}
