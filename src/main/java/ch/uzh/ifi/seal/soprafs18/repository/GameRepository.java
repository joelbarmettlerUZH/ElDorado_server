package ch.uzh.ifi.seal.soprafs18.repository;

import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Integer> {
    List<Game> findByGameId(int ID);
}
