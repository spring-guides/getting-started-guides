package mysqlapp.repositories;

import org.springframework.data.repository.CrudRepository;

import mysqlapp.models.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called usersRepository
// CrUD refers Create, Update, Delete
public interface UsersRepository extends CrudRepository<User, Long> {

}
