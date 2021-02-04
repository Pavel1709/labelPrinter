/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labelprinter;
import com.qoppa.office.ExcelConvertOptions;
import com.qoppa.office.ExcelDocument;
import com.qoppa.office.OfficeException;
import java.awt.Desktop;
import java.io.*;
import java.sql.SQLException;
import java.awt.print.*;
import java.io.File;
import java.util.*;
import javax.print.PrintService;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
/**
 *
 * @author pmolchanov
 */
public class LabelPrinter {
    public static ArrayList<String> codes = new ArrayList<String>();
    public static HashMap<String, ArrayList<String>> hm = new  HashMap<String, ArrayList<String>>();
    public static String code;

    public static void main(String[] args) {
        FrameForCodes f = new FrameForCodes();
        f.setVisible(true);
    }

}



