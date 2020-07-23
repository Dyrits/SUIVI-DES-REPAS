package fr.eni.javaee.suividesrepas.servlets;

import fr.eni.javaee.suividesrepas.bll.RepasManager;
import fr.eni.javaee.suividesrepas.RepasException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/ServletAddRepas")
public class ServletAddRepas extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDate date = null;
        LocalTime heure = null;
        String aliments;
        List<Integer> erreurs = new ArrayList<>(); // Liste stockant les erreurs survenant au cours de l'execution.

        // Date:
        try {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = LocalDate.parse(request.getParameter("date"), dateFormat);
        } catch (DateTimeParseException exception) {
            exception.printStackTrace();
            erreurs.add(CodeExceptionsServlet.CK_DATE_ERROR);
        }

        // Heure:
        try {
            DateTimeFormatter heureFormat = DateTimeFormatter.ofPattern("HH:mm");
            heure = LocalTime.parse(request.getParameter("heure"), heureFormat);
        } catch (DateTimeParseException exception) {
            exception.printStackTrace();
            erreurs.add(CodeExceptionsServlet.CK_HEURE_ERROR);
        }

        // Aliments:
        aliments = request.getParameter("aliments");
        if (aliments == null || aliments.trim().isEmpty()) { erreurs.add(CodeExceptionsServlet.CK_ALIMENTS_ERROR); }

        RequestDispatcher requestDispatcher;
        if(!erreurs.isEmpty()) {  // Si la liste n'est pas vide, les erreurs sont envoyées.
            request.setAttribute("erreurs", erreurs);
            request.getRequestDispatcher("/WEB-INF/pages/add.jsp").forward(request, response);
        } else {
            // Si la liste d'erreur est vide, le repas est ajouté à la base de données et affiché.
            RepasManager repasManager;
            try {
                repasManager = new RepasManager();
                assert aliments != null; // Inutile.
                List<String> alimentsList = new ArrayList<>(Arrays.asList(aliments.split(", ")));
                repasManager.addRepas(date, heure, alimentsList);
                request.getRequestDispatcher("/ServletDisplayRepas").forward(request, response);
            } catch (RepasException exception) {
                exception.printStackTrace();
                request.setAttribute("erreurs", exception.getCodes());
                request.getRequestDispatcher("/WEB-INF/pages/add.jsp").forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/add.jsp");
        requestDispatcher.forward(request, response);
    }
}
