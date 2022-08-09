package myhome.accountbook.presentation;

import lombok.RequiredArgsConstructor;
import myhome.accountbook.dto.AccountBookDto;
import myhome.accountbook.service.AccountBookSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class AccountBookController {

    private final AccountBookSearchService accountBookSearchService;

    @GetMapping("account-book")
    public AccountBookDto searchOneBook(@RequestParam String bookName){
        return accountBookSearchService.searchBook(bookName);
    }

}
