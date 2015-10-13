package production.dao;

import javax.ejb.Stateless;
import production.entity.UserProfile;
@Stateless
public class UserProfileDao extends AbstractDao<UserProfile>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserProfileDao() {
		super(UserProfile.class);
	}
}
