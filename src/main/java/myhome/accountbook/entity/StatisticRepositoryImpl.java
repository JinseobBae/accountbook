package myhome.accountbook.entity;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import myhome.accountbook.dto.ContentSearchDto;
import myhome.accountbook.dto.TotalStatisticDto;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class StatisticRepositoryImpl implements StatisticRepository{

    public StatisticRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TotalStatisticDto> findTotalByMonth(ContentSearchDto contentSearchDto, AccountBook accountBook) {
        QContent content = QContent.content;

        StringTemplate dateFormat = Expressions.stringTemplate("TO_CHAR({0}, '{1s}')", content.realUseDt, "YYYY-MM");

        return jpaQueryFactory.select(Projections.fields(TotalStatisticDto.class,
                                    content.count().as("cnt"),
                                    content.amount.sum().as("totalAmount"),
                                    dateFormat.as("label"))
                )
                .from(content)
                .where(content.accountBook.eq(accountBook))
                .where(content.type.eq("1"))
                .where(content.realUseDt.between(LocalDate.parse(contentSearchDto.getStartDate()), LocalDate.parse(contentSearchDto.getEndDate())))
                .groupBy(dateFormat)
                .orderBy(dateFormat.asc())
                .fetch();

    }

    @Override
    public List<TotalStatisticDto> findTotalByMonthDetail(ContentSearchDto contentSearchDto, AccountBook accountBook) {
        QContent content = QContent.content;

        StringTemplate dateFormat = Expressions.stringTemplate("TO_CHAR({0}, '{1s}')", content.realUseDt, "YYYY-MM");

        return jpaQueryFactory.select(Projections.fields(TotalStatisticDto.class,
                content.category1,
                content.category2,
                content.amount.sum().as("totalAmount"),
                dateFormat.as("label"))
        )
                .from(content)
                .where(content.accountBook.eq(accountBook))
                .where(content.type.eq("1"))
                .where(content.realUseDt.between(LocalDate.parse(contentSearchDto.getStartDate()), LocalDate.parse(contentSearchDto.getEndDate())))
                .groupBy(dateFormat)
                .groupBy(content.category1)
                .groupBy(content.category2)
                .orderBy(dateFormat.asc())
                .fetch();

    }
}
