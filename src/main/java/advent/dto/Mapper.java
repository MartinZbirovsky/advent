package advent.dto;

import advent.dto.requestDto.GlobalInfoMessageDto;
import advent.dto.responseDto.AdsDeleteResDto;
import advent.dto.responseDto.AdsDetailResDto;
import advent.dto.responseDto.AdsHomeResDto;
import advent.model.*;
import advent.service.intf.GlobalInfoMessageService;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Mapper {

   /* public UserCreateDto userToCreateDto(User user) {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setId(user.getId());
        userCreateDto.setEmail(user.getEmail());
        return userCreateDto;
    }

    public UserDetailDto userToDetailDto(User user) {
        UserDetailDto userCreateDtoRes = new UserDetailDto();
        userCreateDtoRes.setId(user.getId());
        userCreateDtoRes.setEmail(user.getEmail());
        userCreateDtoRes.setAddress(user.getFirstAddress());
        return userCreateDtoRes;
    }*/

    /**
     * Change "Ads" to "AdsHomeResDto" - Used on the title page
     * @param ads - Ads model
     * @return AdsHomeResDto model
     */
    public AdsHomeResDto adsToAdsHomeResDto(Ads ads) {
        AdsHomeResDto adsHomeResDto = new AdsHomeResDto();

        adsHomeResDto.setId(ads.getId());
        adsHomeResDto.setName(ads.getName());
        adsHomeResDto.setSalaryFrom(ads.getSalaryFrom());
        adsHomeResDto.setSalaryTo(ads.getSalaryTo());
        adsHomeResDto.setOfficePlace(ads.getOfficePlace());
        adsHomeResDto.setWorkType(ads.getWorkType());
        adsHomeResDto.setPublisherName(ads.getUser().getPublisherName());
        adsHomeResDto.setCompanyLogo(ads.getUser().getCompanyLogo());

        return adsHomeResDto;
    }

    /**
     * Change "Ads" to "AdsDetailResDto" - Include AdsHomeResDto used for detail view.
     * @param ads - Ads model
     * @return AdsDetailResDto model
     */
    public AdsDetailResDto adsToAdsDetailResDto(Ads ads){
        AdsDetailResDto adsDetailResDto = new AdsDetailResDto();

        adsDetailResDto.setId(ads.getId());
        adsDetailResDto.setName(ads.getName());
        adsDetailResDto.setSalaryFrom(ads.getSalaryFrom());
        adsDetailResDto.setSalaryTo(ads.getSalaryTo());
        adsDetailResDto.setOfficePlace(ads.getOfficePlace());
        adsDetailResDto.setWorkType(ads.getWorkType());
        adsDetailResDto.setCreatedAt(ads.getCreatedAt());
        adsDetailResDto.setPublisherName(ads.getUser().getPublisherName());
        adsDetailResDto.setCompanyLogo(ads.getUser().getCompanyLogo());

        adsDetailResDto.setDescription(ads.getDescription());
        adsDetailResDto.setRequirements(ads.getRequirements());
        adsDetailResDto.setCompanyOffer(ads.getCompanyOffer());
        adsDetailResDto.setBenefits(ads.getBenefits());

        return adsDetailResDto;
    }

    /**
     * Change "Ads" to "AdsDeleteResDto" used after delete ads.
     * @param ads
     * @return AdsDeleteResDto model
     */
    public AdsDeleteResDto adsDeleteResDto(Ads ads) {
        AdsDeleteResDto adsDeleteResDto = new AdsDeleteResDto();

        adsDeleteResDto.setMessage("Ad was removed.");
        adsDeleteResDto.setName(ads.getName());
        adsDeleteResDto.setSalaryFrom(ads.getSalaryFrom());
        adsDeleteResDto.setSalaryTo(ads.getSalaryTo());
        adsDeleteResDto.setPublisherName(ads.getUser().getPublisherName());

        return adsDeleteResDto;
    }

    /**
     * Global info message - request body
     * @param globalInfoMessageDto
     * @return
     */
    public GlobalInfoMessage globalInfoMessageDtoToGlobalInfoMessage(GlobalInfoMessageDto globalInfoMessageDto){
        GlobalInfoMessage message = new GlobalInfoMessage();

        message.setMessage(globalInfoMessageDto.getMessage());

        return message;
    }
}
