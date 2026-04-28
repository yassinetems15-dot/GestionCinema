package Cine;

import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();
            System.out.println("Connexion réussie à la base cinema_db !");
            conn.close();
        } catch (Exception e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
    }
}