package advent.service.Interface;

import advent.model.Ads;
import advent.model.AdsResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface AdsService{
    Ads addNew(Ads ads, String principalName);
    Page<Ads> getAll(int pageNo, int pageSize, String sortBy);
    Ads get(Long entityId);
    Ads edit(Long entityId, Ads entityBody);
    Ads delete(Long entityId);
    Page<Ads> getAll(String adName, Long category, int pageNo, int pageSize, String sortBy);
    public Ads addCategory(String categoryName, Long adsId);
    public Ads addBenefit(Long benefitId, Long adsId);
    public Ads removeBenefit(Long benefitId, Long adsId);
    public AdsResponse responseToAds (Long adsId, AdsResponse adsResponse);
}
