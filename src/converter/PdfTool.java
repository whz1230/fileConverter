package converter;

import org.apache.commons.io.IOUtils;
import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.*;

public class PdfTool {
    /**
     * docx文档转换为PDF
     *
     * @param docxPath 文档
     * @param pdfPath PDF文档存储路径
     * @throws Exception 可能为Docx4JException, FileNotFoundException, IOException等
     */
    public static void convertDocxToPDF(String docxPath, String pdfPath) throws Exception {
        OutputStream os = null;
        try {
            WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(new File(docxPath));
            //Mapper fontMapper = new BestMatchingMapper();
            Mapper fontMapper = new IdentityPlusMapper();
            fontMapper.put("华文行楷", PhysicalFonts.get("STXingkai"));
            fontMapper.put("华文仿宋", PhysicalFonts.get("STFangsong"));
            fontMapper.put("隶书", PhysicalFonts.get("LiSu"));
            mlPackage.setFontMapper(fontMapper);

            os = new FileOutputStream(pdfPath);

            FOSettings foSettings = Docx4J.createFOSettings();
            foSettings.setWmlPackage(mlPackage);
            Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);

        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            IOUtils.closeQuietly(os);
        }
    }

    /**
     * 把docx转成html
     * @param docxFilePath
     * @param htmlPath
     * @throws Exception
     */
    public static void convertDocxToHtml(String docxFilePath,String htmlPath) throws Exception{

        WordprocessingMLPackage wordMLPackage= Docx4J.load(new File(docxFilePath));

        HTMLSettings htmlSettings = Docx4J.createHTMLSettings();
        String imageFilePath=htmlPath.substring(0,htmlPath.lastIndexOf("/")+1)+"/images";
        htmlSettings.setImageDirPath(imageFilePath);
        htmlSettings.setImageTargetUri( "images");
        htmlSettings.setWmlPackage(wordMLPackage);

        String userCSS = "html, body, div, span, h1, h2, h3, h4, h5, h6, p, a, img,  ol, ul, li, table, caption, tbody, tfoot, thead, tr, th, td " +
                "{ margin: 0; padding: 0; border: 0;}" +
                "body {line-height: 1;} ";

        htmlSettings.setUserCSS(userCSS);

        OutputStream os;

        os = new FileOutputStream(htmlPath);

        Docx4jProperties.setProperty("docx4j.Convert.Out.HTML.OutputMethodXML", true);

        Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);

    }

    public static void main(String[] args) throws Exception {
        convertDocxToHtml("D:\\中烟实业\\测试.docx","D:\\中烟实业\\测试.html");
        convertDocxToHtml("D:\\中烟实业\\湖北中烟数据分析系统建设及应用一期项目 - 详细设计说明书.docx","D:\\中烟实业\\湖北中烟数据分析系统建设及应用一期项目 - 详细设计说明书.html");
//        InputStream inputStream = new FileInputStream(new File("D:\\中烟实业\\湖北中烟数据分析系统建设及应用一期项目 - 详细设计说明书.docx"));
//        OutputStream outputStream = new FileOutputStream(new File("D:\\中烟实业\\湖北中烟数据分析系统建设及应用一期项目 - 详细设计说明书.pdf"));
//        Converter converter = new DocxToPDFConverter(inputStream,outputStream,true,true);
//        converter.convert();
    }
}
