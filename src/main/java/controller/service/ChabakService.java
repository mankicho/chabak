package controller.service;

import domain.Chabak;
import domain.Review;
import domain.facility.Fishing;
import domain.facility.Toilet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.ChabakRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping(value = "/chabak")
public class ChabakService {
    private ChabakRepository repository;

    public ChabakService() {
        repository = new ChabakRepository();
    }

    /**
     * 모든 차박지 리스트
     */
    @RequestMapping(value = "/get.do")
    public List<Chabak> getAllChabakList() { return repository.getAllChabakList(); }

    /**
     * 하나의 차박지 정보
     */
    @RequestMapping(value = "/getOne.do")
    public List<Chabak> getOne(HttpServletRequest request) {
        return repository.getOne(Integer.parseInt(request.getParameter("placeId")));
    }

    /**
     * 현재 인기있는 차박지 리스트 (별점 기준 상위 10개)
     */
    @RequestMapping(value = "/getPopularList.do")
    public List<Chabak> getPopularList(){
        return repository.getPopularList();
    }

    /**
     * 차박지별 화장실 정보
     */
    @RequestMapping(value = "/getToilets.do")
    public List<Toilet> getToilets(HttpServletRequest request){
        int placeId = Integer.parseInt(request.getParameter("placeId"));
        return repository.getToilets(placeId);
    }

    /**
     * 차박지별 낚시터 정보
     */
    @RequestMapping(value = "/getFishings.do")
    public List<Fishing> getFishings(HttpServletRequest request){
        int placeId = Integer.parseInt(request.getParameter("placeId"));
        return repository.getFishings(placeId);
    }

    /**
     * 차박지 평가
     */
    @RequestMapping(value = "/eval.do")
    public int userEval(HttpServletRequest request) {
        String memberId = request.getParameter("mId");
        int placeId = Integer.parseInt(request.getParameter("pId"));
        String placeName = request.getParameter("pName");
        double eval = Double.parseDouble(request.getParameter("eval"));
        String review = request.getParameter("review");
        return repository.userEval(memberId, placeId, placeName, eval, review);
    }

    /**
     * 차박지 검색
     */
    @RequestMapping(value = "/filter.do")
    public List<Chabak> getFilteredList(HttpServletRequest request) {
        String flags = request.getParameter("flags");
        String address = request.getParameter("add");
        String[] addresses = address.split("/");
        String[] split = flags.split("/");
        System.out.println(Arrays.toString(split)+"ASD");
        return repository.getFilteredList(addresses,split);
    }

    /**
     * 광역시, 도별 차박지 리스트
     */
    @RequestMapping(value = "/getProvinceChabakList.do")
    public List<Chabak> getProvinceChabakList(HttpServletRequest request) {
        String province = request.getParameter("province");
        return repository.getProvinceChabakList(province);
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
        String urlPath = "/resources/suggest/" + fileName;

        return repository.suggest(placeName, address, introduce, phone, urlPath, latitude, longitude);
    }

    /**
     * 차박지별 등록된 리뷰 읽기
     */
    @RequestMapping(value = "/getReviews.do")
    public List<Review> getReviews(HttpServletRequest request){
        return repository.getReviews(Integer.parseInt(request.getParameter("placeId")));
    }
}

