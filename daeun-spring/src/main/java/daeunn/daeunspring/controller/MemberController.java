package daeunn.daeunspring.controller;

import daeunn.daeunspring.domain.Member;
import daeunn.daeunspring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public class MemberController {
    // 다음과같이 memberService를 생성하면 memberController 말고 다른 controller에서도
    // memberService를 가져다 쓸 수 있는 상황이라 문제가 됨
    // private final MemberService memberService = new MemberService();

    private final MemberService memberService;

    // Autowired -> 스프링 컨테이너에서 memberService를 가져온다

    @Autowired
    // memberService 빨간줄 -> @Service
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 등록 1. 뼈대
    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    // 등록 4.
    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/"; // 회원가입 끝나면 home으로 되돌아감감
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}





















