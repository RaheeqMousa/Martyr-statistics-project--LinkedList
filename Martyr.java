package application;



import java.util.Date;

//(Name, Age, Event location, Date of death, and Gender).
public class Martyr implements Comparable<Martyr>, Cloneable {

	private String name;
	private byte age;  //the range is -128 to 127, and I think 
					  //that the age of any person on earth is impossible to be greater than 127 
	private String location;
	private String dateOfDeath;
	private char gender;
	private String District;
	
	public Martyr() {
		this.name="no name yet";
		this.age=0;
		this.location="no yet";
		this.dateOfDeath="no date yet";
		this.gender='F';
		this.District="no district yet";
	}
		
	
	public Martyr(String name, byte age,char gender, String date_of_death, String location, String dis) {
		super();
		this.name = name;
		setAge(age);
		this.location = location;
		setDateOfDeath(date_of_death);
		setGender(gender);
		this.District=dis;
	}

	public static boolean validDate(String d) {
		
		String[] a=d.split("/");
		Date s=new Date();
		int currentYear =s.getYear()+1900;
		short currentdays= (short) s.getDate();
		byte currentMonth=(byte) (s.getMonth()+1);
		
		try {
			
		byte month=Byte.parseByte(a[0]);
		byte day=Byte.parseByte(a[1]);
		short year=Short.parseShort(a[2]);
			
			if(year==currentYear && (day>currentdays || month>currentMonth )) { // If the entered year is equal to current year and the day of month or the month of year is larger than the current date so the date is invalid
				return false;
			}
			if(day>=1 && day<=31 && (month==1 || month==12 || month==10|| month==8 || month==7 || month==5 || month==3) && year>0 && year<=currentYear) {
				return true;
			}
			if(day>=1 && day<=30 && (month==4 || month==6 || month==9 || month==11) && year>0 && year<=currentYear) {
				return true;
			}
			if((day>=1 && day<=29) && (month==2) && year>0 && (year%4 ==0  && year<=currentYear)) {  //if the year is leap so the number of days is between 1 and 29
				return true;
			}
			if((day>=1 && day<=28) && (month==2) && year>0 && (year%4 !=0 && year<=currentYear)) {  //if the year is not leap and the month is the second month so the days is between 28 ad 29
				return true;
			}
		
		}catch(NumberFormatException e) { //this means that the date contains string instead of number
			return false;
		}
		return false;
	}
	
	//setters and getters

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public byte getAge() {
		return age;
	}
	public void setAge(byte age) {
		if(age>=0 && age<=127) {
		this.age = age;}
		else {  //if the age is invalid i will throw illegal argument exception
			throw new IllegalArgumentException("invalid age");
		}
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	
	public String getDateOfDeath() {
		return dateOfDeath;
	}
	public void setDateOfDeath(String date_of_death) {
		if(validDate(date_of_death)) {			
			this.dateOfDeath = date_of_death;
		}
		else //if the date is invalid i will throw illegal argument exception
			throw new IllegalArgumentException("INVALID date!");
	}

	
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		if(gender=='F'|| gender=='f'|| gender=='m'|| gender=='M')
			this.gender = gender;
		else  //if the gender is invalid i will throw illegal argument exception
			throw new IllegalArgumentException("invalid gender type");
	}
	
	public String getDistrict() {
		return District;
	}


	public void setDistrict(String district) {
		District = district;
	}


	@Override
	public int compareTo(Martyr o) {
		if(this.getAge() > o.getAge())
			return 1;
		else if(this.getAge() < o.getAge())
			return -1;
		else
			return 0;
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("clone not supported exception");
			return null;
		}
		
	}
	
	@Override
	public String toString() {
		return name+","+dateOfDeath+","+age+","+location+","+District+","+gender;
	}


	
	
}
