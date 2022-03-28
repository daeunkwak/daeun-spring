package daeunn.daeunspring.repository;

import daeunn.daeunspring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // callback method 함수가 끝날 때마다 호출
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    // main함수 작성 느낌
    @Test
    public void save(){
        // given
        Member member = new Member();
        member.setName("spring");

        // when
        repository.save(member);

        // then
        // get()을 통해 Optional 객체 꺼내기
        Member result = repository.findById(member.getId()).get();
        // System.out.println("result = " + (result == member));

        // result, member가 같은지 확인 가능
        // Assertions.assertEquals(member, result);
        // Assertions.assertEquals(member, null);

        // 요즘 많이 사용하는 방식, isEqualTo로 방향성 헷갈릴 일 xx
        // Assertions.assertThat(member).isEqualTo(result);
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        // given
        // spring1, spring2라는 회원이 가입된 상황
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring1");
        repository.save(member2);

        // when
        Member result = repository.findByName("spring1").get();

        // then
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring1");
        repository.save(member2);

        // when
        List<Member> result = repository.findAll();

        // then
        // member 2개를 만들고 isEqaulTo(3)으로 돌리면 Error
        assertThat(result.size()).isEqualTo(2);

    }

}














