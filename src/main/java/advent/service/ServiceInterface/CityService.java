package advent.service.ServiceInterface;

import advent.model.City;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CityService {
    public City addCity(City city);
    public List<City> getCities();
    public City getCity(Long cityId);
    public City deleteCity(Long cityId);
    public City editCity(Long cityId, City city);
}