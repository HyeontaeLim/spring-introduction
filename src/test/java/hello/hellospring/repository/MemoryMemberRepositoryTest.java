package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemberRepository repository = new MemoryMemberRepository();


    @Test
    void save() {
        Member member1 = new Member();
        member1.setName("spring");

        repository.save(member1);

        Member result = repository.findById(member1.getId()).get();
        assertThat(member1).isEqualTo(result);
    }

    @Test
    void findById() {
        Member member1 = new Member();
        member1.setName("임현태");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("홍길동");
        repository.save(member2);

        Member result = repository.findById(member1.getId()).get();
        assertThat(member1).isEqualTo(result);;
    }

    @Test
    void findByName() {
        Member member1 = new Member();
        member1.setName("임현태");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("홍길동");
        repository.save(member2);

        Member result = repository.findByName("임현태").get();
        assertThat(member1).isEqualTo(result);
    }

    @Test
    void findAll() {
        Member member1 = new Member();
        member1.setName("임현태");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("홍길동");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}