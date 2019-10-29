package com.liuyao.demo.util.office.office;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ExcelCreate {

    private HttpServletResponse response;
    private String fileName;
    private String encoding;
    private XSSFWorkbook workbook;
    private XSSFSheet lastSheet;//正在操作的最后一个
    private XSSFRow lastRow;//正在操作的最后一行
    private final float H_RATIO = 21.3f;

    public ExcelCreate(HttpServletResponse response, String fileName, int defaultHeight) {
        this(response, "utf-8", fileName, defaultHeight);
    }

    public ExcelCreate(HttpServletResponse response, String encoding, String fileName, int defaultHeight) {
        this(response, new XSSFWorkbook(), encoding, fileName);
        this.workbook = new XSSFWorkbook();
        createSeet(defaultHeight);
    }

    public ExcelCreate(HttpServletResponse response, XSSFWorkbook workbook,String encoding, String fileName) {
        this.response = response;
        this.workbook = workbook;
        this.encoding = encoding;
        this.fileName = fileName + ".xlsx";
    }

    public XSSFSheet createSeet(String sheetName){
        this.lastSheet = this.workbook.createSheet(sheetName);
        return this.lastSheet;
    }

    public XSSFSheet createSeet(int defaultHeight){
        this.lastSheet = this.workbook.createSheet();
        this.lastSheet.setDefaultRowHeight((short) (defaultHeight * H_RATIO));
        return this.lastSheet;
    }

    public XSSFSheet createSeet(){
        this.lastSheet = this.workbook.createSheet();
        return this.lastSheet;
    }

    public XSSFRow createRow(int index){
        this.lastRow = this.lastSheet.createRow(index);
        return this.lastRow;
    }

    /**
     * 最后位置追加行
     * @return
     */
    public XSSFRow appendRow(){
        int index = this.lastSheet.getLastRowNum();
        if (index == 0 && null == this.lastSheet.getRow(index)){
            index--;
        }
        this.lastRow = createRow(index+1);
        return this.lastRow;
    }

    /**
     * 最后位置追加行
     * @return
     */
    public XSSFRow appendRow(CellStyle style, String content){
        appendRow();
        appendCell(style,content);
        return this.lastRow;
    }

    /**
     * 最后位置追加行
     * @return
     */
    public XSSFRow appendRow(CellStyle style, double height, XSSFRichTextString content){
        appendRow();
        this.lastRow.setHeight((short) (height * H_RATIO));
        appendCell(style,content);
        return this.lastRow;
    }

    /**
     * 最后位置追加行
     * @return
     */
    public XSSFRow appendRow(CellStyle style, double height, String content){
        appendRow();
        this.lastRow.setHeight((short) (height * H_RATIO));
        appendCell(style,content);
        return this.lastRow;
    }

    /**
     * 最后位置追加单元格
     * @param content 内容
     * @param style 样式
     * @return
     */
    public XSSFCell appendCell(CellStyle style, String content){
        XSSFCell cell = appendCell();
        cell.setCellValue(content);
        if (null != style){
            cell.setCellStyle(style);
        }
        return cell;
    }

    public XSSFCell appendCell(CellStyle style, XSSFRichTextString content){
        XSSFCell cell = appendCell();
        cell.setCellValue(content);
        if (null != style){
            cell.setCellStyle(style);
        }
        return cell;
    }

    public XSSFCell appendCell(){
        return this.lastRow.createCell(this.lastRow.getLastCellNum() < 0 ? 0 : this.lastRow.getLastCellNum());
    }

    public Font createFont(){
        return this.workbook.createFont();
    }

    /**
     * 合并
     */
    public void merge(int startRow, int endRow, int startCell, int endCell){
        this.lastSheet.addMergedRegion(new CellRangeAddress(startRow, endRow, startCell, endCell));
    }

    /**
     * 设置列宽
     */
    public void width(int index, int width){
        this.lastSheet.setColumnWidth(index, width * 256);
    }

    /**
     * 居中对齐样式
     */
    public XSSFCellStyle centerStyle(int borderSize, String fontStyle, int fontSize, boolean blod){
        return style(borderSize, fontStyle, fontSize, blod, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);
    }
    /**
     * 设置样式
     */
    public XSSFCellStyle style(int borderSize, String fontStyle, int fontSize, boolean blod, int alignX, int alignY, boolean warp) {
        XSSFCellStyle css = this.workbook.createCellStyle();
        if (borderSize > 0){
            short bs = (short) borderSize;
            css.setBorderBottom(bs); // 下边框
            css.setBorderLeft(bs);// 左边框
            css.setBorderTop(bs);// 上边框
            css.setBorderRight(bs);// 右边框
        }
        if (alignY > 0) css.setVerticalAlignment((short) alignY);//垂直居中
        if (alignX > 0) css.setAlignment((short) alignX); // 水平居中
        css.setWrapText(warp);//自动换行
        // 设置字体:
        Font font = workbook.createFont();
        if (null != fontStyle && !"".equals(fontStyle)) font.setFontName(fontStyle);
        if (fontSize > 4) font.setFontHeightInPoints((short) fontSize);// 设置字体大小
        font.setBold(blod);// 粗体显示
        css.setFont(font);

//        XSSFDataFormat format =  (XSSFDataFormat) workbook.createDataFormat();//设置文本格式样式
//        css.setDataFormat(format.getFormat("@"));
        return css;
    }


    public void saveFile() throws IOException {
        setRespHeader();
        OutputStream ops = this.response.getOutputStream();
        this.workbook.write(ops);
        ops.flush();
        ops.close();
    }

    public void setRespHeader() throws UnsupportedEncodingException {
        this.fileName = this.response.encodeURL(new String(this.fileName.getBytes(),this.encoding));
        this.response.setContentType("application/octet-stream;charset=" + this.encoding);
        response.setHeader("Content-Disposition", "attachment;filename="+ this.fileName);
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    }
}
