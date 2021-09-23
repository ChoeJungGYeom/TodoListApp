package com.todo.service;

import java.util.*;
import java.io.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
//		System.out.println("\n"
//				+ "========== Create item Section\n"
//				+ "enter the title\n");
		System.out.print("[항목 추가]\n"
				+"제목 > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {	// 중복 검사
			//System.out.printf("title can't be duplicate");
			System.out.println("제목이 중복됩니다.");
			return;
		}
		sc.nextLine();
		System.out.print("추가할 내용 > ");
		desc = sc.nextLine().trim();
		//System.out.println("enter the description");
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.print("추가되었습니다!");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 삭제]\n"
				+"삭제할 항목 > ");
		String title = sc.next();
		
//		System.out.println("\n"
//				+ "========== Delete Item Section\n"
//				+ "enter the title of item to remove\n"
//				+ "\n");
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
		
		System.out.println("삭제되었습니다.");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print("[항목 수정]\n"
				+"수정할 항목 > ");
		
//		System.out.println("\n"
//				+ "========== Edit Item Section\n"
//				+ "enter the title of the item you want to update\n"
//				+ "\n");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("항목이 존재하지 않습니다.");
			return;
		}

		System.out.println("새 제목 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다.");
			return;
		}
		
		System.out.println("새 내용 > ");
		String new_description = sc.next().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록]");
		for (TodoItem item : l.getList()) {
			System.out.println("[" + item.getTitle() + "]"+" : "+item.getDesc()+" ["+item.getCurrent_date()+"] ");
		}
	}
	
	public static void loadList(TodoList l, String string) throws IOException{
		try {
			BufferedReader in = new BufferedReader(new FileReader(string));
			
			String inputline;
			int count = 0;
			while((inputline = in.readLine()) != null) {
				System.out.println(inputline);
				StringTokenizer st = new StringTokenizer(inputline, "##");
				String title = st.nextToken();
				String desc = st.nextToken();
				String current_date = st.nextToken();
			
				TodoItem t = new TodoItem(title, desc);
				t.setCurrent_date(current_date);
				l.addItem(t);
				count = count + 1;
			}
			
			in.close();
			System.out.println(count + "개의 항목을 읽었습니다.");
			
		} 
		catch (FileNotFoundException e) {
			System.out.println("파일이 존재하지 않습니다.");
		} 
		
	}

	public static void saveList(TodoList l, String string) throws IOException {
		Writer out = new FileWriter(string);
		for (TodoItem item : l.getList()) {
			out.write(item.toSaveString());
		}
		out.close();
		
		System.out.println("데이터를 저장했습니다.");
	}
}
