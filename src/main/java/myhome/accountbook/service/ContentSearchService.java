package myhome.accountbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myhome.accountbook.dto.CategoryDto;
import myhome.accountbook.dto.ContentDto;
import myhome.accountbook.dto.ContentSearchDto;
import myhome.accountbook.entity.AccountBook;
import myhome.accountbook.entity.AccountBookRepository;
import myhome.accountbook.entity.Content;
import myhome.accountbook.entity.ContentRepository;
import myhome.accountbook.util.CategoryUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static myhome.accountbook.common.SpecificationFactory.contentSpec;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentSearchService {

    private final ContentRepository contentRepository;
    private final AccountBookRepository accountBookRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<ContentDto> searchAllContent(ContentSearchDto contentSearchDto) {

        AccountBook accountBook = accountBookRepository.findByName(contentSearchDto.getAccountBookName())
                .orElseThrow(() -> new RuntimeException("Accountbook not exist"));

        List<Content> contents = contentRepository.findAll(contentSpec(contentSearchDto, accountBook), Sort.by(Sort.Direction.DESC, "realUseDt", "id"));

        return contents.stream().map(content -> {
            ContentDto dto = modelMapper.map(content, ContentDto.class);
            CategoryDto category = CategoryUtils.getMyCategory(CategoryUtils.generateKey(dto.getType(), dto.getCategory1(), dto.getCategory2()));

            if(category != null){
                dto.setCategory1(category.getName());
                dto.setCategory2(category.getSubName());
            }else{
                log.error("key ==> {}", dto.getType() + dto.getCategory1() + dto.getCategory2());
                log.error("dto ==> {}", dto.getTitle());
            }

            return dto;

        }).collect(Collectors.toList());
    }




}
