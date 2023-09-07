package platform.ads.config.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import platform.ads.api.v1.member.domain.Member;
import platform.ads.api.v1.member.enums.Role;
import platform.ads.api.v1.member.repository.MemberRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        log.debug("login auth");

        Member member = memberRepository.findByEmail(username).orElseThrow(() ->
            new UsernameNotFoundException("유효하지 않은 회원입니다.")
        );

        Role role = member.getRole();
        Set<String> roleSet = new HashSet<>();
        String roleListToString = Role.valueOf(role.roleName).roleList;
        String[] roleList = roleListToString.split(",");

        for (String s : roleList) {
            roleSet.add(s.trim());
        }

        String[] roles = Arrays.copyOf(roleSet.toArray(), roleSet.size(), String[].class);

        return User.builder()
                   .username(String.valueOf(member.getId()))
                   .password(member.getPassword())
                   .roles(roles)
                   .build();
    }
}
