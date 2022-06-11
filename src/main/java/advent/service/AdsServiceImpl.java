package advent.service;

import advent.model.Ads;
import advent.model.Benefit;
import advent.model.Category;
import advent.repository.AdsRepository;
import advent.service.serviceinterface.AdsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService<Ads> {

    private final AdsRepository adsRepository;
    private final CategoryServiceImpl catService;
    private final UserServiceImpl userService;
    private final BenefitServiceImpl benefitService;

    @Transactional
    public Ads addNew(Ads entityBody) {
        /*  User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ads.setUser(user);*/
       // User neco = userService.findByEmail("neco@neco.cz");
      //  entityBody.setUser(neco);
        return adsRepository.save(entityBody);
    }

    @Override
    public Page<Ads> getAll(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return adsRepository.findAll(paging);
    }

    @Override
    public Page<Ads> getAll(String adName, Long categoryId, int pageNo, int pageSize, String sortBy) {
        Page<Ads> ads = null;
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        if(!adName.isEmpty() && categoryId != 0){
            ads = adsRepository.findByNameContaining(adName, paging);
            System.out.println("LOOOL");
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
    public Ads addCategory(Long categoryId, Long adsId) {
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement " + adsId + " not found"));
        Category category = catService.get(categoryId);
        ads.setCategory(category);

        return adsRepository.save(ads);
    }

    @Transactional
    public Ads removeCategory(Long adsId) {
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement " + adsId + " not found"));
        ads.setCategory(null);

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
}