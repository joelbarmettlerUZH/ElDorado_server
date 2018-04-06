package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.service.HexSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@EnableAutoConfiguration
@ComponentScan
@Service
public class TestTest {

    @Autowired
    private HexSpaceService hexSpaceService;

    public void assembleBoard() {
        System.out.print("hghkjafhjklah");
        HexSpaceEntity TestSpace = new HexSpaceEntity("J1", "JUNGLE", 1);
        System.out.print(TestSpace.getColor());
        hexSpaceService.addHexSpaceEntity(TestSpace);
        HexSpaceEntity TestSpace2 = new HexSpaceEntity("J2", "JUNGLE", 2);
        hexSpaceService.addHexSpaceEntity(TestSpace2);
        System.out.print(hexSpaceService.getHexSpaceEntity().get(1));

    }
}
