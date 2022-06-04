package advent.service;

import advent.model.City;
import advent.repository.CityRepository;
import advent.service.ServiceInterface.CityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }

    @Override
    public City addCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @Override
    public City getCity(Long cityId) {
        return cityRepository.findById(cityId).orElse(null);
    }

    @Override
    public City deleteCity(Long cityId) {
        City city = cityRepository.findById(cityId).orElse(null);
        cityRepository.deleteById(city.getId());
        return city;
    }

    @Override
    public City editCity(Long cityId, City city) {
        return null;
    }
}
