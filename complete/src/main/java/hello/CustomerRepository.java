package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerRepository {

	private final JdbcTemplate jdbc;

	@Autowired
	public CustomerRepository(JdbcTemplate jdbcTemplate) {
		this.jdbc = jdbcTemplate;
	}

	public Customer getCustomer(int id) {
		return jdbc.queryForObject("SELECT * FROM CUSTOMERS WHERE ID=?", new CustomerRowMapper(), id);
	}

	public List<Customer> getCustomers() {
		return jdbc.query("SELECT * FROM CUSTOMERS", new CustomerRowMapper());
	}

	/**
	 * @return - the customer.id value that was created by the database
	 */
	public int createCustomer(Customer customer) {
		final String firstName = customer.getFirstName();
		final String lastName = customer.getLastName();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps =
								connection.prepareStatement("INSERT INTO CUSTOMERS (ID, FIRSTNAME, LASTNAME) VALUES (DEFAULT, ?, ?)", new String[]{"ID"});
						ps.setString(1, firstName);
						ps.setString(2, lastName);
						return ps;
					}
				},
				keyHolder);

		return keyHolder.getKey().intValue();
	}

	private static class CustomerRowMapper implements RowMapper<Customer> {
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer customer = new Customer();
			customer.setId(rs.getInt("ID"));
			customer.setFirstName(rs.getString("FIRSTNAME"));
			customer.setLastName(rs.getString("LASTNAME"));
			return customer;
		}
	}
}

