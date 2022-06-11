package advent.configuration;


import advent.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean("newuser")
    public User createNewUser(){
        return new User();
    }
}
