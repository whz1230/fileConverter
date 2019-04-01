package converter;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Demo1 {

    public static void main(String[] args) {
        double totalshendingjine = 3308417.5;
        String money = String.valueOf(new BigDecimal(totalshendingjine));
        if (money.length() > 0 && money.indexOf(".") != -1) {
            money = money.substring(0, money.indexOf(".") + 3);
        }
        System.out.println(money);
    }

    public static void test() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String[] expireDate = new String[2];
        try {
            conn = getDB2Connection();
            String sql = "SELECT\n" +
                    "    ID,\n" +
                    "    CONTRACTNAME,\n" +
                    "    PROJECTNAME,\n" +
                    "    PROJECTID,\n" +
                    "    TWOWORKNAME,\n" +
                    "    TWOWORKID,\n" +
                    "    CREATEORID,\n" +
                    "    CREATEORNAME,\n" +
                    "    CONTENT,\n" +
                    "    TELEPHOTE,\n" +
                    "    CREATETIME,\n" +
                    "    DEPARTMENTID,\n" +
                    "    DEPARTMENTNAME,\n" +
                    "    YEARBUDGET,\n" +
                    "    BUDGETREMARK,\n" +
                    "    PROCUREMENTMODE,\n" +
                    "    CONTRACTVALUE_BIG,\n" +
                    "    CONTRACTVALUE_LOW,\n" +
                    "    AUDITEDAMOUNT_BIG,\n" +
                    "    AUDITEDAMOUNT_LOW,\n" +
                    "    SUPPLIERNAME,\n" +
                    "    SUPPLIERPHONE,\n" +
                    "    SUPPLIERADDRESS,\n" +
                    "    SUPPLIERPERSON,\n" +
                    "    SUPPLIERTYPE,\n" +
                    "    CONTRACTNUMBER,\n" +
                    "    CONTRACTTYPE,\n" +
                    "    OPINIONTYPE,\n" +
                    "    JIANYISHU,\n" +
                    "    YIJIANSHU,\n" +
                    "    BUCHONGZILIAO,\n" +
                    "    HUIFUJIANYISHU,\n" +
                    "    HUIFUYIJIANSHU,\n" +
                    "    HUIFUBUCHONG,\n" +
                    "    ISJUNGONG,\n" +
                    "    TYPE,\n" +
                    "    COMPANYTYPE,\n" +
                    "    SSHENHE,\n" +
                    "    SFUSHEN,\n" +
                    "    ESSDATE,\n" +
                    "    ISOVER,\n" +
                    "    OVERDATE,\n" +
                    "    SUPPLIERCODE,\n" +
                    "    SIGNATUREID,\n" +
                    "    DAY,\n" +
                    "    COMPANYCODE,\n" +
                    "    SETTLEACCOUNT,\n" +
                    "    SITEPERSON,\n" +
                    "    MOBILE,\n" +
                    "    SBACKMARK,\n" +
                    "    CBACKMARK,\n" +
                    "    FBACKMARK,\n" +
                    "    JUNGONGID,\n" +
                    "    HTTYPE,\n" +
                    "    SUBDEPARTMENTID,\n" +
                    "    SUBDEPARTMENTNAME,\n" +
                    "    BUDGET_AMOUNT_LOWER,\n" +
                    "    BUDGET_AMOUNT_UPPER,\n" +
                    "    UP_ZIP_FILE,\n" +
                    "    MAIN_CONTRACT_ID,\n" +
                    "    SIDE_CONTRACT,\n" +
                    "    STARTDATE,\n" +
                    "    ENDDATE,\n" +
                    "    LEADER_NAME,\n" +
                    "    SHENJI_ID,\n" +
                    "    SHENJI_NAME,\n" +
                    "    AGENCY_ORGAN,\n" +
                    "    LAWER_OFFICE,\n" +
                    "    YEARBUDGET_UPPER,\n" +
                    "    FAGUI_ID,\n" +
                    "    MAIN_CONTRACT_NAME,\n" +
                    "    SETTLEAC_COUNT_UPPER\n" +
                    "FROM\n" +
                    "    SUREKAM.KJY_CONTRACT where (isover='01' or isover='02') and companycode='dxy' order by contractnumber desc";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            String temp = "";
            String money = "";
            double totalshendingjine = 0;
            while (rs.next()) {
                temp = rs.getString("AUDITEDAMOUNT_LOW");
                if (temp != null && temp.length() > 0) {
                    totalshendingjine += Double.parseDouble(temp.replaceAll(",", ""));
                }
            }
            money = String.valueOf(new BigDecimal(totalshendingjine));
            if (money.length() > 0 && money.indexOf(".") != -1) {
                money = money.substring(0, money.indexOf(".") + 3);
            }
            System.out.println(money);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getDB2Connection() {
        Connection conn = null;
        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
            if (conn == null) {
                conn = DriverManager.getConnection("jdbc:db2://10.156.6.6:50000/surekam", "db2inst1", "db2inst1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}
