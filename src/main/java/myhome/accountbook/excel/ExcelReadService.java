package myhome.accountbook.excel;

import lombok.RequiredArgsConstructor;
import myhome.accountbook.config.SimpleCategoryCache;
import myhome.accountbook.dto.CategoryDto;
import myhome.accountbook.dto.ContentDto;
import myhome.accountbook.entity.Content;
import myhome.accountbook.entity.ContentRepository;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 임시 엑셀 읽기용
 */
@Service
@RequiredArgsConstructor
public class ExcelReadService {

    private final ContentRepository contentRepository;

    @Transactional
    public void read(String path) {

        List<ContentDto> list = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(path)) {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            int rowNum = 0;
            int cellIndex = 0;

            XSSFSheet sheet = workbook.getSheetAt(0);

            int lastNum = sheet.getPhysicalNumberOfRows();

            SimpleCategoryCache categoryCache = SimpleCategoryCache.getInstance();

            for (rowNum = 1; rowNum < lastNum; rowNum++) {
                XSSFRow row = sheet.getRow(rowNum);

                if (row != null) {
                    ContentDto contentDto = ContentDto.builder()
                            .realUseDt(stringToLocalDate(row.getCell(cellIndex++).getStringCellValue()))
                            .type(row.getCell(cellIndex++).getStringCellValue())
                            .amount(Math.abs(Integer.parseInt(row.getCell(cellIndex++).getStringCellValue().replace(",",""))))
                            .title(row.getCell(cellIndex++).getStringCellValue())
                            .note(getNote(row.getCell(cellIndex++)))
                            .category1(getOrNull(row.getCell(cellIndex++)))
                            .category2(getOrNull(row.getCell(cellIndex)))
                            .build();

                    if (contentDto.getCategory1() == null) {
                        findCategory(contentDto);
                    }

                    CategoryDto categoryDto = categoryCache.getCategoryByName(contentDto.getType(), contentDto.getCategory1(), contentDto.getCategory2());

                    contentDto.changeNameToCode(categoryDto.getType(), categoryDto.getCode(), categoryDto.getSubCode());

                    list.add(contentDto);
                }

                cellIndex = 0;
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException npe){
            npe.printStackTrace();
        }


        if(!list.isEmpty()){
            list.forEach(data -> contentRepository.save(data.toEntity()));
        }

    }

    private LocalDate stringToLocalDate(String date) {
        String onlyDate = date.split(" ")[0];

//        String[] splitDate = onlyDate.split("\\.");

//        int[] intDate = Arrays.stream(splitDate).mapToInt(Integer::parseInt).toArray();

        return LocalDate.parse(onlyDate, DateTimeFormatter.ofPattern("yyyy.MM.dd"));

//        return LocalDate.of(intDate[0], intDate[1], intDate[2]);
    }

    private String getNote(XSSFCell cell){
        if(cell != null){
            return cell.getStringCellValue();
        }else{
            return "";
        }
    }

    private String getOrNull(XSSFCell cell) {
        return Optional.ofNullable(cell)
                .map(XSSFCell::getStringCellValue)
                .orElse(null);
    }

    private void findCategory(ContentDto contentDto){
        String title = contentDto.getTitle() ;

        if (title.contains("전기료")) {
            contentDto.setCategory1("공과금");
            contentDto.setCategory2("전기");
        } else if (title.contains("인터넷")) {
            contentDto.setCategory1("공과금");
            contentDto.setCategory2("인터넷");
        } else if (title.contains("대출이자")) {
            contentDto.setCategory1("공과금");
            contentDto.setCategory2("이자");
        } else if (title.contains("수도")) {
            contentDto.setCategory1("공과금");
            contentDto.setCategory2("수도");
        } else if (title.contains("월세")) {
            contentDto.setCategory1("공과금");
            contentDto.setCategory2("월세");
        } else if (title.contains("코원에너지서비스")) {
            contentDto.setCategory1("공과금");
            contentDto.setCategory2("가스");
        } else if (title.contains("현대오일뱅크") || title.contains("지에스칼텍스")) {
            contentDto.setCategory1("차량");
            contentDto.setCategory2("주유비");
        } else if (title.contains("성남시청")) {
            contentDto.setCategory1("차량");
            contentDto.setCategory2("주차비");
        } else if (title.contains("홈플러스") || title.contains("이마트")) {
            contentDto.setCategory1("생활");
            contentDto.setCategory2("마트/편의점/백화점");
        } else if (title.contains("더마켓")) {
            contentDto.setCategory1("생활");
            contentDto.setCategory2("E-커머스");
        } else if (title.contains("차의과학대학교")) {
            contentDto.setCategory1("병원");
            contentDto.setCategory2("진료비");
        }
    }
}
