package myhome.accountbook.config;

import myhome.accountbook.dto.CategoryDto;
import myhome.accountbook.entity.Category;
import myhome.accountbook.entity.CategoryRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SimpleCategoryCache {
    private final CategoryRepository categoryRepository;

    private final Map<String, CategoryDto> cache = new ConcurrentHashMap<>();

    public SimpleCategoryCache(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public static SimpleCategoryCache instance;

    public static SimpleCategoryCache getInstance(){
        return instance;
    }

    @PostConstruct
    void init(){
        List<Category> list = categoryRepository.findAll();
        list.forEach(category -> {
            String key = category.getType() + "|" + category.getCode() + "|" + category.getSubCode();
            CategoryDto value = CategoryDto.builder()
                    .name(category.getName())
                    .code(category.getCode())
                    .subName(category.getSubName())
                    .subCode(category.getSubCode())
                    .type(category.getType())
                    .build();

            cache.put(key, value);
        });

        instance = this;
    }

    public CategoryDto getCategory(String key){
        return cache.get(key);
    }

    public CategoryDto getCategoryByName(String typeName, String category1Name, String category2Name){
        String type = typeName.equals("입금") ? "0" : "1";

        return cache.values().stream()
                .filter(categoryDto -> categoryDto.getType().equals(type))
                .filter(categoryDto -> categoryDto.getName().equals(category1Name))
                .filter(categoryDto -> categoryDto.getSubName().equals(category2Name))
                .findAny()
                .orElseThrow( () -> {
                    System.out.println(type);
                    System.out.println(category1Name);
                    System.out.println(category2Name);
                    return new RuntimeException("존재하지 않는 카테고리입니다.");
                });
    }

    public void refreshCategory() {
        this.init();
    }
}

