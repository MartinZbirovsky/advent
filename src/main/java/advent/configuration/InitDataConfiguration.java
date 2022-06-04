package advent.configuration;

import advent.model.*;
import advent.repository.AdsRepository;
import advent.repository.RoleRepository;
import advent.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InitDataConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(AdsRepository aRepo,
                                        RoleRepository rRepo,
                                        UserRepository uRepo){
        return args -> {

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = passwordEncoder.encode("neco");

            int numUsers = uRepo.findAll().size();
            int numRoles = rRepo.findAll().size();
            int numAds = aRepo.findAll().size();

            List<Role> roles = new ArrayList<>();
            roles.add(new Role("ROLE_ADMIN"));
            roles.add(new Role("ROLE_EDITOR"));
            roles.add(new Role("ROLE_CUSTOMER"));

            // Create roles
            if(numRoles == 0)
                rRepo.saveAll(roles);

            City city = new City("Olomouc");
            Address address = new Address("Olomoucka", city);

            User newUser = new User("neco@neco.cz", password, address, roles);

            List<Ads> newAds = new ArrayList<>();
            newAds.add(new Ads("JAVA"));
            newAds.add(new Ads("C#"));

            Category category = new Category("IT");
            newAds.get(0).setCategory(category);
            newAds.get(1).setCategory(category);

            Benefit benefit1 = new Benefit("13 plat");
            Benefit benefit2 = new Benefit("Home office");

            newAds.get(0).addBenefitToAds(benefit1);
            newAds.get(0).addBenefitToAds(benefit2);
            newAds.get(1).addBenefitToAds(benefit1);


            // Create users
            if(numUsers == 0)
                uRepo.save(newUser);

            //Save ads
            if(numAds == 0)
                aRepo.saveAll(newAds);
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