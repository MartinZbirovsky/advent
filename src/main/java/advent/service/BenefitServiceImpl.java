package advent.service;

import advent.model.Benefit;
import advent.repository.BenefitRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BenefitServiceImpl {

    private final BenefitRepository benefitRepository;

    public BenefitServiceImpl(BenefitRepository benefitRepository) {
        this.benefitRepository = benefitRepository;
    }

    public Benefit getBenefit(Long id) {
        return benefitRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User" + id + "not found"));}
    public List<Benefit> getBenefits() {return benefitRepository.findAll();}
    public Benefit createBenefit(Benefit benefit){return benefitRepository.save(benefit);}
    public ResponseEntity<?> deleteBenefit(Long id) {
        return benefitRepository.findById(id)
            .map(post -> {
                benefitRepository.delete(post);
                return ResponseEntity.ok().build();
            })
            .orElseThrow(() ->
                    new RuntimeException("PostId " + id + " not found")
            );
    }
}
