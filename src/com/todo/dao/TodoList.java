package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import com.todo.service.DbConnect;

public class TodoList {
	Connection conn;

	public TodoList() {
		this.conn = DbConnect.getConnetion();
	}

	public void importData(String filename) {

		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list(title, timetable, category, current_date, due_date)" + "values(?,?,?,?,?);";
			int records = 0;
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String title = st.nextToken();
				String category = st.nextToken();
				String timetable = st.nextToken();
				String due_date = st.nextToken();
				String currnet_date = st.nextToken();

				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, timetable);
				pstmt.setString(3, category);
				pstmt.setString(4, currnet_date);
				pstmt.setString(5, due_date);

				int count = pstmt.executeUpdate();

				if (count > 0)
					records++;
				pstmt.close();
			}
			System.out.println(records + " records read!!");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String settingtitle(String title,String category) {
		String timetable="";
		if(!category.equals("휴일")) {
			if(title.contains("월요일")) {
				timetable="컴퓨터 구조, 컴퓨터 비전, 공학설계입문";
			}
			else if(title.contains("화요일")||title.equals("금요일")) {
				timetable="컴퓨터 구조, 컴퓨터 비전";
			}
			else if(title.contains("수요일")) {
				timetable="채플, 팀모임";
			}
			else if(title.contains("목요일")) {
				timetable="컴퓨터 구조, 컴퓨터 비전";
			}
		}
			return timetable;
	}
	public void setData() {
		String line;
		String sql = "insert into list(title, timetable, category, current_date, due_date, assignment)" + "values(?,?,?,?,?,?);";
		int records = 0;
		String category = "";
		String timetable = "";
		String assignment="";
		String[] weeks = { "월", "화", "수", "목", "금", "토", "일" };
		SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		Date current_date1;
		String current_date;
		String due_date = "";
		current_date1 = new Date();
		current_date = SimpleDateFormat.format(current_date1);
		try {
			for (int i = 1; i <= 7; i++) {
				for (int j = 0; j < 7; j++) {
					line = (i) + "주차 "+weeks[j] + "요일";
					if (i != 16) {
						due_date = (i+1) + "주차 " + weeks[j]+"요일";
					} else {
						due_date = "16주차 금요일";
					}

					String title = line;
					if (j == 0) {
						category="평일";
						timetable = "2교시: 컴퓨터 구조, 3교시: 컴퓨터 비전, 6,7교시: 공학설계입문";
						assignment="컴퓨터 비전 과제";
					} else if (j == 1) {
						category="평일";
						timetable = "2교시: 실전프로젝트1, 3교시: 이산수학, 4교시: 기독교 변증학";
						assignment="이산수학 과제";
					} else if (j == 2) {
						category="평일";
						timetable = "5교시: 채플,7교시: 팀모임";
						assignment="스터디 과제";
					} else if (j == 3) {
						category="평일";
						timetable = "2교시: 컴퓨터 구조, 3교시: 컴퓨터 비전";
						assignment="컴퓨터 구조 과제";
					} else if (j == 4) {
						category="평일";
						timetable = "1,2교시: 실전프로젝트1, 3교시: 이산수학, 4교시: 기독교 변증학";
						assignment="실전프로젝트1 과제";
					} else if (j == 5) {
						category="주말";
						timetable = "";
						assignment="공학설계입문 과제";
					} else if (j == 6) {
						category="주말";
						assignment = "기독교 변증학 과제";
					}
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, title);
					pstmt.setString(2, timetable);
					pstmt.setString(3, category);
					pstmt.setString(4, current_date);
					pstmt.setString(5, due_date);
					pstmt.setString(6, assignment);
					int count = pstmt.executeUpdate();
					if (count > 0)
						records++;
					pstmt.close();
				}
			}
			System.out.println(records + " records read!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list(title, timetable, category, current_date,due_date, assignment,overdue_task)" + "values(?,?,?,?,?,?,?);";
		int count = 0;
		PreparedStatement pstmt;
		String timetable="";
		String title=t.getTitle();
		System.out.println(title);
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,title);
			if(!t.getCategory().equals("휴일")) {
				if(title.contains("월요일")) {
					timetable = "2교시: 컴퓨터 구조, 3교시: 컴퓨터 비전, 6,7교시: 공학설계입문";
				}
				else if(title.contains("화요일")||title.equals("금요일")) {
					timetable = "2교시: 실전프로젝트1, 3교시: 이산수학, 4교시: 기독교 변증학";
				}
				else if(title.contains("수요일")) {
					timetable = "5교시: 채플,7교시: 팀모임";
				}
				else if(title.contains("목요일")) {
					timetable = "2교시: 컴퓨터 구조, 3교시: 컴퓨터 비전";
				}
				else if(title.equals("금요일")) {
					timetable = "1,2교시: 실전프로젝트1, 3교시: 이산수학, 4교시: 기독교 변증학";
				}
				
				pstmt.setString(2, timetable);
				pstmt.setString(3, t.getCategory());
				pstmt.setString(4, t.getCurrent_date());
				pstmt.setString(5, t.getDue_date());
				pstmt.setString(6, t.getAssignment());
				pstmt.setString(7, t.getOverdue_task());
				count = pstmt.executeUpdate();
				pstmt.close();
			}
			else {
				timetable="없습니다";
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(String title_num) {
		String sql = "delete from list where title = ?;";
		PreparedStatement pstmt;
		StringTokenizer token = new StringTokenizer(title_num);
		int num;
		int count = 0;
		while (token.hasMoreTokens()) {
			num = Integer.parseInt(token.nextToken());
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				count = pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	public int editItem(TodoItem t) {
		String sql = "update list set title= ? , timetable=?, category=?,current_date=?,due_date=?,complete=?"
				+ " where  = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.gettimetable());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, 0);
			pstmt.setInt(7, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int editAssignment(TodoItem t) {
		String sql = "update list set title= ? , timetable=?, category=?,current_date=?,due_date=?,complete=?"
				+ " where title = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, settingtitle(t.getTitle(),t.getCategory()));
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, 0);
			pstmt.setString(7, t.getTitle());
			count = pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String timetable = rs.getString("timetable");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				String assignment = rs.getString("assignment");
				TodoItem t = new TodoItem(title, timetable, category, due_date, assignment);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%" + keyword + "%";
		try {
			String sql = "SELECT * FROM list WHERE title LIKE? or timetable LIKE ? or assignment LIKE? or overdue_task Like?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			pstmt.setString(3, keyword);
			pstmt.setString(4, keyword);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String category = rs.getString("category");
				String title = rs.getString("title");
				String timetable = rs.getString("timetable");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				String assignemnt = rs.getString("assignment");
				String overdue_task = rs.getString("overdue_task");
				int complete = rs.getInt("complete");
				list.add(new TodoItem(title, complete, timetable, category, due_date, current_date, assignemnt,
						overdue_task));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private String toString(String category, int complete, String title, String timetable, String due_date,
			String current_date, String assignment, String overdue_task) {
		if (complete == 0) {
			return "[" + category + "] " + title + " - " + "[" + "시간표" + "] " + timetable + " - " + "[과제] "
					+ assignment + " - " + "[마감일] " + due_date + " - " + "밀린과제: " + overdue_task + " - " + current_date;
		} else {
			return "[" + category + "] " + title + " - " + "[" + "시간표" + "] " + timetable + " - " + "[과제] "
					+ assignment + "[V]" + " - " + "[마감일] " + due_date + " - " + "밀린과제: " + overdue_task + " - "
					+ current_date;
		}
	}

	public int listAll() {
		System.out.println("[전체 목록 , 총 " + getCount() + "개 ]");
		Statement stmt;
		int count = 0;
		try {
			String sql = "SELECT * from list";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String category = rs.getString("category");
				String title = rs.getString("title");
				String timetable = rs.getString("timetable");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int complete = rs.getInt("complete");
				String assignment = rs.getString("assignment");
				String overdue_task = rs.getString("overdue_task");
				System.out.println(toString(category, complete, title, timetable, due_date, current_date, assignment,
						overdue_task));
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int getCount() {
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public Boolean isDuplicate(String title) {
		Statement stmt;
		boolean check = false;
		try {
			String sql = "select * FROM list WHERE title ='" + title + "'";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if (title.equals(rs.getString("title"))) {
					check = true;
					break;
				}
				stmt.close();
				return check;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

	public int listAll(String string) {
		System.out.println("[전체 목록 , 총 " + getCount() + "개 ]");
		Statement stmt;
		int count = 0;
		try {
			String sql = "";
			if (string == "name_asc")
				sql = "SELECT * from list order by title";
			else if (string == "name_timetable")
				sql = "SELECT * from list order by title desc";
			else if (string == "date_asc")
				sql = "SELECT * from list order by due_date";
			else if (string == "date_timetable")
				sql = "SELECT * from list order by due_date desc";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String category = rs.getString("category");
				String title = rs.getString("title");
				String timetable = rs.getString("timetable");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int complete = rs.getInt("complete");
				String assignment = rs.getString("assignment");
				String overdue_task = rs.getString("overdue_task");
				System.out.println(toString(category, complete, title, timetable, due_date, current_date, assignment,
						overdue_task));
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			int count = 0;
			while (rs.next()) {
				list.add(rs.getString("category"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getCategory(String cat) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE category = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cat);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String category = rs.getString("category");
				String title = rs.getString("title");
				String timetable = rs.getString("timetable");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int complete = rs.getInt("complete");
				String assignment = rs.getString("assignment");
				String overdue_task = rs.getString("overdue_task");
				list.add(new TodoItem(category, complete, title, timetable, due_date, current_date, assignment,
						overdue_task));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void listAll(int i) {
		String sql = "SELECT * FROM list WHERE complete = 1";
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String category = rs.getString("category");
				String title = rs.getString("title");
				String timetable = rs.getString("timetable");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int complete = rs.getInt("complete");
				String assignment = rs.getString("assignment");
				String overdue_task = rs.getString("overdue_task");
				System.out.println(toString(category, complete, title, timetable, due_date, current_date, assignment,
						overdue_task));
				count++;
			}
			System.out.println("총" + count + "개의 항목이 완료되었습니다");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int completeItem(String complete_multi_num) {
		int i = 0;
		StringTokenizer token = new StringTokenizer(complete_multi_num);
		int num;
		int count = 0;
		while (token.hasMoreTokens()) {
			num = Integer.parseInt(token.nextToken());
			String sql = "update list set complete=?" + " where id = ?;";
			PreparedStatement pstmt;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 1);
				pstmt.setInt(2, num);
				count = pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	public String getoverdue_task() {
		PreparedStatement pstmt;
		String overdue_task="";
		try {
			String sql = "SELECT DISTINCT* FROM  list WHERE  overdue_task  NOT IN(?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "없습니다");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				overdue_task += rs.getString("overdue_task");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return overdue_task;
	}

	public ArrayList<TodoItem> getDueAssignments(String due_Date) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE due_date = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, due_Date);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String category = rs.getString("category");
				String title = rs.getString("title");
				String timetable = rs.getString("timetable");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int complete = rs.getInt("complete");
				String assignment = rs.getString("assignment");
				String overdue_task = rs.getString("overdue_task");
				list.add(new TodoItem(category, complete, title, timetable, due_date, current_date, assignment,
						overdue_task));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}