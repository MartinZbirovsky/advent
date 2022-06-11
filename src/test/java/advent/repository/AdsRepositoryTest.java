package advent.repository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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