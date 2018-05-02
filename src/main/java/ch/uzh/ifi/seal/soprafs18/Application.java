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
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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


    @Bean
    CommandLineRunner savePathElementsToDB(TileService tileService){
        return args -> {
            // read files and write to db
            ClassLoader classLoader = getClass().getClassLoader();
            String readLine = "";
            //-----SAVE-HEXSPACES-----
            try {
                BufferedReader b = getBufferedReader(classLoader, "json/hexspaces.txt");
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
                BufferedReader b = getBufferedReader(classLoader, "json/blockadespaces.txt");
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
                BufferedReader b = getBufferedReader(classLoader, "json/tiles.txt");
                while ((readLine = b.readLine()) != null) {
                    JsonNode tile = getJsonNode(readLine);
                    System.out.println(tile.get("id"));
                    List<HexSpaceEntity> HexSpaces_Tile = new ArrayList<>();
                    System.out.println(tile.get("hexspaces"));
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
                BufferedReader b = getBufferedReader(classLoader, "json/strips.txt");
                while ((readLine = b.readLine()) != null) {
                    JsonNode strip = getJsonNode(readLine);
                    System.out.println(strip.get("id"));
                    List<HexSpaceEntity> HexSpaces_Strip = new ArrayList<>();
                    System.out.println(strip.get("hexspaces"));
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
                BufferedReader b = getBufferedReader(classLoader, "json/paths.txt");
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
                                    reader.readValue(path.get("bloackeIds")),
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

            //enter HexSpaces
            /*
            hexSpaceRepository.save(new HexSpaceEntity("J1", "JUNGLE", 1));
            hexSpaceRepository.save(new HexSpaceEntity("J2", "JUNGLE", 2));
            hexSpaceRepository.save(new HexSpaceEntity("J3", "JUNGLE", 3));
            hexSpaceRepository.save(new HexSpaceEntity("W1", "RIVER", 1));
            hexSpaceRepository.save(new HexSpaceEntity("W2", "RIVER", 2));
            hexSpaceRepository.save(new HexSpaceEntity("W3", "RIVER", 3));
            hexSpaceRepository.save(new HexSpaceEntity("W4", "RIVER", 4));
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

            //BlokadeSpaces
            blockadeSpaceRepository.save(new BlockadeSpaceEntity("BJ1","JUNGLE",1,1));
            blockadeSpaceRepository.save(new BlockadeSpaceEntity("BJ2","JUNGLE",2,2));
            blockadeSpaceRepository.save(new BlockadeSpaceEntity("BW1","RIVER",1,3));
            blockadeSpaceRepository.save(new BlockadeSpaceEntity("BS1","SAND",1,4));
            blockadeSpaceRepository.save(new BlockadeSpaceEntity("RB1","RUBBLE",1,5));
            blockadeSpaceRepository.save(new BlockadeSpaceEntity("BR2","RUBBLE",2,6));

            System.out.println("Applicationos buildos tilete A");

            //Tile A
            List<HexSpaceEntity> HexSpaces_TileA = new ArrayList<>();
            String[] HexSpaceIds_TileA = {"J1","S1","J1","J1","J1", "J1","ST","ST","ST","ST","J1","J1","J1","J1","W1","J1","B1","J1",
                    "J1","J1","S1","W1","J1","J1","J1","J1","J1","S1","M","M",
                    "J1", "J1", "J1", "S1", "J1", "S1",
                    "W1"};
            for (String id : HexSpaceIds_TileA) {
                HexSpaces_TileA.add(hexSpaceRepository.findByHexID(id));
            }
            tileRepository.save(new TileEntity('A', HexSpaces_TileA));

            //Tile B
            List<HexSpaceEntity> HexSpaces_TileB = new ArrayList<>();
            String[] HexSpaceIds_TileB = {"W1","J1","J1","J1","J1","J1","ST","ST","ST","ST","J1","J1","W1","J1","J1","J1","W1","B1",
                    "M","J1","J1","J1","J1","J1","J1","J1","J1","S1","J1","S1",
                    "J1","S1","J1","W1","S1","J1",
                    "J1"};
            for (String id : HexSpaceIds_TileB) {
                HexSpaces_TileB.add(hexSpaceRepository.findByHexID(id));
            }
            tileRepository.save(new TileEntity('B', HexSpaces_TileB));

            //Tile C
            List<HexSpaceEntity> HexSpaces_TileC = new ArrayList<>();
            String[] HexSpaceIds_TileC = {"R1","W1","W1","R1","S1","W1","W1","W1","J1","J1","S1","S1","W1","W1","J1","J1","J1","R1",
                    "W1","R1","R1","S1","S1","J1","R1","R1","S1","W1","S1","R1",
                    "S1","W1","W1","W1","R1","S1",
                    "M"};
            for (String id : HexSpaceIds_TileC) {
                HexSpaces_TileC.add(hexSpaceRepository.findByHexID(id));
            }
            tileRepository.save(new TileEntity('C', HexSpaces_TileC));

            //Tile D
            List<HexSpaceEntity> HexSpaces_TileD = new ArrayList<>();
            String[] HexSpaceIds_TileD = {"W3","M","J1","J2","J1","J1","J2","J1","J1","J1","J1","J1","J2","J1","J1","J2","J1","M",
                    "S1","S3","J1","W1","W1","W1","W1","W1","W1","W1","J1","S3",
                    "J1","W2","W1","W2","J1","M",
                    "M"};
            for (String id : HexSpaceIds_TileD) {
                HexSpaces_TileD.add(hexSpaceRepository.findByHexID(id));
            }
            tileRepository.save(new TileEntity('D', HexSpaces_TileD));

            //Tile E
            List<HexSpaceEntity> HexSpaces_TileE = new ArrayList<>();
            String[] HexSpaceIds_TileE = {"S1","S1","J1","R1","J1","J1","J1","R1","R1","M","R1","J1","J1","R1","J1","B1","S1","S1",
                    "M","W1","J2","M","W2","J2","M","R1","J2","J1","J2","J1",
                    "W1","J1","R1","R3","J3","M",
                    "W1"};
            for (String id : HexSpaceIds_TileE) {
                HexSpaces_TileE.add(hexSpaceRepository.findByHexID(id));
            }
            tileRepository.save(new TileEntity('E', HexSpaces_TileE));

            //Tile F
            List<HexSpaceEntity> HexSpaces_TileF = new ArrayList<>();
            String[] HexSpaceIds_TileF = {"W1","W1","R1","R1","R1","W2","B2","R1","J2","B1","J1","R1","R1","R1","J2","J1","M","M",
                    "W1","W1","J1","J1","W2","J1","J3","S1","S1","J1","J1","M",
                    "W3","J2","J1","R2","S2","W2",
                    "M"};
            for (String id : HexSpaceIds_TileF) {
                HexSpaces_TileF.add(hexSpaceRepository.findByHexID(id));
            }
            tileRepository.save(new TileEntity('F', HexSpaces_TileF));

            //Tile G
            List<HexSpaceEntity> HexSpaces_TileG = new ArrayList<>();
            String[] HexSpaceIds_TileG = {"J1","J1","J1","B1","J1","J1","J1","J1","J1","J1","J1","S1","M","S1","J1","J1","J1","J1",
                    "M","S1","J2","S1","M","S1","J2","S2","M","S2","J2","S1",
                    "R1","S2","R1","S2","S4","S2",
                    "S3"};
            for (String id : HexSpaceIds_TileG) {
                HexSpaces_TileG.add(hexSpaceRepository.findByHexID(id));
            }
            tileRepository.save(new TileEntity('G', HexSpaces_TileG));

            //Tile H
            List<HexSpaceEntity> HexSpaces_TileH = new ArrayList<>();
            String[] HexSpaceIds_TileH = {"W2","W2","W2","J1","J2","J2","J2","J2","J2","J1","S1","S2","S3","S2","S1","W1","W2","W2",
                    "W1","W1","J1","J1","J1","J1","J1","S2","M","S2","W1","W1",
                    "S1","S1","S1","S1","S2","S1",
                    "S2"};

            for (String id : HexSpaceIds_TileH) {
                HexSpaces_TileH.add(hexSpaceRepository.findByHexID(id));
            }
            tileRepository.save(new TileEntity('H', HexSpaces_TileH));

            //Tile I
            List<HexSpaceEntity> HexSpaces_TileI = new ArrayList<>();
            String[] HexSpaceIds_TileI = {"J1","J1","J1","M","J1","J1","J1","W1","W2","W2","W2","S2","S1","S1","S1","J1","J1","J1",
                    "J1","J2","M","J2","J1","W1","W1","R3","S2","S2","J1","M",
                    "M","B3","M","M","J1","J1",
                    "J2"};
            for (String id : HexSpaceIds_TileI) {
                HexSpaces_TileI.add(hexSpaceRepository.findByHexID(id));
            }
            tileRepository.save(new TileEntity('I', HexSpaces_TileI));

            //Tile J
            List<HexSpaceEntity> HexSpaces_TileJ = new ArrayList<>();
            String[] HexSpaceIds_TileJ = {"W1","R2","R1","R1","R1","R1","R2","S1","S1","S1","S1","S1","S1","W1","W1","W1","W1","W1",
                    "W1","R2","R2","R2","M","S2","S2","S2","S1","W2","M","W2",
                    "J2","J1","J1","J2","J2","J1",
                    "B1"};
            for (String id : HexSpaceIds_TileJ) {
                HexSpaces_TileJ.add(hexSpaceRepository.findByHexID(id));
            }
            tileRepository.save(new TileEntity('J', HexSpaces_TileJ));

            //Tile K
            List<HexSpaceEntity> HexSpaces_TileK = new ArrayList<>();
            String[] HexSpaceIds_TileK = {"J1","J2","J2","J2","J1","J1","B1","J2","J2","J1","J2","J2","J2","J1","J1","B1","J2","J2",
                    "J1","J1","J1","J2","J1","S4","J1","J1","J1","J2","J1","W3",
                    "J3","J3","J1","J3","J3","J1",
                    "J1"};
            for (String id : HexSpaceIds_TileK) {
                HexSpaces_TileK.add(hexSpaceRepository.findByHexID(id));
            }
            tileRepository.save(new TileEntity('K', HexSpaces_TileK));

            //Tile L
            List<HexSpaceEntity> HexSpaces_TileL = new ArrayList<>();
            String[] HexSpaceIds_TileL = {"J3","J1","J2","J2","J1","J1","M","J1","J2","J2","S2","J1","J2","J2","J1","W1","B1","B1",
                    "J3","J1","J1","J2","J1","S2","B2","J2","J1","J1","W1","W1",
                    "J3","M","J1","J2","M","J1",
                    "J1"};
            for (String id : HexSpaceIds_TileL) {
                HexSpaces_TileL.add(hexSpaceRepository.findByHexID(id));
            }
            tileRepository.save(new TileEntity('L', HexSpaces_TileL));

            //Tile M
            List<HexSpaceEntity> HexSpaces_TileM = new ArrayList<>();
            String[] HexSpaceIds_TileM = {"B1","W4","M","M","J1","J1","J1","J1","W1","W1","J1","M","M","J1","J1","J1","J1","J1",
                    "M","W1","J1","R2","R2","J1","J1","M","J1","S2","S4","M",
                    "J1","J1","M","M","J1","J1",
                    "R2"};
            for (String id : HexSpaceIds_TileM) {
                HexSpaces_TileM.add(hexSpaceRepository.findByHexID(id));
            }
            tileRepository.save(new TileEntity('M', HexSpaces_TileM));

            //Tile N
            List<HexSpaceEntity> HexSpaces_TileN = new ArrayList<>();
            String[] HexSpaceIds_TileN = {"J1","W1","W1","S1","S1","J1","J1","J1","J1","J1","J1","W1","W1","S1","S1","J1","J1","J1",
                    "J1","W1","S2","S2","J1","J2","J1","W1","W1","S2","J1","J2",
                    "W1","S3","J1","W1","S3","J1",
                    "S4"};
            for (String id : HexSpaceIds_TileN) {
                HexSpaces_TileN.add(hexSpaceRepository.findByHexID(id));
            }
            tileRepository.save(new TileEntity('N', HexSpaces_TileN));

            //-------------------------------------
            //Strip O
            List<HexSpaceEntity> HexSpaces_StripO = new ArrayList<>();
            String[] HexSpaceIds_StripO = {"S1","S2","S1","R1","J2","R2","R1","R1","J1","J2","W1","S1",
                    "M","W4","M","M"};
            for (String id : HexSpaceIds_StripO) {
                HexSpaces_StripO.add(hexSpaceRepository.findByHexID(id));
            }
            stripRepository.save(new StripEntity('O', HexSpaces_StripO));

            //Strip P
            List<HexSpaceEntity> HexSpaces_StripP = new ArrayList<>();
            String[] HexSpaceIds_StripP = {"R1","W4","S1","S1","W1","J3","J2","W2","J1","J1","R3","J1",
                    "R1","J2","S3","W1"};
            for (String id : HexSpaceIds_StripP) {
                HexSpaces_StripP.add(hexSpaceRepository.findByHexID(id));
            }
            stripRepository.save(new StripEntity('P', HexSpaces_StripP));

            //Strip Q
            List<HexSpaceEntity> HexSpaces_StripQ = new ArrayList<>();
            String[] HexSpaceIds_StripQ = {"J2","R1","S1","S1","W1","J3","J2","W2","J1","J1","R3","J1",
                    "R1","J2","S3","W1"};
            for (String id : HexSpaceIds_StripQ) {
                HexSpaces_StripQ.add(hexSpaceRepository.findByHexID(id));
            }
            stripRepository.save(new StripEntity('Q', HexSpaces_StripQ));

            //Strip R
            List<HexSpaceEntity> HexSpaces_StripR = new ArrayList<>();
            String[] HexSpaceIds_StripR = {"J1","J1","J1","M","S1","S1","S1","S1","S1","M","J1","J1",
                    "J3","M","R1","B1"};
            for (String id : HexSpaceIds_StripR) {
                HexSpaces_StripR.add(hexSpaceRepository.findByHexID(id));
            }
            stripRepository.save(new StripEntity('R', HexSpaces_StripR));

            //----------------------DEFAULT PATH----------------------
            List<TileEntity> tiles_defaultPath = new ArrayList<>();
            char [] tile = {'B','C','N','I','K'};
            for (char id : tile) {
                tiles_defaultPath.add(tileRepository.findByTileID(id));
            }

            List<Integer> tileRotation_defaultPath = new ArrayList<>();
            int [] tileRot = {3,0,3,3,5};
            for (int rot : tileRot) {
                tileRotation_defaultPath.add(rot);
            }

            List<Integer> tilePositionsX_defaultPath = new ArrayList<>();
            int [] tilePosX = {4,8,16,24,27};
            for (int pos : tilePosX) {
                tilePositionsX_defaultPath.add(pos);
            }

            List<Integer> tilePositionsY_defaultPath = new ArrayList<>();
            int [] tilePosY = {4,10,11,10,16};
            for (int id : tilePosY) {
                tilePositionsY_defaultPath.add(id);
            }

            List<Integer> blockadeX_defaultPath = new ArrayList<>();
            int [] blockadeX = {5,6,7,8,12,12,12,12,20,20,20,20,24,25,26,27};
            for (int posX : blockadeX) {
                blockadeX_defaultPath.add(posX);
            }

            List<Integer> blockadeIDs_defaultPath = new ArrayList<>();
            int [] blockadeId = {0,0,0,0,1,1,1,1,2,2,2,2,3,3,3,3};
            for (int id : blockadeId) {
                blockadeIDs_defaultPath.add(id);
            }

            List<Integer> blockadeY_defaultPath = new ArrayList<>();
            int [] blockadeY = {7,7,6,6,9,10,11,12,9,10,11,12,14,13,13,12};
            for (int posY : blockadeY) {
                blockadeY_defaultPath.add(posY);
            }

            List<HexSpaceEntity> EndSpaces_defaultPath = new ArrayList<>();
            String[] EndSpacesId_defaultPath = {"EW","EW","EW"};
            for (String id : EndSpacesId_defaultPath) {
                EndSpaces_defaultPath.add(hexSpaceRepository.findByHexID(id));
            }

            List<Integer> EndSpacesX_defaultPath = new ArrayList<>();
            int [] EndSpacesX = {26,27,28};
            for (int posX : EndSpacesX) {
                EndSpacesX_defaultPath.add(posX);
            }
            List<Integer> EndSpacesY_defaultPath = new ArrayList<>();
            int [] EndSpacesY = {20,20,20};
            for (int posY : EndSpacesY) {
                EndSpacesY_defaultPath.add(posY);
            }

            List<HexSpaceEntity> EldoradoSpaces_defaultPath = new ArrayList<>();
            String[] EldoradoSpacesId_defaultPath = {"ED","ED","ED"};
            for (String id : EldoradoSpacesId_defaultPath) {
                EldoradoSpaces_defaultPath.add(hexSpaceRepository.findByHexID(id));
            }

            List<Integer> EldoradoSpacesX_defaultPath = new ArrayList<>();
            int [] EldoradoSpacesX = {26,27,28};
            for (int posX : EldoradoSpacesX) {
                EldoradoSpacesX_defaultPath.add(posX);
            }
            List<Integer> EldoradoSpacesY_defaultPath = new ArrayList<>();
            int [] EldoradoSpacesY = {21,21,21};
            for (int posY : EldoradoSpacesY) {
                EldoradoSpacesY_defaultPath.add(posY);
            }

            boardRepository.save(new BoardEntity(0, "Quest for Eldorado", tiles_defaultPath, tileRotation_defaultPath, tilePositionsX_defaultPath,
                    tilePositionsY_defaultPath, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    blockadeIDs_defaultPath, blockadeX_defaultPath, blockadeY_defaultPath,
                    EndSpaces_defaultPath, EndSpacesX_defaultPath, EndSpacesY_defaultPath,
                    EldoradoSpaces_defaultPath, EldoradoSpacesX_defaultPath, EldoradoSpacesY_defaultPath));*/

            //----------------------HILLSOFGOLD PATH----------------------

            List<TileEntity> tiles_HillsOfGoldPath = new ArrayList<>();
            char [] tile_HillsOfGoldPath = {'B','C','G','K','J','N'};
            for (char id : tile_HillsOfGoldPath) {
                tiles_HillsOfGoldPath.add(tileRepository.findByTileID(id));
            }

            List<Integer> tileRotation_HillsOfGoldPath = new ArrayList<>();
            int [] tileRot_HillsOfGoldPath = {3,2,0,3,3,0};
            for (int rot : tileRot_HillsOfGoldPath) {
                tileRotation_HillsOfGoldPath.add(rot);
            }

            List<Integer> tilePositionsX_HillsOfGoldPath = new ArrayList<>();
            int [] tilePosX_HillsOfGoldPath = {4,7,11,15,18,26};
            for (int pos : tilePosX_HillsOfGoldPath) {
                tilePositionsX_HillsOfGoldPath.add(pos);
            }

            List<Integer> tilePositionsY_HillsOfGoldPath = new ArrayList<>();
            int [] tilePosY_HillsOfGoldPath = {5,11,17,11,5,4};
            for (int id : tilePosY_HillsOfGoldPath) {
                tilePositionsY_HillsOfGoldPath.add(id);
            }

            List<Integer> blockadeX_HillsOfGoldPath = new ArrayList<>();
            int [] blockadesX_HillsOfGoldPath = {4,5,6,7,7,8,9,10,12,13,14,15,15,16,17,18,22,22,22,22};
            for (int posX : blockadesX_HillsOfGoldPath) {
                blockadeX_HillsOfGoldPath.add(posX);
            }

            List<Integer> blockadeIDs_HillsOfGoldPath = new ArrayList<>();
            int [] blockadeId_HillsOfGoldPath = {0,0,0,0,1,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4};
            for (int id : blockadeId_HillsOfGoldPath) {
                blockadeIDs_HillsOfGoldPath.add(id);
            }

            List<Integer> blockadeY_HillsOfGoldPath = new ArrayList<>();
            int [] blockadesY_HillsOfGoldPath = {9,8,8,7,15,15,14,14,14,14,15,15,7,8,8,9,3,4,5,6};
            for (int posY : blockadesY_HillsOfGoldPath) {
                blockadeY_HillsOfGoldPath.add(posY);
            }

            List<HexSpaceEntity> EndSpaces_HillsOfGoldPath = new ArrayList<>();
            String[] EndSpacesId_HillsOfGoldPath = {"EJ","EJ","EJ"};
            for (String id : EndSpacesId_HillsOfGoldPath) {
                EndSpaces_HillsOfGoldPath.add(hexSpaceRepository.findByHexID(id));
            }

            List<Integer> EndSpacesX_HillsOfGoldPath = new ArrayList<>();
            int [] EndSpaceX_HillsOfGoldPath = {29,30,30};
            for (int posX : EndSpaceX_HillsOfGoldPath) {
                EndSpacesX_HillsOfGoldPath.add(posX);
            }
            List<Integer> EndSpacesY_HillsOfGoldPath = new ArrayList<>();
            int [] EndSpaceY_HillsOfGoldPath = {1,2,3};
            for (int posY : EndSpaceY_HillsOfGoldPath) {
                EndSpacesY_HillsOfGoldPath.add(posY);
            }

            List<HexSpaceEntity> EldoradoSpaces_HillsOfGoldPath = new ArrayList<>();
            String[] EldoradoSpacesId_HillsOfGoldPath = {"ED","ED","ED"};
            for (String id : EldoradoSpacesId_HillsOfGoldPath) {
                EldoradoSpaces_HillsOfGoldPath.add(hexSpaceRepository.findByHexID(id));
            }

            List<Integer> EldoradoSpacesX_HillsOfGoldPath = new ArrayList<>();
            int [] EldoradoSpaceX_HillsOfGoldPath = {30,31,31};
            for (int posX : EldoradoSpaceX_HillsOfGoldPath) {
                EldoradoSpacesX_HillsOfGoldPath.add(posX);
            }
            List<Integer> EldoradoSpacesY_HillsOfGoldPath = new ArrayList<>();
            int [] EldoradoSpaceY_HillsOfGoldPath = {1,1,2};
            for (int posY : EldoradoSpaceY_HillsOfGoldPath) {
                EldoradoSpacesY_HillsOfGoldPath.add(posY);
            }

            boardRepository.save(new BoardEntity(1,"Hills of Gold", tiles_HillsOfGoldPath, tileRotation_HillsOfGoldPath, tilePositionsX_HillsOfGoldPath,
                    tilePositionsY_HillsOfGoldPath, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    blockadeIDs_HillsOfGoldPath, blockadeX_HillsOfGoldPath, blockadeY_HillsOfGoldPath,
                    EndSpaces_HillsOfGoldPath, EndSpacesX_HillsOfGoldPath, EndSpacesY_HillsOfGoldPath,
                    EldoradoSpaces_HillsOfGoldPath, EldoradoSpacesX_HillsOfGoldPath, EldoradoSpacesY_HillsOfGoldPath));


            //----------------------HOMESTRETCH PATH----------------------

            List<TileEntity> tiles_HomeStretchPath = new ArrayList<>();
            char [] tile_HomeStretchPath = {'B','J','K','M','C'};
            for (char id : tile_HomeStretchPath) {
                tiles_HillsOfGoldPath.add(tileRepository.findByTileID(id));
            }

            List<Integer> tileRotation_HomeStretchPath = new ArrayList<>();
            int [] tileRot_HomeStretchPath = {3,1,0,2,0,2};
            for (int rot : tileRot_HomeStretchPath) {
                tileRotation_HomeStretchPath.add(rot);
            }

            List<Integer> tilePositionsX_HomeStretchPath = new ArrayList<>();
            int [] tilePosX_HomeStretchPath = {4,9,20,18,36};
            for (int pos : tilePosX_HomeStretchPath) {
                tilePositionsX_HomeStretchPath.add(pos);
            }

            List<Integer> tilePositionsY_HomeStretchPath = new ArrayList<>();
            int [] tilePosY_HomeStretchPath = {4,10,10,11,10};
            for (int id : tilePosY_HomeStretchPath) {
                tilePositionsY_HomeStretchPath.add(id);
            }

            List<StripEntity> strip_HomeStretchPath = new ArrayList<>();
            char [] strips_HomeStretchPath = {'Q'};
            for (char id : strips_HomeStretchPath) {
                strip_HomeStretchPath.add(stripRepository.findByStripID(id));
            }

            List<Integer> stripRotation_HomeStretchPath = new ArrayList<>();
            int [] stripRot_HomeStretchPath = {0};
            for (int rot : stripRot_HomeStretchPath) {
                stripRotation_HomeStretchPath.add(rot);
            }

            List<Integer> stripPositionsX_HomeStretchPath = new ArrayList<>();
            int [] stripPosX_HomeStretchPath = {14};
            for (int pos : stripPosX_HomeStretchPath) {
                stripPositionsX_HomeStretchPath.add(pos);
            }

            List<Integer> stripPositionsY_HomeStretchPath = new ArrayList<>();
            int [] stripPosY_HomeStretchPath = {8};
            for (int id : stripPosY_HomeStretchPath) {
                stripPositionsY_HomeStretchPath.add(id);
            }

            List<Integer> blockadeX_HomeStretchPath = new ArrayList<>();
            int [] blockadesX_HomeStretchPath = {4,5,6,7,12,12,12,12,16,16,16,16,24,24,24,24,32,32,32,32};
            for (int posX : blockadesX_HomeStretchPath) {
                blockadeX_HomeStretchPath.add(posX);
            }

            List<Integer> blockadeIDs_HomeStretchPath = new ArrayList<>();
            int [] blockadeId_HomeStretchPath = {0,0,0,0,1,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4};
            for (int id : blockadeId_HomeStretchPath) {
                blockadeIDs_HomeStretchPath.add(id);
            }

            List<Integer> blockadeY_HomeStretchPath = new ArrayList<>();
            int [] blockadesY_HomeStretchPath = {8,7,7,6,8,9,10,11,8,9,10,11,9,10,11,12,9,10,11,12};
            for (int posY : blockadesY_HomeStretchPath) {
                blockadeY_HomeStretchPath.add(posY);
            }

            List<HexSpaceEntity> EndSpaces_HomeStretchPath = new ArrayList<>();
            String[] EndSpacesId_HomeStretchPath = {"EJ","EJ","EJ"};
            for (String id : EndSpacesId_HomeStretchPath) {
                EndSpaces_HomeStretchPath.add(hexSpaceRepository.findByHexID(id));
            }

            List<Integer> EndSpacesX_HomeStretchPath = new ArrayList<>();
            int [] EndSpaceX_HomeStretchPath = {39,40,40};
            for (int posX : EndSpaceX_HomeStretchPath) {
                EndSpacesX_HomeStretchPath.add(posX);
            }
            List<Integer> EndSpacesY_HomeStretchPath = new ArrayList<>();
            int [] EndSpaceY_HomeStretchPath = {12,12,11};
            for (int posY : EndSpaceY_HomeStretchPath) {
                EndSpacesY_HomeStretchPath.add(posY);
            }

            List<HexSpaceEntity> EldoradoSpaces_HomeStretchPath = new ArrayList<>();
            String[] EldoradoSpacesId_HomeStretchPath = {"ED","ED","ED"};
            for (String id : EldoradoSpacesId_HomeStretchPath) {
                EldoradoSpaces_HomeStretchPath.add(hexSpaceRepository.findByHexID(id));
            }

            List<Integer> EldoradoSpacesX_HomeStretchPath = new ArrayList<>();
            int [] EldoradoSpaceX_HomeStretchPath = {40,41,41};
            for (int posX : EldoradoSpaceX_HomeStretchPath) {
                EldoradoSpacesX_HomeStretchPath.add(posX);
            }
            List<Integer> EldoradoSpacesY_HomeStretchPath = new ArrayList<>();
            int [] EldoradoSpaceY_HomeStretchPath = {13,13,11};
            for (int posY : EldoradoSpaceY_HomeStretchPath) {
                EldoradoSpacesY_HomeStretchPath.add(posY);
            }

            boardRepository.save(new BoardEntity(2, "Home Stretch", tiles_HomeStretchPath, tileRotation_HomeStretchPath, tilePositionsX_HomeStretchPath,
                    tilePositionsY_HomeStretchPath, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    blockadeIDs_HomeStretchPath, blockadeX_HomeStretchPath, blockadeY_HomeStretchPath,
                    EndSpaces_HomeStretchPath, EndSpacesX_HomeStretchPath, EndSpacesY_HomeStretchPath,
                    EldoradoSpaces_HomeStretchPath, EldoradoSpacesX_HomeStretchPath, EldoradoSpacesY_HomeStretchPath));
            /*
            //----------------------TEST PATH----------------------------------------
            List<Integer> tileRotation_testPath = new ArrayList<>();
            int [] tileRot_test = {5,0,3,3,5};
            for (int rot : tileRot_test) {
                tileRotation_testPath.add(rot);
            }


            boardRepository.save(new BoardEntity(98, "Test 98", tiles_defaultPath, tileRotation_testPath, tilePositionsX_defaultPath,
                    tilePositionsY_defaultPath, strip_HomeStretchPath, stripRotation_HomeStretchPath,
                    stripPositionsX_HomeStretchPath, stripPositionsY_HomeStretchPath,
                    blockadeIDs_defaultPath, blockadeX_defaultPath, blockadeY_defaultPath,
                    EndSpaces_defaultPath, EndSpacesX_defaultPath, EndSpacesY_defaultPath,
                    EldoradoSpaces_defaultPath, EldoradoSpacesX_defaultPath, EldoradoSpacesY_defaultPath));

            //----------------------TEST PATH----------------------------------------
            List<TileEntity> tiles_demoPath = new ArrayList<>();
            char [] tiles = {'K','C','N','I','B'};
            for (char id : tiles) {
                tiles_demoPath.add(tileRepository.findByTileID(id));
            }

            List<Integer> tileRotation_demoPath = new ArrayList<>();
            int [] tileRot_demo = {5,0,3,3,4};
            for (int rot : tileRot_demo) {
                tileRotation_demoPath.add(rot);
            }


            boardRepository.save(new BoardEntity(99,"Test 99", tiles_demoPath, tileRotation_demoPath, tilePositionsX_defaultPath,
                    tilePositionsY_defaultPath, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    blockadeIDs_defaultPath, blockadeX_defaultPath, blockadeY_defaultPath,
                    EndSpaces_defaultPath, EndSpacesX_defaultPath, EndSpacesY_defaultPath,
                    EldoradoSpaces_defaultPath, EldoradoSpacesX_defaultPath, EldoradoSpacesY_defaultPath));*/

        };
    }

    private JsonNode getJsonNode(String readLine) throws IOException {
        ObjectMapper HexSpacemapper = new ObjectMapper();
        return HexSpacemapper.readTree(readLine);
    }

    private BufferedReader getBufferedReader(ClassLoader classLoader, String s) throws FileNotFoundException {
        File f = new File(classLoader.getResource(s).getFile());
        return new BufferedReader(new FileReader(f));
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
