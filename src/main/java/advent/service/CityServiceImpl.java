package advent.service;

import advent.model.City;
import advent.repository.CityRepository;
import advent.service.erviceinterface.CityService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CityServiceImpl implements CityService<City> {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }


    @Override
    public City addNew(City entityBody) {
        return cityRepository.save(entityBody);
    }

    @Override
    public List<City> getAll() {
        return cityRepository.findAll();
    }

    @Override
    public City getById(Long entityId) {
        return cityRepository.findById(entityId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement" + entityId + "not found"));
    }

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
    }
}
