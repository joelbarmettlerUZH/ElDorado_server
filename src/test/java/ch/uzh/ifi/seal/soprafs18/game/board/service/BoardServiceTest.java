package ch.uzh.ifi.seal.soprafs18.game.board.service;

import ch.uzh.ifi.seal.soprafs18.game.board.repository.BoardRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import static org.junit.Assert.*;

public class BoardServiceTest {

    /*@TestConfiguration
    static class GameServiceTestContextConfiguration {

        @Bean
        public BoardService boardService () { return new BoardService(new BoardRepository());
        }
    }

    @Autowired
    private BoardService boardService;

    @MockBean
    private BoardRepository boardRepository;



    @Test
    public void getFirst() {
        boardService.getFirst();
    }*/
}