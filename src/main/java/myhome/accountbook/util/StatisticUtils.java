package myhome.accountbook.util;

import myhome.accountbook.dto.ContentStatisticDto;
import myhome.accountbook.entity.Content;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToLongFunction;

public class StatisticUtils {

    public static final ToLongFunction<Content> toLong = (content) -> {
        Long result;
        if (content.getType().equals("1")) {
            result = -(content.getAmount());
        } else {
            result = content.getAmount();
        }

        return result;
    };

    public static final Function<List<ContentStatisticDto>, List<ContentStatisticDto>> sortResult = (resultList) -> {
        resultList.sort(Comparator.comparingLong(ContentStatisticDto::getTotalAmount));

        return resultList;
    };
}
