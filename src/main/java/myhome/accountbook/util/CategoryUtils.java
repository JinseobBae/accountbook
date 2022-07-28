package myhome.accountbook.util;

import myhome.accountbook.config.SimpleCategoryCache;
import myhome.accountbook.dto.CategoryDto;

public class CategoryUtils {

    public static CategoryDto getMyCategory(String key){
        return SimpleCategoryCache.getInstance().getCategory(key);
    }
}
