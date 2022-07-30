package advent.configuration;

import advent.dto.requestDto.CreateAdDto;
import advent.dto.requestDto.GlobalInfoMessageDto;
import advent.dto.requestDto.RegistrationReqDto;
import advent.dto.responseDto.AdsHomeResDto;
import advent.enums.StateAds;
import advent.enums.WorkTypeAds;
import advent.model.*;
import advent.service.impl.RegisterServiceImpl;
import advent.service.impl.UserServiceImpl;
import advent.service.intf.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * Class InitDataConfiguration is used for creating demo data in DB.
 */
@Configuration
public class InitDataConfiguration {

    @Bean
    CommandLineRunner run (UserServiceImpl userService,
                           AdsService adsService,
                           CategoryService categoryService,
                           BenefitService benefitService,
                           GlobalInfoMessageService globalInfoMessageService,
                           RegisterServiceImpl registerService
                          ){
        return args -> {
            /////////////////////////Core data/////////////////////////
            userService.saveRole(new Role("ROLE_USER"));
            userService.saveRole(new Role("ROLE_ADMIN"));
            userService.saveRole(new Role("ROLE_SUPER_ADMIN"));

            categoryService.addNew(new Category("IT"));
            categoryService.addNew(new Category("AUTO"));
            categoryService.addNew(new Category("ECO"));
            categoryService.addNew(new Category("OTHER"));

            benefitService.addNew(new Benefit("007"));
            benefitService.addNew(new Benefit("Home"));
            benefitService.addNew(new Benefit("Vegetable"));

            globalInfoMessageService.addNew(new GlobalInfoMessageDto("DOOM"));
            globalInfoMessageService.addNew(new GlobalInfoMessageDto("Raining"));
            globalInfoMessageService.addNew(new GlobalInfoMessageDto("Moon fall"));
            ///////////////////////////////////////////////////////////

            registerService.registerUser(new RegistrationReqDto("Pepa", "Zdepa", "1neco@neco.cz", "neco1"));
            registerService.registerUser(new RegistrationReqDto("Pepa", "Zdepa", "2neco@neco.cz", "neco1"));
            registerService.registerUser(new RegistrationReqDto("Pepa", "Zdepa", "3neco@neco.cz", "neco1"));
            registerService.registerUser(new RegistrationReqDto("Pepa", "Zdepa", "4neco@neco.cz", "neco1"));
            registerService.registerUser(new RegistrationReqDto("Pepa", "Zdepa", "lolburhehe@seznam.cz", "neco1"));

            userService.addRoleToUse("1neco@neco.cz", "ROLE_USER");
            userService.addRoleToUse("3neco@neco.cz", "ROLE_ADMIN");
            userService.addRoleToUse("4neco@neco.cz", "ROLE_SUPER_ADMIN");
            userService.addRoleToUse("lolburhehe@seznam.cz", "ROLE_ADMIN");

            Payment firstCharge = new Payment(null, "Charge for 200", "959599", new BigDecimal(100), null, null);

            userService.chargeMoney("lolburhehe@seznam.cz", firstCharge);
            User userToUpdate = userService.getUserByEmail("lolburhehe@seznam.cz");
            userToUpdate.getFirstAddress().setStreet("Olomoucka");
            userToUpdate.getFirstAddress().setCity("OL");
            userToUpdate.getSecondAddress().setStreet("Brnenska");
            userToUpdate.getSecondAddress().setCity("Brno");
            userService.editUser(userToUpdate);

            adsService.addNew(new CreateAdDto("Neco", "Tester", "Java", "car", 5000L,
                    20000L,"OL, Olomouc")
                    , "lolburhehe@seznam.cz");

            //Assign Benefits
            Benefit benefitToAdd1 = benefitService.getBenefitByName("Home");
            AdsHomeResDto adsBeforeAddBenefit = adsService.getAll(0,1, "id").getContent().get(0);
            adsService.addBenefit(benefitToAdd1.getId(), adsBeforeAddBenefit.getId());
        };
    }
}