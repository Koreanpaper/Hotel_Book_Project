package org.example.yuhgisuh_jah.controller;

import lombok.RequiredArgsConstructor;
import org.example.yuhgisuh_jah.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/join")
    public String join(Model model) {return "join";}

    @PostMapping("/join")
    public String join(@RequestParam String username,@RequestParam String password) {
       try {
           memberService.join(username, password);
           return "redirect:/login";
       }catch (Exception e){return "redirect:/join?error";}
    }
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}
