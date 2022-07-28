package myhome.accountbook.service.statistic;

import lombok.RequiredArgsConstructor;
import myhome.accountbook.dto.ContentSearchDto;
import myhome.accountbook.dto.ContentStatisticDto;
import myhome.accountbook.entity.AccountBook;
import myhome.accountbook.entity.AccountBookRepository;
import myhome.accountbook.entity.Content;
import myhome.accountbook.entity.ContentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static myhome.accountbook.common.SpecificationFactory.contentSpec;

@Service
@RequiredArgsConstructor
public class ContentStatisticService {

    private final ContentRepository contentRepository;
    private final AccountBookRepository accountBookRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public ContentStatisticDto contentStatistic(ContentSearchDto contentSearchDto) {
        AccountBook accountBook = accountBookRepository.findByName(contentSearchDto.getAccountBookName())
                .orElseThrow(() -> new RuntimeException("Accountbook not exist"));

        List<Content> contents = contentRepository.findAll(contentSpec(contentSearchDto, accountBook));

        ContentStatisticDto statisticDto = new ContentStatisticDto();

        statisticDto.setTotalAmount(contents.stream().mapToLong(toLong).sum());

        return statisticDto;

    }

    private final ToLongFunction<Content> toLong = (content) -> {
        Long result;
        if (content.getType().equals("1")) {
            result = -(content.getAmount());
        } else {
            result = content.getAmount();
        }

        return result;
    };


}
