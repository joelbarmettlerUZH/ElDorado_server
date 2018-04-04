package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.BoardEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.TileEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.BoardRepository;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.HexSpaceRepository;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.HexSpaceService;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.TileRepository;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
/*
@TestPropertySource(locations = "classpath:data.sql")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootApplication
@TestExecutionListeners(
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
        listeners = {TransactionalTestExecutionListener.class}
)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
*/

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = {TileRepository.class,HexSpaceRepository.class,BoardRepository.class,TestTest.class})
//SpringBootApplication
//@AutoConfigureTestDatabase
//@EnableAutoConfiguration
//@Configuration
//@ComponentScan
//@EntityScan("com.delivery.domain")
//@EnableTransactionManagement
//@EnableJpaRepositories
//@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")
//@Repository
//@Component
@RunWith(SpringRunner.class)
public class AssemblerTest {

    @TestConfiguration
    static class AssemblerConf{
        @Bean
        public HexSpaceService hexSpaceService(){
            return new HexSpaceService();
        }
    }

    private HexSpaceService hexSpaceService;

    @MockBean
    private HexSpaceRepository hexSpaceRepository;

    private String[] hexSpaceArray = {"J1","S1","J1","J1","J1","J1","ST","ST","ST","ST","J1","J1","J1","J1","W1","J1","B1","J1",
            "J1","J1","S1","W1","J1","J1","J1","J1","J1","S1","M","M",
            "J1","J1","J1","S1","J1","S1",
            "W1"};




    @MockBean(name="tileRepository")
    private TileRepository tileRepository;
    //@MockBean(name="hexSpaceRepository")
    //@Autowired
    //private HexSpaceRepository hexSpaceRepository;
    @MockBean(name="boardRepository")
    private BoardRepository boardRepository;
    @MockBean(name="hexSpace")
    private List<HexSpaceEntity> hexSpaces;
    @MockBean(name="tileRotation")
    private List<Integer> tileRotation;
    @Autowired
    private List<Integer> tilePosX;
    @Autowired
    private List<Integer> tilePosY;


    @Autowired
    //private TestTest test;

    @Before
    public void init(){



        System.out.print("hgh");
        //test.assembleBoard();
        System.out.print("hgh");
        hexSpaceRepository.save(new HexSpaceEntity("J1","JUNGLE",1));
        hexSpaceRepository.save(new HexSpaceEntity("S1","SAND",1));
        hexSpaceRepository.save(new HexSpaceEntity("W1","RIVER",1));
        hexSpaceRepository.save(new HexSpaceEntity("ST","STARTFIELD",1000));
        hexSpaceRepository.save(new HexSpaceEntity("B1","BASECAMP",1));
        hexSpaceRepository.save(new HexSpaceEntity("M","MOUNTAIN",1000));

        System.out.print(hexSpaceRepository.findByHexid("J1").getColor());

        for (int i = 0; i<hexSpaceArray.length;i++){
            hexSpaces.add(hexSpaceRepository.findByHexid(hexSpaceArray[i]));
        }
        tileRotation.add(0);


        tilePosX.add(4);
        tilePosY.add(4);
        tileRepository.save(new TileEntity('Z', hexSpaces));
        boardRepository.save((new BoardEntity(111,tileRepository.findByTileID('Z'),
                tileRotation,tilePosX,tilePosY)));
    }

    @Test
    //@SqlFileLocation("classpath:sql/myfil.sql")
    //@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void assembleBoard() {


        //System.out.print(boardRepository.findAll());

        //Assembler assemly = new Assembler();
        //HexSpaceEntity[][] mat =  assemly.assembleBoard(111);
        //System.out.print(mat);

        assertEquals(12, 12);
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