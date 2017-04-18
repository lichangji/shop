package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import model.BusinessVO;
import model.Call;
import model.CatVO;
import model.Cus_catVO;
import model.EmpVO;
import model.LibVO;
import model.SaleVO;
import model.SmsVO;
import model.SmslogVO;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class ExcelUtil {
	
	public static String exportExcelCall(List<Call> callList) throws Exception {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();
		
        String path = servletContext.getRealPath("/excelModel/excel"+new java.util.Date().getTime()+".xls");
		WritableWorkbook workbook = Workbook.createWorkbook(new File(path));
		
		for(int page = 0; page < (callList.size() / 65000) +1; page++){
			WritableSheet sheet = workbook.createSheet("Sheet" + page, page);
			sheet.setColumnView(1, 20);  
			sheet.setColumnView(2, 20);
			sheet.setColumnView(3, 20);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 20);
			sheet.setColumnView(7, 20);
			setWorkbookTitle(sheet);
			for (int i = 0; i < (page==(callList.size() / 65000) ? (callList.size()-page*65000) : 65000); i++) {
				Label label0 = new Label(0, i+1, (page*65000+i)+1+""); 
				sheet.addCell(label0); 
				Label label1 = new Label(1, i+1, callList.get(page*65000+i).getFrom_num()); 
				sheet.addCell(label1); 
				Label label2 = new Label(2, i+1, callList.get(page*65000+i).getTo_num()); 
				sheet.addCell(label2); 
				String time = callList.get(page*65000+i).getCall_time();
				time = DateParseUtil.parseTimestampToDatetime(time);
				Label label3 = new Label(3, i+1, time); 
				sheet.addCell(label3); 
				Label label4 = new Label(4, i+1, callList.get(page*65000+i).getLib_name()); 
				sheet.addCell(label4); 
				Label label5 = new Label(5, i+1, callList.get(page*65000+i).getCity_name()); 
				sheet.addCell(label5); 
				List<CatVO> catList = callList.get(page*65000+i).getCatList();
				String cats = "";
				for (CatVO catVO : catList) {
					cats += " "+catVO.getName();
				}
				Label label6 = new Label(6, i+1, cats); 
				sheet.addCell(label6); 
				Call call = callList.get(page*65000+i);
				List<CatVO> cat_list = call.getCatList();
				CatVO cat = cat_list.get(0);
				BusinessVO businessVO = cat.getBusinessVO();
				Label label7 = new Label(7, i+1, businessVO.getName()); 
				sheet.addCell(label7); 
			}
		}
		workbook.write(); 
		workbook.close();
		return path;
	}

	private static void setWorkbookTitle(WritableSheet sheet)
			throws WriteException, RowsExceededException {
		Label title0 = new Label(0, 0, "序号"); 
		sheet.addCell(title0); 
		Label title1 = new Label(1, 0, "来电号码"); 
		sheet.addCell(title1); 
		Label title2 = new Label(2, 0, "查询号码"); 
		sheet.addCell(title2); 
		Label title3 = new Label(3, 0, "查询时间"); 
		sheet.addCell(title3); 
		Label title4 = new Label(4, 0, "单位"); 
		sheet.addCell(title4); 
		Label title5 = new Label(5, 0, "城市"); 
		sheet.addCell(title5); 
		Label title6 = new Label(6, 0, "分类"); 
		sheet.addCell(title6); 
		Label title7 = new Label(7, 0, "行业"); 
		sheet.addCell(title7);
	}
	
	private static void setWorkbookCusTitle(WritableSheet sheet)
			throws WriteException, RowsExceededException {
		Label title0 = new Label(0, 0, "序号"); 
		sheet.addCell(title0); 
		Label title1 = new Label(1, 0, "来电号码"); 
		sheet.addCell(title1); 
		Label title2 = new Label(2, 0, "查询号码"); 
		sheet.addCell(title2); 
		Label title3 = new Label(3, 0, "查询时间"); 
		sheet.addCell(title3); 
		Label title4 = new Label(4, 0, "单位"); 
		sheet.addCell(title4); 
		Label title5 = new Label(5, 0, "城市"); 
		sheet.addCell(title5); 
		Label title6 = new Label(6, 0, "分类"); 
		sheet.addCell(title6); 
	}

	public static List<LibVO> importLib(String path, LibVO vo) throws Exception{
		Workbook workbook = Workbook.getWorkbook(new File(path));
		Sheet sheet = workbook.getSheet(0);
		List<LibVO> list = new ArrayList<LibVO>();
		for(int i = 0; i < sheet.getRows()-1; i++){
			String number = sheet.getCell(0, i+1).getContents().trim();
			String name = sheet.getCell(1, i+1).getContents().trim();
			String cats = sheet.getCell(2, i+1).getContents().trim();
			if(!"".equals(number) && !"".equals(name) && !"".equals(cats)){
				LibVO libVO = new LibVO();
				libVO.setNum(number);
				libVO.setName(name);
				libVO.setCity_id(vo.getCity_id());
				libVO.setCity_name(vo.getCity_name());
				String[] cat_ids = cats.split(",");
				libVO.setCat_ids(cat_ids);
				list.add(libVO);
			}
			
		}
		workbook.close();
		return list;
	}

	public static String exporeExcelDetail(List<Call> callList, CatVO catVO) throws Exception {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();
		
        String path = servletContext.getRealPath("/excelModel/excel"+new java.util.Date().getTime()+".xls");
		WritableWorkbook workbook = Workbook.createWorkbook(new File(path));
		
        for(int page = 0; page < callList.size() / 65000 +1; page++){
        	WritableSheet sheet = workbook.createSheet("Sheet" + page, page);
        	sheet.setColumnView(1, 20);  
			sheet.setColumnView(2, 20);
			sheet.setColumnView(3, 20);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 20);
			sheet.setColumnView(7, 20);
			setWorkbookTitle(sheet); 
			for (int i = 0; i < (page==(callList.size() / 65000) ? (callList.size()-page*65000) : 65000); i++) {
				Label label0 = new Label(0, i+1, (page*65000+i)+1+""); 
				sheet.addCell(label0); 
				Label label1 = new Label(1, i+1, callList.get(page*65000+i).getFrom_num()); 
				sheet.addCell(label1); 
				Label label2 = new Label(2, i+1, callList.get(page*65000+i).getTo_num()); 
				sheet.addCell(label2); 
				String time = callList.get(page*65000+i).getCall_time();
				time = DateParseUtil.parseTimestampToDatetime(time);
				Label label3 = new Label(3, i+1, time); 
				sheet.addCell(label3); 
				Label label4 = new Label(4, i+1, callList.get(page*65000+i).getLib_name()); 
				sheet.addCell(label4); 
				Label label5 = new Label(5, i+1, callList.get(page*65000+i).getCity_name()); 
				sheet.addCell(label5); 
				if(catVO == null){
					List<CatVO> catList = callList.get(page*65000+i).getCatList();
					String cats = "";
					for (CatVO vo : catList) {
						cats += " "+vo.getName();
					}
					Label label6 = new Label(6, i+1, cats); 
					sheet.addCell(label6); 
					Label label7 = new Label(7, i+1, callList.get(page*65000+i).getCatList().get(0).getBusinessVO().getName()); 
					sheet.addCell(label7); 
				}else{
					Label label6 = new Label(6, i+1, catVO.getName()); 
					sheet.addCell(label6); 
					Label label7 = new Label(7, i+1, catVO.getBusinessVO().getName()); 
					sheet.addCell(label7); 
				}
			}
        }
		workbook.write(); 
		workbook.close();
		return path;
	}

	public static String exportFailedLib(List<LibVO> failedList) throws Exception {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();
		
        String path = servletContext.getRealPath("/excelModel/lib"+new java.util.Date().getTime()+".xls");
        
		WritableWorkbook workbook = Workbook.createWorkbook(new File(path));
		
		WritableSheet sheet = workbook.createSheet("Sheet0", 0);
    	sheet.setColumnView(1, 20);  
		sheet.setColumnView(2, 20);
		
		Label title0 = new Label(0, 0, "序号"); 
		sheet.addCell(title0); 
		Label title1 = new Label(1, 0, "号码"); 
		sheet.addCell(title1); 
		Label title2 = new Label(2, 0, "单位名"); 
		sheet.addCell(title2); 
		Label title3 = new Label(3, 0, "分类"); 
		sheet.addCell(title3); 
		for (int i=0; i < failedList.size(); i++) {
			LibVO libVO = failedList.get(i);
			Label label0 = new Label(0, i+1, i+1+""); 
			sheet.addCell(label0); 
			Label label1 = new Label(1, i+1, libVO.getNum()); 
			sheet.addCell(label1); 
			Label label2 = new Label(2, i+1, libVO.getName()); 
			sheet.addCell(label2); 
			Label label3 = new Label(3, i+1, OtherUtil.parseArrToString(libVO.getCat_ids())); 
			sheet.addCell(label3); 
		}
		workbook.write(); 
		workbook.close();
		return path;
	}

	public static String exporeExcelCusDetail(List<Call> callList, Cus_catVO cus_catVO) throws Exception{
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();
		
        String path = servletContext.getRealPath("/excelModel/excel"+new java.util.Date().getTime()+".xls");
		WritableWorkbook workbook = Workbook.createWorkbook(new File(path));
		
        for(int page = 0; page < callList.size() / 65000 +1; page++){
        	WritableSheet sheet = workbook.createSheet("Sheet" + page, page);
        	sheet.setColumnView(1, 20);  
			sheet.setColumnView(2, 20);
			sheet.setColumnView(3, 20);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 20);
			setWorkbookCusTitle(sheet); 
			for (int i = 0; i < (page==(callList.size() / 65000) ? (callList.size()-page*65000) : 65000); i++) {
				Label label0 = new Label(0, i+1, (page*65000+i)+1+""); 
				sheet.addCell(label0); 
				Label label1 = new Label(1, i+1, callList.get(page*65000+i).getFrom_num()); 
				sheet.addCell(label1); 
				Label label2 = new Label(2, i+1, callList.get(page*65000+i).getTo_num()); 
				sheet.addCell(label2); 
				String time = callList.get(page*65000+i).getCall_time();
				time = DateParseUtil.parseTimestampToDatetime(time);
				Label label3 = new Label(3, i+1, time); 
				sheet.addCell(label3); 
				Label label4 = new Label(4, i+1, callList.get(page*65000+i).getLib_name()); 
				sheet.addCell(label4); 
				Label label5 = new Label(5, i+1, callList.get(page*65000+i).getCity_name()); 
				sheet.addCell(label5); 
				if(cus_catVO ==null){
					Label label6 = new Label(6, i+1, callList.get(page*65000+i).getCat_name()); 
					sheet.addCell(label6); 
				}else{
					Label label6 = new Label(6, i+1, cus_catVO.getName()); 
					sheet.addCell(label6); 
				}
			}
        }
		workbook.write(); 
		workbook.close();
		return path;
	}

	public static String exporeSms(List<SmslogVO> list, SaleVO saleVO) throws Exception {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();
		
        String path = servletContext.getRealPath("/excelModel/sms"+new java.util.Date().getTime()+".xls");
		WritableWorkbook workbook = Workbook.createWorkbook(new File(path));
		
		 for(int page = 0; page < list.size() / 65000 +1; page++){
	        	WritableSheet sheet = workbook.createSheet("Sheet" + page, page);
	        	sheet.setColumnView(1, 20);  
				sheet.setColumnView(2, 20);
				sheet.setColumnView(3, 20);
				sheet.setColumnView(4, 20);
				sheet.setColumnView(5, 20);
				Label title0 = new Label(0, 0, "序号"); 
				sheet.addCell(title0); 
				Label title1 = new Label(1, 0, "活动标题"); 
				sheet.addCell(title1); 
				Label title2 = new Label(2, 0, "号码"); 
				sheet.addCell(title2); 
				Label title3 = new Label(3, 0, "查询号码"); 
				sheet.addCell(title3); 
				Label title4 = new Label(4, 0, "时间"); 
				sheet.addCell(title4); 
				Label title5 = new Label(5, 0, "网络"); 
				sheet.addCell(title5); 
				for (int i = 0; i < (page==(list.size() / 65000) ? (list.size()-page*65000) : 65000); i++) {
					Label label0 = new Label(0, i+1, (page*65000+i)+1+""); 
					sheet.addCell(label0); 
					Label label1 = new Label(1, i+1, saleVO.getTitle()); 
					sheet.addCell(label1); 
					Label label2 = new Label(2, i+1, list.get(page*65000+i).getFrom_num()); 
					sheet.addCell(label2); 
					Label label3 = new Label(3, i+1, list.get(page*65000+i).getTo_num()); 
					sheet.addCell(label3); 
					Label label4 = new Label(4, i+1, list.get(page*65000+i).getCreate_date()); 
					sheet.addCell(label4); 
					Label label5 = new Label(5, i+1, list.get(page*65000+i).getSend_cat()); 
					sheet.addCell(label5); 
				}
	        }
			workbook.write(); 
			workbook.close();
			return path;
	}

	public static String exportEmplog(List<EmpVO> list) throws Exception {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();
		
        String path = servletContext.getRealPath("/excelModel/emplog"+new java.util.Date().getTime()+".xls");
        
		WritableWorkbook workbook = Workbook.createWorkbook(new File(path));
		
		WritableSheet sheet = workbook.createSheet("Sheet0", 0);
    	sheet.setColumnView(1, 20);  
		sheet.setColumnView(2, 20);
		
		Label title0 = new Label(0, 0, "序号"); 
		sheet.addCell(title0); 
		Label title1 = new Label(1, 0, "工号"); 
		sheet.addCell(title1); 
		Label title2 = new Label(2, 0, "短信量"); 
		sheet.addCell(title2); 
		for (int i=0; i < list.size(); i++) {
			EmpVO vo = list.get(i);
			Label label0 = new Label(0, i+1, i+1+""); 
			sheet.addCell(label0); 
			Label label1 = new Label(1, i+1, vo.getEmpno()); 
			sheet.addCell(label1); 
			Label label2 = new Label(2, i+1, vo.getCount()); 
			sheet.addCell(label2); 
		}
		workbook.write(); 
		workbook.close();
		return path;
	}
}
