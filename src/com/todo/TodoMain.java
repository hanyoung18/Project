package com.todo;

import java.io.IOException;
import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {

	public static void start() {
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		//l.importData("todoList.txt");
		//l.setData();
		boolean quit = false;
		Menu.displaymenu();
		do {
			System.out.print("\n명령을 입력하세요 > ");
			String choice = sc.next();
			if(prompt(choice)) {
				Menu.displaymenu();
				continue;
			}
			switch (choice) {
			case "add":
				System.out.println("[항목 추가]");
				TodoUtil.createItem(l);
				break;
			case "del":
				System.out.println("[항목 삭제]");
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				System.out.println("[항목 편집]");
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				System.out.println("전체 제목기준 오름차순 정렬");
				TodoUtil.listAll(l,"name_asc");
				break;

			case "ls_name_desc":
				System.out.println("전체 제목기준 내림차순 정렬");
				TodoUtil.listAll(l,"name_desc");
				break;
				
			case "ls_date":
				System.out.println("날짜순으로 정렬");
				TodoUtil.listAll(l,"date_asc");
				break;
			case "find":
				String  keyword = sc.nextLine().trim();
				TodoUtil.find(l,keyword);
				break;				
			case "find_cate":
				String cat = sc.nextLine().trim();
				TodoUtil.findCateList(l,cat);
				break;
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
			case "must_do":
				String word = sc.nextLine().trim();
				TodoUtil.find_duedate(l, word);
				break;
			case "ls_date_desc":
				System.out.println("전체 날짜기준 내림차순 정렬");
				TodoUtil.listAll(l,"date_desc");
				break;
			case "comp":
				String  complete_multi_num = sc.nextLine();
				TodoUtil.completeItem(l,complete_multi_num);
				break;
			case "ls_comp":
				TodoUtil.listAll(l,1);
				break;
			case "update_assignment":
				TodoUtil.update_assignment(l);
				break;
			case "exit":
				quit = true;
				break;
			default:
				System.out.println("잘못 입력하셨습니다. 다시 입력하세요.");
				break;
			}
		} while (!quit);
	}

	private static boolean prompt(String ch) {
		String check = "help";
		return ch.equals(check);
	}
}