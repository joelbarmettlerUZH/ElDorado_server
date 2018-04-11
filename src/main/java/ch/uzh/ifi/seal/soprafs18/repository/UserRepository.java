package ch.uzh.ifi.seal.soprafs18.repository;

import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    List<UserEntity> findByUserID(int ID);
    List<UserEntity> findByToken(String token);
    List<UserEntity> findByName(String name);
}
