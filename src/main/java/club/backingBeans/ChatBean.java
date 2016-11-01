package club.backingBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import club.DAO.user.User;
import club.EJB.interfaces.LocalUser;

@Named(value="chatBean")
public class ChatBean {

	@EJB
	private LocalUser userEJB;	

	private Integer chatWithId;
	
	@PostConstruct
	public void init() {
		
		chatWithId = null;
		
		Map<String, String> params =  FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRequestParameterMap();
		if(params.get("id")!=null)
			chatWithId = Integer.parseInt(params.get("id"));
		
	}
	public User getChatWith() {
		if(chatWithId==null) return null;
		return userEJB.getById(chatWithId);
	}

	public String getChatRoom() {
		if(chatWithId!=null) return null;
		return "main";
	}
	
	public List<String> getLinks() {
		//TODO: do a list of chat links (etc users and rooms)
		List<String> links = new ArrayList<>();
		links.add("test1");
		links.add("test2");
		return links;
	}

}
