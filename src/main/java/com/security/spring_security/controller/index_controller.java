package com.security.spring_security.controller;

import com.security.spring_security.dto.MemberDto;
import com.security.spring_security.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Arrays;

@Controller
@RequiredArgsConstructor
@Slf4j

public class index_controller {

    @Autowired
    private MemberService memberService;

    @GetMapping("/")
    public String index(HttpSession httpSession, Model model, Principal principal) {
        if (httpSession.getAttribute("msg") != null) {
            model.addAttribute("msg", httpSession.getAttribute("msg"));
            httpSession.removeAttribute("msg");
        }
        return "index";
    }

    @GetMapping("/member/join")
    public String join() {
        return "member/join";
    }

    @PostMapping("/member/join")
    public String joinPost(MemberDto memberDto, RedirectAttributes redirectAttributes) {
        memberService.join(memberDto);
        redirectAttributes.addFlashAttribute("msg", "회원가입성공");
        return "redirect:/";
    }

    @GetMapping("/member/anyone")
    public void anyone() {}

    @PreAuthorize("isAnonymous()")
    @GetMapping("/member/anonymous")
    public void anonymous() {}

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/member/authenticated")
    public void authenticated() {}

    @GetMapping("/member/login")
    public void login() {}

    @GetMapping("/member/login/error")
    public String login(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", "로그인 실패");
        return "redirect:/member/login";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/member/user")
    public void user() {}

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board/list")
    public void list(Model model) {
        model.addAttribute("list", Arrays.asList(1, 2, 3));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board/contents")
    public void contents() {}

    @Secured("ROLE_ADMIN")
    @GetMapping("/member/admin")
    public void admin() {}
}
