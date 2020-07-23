package fr.eni.javaee.suividesrepas.servlets;

import fr.eni.javaee.suividesrepas.bll.RepasManager;
import fr.eni.javaee.suividesrepas.bo.Repas;
import fr.eni.javaee.suividesrepas.RepasException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ServletDisplayRepas")
public class ServletDisplayRepas extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDate dateSelected = null;
        List<Integer> erreurs = new ArrayList<>(); // Liste stockant les erreurs survenant au cours de l'execution.

        // Date:
        if(request.getParameter("dateSelected") != null && !request.getParameter("dateSelected").isEmpty()) {
            try {
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dateSelected = LocalDate.parse(request.getParameter("dateSelected"), dateFormat);
            } catch(DateTimeParseException exception) {
                exception.printStackTrace();
                erreurs.add(CodeExceptionsServlet.CK_DATE_ERROR);
            }
        }

        // Si la liste n'est pas vide, les erreurs sont envoyées.
        if(!erreurs.isEmpty()) { request.setAttribute("erreurs", erreurs); }
        else {
            try {
                // Si la liste d'erreur est vide, les repas sont affichés.
                RepasManager repasManager = new RepasManager();
                List<Repas> repas = null;
                repas = dateSelected == null ? repasManager.getRepas() : repasManager.getRepas(dateSelected);
                request.setAttribute("repas", repas);
            } catch (RepasException exception) {
                exception.printStackTrace();
                request.setAttribute("erreurs", exception.getCodes());
            }
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/display.jsp");
        requestDispatcher.forward(request, response);
    }
}
