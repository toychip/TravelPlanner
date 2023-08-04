package travelplanner.project.demo.planner.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "그룹멤버 검색 응답 DTO")
@Data
@NoArgsConstructor
public class GroupMemberSearchResponse {

    @Schema(description = "이메일", example = "user1@gmail.com")
    private String email;

    @Schema(description = "프로필 이미지 주소")
    private String profileImageUrl;

    @Schema(description = "유저 닉네임", example = "시니")
    private String userNickname;
}
