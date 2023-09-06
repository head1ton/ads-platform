package platform.ads.api.v1.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import platform.ads.api.v1.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
