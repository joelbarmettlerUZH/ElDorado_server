package ch.uzh.ifi.seal.soprafs18;

import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs18.repository.RoomRepository;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner loadDUsers(UserRepository userRepository) {
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
