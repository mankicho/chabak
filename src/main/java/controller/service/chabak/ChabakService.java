package controller.service.chabak;

import domain.Chabak;
import domain.facility.Utility;
import filter.Filter;
import filter.FishingFilter;
import filter.ToiletFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.ChabakRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/chabak")
public class ChabakService {
    private ChabakRepository repository;
    private final String filePath = "/resources/member/";

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

    @RequestMapping(value = "/getAds.do")
    public List<Chabak> searchByAddress(HttpServletRequest request, HttpServletResponse response) {
        String address = request.getParameter("address");
        return repository.searchByAddress(address);
    }

    @RequestMapping(value = "/eval.do")
    public boolean userEval(HttpServletRequest request) {
        String memberId = request.getParameter("mId");
        int placeId = Integer.parseInt(request.getParameter("pId"));
        String placeName = request.getParameter("pName");
        double eval = Double.parseDouble(request.getParameter("eval"));
        return repository.userEval(memberId, placeId, placeName, eval);
    }

    @RequestMapping(value = "/filter.do")
    public List<Chabak> filter(HttpServletRequest request) {
        String para = request.getParameter("para");

        String[] split = para.split("s");

        List<Filter> filters = new ArrayList<>();
        if (split[0].equals("T")) {
            filters.add(new ToiletFilter());
        }
        if (split[1].equals("T")) {
            filters.add(new FishingFilter());
        }

        return repository.getFilteredChabaks(filters);
    }

    /**
     * 차박지 등록하기
     */
    @RequestMapping(value = "/suggest.do")
    public String suggest(HttpServletRequest req){
        String placeName = req.getParameter("placeName");
        String address = req.getParameter("address");
        String introduce = req.getParameter("introduce");
        String phone = req.getParameter("phone");
        String fileName = req.getParameter("fileName");
        double latitude = Double.parseDouble(req.getParameter("latitude"));
        double longitude = Double.parseDouble(req.getParameter("longitude"));
        String urlPath = filePath + "suggest/" + fileName;

        return repository.suggest(placeName, address, introduce, phone, urlPath, latitude, longitude);
    }
}

