package platform.ads.api.v1.member.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    MEMBER("MEMBER", "MEMBER"),
    ADVERTISER("ADVERTISER", "MEMBER, ADVERTISER"),
    INFLUENCER("INFLUENCER", "MEMBER, INFLUENCER"),
    ADMIN("ADMIN", "MEMBER, INFLUENCER, ADVERTISER, ADMIN"),
    ;

    public final String roleName;
    public final String roleList;
}
