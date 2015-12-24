package edu.iut.app;

public class Person implements Comparable {

	private static int AUTO_INCREMENT = 1;

	public enum PersonFunction{
		/* EX2 : Internationalisation */
		NONE("None"),
		JURY("Jury"),
		STUDENT("Student");
		
		private String personFunction;
		
		PersonFunction(String personFunction) {
			this.personFunction = personFunction;
		}
		
		public String toString() {
			return personFunction;
		}		
	}
	
	public Person() {
		personFunction = PersonFunction.NONE;
		this.id = AUTO_INCREMENT;
		AUTO_INCREMENT++;
	}

	public Person(PersonFunction personFunction, String firstname, String lastname){
		this(personFunction,firstname,lastname, null, null);
	}
	
	public Person(PersonFunction personFunction,
				  String firstname,
				  String lastname,
				  String email,
				  String phone) {
		this.personFunction = personFunction;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phone = phone;
		this.id = AUTO_INCREMENT;
		AUTO_INCREMENT++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id){
		this.id = id;
		if(id >= AUTO_INCREMENT){
			AUTO_INCREMENT = id+1;
		}
	}

	public void setFunction(PersonFunction function) {
		this.personFunction = function;
	}
	public PersonFunction getFunction() {
		return personFunction;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getFirstname() {
		return firstname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhone() {
		return phone;
	}


	protected int id;
	protected PersonFunction personFunction;
	protected String firstname;
	protected String lastname;
	protected String email;
	protected String phone;

	public String getFullName() {
		return getFirstname() + " " + getLastname();
	}

	public String toString(){
		return getFullName();
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof Person){
			Person p = (Person) o;
			int c = getLastname().compareTo(p.getLastname());
			if(c != 0){
				return c;
			}else{
				return getFirstname().compareTo(p.getFirstname());
			}
		}else{
			return 0;
		}
	}
}
