package advent.service.impl;

import advent.model.Address;
import advent.repository.AddressRepository;
import advent.service.intf.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Page<Address> getAll(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return addressRepository.findAll(paging);
    }

    @Override
    public Address get(Long addressId) {
        return  addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address " + addressId + " not found"));
    }

    @Override
    @Transactional
    public Address delete(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address " + addressId + " not found"));
        addressRepository.deleteById(address.getId());
        return address;
    }
}
