//package organico.organico.repository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import lombok.extern.slf4j.Slf4j;
//import organico.organico.domain.Member;
//
//@SpringBootTest
//@Slf4j
//class MemberRepositoryTest {
//
//	@Autowired private MemberRepository memberRepository;
//
//	@Test
//	void 가입() {
//		Member member = new Member("홍길동", "hong", "123");
//		memberRepository.signUp(member);
//
//		log.info("이름 : {}", member.getMemberName());
//		log.info("아이디 : {}", member.getMemberId());
//		log.info("비밀번호 : {}", member.getPassword());
//
//		Optional<Member> result = memberRepository.searchByMemberId(member.getMemberId());
//
//		assertThat(result).isNotEmpty();
//	}
//
//	@Test
//	void 조회() {
//		Member member = new Member("박보검", "park", "456");
//		memberRepository.signUp(member);
//
//		Member result = memberRepository.searchByMemberId(member.getMemberId()).get();
//
//		assertThat(member.getMemberName()).isEqualTo(result.getMemberName());
//	}
//
//}
