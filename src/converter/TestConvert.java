package converter;

import java.io.*;

public class TestConvert {

    public static void main(String[] args) throws Exception {
        InputStream inputStream = new FileInputStream(new File("D:\\中烟实业\\A17016ZK-湖北中烟数据集成平台升级及集成实施项目详细设计--初稿.docx"));
        OutputStream outputStream = new FileOutputStream(new File("D:\\中烟实业\\A17016ZK-湖北中烟数据集成平台升级及集成实施项目详细设计--初稿.pdf"));
        Converter converter = new DocxToPDFConverter(inputStream,outputStream,true,true);
        converter.convert();
    }



}
