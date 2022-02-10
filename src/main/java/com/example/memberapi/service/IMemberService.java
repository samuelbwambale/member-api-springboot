package com.example.memberapi.service;

import com.example.memberapi.entities.Member;

import java.util.List;
import java.util.Optional;

public interface IMemberService {

    List<Member> getAll();
    Optional<Member> getMemberById(Long id);
    Member addMember(Member member);
    Member updateMember(Long id, Member member);
}
