package controller;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import model.CatVO;
import model.Good;
import service.CatService;

@Controller
public class CatController {
	@Autowired
	private CatService catService;
	
	@RequestMapping("/catList")
	public String catList(HttpServletRequest request, HttpServletResponse response){
		
		List<CatVO> catList = catService.getList();
		request.setAttribute("catList", catList);
		
		return "catList";//request.getRequestDispatcher("catList.jsp").forward(request,respose);
	}
	
	@ResponseBody
	@RequestMapping("/goods")
	public String aa(HttpServletRequest request, HttpServletResponse response, String pageIndex){
		System.out.println(pageIndex); 
		response.setHeader("Access-Control-Allow-Origin","*");  
		List<Good> list = new ArrayList<Good>();
		for (int i = 0; i < 10; i++) {
			Good g1 = new Good();
			g1.setId(i+1);
			g1.setImg("http://ionicframework.com/dist/preview-app/www/assets/img/nin-live.png");
			g1.setTitle("阿打扫打扫打扫大苏打打扫打扫打扫打扫打扫打扫大adasdasd萨打扫大苏打打扫大");
			g1.setPrice(2.55);
			g1.setScore(10);
			list.add(g1);
		}
		
		return JSON.toJSONString(list);
	}
	
	@ResponseBody
	@RequestMapping("/good")
	public String good(HttpServletRequest request, HttpServletResponse response, String id){
		System.out.println(id); 
		response.setHeader("Access-Control-Allow-Origin","*");  
		
		Good g1 = new Good();
		g1.setId(1);
		g1.setImg("http://ionicframework.com/dist/preview-app/www/assets/img/nin-live.png");
		g1.setTitle("阿打扫打扫打扫大苏打打扫打扫打扫打扫打扫打扫大adasdasd萨打扫大苏打打扫大阿打扫打扫打扫大苏打打扫打扫打扫打扫打扫打扫大adasdasd萨打扫大苏打打扫大阿打扫打扫打扫大苏打打扫打扫打扫打扫打扫打扫大adasdasd萨打扫大苏打打扫大");
		g1.setPrice(2.55);
		g1.setScore(10);
		return JSON.toJSONString(g1);
	}
}
