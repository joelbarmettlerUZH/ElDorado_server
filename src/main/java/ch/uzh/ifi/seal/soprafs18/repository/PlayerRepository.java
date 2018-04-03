package ch.uzh.ifi.seal.soprafs18.repository;

import ch.uzh.ifi.seal.soprafs18.entity.PlayerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepository extends CrudRepository<PlayerEntity, Integer> {
    List<PlayerEntity> findByPlayerID(int ID);
}
