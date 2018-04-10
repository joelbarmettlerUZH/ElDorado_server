package ch.uzh.ifi.seal.soprafs18;

import ch.uzh.ifi.seal.soprafs18.entity.GameEntity;
import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.BoardEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.HexSpaceEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.TileEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.BoardRepository;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.HexSpaceRepository;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.TileRepository;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs18.repository.RoomRepository;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner loadDUsers(UserRepository userRepository) {
        return (args) -> {
            for(String name:new String[] {"Joel", "Marius"}){
                UserEntity user = new UserEntity(name, 1, null);
                userRepository.save(user);
            }
        }; //userRepository.findAll().iterator().next().setReady(true);

    }

    @Bean
    public CommandLineRunner loadRooms(RoomRepository roomRepository) {
        return (args) -> {
            roomRepository.save(new RoomEntity("Testroom"));
        };
    }

    @Bean
    public CommandLineRunner userToGame(UserRepository userRepository, RoomRepository roomRepository) {
        return (args) -> {
            RoomEntity roomEntity = roomRepository.findAll().iterator().next();
            List<UserEntity> users = new ArrayList<>();
            userRepository.findAll().forEach(users::add);
            for(UserEntity user:users){
                roomEntity.addUser(user);
                roomRepository.save(roomEntity);
                user.setReady(true);
                userRepository.save(user);
            }

        }; //userRepository.findAll().iterator().next().setReady(true);

    }

    @Bean CommandLineRunner saveHexSpacesToDB(HexSpaceRepository hexSpaceRepository) {
        return (args) -> {
            hexSpaceRepository.save(new HexSpaceEntity("J1", "JUNGLE", 1));
            hexSpaceRepository.save(new HexSpaceEntity("J2", "JUNGLE", 2));
            hexSpaceRepository.save(new HexSpaceEntity("J3", "JUNGLE", 3));
            hexSpaceRepository.save(new HexSpaceEntity("W1", "RIVER", 1));
            hexSpaceRepository.save(new HexSpaceEntity("W2", "RIVER", 2));
            hexSpaceRepository.save(new HexSpaceEntity("W3", "RIVER", 3));
            hexSpaceRepository.save(new HexSpaceEntity("S1", "SAND", 1));
            hexSpaceRepository.save(new HexSpaceEntity("S2", "SAND", 2));
            hexSpaceRepository.save(new HexSpaceEntity("S3", "SAND", 3));
            hexSpaceRepository.save(new HexSpaceEntity("S4", "SAND", 4));
            hexSpaceRepository.save(new HexSpaceEntity("R1", "RUBBLE", 1));
            hexSpaceRepository.save(new HexSpaceEntity("R2", "RUBBLE", 2));
            hexSpaceRepository.save(new HexSpaceEntity("R3", "RUBBLE", 3));
            hexSpaceRepository.save(new HexSpaceEntity("M", "MOUNTAIN", 1000));
            hexSpaceRepository.save(new HexSpaceEntity("B1", "BASECAMP", 1));
            hexSpaceRepository.save(new HexSpaceEntity("B2", "BASECAMP", 2));
            hexSpaceRepository.save(new HexSpaceEntity("B3", "BASECAMP", 3));
            hexSpaceRepository.save(new HexSpaceEntity("ST", "STARTFIELD", 1000));
            hexSpaceRepository.save(new HexSpaceEntity("N", "EMPTY", 1000));
            hexSpaceRepository.save(new HexSpaceEntity("EJ", "ENDFIELDJUNGLE", 1));
            hexSpaceRepository.save(new HexSpaceEntity("EW", "ENDFIELDRIVER", 1));
            hexSpaceRepository.save(new HexSpaceEntity("ED", "ELDORADO", 1000));
        };
    }

    @Bean CommandLineRunner saveTilesToDB(TileRepository tileRepository, HexSpaceRepository hexSpaceRepository,
                                    BoardRepository boardRepository) {

        List<HexSpaceEntity> HexSpaces_TileA = new ArrayList<>();
        String[] HexSpaceIds_TileA = {"J1", "S1", "J1", "J1", "J1", "J1", "ST", "ST", "ST", "ST", "J1", "J1", "J1", "J1", "W1", "J1", "B1", "J1", "J1", "J1", "S1", "W1", "J1", "J1", "J1", "J1", "J1", "S1", "M", "M", "J1", "J1", "J1", "S1", "J1", "S1", "W1"};
        for (String id : HexSpaceIds_TileA) {
            HexSpaces_TileA.add(hexSpaceRepository.findByHexID(id));
        }
        return (args) -> {
            tileRepository.save(new TileEntity('A', HexSpaces_TileA));
            //HexSpaceEntity newEntity = tileRepository.findAll().iterator().next().getHexSpaceEntities().iterator().next();
            //System.out.println(newEntity.getColor());
            //System.out.println(tileRepository.findAll().iterator().next().getTileID());
            //System.out.println(tileRepository.findAll().iterator().next().getTileID());
        };
    }

    @Bean CommandLineRunner savePathToDB(BoardRepository boardRepository, TileRepository tileRepository) {
            List<TileEntity> tiles_defaultPath = new ArrayList<>();
            tiles_defaultPath.add(tileRepository.findByTileID('A'));
            List<Integer> tilePositionsX_defaultPath = new ArrayList<>();
            tilePositionsX_defaultPath.add(4);
            return (args) -> {
            boardRepository.save(new BoardEntity(1, tiles_defaultPath, tilePositionsX_defaultPath, tilePositionsX_defaultPath,
                    tilePositionsX_defaultPath, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        };
    }
        /*(1,
                ('B','C'),(3,0,3,3,5),(4,5,11,17,16),(4,5,16,19,27),
        (),(),(),(),
                (1,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4),(3,4,5,6,8,8,7,7,15,14,14,13,15,16,17,18),(8,8,8,8,13,14,15,16,16,17,18,19,23,23,23,23),
        ("EW","EW","EW"),(14,14,15),(30,31,31));*/





    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}
