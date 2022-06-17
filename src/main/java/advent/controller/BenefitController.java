package advent.controller;

import advent.model.Ads;
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

    @GetMapping("/{benefitName}")
    public Benefit getBenefitByName(@PathVariable String benefitName){
        return benefitService.getBenefitByName(benefitName);
    }

    @GetMapping("")
    public Page<Benefit> getBenefits(@RequestParam(defaultValue = PAGE_NUMBER) Integer pageNo,
                                     @RequestParam(defaultValue = PAGE_SIZE) Integer pageSize,
                                     @RequestParam(defaultValue = "id") String sortBy){
        return benefitService.getAll(pageNo, pageSize, sortBy);
    }

    @PutMapping("/{id}")
    public Benefit editBenefit(@PathVariable Long id, @Valid @RequestBody Benefit benefit) {
        return benefitService.edit(id, benefit);
    }

    @PostMapping("")
    public Benefit createBenefit (@RequestBody @Valid Benefit benefit){
        return benefitService.addNew(benefit);
    }

    @DeleteMapping("/{benefitName}")
    public Benefit deleteBenefit(@PathVariable String benefitName){
        return benefitService.delete(benefitName);
    }
}
