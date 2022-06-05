package advent.service.erviceinterface;

import advent.service.erviceinterface.general.BaseService;
import org.springframework.stereotype.Service;

@Service
public interface AdsService<T> extends BaseService<T> {

    T addCategoryToAds(Long adsId, Long categoryId);
    T deleteCategoryFromAds(Long adsId);
}
