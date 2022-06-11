package advent.controller;

import advent.model.Benefit;
import advent.service.BenefitServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static advent.configuration.Constants.PAGE_NUMBER;
import static advent.configuration.Constants.PAGE_SIZE;

@RestController()
@RequestMapping("/api/benefits")
public class BenefitController {

    final BenefitServiceImpl benefitServiceImpl;
    BenefitController(BenefitServiceImpl BenefitServiceImpl) {
        this.benefitServiceImpl = BenefitServiceImpl;
    }

    @GetMapping("/{id}")
    public Benefit getBenefit(@PathVariable long id){
        return benefitServiceImpl.get(id);
    }

    @GetMapping("")
    public Page<Benefit> getBenefits(@RequestParam(defaultValue = PAGE_NUMBER) Integer pageNo,
                                     @RequestParam(defaultValue = PAGE_SIZE) Integer pageSize,
                                     @RequestParam(defaultValue = "id") String sortBy){
        return benefitServiceImpl.getAll(pageNo, pageSize, sortBy);
    }

    @PostMapping("")
    public Benefit createBenefit (@RequestBody @Valid Benefit benefit){
        return benefitServiceImpl.addNew(benefit);
    }

    @DeleteMapping("/{id}")
    public Benefit deleteBenefit(@PathVariable long id){
        return benefitServiceImpl.delete(id);
    }
}
