package com.example.memberapi.service;

import com.example.memberapi.entities.Member;
import com.example.memberapi.repository.IMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public Long addMember(Member member) {
        return memberRepository.save(member).getId();
    }

    @Override
    public Long updateMember(Member member) {
        return memberRepository.save(member).getId();
    }
}
