package com.ygb.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ygb.dao.HrDao;
import com.ygb.model.Employee;

public class HrMain { 
	public static void main(String[] args) {
		boolean run = true;
	    Scanner sc = new Scanner(System.in);
		
		while(run) {
			main_info();
			String name = sc.next();
			
			switch(name) {
				case "a" :
					getEmployeesByName();
					break;				
				case "b" :
					getEmployeesByYear();
					break;
				case "c" :
					prepareCall();
					break;
				case "q" :
					System.out.println("종료!");
					run = false;
					break;					
				default : System.out.println("잘못 선택하셨습니다. 다시 선택하세요!!");
				} 
			} 
		sc.close();
		}
	
	private static void main_info() {
		System.out.println("메뉴 선택!");
		System.out.println("=========================");
		System.out.println("a. 사원정보 검색(이름) : ");
		System.out.println("b. 사원정보 검색(입사년) : ");
		System.out.println("c. 과거 업무이력 검색(사원ID): ");
		System.out.println("q. 종료");
	}

	private static void getEmployeesByName() {
		Scanner sc = new Scanner(System.in);
		HrDao dao = HrDao.getInstance();
		List<Employee> employees = new ArrayList<Employee>();
		
		System.out.println("        이름                      성                  이메일                        전화                               입사일 ");
		System.out.println("=============================================================================");
		System.out.print("사원의 First name 또는 Last name을 입력하세요  : ");
		String inputS = sc.next();
		
		employees = dao.getEmployeesByName(inputS);
		System.out.println("        이름                      성                  이메일                        전화                               입사일 ");
		System.out.println("=============================================================================");
		if (employees.size() == 0) System.out.println("조회 결과가 없습니다.\n");
		
		for(Employee employee : employees) {
			System.out.printf("%10s", employee.getFirstName());
			System.out.printf("%10s", employee.getLastName());
			System.out.printf("%15s", employee.getEmail());
			System.out.printf("%20s", employee.getPhoneNumber());
			System.out.printf("%20s", employee.getHireDate());
			System.out.println();
		}
		//sc.close();		
	}
	private static void getEmployeesByYear() {
		Scanner sc = new Scanner(System.in);
		HrDao dao = HrDao.getInstance();
		List<Employee> employees = new ArrayList<Employee>();
	
			System.out.print("입사년도를 입력하세요 : ");
			int inputY = sc.nextInt();
			employees = dao.getEmployeesByYear(inputY);
			
			System.out.println("사원번호             이름                               성                                    부서");
			System.out.println("==============================================================");
			if (employees.size() == 0) System.out.println("조회 결과가 없습니다.\n");
			
			for(Employee employee : employees) {
				System.out.print(employee.getEmployeeID());
				System.out.printf("%15s", employee.getFirstName());
				System.out.printf("%20s", employee.getLastName());
				System.out.printf("%20s", employee.getDepartmentName());
				System.out.println();
			}
			//sc.close();
		}
	private static void prepareCall() {
		Scanner sc = new Scanner(System.in);
		HrDao dao = HrDao.getInstance();    
		List<Employee> employees = new ArrayList<Employee>();
	
		System.out.println("사원번호를 입력하세요 : ");
		int inputH = sc.nextInt();
		employees = dao.prepareCall(inputH);
	
		System.out.println("이름                                                        담당업무                                                    시작일                                      종료일");
		System.out.println("===============================================================================================================================");
		if (employees.size() == 0) System.out.println("조회 결과가 없습니다.\n");

		for (Employee employee : employees) {
		 	System.out.print(employee.getFirstName());
         	System.out.printf("%30s", employee.getJobTitle());
         	System.out.printf("%30s", employee.getHireDate());
         	System.out.printf("%30s", employee.getEndDate());
         	System.out.println();
    	}
		//sc.close();
	}
}