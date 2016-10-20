package club.resource;

import static javax.ws.rs.core.Response.Status.*;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import club.DAO.post.Post;
import club.EJB.interfaces.LocalPost;

@Stateless
@Path("/posts")
public class PostResource extends BasicResource {
	
	@EJB
	LocalPost postEJB;
	@Inject
	CommentResource commentResource;
	
	@Context
	UriInfo uriInfo;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPosts(){
		List<Post> posts = postEJB.getAll();
		return Response.status(OK)
				.entity(posts)
				.links(super.getSelfLink())
				.build();
	}
	
	@Path("/{post_id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("post_id") int id){
		Post post = postEJB.getById(id);
		return Response.status(OK)
				.entity(post)
				.links(
						super.getSelfLink(), 
						super.appendResourceToSelf(CommentResource.class, "comments")
						)
				.build();
	}
	
	@Path("/{post_id}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public CommentResource getPostComments(@PathParam("post_id") int id){
		return commentResource;
	}
	
	
	
}
