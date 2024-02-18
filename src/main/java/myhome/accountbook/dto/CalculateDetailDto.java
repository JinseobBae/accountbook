package myhome.accountbook.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculateDetailDto {

    private String category;
    private String categoryName;
    private Long totalAmount;

    @JsonIgnore
    private String date;
}
