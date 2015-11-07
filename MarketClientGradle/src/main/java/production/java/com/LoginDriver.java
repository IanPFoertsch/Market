package production.java.com;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import production.entity.PositionCollection;
import production.entity.Post;
import production.entity.SpreadStatusReportWrapper;
import production.entity.UserProfile;
import production.enums.PostingType;
import production.marshalling.PostMarshaller;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class LoginDriver {
	public static void main(String[] args) throws JAXBException, JsonProcessingException {
		
		UserProfile userProfile = new UserProfile();
		userProfile.setPassword("password");
		userProfile.setUserIdentifier("Ian");
	
		
		String targetURL = "http://localhost:8080/MarketServiceGradle";
		String resourceUrl = "/account/login";
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(targetURL + resourceUrl);
		
		Response response = target.request().post(Entity.json(userProfile));
		System.out.println(response.getStatus());
		response.close();
		
		
		
		//String positionsUrl = "/position/user";
		//WebTarget accountTarget = client.target(targetURL + positionsUrl);
				
		//Response userResponse = accountTarget.queryParam("userIdentifier", "Ian").queryParam("password", "password").request().get();
	
		//String accountsResponse = userResponse.readEntity(PositionCollection.class).toString();
		//System.out.println(accountsResponse);
		
		
		
		
	}
}