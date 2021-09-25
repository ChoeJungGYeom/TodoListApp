package com.todo.service;

import java.util.*;
import java.io.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, category, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� �߰�]\n"
				+"���� > ");
		
		title = sc.nextLine().trim();
		if (list.isDuplicate(title)) {	// �ߺ� �˻�
			System.out.println("������ �ߺ��˴ϴ�.");
			return;
		}
		
		System.out.print("ī�װ� > ");
		category = sc.nextLine().trim();
		
		System.out.print("���� > ");
		desc = sc.nextLine().trim();

		System.out.print("�������� > ");
		due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, category, desc, due_date);
		list.addItem(t);
		System.out.print("�߰��Ǿ����ϴ�!");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� ����]\n"
				+"������ �׸��� ��ȣ�� �����Ͻÿ� > ");
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
		
		System.out.println("�����Ǿ����ϴ�.");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print("[�׸� ����]\n"
				+"������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		
//		String title = sc.nextLine().trim();
//		if (!l.isDuplicate(title)) {
//			System.out.println("�׸��� �������� �ʽ��ϴ�.");
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
		
		sc.nextLine();	// ���� ����
		System.out.println("�� ���� > ");
		String new_title = sc.nextLine().trim();
		
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��˴ϴ�.");
			return;
		}
		
		System.out.println("�� ī�װ� > ");
		String new_category = sc.nextLine().trim();
		
		System.out.println("�� ���� > ");
		String new_description = sc.nextLine().trim();
		
		System.out.println("�� �������� > ");
		String new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_category, new_description, new_due_date);
		l.addItem(t);
		System.out.println("�����Ǿ����ϴ�.");

	}

	public static void listAll(TodoList l) {
		System.out.println("[��ü ���, �� "+l.size()+"��]");
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
			System.out.println(count+"���� �׸��� �о����ϴ�.");		
		}catch (FileNotFoundException e) {
			System.out.println(filename+" ������ �����ϴ�.");
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
		System.out.println("��� �����Ͱ� ����Ǿ����ϴ�.");
	}
	
	public static void find(TodoList l, String word) {
		int count = 0;
		int check = 0;	// ã�� ���� �Է� ����
		for (TodoItem item : l.getList()) {
			count++;
			if(item.getTitle().contains(word) || item.getDesc().contains(word)) {
				check++;
				System.out.println(count+". [" + item.getCategory() + "]"+ item.getTitle() + " - " +item.getDesc() + " - " +item.getDue_date() + " [" + item.getCurrent_date()+"] ");
			}
		}
		System.out.println("�� " + check + "���� �׸��� ã�ҽ��ϴ�.");
	}
}
