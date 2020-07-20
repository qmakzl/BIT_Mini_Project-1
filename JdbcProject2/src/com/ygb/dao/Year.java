package com.ygb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.ygb.model.Employee;

public class Year implements Case {
	private HrDao dao = HrDao.getInstance();
	private Connection conn = null;
	private PreparedStatement pStmt = null;
	private ResultSet rSet = null;
	
	@Override
	public void getData(Scanner scanner) {
		// TODO Auto-generated method stub
		dao.getConnection();
	
		conn = dao.getConn();
		pStmt =dao.getpStmt();
		rSet = dao.getrSet();
		
		List<Employee> employees = new ArrayList<Employee>();
		System.out.println("입사년도를 입력하세요 : ");
		int year = scanner.nextInt();
		StringBuffer sa = new StringBuffer();
		sa.append("SELECT E.EMPLOYEE_ID, E.FIRST_NAME, E.LAST_NAME, NVL((D.DEPARTMENT_NAME),'<Not Assigend>') ");
		sa.append("FROM EMPLOYEES E LEFT JOIN DEPARTMENTS D ");
		sa.append("ON E.DEPARTMENT_ID = D.DEPARTMENT_ID ");
		sa.append("WHERE TO_CHAR(E.HIRE_DATE,'YYYY') = ? "); // 2007년도에 입사한 사원을 찾기 때문에  where에 넣습니다.
		sa.append("ORDER BY E.EMPLOYEE_ID");
		
		String sql = sa.toString();
		try {
			pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, year); //하나인 이유는 년도에서만 찾아서요
			rSet = pStmt.executeQuery();
			Employee employee;
			while (rSet.next()) {
				employee = new Employee();
				employee.setEmployeeID(rSet.getLong(1));
				employee.setFirstName(rSet.getString(2));
				employee.setLastName(rSet.getString(3));
				employee.setDepartmentName(rSet.getString(4));
				employees.add(employee);
			}
			print(employees);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.closeConnection();
		}
		
	}
	private void print(List<Employee> employees) {
		System.out.println("사원번호             이름                               성                                    부서");
		System.out.println("==============================================================");
		
		if (employees.size() == 0) {
			System.out.println("조건에 맞는 데이터가 없습니다.");
		}
		
		for(Employee employee : employees) {
			System.out.print(employee.getEmployeeID());
			System.out.printf("%15s", employee.getFirstName());
			System.out.printf("%20s", employee.getLastName());
			System.out.printf("%20s", employee.getDepartmentName());
			System.out.println();
		}
	}
}