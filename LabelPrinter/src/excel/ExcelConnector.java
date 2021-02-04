/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;
import labelprinter.LabelPrinter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author pmolchanov
 */
public class ExcelConnector {

    public static double[] codes;
    public static File file;
    public static void removeFromXLS() throws FileNotFoundException, IOException{
        Path file_path=FileSystems.getDefault().getPath("Codes.xls");
        HSSFWorkbook myBook= new HSSFWorkbook(new FileInputStream(file_path.toString()));
        HSSFSheet MySheet= myBook.getSheet("List1");
        int rowCount=MySheet.getPhysicalNumberOfRows();
        codes = new double[rowCount];
        for(int i=0;i<rowCount;i++) {
            codes[i]=MySheet.getRow(i).getCell(0).getNumericCellValue();
        }

    }
    public static void createXLS() throws FileNotFoundException, IOException {
        HSSFWorkbook workbook= new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Codes");
        Cell cell;
        Row row;
        int rownum = 0;
        HSSFCellStyle style1 = createStyleForTitle(workbook);
        HSSFCellStyle style2 = createStyleForBarcode(workbook);
        HSSFCellStyle style3 = createStyleForArt(workbook);
        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:C1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("E1:J1"));
        row = sheet.createRow(rownum);
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Артикул");
        cell.setCellStyle(style1);
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Код Маркировки");
        cell.setCellStyle(style1);
      
        rownum+=2;
        int i = 0; // количество кодов на один артикул
        for (Map.Entry<String, ArrayList<String>> pair: LabelPrinter.hm.entrySet()) {
           int l = 0;
           i = pair.getValue().size();
           sheet.addMergedRegion(CellRangeAddress.valueOf("A"+ (rownum +1 ) +":C"+ (rownum + i*3 -1) ));
           for (int k = 0; k < i; k+=1) {
                sheet.addMergedRegion(CellRangeAddress.valueOf("D"+ (rownum + 1) +":J"+ (rownum + 2) ));
                row = sheet.createRow(rownum);
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue("*" + pair.getValue().get(l) + "*");
                cell.setCellStyle(style2);
                l++;
                rownum+=3;
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(pair.getKey());
                cell.setCellStyle(style3);
            }
            rownum+=1;
        }
        file = new File("C:/demo/codes.xls");
        file.getParentFile().mkdirs();
        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        System.out.println("Created file: " + file.getAbsolutePath());


    }
      private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        short s = 400;
        font.setFontHeight(s);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }
    private static HSSFCellStyle createStyleForArt(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        short s = 300;
        font.setFontHeight(s);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        return style;
    }
        private static HSSFCellStyle createStyleForBarcode(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontName("3 of 9 Barcode");
        short s = 540;
        font.setFontHeight(s);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }
        public static void openXLS() throws IOException {
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) {         //checks file exists or not
                desktop.open(file);
            }
        }
}
