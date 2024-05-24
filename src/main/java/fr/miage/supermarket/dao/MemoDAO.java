/**
 * 
 */
package fr.miage.supermarket.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import fr.miage.supermarket.models.Memo;
import fr.miage.supermarket.models.Produit;
import fr.miage.supermarket.models.ShoppingList;
import fr.miage.supermarket.utils.HibernateUtil;

/**
 * 
 */
public class MemoDAO {

	private SessionFactory sessionFactory;

	public MemoDAO() {
		this.sessionFactory = HibernateUtil.getSessionAnnotationFactory();
	}

	public static List<Memo> getMemos(List<ShoppingList> shoppingLists) {
		Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
		Transaction tx = null;
		List<Memo> memoList = null;

		try {
			tx = session.beginTransaction();
			List<Integer> shoppingListIds = shoppingLists.stream().map(ShoppingList::getId)
					.collect(Collectors.toList());
			memoList = session.createQuery("SELECT m FROM Memo m JOIN m.shoppingList s WHERE s.id IN :shoppingListIds",
					Memo.class).setParameter("shoppingListIds", shoppingListIds).list();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
		return memoList;
	}

	public static List<Memo> getMemoByListId(int listeId) throws Exception {
		Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
		Transaction tx = null;
		List<Memo> memoContent = null;

		try {
			tx = session.beginTransaction();
			Query<Memo> query = session.createQuery("FROM Memo where shoppingList.id = :listeId", Memo.class);
			query.setParameter("listeId", listeId);
			memoContent = query.list();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
		return memoContent;
	}

	public static void ajouterMemo(String libelle, int listeId) {
		Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Memo memo = new Memo();
			memo.setLibelle(libelle);

			ShoppingList shoppingList = session.get(ShoppingList.class, listeId);
			memo.setShoppingList(shoppingList);

			session.persist(memo);
			tx.commit();
			System.out.println("Mémo ajouté avec succès.");
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public static void supprimerMemo(int memoId, int listeId) {
		Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			Memo memo = session.get(Memo.class, memoId);
			if (memo != null && memo.getShoppingList().getId() == listeId) {
				session.remove(memo);
			}

			tx.commit();
			System.out.println("Mémo supprimé avec succès.");
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public static List<Produit> rechercherProduitsPourMemos(List<String> memos) {
		Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Produit> query = builder.createQuery(Produit.class);
			Root<Produit> root = query.from(Produit.class);
			query.select(root);

			List<Predicate> predicates = new ArrayList<>();
			for (String memo : memos) {
				Predicate libellePredicate = builder.like(builder.lower(root.get("libelle")),
						"%" + memo.toLowerCase() + "%");
				Predicate marquePredicate = builder.like(builder.lower(root.get("marque")),
						"%" + memo.toLowerCase() + "%");
				Predicate descriptionCourtePredicate = builder.like(builder.lower(root.get("descriptionCourte")),
						"%" + memo.toLowerCase() + "%");
				Predicate descriptionPredicate = builder.like(builder.lower(root.get("description")),
						"%" + memo.toLowerCase() + "%");
				Predicate labelPredicate = builder.like(builder.lower(root.get("label")),
						"%" + memo.toLowerCase() + "%");

				predicates.add(builder.or(libellePredicate, marquePredicate, descriptionCourtePredicate,
						descriptionPredicate, labelPredicate));
			}

			Predicate finalPredicate = builder.or(predicates.toArray(new Predicate[0]));
			query.where(finalPredicate);

			return session.createQuery(query).getResultList();
		} finally {
			session.close();
		}
	}

	public static List<Produit> rechercherProduitsPourMemo(String memo) {
		Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Produit> query = builder.createQuery(Produit.class);
			Root<Produit> root = query.from(Produit.class);
			query.select(root);

			List<Predicate> predicates = new ArrayList<>();
			Predicate libellePredicate = builder.like(builder.lower(root.get("libelle")),
					"%" + memo.toLowerCase() + "%");
			Predicate marquePredicate = builder.like(builder.lower(root.get("marque")), "%" + memo.toLowerCase() + "%");
			Predicate descriptionCourtePredicate = builder.like(builder.lower(root.get("descriptionCourte")),
					"%" + memo.toLowerCase() + "%");
			Predicate descriptionPredicate = builder.like(builder.lower(root.get("description")),
					"%" + memo.toLowerCase() + "%");
			Predicate labelPredicate = builder.like(builder.lower(root.get("label")), "%" + memo.toLowerCase() + "%");

			predicates.add(builder.or(libellePredicate, marquePredicate, descriptionCourtePredicate,
					descriptionPredicate, labelPredicate));

			Predicate finalPredicate = builder.or(predicates.toArray(new Predicate[0]));
			query.where(finalPredicate);

			return session.createQuery(query).getResultList();
		} finally {
			session.close();
		}
	}

	public void deleteMemosByShoppingListId(int shoppingListId) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			session.getTransaction().begin();
			Query query = session.createQuery("DELETE FROM Memo m WHERE m.shoppingList.id = :shoppingListId");
			query.setParameter("shoppingListId", shoppingListId);
			query.executeUpdate();
			session.getTransaction().commit();
		}
	}
}
