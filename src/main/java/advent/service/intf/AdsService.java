package advent.service.intf;

import advent.dto.requestDto.CreateAdDto;
import advent.dto.responseDto.AdsDeleteResDto;
import advent.dto.responseDto.AdsDetailResDto;
import advent.dto.responseDto.AdsHomeResDto;
import advent.model.Ads;
import advent.model.AdsResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * <p>Ads. service</p>
 *
 * @see #addNew(CreateAdDto, String)
 * @see #getAll(int, int, String) 
 * @see #get(Long) 
 * @see #edit(Long, Ads) 
 * @see #delete(Long) 
 * @see #getAll(int, int, String) 
 * @see #getAll(String, Long, int, int, String) 
 * @see #changeCategory(String, Long) 
 * @see #addBenefit(Long, Long)
 * @see #removeBenefit(Long, Long)
 * @see #responseToAds(Long, AdsResponse)
 *  
 * @author mz
 */
@Service
public interface AdsService{
    /**
     * Save new ads.
     *
     * @param ads - ad body.
     * @param principalName - user name.
     * @return AdsHomeResDto
     */
    AdsHomeResDto addNew(CreateAdDto ads, String principalName);

    /**
     * Get all ads with pagination and sort.
     *
     * @param pageNo - page number
     * @param pageSize - page size
     * @param sortBy - sort by attribute
     * @return
     */
    Page<AdsHomeResDto> getAll(int pageNo, int pageSize, String sortBy);

    /**
     * Get ads. by his ID.
     *
     * @param entityId
     * @return
     */
    AdsDetailResDto get(Long entityId);

    /**
     * Edit ads. by his ID and body.
     * @param entityId
     * @param entityBody
     * @return
     */
    Ads edit(Long entityId, Ads entityBody);

    /**
     * Delete ads. by his ID.
     *
     * @param entityId
     * @return
     */
    AdsDeleteResDto delete(Long entityId);

    /**
     * Get all ads. with and pagination sort and containing name.
     *
     * @param adName
     * @param category
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return
     */
    Page<AdsHomeResDto> getAll(String adName, Long category, int pageNo, int pageSize, String sortBy);

    /**
     * Change ads. category.
     *
     * @param categoryName
     * @param adsId
     * @return
     */
    Ads changeCategory(String categoryName, Long adsId);

    /**
     * Add existing benefit tu ads.
     *
     * @param benefitId
     * @param adsId
     * @return
     */
    Ads addBenefit(Long benefitId, Long adsId);

    /**
     * Remove benefit from ads.
     *
     * @param benefitId
     * @param adsId
     * @return
     */
    Ads removeBenefit(Long benefitId, Long adsId);

    /**
     * Response to published ads.
     * @param adsId
     * @param adsResponse
     * @return
     */
    AdsResponse responseToAds (Long adsId, AdsResponse adsResponse);
}
