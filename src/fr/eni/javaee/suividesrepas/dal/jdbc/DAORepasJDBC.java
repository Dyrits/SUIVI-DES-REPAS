package fr.eni.javaee.suividesrepas.dal.jdbc;

import fr.eni.javaee.suividesrepas.bo.Aliment;
import fr.eni.javaee.suividesrepas.bo.Repas;
import fr.eni.javaee.suividesrepas.dal.CodesExceptionDAL;
import fr.eni.javaee.suividesrepas.RepasException;
import fr.eni.javaee.suividesrepas.dal.DAO;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DAORepasJDBC implements DAO<Repas> {
    private static final String SELECT_BY_DATE =
            "SELECT REPAS.id, date_repas, heure_repas, ALIMENTS.id, nom " +
                    "FROM REPAS " +
                    "INNER JOIN ALIMENTS ON REPAS.id = ALIMENTS.id " +
                    "WHERE date_repas = ? " +
                    "ORDER BY REPAS.heure_repas DESC";
    private static final String SELECT_ALL =
            "SELECT REPAS.id, date_repas, heure_repas, ALIMENTS.id, nom " +
                    "FROM REPAS " +
                    "INNER JOIN ALIMENTS ON REPAS.id = ALIMENTS.id " +
                    "ORDER BY REPAS.date_repas DESC, REPAS.heure_repas DESC";
    private static final String INSERT_REPAS =
            "INSERT INTO REPAS(date_repas, heure_repas) " +
                    "VALUES(?, ?)";
    private static final String INSERT_ALIMENTS =
            "INSERT INTO ALIMENTS(nom, id_repas) " +
                    "VALUES(?, ?)";


    /**
     * Retourne la liste des repas pour la date spécifiée en paramètre.
     * @param date LocalDate | Date
     * @return ArrayList | Liste des repas pour la date spécifiée.
     * @throws RepasException Exception.
     */
    @Override
    public List<Repas> selectByDate(LocalDate date) throws RepasException {
        return selectAllBy(SELECT_BY_DATE, date);
    }

    /**
     * Retourne la liste complète des repas.
     * @return ArrayList | Liste des repas.
     * @throws RepasException Exception.
     */
    @Override
    public List<Repas> selectAll() throws RepasException {
        return selectAllBy(SELECT_ALL, null);
    }

    /**
     * Retourne la liste des repas selon une requête avec une condition unique (ou non).
     * Permet de mutualiser les méthodes selectAll() et selectByDate();
     */
    public <T> List<Repas> selectAllBy(String query, T queryParameter) throws RepasException {
        List<Repas> listeRepas = new ArrayList<>();
        try (Connection connection = JDBC.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            if (queryParameter instanceof LocalDate) { statement.setDate(1, Date.valueOf((LocalDate) queryParameter)); };
            // D'autres conditions peuvent être ajoutées selon le type d'objet possible entrée en paramètre.
            ResultSet resultSet = statement.executeQuery();
            Repas repas = null;
            Aliment aliment = null;
            while(resultSet.next()) {
                int identifiant = resultSet.getInt(1);
                if (repas == null || identifiant != repas.getIdentifiant()) {
                    if (repas != null) { listeRepas.add(repas); }
                    repas = getRepas(resultSet, identifiant);
                }
                aliment = getAliment(resultSet);
                repas.getAliments().add(aliment);
            }
            resultSet.close();
            statement.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RepasException(CodesExceptionDAL.SELECT_FAIL);
        }
        return listeRepas;
    }

    @Override
    public void insert(Repas repas) throws RepasException {
        if (repas == null) { throw new RepasException(CodesExceptionDAL.INSERT_OBJECT_IS_NULL); }
        try (Connection connection = JDBC.getConnection()) {
            // Repas:
            PreparedStatement statement = connection.prepareStatement(INSERT_REPAS, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(repas.getDate()));
            statement.setTime(2, Time.valueOf(repas.getHeure()));
            int rows = statement.executeUpdate();
            if (rows == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) { repas.setIdentifiant(resultSet.getInt(1)); }
                resultSet.close();
            }
            statement.close();
            // Aliments:
            statement = connection.prepareStatement(INSERT_ALIMENTS, PreparedStatement.RETURN_GENERATED_KEYS);
            for (Aliment aliment : repas.getAliments()) {
                statement.setString(1, aliment.getNom());
                statement.setInt(2, repas.getIdentifiant());
                rows = statement.executeUpdate();
                if (rows == 1) {
                    ResultSet resultSet = statement.getGeneratedKeys();
                    if (resultSet.next()) { aliment.setIdentifiant(resultSet.getInt(1)); }
                    resultSet.close();
                }
            }
            statement.close();
            connection.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RepasException(CodesExceptionDAL.INSERT_OBJECT_FAIL);
        }
    }

    @Override
    public void delete(int identifiant) throws RepasException {
    }

    /**
     * Génère une nouvelle instance de la classe Repas à partir d'un set de résultat.
     * @param resultSet ResultSet | Set de résultat source.
     * @param identifiant int | Attribut "identifiant" de l'instance de l'objet.
     * @return Repas | Instance de la classe Repas.
     * @throws SQLException Exception.
     */
    public Repas getRepas(ResultSet resultSet, int identifiant) throws SQLException {
        LocalDate date = resultSet.getDate("date_repas").toLocalDate();
        LocalTime heure = resultSet.getTime("heure_repas").toLocalTime();
        return new Repas(identifiant, date, heure);
    }

    public Repas getRepas(ResultSet resultSet) throws SQLException {
        int identifiant = resultSet.getInt(1);
        return getRepas(resultSet, identifiant);
    }

    /**
     * Génère une nouvelle instance de la classe Aliment à partir d'un set de résultat.
     * @param resultSet ResultSet | Set de résultat source.
     * @return Aliment | Instance de la classe Aliment.
     * @throws SQLException Exception.
     */
    public Aliment getAliment(ResultSet resultSet) throws SQLException {
        int identifiant = resultSet.getInt(1);
        String nom = resultSet.getString("nom");
        return new Aliment(identifiant, nom);
    }
}
