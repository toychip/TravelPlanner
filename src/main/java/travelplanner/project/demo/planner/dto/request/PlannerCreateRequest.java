package travelplanner.project.demo.planner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "플래너 생성 요청 DTO")
@Data
@NoArgsConstructor
public class PlannerCreateRequest {

    @Schema(description = "플래너 제목", example = "제주도 여행")
    @NotEmpty
    private String planTitle;

    @Schema(description = "공개여부", example = "false")
    private Boolean isPrivate;

}
