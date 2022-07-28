package myhome.accountbook.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentSearchDto {

    private String accountBookName;

    private String startDate;

    private String endDate;

    private String category1;

    private String category2;

    private String contentTitle;

    private String type;
}
