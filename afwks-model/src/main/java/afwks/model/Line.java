package afwks.model;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="LINE")
public class Line {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="NAME")
	private String name;

	
	@OneToMany(mappedBy="line", cascade=CascadeType.ALL/*, orphanRemoval=true*/)

	private Set<StationLine>stationslines = new LinkedHashSet<StationLine>();
	
	public void addStations(List<Station>stations){
		
		int i=1;
		for(Station station : stations){
			StationLine stationLine = new StationLine();
			stationLine.setStation(station);
			stationLine.setLine(this);
			stationLine.setOrder(i++);
			stationslines.add(stationLine);
		}
	}
	
	public Line(){
		
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Line other = (Line) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Set<StationLine> getStationslines() {
		return stationslines;
	}


	public void setStationslines(Set<StationLine> stationslines) {
		this.stationslines = stationslines;
	}

	public Line(String name){
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
