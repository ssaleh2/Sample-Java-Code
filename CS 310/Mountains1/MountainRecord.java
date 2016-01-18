public class MountainRecord implements Comparable<MountainRecord> {
	String name;
	String country;
	Integer altitude;
	
	//constructor with three attributes
	public MountainRecord(String name2, String country2, Integer altitude2) {
		name = name2;
		country = country2;
		altitude = altitude2;
	}
	
	//default constructor
	public MountainRecord() {
	}
	
	//compares two mountain records based on the altitude values 
	public int compareTo(MountainRecord record) {
		if((this.altitude).compareTo((record).altitude)<0) return -1;
		else if((this.altitude).compareTo((record).altitude)>0) return 1;
		else return 0;
	}
	
	//two mountain records equal each other if the two mountain names are equal
	public boolean equals(Object o) {
		if ( this == o ) 
			return true;
		if ( !(o instanceof MountainRecord) ) 
			return false;
		MountainRecord mount = (MountainRecord) o;
		return this.name.equals(mount.name);
	}	  
	
	public String toString(){
		return name + ", " + country + ", " + altitude;
	}
}
