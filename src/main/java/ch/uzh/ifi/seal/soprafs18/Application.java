package ch.uzh.ifi.seal.soprafs18;

import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.game.board.entity.*;
import ch.uzh.ifi.seal.soprafs18.game.board.repository.*;
import ch.uzh.ifi.seal.soprafs18.game.board.service.BlockadeSpaceService;
import ch.uzh.ifi.seal.soprafs18.game.board.service.TileService;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs18.repository.RoomRepository;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    BlockadeSpaceRepository blockadeSpaceRepository;

    @Autowired
    HexSpaceRepository hexSpaceRepository;

    @Autowired
    TileRepository tileRepository;

    @Autowired
    StripRepository stripRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomRepository roomRepository;

    private ResourceLoader resourceLoader;



    /*
    @Bean
    public CommandLineRunner loadDUsers() {
        return (args) -> {
            int i = 0;
            for(String name:new String[] {"Joel", "Marius"}){
                UserEntity user = new UserEntity(name, i, null);
                userRepository.save(user);
                i++;
            }
        }; //userRepository.findAll().iterator().next().setReady(true);

    }

    @Bean
    public CommandLineRunner loadRooms() {
        return (args) -> {
            roomRepository.save(new RoomEntity("Testroom"));
        };
    }

    @Bean
    public CommandLineRunner userToGame() {
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

    */
    @Bean
    CommandLineRunner savePathElementsToDB(TileService tileService){
        return args -> {
            // read files and write to db
            ClassLoader classLoader = getClass().getClassLoader();
            String readLine = "";
            //-----SAVE-HEXSPACES-----
            try {
                BufferedReader b = getBufferedReader("json/hexspaces.txt");
                while ((readLine = b.readLine()) != null) {
                    JsonNode hexSpace = getJsonNode(readLine);
                    hexSpaceRepository.save(new HexSpaceEntity(hexSpace.get("id").asText(),
                            hexSpace.get("color").asText(),hexSpace.get("strength").asInt()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //-----SAVE-BLOCKADESPACES-----
            try {
                BufferedReader b = getBufferedReader("json/blockadespaces.txt");
                while ((readLine = b.readLine()) != null) {
                    JsonNode blockadeSpace = getJsonNode(readLine);
                    blockadeSpaceRepository.save(new BlockadeSpaceEntity(blockadeSpace.get("id").asText(),
                            blockadeSpace.get("color").asText(),blockadeSpace.get("strength").asInt(),
                            blockadeSpace.get("blockadeId").asInt()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //-----SAVE-TILES-----
            try {
                BufferedReader b = getBufferedReader("json/tiles.txt");
                while ((readLine = b.readLine()) != null) {
                    JsonNode tile = getJsonNode(readLine);
                    List<HexSpaceEntity> HexSpaces_Tile = new ArrayList<>();
                    tile.get("hexspaces").forEach(
                            hexId -> HexSpaces_Tile.add(hexSpaceRepository.findByHexID(hexId.asText()))
                    );
                    tileRepository.save(new TileEntity(tile.get("id").asText().charAt(0), HexSpaces_Tile));
                    //
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //-----SAVE-STRIPS-----
            try {
                BufferedReader b = getBufferedReader("json/strips.txt");
                while ((readLine = b.readLine()) != null) {
                    JsonNode strip = getJsonNode(readLine);
                    List<HexSpaceEntity> HexSpaces_Strip = new ArrayList<>();
                    strip.get("hexspaces").forEach(
                            hexId -> HexSpaces_Strip.add(hexSpaceRepository.findByHexID(hexId.asText()))
                    );
                    stripRepository.save(new StripEntity(strip.get("id").asText().charAt(0), HexSpaces_Strip));
                    //
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //-----SAVE-PATHS-----
            try {
                BufferedReader b = getBufferedReader("json/paths.txt");
                while ((readLine = b.readLine()) != null) {
                    JsonNode path = getJsonNode(readLine);
                    System.out.println(path.get("id"));
                    List<TileEntity> tiles = new ArrayList<>();
                    path.get("tiles").forEach(
                            tile -> tiles.add(tileRepository.findByTileID(tile.asText().charAt(0)))
                    );
                    List<StripEntity> strips = new ArrayList<>();
                    path.get("strips").forEach(
                            strip -> strips.add(stripRepository.findByStripID(strip.asText().charAt(0)))
                    );
                    List<HexSpaceEntity> ending = new ArrayList<>();
                    path.get("endSpaces").forEach(
                            end -> ending.add(hexSpaceRepository.findByHexID(end.asText()))
                    );
                    List<HexSpaceEntity> elDorado = new ArrayList<>();
                    path.get("elDoradoSpaces").forEach(
                            elDor -> elDorado.add(hexSpaceRepository.findByHexID(elDor.asText()))
                    );

                    ObjectMapper mapper = new ObjectMapper();
                    ObjectReader reader = mapper.readerFor(new TypeReference<List<Integer>>() {});
                    List<Integer> list = reader.readValue(path.get("tileRot"));
                    System.out.println(list);
                    boardRepository.save(
                            new BoardEntity(
                                    path.get("id").asInt(),
                                    path.get("name").asText(),
                                    tiles,
                                    reader.readValue(path.get("tileRot")),
                                    reader.readValue(path.get("tileX")),
                                    reader.readValue(path.get("tileY")),
                                    strips,
                                    reader.readValue(path.get("stripRot")),
                                    reader.readValue(path.get("stripX")),
                                    reader.readValue(path.get("stripY")),
                                    reader.readValue(path.get("blockadeIds")),
                                    reader.readValue(path.get("blockadeOrient")),
                                    reader.readValue(path.get("blockadeX")),
                                    reader.readValue(path.get("blockadeY")),
                                    ending,
                                    reader.readValue(path.get("endSpacesX")),
                                    reader.readValue(path.get("endSpacesY")),
                                    elDorado,
                                    reader.readValue(path.get("elDoradoSpacesX")),
                                    reader.readValue(path.get("elDoradoSpacesY"))
                    ));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private JsonNode getJsonNode(String readLine) throws IOException {
        ObjectMapper HexSpacemapper = new ObjectMapper();
        return HexSpacemapper.readTree(readLine);
    }

    private BufferedReader getBufferedReader(String s) throws FileNotFoundException {
        Resource resource = new ClassPathResource(s);
        try {
            InputStream dbAsStream = resource.getInputStream();
            return new BufferedReader(new InputStreamReader(dbAsStream, "UTF-8"));

        } catch (Exception e){
            System.out.println("Could not find file");
            return null;
        }
        //File f = new File(classLoader.getResource(s).getFile());
    }


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
