package advent.service.serviceinterface;

import advent.model.Ads;
import advent.service.serviceinterface.general.BaseService;
import org.springframework.data.domain.Page;

public interface AdsService<T> extends  BaseService<T>{
    Page<Ads> getAll(String adName, Long category, int pageNo, int pageSize, String sortBy);
}
