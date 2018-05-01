package ch.uzh.ifi.seal.soprafs18.game.board.service;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.StripEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.TileEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.StripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class StripService implements Serializable {

    @Autowired
    public StripRepository stripRepository;

    public StripEntity getStrip(char stripId){
            return stripRepository.findByStripID(stripId);
        }

    public void saveStrip(StripEntity stripEntity){
        stripRepository.save(stripEntity);
    }
}

