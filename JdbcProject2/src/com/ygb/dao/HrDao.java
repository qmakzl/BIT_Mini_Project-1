package com.ygb.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.ygb.model.Employee;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class HrDao {
	private static HrDao dao = new HrDao();
	private Connection conn;
	private ResultSet rSet;
	private PreparedStatement pStmt;
	private CallableStatement cstmt = null;
	private OracleCallableStatement ocstmt = null;
	private final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String username = "c##hr";
	private final String password = "1234";
	private HrDao() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static HrDao getInstance() {
		return dao;
	}
//=============================================================================================
	
//=============================================================================================	
	public void getConnection() { // 왜 private로 만들었는가. 내부에서 사용할거니까
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	public ResultSet getrSet() {
		return rSet;
	}
	public void setrSet(ResultSet rSet) {
		this.rSet = rSet;
	}
	public PreparedStatement getpStmt() {
		return pStmt;
	}
	public void setpStmt(PreparedStatement pStmt) {
		this.pStmt = pStmt;
	}
	public CallableStatement getCstmt() {
		return cstmt;
	}
	public void setCstmt(CallableStatement cstmt) {
		this.cstmt = cstmt;
	}
	public OracleCallableStatement getOcstmt() {
		return ocstmt;
	}
	public void setOcstmt(OracleCallableStatement ocstmt) {
		this.ocstmt = ocstmt;
	}
	public void closeConnection() {
		try {
			if (rSet != null)
				rSet.close();
			if (pStmt != null)
				pStmt.close();
			if (conn != null)
				conn.close();
			if (cstmt != null)
				cstmt.close();
			if (ocstmt != null)
				ocstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Employee> getEmployeesById(int id) {
		List<Employee> employees = new ArrayList<Employee>();
		getConnection();
		try {
			// Stored Procedure 를 호출하기 위해 JDBC Callable Statement를 사용 합니다
			cstmt = conn.prepareCall("BEGIN cursor_pkg.sp_job_history(?,?); END;");
			// 프로시져의 In Parameter로 SELECT문장을 넘깁니다.
			cstmt.setInt(1, id);
			// CallableStatement를 위한 REF CURSOR OUTPUT PARAMETER를
			// OracleTypes.CURSOR로 등록합니다.
			cstmt.registerOutParameter(2, OracleTypes.CURSOR);
			// CallableStatement를 실행합니다.
			cstmt.execute();
			// getCursor() method를 사용하기 위해 CallableStatement를
			// OracleCallableStatement object로 바꿉니다.
			ocstmt = (OracleCallableStatement) cstmt;
			
			// OracleCallableStatement 의 getCursor() method를 사용해서 REF CURSOR를
			// JDBC ResultSet variable 에 저장합니다.
			rSet = ocstmt.getCursor(2);
			Employee employee;
			while (rSet.next()) {
				employee = new Employee();
				employee.setFirstName(rSet.getString(1));
				employee.setJobTitle(rSet.getString(2));
				employee.setHireDate(rSet.getString(3));
				employee.setEndDate(rSet.getString(4));
				employees.add(employee);
			}
		} catch (Exception e) {
		} finally {
			closeConnection();
		}
		return employees;
	}
}