package hello.repositories;

import org.springframework.data.repository.CrudRepository;

import hello.models.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called usersRepository
// CRUD refers Create, Read, Update, Delete
public interface UsersRepository extends CrudRepository<User, Long> {

}
