package controller.service.chabak;

import domain.Chabak;
import domain.facility.Utility;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.ChabakRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/chabak")
public class ChabakService {
    private ChabakRepository repository;

    public ChabakService() {
        repository = new ChabakRepository();
    }

    @RequestMapping(value = "/get.do")
    public List<Chabak> getChabakTenAtATime(HttpServletRequest request) {
        String num = request.getParameter("num");
        String address = request.getParameter("add");
        String fish = request.getParameter("fish");
        String toilet = request.getParameter("toilet");

        if (num == null) {
            return repository.getChabaks();
        }
        int n;
        try {
            n = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            n = 0;
        }
        // 2초 정도 걸리는작업이라고 가정.
        return repository.getChabaks(n);
    }

    @RequestMapping(value = "/getKey.do")
    public List<Chabak> searchByKeyword(HttpServletRequest request, HttpServletResponse response) {
        String key = request.getParameter("key");
        return repository.searchByKeyword(key);
    }

    @RequestMapping(value = "getAds.do")
    public List<Chabak> searchByAddress(HttpServletRequest request, HttpServletResponse response) {
        String address = request.getParameter("address");
        return repository.searchByAddress(address);
    }

    @RequestMapping(value = "test.do")
    public Map<Chabak,List<Utility>> test(){
        return repository.getChabakWithUtility();
    }
//    @RequestMapping(value = "getFiltered.do")
}

