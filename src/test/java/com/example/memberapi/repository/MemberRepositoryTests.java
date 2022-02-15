package com.example.memberapi.repository;

import com.example.memberapi.entities.Member;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTests {

    @Autowired
    private IMemberRepository memberRepository;

    private Member testMember;

    @Before
    public void setUp() {
        Member member = new Member(null, "Bill", "John",
                LocalDate.of(2022, 2, 11), LocalDate.of(2022, 2, 11));
        testMember = memberRepository.save(member);
    }

    @Test
    public void testSaveMember() {
        Member member = new Member(null, "Alex", "Tim", LocalDate.now(), LocalDate.now());
        Member savedMember = memberRepository.save(member);
        assertNotNull(savedMember.getId());
        assertEquals(savedMember.getFirstName(), member.getFirstName());
        assertEquals(savedMember.getCreateTs(), member.getCreateTs());

    }

    @Test
    public void testFindMemberById() {
        assertEquals(testMember, memberRepository.findById(testMember.getId()).get());
    }

    @Test
    public void testUpdateMember() {
        Member newMemberInfo = new Member(testMember.getId(), "NewFirstName", "NewLastName", testMember.getCreateTs(), LocalDate.now());
        memberRepository.save(newMemberInfo);
        testMember = memberRepository.findById(testMember.getId()).get();
        assertEquals(testMember.getFirstName(), newMemberInfo.getFirstName());
    }

    @Test
    public void testDeleteMember() {
        Member additionalMember = new Member(null, "Allan", "Smith", LocalDate.now(), LocalDate.now());
        memberRepository.save(additionalMember);
        List<Member> memberList = memberRepository.findAll();

        memberRepository.deleteById(testMember.getId());
        List<Member> currentMemberList = memberRepository.findAll();
        assertEquals(memberList.size() - 1, currentMemberList.size());
    }


}
