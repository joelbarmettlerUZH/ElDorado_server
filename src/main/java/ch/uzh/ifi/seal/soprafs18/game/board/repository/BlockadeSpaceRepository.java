package ch.uzh.ifi.seal.soprafs18.game.board.repository;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.BlockadeSpaceEntity;
import org.springframework.data.repository.CrudRepository;

public interface BlockadeSpaceRepository extends CrudRepository<BlockadeSpaceEntity, Integer> {
    BlockadeSpaceEntity findByBlockadeID(int blockadeId);
}
