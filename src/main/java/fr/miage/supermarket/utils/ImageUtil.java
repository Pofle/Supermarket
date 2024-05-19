package fr.miage.supermarket.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;


/**
 * Utilitaire servant à la gestion des opérations effectuées dans le cadre de la gestion images
 * @author EricB
 */
public class ImageUtil {

	private ImageUtil() {
		throw new UnsupportedOperationException("Une classe utilitaire ne doit pas être instanciée.");
	}
	
	/**
	 * A partir d'un chemin spécifié en paramètre, renvoit une chaîne de caractères représentant l'image sous la forme d'une base64
	 * 
	 * @param imagePath le chemin de l'image
	 * @param fullPath le chemin complet de l'image (contexte serveur compris)
	 * @return la chaîne de caractère représentant la base64
	 * @throws FileNotFoundException si l'image n'est pas trouvée, exception levée
	 * @throws IOException si une erreur d'I/O intervient, exception levée
	 * @author EricB
	 */
	public static String writeImageToBase64(String imagePath, String fullPath) throws FileNotFoundException, IOException {
		File image = checkIfImageExist(imagePath, fullPath);
		String mimeType = getMimeType(image);
		
		
		byte[] imageBytes;
	    try (FileInputStream fis = new FileInputStream(image)) {
	        imageBytes = new byte[(int) image.length()];
	        fis.read(imageBytes);
	    }

	    return "data:"+mimeType+";base64," + Base64.getEncoder().encodeToString(imageBytes);
	}
	
	/**
	 * Vérifie si une image existe bien au répertoire spécifié en paramètre
	 * 
	 * @param imagePath le chemin de l'image
	 * @param fullPath le chemin complet de l'image
	 * @return le {@link File } correspondant à l'image
	 * @throws FileNotFoundException exception levée si l'image n'a pas été trouvée
	 * @author EricB
	 */
	public static File checkIfImageExist(String imagePath, String fullPath) throws FileNotFoundException {
		if (imagePath == null || imagePath.isEmpty()) {
            throw new FileNotFoundException("Il n'y a pas de chemin d'image pour ce produit");
        }
        
        File imageFile = new File(fullPath);
        if (!imageFile.exists()) {
            throw new FileNotFoundException("L'image n'a pas été trouvée");
        }
        return imageFile;
	}
	
	/**
	 * Renvoit sous forme d'une chaîne de caractères, le type mime de l'image passée en paramètre
	 * @param imageFile le {@link File} correspondant à l'image
	 * @return le type mime sous forme d'une chaîne de caractères
	 * @throws IOException exception levée si le type mime n'est pas supporté.
	 */
	public static String getMimeType(File imageFile) throws IOException {
		String mimeType = Files.probeContentType(imageFile.toPath());
        if(mimeType == null) {
        	throw new IOException("Ce type d'image n'est pas supporté");
        }
        return mimeType;
	}
}
