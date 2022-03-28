package daeunn.daeunspring.repository;

import daeunn.daeunspring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store = new HashMap<>();
    // 실무에서는 공유되는 hashmap을 사용할 때 동시성 문제를 해결하기 위해concreteHashMap 사용함
    private static long sequence = 0L;
    // 키값 생성해주는 역할, 역시 실무에서는 long보다는 autumn long 등을 사용한다

    @Override
    public Member save(Member member) {
        member.setId(++sequence);   // id setting
        store.put(member.getId(), member);  // store
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        // 결과값이 null인 경우? -> optional로 감싸준다
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    // 실무에서 list를 많이 사용한다
    @Override
    public List<Member> findAll() {
        // values <-> members
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
