package production.java.com;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import production.entity.Post;
import production.enums.PostingType;
import production.marshalling.PostMarshaller;

public class clientDriver {
	public static void main(String[] args) throws JAXBException {
		
		String targetURL = "http://localhost:8080/MarketService";
		String resourceUrl = "/offer";
		String preExistingPost = "/CurrentOffer";
		
		
		Client client = ClientBuilder.newClient();
		
		//Post response = client.target(targetURL + resourceUrl + preExistingPost).request().get(Post.class); 
		
		//System.out.println(response.toString());
		Post post = new Post();
		
		post.setPostingType(PostingType.OFFER);
		post.setPrice(1.0);
		post.setSymbol("GOLD");
		post.setDate(System.currentTimeMillis());
		post.setUserIdentifier("Ian");
		post.setVolume(1.0);
		
		PostMarshaller postMarshaller = new PostMarshaller();
		String marshalledPost = postMarshaller.marshal(post);
		
		Response  response = client.target(targetURL+resourceUrl).request().post(Entity.xml(marshalledPost));
		System.out.println(response.getStatus());
		
		client.close();
	}
}
