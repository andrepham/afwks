package afwks.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name="STATION_LINE")
@Entity
public class StationLine {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne/*(cascade = CascadeType.ALL)*/
	@JoinColumn(name="LINE_ID"/*,insertable = false, updatable = false*/)
	private Line line;
	
	@ManyToOne/*(cascade = CascadeType.ALL)*/
	@JoinColumn(name="STATION_ID"/*,insertable = false, updatable = false*/)
	private Station station;

	@Column(name="ORDER_STATION")
	private Integer order;
	
	public StationLine(Line line, Station station, int order) {
		super();
		this.line = line;
		this.station = station;
		this.order = order;
	}

	public StationLine(){
		
	}
	
	@Override
	public int hashCode(){
		return this.line.hashCode()+this.station.hashCode()+order;
	}
	
	@Override
	public boolean equals(Object other){
		StationLine otherStationLine=(StationLine)other;
			return (this.line.equals(otherStationLine.station) && this.station.equals(otherStationLine.station)
					&& this.order.equals(otherStationLine.order));
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
}
