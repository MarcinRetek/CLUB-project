package club.EJB.interfaces;

import java.util.List;

import javax.ejb.Local;
import club.DAO.User;

@Local
public interface LocalUser {
	boolean saveUser(User user);
	User getUserById(int id);
	List<User> getAll();
	boolean deleteUser(int id);
	User getUserByEmailAndPassword(String email, String password);
}
