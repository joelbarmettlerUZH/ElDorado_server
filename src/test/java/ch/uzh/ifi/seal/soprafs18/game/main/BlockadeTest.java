package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.BlockadeSpace;
import org.junit.Test;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

@WebAppConfiguration
public class BlockadeTest {

    @Test
    public void deactivate() {
        BlockadeSpace testSpace = new BlockadeSpace();
        List<BlockadeSpace> spaces = new ArrayList<BlockadeSpace>();
        spaces.add(testSpace);
        Blockade testBlockade = new Blockade(spaces);

        testBlockade.deactivate();
    }

    @Test
    public void assign() {
    }
}