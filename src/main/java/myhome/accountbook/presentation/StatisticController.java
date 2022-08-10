package myhome.accountbook.presentation;

import lombok.RequiredArgsConstructor;
import myhome.accountbook.dto.ContentSearchDto;
import myhome.accountbook.dto.TotalStatisticDto;
import myhome.accountbook.service.statistic.TotalStatisticService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final TotalStatisticService totalStatisticService;

    @GetMapping("/with-last-month")
    public List<TotalStatisticDto> compareLastMonth(ContentSearchDto contentSearchDto){
        return totalStatisticService.compareTotalAmountWithLastMonth(contentSearchDto);
    }

}
