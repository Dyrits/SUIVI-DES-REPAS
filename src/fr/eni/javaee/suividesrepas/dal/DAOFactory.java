package fr.eni.javaee.suividesrepas.dal;

import fr.eni.javaee.suividesrepas.bo.Repas;
import fr.eni.javaee.suividesrepas.dal.jdbc.DAORepasJDBC;

public class DAOFactory {
    public static DAO<Repas> getRepasDAO()  {
        return new DAORepasJDBC();
    }
}