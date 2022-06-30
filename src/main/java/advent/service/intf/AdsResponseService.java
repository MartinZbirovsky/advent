package advent.service.intf;

import advent.model.AdsResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 *
 * @see #addNew(AdsResponse)
 * @see #getAll(int, int, String)
 */
@Service
public interface AdsResponseService {
    /**
     *
     * @param entityBody
     * @return
     */
    AdsResponse addNew(AdsResponse entityBody);

    /**
     *
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return
     */
    Page<AdsResponse> getAll(int pageNo, int pageSize, String sortBy);
}
