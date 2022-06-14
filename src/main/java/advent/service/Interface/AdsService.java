package advent.service.Interface;

import advent.model.Ads;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface AdsService{
    Ads addNew(Ads entityBody);
    Page<Ads> getAll(int pageNo, int pageSize, String sortBy);
    Ads get(Long entityId);
    Ads edit(Long entityId, Ads entityBody);
    Ads delete(Long entityId);
    Page<Ads> getAll(String adName, Long category, int pageNo, int pageSize, String sortBy);
    public Ads addCategory( Long categoryId, Long adsId);
    public Ads removeCategory( Long adsId);
    public Ads addBenefit(Long benefitId, Long adsId);
    public Ads removeBenefit( Long benefitId, Long adsId);
}
