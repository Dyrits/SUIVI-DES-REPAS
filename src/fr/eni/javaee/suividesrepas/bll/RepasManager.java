package fr.eni.javaee.suividesrepas.bll;

import fr.eni.javaee.suividesrepas.bo.Repas;
import fr.eni.javaee.suividesrepas.RepasException;
import fr.eni.javaee.suividesrepas.bo.Aliment;
import fr.eni.javaee.suividesrepas.dal.DAO;
import fr.eni.javaee.suividesrepas.dal.DAOFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RepasManager {
    private static DAO daoRepas;

    /**
     * Constructeur initialisant une interface d'accès aux repas.
     * @throws RepasException Exception
     */
    public RepasManager() throws RepasException { daoRepas = DAOFactory.getRepasDAO(); }

    /**
     * @return List | Liste de l'ensemble des repas.
     * @throws RepasException Exception
     */
    public List<Repas> getRepas() throws RepasException {
        try { return daoRepas.selectAll(); }
        catch (RepasException exception) {
            throw new RepasException("Erreur lors de la récupération des repas.", exception);
        }
    }

    /**
     * @return List | Liste de l'ensemble des repas pour une date spécifiée.
     * @throws RepasException Exception
     */
    public List<Repas> getRepas(LocalDate date) throws RepasException {
        try { return daoRepas.selectByDate(date); }
        catch (RepasException exception) {
            throw new RepasException("Erreur lors de la récupération des repas pour le " + date + ".", exception);
        }
    }

    /**
     * Ajoute un repas.
     * @param date LocalDate | Date du repas.
     * @param heure LocalTime | Heure du repas.
     * @param aliments List<String> | Liste des aliments.
     * @throws RepasException Exception.
     */
    public void addRepas(LocalDate date, LocalTime heure, List<String> aliments) throws RepasException {
        Repas repas = null;
        try {
            checkRepas(date, heure, aliments);
            repas = new Repas(date, heure);
            for (String aliment: aliments) {
                repas.getAliments().add(new Aliment(aliment));
            }
            daoRepas.insert(repas);
        } catch (RepasException exception) {
            throw new RepasException("Erreur lors de l'ajout d'un nouveau repas.", exception);
        }
    }


    private void checkRepas(LocalDate date, LocalTime heure, List<String> aliments) throws RepasException {
        RepasException checkRepas = new RepasException();
        checkDateRepas(date, heure, checkRepas);
        checkAliments(aliments, checkRepas);
        if (checkRepas.hadCodes()) {throw checkRepas; }
    }

    /**
     * Vérifie la date et l'heure du repas.
     * @param date LocalDate | Date du repas.
     * @param heure LocalTime | Heure du repas.
     * @param checkRepas RepasException.
     * @throws RepasException Exception.
     */
    private void checkDateRepas(LocalDate date, LocalTime heure, RepasException checkRepas) throws RepasException {
        LocalDate now = LocalDate.now();
        if(date == null || !date.isBefore(now) || (date.isEqual(now) && !heure.isBefore(LocalTime.now()))) {
            checkRepas.addCode(CodesExceptionBLL.CK_REPAS_DATE_ERROR);
        }
    }

    /**
     * Vérifie la liste des aliments.
     * @param aliments List<String> | Liste des aliments.
     * @param checkRepas RepasException.
     * @throws RepasException Exception.
     */
    private void checkAliments(List<String> aliments, RepasException checkRepas) throws RepasException {
        if ( aliments == null || aliments.isEmpty()) {
            throw new RepasException(CodesExceptionBLL.CK_ALIMENT_NOM_ERROR);
        } else {
            for (String aliment : aliments) {
                if (aliment.length() > 50) {
                    checkRepas.addCode(CodesExceptionBLL.CK_ALIMENT_NOM_ERROR);
                    break;
                }
            }
        }

    }

}
