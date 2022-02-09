package com.example.memberapi.controller;

import com.example.memberapi.entities.Member;
import com.example.memberapi.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {

    @Autowired
    private IMemberService memberService;

    @GetMapping("/members")
    public List<Member> getAllMembers() {
        return memberService.getAll();
    }

}
