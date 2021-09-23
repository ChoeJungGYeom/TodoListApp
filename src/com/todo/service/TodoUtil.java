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
		System.out.print("[�׸� �߰�]\n"
				+"���� > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {	// �ߺ� �˻�
			//System.out.printf("title can't be duplicate");
			System.out.println("������ �ߺ��˴ϴ�.");
			return;
		}
		sc.nextLine();
		System.out.print("�߰��� ���� > ");
		desc = sc.nextLine().trim();
		//System.out.println("enter the description");
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.print("�߰��Ǿ����ϴ�!");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� ����]\n"
				+"������ �׸� > ");
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
		
		System.out.println("�����Ǿ����ϴ�.");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print("[�׸� ����]\n"
				+"������ �׸� > ");
		
//		System.out.println("\n"
//				+ "========== Edit Item Section\n"
//				+ "enter the title of the item you want to update\n"
//				+ "\n");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("�׸��� �������� �ʽ��ϴ�.");
			return;
		}

		System.out.println("�� ���� > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��˴ϴ�.");
			return;
		}
		
		System.out.println("�� ���� > ");
		String new_description = sc.next().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("�����Ǿ����ϴ�.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[��ü ���]");
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
			System.out.println(count + "���� �׸��� �о����ϴ�.");
			
		} 
		catch (FileNotFoundException e) {
			System.out.println("������ �������� �ʽ��ϴ�.");
		} 
		
	}

	public static void saveList(TodoList l, String string) throws IOException {
		Writer out = new FileWriter(string);
		for (TodoItem item : l.getList()) {
			out.write(item.toSaveString());
		}
		out.close();
		
		System.out.println("�����͸� �����߽��ϴ�.");
	}
}
