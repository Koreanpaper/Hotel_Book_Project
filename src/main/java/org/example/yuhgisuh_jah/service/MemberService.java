package org.example.yuhgisuh_jah.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.yuhgisuh_jah.model.Member;
import org.example.yuhgisuh_jah.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join (String username, String password) {

        if(memberRepository.existsByUsername(username)){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

        Member member = new Member();
        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password));
        member.setRole("ROLE_USER");
        memberRepository.save(member);
    }
}
