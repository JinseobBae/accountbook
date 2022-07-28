package myhome.accountbook.dto;

import lombok.*;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    String type;

    String code;

    String subCode;

    String name;

    String subName;
}
