package club.resource;

import static javax.ws.rs.core.Response.Status.OK;

import java.util.HashMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import club.DAO.User;
import club.DAO.User.ApprovedState;
import club.EJB.interfaces.LocalUser;

@Stateless
@Path("/users")
public class UserResource extends BasicResource{
	
	@EJB
	LocalUser userEJB;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(User user){
		user.setApprovedState(ApprovedState.DENIED);
		user.setAdmin(false);
		user.setBirthday(new java.sql.Date(0));
		user.setHMACPassword(user.getPassword());
		User createdUser = userEJB.create(user);
		
		return Response.status(OK)
				.entity(createdUser)
				.links(super.getSelfLink())
				.build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(User user, @PathParam("id") int id ){
		if(user.getId() == 0){
			HashMap<String, String> errorMsg = new HashMap<>();
			errorMsg.put("Error", "Json must have id");
			return Response.status(Status.PRECONDITION_FAILED).entity(errorMsg).build();
		}
		user.setBirthday(new java.sql.Date(0));
		user.setHMACPassword(user.getPassword());
		User updateUser = userEJB.update(user);
		
		return Response.status(OK)
				.entity(updateUser)
				.links(super.getSelfLink())
				.build();
	}
	
}
