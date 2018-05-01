package ch.uzh.ifi.seal.soprafs18.game.board.service;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.BoardEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.BoardRepository;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService implements Serializable {


    @Autowired
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    //public BoardService(BoardRepository boardRepository){
    //    this.boardRepository = boardRepository;
    //}

    public BoardEntity getBoard(int boardId) {
        return boardRepository.findByBoardID(boardId);
    }

    public List<BoardEntity> getAll() {
        List<BoardEntity> boards = new ArrayList<>();
        boardRepository.findAll().forEach(boards::add);
        return boards;
    }


    public BoardEntity getFirst() {
        BoardEntity boardEntity = boardRepository.findAll().iterator().next();
        System.out.println(boardEntity.getBoardID());
        return boardEntity;
    }

    public void saveBoard(BoardEntity boardEntity){
        boardRepository.save(boardEntity);
    }

}
