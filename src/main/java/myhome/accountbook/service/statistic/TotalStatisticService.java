package myhome.accountbook.service.statistic;

import lombok.RequiredArgsConstructor;
import myhome.accountbook.dto.CategoryDto;
import myhome.accountbook.dto.ContentSearchDto;
import myhome.accountbook.dto.DateDto;
import myhome.accountbook.dto.TotalStatisticDto;
import myhome.accountbook.entity.AccountBook;
import myhome.accountbook.entity.AccountBookRepository;
import myhome.accountbook.entity.ContentRepository;
import myhome.accountbook.util.CategoryUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TotalStatisticService {

    private final ContentRepository contentRepository;
    private final AccountBookRepository accountBookRepository;

    private final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<TotalStatisticDto> compareTotalAmountWithLastMonth(ContentSearchDto contentSearchDto){
        AccountBook accountBook = accountBookRepository.findByName(contentSearchDto.getAccountBookName())
                .orElseThrow(() -> new RuntimeException("Accountbook not exist"));

        DateDto thisMonth = getDateInfo(LocalDate.now());
        DateDto lastMonth = getDateInfo(LocalDate.now().minusMonths(6));

        contentSearchDto.setStartDate(lastMonth.getFirstDayAsString());
        contentSearchDto.setEndDate(thisMonth.getLastDayAsString());

        List<TotalStatisticDto> resultList = contentRepository.findTotalByMonth(contentSearchDto, accountBook);
        resultList.sort(Comparator.comparing(TotalStatisticDto::getLabel));

        return resultList;
    }

    public List<TotalStatisticDto> compareTotalAmountWithLastMonthDetail(ContentSearchDto contentSearchDto){
        AccountBook accountBook = accountBookRepository.findByName(contentSearchDto.getAccountBookName())
                .orElseThrow(() -> new RuntimeException("Accountbook not exist"));

        DateDto thisMonth = getDateInfo(LocalDate.now());
        DateDto pastMonth = getDateInfo(LocalDate.now().minusMonths(6));

        contentSearchDto.setStartDate(pastMonth.getFirstDayAsString());
        contentSearchDto.setEndDate(thisMonth.getLastDayAsString());

        List<TotalStatisticDto> resultList = contentRepository.findTotalByMonthDetail(contentSearchDto, accountBook);

        resultList.forEach(result -> {
            CategoryDto category =
                    CategoryUtils.getMyCategory(CategoryUtils.generateKey("1", result.getCategory1(), result.getCategory2()));

            result.setCategory1(category.getName());
            result.setCategory2(category.getSubName());
        });

        resultList.sort(Comparator.comparing(TotalStatisticDto::getLabel));

        return resultList;
    }

    private DateDto getDateInfo(LocalDate date){
        LocalDate firstDay = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
        LocalDate lastDay  = date.withDayOfMonth(
                date.getMonth().length(date.isLeapYear())
        );

        return DateDto.builder()
                .firstDay(firstDay)
                .firstDayAsString(firstDay.format(pattern))
                .lastDay(lastDay)
                .lastDayAsString(lastDay.format(pattern))
                .build();
    }
}
