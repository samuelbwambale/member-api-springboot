package com.example.memberapi.service;

import com.example.memberapi.entities.Member;
import com.example.memberapi.repository.IMemberRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MemberServiceTests {

    @Mock
    private IMemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    Member member1 = new Member(1L, "John", "Joe", LocalDate.parse("2022-02-10"), LocalDate.parse("2022-02-11"));
    Member member2 = new Member(2L, "Bill", "Smith", LocalDate.parse("2022-02-11"), LocalDate.parse("2022-02-11"));
    List<Member> memberList = List.of(member1, member2);

    @Test
    public void testGetMemberById() {
        when(memberRepository.findById(memberList.get(0).getId()))
                .thenReturn(Optional.of(memberList.get(0)));
        Optional<Member> response = memberService.getMemberById(memberList.get(0).getId());
        Member member = response.get();
        assertNotNull(member);
        assertEquals(memberList.get(0).getFirstName(), member.getFirstName());
        assertEquals(memberList.get(0).getCreateTs(), member.getCreateTs());
    }

    @Test
    public void testAddMember() {
        when(memberRepository.save(memberList.get(0))).thenReturn(memberList.get(0));
        Member response = memberService.updateMember(1L, memberList.get(0));
        assertNotNull(response);
        assertEquals(memberList.get(0).getLastName(), response.getLastName());
    }

    @Test
    public void testUpdateMember() {
        Member member = new Member(null, "Alex", "Robert", null, null);
        Member updatedMember = new Member(1L, "Alex", "Robert", LocalDate.parse("2022-02-10"), LocalDate.now());
        when(memberRepository.save(member)).thenReturn(updatedMember);
        Member response = memberService.updateMember(1L, member);
        assertNotNull(response);
        assertEquals(response.getLastName(), member.getLastName());
    }

    @Test
    public void testDeleteMember() {
        when(memberRepository.findById(memberList.get(0).getId()))
                .thenReturn(Optional.of(memberList.get(0)));

        memberService.deleteMember(memberList.get(0).getId());
        verify(memberRepository, times(1)).deleteById(memberList.get(0).getId());

    }
}
