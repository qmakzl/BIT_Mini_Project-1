package com.ygb.main;

import java.util.Scanner;

import com.ygb.dao.Case;
import com.ygb.dao.EmpID;
import com.ygb.dao.Name;
import com.ygb.dao.Year;

public class Run {
	
	Scanner scanner = null;
	Case c = null;
	boolean check = true;
	
	private static Run run = new Run(); // main() 함수가 실행되기전 실행되는 static 실행문
	
	private Run() {
		
	}
	
	public static Run getInstance() {
		return run;
	}
//=============================================================================
	public void run() {
		scanner = new Scanner(System.in);
		while(check) {
			init();
			
			String choiceMenu = scanner.next();			
			switch(choiceMenu) {
			
				case "a": c = new Name(); 			break;
				case "b": c = new Year(); 			break;
				case "c": c = new EmpID(); 			break;
				case "q": exit();					break;
				default : System.out.println("조건에 맞는 데이터가 없습니다."); continue;
			}
			c.getData(scanner);
		}
	}
	
	private void init() {
		System.out.println();
		System.out.println("메뉴 선택!");
		System.out.println("================");		
		System.out.println("a. 사원정보 검색(이름) : ");
		System.out.println("b. 사원정보 검색(입사년) : ");
		System.out.println("c. 과거 업무이력 검색(사원ID): ");
		System.out.println("q. 종료");
	}
	
	private void exit() {
		System.out.println("프로그램 종료");
		if(check == true)
			check = false;
		scanner.close();
	}
}