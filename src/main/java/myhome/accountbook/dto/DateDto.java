package myhome.accountbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DateDto {

    private String lastDayAsString;
    private String firstDayAsString;
    private LocalDate lastDay;
    private LocalDate firstDay;
}
