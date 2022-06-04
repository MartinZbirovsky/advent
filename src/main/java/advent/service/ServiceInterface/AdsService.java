package advent.service.ServiceInterface;

import advent.model.Ads;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdsService {
    public Ads addAds(Ads ads);
    public List<Ads> getAllAds();
    public Ads getAdsById(Long id);
    public Ads deleteAds(Long id);
    public Ads editAds(Long id, Ads ads);
    public Ads addCategoryToAds(Long adsId, Long categoryId);
    public Ads deleteCategoryFromAds(Long categoryId);
}
