package com.example.memberapi.service;

import com.example.memberapi.entities.Member;

import java.util.List;

public interface IMemberService {

    List<Member> getAll();
    Long addMember(Member member);
    Long updateMember(Member member);
}
