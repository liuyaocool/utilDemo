package com.liuyao.demo.util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

/**
 * 文件工具类
 */
public class FileIOUtil {

    /**
     * 导入工具类
     * @param multipartFile 文件
     * @param columns   列映射 map = {1: "name"; ......; 6: "age"} 第一列对应name字段, 第六列对应年龄(Excel中)
     * @param format 文件中涉及到的日期格式
     * @param startRowNum 从第几行开始读 (Excel行号) >0
     */
    public static List<Map<String, String>> importExcel(MultipartFile multipartFile, Map<Integer, String> columns, String format, int startRowNum){

        if (null == multipartFile){
            System.out.println("文件不存在");
            return null;
        }

        List<Map<String, String>> list = new ArrayList<>();

        Workbook workbook = getWorkbook(multipartFile);
        if (null != workbook){
            for (int i = 0; i < workbook.getNumberOfSheets(); i++){

                Sheet sheet = workbook.getSheetAt(i);//获得sheet
                if (null == sheet) continue;
                int firstRowNum = sheet.getFirstRowNum();//有效数据第一行的行号
                int lastRowNum = sheet.getLastRowNum();//有效数据最后一行的行号

                startRowNum = startRowNum < 0 ? firstRowNum : startRowNum - 1; //设置开始行
                for (int j = startRowNum; j <= lastRowNum; j++){// 遍历行 得到所有行
                    Row row = sheet.getRow(j); //获得行
                    if (null == row) continue;
                    int firstCellNum = row.getFirstCellNum(); //有效数据第一列的列号
                    int lastCellNum = row.getLastCellNum(); //有效数据最后一列的列号

                    Map<String, String> map = new HashMap<>();
                    for (int k = firstCellNum; k < lastCellNum; k++){ //遍历列 得到一行的所有数据
                        Cell cell = row.getCell(k);// 获得单元格
                        if (null == cell) continue;
                        if (columns.keySet().contains(k + 1)) {
                            map.put(columns.get(k + 1), getCellValue(cell, format));
                        }else {
                            map.put(String.valueOf(k), getCellValue(cell, format));
                        }
                    }
                    list.add(map);
                }
            }
        }
        return list;
    }

    /**
     * 获得工作表
     */
    public static Workbook getWorkbook(MultipartFile multipartFile){

        //获得文件名
        String fileName = multipartFile.getOriginalFilename();

        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")){
            System.out.println("文件不是Excel格式");
            return null;
        }
        Workbook workbook = null;

        try{
            //获得文件io流
            InputStream is = multipartFile.getInputStream();

            if (fileName.endsWith("xlsx")){
                workbook = new XSSFWorkbook(is);
            } else if (fileName.endsWith("xls")){
                workbook = new HSSFWorkbook();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     *  获得单元格数据
     */
    public static String getCellValue(Cell cell, String format) {
        System.out.println(cell.toString());

        if (!(CellType.NUMERIC == cell.getCellTypeEnum())) {
            return cell.toString();
        }
         if ((!HSSFDateUtil.isCellDateFormatted(cell))){
            return cell.toString();
        }
        Date date = cell.getDateCellValue();
        return DateFormatUtil.getDateFormat(date, format);
    }

}
