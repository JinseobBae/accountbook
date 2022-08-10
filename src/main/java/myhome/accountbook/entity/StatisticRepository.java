package myhome.accountbook.entity;

import myhome.accountbook.dto.ContentSearchDto;
import myhome.accountbook.dto.TotalStatisticDto;

import java.util.List;


public interface StatisticRepository {

    List<TotalStatisticDto> findTotalByMonth(ContentSearchDto contentSearchDto, AccountBook accountBook);
}
