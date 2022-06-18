package advent.controller;

import advent.model.Ads;
import advent.service.Interface.AdsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;

import static advent.configuration.Constants.PAGE_NUMBER;
import static advent.configuration.Constants.PAGE_SIZE;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class AdsController{

    private final AdsService adsService;

    @GetMapping("/ads")
    public Page<Ads> getAds (
            @RequestParam(defaultValue = "") String adName,
            @RequestParam(defaultValue = "0") Long categoryId,
            @RequestParam(defaultValue = PAGE_NUMBER) Integer pageNo,
            @RequestParam(defaultValue = PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
        return adsService.getAll(adName, categoryId, pageNo, pageSize, sortBy);
    }

    @GetMapping("/ads/{id}")
    public Ads getAdsById (@PathVariable Long id){
        return adsService.get(id);
    }

    @PostMapping("/ads")
    public Ads addAds (@Valid @RequestBody Ads ads, Principal principal){
        return adsService.addNew(ads, "5neco@neco.cz"/*principal.getName()*/);
    }

    @PutMapping("/ads/{id}")
    public Ads editAds(@PathVariable Long id, @Valid @RequestBody Ads ads) {
        return adsService.edit(id, ads);
    }

    @DeleteMapping("/ads/{id}")
    public Ads deleteAds(@PathVariable Long id) {
        return adsService.delete(id);
    }

    @PostMapping("/ads/addCategory/{categoryName}/toAds/{adsId}")
    public Ads addCategory(@PathVariable String categoryName, @PathVariable Long adsId) {
        return adsService.addCategory(categoryName, adsId);
    }

    @PostMapping("/ads/addBenefit/{benefitId}/toAds/{adsId}")
    public Ads addBenefit(@PathVariable Long benefitId, @PathVariable Long adsId) {
        return adsService.addBenefit(benefitId, adsId);
    }

    @PostMapping("/ads/removeBenefit/{benefitId}/fromAds/{adsId}")
    public Ads removeBenefit(@PathVariable Long benefitId, @PathVariable Long adsId) {
        return adsService.removeBenefit(benefitId, adsId);
    }

        /*@GetMapping("/ads/myads")
    public Set<Ads> allMyAds (Principal principal) {
      return adsService.getAllMyAds(principal.getName());
    };*/
}
