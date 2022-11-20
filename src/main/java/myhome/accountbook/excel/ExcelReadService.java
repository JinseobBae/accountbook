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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
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

            for (rowNum = 0; rowNum < lastNum; rowNum++) {
                XSSFRow row = sheet.getRow(rowNum);

                if (row != null) {
                    ContentDto contentDto = ContentDto.builder()
                            .type(row.getCell(cellIndex++).getStringCellValue())
                            .amount(Math.abs(Integer.parseInt(row.getCell(cellIndex++).getRawValue() + "")))
                            .title(row.getCell(cellIndex++).getStringCellValue())
                            .note(getNote(row.getCell(cellIndex++)))
                            .realUseDt(stringToLocalDate(row.getCell(cellIndex++).getStringCellValue()))
                            .category1(row.getCell(cellIndex++).getStringCellValue())
                            .category2(row.getCell(cellIndex).getStringCellValue())
                            .build();

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

        String[] splitDate = onlyDate.split("\\.");

        int[] intDate = Arrays.stream(splitDate).mapToInt(Integer::parseInt).toArray();

        return LocalDate.of(intDate[0], intDate[1], intDate[2]);
    }

    private String convertType(String value){
        if(value.equals("출금")){
            return "1";
        }else{
            return "0";
        }
    }

    private String getNote(XSSFCell cell){
        if(cell != null){
            return cell.getStringCellValue();
        }else{
            return "";
        }
    }
}
