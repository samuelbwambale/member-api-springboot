package com.example.memberapi.repository;

import com.example.memberapi.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMemberRepository extends JpaRepository<Member, Long> {
}

