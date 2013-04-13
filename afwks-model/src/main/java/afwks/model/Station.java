package afwks.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="STATION")
public class Station {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	public Station(){
		
	}
	
	public Station(String name){
		this.name = name;
	}
	
	public int hashCode(){
		return this.name.hashCode()+(this.id!=null?id.hashCode():0);
	}
	
	@Override
	public boolean equals (Object o){
		
		if(o!=null){
			Station other = (Station)o;
			if(this.name.equals(other.getName())){
				return true;
			}
		}
		return false;
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
