package controller.service.member;

import domain.Chabak;
import domain.member.Member;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/member")
public class MemberService {
    MemberRepository memberRepository;

    public MemberService() {
        this.memberRepository = new MemberRepository();
    }

    @RequestMapping(value = "/login.do")
    public String login(HttpServletRequest request) throws Exception {
        System.out.println(getClass().getName()+" is called login");

        String userId = request.getParameter("id");
        String userPassword = request.getParameter("password");
        return memberRepository.select(userId, userPassword);
    }

    @RequestMapping(value = "/insert.do")
    public String register(HttpServletRequest request) {
        System.out.println(getClass().getName()+" insert");

        String id = request.getParameter("id");
        String nickName = request.getParameter("nickName");
        String pw = request.getParameter("password");

        Member member = new Member(id, nickName, pw);
        return memberRepository.insert(member);
    }


    @RequestMapping(value = "/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String nickName = request.getParameter("nickName");
        String password = request.getParameter("password");
        System.out.println(id + "," + nickName + "," + password);

        return memberRepository.update(id, nickName, password);
    }

    @RequestMapping(value = "/getJJim.do")
    public List<Chabak> getJjimList(HttpServletRequest request) {
        String id = request.getParameter("id");

        return memberRepository.getJJimList(id);
    }

    @RequestMapping(value = "/jjim.do")
    public String jjimDo(HttpServletRequest request) {
        String id = request.getParameter("id");
        String placeName = request.getParameter("placeName");

        System.out.println("id = " + id + ", placeName " + placeName);
        return memberRepository.jjimDo(id, placeName);
    }

    @RequestMapping(value = "/jjim.undo")
    public String jjimUndo(HttpServletRequest request) {
        String id = request.getParameter("id");
        String placeName = request.getParameter("placeName");

        return memberRepository.jjimUndo(id, placeName);
    }
}
