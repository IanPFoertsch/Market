package production.java.com;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import production.entity.PositionCollection;
import production.entity.Post;
import production.enums.PostingType;

public class MarketInitializationScript {
	public static void main(String[] args) {
		String targetURL = "http://localhost:8080/MarketServiceGradle";
		String resourceUrl = "/order";

		ObjectMapper mapper = new ObjectMapper();
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(targetURL + resourceUrl);
		

		String[] bidders = {"Ian", "Christine"};
		String[] askers = {"Tana", "Travis", "Eli"};
		
		String[] symbols = {"SILVER", "GOLD", "COPPER", "IRON", "STEEL", "WHEAT", "OIL", "TIMBER", "STONE"};
		
		
		double askPrice = 20d;
		double bidPrice = 10d;
		
		double askVolume = 10d;
		double bidVolume = 10d;
		
		Post post = new Post();
		for(String name: bidders) {
			for(String symbol: symbols) {
				post.setDate(System.currentTimeMillis());
				post.setPostingType(PostingType.BID);
				post.setPrice(bidPrice);
				post.setVolume(bidVolume);
				post.setUserIdentifier(name);
				post.setSymbol(symbol);
				
				Response response = target.request().post(Entity.json(post));
				System.out.println(response.getStatus());
				response.close();
			}
		}
		
		for(String name: askers) {
			for(String symbol: symbols) {
				post.setDate(System.currentTimeMillis());
				post.setPostingType(PostingType.ASK);
				post.setPrice(askPrice);
				post.setVolume(askVolume);
				post.setUserIdentifier(name);
				post.setSymbol(symbol);
				
				Response response = target.request().post(Entity.json(post));
				System.out.println(response.getStatus());
				response.close();
			}
		}
		
	}

}
