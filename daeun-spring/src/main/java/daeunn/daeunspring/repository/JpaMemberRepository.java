package daeunn.daeunspring.repository;

import daeunn.daeunspring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    // JPA는 EntityManager로 모든게 동작한다
    // 자동으로 스프링 부트가 생성해주면,, 이 만들어진걸 injection 받으면 됨!
    // -> JPA를 쓰려면 EntityManager를 주입받아야 한다!
    // CRUD는 SQL을 작성할 필요가 없음 <-> pk기반 아닌 나머지는 SQL을 작성해야함
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // find(조회할 타입, 식별자) -> select문 생성
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // inline variables <-> result 합치기
        // Jpql Query -> 객체를 대상으로 쿼리를 날린다 (entity 대상)
        // 객체 자체를 select한다
        return em.createQuery("select m from Member as m", Member.class)
                .getResultList();
    }
}
