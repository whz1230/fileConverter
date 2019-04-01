package converter;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;

@WebServlet("/servlet/viewFileServlet")
public class ViewFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String report_content_id = req.getParameter("report_content_id");
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream in = null;
        String fileType = "";
        try {
            ps = conn.prepareStatement("select ID,FILE_NAME,FILE_TYPE,FILE_DATA from DCDW.FG_FILE_DATA where ID = ?");
            ps.setString(1, report_content_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                // 得到Blob对象
                fileType = rs.getString(3);
                Blob b = rs.getBlob(4);
                in = b.getBinaryStream();

                String path = this.getServletContext().getRealPath("/temp");
                File file = new File(path + File.separator + report_content_id + ".pdf");

                Converter converter = null;
                OutputStream out = new FileOutputStream(file);
                if ("doc".equals(fileType)){
                    converter = new DocToPDFConverter(in,out,false,true);
                } else if ("docx".equals(fileType)){
                    converter = new DocxToPDFConverter(in,out,false,true);
                }
                converter.convert();

                in.close();
            }
            req.getRequestDispatcher("/temp/main.html?report_content_id=" + report_content_id).forward(req, resp);

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
            if (conn == null) {
                //conn = DriverManager.getConnection("jdbc:db2://10.156.1.77:50000/hbzydw", "dwusr", "dwusr");
                conn = DriverManager.getConnection("jdbc:db2://10.1.90.155:50000/sydc", "dwusr", "dwusr");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void main(String[] args) throws Exception {
        InputStream inputStream = new FileInputStream(new File("D:\\中烟实业\\湖北中烟数据分析系统建设及应用一期项目 - 详细设计说明书.docx"));
        OutputStream outputStream = new FileOutputStream(new File("D:\\中烟实业\\湖北中烟数据分析系统建设及应用一期项目 - 详细设计说明书.pdf"));
        Converter converter = new DocxToPDFConverter(inputStream,outputStream,true,true);
        converter.convert();
    }
}
