package ch.uzh.ifi.seal.soprafs18.game.board.service;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.BlockadeSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.BlockadeSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockadeSpaceService extends HexSpaceService{

    @Autowired
    public BlockadeSpaceRepository blockadeSpaceRepository;

    public BlockadeSpaceEntity getBlockadeSpaceEntity(int id) {
        return blockadeSpaceRepository.findByBlockadeID(id);
    }

    public int getBlockadeCount(){
        return (int) blockadeSpaceRepository.count();
    }
}
