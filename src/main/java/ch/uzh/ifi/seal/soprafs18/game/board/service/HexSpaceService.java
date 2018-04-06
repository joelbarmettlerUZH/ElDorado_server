package ch.uzh.ifi.seal.soprafs18.game.board.service;

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

    public List<HexSpaceEntity> getAllHexSpaceEntity(){
        List<HexSpaceEntity> hexSpaceEntities = new ArrayList<>();
        hexSpaceRepository.findAll().forEach(hexSpaceEntities::add);
        return hexSpaceEntities;
    }

    public HexSpaceEntity getHexSpaceEntity(String id){
        return hexSpaceRepository.findByHexid(id);
    }

    public void addHexSpaceEntity(HexSpaceEntity hexSpaceEntity){
        hexSpaceRepository.save(hexSpaceEntity);
    }

}
