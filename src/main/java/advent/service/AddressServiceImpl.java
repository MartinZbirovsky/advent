package advent.service;

import advent.model.Address;
import advent.repository.AddressRepository;
import advent.service.serviceinterface.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService<Address> {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address addNew(Address entity) {
        return null;
    }

    @Override
    public List<Address> getAll() {
        return null;
    }

    @Override
    public Address getById(Long entity) {
        return null;
    }

    @Override
    public Address deleteById(Long entity) {
        return null;
    }

    @Override
    public Address editById(Long addressId, Address entity) {
        return null;
    }
/*
    @Override
    public City deleteById(Long entityId) {
        City city = cityRepository.findById(entityId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement" + entityId + "not found"));
        cityRepository.deleteById(city.getId());
        return city;
    }

    @Override
    public City editById(Long entityId, City entityBody) {
        return cityRepository.findById(entityId)
                .map(ad -> {
                    ad.setName(entityBody.getName());
                    return cityRepository.save(ad);
                })
                .orElseGet(() -> {
                    entityBody.setId(entityId);
                    return cityRepository.save(entityBody);
                });
    }*/
}
