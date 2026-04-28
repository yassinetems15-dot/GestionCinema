package Cine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SalleDAO {

    public void ajouterSalle(Salle salle) {
        String sql = "INSERT INTO salle (numero, nom, capacite, type_salle, prix) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, salle.getNumero());
            ps.setString(2, salle.getNom());
            ps.setInt(3, salle.getCapacite());

            if (salle instanceof SalleVIP) {
                ps.setString(4, "VIP");
            } else {
                ps.setString(4, "Normale");
            }

            ps.setDouble(5, salle.getPrix());

            ps.executeUpdate();
            System.out.println("Salle ajoutée dans la base de données.");

        } catch (Exception e) {
            System.out.println("Erreur ajout salle : " + e.getMessage());
        }
    }

    public List<Salle> listerSalles() {
        List<Salle> salles = new ArrayList<>();

        String sql = "SELECT * FROM salle";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int numero = rs.getInt("numero");
                String nom = rs.getString("nom");
                int capacite = rs.getInt("capacite");
                String type = rs.getString("type_salle");

                Salle salle;

                if (type.equalsIgnoreCase("VIP")) {
                    salle = new SalleVIP(numero, nom, capacite);
                } else {
                    salle = new SalleNormale(numero, nom, capacite);
                }

                salles.add(salle);
            }

        } catch (Exception e) {
            System.out.println("Erreur liste salles : " + e.getMessage());
        }

        return salles;
    }

    public Salle chercherSalleParNumero(int numeroRecherche) {
        String sql = "SELECT * FROM salle WHERE numero = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, numeroRecherche);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int numero = rs.getInt("numero");
                String nom = rs.getString("nom");
                int capacite = rs.getInt("capacite");
                String type = rs.getString("type_salle");

                if (type.equalsIgnoreCase("VIP")) {
                    return new SalleVIP(numero, nom, capacite);
                } else {
                    return new SalleNormale(numero, nom, capacite);
                }
            }

        } catch (Exception e) {
            System.out.println("Erreur recherche salle : " + e.getMessage());
        }

        return null;
    }
}