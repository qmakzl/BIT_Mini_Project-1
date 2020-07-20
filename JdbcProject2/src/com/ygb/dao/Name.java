package com.ygb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.ygb.model.Employee;
import com.ygb.dao.HrDao;

public class Name implements Case {
	private HrDao dao = HrDao.getInstance();
	private Connection conn = null;
	private PreparedStatement pStmt = null;
	private ResultSet rSet = null;
	@Override
	public void getData(Scanner scanner) {
		dao.getConnection();
		conn = dao.getConn();
		pStmt = dao.getpStmt();
		rSet = dao.getrSet();
		List<Employee> employees = new ArrayList<Employee>();
		System.out.println("사원의 First name 또는 Last name을 입력하세요: ");
		String name = scanner.next();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE ");
		sb.append("FROM EMPLOYEES ");
		sb.append("WHERE FIRST_NAME LIKE ? OR LAST_NAME LIKE ?");
		String sql = sb.toString();
		try {
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, "%" + name + "%");// 이름 앞이나 중간 뒤나 어디에나 있는 거 해당되는 것을 찾으려공!!!
			pStmt.setString(2, "%" + name + "%");// 두개가 있는 이유는 FIRST NAME 과 LAST NAME에서 찾기 때문에
			rSet = pStmt.executeQuery();
			Employee employee;
			while (rSet.next()) {
				employee = new Employee();
				employee.setFirstName(rSet.getString(1));
				employee.setLastName(rSet.getString(2));
				employee.setEmail(rSet.getString(3));
				employee.setPhoneNumber(rSet.getString(4));
				employee.setHireDate(rSet.getDate(5).toString());
				employees.add(employee);
			}
			
			print(employees);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.closeConnection(); // 메소드 완성
		}
	}
	private void print(List<Employee> employees) {
		System.out.println("        이름                      성                  이메일                        전화                               입사일 ");
		System.out.println("=============================================================================");
		
	if (employees.size() == 0) {
		System.out.println("조건에 맞는 데이터가 없습니다.");
	}
		
	for (Employee employee : employees) { // 첨자(i)가 필요없으니까 새로운 for문을 사용권장!
			// for(int i=0; i<array.size(); i++)
			// 파이썬에서 for i in range()
			System.out.printf("%10s", employee.getFirstName());
			System.out.printf("%10s", employee.getLastName());
			System.out.printf("%15s", employee.getEmail());
			System.out.printf("%20s", employee.getPhoneNumber());
			System.out.printf("%20s", employee.getHireDate());
			System.out.println();
		}
	}
}
