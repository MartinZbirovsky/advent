package advent.repository;

import advent.model.Ads;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdsRepositoryTest {
/*
    @Autowired
    private AdsRepository adsRepository;

    @Test
    void testCreateAds() {
        Ads ads = new Ads("test");
        adsRepository.save(ads);

        List<Ads> ads2 = adsRepository.findByNameContaining("test");
        assertThat(ads2).isNotNull();
    }*/
}