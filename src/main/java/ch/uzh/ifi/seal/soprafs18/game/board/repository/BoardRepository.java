package ch.uzh.ifi.seal.soprafs18.game.board.repository;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.BoardEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BoardRepository extends CrudRepository<BoardEntity, Integer> {
    BoardEntity findByBoardID(int boardID);
}
