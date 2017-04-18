
package util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpXmlClientUtil {
	//用apache接口实现http的post提交数据
	public static String sendHttpClientPost(String path,
			Map<String, String> params, String encode) throws Exception {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		// 实现将请求的参数封装到表单中，即请求体中
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, encode);
		// 使用post方式提交数据
		HttpPost httpPost = new HttpPost(path);
		httpPost.setEntity(entity);
		// 执行post请求，并获取服务器端的响应HttpResponse
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse httpResponse = client.execute(httpPost);
		
		//获取服务器端返回的状态码和输入流，将输入流转换成字符串
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			HttpEntity httpEntity = httpResponse.getEntity();
			String body = EntityUtils.toString(httpEntity, encode);
			return body;
		}
		return "";
	}
	
	public static String get(String url) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		// The underlying HTTP connection is still held by the response object
		// to allow the response content to be streamed directly from the network socket.
		// In order to ensure correct deallocation of system resources
		// the user MUST either fully consume the response content  or abort request
		// execution by calling CloseableHttpResponse#close().
		//建立的http连接，仍旧被response保持着，允许我们从网络socket中获取返回的数据
		//为了释放资源，我们必须手动消耗掉response1或者取消连接（使用CloseableHttpResponse类的close方法）
		
		System.out.println(response.getStatusLine());
		HttpEntity entity1 = response.getEntity();
		// do something useful with the response body
		// and ensure it is fully consumed
		EntityUtils.consume(entity1);
		response.close();
		return paseResponse(response);
	}
	
	private static String paseResponse(HttpResponse response) {  
		HttpEntity entity = response.getEntity();  
		
		String body = null;  
		try {  
			body = EntityUtils.toString(entity);  
		} catch (ParseException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		
		return body;  
	} 
	
	@SuppressWarnings("deprecation")
	public static void SubmitPost(String url, String filepath, String filename, String type, String subject, String text){  
		HttpClient httpclient = null;
		try {  
			httpclient = new DefaultHttpClient();  
			HttpPost httppost = new HttpPost(url);  
			MultipartEntity reqEntity = new MultipartEntity();  
			
			reqEntity.addPart("file", new FileBody(new File(filepath)));//file1为请求后台的File upload;属性      
			reqEntity.addPart("name", new StringBody(filename, Charset.forName("utf-8")));//filename1为请求后台的普通参数;属性     
			reqEntity.addPart("type", new StringBody(type, Charset.forName("utf-8")));//filename1为请求后台的普通参数;属性     
			reqEntity.addPart("subject", new StringBody(subject, Charset.forName("utf-8")));//filename1为请求后台的普通参数;属性     
			reqEntity.addPart("text", new StringBody(text, Charset.forName("utf-8")));//filename1为请求后台的普通参数;属性     
			
			httppost.setEntity(reqEntity);  
			
			HttpResponse response = httpclient.execute(httppost);  
			int statusCode = response.getStatusLine().getStatusCode();  
			if(statusCode == HttpStatus.SC_OK){  
				System.out.println("服务器正常响应.....");  
				HttpEntity resEntity = response.getEntity();  
				System.out.println(EntityUtils.toString(resEntity));//httpclient自带的工具类读取返回数据  
				System.out.println(resEntity.getContent());     
				EntityUtils.consume(resEntity);  
			}  
		} catch (ParseException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		} finally {  
			try {   
				httpclient.getConnectionManager().shutdown();   
			} catch (Exception ignore) {  
				
			}  
		}  
	}  
}