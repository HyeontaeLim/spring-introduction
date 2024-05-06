package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
 @Transactional
class MemberServiceIntegratedTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void join() {
        //given
        Member member1 = new Member();
        member1.setName("임현태");

        //when
        Long savedId = memberService.join(member1);

        //then
        Member result = memberService.findOne(savedId).get();
        assertThat(member1.getName()).isEqualTo(result.getName());
    }

    @Test
    void validateDuplicateMember() {
        //given
        Member member1 = new Member();
        member1.setName("임현태");

        Member member2 = new Member();
        member2.setName("임현태");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        /*try {
            memberService.join(member2);
            fail();
        }catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/

    }


    @Test
    void findMembers() {
        Member member1 = new Member();
        member1.setName("임현태");

        Member member2 = new Member();
        member2.setName("홍길동");

        memberService.join(member1);
        memberService.join(member2);

        //when
        List<Member> result = memberService.findMembers();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findOne() {
        Member member1 = new Member();
        member1.setName("임현태");

        Member member2 = new Member();
        member2.setName("홍길동");

        memberService.join(member1);
        memberService.join(member2);

        //when
        Member result = memberService.findOne(member1.getId()).get();

        //then
        assertThat(result.getId()).isEqualTo(member1.getId());
    }
}