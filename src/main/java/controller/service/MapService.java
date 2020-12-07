package controller.service;

import domain.BestAndCount;
import domain.Chabak;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import repository.ChabakRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/map")
public class MapService {
    private final ChabakRepository chabakRepository = new ChabakRepository();

    @RequestMapping("/")
    public ModelAndView map(){
        ModelAndView mav = new ModelAndView();
        Map<String, BestAndCount> map = chabakRepository.getBestAndCount();
        mav.addObject("BestAndCount", map );
        mav.setViewName("map/map");
        return mav;
    }
}
