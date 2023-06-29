//package organico.organico.controller
//
//import lombok.extern.slf4j.Slf4j
//import org.springframework.stereotype.Controller
//import org.springframework.ui.Model
//import org.springframework.web.bind.annotation.*
//import org.springframework.web.servlet.mvc.support.RedirectAttributes
//import organico.organico.domain.Member
//import organico.organico.service.MemberService
//
//@Controller
//@Slf4j
//@RequestMapping("/user")
//class MemberController2(
//        private val memberService: MemberService
//) {
//    // [회원가입/로그인]
//    // 가입 폼
//    @GetMapping("/signup")
//    fun signUpForm(): String {
//        return "user/sign-up"
//    }
//
//    // 가입
//    @PostMapping("/signup")
//    fun signUp(@ModelAttribute member: Member?, redirectAttributes: RedirectAttributes): String {
//        val result = memberService.signUp(member)
//        if (result.isEmpty) {
//            MemberController2.log.info("null")
//            return "redirect:/user/signup/error"
//        }
//        redirectAttributes.addAttribute("memberName", result.get().memberName)
//        MemberController2.log.info("!null")
//        return "redirect:/user/signup/success"
//    }
//
//    // 가입 - 성공
//    @GetMapping("/signup/success")
//    fun signUpSuccess(@RequestParam memberName: String?, model: Model): String {
//        model.addAttribute("memberName", memberName)
//        return "user/sign-up-success"
//    }
//
//    // 가입 - 실패(아이디 중복)
//    @GetMapping("/signup/error")
//    fun signUpError(): String {
//        return "user/sign-up-error"
//    }
//
//    // 로그인 폼
//    @GetMapping("/signin")
//    fun signInForm(): String {
//        return "user/sign-in"
//    }
//
//    // 로그인
//    @PostMapping("/signin")
//    fun signIn(@ModelAttribute member: Member, redirectAttributes: RedirectAttributes): String {
//        MemberController2.log.info("[controller] memberId : {}", member.memberId)
//        val result = memberService.signIn(member)
//        return if (result.isEmpty) {
//            "user/sign-in-error"
//        } else if (result.get().memberName == "admin") {
//            "redirect:/admin/items"
//        } else {
//            redirectAttributes.addAttribute("memberName", member.memberName)
//            "redirect:/user/signin/success"
//        }
//    }
//
//    // 로그인 성공
//    @GetMapping("/signin/success")
//    fun signInSuccess(@RequestParam memberName: String?, model: Model): String {
//        model.addAttribute("memberName", memberName)
//        return "user/sign-in-success"
//    }
//}
