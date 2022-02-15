package com.example.memberapi.controller;

import com.example.memberapi.entities.Member;
import com.example.memberapi.service.IMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/member")
@Slf4j(topic = "MemberController")
public class MemberController {

    @Autowired
    private IMemberService memberService;

    @GetMapping
    public ResponseEntity<Member> getMemberById(
            @RequestParam Long memberId
    ) {
        log.info("getMemberById: {}", memberId);
        Optional<Member> memberOptional = memberService.getMemberById(memberId);
        return memberOptional.map(member -> new ResponseEntity<>(member, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Member> addNewMember(
            @Valid @RequestBody Member memberInfo) {
        log.info("addNewMember with info: {}", memberInfo);
        Member response = memberService.addMember(memberInfo);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<Member> updateMemberInfo(
            @Valid @RequestBody Member memberInfo,
            @PathVariable Long memberId) {
        log.info("updateMemberInfo for member with id: {} and new info: {}", memberId, memberInfo);
        Member response = memberService.updateMember(memberId, memberInfo);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<String> deleteMemberById(
            @RequestParam Long memberId
    ) {
        log.info("deleteMemberById: {}", memberId);
        Optional<Member> memberOptional = memberService.getMemberById(memberId);
        return memberOptional.map(member -> {
            memberService.deleteMember(memberId);
            return new ResponseEntity<>("Member successfully deleted", HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Member>> getAllMembers() {
        log.info("getAllMembers");
        List<Member> responseList = memberService.getAll();
        return new ResponseEntity<>(responseList,HttpStatus.OK);
    }

}
