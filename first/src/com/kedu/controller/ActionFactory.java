package com.kedu.controller;

import com.kedu.common.Action;
import com.kedu.emp.action.GoAction;
import com.kedu.emp.action.BoardListAction;
import com.kedu.emp.action.EmpInUpAction;
import com.kedu.emp.action.EmpListAction;
import com.kedu.emp.action.EmpSkillInsertAction;
import com.kedu.emp.action.EmpSkillListAction;
import com.kedu.emp.action.LogOutAction;
import com.kedu.emp.action.LoginGoAction;
import com.kedu.emp.action.LoginOk;
import com.kedu.emp.action.ManagerUpdateAction;
import com.kedu.emp.action.MemberListGoAction;
import com.kedu.emp.action.PicUploadAction;
import com.kedu.emp.action.PrjInUpAction;
import com.kedu.emp.action.PrjListAction;
import com.kedu.emp.action.SchInupAction;
import com.kedu.emp.action.SchListAction;
import com.kedu.emp.action.SearchAddr;
import com.kedu.emp.action.SkillListAction;

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
		} else if(cmd.equals("go")) {
			action = new GoAction();
		} else if (cmd.equals("empJsonList")) {
			action = new EmpListAction();
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
		} else if (cmd.equals("schList")){
			action = new SchListAction();
		} else if (cmd.equals("schInup")){
			action = new SchInupAction();
		} else if (cmd.equals("empSkillList")){
			action = new EmpSkillListAction();
		} else if (cmd.equals("skillList")){
			action = new SkillListAction();
		} else if (cmd.equals("empSkillInsert")){
			action = new EmpSkillInsertAction();
		} else if (cmd.equals("prjList")){
			action = new PrjListAction();
		} else if (cmd.equals("prjInUp")){
			action = new PrjInUpAction();
		} else if (cmd.equals("boardList")){
			action = new BoardListAction();
		}                                      
		


		return action;
	}
	
}
