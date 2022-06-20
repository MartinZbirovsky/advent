package advent.controller;

import advent.model.Payment;
import advent.service.intf.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static advent.cons.GeneralCons.PAGE_NUMBER;
import static advent.cons.GeneralCons.PAGE_SIZE;
@RestController
@RequestMapping("/api/pay")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("")
    public Page<Payment> getAllPayments (@RequestParam(defaultValue = PAGE_NUMBER) Integer pageNo,
                                            @RequestParam(defaultValue = PAGE_SIZE) Integer pageSize,
                                            @RequestParam(defaultValue = "id") String sortBy){
        return paymentService.getAll(pageNo, pageSize, sortBy);
    }

    @GetMapping("/{id}")
    public Payment getPaymentById (@PathVariable Long id){
        return paymentService.get(id);
    }

    @PostMapping("")
    public Payment addPayment (@Valid @RequestBody Payment Payment){
        return paymentService.addNew(Payment);
    }
}
