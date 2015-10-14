package production.entity;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PositionTest {

	private String sellerId = "seller";
	private String buyerId = "buyer";
	private String symbol = "GOLD";
	private String cashSymbol = "CASH";
	private double volume = 10d;
	private double price = 5d; 
	private Position sellerPosition;
	private Position buyerPosition;
	private Position sellerCash;
	private Position buyerCash;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		double cash = this.price * this.volume;
		
		
		this.sellerPosition = new Position.Builder().
				setUserIdentifier(sellerId).setSymbol(symbol).setVolume(-volume).build();
		this.buyerPosition = new Position.Builder().
				setUserIdentifier(buyerId).setSymbol(symbol).setVolume(volume).build();
		
		this.sellerCash = new Position.Builder().setSymbol("CASH").setUserIdentifier(sellerId).setVolume(cash).build();
		this.buyerCash = new Position.Builder().setSymbol(cashSymbol).setUserIdentifier(buyerId).setVolume( - cash).build();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEqualityOperator() {
		Position newCash = new Position.Builder().deepCopyPosition(this.sellerCash);
		assert(this.sellerCash.equals(this.sellerCash));
		
	}

}
