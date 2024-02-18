package myhome.accountbook.entity;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import myhome.accountbook.dto.CalculateDetailDto;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CalculateRepositoryService implements CalculateRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CalculateDetailDto> findContentsList(String type, int period) {
        QContent content = QContent.content;

        StringTemplate dateFormat = Expressions.stringTemplate("TO_CHAR({0}, '{1s}')", content.realUseDt, "YYYYMM");

        return jpaQueryFactory.select(Projections.fields(CalculateDetailDto.class,
                    content.category1.as("category"),
                    content.amount.sum().as("totalAmount"),
                    dateFormat.as("date")
                ))
                .from(content)
                .where(content.type.eq(type))
                .where(content.realUseDt.goe(LocalDate.now().minusMonths(period).withDayOfMonth(1)))
                .groupBy(content.category1)
                .groupBy(dateFormat)
                .orderBy(dateFormat.desc())
                .fetch();
    }
}
