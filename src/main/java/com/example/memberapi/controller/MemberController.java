package com.example.memberapi.controller;

import com.example.memberapi.entities.Member;
import com.example.memberapi.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private IMemberService memberService;

//    @GetMapping
//    public ResponseEntity<List<Member>> getAllMembers() {
//        List<Member> responseList = memberService.getAll();
//        return new ResponseEntity<>(responseList,HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<Member> getMemberById(
            @RequestParam Long memberId
    ) {
        Optional<Member> memberOptional = memberService.getMemberById(memberId);
        return memberOptional.map(member -> new ResponseEntity<>(member, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Member> addNewMember(
            @Valid @RequestBody Member memberInfo) {
        Member response = memberService.addMember(memberInfo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<Member> updateMemberInfo(
            @Valid @RequestBody Member memberInfo,
            @PathVariable Long memberId) {
        Member response = memberService.updateMember(memberId, memberInfo);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
