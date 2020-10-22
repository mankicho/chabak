package controller.service.member;

import domain.Member;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/member")
public class MemberService {
    MemberRepository memberRepository;

    public MemberService() {
        this.memberRepository = new MemberRepository();
    }

    @RequestMapping(value = "/login.do")
    public Member login(HttpServletRequest request) throws Exception {
        String userId = request.getParameter("id");
        String userPassword = request.getParameter("password");
        return memberRepository.select(userId, userPassword);
    }

    @RequestMapping(value = "/insert.do")
    public String register(HttpServletRequest request) {
        String id = request.getParameter("id");
        String nickName = request.getParameter("nickName");
        String pw = request.getParameter("password");

        Member member = new Member(id, nickName, pw);
        return memberRepository.insert(member);
    }

    @RequestMapping(value = "/select.do")
    public Member select(HttpServletRequest request) {
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        return memberRepository.select(id, password);
    }

    @RequestMapping(value = "/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String nickName = request.getParameter("nickName");
        String password = request.getParameter("password");
        System.out.println(id+","+nickName+","+password);

        return memberRepository.update(id, nickName, password);
    }
}
