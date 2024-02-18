package myhome.accountbook.presentation;

import lombok.RequiredArgsConstructor;
import myhome.accountbook.dto.CalculateDto;
import myhome.accountbook.dto.ContentSearchDto;
import myhome.accountbook.dto.TotalStatisticDto;
import myhome.accountbook.service.calculate.CalculateService;
import myhome.accountbook.service.statistic.TotalStatisticService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calculate")
@RequiredArgsConstructor
public class CalculateController {

    private final CalculateService calculateService;

    @GetMapping("/calculate-last-six-month")
    public List<CalculateDto> compareLastMonthTaxDetail(){
        return calculateService.calculateAll();
    }
}
