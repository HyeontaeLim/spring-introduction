package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;

    @BeforeEach
    void beforeEach() {
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }

    @AfterEach
    void afterEach() {
        memoryMemberRepository.clearStore();
    }

    @Test
    void join() {
        //given
        Member member1 = new Member();
        member1.setName("임현태");

        //when
        Long savedId =  memberService.join(member1);

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
        assertThat(result).isEqualTo(member1);
    }
}