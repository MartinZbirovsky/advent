package advent.service.Interface;

import advent.model.Benefit;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface BenefitService {
    Benefit addNew(Benefit entityBody);
    Page<Benefit> getAll(int pageNo, int pageSize, String sortBy);
    Benefit get(Long id);
    Benefit getBenefitByName(String benefitName);
    Benefit edit(Long entityId, Benefit entityBody);
    Benefit delete(String benefitName);
}
