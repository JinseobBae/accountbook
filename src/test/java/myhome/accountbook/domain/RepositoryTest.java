package myhome.accountbook.domain;

import myhome.accountbook.entity.AccountBook;
import myhome.accountbook.entity.AccountBookRepository;
import myhome.accountbook.entity.Content;
import myhome.accountbook.entity.ContentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    AccountBookRepository accountBookRepository;

    @Autowired
    ContentRepository contentRepository;


    @Test
    void accountBookSaveTest() {
        AccountBook accountBook = accountBookRepository.save(AccountBook.builder().name("테스트").build());

        Assertions.assertFalse(ObjectUtils.isEmpty(accountBook.getId()));
    }

    @Test
    void contentSaveTest(){
        AccountBook accountBook = accountBookRepository.findAll().get(0);

        Content content = contentRepository.save(Content.builder()
                .title("타이틀")
                .category1("카테고리1")
                .category2("카테고리2")
                .realUseDt(LocalDate.now())
                .note("비고")
                .amount(10000)
                .accountBook(accountBook)
                .build());

        

        Assertions.assertFalse(ObjectUtils.isEmpty(content.getId()));

        System.out.println(content.toString());
    }
}