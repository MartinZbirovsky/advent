package advent.controller;

import advent.model.Ads;
import advent.service.AdsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static advent.configuration.Constants.PAGE_NUMBER;
import static advent.configuration.Constants.PAGE_SIZE;

@RestController
@RequestMapping("/api/ads")
@RequiredArgsConstructor
public class AdsController {

    final AdsServiceImpl adsServiceImpl;

    @GetMapping("")
    //@RolesAllowed(Role.AUTHOR_ADMIN)
    //@PreAuthorize("hasAnyRole('ADMIN')")
    public Page<Ads> getAds (
            @RequestParam(defaultValue = "") String adName,
            @RequestParam(defaultValue = "0") Long categoryId,
            @RequestParam(defaultValue = PAGE_NUMBER) Integer pageNo,
            @RequestParam(defaultValue = PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
        return adsServiceImpl.getAll(adName, categoryId, pageNo, pageSize, sortBy);
    }

    @GetMapping("/{id}")
    public Ads getAdsById (@PathVariable Long id){
        return adsServiceImpl.get(id);
    }

    @PostMapping("")
    public Ads addAds (@Valid @RequestBody Ads ads){
        return adsServiceImpl.addNew(ads);
    }

    @PutMapping("/{id}")
    public Ads updateAds(@PathVariable Long id, @RequestBody Ads ads) {
        return adsServiceImpl.edit(id, ads);
    }

    @DeleteMapping("/{id}")
    public Ads deleteAds(@PathVariable Long id) {
        return adsServiceImpl.delete(id);
    }

    @PostMapping("/addCategory/{categoryId}/toAds/{adsId}")
    public Ads addCategory(@PathVariable Long categoryId, @PathVariable Long adsId) {
        return adsServiceImpl.addCategory(categoryId, adsId);
    }

   /* @GetMapping("/allMyAds/${id}")
    public List<Ads> allMyAds (Long id) {
      return adsServiceImpl.getById(id);
    };*/

    @PostMapping("/removeCategory/{adsId}")
    public Ads removeCategory(@PathVariable Long adsId) {
        return adsServiceImpl.removeCategory(adsId);
    }

    @PostMapping("/addBenefit/{benefitId}/toAds/{adsId}")
    public Ads addBenefit(@PathVariable Long benefitId, @PathVariable Long adsId) {
        return adsServiceImpl.addBenefit(benefitId, adsId);
    }

    @PostMapping("/removeBenefit/{benefitId}/fromAds/{adsId}")
    public Ads removeBenefit(@PathVariable Long benefitId, @PathVariable Long adsId) {
        return adsServiceImpl.removeBenefit(benefitId, adsId);
    }
}
