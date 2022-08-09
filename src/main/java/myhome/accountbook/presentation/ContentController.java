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
import java.util.Map;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class ContentController {

    private final ContentSearchService contentSearchService;
    private final ContentUpsertService contentUpsertService;
    private final ContentStatisticService contentStatisticService;


    @GetMapping("/content")
    public List<ContentDto> searchContent(ContentSearchDto contentSearchDto){
        return contentSearchService.searchAllContent(contentSearchDto);
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
    public Map<String, List<ContentStatisticDto>> statistic(ContentSearchDto contentSearchDto){
        return contentStatisticService.contentStatistic(contentSearchDto);
    }
}
