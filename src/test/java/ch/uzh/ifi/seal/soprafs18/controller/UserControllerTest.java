package ch.uzh.ifi.seal.soprafs18.controller;

import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;


import org.junit.Test;
//@WebMvcTest(UserController.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {


    private MockMvc mvc;

   // @Autowired
    //private MockMvc mvc;

    //@MockBean
    //private UserService userService;

    //@MockBean
    //private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    private JacksonTester<UserEntity> jsonSuperHero;

    @Before
    public void setup() {


        // We would need this line if we would not use MockitoJUnitRunner
        // MockitoAnnotations.initMocks(this);
        // Initializes the JacksonTester
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(userController)
                .build();

        /*j
        RoomEntity room = new RoomEntity();
        UserEntity user = new UserEntity("Testuser",1,room);
        List<UserEntity> users = new ArrayList<UserEntity>();
        users.add(user);
        //Mockito.when(userRepository.findByUserID(user.getUserID())).thenReturn(users);
        //Mockito.when(userRepository.findByUserID(1)).thenReturn(users);
        //Mockito.when(userService.getByID(1)).thenReturn(user);
        //Mockito.when(userService.getByID(0)).thenReturn(user);
*/

    }


    @Test
    public void getUsers() {
    }

    @Test
    public void createUser() {

        /*
        RoomEntity room = new RoomEntity();
        UserController cont = new UserController();
        UserEntity user = new UserEntity("Testuser", 1, room);
        List<String> usernames = new ArrayList<String>();
        usernames.add("Testuser");
        Mockito.when(userService.createUser(user)).thenReturn(usernames);
        cont.createUser(user);*/
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void getUser() {
         UserController cont = new UserController();

         //cont.getUser(1);
    }

    @Test
    public void changeUser() {
    }
}