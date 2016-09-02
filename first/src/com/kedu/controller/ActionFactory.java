package com.kedu.controller;

import com.kedu.common.Action;
import com.kedu.emp.action.EmpGoAction;
import com.kedu.emp.action.EmpInUpAction;
import com.kedu.emp.action.EmpJsonListAction;
import com.kedu.emp.action.LogOutAction;
import com.kedu.emp.action.LoginGoAction;
import com.kedu.emp.action.LoginOk;
import com.kedu.emp.action.ManagerUpdateAction;
import com.kedu.emp.action.MemberListGoAction;
import com.kedu.emp.action.PicUploadAction;
import com.kedu.emp.action.SearchAddr;

public class ActionFactory {
	private static ActionFactory instance = new ActionFactory();

	private ActionFactory() {
		super();
	}

	public static ActionFactory getInstance() {
		return instance;
	}
	
	public Action getAction(String cmd) {
		Action action = null;
//		System.out.println("ActionFactory :" + cmd);

//		메뉴 : 사원목록
		if(cmd.equals("login")) {
			action = new LoginGoAction();
		} else if (cmd.equals("logOut")) {
			action = new LogOutAction();
		} else if (cmd.equals("loginOk")) {
			action = new LoginOk();
		} else if(cmd.equals("emp")) {
			action = new EmpGoAction();
		} else if (cmd.equals("empJsonList")) {
			action = new EmpJsonListAction();
		} else if (cmd.equals("empInUp")) {
			action = new EmpInUpAction();
		} else if (cmd.equals("managerUpdate")) {
			action = new ManagerUpdateAction();
		} else if (cmd.equals("memberList")) {
			action = new MemberListGoAction();
		} else if (cmd.equals("picUpload")){
			action = new PicUploadAction();
		} else if (cmd.equals("searchAddr")){
			action = new SearchAddr();
		} 


		return action;
	}
	
}
