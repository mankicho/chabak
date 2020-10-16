package controller.service.chabak;

import domain.Chabak;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import repository.ChabakRepository;

import java.net.HttpURLConnection;
import java.util.List;
@Controller
@RequestMapping(value = "/chabak")
public class ChabakService {
    private ChabakRepository repository;

    public ChabakService() {
        repository = new ChabakRepository();
    }

    @RequestMapping(value = "/get")
    public List<Chabak> getAllChabaks() {
        return repository.getChabaks();
    }

    @RequestMapping(value = "/getKey")
    public List<Chabak> searchByKeyword(@RequestParam(value = "key") String key) {
        return repository.searchByKeyword(key);
    }

    @RequestMapping(value = "/getAds")
    public List<Chabak> searchByAddress(@RequestParam(value = "address") String address) {
        return repository.searchByAddress(address);
    }
}
