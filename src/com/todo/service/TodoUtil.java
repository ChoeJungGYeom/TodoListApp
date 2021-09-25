package com.todo.service;

import java.util.*;
import java.io.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, category, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 추가]\n"
				+"제목 > ");
		
		title = sc.nextLine().trim();
		if (list.isDuplicate(title)) {	// 중복 검사
			System.out.println("제목이 중복됩니다.");
			return;
		}
		
		System.out.print("카테고리 > ");
		category = sc.nextLine().trim();
		
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();

		System.out.print("마감일자 > ");
		due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, category, desc, due_date);
		list.addItem(t);
		System.out.print("추가되었습니다!");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 삭제]\n"
				+"삭제할 항목의 번호를 입혁하시오 > ");
		int num = sc.nextInt();
		int count = 1;
		for (TodoItem item : l.getList()) {
			if (num==count) {
				l.deleteItem(item);
				break;
			}
			else{
				count++;
			}
		}
		
		System.out.println("삭제되었습니다.");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print("[항목 수정]\n"
				+"수정할 항목의 번호를 입력하시오 > ");
		
//		String title = sc.nextLine().trim();
//		if (!l.isDuplicate(title)) {
//			System.out.println("항목이 존재하지 않습니다.");
//			return;
//		}
		int num = sc.nextInt();
		int count = 1;
		for (TodoItem item : l.getList()) {
			if (num==count) {
				l.deleteItem(item);
				break;
			}
			else{
				count++;
			}
		}
		
		sc.nextLine();	// 버퍼 비우기
		System.out.println("새 제목 > ");
		String new_title = sc.nextLine().trim();
		
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다.");
			return;
		}
		
		System.out.println("새 카테고리 > ");
		String new_category = sc.nextLine().trim();
		
		System.out.println("새 내용 > ");
		String new_description = sc.nextLine().trim();
		
		System.out.println("새 마감일자 > ");
		String new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_category, new_description, new_due_date);
		l.addItem(t);
		System.out.println("수정되었습니다.");

	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록, 총 "+l.size()+"개]");
		int count = 0;
		for (TodoItem item : l.getList()) {
			count++;
			System.out.println(count+". [" + item.getCategory() + "]"+ item.getTitle() + " - " +item.getDesc() + " - " +item.getDue_date() + " [" + item.getCurrent_date()+"] ");
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			int count = 0;
			while((line = br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(line,"##");
				String title = st.nextToken();
				String category = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				TodoItem item = new TodoItem(title, category, description, due_date);
				item.setCurrent_date(current_date);
				l.addItem(item);
				count++;
			}
			br.close();
			System.out.println(count+"개의 항목을 읽었습니다.");		
		}catch (FileNotFoundException e) {
			System.out.println(filename+" 파일이 없습니다.");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveList(TodoList l, String filename){
		try {
			Writer w = new FileWriter(filename);
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("모든 데이터가 저장되었습니다.");
	}
	
	public static void find(TodoList l, String word) {
		int count = 0;
		int check = 0;	// 찾은 개수 입력 변수
		for (TodoItem item : l.getList()) {
			count++;
			if(item.getTitle().contains(word) || item.getDesc().contains(word)) {
				check++;
				System.out.println(count+". [" + item.getCategory() + "]"+ item.getTitle() + " - " +item.getDesc() + " - " +item.getDue_date() + " [" + item.getCurrent_date()+"] ");
			}
		}
		System.out.println("총 " + check + "개의 항목을 찾았습니다.");
	}
}
