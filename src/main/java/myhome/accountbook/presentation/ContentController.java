package myhome.accountbook.presentation;

import lombok.RequiredArgsConstructor;
import myhome.accountbook.common.Result;
import myhome.accountbook.dto.ContentDto;
import myhome.accountbook.dto.ContentSearchDto;
import myhome.accountbook.dto.ContentStatisticDto;
import myhome.accountbook.service.ContentSearchService;
import myhome.accountbook.service.ContentUpsertService;
import myhome.accountbook.service.statistic.ContentStatisticService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class ContentController {

    private final ContentSearchService contentSearchService;
    private final ContentUpsertService contentUpsertService;
    private final ContentStatisticService contentStatisticService;


    @GetMapping("/content")
    public Result<List<ContentDto>> searchContent(ContentSearchDto contentSearchDto){
        List<ContentDto> resultList = contentSearchService.searchAllContent(contentSearchDto);
        return new Result<>(resultList, resultList.size());
    }

    @PostMapping("/content")
    public void createContent(@RequestBody ContentDto contentDto){
        contentUpsertService.upsertContent(contentDto);
    }

    @PutMapping("/content")
    public void updateContent(ContentDto contentDto){
        contentUpsertService.upsertContent(contentDto);
    }

    @GetMapping("/content/statistic")
    public Result<?> statistic(ContentSearchDto contentSearchDto){
        ContentStatisticDto result = contentStatisticService.contentStatistic(contentSearchDto);
        return new Result<>(result);
    }
}
