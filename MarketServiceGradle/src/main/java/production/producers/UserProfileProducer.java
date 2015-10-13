package production.producers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import production.dao.UserProfileDao;
import production.entity.UserProfile;

@javax.enterprise.context.RequestScoped
public class UserProfileProducer {
	@Inject
	private UserProfileDao userProfileDao;
	
	private List<UserProfile> userProfiles;
	
	@PostConstruct
	public void retreiveAllProfiles() {
		userProfiles = userProfileDao.findAll();
	}
	
	@Produces
	public List<UserProfile> getallProfiles(){
		return userProfiles;
	}
	
	public void onMemberListChanged(@Observes(notifyObserver= Reception.IF_EXISTS) final UserProfile userProfile) {
		this.retreiveAllProfiles();
	}
}
