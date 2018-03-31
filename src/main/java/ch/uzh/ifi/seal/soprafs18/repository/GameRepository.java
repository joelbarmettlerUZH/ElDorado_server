package ch.uzh.ifi.seal.soprafs18.repository;

import ch.uzh.ifi.seal.soprafs18.entity.GameEntity;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<GameEntity, Integer> {
    List<GameEntity> findByGameID(int ID);
    List<GameEntity> findByRunningIsTrue();
    List<GameEntity> findByPlayersContaining(Player player);
}
