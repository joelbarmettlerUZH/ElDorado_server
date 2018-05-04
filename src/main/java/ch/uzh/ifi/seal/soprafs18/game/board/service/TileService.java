package ch.uzh.ifi.seal.soprafs18.game.board.service;


import ch.uzh.ifi.seal.soprafs18.game.board.entity.BoardEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.TileEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.TileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class TileService implements Serializable {

    @Autowired
    public TileRepository tileRepository;

    public TileEntity getTile(Character tileId){
        return tileRepository.findByTileID(tileId);
    }

    public void saveTile(TileEntity tileEntity){
        tileRepository.save(tileEntity);
    }

    public Iterable<TileEntity> save(List<TileEntity> tiles){
        return tileRepository.saveAll(tiles);
    }

}
