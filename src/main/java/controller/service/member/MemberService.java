package controller.service.member;

import crypto.CryptoUtil;
import domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import repository.MemberRepository;

@Controller
@RequestMapping(value = "/member")
public class MemberService {
    MemberRepository memberRepository;

    public MemberService() {
        this.memberRepository = new MemberRepository();
    }

    public Member login(String userId, String userPassword) throws Exception {
        return memberRepository.select(userId, userPassword);
    }

    @RequestMapping(value = "/insert.do")
    public void register(@RequestParam(value = "id") String id, @RequestParam(value = "nickName") String nickName, @RequestParam(value = "pw") String pw) {

        try {
            Member member = new Member(id, nickName, pw);
            memberRepository.insert(member);
        } catch (Exception e) {
            System.out.println("member 생성이 안됨");
        }
    }

    @RequestMapping(value = "/select.do")
    public Member select(@RequestParam(value = "id") String id, @RequestParam(value = "pw") String password) {
        try {
            Member member = memberRepository.select(id, password);
            System.out.println(member);
            return member;
        } catch (Exception e) {
        }
        return new Member("", "", "");
    }

    public void update() {

    }
}
