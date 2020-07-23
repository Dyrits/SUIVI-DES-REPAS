package fr.eni.javaee.suividesrepas.messages;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class Reader {
    private static ResourceBundle resourceBundle;
    private static final String FILENAME = "fr.eni.javaee.suividesrepas.messages.messages";

    static {
        try { resourceBundle = ResourceBundle.getBundle(FILENAME, Locale.FRANCE); }
        catch (Exception exception) { exception.printStackTrace(); }
    }

    public static  String getMessageErreur(int code) {
        String message;
        try {
            if( resourceBundle != null) { message = resourceBundle.getString(String.valueOf(code)); }
            else { message = "Problème à la lecture du fichier contenant les messages."; }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            message = "Une erreur inconnue est survenue.";
        }
        return message;
    }
}