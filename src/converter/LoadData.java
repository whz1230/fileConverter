package converter;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * 从数据中心同步人员、组织信息
 */
public class LoadData {

    public static void main(String[] args) {
        //sysDeptData();
        sysEmpDeptData();
    }

    public static void sysEmpData() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String[] expireDate = new String[2];
        try {
            conn = getDB2Connection();
            String sql = "select AID as id, PNAME as emp_name,EMPCODE as emp_code,SCODE as sex,NATIONCODE as nation_code,BORNDATE as born_date,IDCARD as id_card,MARRYCODE as marry_code," +
                    "EMTYPECODE as resign_code,STRU_ID as stru_id,EMAIL as email,RTXCODE as rtx_code,TEL as tel,OTEL as o_tel,ITEL as i_tel,FTEL as f_tel,MOBILE as mobile,TECA as teca,TECASC as teca_sc," +
                    "ADDRESS as address,INUSE as in_use,MOBILESTATE as mobile_state,USERSTATE as user_state,CREATE_TIME as create_date,SORT_CODE as sort_code from DCINST.T_EMPLOYEE";
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();
            String insertSql = "INSERT INTO t_sys_emp(id,emp_name,emp_code,sex,nation_code,born_date,id_card,marry_code,resign_code,stru_id," +
                    "email,rtx_code,tel,o_tel,i_tel,f_tel,mobile,teca,teca_sc,address,in_use,mobile_state,user_state,create_date,update_date,is_delete,sort_code) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            conn = getMySQLConnection();
            ps = conn.prepareStatement(insertSql);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while (rs.next()) {
                ps.setString(1, UUIDGenerator.getUUID());
                ps.setString(2, rs.getString("emp_name"));
                ps.setString(3, rs.getString("emp_code"));
                ps.setInt(4, rs.getInt("sex"));
                ps.setString(5, rs.getString("nation_code"));
                ps.setDate(6, rs.getDate("born_date"));
                ps.setString(7, rs.getString("id_card"));
                ps.setInt(8, rs.getInt("marry_code"));
                ps.setString(9, rs.getString("resign_code"));
                ps.setString(10, rs.getString("stru_id"));
                ps.setString(11, rs.getString("email"));
                ps.setString(12, rs.getString("rtx_code"));
                ps.setString(13, rs.getString("tel"));
                ps.setString(14, rs.getString("o_tel"));
                ps.setString(15, rs.getString("i_tel"));
                ps.setString(16, rs.getString("f_tel"));
                ps.setString(17, rs.getString("mobile"));
                ps.setString(18, rs.getString("teca"));
                ps.setString(19, rs.getString("teca_sc"));
                ps.setString(20, rs.getString("address"));
                ps.setInt(21, rs.getInt("in_use"));
                ps.setString(22, rs.getString("mobile_state"));
                ps.setString(23, rs.getString("user_state"));
                ps.setTimestamp(24, new Timestamp(System.currentTimeMillis()));
                ps.setTimestamp(25, new Timestamp(System.currentTimeMillis()));
                ps.setInt(26, 0);
                ps.setInt(27, rs.getInt("sort_code"));

                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sysEmpDeptData() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String[] expireDate = new String[2];
        try {
            conn = getDB2Connection();
            String sql = "select SID as sid, EMPCODE as emp_code, STRU_ID as dept_id,ISDEFAULT as is_default from DCINST.T_EM_DEPTMAP where sid >20000";
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            String insertSql = "INSERT INTO t_sys_emp_dept(id,emp_code,dept_id,is_default) VALUES(?,?,?,?)";
            conn = getMySQLConnection();
            ps = conn.prepareStatement(insertSql);
            while (rs.next()) {
                ps.setString(1, UUIDGenerator.getUUID());
                ps.setString(2, rs.getString("emp_code"));
                ps.setString(3, rs.getString("dept_id"));
                ps.setString(4, rs.getString("is_default"));

                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sysDeptData() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String[] expireDate = new String[2];
        try {
            conn = getDB2Connection();
            String sql = "select STRU_ID as stru_id, ORGAN_ALIAS as organ_alias, ORGAN_ID as organ_id,PARENT_ID as parent_id,DEPTTYPECODE as dept_type_code,STRU_LEVEL as stru_level,STRU_ORDER as stru_order,STRU_PATH as stru_path,IS_LEAF as is_leaf," +
                    "ORGAN_TYPE as organ_type,IN_USE as in_use from DCINST.T_DEPT";
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            String insertSql = "INSERT INTO t_sys_dept(id,stru_id,organ_alias,organ_id,parent_id,dept_type_code,stru_level,stru_order,stru_path,is_leaf,organ_type,in_use,is_delete,create_date,update_date) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            conn = getMySQLConnection();
            ps = conn.prepareStatement(insertSql);
            while (rs.next()) {
                ps.setString(1, UUIDGenerator.getUUID());
                ps.setString(2, rs.getString("stru_id"));
                ps.setString(3, rs.getString("organ_alias"));
                ps.setString(4, rs.getString("organ_id"));
                ps.setString(5, rs.getString("parent_id"));
                ps.setInt(6, rs.getInt("dept_type_code"));
                ps.setInt(7, rs.getInt("stru_level"));
                ps.setString(8, rs.getString("stru_order"));
                ps.setString(9, rs.getString("stru_path"));
                ps.setInt(10, rs.getInt("is_leaf"));
                ps.setInt(11, rs.getInt("organ_type"));
                ps.setInt(12, rs.getInt("in_use"));
                ps.setInt(13, 0);
                ps.setTimestamp(14, new Timestamp(System.currentTimeMillis()));
                ps.setTimestamp(15, new Timestamp(System.currentTimeMillis()));


                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Connection getDB2Connection() {
        Connection conn = null;
        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
            if (conn == null) {
                conn = DriverManager.getConnection("jdbc:db2://10.156.0.161:50000/datacent", "dcusr", "dcusr");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static Connection getMySQLConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if (conn == null) {
                conn = DriverManager.getConnection("jdbc:mysql://10.156.5.49:3306/hbt_standardfile?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true", "root", "dingyi");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}
