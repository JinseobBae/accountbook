package myhome.accountbook.service;

import lombok.RequiredArgsConstructor;
import myhome.accountbook.dto.CategoryDto;
import myhome.accountbook.entity.Category;
import myhome.accountbook.entity.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategorySearchService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<CategoryDto> searchCategory(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

    }
}
