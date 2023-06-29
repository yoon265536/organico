package organico.organico.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import organico.organico.domain.Member;
import organico.organico.repository.MemberRepository;

import java.util.Optional;

@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // [아이디로 조회]
    public Optional<Member> searchMemberId(String memberId) {
        return memberRepository.searchByMemberId(memberId);
    }

    // [가입]
    // 사용자가 입력한 아이디가 이미 존재하는지 검색
    // 존재하지 않으면 member 테이블에 정보 저장
    // 존재하면 empty 객체 리턴
    public Optional<Member> signUp(Member member) {
        log.info("[service] memberId : {}", member.getMemberId());
        log.info("[service] memberName : {}", member.getMemberName());
        log.info("[service] password : {}", member.getPassword());

        Optional<Member> result = searchMemberId(member.getMemberId());

        if (result.isPresent()) {
            return Optional.empty();
        }

        return memberRepository.signUp(member);
    }

    // [로그인]
    public Optional<Member> signIn(Member member) {
        log.info("[service] memberId : {}", member.getMemberId());
        Optional<Member> result = searchMemberId(member.getMemberId());
        if (result.isEmpty()) {
            log.info("[service] empty");
            return Optional.empty();
        }

        if (result.get().getPassword().equals(member.getPassword())) {
            member.setMemberName(result.get().getMemberName());
            log.info("[service] name : {}", result.get().getMemberName());
            return Optional.of(member);
        }
        log.info("[service] empty");
        return Optional.empty();
    }
}