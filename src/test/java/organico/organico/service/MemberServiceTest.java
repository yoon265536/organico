//package organico.organico.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import lombok.extern.slf4j.Slf4j;
//import organico.organico.domain.Member;
//import organico.organico.repository.MemberRepository;
//
//@SpringBootTest
//@Slf4j
//class MemberServiceTest {
//
//	@Autowired private MemberService memberService;
//
//	@Test
//	void 가입ID중복확인() {
//		Member memberOne = new Member("홍길동", "id입니다", "123");
//		memberService.signUp(memberOne);
//
//		Member memberTwo = new Member("박보검", "id입니다", "456");
//		Optional<Member> result = memberService.signUp(memberTwo);
//
//		assertThat(result).isEmpty();
//	}
//
//	@Test
//	void 로그인성공() {
//		Member member = new Member("이미자", "lee", "789");
//		memberService.signUp(member);
//		Member login = new Member("lee", "789");
//
//		Optional<Member> result = memberService.signIn(login);
//
//		assertThat(result).isNotEmpty();
//	}
//
//	@Test
//	void 로그인실패() {
//		Member member = new Member("기린", "kirin", "0000");
//		Member login = new Member("kirin1", "0000");
//
//		Optional<Member> result = memberService.signIn(login);
//
//		assertThat(result).isEmpty();
//	}
//
//	@Test
//	void 아이디조회() {
//		Member member = new Member("고등어", "ko", "123123");
//		memberService.signUp(member);
//
//		Optional<Member> result = memberService.searchMemberId("ko");
//
//		assertThat(result.get().getMemberName()).isEqualTo(member.getMemberName());
//	}
//}
