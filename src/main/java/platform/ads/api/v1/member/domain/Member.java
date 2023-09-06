package platform.ads.api.v1.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditOverride;
import platform.ads.api.v1.member.dto.JoinDto;
import platform.ads.api.v1.member.enums.Role;
import platform.ads.api.v1.member.enums.SnsJoinType;
import platform.ads.api.v1.member.enums.Status;
import platform.ads.common.CommonDateEntity;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AuditOverride(forClass = CommonDateEntity.class)
public class Member extends CommonDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String nickname;
    @NotNull
    private String name;
    private String birth;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String snsId;
    @Enumerated(EnumType.STRING)
    private SnsJoinType snsType;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Member(
        @NotNull final String email,
        @NotNull final String password,
        @NotNull final String nickname,
        @NotNull final String name,
        final String birth,
        final Role role,
        final String snsId,
        final SnsJoinType snsType,
        final Status status) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.birth = birth;
        this.role = role;
        this.snsId = snsId;
        this.snsType = snsType;
        this.status = status;
    }

    public Member(
        final Long id,
        @NotNull final String email,
        @NotNull final String password,
        @NotNull final String nickname,
        @NotNull final String name,
        final String birth,
        final Role role,
        final String snsId,
        final SnsJoinType snsType,
        final Status status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.birth = birth;
        this.role = role;
        this.snsId = snsId;
        this.snsType = snsType;
        this.status = status;
    }

    public static Member of(final JoinDto joinDto) {
        return new Member(
            joinDto.getEmail(),
            joinDto.getPassword(),
            joinDto.getNickname(),
            joinDto.getName(),
            joinDto.getBirth(),
            Role.MEMBER,
            null,
            null,
            Status.NORMAL
        );
    }

    public static Member of(final JoinDto joinDto, final String snsId, final SnsJoinType snsType) {
        return new Member(
            joinDto.getEmail(),
            joinDto.getPassword(),
            joinDto.getNickname(),
            joinDto.getName(),
            joinDto.getBirth(),
            Role.MEMBER,
            snsId,
            snsType,
            Status.NORMAL
        );
    }
}
