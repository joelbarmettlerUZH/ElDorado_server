package ch.uzh.ifi.seal.soprafs18.game.board.repository;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HexSpaceRepository extends CrudRepository<HexSpaceEntity, String> {
        HexSpaceEntity findByHexid(String Hexid);
}
