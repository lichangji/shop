package interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AdminInterceptor implements HandlerInterceptor {

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception ex)
			throws Exception {
		
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mv) throws Exception {
		
	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) throws Exception {
		String url = request.getRequestURI();
		ManagerVO managerVO = (ManagerVO) request.getSession().getAttribute("admin");
		if(managerVO != null){
			return true;
		}
		if(url != null && url.endsWith("managerLogin.do")){
			return true;
		}
		response.setContentType("text/html;charset=utf-8");   
		PrintWriter out = response.getWriter(); 
		out.print("<script language='javascript'>top.location.href='/admin/login4.jsp';</script>");
		out.flush();
		out.close();
		return false;
	}
	
}
