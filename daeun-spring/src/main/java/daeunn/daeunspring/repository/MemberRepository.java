package daeunn.daeunspring.repository;

import daeunn.daeunspring.domain.Member;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id); // optional기능 공부
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
