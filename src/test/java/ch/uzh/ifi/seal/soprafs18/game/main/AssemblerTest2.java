package ch.uzh.ifi.seal.soprafs18.game.main;



import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.HexSpaceRepository;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.HexSpaceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class AssemblerTest2 {

    @TestConfiguration
    static class AssemblerTest2Configuration{
        @Bean
        public HexSpaceService hexSpaceService(){
            return new HexSpaceService();
        }
    }

    @Autowired
    private HexSpaceService hexSpaceService;

    @MockBean
    private HexSpaceRepository hexSpaceRepository;



    @Before
    public void setUp(){
        List<HexSpaceEntity> HexSpaceList = new ArrayList<>();
        HexSpaceEntity hexSpaceEntityJ1 = new HexSpaceEntity("J1","JUNGLE",1);
        HexSpaceList.add(hexSpaceEntityJ1);
        HexSpaceEntity hexSpaceEntityS1 = new HexSpaceEntity("S1","SAND",1);
        HexSpaceList.add(hexSpaceEntityS1);
        HexSpaceEntity hexSpaceEntityW1 = new HexSpaceEntity("W1","RIVER",1);
        HexSpaceList.add(hexSpaceEntityW1);
        HexSpaceEntity hexSpaceEntityST = new HexSpaceEntity("ST","STARTFIELD",1000);
        HexSpaceList.add(hexSpaceEntityST);
        HexSpaceEntity hexSpaceEntityB1 = new HexSpaceEntity("B1","BASECAMP",1);
        HexSpaceList.add(hexSpaceEntityB1);
        HexSpaceEntity hexSpaceEntityM = new HexSpaceEntity("M","MOUNTAIN",1000);
        HexSpaceList.add(hexSpaceEntityM);
        Mockito.when(hexSpaceRepository.findByHexid("J1")).thenReturn(hexSpaceEntityJ1);
    }

    @Test
    public void assembleBoard() {
        //String mockId = "J1";
        //HexSpaceEntity found = hexSpaceService.getHexSpaceEntity();
    }

    @Test
    public void getBlockades() {
        assertEquals(12, 12);
    }

    @Test
    public void getStartingFields() {
    }

    @Test
    public void getEndingFields() {
    }
}