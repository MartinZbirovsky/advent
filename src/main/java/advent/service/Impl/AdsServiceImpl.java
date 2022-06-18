package advent.service.Impl;

import advent.model.Ads;
import advent.model.Benefit;
import advent.model.Category;
import advent.model.User;
import advent.repository.AdsRepository;
import advent.repository.UserRepository;
import advent.service.Interface.AdsService;
import advent.service.Interface.BenefitService;
import advent.service.Interface.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;

import static advent.configuration.Constants.ADS_PRICE;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdsServiceImpl implements AdsService{

    private final AdsRepository adsRepository;
    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private final BenefitService benefitService;

    @Transactional
    public Ads addNew(Ads ads, String principalName) {
        User actualUser = userRepository.findByEmail(principalName);

        log.info("Current money: " + actualUser.getCurrentMoney());
        if(actualUser.getCurrentMoney().compareTo(ADS_PRICE) == -1){
            throw new RuntimeException("No money");
        }else {
            actualUser.setCurrentMoney(actualUser.getCurrentMoney().add(new BigDecimal("-10")));
            userRepository.save(actualUser);
            log.info("Current money REDUCED: " + actualUser.getCurrentMoney());
        }

        log.info("ASSIGN PRINCIPAL TO ADS " + principalName);
        ads.setUser(actualUser);
        return adsRepository.save(ads);
    }

    @Override
    public Page<Ads> getAll(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return adsRepository.findAll(paging);
    }

    @Override
    public Page<Ads> getAll(String adName, Long categoryId, int pageNo, int pageSize, String sortBy) {
        Page<Ads> ads;
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        if(!adName.isEmpty() && categoryId != 0){
            ads = adsRepository.findByCategory(adName, categoryId, paging);
        }
        else if (!adName.isEmpty() && categoryId == 0){
            ads = adsRepository.findByNameContaining(adName, paging);
        }
        else if (adName.isEmpty() && categoryId != 0) {
            ads = adsRepository.findByCategory(categoryId, paging);
        }else {
            ads = adsRepository.findAll(paging) ;
        }
        return ads;
    }

    @Override
    public Ads get(Long adsId) {
        return  adsRepository.findById(adsId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement " + adsId + " not found"));
    }

    @Override
    @Transactional
    public Ads delete(Long adsId) {
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement " + adsId + " not found"));
        adsRepository.deleteById(ads.getId());
        return ads;
    }

    @Override
    @Transactional
    public Ads edit(Long entityId, Ads ads) {
        return adsRepository.findById(entityId)
                .map(ad -> {
                    ad.setName(ads.getName());
                    return adsRepository.save(ad);
                })
                .orElseGet(() -> {
                    ads.setId(entityId);
                    return adsRepository.save(ads);
                });
    }

    @Transactional
    public Ads addCategory(String categoryName, Long adsId) {
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement " + adsId + " not found"));
        Category category = categoryService.findByName(categoryName);
        ads.setCategory(category);

        return adsRepository.save(ads);
    }

    @Transactional
    public Ads addBenefit(Long benefitId, Long adsId) {
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement " + adsId + " not found"));

        Benefit benefit = benefitService.get(benefitId);
        ads.addBenefit(benefit);

        return adsRepository.save(ads);
    }

    @Transactional
    public Ads removeBenefit(Long benefitId, Long adsId) {
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement " + adsId + " not found"));

        Benefit benefit = benefitService.get(benefitId);
        ads.removeBenefit(benefit);

        return adsRepository.save(ads);
    }
/*
    @Override
    public Set<Ads> getAllMyAds(String principalName) {
        User actualUser = userRepository.findByEmail(principalName);

        return actualUser.getAds();
    }*/
}