package com.training.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.junit.Test;

public class XlsxUtil {
    
    /**
     * 创建一个sheet的excel
     * @param xls_write_Address  文件输出路径
     * @param infoArray 所有行组成的集合，每一行的内容是一个数组，infoArray[0]设置列标题
     * @param sheetName 
     * @return
     * @throws IOException
     */
    public File write_Excel(String xls_write_Address, ArrayList<String[]> infoArray , String sheetName) throws IOException{
        
        ArrayList<ArrayList<String[]>> infoLists = new  ArrayList<ArrayList<String[]>>();
        
        infoLists.add(infoArray);
        
        write_Excel(xls_write_Address, infoLists, new String[]{sheetName});
        
        return new File(xls_write_Address);
    }
    
    /**
     * 创建包含多个sheet的excel
     * @param xls_write_Address 文件输出路径
     * @param infoLists 多个sheet组成的集合ArrayList&lt;ArrayList&lt;String[]&gt;&gt;，
     *                  <p>每个sheet所有行组成的集合是一个ArrayList&lt;String[]&gt;，
     *                  <p>每一行的内容是一个数组
     *                  <p>第一行设置列标题
     * @param sheetNames
     * @throws IOException
     */
    public void write_Excel( String xls_write_Address,ArrayList<ArrayList<String[]>> infoLists,
            /*ArrayList<ArrayList<String[]>> headerArrayLists ,*/
            String[] sheetNames ) throws IOException  {
        // 路径不能为null
        AssertUtils.hasText(xls_write_Address);
        
        AssertUtils.notEmpty(sheetNames);
        
        AssertUtils.notEmpty(infoLists, "infoLists is not null");
        
        FileOutputStream output = new FileOutputStream(new File(xls_write_Address));  //读取的文件路径   
        SXSSFWorkbook wb = new SXSSFWorkbook(10000);//内存中保留 10000 条数据，以免内存溢出，其余写入 硬盘          
        
        for(int sn=0;sn<infoLists.size();sn++){  
            Sheet sheet = wb.createSheet(String.valueOf(sn));  
//            
//            if(0==sn){
//                Row row = sheet.createRow(0);
//                for (short i = 0; i < headers.length; i++)  
//                {  
//                    Cell cell = row.createCell(i);  
//                    cell.setCellStyle(style);  
//                    HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
//                    cell.setCellValue(text);  
//                } 
//            }
  
            wb.setSheetName(sn, sheetNames[sn]);     
            ArrayList<String[]> ls2 = infoLists.get(sn);   
            for(int i=0;i<ls2.size();i++){  
                Row row = sheet.createRow(i);  
                String[] s = ls2.get(i);                  
                for(int cols=0;cols<s.length;cols++){  
                    Cell cell = row.createCell(cols);                     
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式                    
                    sheet.setColumnWidth(cols, s[cols].length()*384); //设置单元格宽度  
                    cell.setCellValue(s[cols]);//写入内容  
                }  
            }              
         }    
        wb.write(output);  
        output.close();           
    }  
    
    @Test
    public void test() throws IOException{
        String xls_write_Address = "C:\\Users\\Administrator\\Desktop\\export.xlsx";
        
        ArrayList<String[]> infoArray = new ArrayList<String[]>();
        
        String[] row = new String[5];
        
        row[0] = "主键";
        row[1] = "用户id";
        row[2] = "随机数";
        row[3] = "随机数";
        row[4] = "创建时间";
        
        infoArray.add(row);
        
        row = new String[5];
        row[0] = "1";
        row[1] = UUID.randomUUID().toString();
        row[2] = UUID.randomUUID().toString();
        row[3] = UUID.randomUUID().toString();
        row[4] = DateUtil.formatDate();
        
        infoArray.add(row);
        String sheetName = "sheet1";
        
        ArrayList<ArrayList<String[]>> infoLists = new ArrayList<ArrayList<String[]>>();
        infoLists.add(infoArray);
        infoLists.add(infoArray);
        
        String[] sheetNames = new String[2];
        
        sheetNames[0] = "one";
        sheetNames[1] = "two";
        
//        write_Excel(xls_write_Address,infoArray,sheetName);
        write_Excel(xls_write_Address,infoLists,sheetNames);
    }
}
