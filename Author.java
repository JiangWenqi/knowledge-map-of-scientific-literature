package cn.edu.bistu.node;

import java.util.HashMap;
import java.util.Map;

public class Author {
	private String forename;
	private String surname;
	private String email;
	private String institution;
	private int postCode;
	private String country;

	public Author() {
		forename = null;
		surname = null;
		email = null;
		institution = null;
		postCode = 0;
		country = null;
	}

	public Author(String forename, String surname, String email, String institution, int postCode, String country) {
		this.forename = forename;
		this.surname = surname;
		this.email = email;
		this.institution = institution;
		this.postCode = postCode;
		this.country = country;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public int getPostCode() {
		return postCode;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Author [forename=" + forename + ", surname=" + surname + ", email=" + email + ", institution="
				+ institution + ", postCode=" + postCode + ", country=" + country + "]";
	}

	public final String TEMPLATE = "CREATE (a:Author {name: $name,email:$email,institution:$institution,postCode:$postCode,country:$country})";

	public Map<String, Object> toNode() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", this.forename+" "+this.surname);
		parameters.put("email", this.email);
		parameters.put("institution", this.institution);
		parameters.put("postCode", this.postCode);
		parameters.put("country", this.country);
		return parameters;
	}

}
