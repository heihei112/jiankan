
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;

public class xls {
    public static void main(String[] args) {


    }

    @Test
    public void text1() throws Exception {
        //创建工作簿
        //创建工作簿
        XSSFWorkbook xsf =  new XSSFWorkbook(new FileInputStream("D:\\cxl.xlsx"));
        XSSFSheet sheetAt = xsf.getSheetAt(0);
        for (Row row : sheetAt){
            for (Cell cell : row){
                String value = cell.getStringCellValue();
                System.out.println(value);
            }
        }
        xsf.close();
    }
    @Test
    public void text2()throws Exception{
        //创建工作簿
        XSSFWorkbook xsff = new XSSFWorkbook(new FileInputStream("D:\\cxl.xlsx"));
        //从0号索引开始获取每一行
        XSSFSheet sheetAt = xsff.getSheetAt(0);
        //获取一行中最后一个行号
        int lastRowNum = sheetAt.getLastRowNum();
        for(int i = 0 ;i<=lastRowNum ;i++){
            XSSFRow row = sheetAt.getRow(i);
            short lastCellNum = row.getLastCellNum();
            for (int j = 0 ;j<lastCellNum;j++){
                XSSFCell cell = row.getCell(j);
                System.out.println(cell.getStringCellValue());
            }
        }
        xsff.close();
    }
}
