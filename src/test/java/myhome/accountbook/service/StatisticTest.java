package myhome.accountbook.service;

import myhome.accountbook.dto.ContentSearchDto;
import myhome.accountbook.service.statistic.ContentStatisticService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StatisticTest {

    @Autowired
    ContentStatisticService contentStatisticService;

    @Test
    void statisticTest(){
        ContentSearchDto contentSearchDto = new ContentSearchDto();

        contentSearchDto.setAccountBookName("집");
        contentSearchDto.setStartDate("2022-07-01");

        contentStatisticService.contentStatistic(contentSearchDto);

    }
}
