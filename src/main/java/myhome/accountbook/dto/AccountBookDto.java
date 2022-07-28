package myhome.accountbook.dto;

import lombok.*;
import myhome.accountbook.entity.AccountBook;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountBookDto {

    long id;

    String name;

    String bank;

    long totalAmount;

    public AccountBook toEntity(){
        return AccountBook.builder()
                .name(this.name)
                .build();
    }

    public AccountBook toEntityWithId(){
        return AccountBook.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
