package production.java.com.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import production.dao.PositionDao;
import production.entity.Position;
import production.entity.PositionCollection;
import production.entity.UserProfile;

@Path("/position")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PositionService {

	@Inject
	private PositionDao positionDao;

	@Path("")
	@POST
	public Response addNewPosition(Position position) {
		System.out.println("Position: " + position.getSymbol() + position.getUserIdentifier() + position.getVolume()); 
		this.positionDao.persistPosition(position);
		return Response.ok().build();
	}
	
	@Path("")
	@GET
	public PositionCollection getAllPositions() {
		return this.positionDao.getAllPositions();
	}
	
	@Path("/user")
	@GET
	public PositionCollection getPositionsForUser(@QueryParam("userIdentifier") String userIdentifier, @QueryParam("password") String password) {
		
		UserProfile userProfile = new UserProfile();
		userProfile.setUserIdentifier(userIdentifier);
		userProfile.setPassword(password);
		return this.positionDao.getPositionsForUser(userProfile);
	}
	
}
