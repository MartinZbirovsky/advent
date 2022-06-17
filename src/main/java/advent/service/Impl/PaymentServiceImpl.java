package advent.service.Impl;

import advent.model.Payment;
import advent.repository.PaymentRepository;
import advent.service.Interface.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public Payment addNew(Payment entityBody) {
        /*  User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ads.setUser(user);*/
        // User neco = userService.findByEmail("neco@neco.cz");
        //  entityBody.setUser(neco);
        return paymentRepository.save(entityBody);
    }

    @Override
    public Page<Payment> getAll(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return paymentRepository.findAll(paging);
    }

    @Override
    public Payment get(Long paymentId) {
        return  paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment " + paymentId + " not found"));
    }
}