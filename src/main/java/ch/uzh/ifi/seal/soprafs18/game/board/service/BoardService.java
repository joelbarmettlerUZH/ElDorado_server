package ch.uzh.ifi.seal.soprafs18.game.board.service;

import ch.uzh.ifi.seal.soprafs18.game.board.entity.BoardEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    public BoardRepository boardRepository;

    public BoardEntity getBoard(int boardId){
        return boardRepository.findByBoardID(boardId);
    }
}
