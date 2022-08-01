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
                                    .subName(category.getSubName())
                                    .build();

            cache.put(key, value);
        });

        instance = this;
    }

    public CategoryDto getCategory(String key){
        return cache.get(key);
    }
}

