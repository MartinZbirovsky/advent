package advent.controller;

import advent.model.Payment;
import advent.service.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static advent.configuration.Constants.PAGE_NUMBER;
import static advent.configuration.Constants.PAGE_SIZE;
// keep for testing purpose
@RestController
@RequestMapping("/api/pay")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentServiceImpl paymentServiceImpl;

    @GetMapping("")
    public Page<Payment> getAllPayments (@RequestParam(defaultValue = PAGE_NUMBER) Integer pageNo,
                                            @RequestParam(defaultValue = PAGE_SIZE) Integer pageSize,
                                            @RequestParam(defaultValue = "id") String sortBy){
        return paymentServiceImpl.getAll(pageNo, pageSize, sortBy);
    }

    @GetMapping("/{id}")
    public Payment getPaymentById (@PathVariable Long id){
        return paymentServiceImpl.get(id);
    }

    @PostMapping("")
    public Payment addPayment (@Valid @RequestBody Payment Payment){
        return paymentServiceImpl.addNew(Payment);
    }
}
