package controller.service.map;

import database.DatabaseConnection;
import domain.BestAndCount;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import repository.ArticleRepository;
import repository.ChabakRepository;
import util.ConsoleUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
