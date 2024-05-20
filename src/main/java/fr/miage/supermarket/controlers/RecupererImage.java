package fr.miage.supermarket.controlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.utils.ImageUtil;

/**
 * Servlet de gestion de la récupération des images contenues sur le serveur.
 * 
 * @author EricB
 */
public class RecupererImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RecupererImage() {
		super();
	}

	/**
	 * Méthode GET permettant de récupérer sous forme d'un {@link OutputStream} les
	 * images contenues sur le serveur. </br>
	 * Paramètre cheminImage obligatoire, menant au chemin de l'image sur le serveur
	 * à partir du dossier WEB-INF/images.
	 * 
	 * @see {@link HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)};
	 * 
	 * @author EricB
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String imagePath = request.getParameter("cheminImage");
		try {
			File image = ImageUtil.checkIfImageExist(imagePath, request.getServletContext()
					.getRealPath("WEB-INF/images/" + java.net.URLDecoder.decode(imagePath, "UTF-8")));
			String mimeType = ImageUtil.getMimeType(image);

			response.setContentType(mimeType);
			response.setContentLengthLong(image.length());

			try (FileInputStream fis = new FileInputStream(image); OutputStream os = response.getOutputStream()) {
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = fis.read(buffer)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}

	}
}
