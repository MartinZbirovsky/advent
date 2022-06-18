package advent.configuration;

import advent.model.*;
import advent.service.Interface.AdsService;
import advent.service.Interface.BenefitService;
import advent.service.Interface.CategoryService;
import advent.service.Interface.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

@Configuration
public class InitDataConfiguration {

    @Bean
    CommandLineRunner run (UserService userService, AdsService adsService, CategoryService categoryService, BenefitService benefitService){
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new User(null,"1neco@neco.cz", "neco", new HashSet<>(), null, null));
            userService.saveUser(new User(null,"2neco@neco.cz", "neco", new HashSet<>(), null, null));
            userService.saveUser(new User(null,"3neco@neco.cz", "neco", new HashSet<>(), null, null));
            userService.saveUser(new User(null,"4neco@neco.cz", "neco", new HashSet<>(), null, null));
            userService.saveUser(new User(null,"5neco@neco.cz", "neco", new HashSet<>(), null, new BigDecimal(200)));

            userService.addRoleToUse("1neco@neco.cz", "ROLE_USER");
            userService.addRoleToUse("2neco@neco.cz", "ROLE_MANAGER");
            userService.addRoleToUse("3neco@neco.cz", "ROLE_ADMIN");
            userService.addRoleToUse("4neco@neco.cz", "ROLE_SUPER_ADMIN");
            userService.addRoleToUse("5neco@neco.cz", "ROLE_ADMIN");

            categoryService.addNew(new Category(null, "IT", null));
            categoryService.addNew(new Category(null, "AUTO", null));
            categoryService.addNew(new Category(null, "ECO", null));

            adsService.addNew(new Ads(null,"neco", null, null, "Apple",5000L,
                    null,null, null, null, null, null, null, null, null)
                    , "5neco@neco.cz");

            benefitService.addNew(new Benefit(null, "007"));
            benefitService.addNew(new Benefit(null, "Home"));
            benefitService.addNew(new Benefit(null, "Vegetable"));

        };
    }

    /*
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

            List<Ads> newAds = new HashSet<>();
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

            aRepo.saveAll(allADs);
        };
    }*/
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