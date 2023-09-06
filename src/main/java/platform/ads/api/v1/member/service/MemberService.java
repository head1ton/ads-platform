package platform.ads.api.v1.member.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platform.ads.api.v1.member.domain.Member;
import platform.ads.api.v1.member.dto.JoinDto;
import platform.ads.api.v1.member.repository.MemberRepository;
import platform.ads.api.v1.member.vo.JoinVo;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public JoinVo join(final JoinDto joinDto) {
        joinDto.passwordEncoder(passwordEncoder);   // password 암호화

        Optional<Member> findMember = memberRepository.findByEmail(joinDto.getEmail());
        if (findMember.isPresent()) {
            throw new IllegalArgumentException("이미 등록된 이메일 입니다.");
        }

        Member saveMember = memberRepository.save(Member.of(joinDto));

        return new JoinVo(saveMember.getName(), saveMember.getNickname(), saveMember.getEmail());
    }
}
