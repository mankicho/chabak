package controller.service.chabak;

import domain.Chabak;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.ChabakRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping(value = "chabak")
public class ChabakService {
    private ChabakRepository repository;

    public ChabakService() {
        repository = new ChabakRepository();
    }

    @RequestMapping(value = "get")
    public List<Chabak> getAllChabaks() {
        return repository.getChabaks();
    }

    @RequestMapping(value = "getKey")
    public List<Chabak> searchByKeyword(HttpServletRequest request, HttpServletResponse response) {
        String key = request.getParameter("key");
        System.out.println(key);
        return repository.searchByKeyword(key);
    }

    @RequestMapping(value = "getAds")
    public List<Chabak> searchByAddress(HttpServletRequest request, HttpServletResponse response) {
        String address = request.getParameter("address");
        return repository.searchByAddress(address);
    }
}
