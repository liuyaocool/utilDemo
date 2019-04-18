package com.liuyao.demo.util;

//import com.kjps.service.system.DicService;
//import com.kjps.util.SpringContextUtil;
//import org.activiti.engine.impl.util.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 
* @ClassName: CacheServlet  
* @Description: TODO(字典缓存)  
* @author yangrongze  
* @date 2018年4月26日  
*
 */
public class CacheServlet extends HttpServlet {

    public CacheServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
    	ServletContext context = this.getServletContext();
    	this.dictionaryInit(context);
    	System.out.println("字典缓存创建成功");
    }
    
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/x-javascript;charset=utf-8");
		resp.setHeader("cache-control", "no-cache");
//		JSONObject json = new JSONObject().put("dicCode",
//				this.getServletContext().getAttribute("dicCode"));
//		System.out.println(json.toString());
//		resp.getWriter().write(json.toString());
		resp.getWriter().flush();
	}

	/**
	 * @author yangrongze
	 * @description 
	 */
	public synchronized void dictionaryInit(ServletContext context){
		if(context.getAttribute("dicCode")!=null) {
			return;
		}
//		DicService dicService = SpringContextUtil
//		        .getBean(DicService.class);
//		Map<String,Object> map = dicService.listForMap();
//		context.setAttribute("dicCode", map);
	}

	/**
	 * 字典缓存重载
	 * @author liuyao
	 * @param context
	 */
	public static synchronized void dictionaryReload(ServletContext context) {
		if(context.getAttribute("dicCode")!=null) {
			context.removeAttribute("dicCode");
		}
//		DicService dicService = SpringContextUtil
//		        .getBean(DicService.class);
//		Map<String,Object> map = dicService.listForMap();
//		context.setAttribute("dicCode", map);
		System.out.println("字典缓存重载成功");
	}
}
