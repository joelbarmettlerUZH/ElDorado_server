package ch.uzh.ifi.seal.soprafs18.game.board.controller;

import ch.uzh.ifi.seal.soprafs18.controller.CONSTANTS;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.BoardEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.service.BoardService;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.service.GameService;
import ch.uzh.ifi.seal.soprafs18.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class BoardController implements Serializable {
    private final String context = CONSTANTS.APICONTEXT + "/Board";

    @Autowired
    private BoardService boardService;

    //gets all boards
    @GetMapping(value = context)
    @ResponseStatus(HttpStatus.OK)
    private List<BoardEntity> getBoards(){
        return boardService.getAll();
    }
}
