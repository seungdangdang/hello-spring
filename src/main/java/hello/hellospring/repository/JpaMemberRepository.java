package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em; //jpa는 EntityManager로 모든걸 동작함

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
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
        // 아래의 " select~m"는 jpql이라는 쿼리의 언어, 객체를 대상으로 쿼리를 날림 (본래 테이블단위로 날림)
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    // find by name이나 find all 같은 pk기반이 아닌 나머지들은 jpql이라는 것을 작성해야함 = 기본적인 CRUD는 하지만 select는 JPQL이 필요함
}