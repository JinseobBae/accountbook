package myhome.accountbook.presentation;

import lombok.RequiredArgsConstructor;
import myhome.accountbook.dto.CategoryDto;
import myhome.accountbook.service.CategorySearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class CategoryController {

    private final CategorySearchService categorySearchService;

    @GetMapping("category")
    public List<CategoryDto> searchCategory(){
        return categorySearchService.searchCategory();
    }

}
