package ch.uzh.ifi.seal.soprafs18.game.board.service;

import ch.uzh.ifi.seal.soprafs18.game.board.repository.BoardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@WebAppConfiguration
@DirtiesContext
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void getFirst() {
        assertEquals(0, boardService.getFirst().getBoardID());
    }

    @Test
    public void getAll() {
        assertEquals(true, boardService.getAll().size()>5);
    }

    @Test
    public void getBoards() {
        assertEquals(6, boardService.getBoards(0,5).size());
    }

    @Test
    public void getBoardsFromBiggerThanTo() {
        assertEquals(0, boardService.getBoards(5,0).size());
    }

    @Test
    public void getBoardsTooLessBoards() {
        assertEquals(true, boardService.getBoards(0,50).size()<50);
    }
}