package myhome.accountbook.presentation;

import lombok.RequiredArgsConstructor;
import myhome.accountbook.dto.AccountBookDto;
import myhome.accountbook.filter.AuthFree;
import myhome.accountbook.service.AccountBookSearchService;
import myhome.accountbook.util.AuthUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class AccountBookController {

    private final AccountBookSearchService accountBookSearchService;

    @GetMapping("account-book")
    public AccountBookDto searchOneBook(@RequestParam String bookName) {
        return accountBookSearchService.searchBook(bookName);
    }

    @PostMapping("/auth")
    @AuthFree
    public Boolean auth(@RequestBody String auth) {
        return AuthUtils.doAuth(auth);
    }

}
