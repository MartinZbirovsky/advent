package advent.service.serviceinterface;

import advent.service.serviceinterface.general.BaseService;
import org.springframework.stereotype.Service;

@Service
public interface AdsService<T> extends BaseService<T> {

    T addCategoryToAds(Long adsId, Long categoryId);
    T deleteCategoryFromAds(Long adsId);
}
