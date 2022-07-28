package myhome.accountbook.excel;

import lombok.RequiredArgsConstructor;
import myhome.accountbook.dto.ContentDto;
import myhome.accountbook.entity.Content;
import myhome.accountbook.entity.ContentRepository;
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

            for (rowNum = 0; rowNum < lastNum; rowNum++) {
                XSSFRow row = sheet.getRow(rowNum);

                if (row != null) {
                    list.add(ContentDto.builder()
                            .type(row.getCell(cellIndex++).getRawValue() + "")
                            .amount(Integer.parseInt(row.getCell(cellIndex++).getRawValue() + ""))
                            .title(row.getCell(cellIndex++).getStringCellValue())
                            .note(row.getCell(cellIndex++).getStringCellValue())
                            .realUseDt(stringToLocalDate(row.getCell(cellIndex++).getStringCellValue()))
                            .category1(row.getCell(cellIndex++).getStringCellValue())
                            .category2(row.getCell(cellIndex).getStringCellValue())
                            .build());

                }

                cellIndex = 0;
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
}
