package com.test.sku.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserService
{	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	static UserDAO dao = new UserDAO();
	
	public UserService() {}
	
	public UserService(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	/**정보를 처리한다*/
	public String process() 
	{
		String cmd = request.getParameter("cmd");
		String viewPath = null;
		
		if(cmd==null) {
	         return "/jsp/index.jsp";
	      }

		 if(cmd.equals("add") || cmd.equals("updatePwd") || cmd.equals("delete")) {	//||이거나
	         if(!loginCheck()) {
	            Map<String,Object> map = new HashMap<>();
	            map.put("added", false);
	            map.put("updated", false);
	            map.put("deleted", false);
	            map.put("cause", "로그인 이용자만 이용할 수 있는 기능입니다");
	            map.put("loginRequired", true);
	            sendJSON(map);
	            return null;
	         }
	      }

		 if(cmd.equals("loginForm")) 
	      {
	         viewPath = "/jsp/loginForm.jsp";
	      }else if(cmd.equals("login")) {
			//로그인 절차
			String uid = request.getParameter("uid");
			String pwd = request.getParameter("pwd");
			
			User user = new User(uid,pwd);
			//UserDAO dao = new UserDAO();
			boolean ok = dao.login(user);
			
			//로그인 통과한 이용자는 어떤 페이지로 이동하든지 로그인 여부를 확인할 수 있도록
			//영역 오브젝트에 로그인 성공 사실을 기억해놓는다
			if(ok) {
				request.getSession().setAttribute("uid", uid);
			}			
			//이용자에게 로그인 성공/실패 여부를 전달한다
			sendJSON("ok",ok+"");
			
		}else if(cmd.equals("logout")){
			request.getSession().invalidate();	//세션 사라짐, 세션에 장바구니
			sendJSON("logout",true+"");
			return null;
		}else if(cmd.equals("List")){
			//UserDAO dao = new UserDAO();
			List<User> list = dao.getList();
			request.setAttribute("list", list);
			viewPath = "/jsp/userList.jsp";
			
		}else if(cmd.equals("detail")) {	
			String uid = request.getParameter("uid");
			//UserDAO dao = new UserDAO();			
			User deail = dao.getUser(uid);
			request.setAttribute("deail", deail);
			viewPath = "/jsp/detailUser.jsp";
		}else if (cmd.equals("edit")) {
		    String uid = request.getParameter("uid");
		   
		    request.setAttribute("uid", uid);

		    viewPath = "/jsp/editPwd.jsp";
		   		 
		}else if(cmd.equals("updatePwd")) 
         {
            String uid = request.getParameter("uid");
            String newPwd = request.getParameter("pwd");
           // UserDAO dao = new UserDAO();
            boolean updated = dao.update(new User(uid, newPwd));
           // System.out.println("업데이트 결과:" + updated);
            
           sendJSON("updated", updated+"");
         }else if(cmd.equals("delete")) 
         {
     	    String uid = request.getParameter("uid");
     	    
     	    //UserDAO dao = new UserDAO();
     	    
     	    boolean deleted = dao.delete(uid); 
     	    
     	    sendJSON("deleted", deleted+"");
     	}else if(cmd.equals("addForm"))
     	{
     		viewPath = "/jsp/userInput.jsp";
     	}else if(cmd.equals("add"))
     	{
     		
     		String uid = request.getParameter("uid");
     		String pwd = request.getParameter("pwd");
     		
     		//UserDAO dao = new UserDAO();
     		
     		boolean added = dao.add(new User(uid,pwd));
     		
     		sendJSON("added", added+"");    		
     		
     	}
		return viewPath;
	}
	
	private void sendJSON(String key, String value) {
		String json = String.format("{\"%s\":%s}", key, value);
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {			
			e.printStackTrace();
		}
 		out.print(json);
 		out.flush(); 
	}
	 private void sendJSON(Map<String,Object> map) {
	      JSONObject jsObj = new JSONObject(map);
	      String js = jsObj.toJSONString();
	      try {
	         PrintWriter out = response.getWriter();
	         out.print(js);
	         out.flush();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }

	private boolean loginCheck() {
		Object obj = request.getSession().getAttribute("uid");
		return obj != null;
	}
	
	
}
