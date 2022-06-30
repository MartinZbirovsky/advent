package advent.service.impl;

import advent.model.AdsResponse;
import advent.repository.AdsResponseRepository;
import advent.service.intf.AdsResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @see advent.service.intf.AdsResponseService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdsResponseServiceImpl implements AdsResponseService {

    private final AdsResponseRepository adsResponseRepository;

    @Override
    public AdsResponse addNew(AdsResponse entityBody) {
        return adsResponseRepository.save(entityBody);
    }

    @Override
    public Page<AdsResponse> getAll(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return adsResponseRepository.findAll(paging);
    }
}
