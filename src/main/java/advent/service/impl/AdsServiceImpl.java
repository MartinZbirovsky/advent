package advent.service.impl;

import advent.dto.Mapper;
import advent.dto.requestDto.CreateAdDto;
import advent.dto.responseDto.AdsDeleteResDto;
import advent.dto.responseDto.AdsDetailResDto;
import advent.dto.responseDto.AdsHomeResDto;
import advent.model.*;
import advent.repository.AdsRepository;
import advent.repository.UserRepository;
import advent.service.intf.AdsService;
import advent.service.intf.BenefitService;
import advent.service.intf.CategoryService;
import advent.validators.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

/**
 * @see advent.service.intf.AdsService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdsServiceImpl implements AdsService{
    private final AdsRepository adsRepository;
    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private final BenefitService benefitService;
    private final Mapper mapper;
    private final Validator validator;
    @Transactional
    public AdsHomeResDto addNew(CreateAdDto createAdDto, String principalName) {
        Ads ads = mapper.createAdDtoAds(createAdDto);

        User actualUser = userRepository.findByEmail(principalName)
                .orElseThrow(() -> new EntityNotFoundException("Email not found"));
        //if (!validator.onlyStringWithCapital(ads.getName())) throw new IllegalStateException("Advertisement name not valid");

        actualUser.reduceCurrentMoney();
        userRepository.save(actualUser);

        log.info("ASSIGN PRINCIPAL TO AD " + principalName);
        ads.setUser(actualUser);

        // for rework and remove
        Category category = categoryService.findByName("OTHER")
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        ads.setCategory(category);

        return mapper.adsToAdsHomeResDto(adsRepository.save(ads));
    }

    @Override
    public Page<AdsHomeResDto> getAll(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return adsRepository.findAll(paging).map(ad -> mapper.adsToAdsHomeResDto(ad));
    }

    @Override
    public Page<AdsHomeResDto> getAll(String adName, Long categoryId, int pageNo, int pageSize, String sortBy) {
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
        return ads.map(ad -> mapper.adsToAdsHomeResDto(ad));
    }

    @Override
    public AdsDetailResDto get(Long adsId) {
        return  mapper.adsToAdsDetailResDto(adsRepository.findById(adsId)
                .orElseThrow(() -> notFoundMessage(adsId)));
    }

    @Override
    @Transactional
    public AdsDeleteResDto delete(Long adsId) {
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> notFoundMessage(adsId));

        adsRepository.deleteById(ads.getId());
        return mapper.adsDeleteResDto(ads);
    }

    @Override
    @Transactional
    public Ads edit(Long entityId, Ads ads) {
        Ads adsToUpdate = adsRepository.findById(entityId)
                .orElseThrow(() -> notFoundMessage(entityId));

        adsToUpdate.setName(ads.getName());
        adsToUpdate.setDescription(ads.getDescription());
        adsToUpdate.setCompanyOffer(ads.getCompanyOffer());

       return adsRepository.save(adsToUpdate);
    }

    @Transactional
    public Ads changeCategory(String categoryName, Long adsId) {
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> notFoundMessage(adsId));

        Category category = categoryService.findByName(categoryName)
                .orElseThrow(() -> notFoundMessage(adsId));
        ads.setCategory(category);

        return adsRepository.save(ads);
    }

    @Transactional
    public Ads addBenefit(Long benefitId, Long adsId) {
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> notFoundMessage(adsId));

        Benefit benefit = benefitService.get(benefitId);
        ads.addBenefit(benefit);

        return adsRepository.save(ads);
    }

    @Transactional
    public Ads removeBenefit(Long benefitId, Long adsId) {
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> notFoundMessage(adsId));

        Benefit benefit = benefitService.get(benefitId);
        ads.removeBenefit(benefit);

        return adsRepository.save(ads);
    }

    @Override
    @Transactional
    public AdsResponse responseToAds(Long adsId, AdsResponse adsResponse) {
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> notFoundMessage(adsId));

        ads.getResponse().add(adsResponse);
        adsRepository.save(ads);
        return adsResponse;
    }

    /**
     * Not found entity message.
     * @param aId - Record ID
     * @return - EntityNotFoundException with message and record ID
     */
    private EntityNotFoundException notFoundMessage(Long aId) {
        return new EntityNotFoundException("Advertisement " + aId + " not found");
    }
/*
    @Override
    public Set<Ads> getAllMyAds(String principalName) {
        User actualUser = userRepository.findByEmail(principalName);

        return actualUser.getAds();
    }*/
}