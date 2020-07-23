package fr.eni.javaee.suividesrepas.dal;

import fr.eni.javaee.suividesrepas.RepasException;

import java.time.LocalDate;
import java.util.List;

public interface DAO<T> {

    public List<T> selectByDate(LocalDate Date) throws RepasException;

    public List<T> selectAll() throws RepasException;

    public void insert(T object) throws RepasException;

    public void delete(int identifiant) throws RepasException;
}