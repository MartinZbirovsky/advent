package advent.service.intf;

import advent.model.Benefit;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * 
 * @see #addNew(Benefit) 
 * @see #getAll(int, int, String) 
 * @see #get(Long) 
 * @see #getBenefitByName(String) 
 * @see #edit(Long, Benefit) 
 * @see #delete(String)
 */
@Service
public interface BenefitService {
    /***
     *
     * @param entityBody
     * @return
     */
    Benefit addNew(Benefit entityBody);

    /**
     *
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return
     */
    Page<Benefit> getAll(int pageNo, int pageSize, String sortBy);

    /**
     *
     * @param id
     * @return
     */
    Benefit get(Long id);

    /**
     *
     * @param benefitName
     * @return
     */
    Benefit getBenefitByName(String benefitName);

    /**
     *
     * @param entityId
     * @param entityBody
     * @return
     */
    Benefit edit(Long entityId, Benefit entityBody);

    /**
     *
     * @param benefitName
     * @return
     */
    Benefit delete(String benefitName);
}
