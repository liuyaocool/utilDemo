package com.liuyao.demo.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件 IO 流工具类  学习使用(非项目)
 */
public class MyFileIOUtil {

    public static boolean isExcel2003(String filePath)
    {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isExcel2007(String filePath)
    {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    public static List<Map<String, String>> importExcel(MultipartFile multipartFile, Class clazz, Map<Integer, String> columns, String format){

        if (null == multipartFile){
            System.out.println("文件不存在");
            return null;
        }

        List<Map<String, String>> list = new ArrayList<>();

        Workbook workbook = FileIOUtil.getWorkbook(multipartFile);
        if (null != workbook){
            for (int i = 0; i < workbook.getNumberOfSheets(); i++){

                Sheet sheet = workbook.getSheetAt(i);//获得sheet
                if (null == sheet) continue;
                int firstRowNum = sheet.getFirstRowNum();//有效数据第一行的行号
                int lastRowNum = sheet.getLastRowNum();//有效数据最后一行的行号
                for (int j = firstRowNum; j <= lastRowNum; j++){// 遍历行 得到所有行
                    Row row = sheet.getRow(j); //获得行
                    if (null == row) continue;
                    int firstCellNum = row.getFirstCellNum(); //有效数据第一列的列号
                    int lastCellNum = row.getLastCellNum(); //有效数据最后一列的列号

                    Map<String, String> map = new HashMap<>();
                    for (int k = firstCellNum; k < lastCellNum; k++){ //遍历列 得到一行的所有数据
                        Cell cell = row.getCell(k);// 获得单元格
                        if (null == cell) continue;
                        if (columns.keySet().contains(k)) {
                            map.put(columns.get(k), FileIOUtil.getCellValue(cell, format));
                        }else {
                            map.put(String.valueOf(k), FileIOUtil.getCellValue(cell, format));
                        }
                    }
                    list.add(map);
                }
            }
        }

//        String className = clazz.getName();
//        System.out.println("==============" + className);
//        //获得属性的所有信息
//        Field[] fields = clazz.getDeclaredFields();
//        for (Field f: fields) {
//            //获得属性名
//            String fstr = f.getName();
//            System.out.print("属性类型:" + f.getGenericType().toString());
//            System.out.println("属性名:" + fstr);
//        }
//
//        try {
//            //通过反射创建对象
//            Object object = Class.forName(className).newInstance();
//            //通过反射给属性赋值
//            for (Integer column: columnMap.keySet()) {
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        return new ArrayList<Map<Integer, String>>();
        return list;
    }
}
