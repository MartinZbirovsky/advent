package advent.service;

import advent.model.Ads;
import advent.model.Category;
import advent.repository.AdsRepository;
import advent.repository.CategoryRepository;
import advent.service.ServiceInterface.AdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
    private final CategoryRepository catRepository;

    public AdsServiceImpl(AdsRepository adsService, CategoryRepository catRepository){
        this.adsRepository = adsService;
        this.catRepository = catRepository;
    }

    @Override
    public Ads addAds(Ads ads) {
        /*  User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ads.setUser(user);*/
        return adsRepository.save(ads);
    }

    @Override
    public List<Ads> getAllAds() {
        return adsRepository.findAll();
    }

    @Override
    public Ads getAdsById(Long id) {
        return  adsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement" + id + "not found"));
    }

    @Override
    public Ads deleteAds(Long id) {
        Ads ads = adsRepository.findById(id).orElse(null);
        adsRepository.deleteById(ads.getId());
        return ads;
    }

    @Override
    public Ads editAds(Long id, Ads ads) {
        return adsRepository.findById(id)
                .map(ad -> {
                    ad.setName(ads.getName());
                    return adsRepository.save(ad);
                })
                .orElseGet(() -> {
                    ads.setId(id);
                    return adsRepository.save(ads);
                });
    }

    @Transactional
    @Override
    public Ads addCategoryToAds(Long adsId, Long categoryId) {
        Ads ads = adsRepository.findById(categoryId).orElse(null);
        Category category = catRepository.findById(categoryId).orElse(null);

        ads.setCategory(category); //setZipcode(zipcode);
        return ads;
    }

    @Transactional
    @Override
    public Ads deleteCategoryFromAds(Long categoryId) {
        Ads ads = adsRepository.findById(categoryId).orElse(null);
        ads.setCategory(null);
        return ads;
    }
}