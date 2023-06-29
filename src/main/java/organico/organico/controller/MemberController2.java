//package organico.organico.controller;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import organico.organico.domain.Member;
//import organico.organico.service.MemberService;
//
//import java.util.Optional;
//
//@Controller
//@Slf4j
//@RequestMapping("/user")
//public class MemberController2 {
//    private final MemberService memberService;
//
//    @Autowired
//    public MemberController2(MemberService memberService) {
//        this.memberService = memberService;
//    }
//
//    // [회원가입/로그인]
//    // 가입 폼
//    @GetMapping("/signup")
//    public String signUpForm() {
//        return "user/sign-up";
//    }
//
//    // 가입
//    @PostMapping("/signup")
//    public String signUp(@ModelAttribute Member member, RedirectAttributes redirectAttributes) {
//        Optional<Member> result = memberService.signUp(member);
//
//        if (result.isEmpty()) {
//            log.info("null");
//            return "redirect:/user/signup/error";
//        }
//        redirectAttributes.addAttribute("memberName", result.get().getMemberName());
//        log.info("!null");
//        return "redirect:/user/signup/success";
//    }
//
//    // 가입 - 성공
//    @GetMapping("/signup/success")
//    public String signUpSuccess(@RequestParam String memberName, Model model) {
//        model.addAttribute("memberName", memberName);
//        return "user/sign-up-success";
//    }
//
//    // 가입 - 실패(아이디 중복)
//    @GetMapping("/signup/error")
//    public String signUpError() {
//        return "user/sign-up-error";
//    }
//
//    // 로그인 폼
//    @GetMapping("/signin")
//    public String signInForm() {
//        return "user/sign-in";
//    }
//
//    // 로그인
//    @PostMapping("/signin")
//    public String signIn(@ModelAttribute Member member, RedirectAttributes redirectAttributes) {
//        log.info("[controller] memberId : {}", member.getMemberId());
//        Optional<Member> result = memberService.signIn(member);
//
//        if (result.isEmpty()) {
//            return "user/sign-in-error";
//        } else if (result.get().getMemberName().equals("admin")) {
//            return "redirect:/admin/items";
//        } else {
//            redirectAttributes.addAttribute("memberName", member.getMemberName());
//            return "redirect:/user/signin/success";
//        }
//    }
//
//    // 로그인 성공
//    @GetMapping("/signin/success")
//    public String signInSuccess(@RequestParam String memberName, Model model) {
//        model.addAttribute("memberName", memberName);
//        return "user/sign-in-success";
//    }
//}
