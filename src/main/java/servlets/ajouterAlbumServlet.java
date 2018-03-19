package servlets;

import entities.Album;
import manager.FichiersBibliotheque;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/administrateur/ajouterAlbum")
public class ajouterAlbumServlet extends AbstractGenericServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomAlbum = null;

        try {
            nomAlbum = request.getParameter("nom-album");

        } catch (NumberFormatException ignored) {

        }

        try {
            FichiersBibliotheque.getInstance().addAlbum(nomAlbum);

            response.sendRedirect("home");
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();

            request.getSession().setAttribute("errorMessage", errorMessage);

            response.sendRedirect("home");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext context = new WebContext(request, response, request.getServletContext());

        TemplateEngine templateEngine = this.createTemplateEngine(request);
        templateEngine.process("ajouterAlbum", context, response.getWriter());
    }
}
