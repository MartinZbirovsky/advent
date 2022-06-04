package advent.controller;

import advent.model.Ads;
import advent.service.AdsServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
public class AdsController {

    AdsServiceImpl adsServiceImpl;
    AdsController (AdsServiceImpl adsServiceImpl) {
        this.adsServiceImpl = adsServiceImpl;
    }

    @GetMapping()
    //@RolesAllowed({"ROLE_ADMIN"})
    public List<Ads> getAds (){
        return adsServiceImpl.getAllAds();
    }

    @GetMapping("/{id}")
    public Ads getAdsById (@PathVariable Long id){
        return adsServiceImpl.getAdsById(id);
    }

    @PostMapping("")
    public Ads addAds (@RequestBody Ads ads){
        return adsServiceImpl.addAds(ads);
    }

    @PutMapping("")
    public Ads updateAds(@RequestBody Ads ads, @PathVariable Long id) {
        return adsServiceImpl.editAds(id, ads);
    }

    @DeleteMapping("/{id}")
    public String deleteAds(@PathVariable Long id) {
        return "Record " + adsServiceImpl.deleteAds(id).getName() + " is deleted";
    }
}
