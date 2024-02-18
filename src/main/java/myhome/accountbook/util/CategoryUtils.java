package myhome.accountbook.util;

import myhome.accountbook.config.SimpleCategoryCache;
import myhome.accountbook.dto.CategoryDto;
import org.springframework.util.StringUtils;

public class CategoryUtils {

    public static CategoryDto getMyCategory(String key){
        return SimpleCategoryCache.getInstance().getCategory(key);
    }

    public static String generateKey(String type){
        return generateKey(type, null, null);
    }

    public static String generateKey(String type, String category1){
        return generateKey(type, category1, null);
    }

    public static String generateKey(String type, String category1, String category2){
        StringBuilder stb = new StringBuilder();
        String delim = "|";

        stb.append(type);

        if(StringUtils.hasText(category1)){
            stb.append(delim);
            stb.append(category1);
        }else{
            stb.append(delim);
            stb.append("A");
        }

        if(StringUtils.hasText(category2)){
            stb.append(delim);
            stb.append(category2);
        }else{
            stb.append(delim);
            stb.append("A");
        }

        return stb.toString();
    }
}
