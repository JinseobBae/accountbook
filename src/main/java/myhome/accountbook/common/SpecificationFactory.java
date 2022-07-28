package myhome.accountbook.common;

import myhome.accountbook.dto.ContentSearchDto;
import myhome.accountbook.entity.AccountBook;
import myhome.accountbook.entity.Content;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SpecificationFactory {

    public static Specification<Content> contentSpec(ContentSearchDto searchDto, AccountBook accountBook){
        return contentSpec(searchDto, accountBook, false);
    }

    public static Specification<Content> contentSpec(ContentSearchDto searchDto, AccountBook accountBook, boolean isStatistic) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.equal(root.get("accountBook"), accountBook));

            if (!ObjectUtils.isEmpty(searchDto.getCategory1())) {
                predicates.add(builder.equal(root.get("category1"), searchDto.getCategory1()));
            }

            if (!ObjectUtils.isEmpty(searchDto.getCategory2())) {
                predicates.add(builder.equal(root.get("category2"), searchDto.getCategory2()));
            }

            if (!ObjectUtils.isEmpty(searchDto.getContentTitle())) {
                predicates.add(builder.like(root.get("title"), "%" + searchDto.getContentTitle() + "%"));
            }

            if (!ObjectUtils.isEmpty(searchDto.getStartDate())) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("realUseDt"), LocalDate.parse(searchDto.getStartDate())));
            }

            if (!ObjectUtils.isEmpty(searchDto.getEndDate())) {
                predicates.add(builder.lessThanOrEqualTo(root.get("realUseDt"), LocalDate.parse(searchDto.getEndDate())));
            }

            if (!ObjectUtils.isEmpty(searchDto.getType())) {
                predicates.add(builder.equal(root.get("type"), searchDto.getType()));
            }

//            query.orderBy(builder.desc(root.get("realUseDt")));


            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
