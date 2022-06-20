package advent.service.intf;

import advent.dto.responseDto.AdsDeleteResDto;
import advent.dto.responseDto.AdsDetailResDto;
import advent.dto.responseDto.AdsHomeResDto;
import advent.model.Ads;
import advent.model.AdsResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface AdsService{
    AdsHomeResDto addNew(Ads ads, String principalName);
    Page<AdsHomeResDto> getAll(int pageNo, int pageSize, String sortBy);
    AdsDetailResDto get(Long entityId);
    Ads edit(Long entityId, Ads entityBody);
    AdsDeleteResDto delete(Long entityId);
    Page<AdsHomeResDto> getAll(String adName, Long category, int pageNo, int pageSize, String sortBy);
    public Ads addCategory(String categoryName, Long adsId);
    public Ads addBenefit(Long benefitId, Long adsId);
    public Ads removeBenefit(Long benefitId, Long adsId);
    public AdsResponse responseToAds (Long adsId, AdsResponse adsResponse);
}
