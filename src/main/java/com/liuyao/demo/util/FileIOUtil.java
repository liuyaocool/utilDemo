package com.liuyao.demo.util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * 文件工具类
 */
public class FileIOUtil {

    public static String upLoadFile(MultipartFile multipartFile, HttpServletRequest request, String path) throws IOException {

        InputStream is = multipartFile.getInputStream();

//        String fileName = DateFormatUtil.getDateFormat(new Date(), "yyyyMMddHHmmss");
        String fileName = UUID.randomUUID().toString() + multipartFile.getOriginalFilename();
        String proPath = request.getContextPath();

        FileOutputStream out = new FileOutputStream(proPath + "/" + fileName);

        return proPath;
    }

    /**
     * 文件上传
     */
    public static Map<String, String> fileUpload(HttpSession se, MultipartFile file, String fileFolder){

        String url = "";
        String folder = "resources/upload/";
        Map<String, String> map = new HashMap<>();
        if (null !=file && !file.isEmpty()) {

//            try {
            String path = se.getServletContext().getRealPath(folder + fileFolder);
            String base = System.getProperty("catalina.home"); //获取tomcat根路径
//            } catch (Exception e){
//                e.printStackTrace();
//            }

            //获得文件名
            String filename = file.getOriginalFilename().replace("/", "|");
//            String[] fileFormat = filename.split("\\.");
            String saveFilename = DateFormatUtil.getDateFormat(new Date(), "YYYYMMddHHmmss") + filename;

            File targetFile = new File(path, saveFilename);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            try {
                file.transferTo(targetFile);//
            } catch (Exception e) {
                e.printStackTrace();
            }

            url = "/" + folder + fileFolder + "/" + saveFilename;

            map.put("fileName", filename);
            map.put("filePath", url);
        }
        return map;
    }


    /**
     * 导入工具类
     * @param multipartFile 文件
     * @param columns   列映射 map = {1: "name"; ......; 6: "age"} 第一列对应name字段, 第六列对应年龄(Excel中)
     * @param format 文件中涉及到的日期格式
     * @param startRowNum 从第几行开始读 (Excel行号) >0
     * @return 遍历List中Map的时候需要判断是否包含key
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

                    Map<String, String> map = null;
                    for (int k = firstCellNum; k < lastCellNum; k++){ //遍历列 得到一行的所有数据
                        Cell cell = row.getCell(k);// 获得单元格
                        if (null == cell) continue;
                        String cellValue = getCellValue(cell, format);
                        if ("".equals(cellValue)) continue;
                        if (null == map) map = new HashMap<>();
                        if (columns.keySet().contains(k + 1)) {
                            map.put(columns.get(k + 1), cellValue);
                        }else {
                            map.put(String.valueOf(k +1), cellValue);
                        }
                    }
                    if (null == map) continue;
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

    /**
     * 解析word
     */
    public static String getWordBook(MultipartFile multipartFile){

        if (multipartFile.isEmpty()) return null;

        //获得文件名
        String fileName = multipartFile.getOriginalFilename();

        if (!fileName.endsWith("doc") && !fileName.endsWith("docx")){
            System.out.println("文件不是Word格式");
            return null;
        }

        try{
            //获得文件io流
            InputStream is = multipartFile.getInputStream();
//            InputStream is = new FileInputStream(new File(filePath));

            if (fileName.endsWith("doc")){
                HWPFDocument document = new HWPFDocument(is); // poi-scratchpad-3.16.jar
                WordExtractor word = new WordExtractor(document); // poi-scratchpad-3.16.jar

                StringBuilder wordText = new StringBuilder();
                for (String ss:word.getParagraphText()) {
                    wordText.append(ss);
                }
                return wordText.toString();
//                return word.getText();
            } else if (fileName.endsWith("docx")){
//                OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
//                POIXMLTextExtractor word = new XWPFWordExtractor(opcPackage);
//                POIXMLTextExtractor extractor = new XWPFWordExtractor(is);
//                WordExtractor word = new HSSFWorkbook(is)


//                XWPFDocument document = new XWPFDocument(is); // poi-ooxml-3.16.jar
//                XWPFWordExtractor word = new XWPFWordExtractor(document); // poi-ooxml-3.16.jar
//                return word.getText();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
