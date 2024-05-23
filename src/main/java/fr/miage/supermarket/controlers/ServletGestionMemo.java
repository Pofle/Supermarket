package fr.miage.supermarket.controlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.miage.supermarket.dao.LinkListeProduitDAO;
import fr.miage.supermarket.dao.MemoDAO;
import fr.miage.supermarket.models.Memo;

/**
 * Servlet implementation class ServletGestionMemo
 */
public class ServletGestionMemo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletGestionMemo() {
        super();
        // TODO Auto-generated constructor stub
    }



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // Récupérer le libellé du mémo et l'ID de la liste de courses
	    String libelle = request.getParameter("newLibelle");
	    int listeId = Integer.parseInt(request.getParameter("listeId"));
	    System.out.println("Egalement for add memos : " + libelle + "id liste"+ listeId);

	    try {
	        // Ajouter le nouveau mémo à la base de données
	        MemoDAO.ajouterMemo(libelle, listeId);
	        response.setStatus(HttpServletResponse.SC_OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	    //response.sendRedirect("central?type_action=gestion_List");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionType = request.getParameter("type_action");
	    if ("delete_memo".equals(actionType)) {
	        String memoIdStr = request.getParameter("memoId");
	        String listeIdStr = request.getParameter("listeId");
	        Integer memoId = null;
	        Integer listeId = null;
	        try {
	            memoId = Integer.parseInt(memoIdStr);
	            listeId = Integer.parseInt(listeIdStr);
	        } catch (NumberFormatException e) {
	            System.out.println("Erreur lors de la conversion des IDs : " + e.getMessage());
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            return;
	        }

	        if (memoId != null && listeId != null) {
	            try {
	                MemoDAO.supprimerMemo(memoId, listeId);
	                response.setStatus(HttpServletResponse.SC_OK);
	            } catch (Exception e) {
	                e.printStackTrace();
	                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            }
	        } else {
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        }
	    } else {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    }
	}

}
