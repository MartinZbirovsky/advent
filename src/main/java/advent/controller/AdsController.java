package advent.controller;

import advent.model.Ads;
import advent.service.Interface.AdsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static advent.configuration.Constants.PAGE_NUMBER;
import static advent.configuration.Constants.PAGE_SIZE;

@RestController
@RequestMapping("/api/ads")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class AdsController{

    private final AdsService adsService;

    @GetMapping("")
    //@RolesAllowed(Role.AUTHOR_ADMIN)
    //@PreAuthorize("hasAnyRole('ADMIN')")
    public Page<Ads> getAds (
            @RequestParam(defaultValue = "") String adName,
            @RequestParam(defaultValue = "0") Long categoryId,
            @RequestParam(defaultValue = PAGE_NUMBER) Integer pageNo,
            @RequestParam(defaultValue = PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
        return adsService.getAll(adName, categoryId, pageNo, pageSize, sortBy);
    }

    @GetMapping("/{id}")
    public Ads getAdsById (@PathVariable Long id){
        return adsService.get(id);
    }

    @PostMapping("")
    public Ads addAds (@Valid @RequestBody Ads ads){
        return adsService.addNew(ads);
    }

    @PutMapping("/{id}")
    public Ads editAds(@PathVariable Long id, @Valid @RequestBody Ads ads) {
        return adsService.edit(id, ads);
    }

    @DeleteMapping("/{id}")
    public Ads deleteAds(@PathVariable Long id) {
        return adsService.delete(id);
    }

    @PostMapping("/addCategory/{categoryId}/toAds/{adsId}")
    public Ads addCategory(@PathVariable Long categoryId, @PathVariable Long adsId) {
        return adsService.addCategory(categoryId, adsId);
    }

   /* @GetMapping("/allMyAds/${id}")
    public List<Ads> allMyAds (Long id) {
      return adsService.getById(id);
    };*/

    @PostMapping("/removeCategory/{adsId}")
    public Ads removeCategory(@PathVariable Long adsId) {
        return adsService.removeCategory(adsId);
    }

    @PostMapping("/addBenefit/{benefitId}/toAds/{adsId}")
    public Ads addBenefit(@PathVariable Long benefitId, @PathVariable Long adsId) {
        return adsService.addBenefit(benefitId, adsId);
    }

    @PostMapping("/removeBenefit/{benefitId}/fromAds/{adsId}")
    public Ads removeBenefit(@PathVariable Long benefitId, @PathVariable Long adsId) {
        return adsService.removeBenefit(benefitId, adsId);
    }
}
