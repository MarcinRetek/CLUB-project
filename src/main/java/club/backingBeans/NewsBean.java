package club.backingBeans;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import club.DAO.News;
import club.DAO.Post;
import club.EJB.interfaces.LocalNews;
import club.backingBeans.user.LoginUserBean;


@Named(value="newsBean")
@RequestScoped
public class NewsBean extends PostBean<News> {

	private int selectedNewsId;
	private News selectedNews;
	
	
	@Inject @Named("loginUserBean")
	private LoginUserBean loginUserBean;
	
	@EJB
	private LocalNews newsEJB;
	
	public NewsBean() {
		System.out.println("Creating new NewsBean");
	}
	
	@PostConstruct
	public void init() {
		super.redirectIfNotLoggedIn(loginUserBean);
		super.setAuthor(loginUserBean.getUser());
	}
	
	
	public String create(){ // TODO: naming standard
		System.out.println("creating news..");

		Post savedNews = super.save();
		System.out.println("saved news is: " + savedNews);
		
		if(savedNews != null) {
			System.out.println("Saved news");
			return "post-details.xhtml?faces-redirect=true&id=" + savedNews.getId();
		} else {
			System.out.println("Failed to save news");
			return "";
		}
	}
	

	public String submit(){ // TODO: rename
		System.out.println("inne i update news " + super.getTitle());
		
		Post savedNews = super.update();
			if(savedNews != null){
				return "post-details.xhtml?faces-redirect=true&id=" + savedNews.getId();
			}
		return "";
	} 
	
	public String deleteNews(){

		News newsToUpdate = newsEJB.getById(selectedNewsId);
		newsToUpdate.setHidden(true);

		if(newsEJB.save(newsToUpdate) != null){
			return "news-list.xhtml";
		}
		return ""; //TODO: do error handler
	}
		

	public void useSelectedNews(){
		selectedNews = newsEJB.getById(selectedNewsId);
		System.out.println("getting news with title: " + selectedNews.getTitle());
	}

	
	public int getSelectedNewsId() {
		return selectedNewsId;
	}

	public void setSelectedNewsId(int selectedNewsId) {
		System.out.println("Setting selectedNews to: " + selectedNewsId);
		this.selectedNewsId = selectedNewsId;
	}

	public List<News> getAll(){
		return newsEJB.getAll();
	}

	public News getSelectedNews() {
		return selectedNews;
	}

	public void setSelectedNews(News selectedNews) {
		this.selectedNews = selectedNews;
	}
	
	public Integer getCommentLimit() {
		return null;
	}

	@Override
	public News getFromFields() {
		News news = new News();
		super.getBasicPostEntityFromFields(news);
		return news;
	}

	@Override
	public News updateFromFields() {
		News newsToUpdate = newsEJB.getById(selectedNewsId);
		newsToUpdate.setTitle(super.getTitle());
		newsToUpdate.setText(super.getText());
		return newsToUpdate;
	}
	
}
