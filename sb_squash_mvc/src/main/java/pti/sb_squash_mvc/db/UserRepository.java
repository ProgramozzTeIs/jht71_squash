package pti.sb_squash_mvc.db;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pti.sb_squash_mvc.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

	@Query("SELECT * FROM users WHERE name = :name")
	User getUserByName(@Param("name") String name);

}
