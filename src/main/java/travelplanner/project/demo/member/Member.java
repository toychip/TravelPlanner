package travelplanner.project.demo.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import travelplanner.project.demo.member.auth.Role;
import travelplanner.project.demo.member.profile.Profile;
import travelplanner.project.demo.planner.domain.Planner;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userNickname;

    @Email
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Planner> planners = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.REMOVE)
    /*@JoinColumn(name = "profile_id")*/
    private Profile profile;


    public void mappingPlanner(Planner planner) {
        planners.add(planner);
    }

    private String provider;
    private String providerId;

    public void edit(MemberEditor memberEditor) {
        if (memberEditor.getUserNickname() != null) {
            userNickname = memberEditor.getUserNickname();
        }
        if (memberEditor.getPassword() != null) {
            password = memberEditor.getPassword(); // 비밀번호 수정 로직 추가
        }
    }
}