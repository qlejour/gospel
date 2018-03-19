package servlets;

import entities.Album;
import entities.Photo;
import manager.FichiersBibliotheque;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/administrateur/ajouter-photos")
@MultipartConfig
public class ajouterPhotosServlet extends AbstractGenericServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Part picture = request.getPart("image");
        Album album = null;
        String albumId = request.getParameter("id");

        try {
            album = FichiersBibliotheque.getInstance().getAlbum(Integer.parseInt(albumId));
        } catch (NumberFormatException ignored) {

        }
        Photo newPhoto = new Photo(null,album);
        try {
            Photo createdPhoto = FichiersBibliotheque.getInstance().addPhoto(newPhoto, picture);

            response.sendRedirect("home");
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();

            request.getSession().setAttribute("errorMessage", errorMessage);

            response.sendRedirect("ajouter-photos");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebContext context = new WebContext(request, response, request.getServletContext());

        String albumId = request.getParameter("id");
        Album album = FichiersBibliotheque.getInstance().getAlbum(Integer.parseInt(albumId));
        context.setVariable("album", album);

        TemplateEngine templateEngine = this.createTemplateEngine(request);
        templateEngine.process("ajouterPhotos", context, response.getWriter());

    }
}
