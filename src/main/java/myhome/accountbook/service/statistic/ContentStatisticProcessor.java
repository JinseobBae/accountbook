package myhome.accountbook.service.statistic;

import lombok.Getter;
import myhome.accountbook.common.AmountType;
import myhome.accountbook.dto.CategoryDto;
import myhome.accountbook.dto.ContentStatisticDto;
import myhome.accountbook.entity.Content;
import myhome.accountbook.util.CategoryUtils;

import java.util.*;
import java.util.stream.Collectors;

import static myhome.accountbook.util.StatisticUtils.sortResult;
import static myhome.accountbook.util.StatisticUtils.toLong;

public class ContentStatisticProcessor {

    public List<ContentStatisticDto> getTotalAmount(List<Content> contents){
        ContentStatisticDto statisticDto = new ContentStatisticDto();

        statisticDto.setTotalAmount(contents.stream().mapToLong(toLong).sum());

        return Collections.singletonList(statisticDto);
    }

    public List<ContentStatisticDto> getTypeStatistic(List<Content> contents){
        List<ContentStatisticDto> resultList = new ArrayList<>();

        Map<String, Long> groupMap = contents.stream()
                .map(content ->
                        ContentStatisticDto.builder()
                                .type(content.getType())
                                .totalAmount(content.getAmount())
                                .build()
                )
                .collect(Collectors.groupingBy(ContentStatisticDto::getType, Collectors.summingLong(ContentStatisticDto::getTotalAmount)));

        groupMap.forEach((type, amount) -> {
            ContentStatisticDto statisticDto = new ContentStatisticDto();
            statisticDto.setType(type.equals(AmountType.PLUS) ? "입금" : "출금");
            statisticDto.setTotalAmount(amount);
            resultList.add(statisticDto);
        });

        return sortResult.apply(resultList);
    }

    @Getter
    private class ContentStatisticByCategory1 {
        private final String type;
        private final String category1;

        ContentStatisticByCategory1(ContentStatisticDto dto){
            this.type = dto.getType();
            this.category1 = dto.getCategory1();
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, category1);
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof ContentStatisticByCategory1)){
                return false;
            }

            ContentStatisticByCategory1 target = (ContentStatisticByCategory1) obj;

            return Objects.equals(this.type,target.type)
                    && Objects.equals(this.category1,target.category1);
        }
    }

    public List<ContentStatisticDto> getCategory1Statistic(List<Content> contents){
        List<ContentStatisticDto> resultList = new ArrayList<>();

        Map<ContentStatisticByCategory1, Long> groupMap = contents.stream()
                .map(content ->
                        ContentStatisticDto.builder()
                                .type(content.getType())
                                .category1(content.getCategory1())
                                .totalAmount(content.getAmount())
                                .build()
                )
                .collect(Collectors.groupingBy(ContentStatisticByCategory1::new, Collectors.summingLong(ContentStatisticDto::getTotalAmount)));


        groupMap.forEach((key, amount) -> {
            ContentStatisticDto content = new ContentStatisticDto();
            content.setType(key.getType());

            CategoryDto category = CategoryUtils.getMyCategory(CategoryUtils.generateKey(key.getType(), key.getCategory1()));

            content.setCategory1(category.getName());
            content.setTotalAmount(amount);
            resultList.add(content);
        });

        return sortResult.apply(resultList);
    }

    @Getter
    private class ContentStatisticByCategory2 {
        private final String type;
        private final String category1;
        private final String category2;

        ContentStatisticByCategory2(ContentStatisticDto dto){
            this.type = dto.getType();
            this.category1 = dto.getCategory1();
            this.category2 = dto.getCategory2();
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, category1, category2);
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof ContentStatisticByCategory2)){
                return false;
            }

            ContentStatisticByCategory2 target = (ContentStatisticByCategory2) obj;

            return Objects.equals(this.type,target.type)
                    && Objects.equals(this.category1,target.category1)
                    && Objects.equals(this.category2,target.category2);
        }
    }

    public List<ContentStatisticDto> getCategory2Statistic(List<Content> contents){
        List<ContentStatisticDto> resultList = new ArrayList<>();

        Map<ContentStatisticByCategory2, Long> groupMap = contents.stream()
                .map(content ->
                        ContentStatisticDto.builder()
                                .type(content.getType())
                                .category1(content.getCategory1())
                                .category2(content.getCategory2())
                                .totalAmount(content.getAmount())
                                .build()
                )
                .collect(Collectors.groupingBy(ContentStatisticByCategory2::new, Collectors.summingLong(ContentStatisticDto::getTotalAmount)));


        groupMap.forEach((key, amount) -> {
            ContentStatisticDto content = new ContentStatisticDto();
            content.setType(key.getType());

            CategoryUtils.getMyCategory(key.getCategory1());

            CategoryDto category = CategoryUtils.getMyCategory(CategoryUtils.generateKey(key.getType(), key.getCategory1(), key.getCategory2()));

            content.setCategory1(category.getName());
            content.setCategory2(category.getSubName());
            content.setTotalAmount(amount);

            resultList.add(content);
        });

        return sortResult.apply(resultList);
    }


}
