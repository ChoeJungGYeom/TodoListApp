package com.todo;

import java.io.IOException;
import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.*;

public class TodoMain {
	
	public static void start(){
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		TodoUtil.loadList(l, "todolist.txt");
		do {
			isList = false;
			String choice = sc.next();
		
			switch (choice) {
			case "menu":
				Menu.prompt();
				break;
				
			case "help":
				Menu.displaymenu();
				break;
				
			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				l.sortByName();
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				isList = true;
				break;
				
			case "ls_date_asc":
				l.sortByDate();
				isList = true;
				break;
				
			case "ls_date_desc":
				l.sortByDate();
				l.reverseList();
				isList = true;
				break;
			
			case "find":
				String word = sc.next();
				TodoUtil.find(l, word);
				break;

			case "exit":
				quit = true;
				break;

			default:
		        System.out.println("정확한 명령어를 입력하세요 - help");
				break;
			}
			if(isList) l.listAll();
		} while (!quit);
		
		TodoUtil.saveList(l, "todolist.txt");
	}
}
