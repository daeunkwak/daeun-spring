package daeunn.daeunspring.service;

import daeunn.daeunspring.domain.Member;
import daeunn.daeunspring.repository.MemberRepository;
import daeunn.daeunspring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    // MemberService memberService = new MemberService();
    //MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    // 이렇게 memberRepository를 생성하면 MemberService class에서 생성한 repository와
    // MemberServiceTest에서 생성한 repository가 다른 인스턴스라는 문제점이 발생
    // 지금은 MemberService에서 private static Map<Long, Member> static으로 선언되어서 괜찮지만
    // static이 아니면 다른 DB가 되는 상황 -> 문제 발생

    // => 해결 <-> BeforeEach
    // MemberService memberService;
    // MemoryMemberRepository memberRepository;

    @BeforeEach // 3. 동작하기 전에 memberRepository를 직접 넣어준다 <-> DI(Dependency Injection)
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }


    // 여기서도 callback method 함수가 끝날 때마다 호출
//    @AfterEach
//    public void afterEach(){
//        memberRepository.clearStore();
//    }

    @Test
    @Commit // -> DB에 반영됨
    void 회원가입() {
        // 중복검사를 해서 예외가 터지는지도 잘 확인해야함 -> 중복검사 메소드도 생성
        // given
        Member member = new Member();
        member.setName("hello");

        // when
        Long saveId = memberService.join(member);

        // 우리가 저장한게 repository에 있는 내용인지 확인하고 싶은 경우
        // -> 레포를 꺼내야함
        // then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void 중복_회원_예외(){
        // given
        Member member1 = new Member();
        member1.setName("springg");

        Member member2 = new Member();
        member2.setName("springg");

        // when
        memberService.join(member1);

        // 2. 제공하는 기능 <-> lambda 함수
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        // 1. try-catch구문 사용하는 방법
//        try{
//            memberService.join(member2);
//            fail();
//        } catch(IllegalStateException e){ // Ctrl + Alt + B로 exception이름 확인
//            // catch로 오면 성공
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

        // then
    }

   @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}