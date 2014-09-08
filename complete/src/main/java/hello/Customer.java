package hello;

import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;


public class Customer {

	private Integer id;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = normalizeString(firstName);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = normalizeString(lastName);
	}

	String normalizeString(String str) {
		if (!StringUtils.isEmpty(str)) {
			return StringUtils.capitalize(str.trim().toLowerCase());
		}
		return str;
	}
}

