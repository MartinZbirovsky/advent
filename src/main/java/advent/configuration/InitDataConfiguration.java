package advent.configuration;

import advent.model.*;
import advent.repository.AdsRepository;
import advent.repository.RoleRepository;
import advent.service.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@Configuration
public class InitDataConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(AdsRepository aRepo,
                                        RoleRepository rRepo,
                                        UserServiceImpl uService){
        return args -> {
          ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
            User newUser = context.getBean("newuser", User.class);
            String email = "1@1.cz";

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = passwordEncoder.encode("neco");

            int numUsers = uService.getUsers("", 0, 5, "id").getSize();
            int numRoles = rRepo.findAll().size();
            int numAds = aRepo.findAll().size();

            Set<Role> roles = new HashSet<>();
            roles.add(new Role("ROLE_ADMIN"));
            roles.add(new Role("ROLE_EDITOR"));
            roles.add(new Role("ROLE_CUSTOMER"));

            // Create roles
            if(numRoles == 0)
                rRepo.saveAll(roles);

            Address address = new Address("Olomoucka", "Olomouc");

            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setFirstAddress(address);
            newUser.setRoles(roles);

            List<Ads> newAds = new ArrayList<>();
            newAds.add(new Ads("JAVA"));
            newAds.add(new Ads("C#"));

            Category category = new Category("IT");
            newAds.get(0).setCategory(category);
            newAds.get(1).setCategory(category);

            Benefit benefit1 = new Benefit("13 plat");
            Benefit benefit2 = new Benefit("Home office");

            newAds.get(0).addBenefit(benefit1);
            newAds.get(0).addBenefit(benefit2);
            newAds.get(1).addBenefit(benefit1);


            // Create users
            if(numUsers == 0)
                uService.addNew(newUser);

            //Save ads
            if(numAds == 0)
                aRepo.saveAll(newAds);


           /* User user = uService.findByEmail(email);
            List<Ads> allADs = aRepo.findAll();
            allADs.forEach(e -> e.setUser(user));

            aRepo.saveAll(allADs);*/
        };
    }
}

//@JsonIgnore
   /* @GetMapping("/name")
    public Integer getName(Authentication authentication) {
        Optional<User> user = userRepository.findByEmail(authentication.getName());
        return user.get().getId();

    }*/
// http://localhost:8080/api/ads
// http://localhost:8080/auth/login
// {"email":"neco@neco.cz", "password":"1234"}