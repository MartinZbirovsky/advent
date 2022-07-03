package advent.controller;

import advent.dto.requestDto.CreateAdDto;
import advent.dto.responseDto.AdsDeleteResDto;
import advent.dto.responseDto.AdsDetailResDto;
import advent.dto.responseDto.AdsHomeResDto;
import advent.model.Ads;
import advent.model.AdsResponse;
import advent.service.intf.AdsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;

import static advent.cons.GeneralCons.PAGE_NUMBER;
import static advent.cons.GeneralCons.PAGE_SIZE;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AdsController{

    private final AdsService adsService;

    @GetMapping("/ads")
    public Page<AdsHomeResDto> getAds (
            @RequestParam(defaultValue = "") String adName,
            @RequestParam(defaultValue = "0") Long categoryId,
            @RequestParam(defaultValue = PAGE_NUMBER) Integer pageNo,
            @RequestParam(defaultValue = PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
        return adsService.getAll(adName, categoryId, pageNo, pageSize, sortBy);
    }

    @GetMapping("/ads/{id}")
    public AdsDetailResDto getAdsById (@PathVariable Long id){
        return adsService.get(id);
    }

    @PostMapping("/ads")
    public AdsHomeResDto addAds (@Valid @RequestBody CreateAdDto ads, Principal principal){
        return adsService.addNew(ads, "lolburhehe@seznam.cz"/*principal.getName()*/);
    }

    @PutMapping("/ads/{id}")
    public Ads editAds(@PathVariable Long id, @Valid @RequestBody Ads ads) {
        return adsService.edit(id, ads);
    }

    @DeleteMapping("/ads/{id}")
    public AdsDeleteResDto deleteAds(@PathVariable Long id) {
        return adsService.delete(id);
    }

    @PostMapping("/ads/addCategory/{categoryName}/toAds/{adsId}")
    public Ads addCategory(@PathVariable String categoryName, @PathVariable Long adsId) {
        return adsService.changeCategory(categoryName, adsId);
    }

    @PostMapping("/ads/addBenefit/{benefitId}/toAds/{adsId}")
    public Ads addBenefit(@PathVariable Long benefitId, @PathVariable Long adsId) {
        return adsService.addBenefit(benefitId, adsId);
    }

    @PostMapping("/ads/removeBenefit/{benefitId}/fromAds/{adsId}")
    public Ads removeBenefit(@PathVariable Long benefitId, @PathVariable Long adsId) {
        return adsService.removeBenefit(benefitId, adsId);
    }

    @PostMapping("/ads/response/{adsId}")
    public AdsResponse responseToAd(@PathVariable Long adsId, @RequestBody AdsResponse adsResponse) {
        return adsService.responseToAds(adsId, adsResponse);
    }
        /*@GetMapping("/ads/myads")
    public Set<Ads> allMyAds (Principal principal) {
      return adsService.getAllMyAds(principal.getName());
    };*/
}
