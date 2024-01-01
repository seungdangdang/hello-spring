// 서비스는 비지니스 로직에 의존적으로 설계

package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Service (직접 스프링 빈 등록하기 위해 애노테이션 없앰)

@Transactional //jpa를 쓰려면 항상 트랜잭셔널 애노테이션이 있어야함
public class MemberService {

    private final MemberRepository memberRepository;

    //    @Autowired (직접 스프링 빈 등록하기 위해 애노테이션 없앰)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

/*
의존성주입 3가지 방법이 있음
1. 필드에 생성 -> 비추천
2. 아래와 같이 set메서드 만들어서 사용 -> public 메서드를 아무 개발자가 쓸 수 있음
3. 생성자에 주입 -> best, 생성하는 시점에만 쓰기 때문
   */

    //회원가입
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 중복회원찾는메서드
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName((member.getName()))
                .ifPresent(m -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });
    }

    //전체 회원 조회 (비지니스메서드)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //멤버 id 조회 (비지니스메서드)
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}