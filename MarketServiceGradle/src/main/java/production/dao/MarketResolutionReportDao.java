package production.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import production.entity.MarketResolutionReport;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MarketResolutionReportDao extends AbstractDao<MarketResolutionReport>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject 
	private EntityManager entityManager;
	
	
	public MarketResolutionReportDao() {
		super(MarketResolutionReport.class);
	}
}
