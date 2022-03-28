package daeunn.daeunspring.service;

import daeunn.daeunspring.domain.Member;
import daeunn.daeunspring.repository.MemberRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional // 회원가입 할 때만 필요하긴 하다
// -> 회원가입의 모든 과정이 Transaction 안에서 수행되도록
public class MemberService {
    // private final MemberRepository memberRepository = new MemoryMemberRepository();

    // -> MemberService, MemberServiceTest가 같은 Repo를 쓰게 하기위한 수정
    // 1. new 삭제
    private final MemberRepository memberRepository;

    // 2. MemberRepository를 직접 생성하는것이 아닌, 외부에서 넣어주는 방식으로 수정
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member){
        // 같은이름 중복회원은 안된다
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        // null이 아니고 값이 있으면 동작하는 로직 <-> Optional
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

        // Optional member.ifPresent로 깔끔하게 코드 변경
        validateDuplicateMember(member);    // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // extracted method
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    /**
     * 전체회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * id로 특정회원 조회
     */
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
