package production.java.com.services;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import production.dao.UserProfileDao;
import production.entity.UserProfile;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AccountService {
		
	@Inject
	private UserProfileDao userProfileDao;
	
	private static final int NO_SUCH_PROFILE = 401;
	
	@Path("")
	@POST
	public Response saveNewUserProfile(UserProfile userProfile) {
		this.userProfileDao.persist(userProfile);
		return Response.ok().build();
	}
	
	@Path("")
	@GET
	public List<UserProfile> getAllUserProfiles() {
		return this.userProfileDao.findAll();
	}
	
	@Path("login")
	@POST
	public Response login(UserProfile userProfile) {
		if(this.userProfileDao.login(userProfile)) 
			return Response.ok().build();
		else
			return Response.status(NO_SUCH_PROFILE).build();
	}
}
