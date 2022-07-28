package myhome.accountbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myhome.accountbook.common.AmountType;
import myhome.accountbook.dto.ContentDto;
import myhome.accountbook.entity.AccountBook;
import myhome.accountbook.entity.AccountBookRepository;
import myhome.accountbook.entity.ContentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public
class ContentUpsertService {

    private final ContentRepository contentRepository;
    private final AccountBookRepository accountBookRepository;

    @Transactional
    public void upsertContent(ContentDto contentDto){
        AccountBook accountBook = accountBookRepository.findByName(contentDto.getAccountBookName())
                .orElseThrow(() -> new RuntimeException("Accountbook not exist"));

        log.info("====================== 등록 내용 ======================");

        log.info("종류 : {}", contentDto.getType().equals(AmountType.PLUS) ? "입금" : "출금");
        log.info("항목 : {}", contentDto.getTitle());
        log.info("금액 : {}", contentDto.getAmount());

        log.info("======================================================");


        //항목 저장
        contentRepository.save(contentDto.toEntityWithAccountBook(accountBook));

        //해당 가계부 총액 변경
        if(contentDto.getType().equals(AmountType.PLUS)){
            accountBook.plusAmount(contentDto.getAmount());
        }else{
            accountBook.minusAmount(contentDto.getAmount());
        }

        log.info("잔액 : {}", accountBook.getTotalAmount());

    }
}
