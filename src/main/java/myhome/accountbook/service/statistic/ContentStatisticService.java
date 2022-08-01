package myhome.accountbook.service.statistic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import myhome.accountbook.common.AmountType;
import myhome.accountbook.dto.ContentSearchDto;
import myhome.accountbook.dto.ContentStatisticDto;
import myhome.accountbook.entity.AccountBook;
import myhome.accountbook.entity.AccountBookRepository;
import myhome.accountbook.entity.Content;
import myhome.accountbook.entity.ContentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

import static myhome.accountbook.common.SpecificationFactory.contentSpec;

@Service
@RequiredArgsConstructor
public class ContentStatisticService {

    private final ContentRepository contentRepository;
    private final AccountBookRepository accountBookRepository;

    private final String TOTAL_AMOUNT = "totalAmount";
    private final String TYPE_STATISTIC = "typeStatistic";
    private final String CATEGORY_1_STATISTIC = "category1Statistic";
    private final String CATEGORY_2_STATISTIC = "category2Statistic";

    @Transactional(readOnly = true)
    public Map<String, List<ContentStatisticDto>> contentStatistic(ContentSearchDto contentSearchDto) {
        AccountBook accountBook = accountBookRepository.findByName(contentSearchDto.getAccountBookName())
                .orElseThrow(() -> new RuntimeException("Accountbook not exist"));

        List<Content> contents = contentRepository.findAll(contentSpec(contentSearchDto, accountBook));

        Map<String, List<ContentStatisticDto>> resultMap = new HashMap<>();

        ContentStatisticProcessor processor = new ContentStatisticProcessor();

        resultMap.put(TOTAL_AMOUNT, processor.getTotalAmount(contents)); // 총 액수
        resultMap.put(TYPE_STATISTIC, processor.getTypeStatistic(contents)); // 타입 별 액수
        resultMap.put(CATEGORY_1_STATISTIC, processor.getCategory1Statistic(contents)); // 타입, 카테고리1 별 액수
        resultMap.put(CATEGORY_2_STATISTIC, processor.getCategory2Statistic(contents)); // 타입, 카테고리1, 카테고리2 별 액수

        return resultMap;

    }


}
