package myhome.accountbook.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ContentStatisticDto {

    private String type;
    private String category1;
    private String category2;
    private long totalAmount;



}
