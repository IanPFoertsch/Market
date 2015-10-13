package production.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="UserProfile")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "user_profiles")
public class UserProfile implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7350319114657957364L;

	@Id
	@NotNull
	@Size(min=1,max=25, message="Enter a user identifier between 1 and 25 (inclusive) characters")
	@XmlElement
	@FormParam(value="userIdentifier")
	@Column(name="user_identifier")
	private String userIdentifier;
	
	@NotNull
	@Size(min = 1, max=25, message = "Enter a password between 1 and 25 (inclusive)  characters")
	@FormParam(value="password")
	@Column(name="password")
	private String password;

	/**
	 * @return the userIdentifier
	 */
	public String getUserIdentifier() {
		return userIdentifier;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param userIdentifier the userIdentifier to set
	 */
	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
