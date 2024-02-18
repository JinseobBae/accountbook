package myhome.accountbook.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculateDto {

    private String period;
    private Long inTotal;
    private Long outTotal;
    private List<CalculateDetailDto> inList;
    private List<CalculateDetailDto> outList;
}
