package myhome.accountbook;

import myhome.accountbook.excel.ExcelReadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExcelUploadTest {

    @Autowired
    ExcelReadService excelReadService;


    @Test
    void excelReadTest(){
        String path = "C:/Users/jinseob/Desktop/10월거래내역.xlsx";
        excelReadService.read(path);
    }

}
