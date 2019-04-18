package com.liuyao.demo.util.office;

import com.liuyao.demo.util.DateFormatUtil;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 文件工具类
 */
public class FileIOUtil {

    private final static String excel2003L = ".xls"; // 2003- 版本的excel
    private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel


    public static Map<String, String> fileUpload(HttpServletRequest request, MultipartFile file, String fileFolder){
        return fileUploadF(request, file, "resources/upload/" + fileFolder);
    }

    /**
     * 文件上传
     */
    public static Map<String, String> fileUploadF(HttpServletRequest request, MultipartFile file, String fileFolder){

        HttpSession se = request.getSession();

        Map<String, String> map = null;
        if (null !=file && !file.isEmpty()) {
             map = new HashMap<>();
            //文件大小
            Long filesize = file.getSize();

//            try {
                String path = se.getServletContext().getRealPath(fileFolder);
                String base = System.getProperty("catalina.home"); //获取tomcat根路径
//            } catch (Exception e){
//                e.printStackTrace();
//            }

            //获得文件名
            String filename = file.getOriginalFilename().replace("/", "|");
//            String[] fileFormat = filename.split("\\.");
            String saveFilename = DateFormatUtil.getTimeFormat(new Date(), "YYYYMMddHHmmss") + filename;

            File targetFile = new File(path, saveFilename);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            try {
                file.transferTo(targetFile);//
            } catch (Exception e) {
                e.printStackTrace();
            }

            String url = "/" + fileFolder + "/" + saveFilename;
            map.put("fileName", filename);
            map.put("filePath", url);
            map.put("fullFilePath", path + "/" + saveFilename);
            map.put("filesize", filesize.toString());
            map.put("saveFilename",saveFilename);
        }
        return map;
    }

    /**
     * 文件上传
     * @author XiaoJunXiang
     * @param file MultipartFile 文件对象
     * @param folder 想要存放的文件夹名称
     * @param request HttpServletRequest request对象
     * @return Map集合 ; 文件名key: fileName ; 保存路径key:filePath
     */
    public static Map<String,Object> uploadFile(MultipartFile file, String folder, HttpServletRequest request){
    	Map<String,Object> map = new HashMap<String,Object>();
    	String fileName = "";
    	String filePath = "";
		fileName = file.getOriginalFilename(); //获得文件名称
		//String base = System.getProperty("catalina.home");//获取服务器的根目录
		String base = request.getServletContext().getRealPath("resources");
		Date date = new Date();
		filePath = base + "/upload/"+folder+"/"+DateFormatUtil.getTimeFormat(date,"yyyyMMddHHmmss-")+fileName;
		 if (new File(base + "/upload/"+folder).exists()) { //判断文件夹是否存在，存在直接存，不存在创建存
    		try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(base + "/upload/"+folder, DateFormatUtil.getTimeFormat(date,"yyyyMMddHHmmss-")+fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }else {
			 new File(base + "/upload/"+folder).mkdirs();
			 try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(base + "/upload/"+folder, DateFormatUtil.getTimeFormat(date,"yyyyMMddHHmmss-")+fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
    	map.put("fileName",fileName);
    	map.put("filePath", filePath);
    	return map;
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

            if (fileName.endsWith("doc")){

                HWPFDocument document = new HWPFDocument(is);
                WordExtractor word = new WordExtractor(document);
                return word.getText();

            } else if (fileName.endsWith("docx")){

                XWPFDocument document = new XWPFDocument(is);
                XWPFWordExtractor word = new XWPFWordExtractor(document);
                return word.getText();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 导入工具类
     * @param multipartFile 文件
     * @param columns   列映射 map = {1: "name"; ......; 6: "age"} 第一列对应name字段, 第六列对应年龄(Excel中)
     * @param format 表格中涉及到的日期格式 "yyyy-MM-dd HH:mm:ss"
     * @param startRowNum 从第几行开始读 (Excel行号) >0
     */
    public static List<Map<String, String>> importExcel(MultipartFile multipartFile, Map<Integer, String> columns, String format, int startRowNum){

        if (null == multipartFile){
            System.out.println("文件不存在");
            return null;
        }

        List<Map<String, String>> list = new ArrayList<>();

        Workbook workbook = ExcelUtil.getWorkbook(multipartFile);
        if (null != workbook){
            for (int i = 0; i < workbook.getNumberOfSheets(); i++){

                Sheet sheet = workbook.getSheetAt(i);//获得sheet
                if (null == sheet) continue;
                int firstRowNum = sheet.getFirstRowNum();//有效数据第一行的行号
                int lastRowNum = sheet.getLastRowNum();//有效数据最后一行的行号

                startRowNum = startRowNum < 1 ? firstRowNum : startRowNum - 1; //设置开始行
                for (int j = startRowNum; j <= lastRowNum; j++){// 遍历行 得到所有行
                    Row row = sheet.getRow(j); //获得行
                    if (null == row) continue;
                    int firstCellNum = row.getFirstCellNum(); //有效数据第一列的列号
                    int lastCellNum = row.getLastCellNum(); //有效数据最后一列的列号

                    Map<String, String> map = null;
                    for (int k = firstCellNum; k < lastCellNum; k++){ //遍历列 得到一行的所有数据
                        Cell cell = row.getCell(k);// 获得单元格
                        if (null == cell) continue;
                        String cellValue = ExcelUtil.getCellValue(cell, format);
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
}