package ch.uzh.ifi.seal.soprafs18.game.board.service;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.BlockadeSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.BlockadeSpaceRepository;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.HexSpaceRepository;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.BlockadeSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.annotation.Inherited;

@Service
@Transactional
public class BlockadeSpaceService extends HexSpaceService{

    @Autowired
    private BlockadeSpaceRepository blockadeSpaceRepository;


    public BlockadeSpaceEntity getBlockadeSpaceEntity(int id) {
        return blockadeSpaceRepository.findByBlockadeID(id);
    }

    public void saveBlockadeSpace(BlockadeSpaceEntity blockadeSpace){ blockadeSpaceRepository.save(blockadeSpace);}

    public int getBlockadeCount(){
        return (int) blockadeSpaceRepository.count();
    }
}
