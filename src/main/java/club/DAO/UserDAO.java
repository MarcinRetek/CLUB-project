package club.DAO;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateful
public class UserDAO {
	
	@PersistenceContext
	EntityManager entityManager;

	public boolean saveToDB(User user) {
		return entityManager.merge(user) != null;
		
	}

	public boolean updateDB(User user) {		
//		user = entityManager.find(User.class, 1);
//		entityManager.getTransaction().begin();
//		user.setEmail("karl@karl.se");
//		entityManager.getTransaction().commit();
		entityManager.persist(user);
		return true;
		
	}

	public User getUserById(int id) {
		return entityManager.find(User.class, id);
	}

	public User getUserByEmailAndPassword(String email, String password) {
		Query query = entityManager.createNamedQuery("User.findByEmailAndPassword");

		query.setParameter("email", email);
		query.setParameter("password", password);
		
		//TODO: detta är fel att använda exception för fel man faktiskt kan medvetet göra IMO. borde finnas något sätt att se om resultat finns innan man hämtar det.
		try {
			return (User)query.getSingleResult();		
		}
		catch(NoResultException e) {
			return null;
		}
	}
	
	// Skicka User till databas, hämta osv.

	public List<User> getAll() {
		Query findAll = entityManager.createNamedQuery("User.findAll");
		return (List<User>)findAll.getResultList();
	}
}
