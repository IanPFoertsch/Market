package production.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import production.entity.MarketResolutionReport;
import production.entity.Position;
import production.entity.PositionCollection;
import production.entity.UserProfile;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PositionDao extends AbstractDao<Position>{

	
	@Inject
	private EntityManager entityManager;
	
	private static final long serialVersionUID = 1L;
	
	public PositionDao() {
		super(Position.class);
	}

	/*public PositionCollection getPositionsForUser(UserProfile userProfile)
	{	
		ArrayList<Position> positions = new ArrayList<Position>();
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Position> query = criteriaBuilder.createQuery(Position.class);
		Root<Position> position = query.from(Position.class);
	}*/
	
	public PositionCollection getAllPositions() {
		//define the select all query string
		String queryString = "SELECT p FROM Position p";
		//execute the query on the database
		//
		TypedQuery<Position> query = this.entityManager.createQuery(queryString, Position.class); 
		List<Position> positions = query.getResultList();
		//initialize a positions collection object 
		PositionCollection positionCollection = new PositionCollection();
		//create a new arraylist of positions and loat the List<position> into it
		ArrayList<Position> positionsArray = new ArrayList<Position>(positions);
		//set the appropriate field of the collection
		positionCollection.setPositions(positionsArray);
		//return the collection.
		return positionCollection;
		
	}
	
	
	public void persistPosition(Position position) {
		//query the database to see if the given position currently exists.
		String queryString = "SELECT p FROM Position p WHERE p.userIdentifier = '" + position.getUserIdentifier() 
		+ "' AND p.symbol = '" + position.getSymbol() + "'";
		
		try {
			
			//If the position exists, get the existing volume from the database, then perform a merge to update the field
			Position existingPosition = this.entityManager.createQuery(queryString, Position.class).getSingleResult();
			//execute a merge and set the merged field to equal the volumes added together
			double totalVolume = position.getVolume() + existingPosition.getVolume();
			
			Position managedPosition = this.entityManager.merge(position);
			managedPosition.setVolume(totalVolume);	
		}
		//if such a table entry does not exist, use a simple persist operation.
		catch(NoResultException e)
		{
			super.persist(position);
		}
	}
	
	public PositionCollection getPositionsForUser(UserProfile userProfile) {
		//query the database to see if a user profile exists with a matching userIdentifier/database password combination
		
		//THIS IS A NATIVE POSTGRES SQL QUERY
		String userPasswordMatchQueryString = "SELECT CASE WHEN EXISTS( " + 
				"SELECT * FROM user_profiles WHERE user_identifier = '"+userProfile.getUserIdentifier() + "' and password = '"+ userProfile.getPassword() + "')" + 
				"THEN 1 ELSE 0 END AS BIT;";
		
		//THIS IS A JPQL QUERY
		String userPositionsQueryString = "SELECT p FROM Position p WHERE p.userIdentifier = '" + userProfile.getUserIdentifier() + "'";
		System.out.println(userPasswordMatchQueryString);
		
		int userPasswordMatch = (int) this.entityManager.createNativeQuery(userPasswordMatchQueryString).getSingleResult();
		if(userPasswordMatch == 1) {
			TypedQuery<Position> positionsQuery = this.entityManager.createQuery(userPositionsQueryString, Position.class);
			return this.packagePositionsToCollection(positionsQuery.getResultList());
		}
		else {
			throw new NoResultException("provided UserProfile does not match any existing userIdentifier/Password");
		}
		
	}
	
	/** 
	 * applyMarketResolutionReport applies the sale detailed within a resolution
	 * report to the positions table of the database. This functionality is condensed 
	 * into this class in order to make the exchange of cash and shares transactional
	 * and fail-safe.
	 * @param report
	 */
	public void applyMarketResolutionReport(Position sellerCash, Position buyerCash, Position sellerPosition, Position buyerPosition) {
		this.persistPosition(sellerCash);
		this.persistPosition(buyerCash);
		this.persistPosition(sellerPosition);
		this.persistPosition(buyerPosition);
	}
	
	private PositionCollection packagePositionsToCollection(List<Position> positions) {
		PositionCollection positionCollection = new PositionCollection();
		//create a new arraylist of positions and loat the List<position> into it
		ArrayList<Position> positionsArray = new ArrayList<Position>(positions);
		//set the appropriate field of the collection
		positionCollection.setPositions(positionsArray);
		//return the collection.
		return positionCollection;
	}
	
	
}
