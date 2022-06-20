package advent.service.intf;

import advent.dto.requestDto.GlobalInfoMessageDto;
import advent.model.GlobalInfoMessage;
import org.springframework.data.domain.Page;

public interface GlobalInfoMessageService {
    GlobalInfoMessage findById(Long id);
    GlobalInfoMessage deleteById(Long id);
    GlobalInfoMessage addNew(GlobalInfoMessageDto globalInfoMessageDto);
    Page<GlobalInfoMessage> findAll(int pageNo, int pageSize, String sortBy);
}
