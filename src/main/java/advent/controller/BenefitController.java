package advent.controller;

import advent.model.Benefit;
import advent.service.impl.BenefitServiceImpl;
import advent.service.intf.BenefitService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static advent.cons.GeneralCons.PAGE_NUMBER;
import static advent.cons.GeneralCons.PAGE_SIZE;

@RestController()
@RequestMapping("/api")
public class BenefitController {

    private final BenefitService benefitService;
    BenefitController(BenefitServiceImpl BenefitServiceImpl) {
        this.benefitService = BenefitServiceImpl;
    }

    @GetMapping("/benefits/{benefitName}")
    public Benefit getBenefitByName(@PathVariable String benefitName){
        return benefitService.getBenefitByName(benefitName);
    }

    @GetMapping("/benefits")
    public Page<Benefit> getBenefits(@RequestParam(defaultValue = PAGE_NUMBER) Integer pageNo,
                                     @RequestParam(defaultValue = PAGE_SIZE) Integer pageSize,
                                     @RequestParam(defaultValue = "id") String sortBy){
        return benefitService.getAll(pageNo, pageSize, sortBy);
    }

    @PutMapping("/benefits/{id}")
    public Benefit editBenefit(@PathVariable Long id, @Valid @RequestBody Benefit benefit) {
        return benefitService.edit(id, benefit);
    }

    @PostMapping("/benefits")
    public Benefit createBenefit (@RequestBody @Valid Benefit benefit){
        return benefitService.addNew(benefit);
    }

    @DeleteMapping("/benefits/{benefitName}")
    public Benefit deleteBenefit(@PathVariable String benefitName){
        return benefitService.delete(benefitName);
    }
}
