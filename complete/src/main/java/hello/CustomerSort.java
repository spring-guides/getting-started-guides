package hello;

enum CustomerSort {
	
	ID("id"),
	FIRST_NAME("firstName", "lastName", "id"),
	LAST_NAME("lastName", "firstName", "id");
	
	private String[] properties;
	
	CustomerSort(String... properties) {
		this.properties = properties;
	}

	public String[] getProperties() {
		return properties;
	}
	
}