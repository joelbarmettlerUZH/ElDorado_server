package ch.uzh.ifi.seal.soprafs18.game.board.repository;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.HexSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class HexSpaceService {
    @Autowired
    private HexSpaceRepository hexSpaceRepository;

    public List<HexSpaceEntity> getHexSpaceEntity(){
        List<HexSpaceEntity> hexSpaceEntities = new ArrayList<>();
        hexSpaceRepository.findAll().forEach(hexSpaceEntities::add);
        return hexSpaceEntities;
    }
    @Transactional
    public void addHexSpaceEntity(HexSpaceEntity hexSpaceEntity){
        hexSpaceRepository.save(hexSpaceEntity);
    }

}
