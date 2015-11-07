package production.dao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import production.entity.UserProfile;
@Stateless
public class UserProfileDao extends AbstractDao<UserProfile>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	public UserProfileDao() {
		super(UserProfile.class);
	}
	
	/**
	 * login supports the login functionality of the web service.
	 * The method accepts a userProfile, and checks if the database has a 
	 * matching entry with identical username/password.
	 * @param userProfile
	 * @return
	 */
	public boolean login(UserProfile userProfile) {
		String queryStringOne = "select case when exists (select true from user_profiles where user_identifier='"; 
		String queryStringTwo = "' and password ='";
		String queryStringThree = "') then 1 else 0 end as bit;";
		
		
		String totalQueryString = queryStringOne + userProfile.getUserIdentifier() + queryStringTwo + userProfile.getPassword() + queryStringThree;
		int exists = (int) this.entityManager.createNativeQuery(totalQueryString).getSingleResult();
		
		return (exists == 1);
		
	}
}
