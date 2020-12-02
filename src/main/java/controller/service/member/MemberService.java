package controller.service.member;

import domain.Chabak;
import domain.Review;
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

    /**
     * 로그인
     */
    @RequestMapping(value = "/login.do")
    public String login(HttpServletRequest request) {
        System.out.println(getClass().getName()+" is called login");

        String userId = request.getParameter("id");
        String userPassword = request.getParameter("password");
        return memberRepository.select(userId, userPassword);
    }

    /**
     * 회원가입
     */
    @RequestMapping(value = "/insert.do")
    public String register(HttpServletRequest request) {
        System.out.println(getClass().getName()+" insert");

        String id = request.getParameter("id");
        String nickName = request.getParameter("nickName");
        String pw = request.getParameter("password");

        Member member = new Member(id, nickName, pw);
        return memberRepository.insert(member);
    }

    /**
     * 닉네임 중복확인
     */
    @RequestMapping(value = "/nickDoubleCheck.do")
    public String nickDoubleCheck(HttpServletRequest request){
        System.out.println(getClass().getName()+" 닉네임 중복확인");
        String nickName = request.getParameter("nickName");
        return String.valueOf(memberRepository.nickDoubleCheck(nickName));
    }

    /**
     * 이메일(아이디) 중복확인
     */
    @RequestMapping(value = "/idDoubleCheck.do")
    public String idDoubleCheck(HttpServletRequest request){
        System.out.println(getClass().getName()+" 이메일 중복확인");
        String memberId = request.getParameter("memberId");
        return String.valueOf(memberRepository.idDoubleCheck(memberId));
    }

    /**
     * 비밀번호 변경
     */
    @RequestMapping(value = "/changePassword.do")
    public String changePassword(HttpServletRequest request) {
        String memberId = request.getParameter("memberId");
        String password = request.getParameter("password");
        System.out.println("changePassword : " + memberId + "," + password);

        return String.valueOf(memberRepository.changePassword(memberId, password));
    }

    /**
     * 닉네임 변경
     */
    @RequestMapping(value = "/changeNickname.do")
    public String changeNickname(HttpServletRequest request) {
        String memberId = request.getParameter("memberId");
        String nickName = request.getParameter("nickName");
        System.out.println("changeNickname : " + memberId + "," + nickName);

        return String.valueOf(memberRepository.changeNickname(memberId, nickName));
    }

    /**
     * 회원 탈퇴
     */
    @RequestMapping(value = "/withdraw.do")
    public String withdraw(HttpServletRequest request){
        String memberId = request.getParameter("memberId");
        System.out.println("[회원탈퇴] " + memberId);
        return String.valueOf(memberRepository.withdraw(memberId));
    }

    /**
     * 사용자의 차박지 찜 리스트 가져오기
     */
    @RequestMapping(value = "/getJJim.do")
    public List<Chabak> getJjimList(HttpServletRequest request) {
        String id = request.getParameter("id");

        return memberRepository.getJJimList(id);
    }

    /**
     * 차박지 찜
     */
    @RequestMapping(value = "/jjim.do")
    public String jjimDo(HttpServletRequest request) {
        String id = request.getParameter("id");
        int placeId = Integer.parseInt(request.getParameter("placeId"));
        String placeName = request.getParameter("placeName");

        return memberRepository.jjimDo(id, placeId, placeName);
    }

    /**
     * 차박지 찜 취소
     */
    @RequestMapping(value = "/jjim.undo")
    public String jjimUndo(HttpServletRequest request) {
        String memberId = request.getParameter("id");
        int placeId = Integer.parseInt(request.getParameter("placeId"));

        return memberRepository.jjimUndo(memberId, placeId);
    }

    /**
     * 사용자의 특정 차박지 찜, 평가 여부 가져오기
     */
    @RequestMapping(value = "/getJJimAndEvaluated.do")
    public String getJJimAndEvaluated(HttpServletRequest request) {
        String memberId = request.getParameter("memberId");
        int placeId = Integer.parseInt(request.getParameter("placeId"));

        String isJJim = memberRepository.isJJim(memberId, placeId);
        String isEvaluated = memberRepository.isEvaluated(memberId, placeId);

        return isJJim + " " + isEvaluated;
    }

    /**
     * 사용자가 작성한 리뷰 가져오기
     */
    @RequestMapping(value = "/getUsersReview.do")
    public List<Review> getUsersReview(HttpServletRequest request) {
        String memberId = request.getParameter("memberId");

        return memberRepository.getUsersReview(memberId);
    }
}
