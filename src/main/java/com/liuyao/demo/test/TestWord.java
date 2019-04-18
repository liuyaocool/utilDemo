package com.liuyao.demo.test;

import com.liuyao.demo.util.FileIOUtil;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;

import java.math.BigInteger;

public class TestWord {

    private static ObjectFactory factory;
    private static  WordprocessingMLPackage wordMLPackage;

    public static void main(String[] args) throws Docx4JException {
        wordMLPackage = WordprocessingMLPackage.createPackage();
        factory = Context.getWmlObjectFactory();

        String path = "src/main/resources/static/file/";
        FileIOUtil.createFolder(path);

        Tbl table = createApprove();

        wordMLPackage.getMainDocumentPart().addObject(table);
        wordMLPackage.save(new java.io.File(path+"HelloWord1.docx"));
    }

    private static void addTableCell(Tr tableRow, String content) {
        Tc tableCell = factory.createTc();
        tableCell.getContent().add(
                wordMLPackage.getMainDocumentPart().createParagraphOfText(content));
        tableRow.getContent().add(tableCell);
    }

    private static void addBorders(Tbl table) {
        table.setTblPr(new TblPr());
        CTBorder border = new CTBorder();
        border.setColor("auto");
        border.setSz(new BigInteger("4"));
        border.setSpace(new BigInteger("0"));
        border.setVal(STBorder.SINGLE);

        TblBorders borders = new TblBorders();
        borders.setBottom(border);
        borders.setLeft(border);
        borders.setRight(border);
        borders.setTop(border);
        borders.setInsideH(border);
        borders.setInsideV(border);
        table.getTblPr().setTblBorders(borders);
    }

    private static Tbl createTableWithContent() {

        wordMLPackage.getMainDocumentPart().addParagraphOfText("                Hello Word!<br/>");
        wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title", "Hello Word!");//大标题
        wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle","This is a subtitle!"); //小标题

        Tbl table = factory.createTbl();
        Tr tableRow = factory.createTr();

        addTableCell(tableRow, "Field 1");
        addTableCell(tableRow, "Field 2");

        addRegularTableCell(tableRow, "Normal text");
        addStyledTableCell(tableRow, "Bold text", true, null);
        addStyledTableCell(tableRow, "Bold large text", true, "40");

        table.getContent().add(tableRow);
        addBorders(table);

        table.getContent().add(tableRow);

        addBorders(table);
        addTableRowWithMergedCells("Heading 1", "Heading 1.1",
                "Field 1", table);
        addTableRowWithMergedCells(null, "Heading 1.2", "Field 2", table);

        addTableRowWithMergedCells("Heading 2", "Heading 2.1",
                "Field 3", table);
        addTableRowWithMergedCells(null, "Heading 2.2", "Field 4", table);

        Tr tr = factory.createTr();
        addTableCellWithWidth(tr, "Field 1", 9500);
        addTableCellWithWidth(tr, "Field 2", 0);

        table.getContent().add(tr);
        return table;
    }

    /**
     *  本方法像前面例子中一样再一次创建了普通的单元格
     */
    private static void addRegularTableCell(Tr tableRow, String content) {
        Tc tableCell = factory.createTc();
        tableCell.getContent().add(
                wordMLPackage.getMainDocumentPart().createParagraphOfText(
                        content));
        tableRow.getContent().add(tableCell);
    }


    /**
     *  本方法给可运行块属性添加粗体属性. BooleanDefaultTrue是设置b属性的Docx4j对象, 严格
     *  来说我们不需要将值设置为true, 因为这是它的默认值.
     */
    private static void addBoldStyle(RPr runProperties) {
        BooleanDefaultTrue b = new BooleanDefaultTrue();
        b.setVal(true);
        runProperties.setB(b);
    }

    /**
     *  本方法为可运行块添加字体大小信息. 首先创建一个"半点"尺码对象, 然后设置fontSize
     *  参数作为该对象的值, 最后我们分别设置sz和szCs的字体大小.
     *  Finally we'll set the non-complex and complex script font sizes, sz and szCs respectively.
     */
    private static void setFontSize(RPr runProperties, String fontSize) {
        HpsMeasure size = new HpsMeasure();
        size.setVal(new BigInteger(fontSize));
        runProperties.setSz(size);
        runProperties.setSzCs(size);
    }



    /**
     *  这里我们添加实际的样式信息, 首先创建一个段落, 然后创建以单元格内容作为值的文本对象;
     *  第三步, 创建一个被称为运行块的对象, 它是一块或多块拥有共同属性的文本的容器, 并将文本对象添加
     *  到其中. 随后我们将运行块R添加到段落内容中.
     *  直到现在我们所做的还没有添加任何样式, 为了达到目标, 我们创建运行块属性对象并给它添加各种样式.
     *  这些运行块的属性随后被添加到运行块. 最后段落被添加到表格的单元格中.
     */
    private static void addStyling(Tc tableCell, String content, boolean bold, String fontSize) {
        P paragraph = factory.createP();

        Text text = factory.createText();
        text.setValue(content);

        R run = factory.createR();
        run.getContent().add(text);

        paragraph.getContent().add(run);

        RPr runProperties = factory.createRPr();
        if (bold) {
            addBoldStyle(runProperties);
        }

        if (fontSize != null && !fontSize.isEmpty()) {
            setFontSize(runProperties, fontSize);
        }

        run.setRPr(runProperties);

        tableCell.getContent().add(paragraph);
    }

    /**
     *  本方法创建单元格, 添加样式后添加到表格行中
     */
    private static void addStyledTableCell(Tr tableRow, String content,
                                           boolean bold, String fontSize) {
        Tc tableCell = factory.createTc();
        addStyling(tableCell, content, bold, fontSize);
        tableRow.getContent().add(tableCell);
    }


    /**
     *  本方法创建一行, 并向其中添加合并列, 然后添加再两个普通的单元格. 随后将该行添加到表格
     */
    private static void addTableRowWithMergedCells(String mergedContent,
                                                   String field1Content, String field2Content, Tbl table) {
        Tr tableRow1 = factory.createTr();

        addMergedColumn(tableRow1, mergedContent);

        addTableCell(tableRow1, field1Content);
        addTableCell(tableRow1, field2Content);

        table.getContent().add(tableRow1);
    }

    /**
     *  本方法添加一个合并了其它行单元格的列单元格. 如果传进来的内容是null, 传空字符串和一个为null的合并值.
     */
    private static void addMergedColumn(Tr row, String content) {
        if (content == null) {
            addMergedCell(row, "", null);
        } else {
            addMergedCell(row, content, "restart");
        }
    }

    /**
     *  我们创建一个单元格和单元格属性对象.
     *  也创建了一个纵向合并对象. 如果合并值不为null, 将它设置到合并对象中. 然后将该对象添加到
     *  单元格属性并将属性添加到单元格中. 最后设置单元格内容并将单元格添加到行中.
     *
     *  如果合并值为'restart', 表明要开始一个新行. 如果为null, 继续按前面的行处理, 也就是合并单元格.
     */
    private static void addMergedCell(Tr row, String content, String vMergeVal) {
        Tc tableCell = factory.createTc();
        TcPr tableCellProperties = new TcPr();

        TcPrInner.VMerge merge = new TcPrInner.VMerge();
        if(vMergeVal != null){
            merge.setVal(vMergeVal);
        }
        tableCellProperties.setVMerge(merge);

        tableCell.setTcPr(tableCellProperties);
        if(content != null) {
            tableCell.getContent().add(
                    wordMLPackage.getMainDocumentPart().
                            createParagraphOfText(content));
        }

        row.getContent().add(tableCell);
    }



    /**
     *  本方法创建一个单元格并将给定的内容添加进去.
     *  如果给定的宽度大于0, 将这个宽度设置到单元格.
     *  最后, 将单元格添加到行中.
     */
    private static void addTableCellWithWidth(Tr row, String content, int width){
        Tc tableCell = factory.createTc();
        tableCell.getContent().add(
                wordMLPackage.getMainDocumentPart().createParagraphOfText(
                        content));

        if (width > 0) {
            setCellWidth(tableCell, width);
        }
        row.getContent().add(tableCell);
//        row.getContent().add(factory.createBr());

    }

    /**
     *  本方法创建一个单元格属性集对象和一个表格宽度对象. 将给定的宽度设置到宽度对象然后将其添加到
     *  属性集对象. 最后将属性集对象设置到单元格中.
     */
    private static void setCellWidth(Tc tableCell, int width) {
        TcPr tableCellProperties = new TcPr();
        TblWidth tableWidth = new TblWidth();
        tableWidth.setW(BigInteger.valueOf(width));
        tableCellProperties.setTcW(tableWidth);
        tableCell.setTcPr(tableCellProperties);
    }


    /**
     * 项目中使用
     */
    private static Tbl createApprove(){
        Tbl table = factory.createTbl();

        Tr tr = factory.createTr();
        addTableCellWithWidth(tr, "Field 1", 10000);
        table.getContent().add(tr);

        Tr tr1 = factory.createTr();
        addTableCellWithWidth(tr1, "Field 2", 10000);
        Br br = factory.createBr();
//        br.
        table.getContent().add(tr1);
        table.getContent().add(br);

        addBorders(table);
        return table;
    }



}
