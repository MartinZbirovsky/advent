package advent.controller;

import advent.model.Benefit;
import advent.service.Impl.BenefitServiceImpl;
import advent.service.Interface.BenefitService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static advent.configuration.Constants.PAGE_NUMBER;
import static advent.configuration.Constants.PAGE_SIZE;

@RestController()
@RequestMapping("/api/benefits")
public class BenefitController {

    private final BenefitService benefitService;
    BenefitController(BenefitServiceImpl BenefitServiceImpl) {
        this.benefitService = BenefitServiceImpl;
    }

    @GetMapping("/{id}")
    public Benefit getBenefit(@PathVariable long id){
        return benefitService.get(id);
    }

    @GetMapping("")
    public Page<Benefit> getBenefits(@RequestParam(defaultValue = PAGE_NUMBER) Integer pageNo,
                                     @RequestParam(defaultValue = PAGE_SIZE) Integer pageSize,
                                     @RequestParam(defaultValue = "id") String sortBy){
        return benefitService.getAll(pageNo, pageSize, sortBy);
    }

    @PostMapping("")
    public Benefit createBenefit (@RequestBody @Valid Benefit benefit){
        return benefitService.addNew(benefit);
    }

    @DeleteMapping("/{id}")
    public Benefit deleteBenefit(@PathVariable long id){
        return benefitService.delete(id);
    }
}
