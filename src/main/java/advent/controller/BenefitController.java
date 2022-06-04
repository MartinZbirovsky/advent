package advent.controller;

import advent.model.Benefit;
import advent.service.BenefitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/api/benefits")
public class BenefitController {

    BenefitService benefitService;
    BenefitController(BenefitService BenefitService) {
        this.benefitService = BenefitService;
    }

    @GetMapping("/{id}")
    public Benefit getBenefit(@PathVariable long id){
        return benefitService.getBenefit(id);
    }

    @GetMapping("")
    public List<Benefit> getBenefits(){
        return benefitService.getBenefits();
    }

    @PostMapping("")
    public Benefit createBenefit (@RequestBody @Valid Benefit benefit){
        return benefitService.createBenefit(benefit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBenefit(@PathVariable long id){
        return benefitService.deleteBenefit(id);
    }
}
