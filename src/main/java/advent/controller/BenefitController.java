package advent.controller;

import advent.model.Benefit;
import advent.service.BenefitServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/api/benefits")
public class BenefitController {

    final BenefitServiceImpl benefitServiceImpl;
    BenefitController(BenefitServiceImpl BenefitServiceImpl) {
        this.benefitServiceImpl = BenefitServiceImpl;
    }

    @GetMapping("/{id}")
    public Benefit getBenefit(@PathVariable long id){
        return benefitServiceImpl.getBenefit(id);
    }

    @GetMapping("")
    public List<Benefit> getBenefits(){
        return benefitServiceImpl.getBenefits();
    }

    @PostMapping("")
    public Benefit createBenefit (@RequestBody @Valid Benefit benefit){
        return benefitServiceImpl.createBenefit(benefit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBenefit(@PathVariable long id){
        return benefitServiceImpl.deleteBenefit(id);
    }
}
