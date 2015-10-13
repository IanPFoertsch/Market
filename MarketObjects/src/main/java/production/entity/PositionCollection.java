package production.entity;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PositionCollection")
@XmlAccessorType(XmlAccessType.FIELD)
public class PositionCollection {

	@XmlElement
	private ArrayList<Position> positions;

	/**
	 * @return the positions
	 */
	public ArrayList<Position> getPositions() {
		return positions;
	}

	/**
	 * @param positions the positions to set
	 */
	public void setPositions(ArrayList<Position> positions) {
		this.positions = positions;
	}
	
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("Collection of Positions: ");
		for(Position position: this.positions)
		{
			stringBuilder.append(position.toString());
		}
		
		return stringBuilder.toString();
	}
	
}
