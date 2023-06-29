package organico.organico.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private String memberName;
    private String memberId;
    private String password;

    public Member() {
    }

    public Member(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }

    public Member(String memberName, String memberId, String password) {
        this.memberName = memberName;
        this.memberId = memberId;
        this.password = password;
    }
}
