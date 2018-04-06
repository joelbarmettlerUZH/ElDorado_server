package ch.uzh.ifi.seal.soprafs18.game.board.repository;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.BlockadeSpaceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.lang.annotation.Inherited;

@Repository
@Transactional
public interface BlockadeSpaceRepository extends CrudRepository<BlockadeSpaceEntity, Integer> {
    BlockadeSpaceEntity findByBlockadeID(int blockadeId);
}
