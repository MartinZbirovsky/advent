package advent.dto;

import advent.dto.requestDto.CreateAdDto;
import advent.dto.requestDto.GlobalInfoMessageDto;
import advent.dto.responseDto.AdsDeleteResDto;
import advent.dto.responseDto.AdsDetailResDto;
import advent.dto.responseDto.AdsHomeResDto;
import advent.dto.responseDto.UserListResDto;
import advent.model.*;
import org.springframework.stereotype.Component;

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
     * Change User (all details) to UserListResDto - Used for list of users
     * @param user
     * @return
     */
    public UserListResDto userToUserListResDto (User user) {
        UserListResDto userListResDto = new UserListResDto();
        userListResDto.setId(user.getId());
        userListResDto.setFirstName(user.getFirstName());
        userListResDto.setLastName(user.getLastName());
        userListResDto.setEmail(user.getEmail());
        userListResDto.setLocked(user.getLocked());
        userListResDto.setEnabled(user.getEnabled());
        return userListResDto;
    }

    /**
     * Change CreateAdDto to Ad - create page
     * @param createAdDto
     * @return
     */
    public Ads createAdDtoAds (CreateAdDto createAdDto) {
        Ads ads = new Ads();
        ads.setName(createAdDto.getName());
        ads.setDescription(createAdDto.getDescription());
        ads.setRequirements(createAdDto.getRequirements());
        ads.setCompanyOffer(createAdDto.getCompanyOffer());
        ads.setSalaryFrom(createAdDto.getSalaryFrom());
        ads.setSalaryTo(createAdDto.getSalaryTo());
        return ads;
    }

    /**
     * Change "Ads" to "AdsHomeResDto" - Used on the title page
     *
     * @param ads - Ads model
     * @return AdsHomeResDto
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
     *
     * @param ads - Ads model
     * @return AdsDetailResDto
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
     *
     * @param ads
     * @return AdsDeleteResDto
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
     *
     * @param globalInfoMessageDto
     * @return
     */
    public GlobalInfoMessage globalInfoMessageDtoToGlobalInfoMessage(GlobalInfoMessageDto globalInfoMessageDto){
        GlobalInfoMessage message = new GlobalInfoMessage();

        message.setMessage(globalInfoMessageDto.getMessage());

        return message;
    }
}
