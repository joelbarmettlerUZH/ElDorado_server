package ch.uzh.ifi.seal.soprafs18.game.board.repository;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.StripEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StripRepository extends CrudRepository<StripEntity, Integer> {
    List<StripEntity> findByStripID(int id);
}
