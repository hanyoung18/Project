package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {

	public static void createItem(TodoList list) {
		// timetable가 시간표.
		String title, due_date, category, assignment, overdue_task, check;
		Scanner sc = new Scanner(System.in);
		int i = 0;
		System.out.print("평일 또는 휴일 > ");
		category = sc.nextLine().trim();
		System.out.print("오늘 일 (?주차 ?요일)> ");
		title = sc.nextLine().trim();
		if (list.isDuplicate(title)) {
			System.out.println("제목은 중복 될 수 없습니다.");
			return;
		}
		System.out.print("과제 > ");
		assignment = sc.nextLine().trim();
		System.out.print("마감일 > ");
		due_date = sc.nextLine().trim();
		System.out.print("밀린 과제를 해결? (y/n) > ");
		check = sc.nextLine().trim();
		if (check.equals("y")) {
			overdue_task = "없습니다";
		} else {
			overdue_task = find_overdue_task(list);
		}

		System.out.print("어제까지 과제 제출? (y/n) > ");
		check = sc.nextLine().trim();
		if (check.equals("n")) {
			System.out.print("끝내지 못한 과제 입력 > ");
			if (overdue_task.equals("없습니다")) {
				overdue_task = sc.nextLine().trim() + ", ";
			} else {
				overdue_task += sc.nextLine().trim() + ", ";
			}

		}
		TodoItem t = new TodoItem(category, title, assignment, due_date, overdue_task);
		if (list.addItem(t) > 0)
			System.out.println("추가되었습니다. ");
	}

	private static String find_overdue_task(TodoList list) {
		String overdue_task = list.getoverdue_task();
		return overdue_task;
	}

	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		String check;
		System.out.print("삭제할 항목의 번호 > ");
		String title_num = sc.nextLine();
		System.out.print(title_num + " 위 항목을 삭제하시겠습니까? (y/n) > ");
		check = sc.next();
		if (check.equals("y")) {
			if (l.deleteItem(title_num) > 0) {
				System.out.println("제거 되었습니다.");
			}
		} else {
			System.out.println("취소되었습니다.");
		}
	}

	public static void updateItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		String check, overdue_task;
		System.out.print("삭제할 항목의 번호> ");
		int title_num = sc.nextInt();
		if (title_num > l.getCount() ) {
			System.out.println("해당 항목이 없습니다.");
			return;
		}
		sc.nextLine();
		System.out.print("새로운 요일 > ");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목은 중복 될 수 없습니다.");
			return;
		}
		System.out.print("평일 또는 휴일 > ");
		String new_category = sc.nextLine().trim();

		System.out.print("새로운 과제 > ");
		String new_Assignment = sc.nextLine().trim();
		System.out.print("새로운 마감일 > ");
		String new_due_date = sc.nextLine().trim();
		
		System.out.print("밀린 과제를 해결? (y/n) > ");
		check = sc.nextLine().trim();
		if (check.equals("y")) {
			overdue_task = "없습니다";
		} else {
			overdue_task = find_overdue_task(l);
		}

		System.out.print("어제까지 과제 제출? (y/n) > ");
		check = sc.nextLine().trim();
		if (check.equals("n")) {
			System.out.print("끝내지 못한 과제 입력 > ");
			if (overdue_task.equals("없습니다")) {
				overdue_task = sc.nextLine().trim() + ", ";
			} else {
				overdue_task += sc.nextLine().trim() + ", ";
			}

		}
		
		TodoItem t = new TodoItem(new_title, new_category, new_Assignment, new_due_date, title_num,overdue_task);
		if (l.editItem(t) > 0)
			System.out.println("수정되었습니다.");
		else {
			System.out.println("수정되지 않았습니다. ");
		}
	}

	public static void listAll(TodoList l) {
		l.listAll();
	}

	public static void listAll(TodoList l, String string) {
		// TODO Auto-generated method stub
		l.listAll(string);
	}

	public static void find(TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}

	public static void listCateAll(TodoList l) {
		int count = 0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
	}

	public static void findCateList(TodoList l, String cat) {
		// TODO Auto-generated method stub
		int count = 0;
		for (TodoItem item : l.getCategory(cat)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
	}

	public static void listAll(TodoList l, int i) {
		// TODO Auto-generated method stub
		l.listAll(i);
	}

	public static void completeItem(TodoList l, String complete_multi_num) {
		if (l.completeItem(complete_multi_num) > 0) {
			System.out.println("완료 체크하였습니다.");
		} else {
			System.out.println("오류입니다");
		}
	}

	public static void find_duedate(TodoList l, String word) {
		int count = 0;
		for (TodoItem item : l.getDueAssignments(word)) {
			System.out.print(item.getAssignment() + ",");
			count++;
		}
		System.out.printf("\n총 %d개의 과제가 등록되어 있습니다.\n", count);

	}

	public static void update_assignment(TodoList list) {
		// timetable가 시간표.
		String title, timetable, due_date, category, assignment, overdue_task, check;
		Scanner sc = new Scanner(System.in);
		int i = 0;
		System.out.print("평일 또는 휴일 > ");
		category = sc.nextLine().trim();
		System.out.print("오늘 일 (?주차 ?요일)> ");
		title = sc.nextLine().trim();
		if (list.isDuplicate(title)) {
			System.out.println("제목은 중복 될 수 없습니다.");
			return;
		}
		System.out.print("과제 > ");
		assignment = sc.nextLine().trim();
		System.out.print("마감일 > ");
		due_date = sc.nextLine().trim();
		System.out.print("밀린 과제를 해결? (y/n) > ");
		check = sc.nextLine().trim();
		if (check.equals("y")) {
			overdue_task = "없습니다";
		} else {
			overdue_task = find_overdue_task(list);
		}

		System.out.print("어제까지 과제 제출? (y/n) > ");
		check = sc.nextLine().trim();
		if (check.equals("n")) {
			System.out.print("끝내지 못한 과제 입력 > ");
			if (overdue_task.equals("없습니다")) {
				overdue_task = sc.nextLine().trim() + ", ";
			} else {
				overdue_task += sc.nextLine().trim() + ", ";
			}

		}
		TodoItem t = new TodoItem(category, title, assignment, due_date, overdue_task);
		if (list.addItem(t) > 0)
			System.out.println("추가되었습니다. ");
	}
}
