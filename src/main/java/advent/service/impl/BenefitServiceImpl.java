package advent.service.impl;

import advent.model.Benefit;
import advent.repository.BenefitRepository;
import advent.service.intf.BenefitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

/**
 * @see advent.service.intf.BenefitService
 */
@Service
@RequiredArgsConstructor
public class BenefitServiceImpl implements BenefitService {

    private final BenefitRepository benefitRepository;

    @Override
    public Benefit addNew(Benefit benefit) {
        return benefitRepository.save(benefit);
    }

    @Override
    public Page<Benefit> getAll(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return benefitRepository.findAll(paging);
    }

    @Override
    public Benefit get(Long benefitId) {
        return benefitRepository.findById(benefitId)
                .orElseThrow(() -> new EntityNotFoundException("Benefit " + benefitId + " not found"));
    }

    @Override
    public Benefit getBenefitByName(String benefitName) {
        return benefitRepository.findByName(benefitName);
    }

    @Override
    @Transactional
    public Benefit edit(Long entityId, Benefit benefit) {
        return benefitRepository.findById(entityId)
                .map(ad -> {
                    ad.setName(benefit.getName());
                    return benefitRepository.save(ad);
                })
                .orElseGet(() -> {
                    benefit.setId(entityId);
                    return benefitRepository.save(benefit);
                });
    }

    @Override
    @Transactional
    public Benefit delete(String benefitName) {
        Benefit benefit = benefitRepository.findByName(benefitName);
        benefitRepository.deleteById(benefit.getId());
        return benefit;
    }
}
