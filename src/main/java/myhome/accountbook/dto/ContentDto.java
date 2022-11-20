package myhome.accountbook.dto;

import lombok.*;
import myhome.accountbook.entity.AccountBook;
import myhome.accountbook.entity.Content;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentDto {

    private long id;

    private String category1;

    private String category2;

    private long amount;

    private String title;

    private String note;

    private String type; // 0 : 출금 , 1 : 입금

    private LocalDate realUseDt;

    private LocalDateTime createDt;

    private LocalDateTime modifyDt;

    private String accountBookName;

    public Content toEntity(){
        return Content.builder()
                .amount(this.amount)
                .category1(this.category1)
                .category2(this.category2)
                .note(this.note)
                .title(this.title)
                .realUseDt(this.realUseDt)
                .type(this.type)
                .build();
    }

    public Content toEntityWithId(){
        return Content.builder()
                .id(this.id)
                .amount(this.amount)
                .category1(this.category1)
                .category2(this.category2)
                .note(this.note)
                .title(this.title)
                .realUseDt(this.realUseDt)
                .build();
    }

    public Content toEntityWithAccountBook(AccountBook accountBook){
        return Content.builder()
                .amount(this.amount)
                .category1(this.category1)
                .category2(this.category2)
                .note(this.note)
                .title(this.title)
                .realUseDt(this.realUseDt)
                .type(this.type)
                .accountBook(accountBook)
                .build();
    }

    public void changeNameToCode(String type, String category1, String category2){
        this.type = type;
        this.category1 = category1;
        this.category2 = category2;
    }

}
