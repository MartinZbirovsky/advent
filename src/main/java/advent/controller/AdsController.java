package advent.controller;

import advent.model.Ads;
import advent.service.AdsServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ads")
public class AdsController {

    final AdsServiceImpl adsServiceImpl;
    AdsController (AdsServiceImpl adsServiceImpl) {
        this.adsServiceImpl = adsServiceImpl;
    }

    @GetMapping()
   // @RolesAllowed({"ROLE_ADMIN"})
    public List<Ads> getAds (){
        return adsServiceImpl.getAll();
    }

    @GetMapping("/{id}")
    public Ads getAdsById (@PathVariable Long id){
        return adsServiceImpl.getById(id);
    }

    @PostMapping("")
    public Ads addAds (@Valid @RequestBody Ads ads){
        return adsServiceImpl.addNew(ads);
    }

    @PutMapping("")
    public Ads updateAds(@RequestBody Ads ads, @PathVariable Long id) {
        return adsServiceImpl.editById(id, ads);
    }

    @DeleteMapping("/{id}")
    public String deleteAds(@PathVariable Long id) {
        return "Record " + adsServiceImpl.deleteById(id).getName() + " is deleted";
    }
}
