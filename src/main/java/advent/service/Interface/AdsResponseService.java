package advent.service.Interface;

import advent.model.AdsResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface AdsResponseService {
    AdsResponse addNew(AdsResponse entityBody);
    Page<AdsResponse> getAll(int pageNo, int pageSize, String sortBy);
}
