package fr.miage.supermarket.controlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RecupererImage
 */
public class RecupererImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecupererImage() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imagePath = request.getParameter("cheminImage");
        if (imagePath == null || imagePath.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Il n'y a pas de chemin d'image pour ce produit");
            return;
        }
        
        File imageFile = new File(request.getServletContext().getRealPath("WEB-INF/images/"+java.net.URLDecoder.decode(imagePath, "UTF-8")));
        if (!imageFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "L'image n'a pas été trouvée");
            return;
        }

        response.setContentType("image/jpeg");
        try (FileInputStream fis = new FileInputStream(imageFile);
             OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
	}
}
