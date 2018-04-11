package ch.uzh.ifi.seal.soprafs18.game.board.service;


import ch.uzh.ifi.seal.soprafs18.game.board.entity.TileEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.TileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class TileService implements Serializable {

    @Autowired
    public TileRepository tileRepository;

    public TileEntity getTile(char tileId){
        return tileRepository.findByTileID(tileId);
    }
}
