package myhome.accountbook.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TotalStatisticDto extends ContentStatisticDto{

    private String label;

    public void addTotalAmount(long amount){
        setTotalAmount(getTotalAmount() + amount);
    }


}
