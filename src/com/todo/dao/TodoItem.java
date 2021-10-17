package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class TodoItem {
	private String title;
	private String timetable;
	private Date current_date1;
	private String current_date;
	private String category;
	private String due_date;
	private int is_completed;
	private int id;
	private String assignment;
	private String overdue_task;
	public SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");

	public TodoItem(String title,int is_completed,String timetable, String category, String due_date, String current_date,String assignment) {
		this.title = title;
		this.timetable = timetable;
		this.current_date = current_date;
		this.due_date = due_date;
		this.category = category;
		this.is_completed=is_completed;
		this.assignment=assignment;
	}//완료체크
	public TodoItem(String title,int is_completed,String timetable, String category, String due_date, String current_date,String assignment,String overdue_task) {
		this.title = title;
		this.timetable = timetable;
		this.current_date = current_date;
		this.due_date = due_date;
		this.category = category;
		this.is_completed=is_completed;
		this.overdue_task=overdue_task;
		this.assignment=assignment;
	}

	public TodoItem(String category2, String title2, String assignment2, String due_date2, String overdue_task) {
		this.category=category2;
		this.title=title2;
		this.current_date1 = new Date();
		current_date = SimpleDateFormat.format(current_date1);
		this.assignment=assignment2;
		this.due_date=due_date2;
		this.overdue_task=overdue_task;
	}//add 날
	public TodoItem(String title, String category, String assignment, String due_date, int title_num,String overdue_task) {
		this.current_date1 = new Date();
		this.title = title;
		this.assignment = assignment;
		this.due_date = due_date;
		this.category = category;
		current_date = SimpleDateFormat.format(current_date1);
		this.id = title_num;
		this.overdue_task=overdue_task;
	}//edit

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String gettimetable() {
		return timetable;
	}

	public void settimetable(String timetable) {
		this.timetable = timetable;
	}

	public String getCurrent_date() {
		return current_date;
	}

	@Override
	public String toString() {
		if(getIs_completed()==0) {
			return "[" +getCategory() + "] " +  getTitle() + " - " +"["+ "시간표"+"] "+ gettimetable() + " - "+"[과제] "+getAssignment() +" - "+ "[마감일] "+ getDue_date() + " - "
					+"밀린과제: "+getOverdue_task()+ " - "+getCurrent_date();
		}
		else {
			return "[" +getCategory() + "] " +  getTitle() + " - " +"["+ "시간표"+"] "+ gettimetable() + " - "+"[과제] "+getAssignment()+"[V]" +" - "+ "[마감일] "+ getDue_date() + " - "
					+"밀린과제: "+getOverdue_task()+ " - "+getCurrent_date();
		}
	}

	public void setCurrent_date(String current_date) {
		this.current_date = current_date;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}

	public String getAssignment() {
		return assignment;
	}

	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	public String getOverdue_task() {
		return overdue_task;
	}

	public void setOverdue_task(String overdue_task) {
		this.overdue_task = overdue_task;
	}
}
