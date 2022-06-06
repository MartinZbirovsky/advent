package advent.service;

import advent.model.Ads;
import advent.model.Category;
import advent.model.User;
import advent.repository.AdsRepository;
import advent.repository.CategoryRepository;
import advent.service.serviceinterface.AdsService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class AdsServiceImpl implements AdsService<Ads> {

    private final AdsRepository adsRepository;
    private final CategoryRepository catRepository;
    private final UserServiceImpl userService;

    public AdsServiceImpl(AdsRepository adsService, CategoryRepository catRepository, UserServiceImpl userService){
        this.adsRepository = adsService;
        this.catRepository = catRepository;
        this.userService = userService;
    }


    @Override
    public Ads addCategoryToAds(Long adsId, Long categoryId) {
        Ads ads = adsRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement" + adsId + "not found"));
        Category category = catRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement" + categoryId + "not found"));

        ads.setCategory(category);
        return ads;
    }

    @Transactional
    @Override
    public Ads deleteCategoryFromAds(Long adsId) {
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement" + adsId + "not found"));
        ads.setCategory(null);
        return ads;
    }

    @Override
    public Ads addNew(Ads entityBody) {
        /*  User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ads.setUser(user);*/
        User neco = userService.findByEmail("neco@neco.cz");
        entityBody.setUser(neco);
        return adsRepository.save(entityBody);
    }

    @Override
    public List<Ads> getAll() {
        return adsRepository.findAll();
    }

    @Override
    public Ads getById(Long entityId) {
        return  adsRepository.findById(entityId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement" + entityId + "not found"));
    }

    @Override
    public Ads deleteById(Long entityId) {
        Ads ads = adsRepository.findById(entityId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement" + entityId + "not found"));
        adsRepository.deleteById(ads.getId());
        return ads;
    }

    @Transactional
    @Override
    public Ads editById(Long entityId, Ads entityBody) {
        return adsRepository.findById(entityId)
                .map(ad -> {
                    ad.setName(entityBody.getName());
                    return adsRepository.save(ad);
                })
                .orElseGet(() -> {
                    entityBody.setId(entityId);
                    return adsRepository.save(entityBody);
                });
    }
}