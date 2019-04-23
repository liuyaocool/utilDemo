package com.liuyao.demo.util.office;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WordCreate {

    private static ObjectFactory factory = Context.getWmlObjectFactory();
    private WordprocessingMLPackage wordMLPackage;
    private String folder;
    private String fileName;

    public WordCreate(String folder,String fileName) throws InvalidFormatException {
        this.folder = folder;
        this.fileName = fileName;
        this.wordMLPackage = WordprocessingMLPackage.createPackage();
    }

    //创建文件夹
    private void createFile() throws Docx4JException {
        //创建文件夹
        File targetFile = new File(this.folder);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        wordMLPackage.save(new File(folder+"/"+fileName));
    }

    /**
     * 创建表格
     */
    public Tbl createTable(){
        Tbl table = factory.createTbl();
        wordMLPackage.getMainDocumentPart().addObject(table);
        return table;
    }

    /**
     * 获得模板并添加
     */
    public Tbl addFromModel(String path) throws Docx4JException {
        this.wordMLPackage = getModel(path);
        List<Tbl> tbs = getTbs(wordMLPackage);
        return tbs.size() < 1 ? factory.createTbl() : tbs.get(0);
    }

    /**
     * 获得模板数据
     */
    public WordprocessingMLPackage getModel(String filePath) throws Docx4JException {
        File file = new File(filePath);
        return WordprocessingMLPackage.load(file);
    }
    /**
     * 获得tbl的List 逆向
     */
    public List<Tbl> getTbs(WordprocessingMLPackage work) throws Docx4JException {
        List<Tbl> tbs = new ArrayList<>();
        List<Object> docs = work.getMainDocumentPart().getContent();
        JAXBElement aa;
        for (int i = docs.size()-1; i >= 0 ; i--) {
            if (docs.get(i) instanceof JAXBElement){
                aa = (JAXBElement) docs.get(i);
                if (aa.getValue() instanceof Tbl){
                    tbs.add((Tbl) aa.getValue());
                }
            }
        }
        return tbs;
    }

    /**
     * 创建表格行
     */
    public Tr createTr(Tbl tbl){
        Tr tr = factory.createTr();
        tbl.getContent().add(tr);
        return tr;
    }

    /**
     * 创建单元格
     */
    public Tc createTc(Tr tr, List<String> cons){
        Tc tc = factory.createTc();
        tr.getContent().add(tc);
        for (int i = 0; i < cons.size(); i++) {
            tc.getContent().add(wordMLPackage.getMainDocumentPart().createParagraphOfText(cons.get(i)));
        }
        return tc;
    }
    public Tc createTc(Tr tr, String content){
        Tc tc = factory.createTc();
        tr.getContent().add(tc);
        tc.getContent().add(wordMLPackage.getMainDocumentPart().createParagraphOfText(content));
        return tc;
    }

    /**
     * 获得tr的list 逆向
     */
    public List<Tr> getTrs(Tbl table){
        List<Tr> trs = new ArrayList<>();
        List<Object> os = table.getContent();
        for (int i = os.size()-1; i >= 0; i--) {
            if (os.get(i) instanceof Tr){
                trs.add((Tr) os.get(i));
            }
        }
        return trs;
    }
    /**
     * 获得tc的list 正向
     */
    public List<Tc> getTcs(Tr tr){
        List<Tc> tcs = new ArrayList<>();
        List<Object> os = tr.getContent();
        JAXBElement aa;
        for (int i = 0; i < os.size(); i++) {
            if (os.get(i) instanceof JAXBElement){
                aa = (JAXBElement) os.get(i);
                if (aa.getValue() instanceof Tc){
                    tcs.add((Tc) aa.getValue());
                }
            }
        }
        return tcs;
    }

    /**
     * 获得单元格段落 正向
     */
    public List<P> getPs(Tc tc){
        List<P> ps = new ArrayList<>();
        List<Object> os = tc.getContent();
        for (int i = 0; i < os.size(); i++) {
            if (os.get(i) instanceof P){
                ps.add((P) os.get(i));
            }
        }
        return ps;
    }

    /**
     * 替换单元格占位符
     */
    public void replaceAll(Tc tc, Map<String, String> params){
        for (String key: params.keySet()) {

        }
    }

    /**
     * 替换占位符
     */
    public void replace(P p, String oldStr, String newStr){
        String content = getText(p).replace("${"+oldStr+"}",newStr);
        List<Object> rs = getElements(p,R.class);
        R r = (R) rs.get(0);
        if (r.getContent().get(0) instanceof JAXBElement &&
                ((JAXBElement) r.getContent().get(0)).getValue() instanceof Text){
            ((Text)((JAXBElement) r.getContent().get(0)).getValue()).setValue(content);
        }
        p.getContent().clear();
        p.getContent().add(r);
    }

    /**
     * 获得P中的文本
     */
    public String getText(P p){
        StringBuilder result = new StringBuilder();
        List<Object> texts = getElements(p, Text.class);
        for (int i = 0; i < texts.size(); i++) {
            result.append(((Text) texts.get(i)).getValue());
        }
        return result.toString();
    }

    /**
     * 获得所有对象 递归实现
     * @param obj 从哪
     * @param toSearch 查询的类型
     * @return
     */
    private static List<Object> getElements(Object obj, Class<?> toSearch) {
        List<Object> result = new ArrayList<Object>();
        if (obj instanceof JAXBElement) obj = ((JAXBElement<?>) obj).getValue();

        if (obj.getClass().equals(toSearch)){
            result.add(obj);
        } else if (obj instanceof ContentAccessor) {
            List<Object> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getElements(child, toSearch));
            }
        }
        return result;
    }


        /**
         *
         */
    public static void main(String[] args) throws Docx4JException {
        WordCreate wordCreate = new WordCreate("C:/Users/61/Desktop/doc/","aaa.doc");
        Tbl table = wordCreate.addFromModel("C:/Users/61/Desktop/doc/3.xml");
//        List<String[]> aa = new ArrayList<>();
//        aa.add(new String[] {"aa","aa1"});
//        aa.add(new String[] {"bb","bb1"});
//        aa.add(new String[] {"cc","cc1"});
//        aa.add(new String[] {"dd","dd1"});
        List<Tr> trs = wordCreate.getTrs(table);
        List<Tc> tcs = wordCreate.getTcs(trs.get(0));
        List<P> ps = wordCreate.getPs(tcs.get(0));
//        wordCreate.replace(ps.get(0),"","");
        System.out.println(wordCreate.getText(ps.get(0)));
        wordCreate.replace(ps.get(0),"title", "aaa");
//        Tr tr = wordCreate.createTr(table);
//        Tc tc = wordCreate.createTc(tr,"组长");
//        Tc tc2 = wordCreate.createTc(tr,"张三");
//        Tc tc3 = wordCreate.createTc(tr,"AAA333");
//        wordCreate.createTc(tr,"AAA333");
//        Tc tc = factory.createTc();
//        tc.getContent().add( "");
//        tr.getContent().add(tc);
        wordCreate.createFile();

        System.out.println("============");

    }

}
