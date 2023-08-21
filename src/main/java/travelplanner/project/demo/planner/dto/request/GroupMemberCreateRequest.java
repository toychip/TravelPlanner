package travelplanner.project.demo.planner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "그룹멤버 추가 요청 DTO")
@Data
@NoArgsConstructor
public class GroupMemberCreateRequest {

    @Schema(description = "이메일", example = "user1@gmail.com")
    private String email;
}