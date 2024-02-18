package myhome.accountbook.service.calculate;

import lombok.RequiredArgsConstructor;
import myhome.accountbook.dto.CalculateDetailDto;
import myhome.accountbook.dto.CalculateDto;
import myhome.accountbook.dto.CategoryDto;
import myhome.accountbook.entity.CalculateRepository;
import myhome.accountbook.util.CategoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculateService {

    private final CalculateRepository calculateRepository;

    private static final String IN_TYPE = "0";
    private static final String OUT_TYPE = "1";

    @Transactional(readOnly = true)
    public List<CalculateDto> calculateAll() {
        int monthPeriod = 12;

        Map<String, List<CalculateDetailDto>> inMap = calculateRepository.findContentsList(IN_TYPE, monthPeriod).stream()
                .map(inData -> {
                    CategoryDto categoryDto = getCategoryInfo(inData, IN_TYPE);
                    inData.setCategoryName(categoryDto.getName());
                    return inData;
                })
                .collect(Collectors.groupingBy(CalculateDetailDto::getDate));
        Map<String, List<CalculateDetailDto>> outMap = calculateRepository.findContentsList(OUT_TYPE, monthPeriod).stream()
                .map(outData -> {
                    CategoryDto categoryDto = getCategoryInfo(outData, OUT_TYPE);
                    outData.setCategoryName(categoryDto.getName());
                    return outData;
                })
                .collect(Collectors.groupingBy(CalculateDetailDto::getDate));

        List<String> dateList = new ArrayList<>();

        for (int i = 0; i <= monthPeriod; i++) {
            dateList.add(LocalDate.now().minusMonths(i).format(DateTimeFormatter.ofPattern("yyyyMM")));
        }

        return dateList.stream().map(calculateDate -> {
            List<CalculateDetailDto> inList = inMap.getOrDefault(calculateDate, new ArrayList<>());
            List<CalculateDetailDto> outList = outMap.getOrDefault(calculateDate, new ArrayList<>());

            return CalculateDto.builder()
                    .period(calculateDate)
                    .inList(inList)
                    .inTotal(inList.stream().mapToLong(CalculateDetailDto::getTotalAmount).sum())
                    .outList(outList)
                    .outTotal(outList.stream().mapToLong(CalculateDetailDto::getTotalAmount).sum())
                    .build();
        }).collect(Collectors.toList());

    }

    private CategoryDto getCategoryInfo(CalculateDetailDto detailDto, String type) {
        String key = CategoryUtils.generateKey(type, detailDto.getCategory());
        return CategoryUtils.getMyCategory(key);
    }
}
