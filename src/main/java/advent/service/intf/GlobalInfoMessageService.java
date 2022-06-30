package advent.service.intf;

import advent.dto.requestDto.GlobalInfoMessageDto;
import advent.model.GlobalInfoMessage;
import org.springframework.data.domain.Page;

/**
 *
 * @see #findAll(int, int, String)
 * @see #deleteById(Long)
 * @see #addNew(GlobalInfoMessageDto)
 * @see
 */
public interface GlobalInfoMessageService {
    /**
     *
     * @param id
     * @return
     */
    GlobalInfoMessage findById(Long id);

    /**
     *
     * @param id
     * @return
     */
    GlobalInfoMessage deleteById(Long id);

    /**
     *
     * @param globalInfoMessageDto
     * @return
     */
    GlobalInfoMessage addNew(GlobalInfoMessageDto globalInfoMessageDto);

    /**
     *
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return
     */
    Page<GlobalInfoMessage> findAll(int pageNo, int pageSize, String sortBy);
}
