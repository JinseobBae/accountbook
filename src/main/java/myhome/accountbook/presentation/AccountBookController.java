package myhome.accountbook.presentation;

import lombok.RequiredArgsConstructor;
import myhome.accountbook.common.Result;
import myhome.accountbook.dto.AccountBookDto;
import myhome.accountbook.dto.CategoryDto;
import myhome.accountbook.service.AccountBookSearchService;
import myhome.accountbook.service.CategorySearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class AccountBookController {

    private final AccountBookSearchService accountBookSearchService;

    @GetMapping("account-book")
    public Result<AccountBookDto> searchOneBook(@RequestParam String bookName){
        AccountBookDto result = accountBookSearchService.searchBook(bookName);

        return new Result<>(result);
    }

}
