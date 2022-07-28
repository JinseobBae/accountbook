package myhome.accountbook.service;

import lombok.RequiredArgsConstructor;
import myhome.accountbook.dto.AccountBookDto;
import myhome.accountbook.dto.CategoryDto;
import myhome.accountbook.entity.AccountBook;
import myhome.accountbook.entity.AccountBookRepository;
import myhome.accountbook.entity.Category;
import myhome.accountbook.entity.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountBookSearchService {

    private final AccountBookRepository accountBookRepository;
    private final ModelMapper modelMapper;

    public AccountBookDto searchBook(String bookName){
        AccountBook accountBook = accountBookRepository.findByName(bookName)
                .orElseThrow(() -> new RuntimeException("Accountbook not exist"));

        return modelMapper.map(accountBook, AccountBookDto.class);

    }
}
