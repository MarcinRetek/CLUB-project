package club.EJB;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import club.DAO.News;
import club.DAO.NewsDao;
import club.DAO.Post;
import club.DAO.PostDAO;
import club.EJB.interfaces.LocalNews;
import club.EJB.interfaces.LocalPlatform;
import club.EJB.interfaces.LocalPost;


@Stateless
public class PostEJB implements LocalPost {
	
	@EJB
	PostDAO postDao;
	
	@Override
	public Post save(Post entity) {
		return postDao.save(entity);
	}

	@Override
	public boolean delete(int id) {
		return postDao.delete(id);
	}

	@Override
	public List<Post> getAll() {
		return postDao.getAll();
	}

	@Override
	public Post getById(int id) {
		return postDao.getById(id);
	}

}