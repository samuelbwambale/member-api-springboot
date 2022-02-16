package com.example.memberapi.service;

import com.example.memberapi.entities.Member;
import com.example.memberapi.repository.IMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class MemberService implements IMemberService{

    @Autowired
    private IMemberRepository memberRepository;

    @Override
    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Member addMember(Member member) {
        member.setCreateTs(LocalDate.now());
        member.setLastUpdatedTs(LocalDate.now());
        return memberRepository.save(member);
    }

    @Override
    public Member updateMember(Long id, Member member) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isEmpty()) {
            member.setCreateTs(LocalDate.now());
        } else member.setCreateTs(optionalMember.get().getCreateTs());
        member.setLastUpdatedTs(LocalDate.now());
        return memberRepository.save(member);
    }

    @Override
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

}
