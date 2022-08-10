package myhome.accountbook.service;

import myhome.accountbook.dto.ContentSearchDto;
import myhome.accountbook.dto.TotalStatisticDto;
import myhome.accountbook.service.statistic.ContentStatisticService;
import myhome.accountbook.service.statistic.TotalStatisticService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class StatisticTest {

    @Autowired
    ContentStatisticService contentStatisticService;

    @Autowired
    TotalStatisticService totalStatisticService;

    @Test
    void statisticTest(){
        ContentSearchDto contentSearchDto = new ContentSearchDto();

        contentSearchDto.setAccountBookName("집");
        contentSearchDto.setStartDate("2022-07-01");

        contentStatisticService.contentStatistic(contentSearchDto);

    }

    @Test
    void totalStatisticTest(){
        ContentSearchDto contentSearchDto = ContentSearchDto.builder()
                .accountBookName("집")
                .build();

        List<TotalStatisticDto> result = totalStatisticService.compareTotalAmountWithLastMonth(contentSearchDto);

        assert result.size() > 0;
    }
}
