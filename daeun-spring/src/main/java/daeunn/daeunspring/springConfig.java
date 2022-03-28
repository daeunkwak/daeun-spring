package daeunn.daeunspring;

import daeunn.daeunspring.repository.JpaMemberRepository;
import daeunn.daeunspring.repository.MemberRepository;
import daeunn.daeunspring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class springConfig {

    // em을 받는다
    private EntityManager em;

    // Spring에서 DI 해준다
    @Autowired
    public springConfig(EntityManager em) {
        this.em = em;
    }

    // 스프링이 Configuration을 읽고 Bean에 등록해줌
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        // return new MemoryMemberRepository();
        return new JpaMemberRepository(em);
    }
}
