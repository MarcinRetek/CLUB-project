package club.backingBeans.user;

import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import club.DAO.User;
import club.EJB.interfaces.LocalUser;

@Named(value="registerUser")
@RequestScoped
@Startup
public class RegisterUserBean {
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private boolean admin;
	private boolean approved;
	
	@EJB
	private LocalUser userEJB;
	
	public String saveUser() {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		user.setAdmin(admin);
		user.setApproved(approved);
		
		if (userEJB.saveUser(user)) {
			this.firstName = null;
			this.lastName = null;
			this.email = null;
			this.password = null;
			return "wait-for-approve-index";		

		}
		return "index"; //TODO: make sure this is the right way to 'redirect' to same page		
	}
	
	
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}