package advent.service.impl;

import advent.dto.Mapper;
import advent.dto.requestDto.GlobalInfoMessageDto;
import advent.model.GlobalInfoMessage;
import advent.repository.GlobalInfoMessageRepository;
import advent.service.intf.GlobalInfoMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

/**
 * @see advent.service.intf.GlobalInfoMessageService
 */
@Service
@RequiredArgsConstructor
public class GlobalInfoMessageServiceImpl implements GlobalInfoMessageService {

    private final GlobalInfoMessageRepository repo;
    private final Mapper mapper;
    @Override
    public GlobalInfoMessage findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Message " + id + " not found"));

    }

    @Override
    public GlobalInfoMessage deleteById(Long id) {
        GlobalInfoMessage message
                = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Message " + id + " not found"));
        repo.deleteById(message.getId());
        return message;
    }

    @Override
    public GlobalInfoMessage addNew(GlobalInfoMessageDto globalInfoMessageDto) {
        return repo.save(mapper.globalInfoMessageDtoToGlobalInfoMessage(globalInfoMessageDto));
    }

    @Override
    public Page<GlobalInfoMessage> findAll(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return repo.findAll(paging);
    }
}
