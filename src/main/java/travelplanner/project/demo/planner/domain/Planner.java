package travelplanner.project.demo.planner.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import travelplanner.project.demo.member.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Planner {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "planner",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<GroupMember> groupMembers = new ArrayList<>();

    @Builder.Default
    private String planTitle = "제목을 입력해주세요";

    @ColumnDefault("false")
    private Boolean isPrivate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @OneToMany(mappedBy = "planner", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Calendar> calendars = new ArrayList<>();

    @OneToMany (mappedBy = "planner", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Chatting> chattings = new ArrayList<>();


    public void mappingCalendar(Calendar calendar) {
        calendars.add(calendar);
    }

    public void mappingMember(Member member) {
        this.member = member;
        member.mappingPlanner(this);
    }


    public PlannerEditor.PlannerEditorBuilder toEditor() {
        return PlannerEditor.builder()
                .planTitle(planTitle)
                .isPrivate(isPrivate);
    }


    public void edit(PlannerEditor plannerEditor){ // Member member
        planTitle = plannerEditor.getPlanTitle();
        isPrivate = plannerEditor.getIsPrivate();
    }

    //날짜 추가
    public void createDate(Calendar calendar){
        this.calendars.add(calendar);
    }

    //날짜 삭제
    public void deleteDate(Calendar calendar){
        this.calendars.remove(calendar);
    }

    public void mappingGroupMember(GroupMember groupMember) {
        groupMembers.add(groupMember);
    }

    // 연관 관계 편의 메서드
    public void mappingChatting(Chatting chatting) {
        chattings.add(chatting);
    }

}