package ch.uzh.ifi.seal.soprafs18.game.board.service;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.BlockadeSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.BlockadeSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockadeSpaceService {

    @Autowired
    public BlockadeSpaceRepository blockadeSpaceRepository;

    public BlockadeSpaceEntity getBlockadeSpaceEntity(String id) {
        return blockadeSpaceRepository.findByBlockadeID(id);
    }
}
