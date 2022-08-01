package myhome.accountbook.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentStatisticDto {

    private String type;
    private String category1;
    private String category2;
    private long totalAmount;



}
