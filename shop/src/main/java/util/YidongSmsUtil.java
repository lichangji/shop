
package util;

import org.smslib.IOutboundMessageNotification;
import org.smslib.Library;
import org.smslib.Message.MessageEncodings;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;


public class YidongSmsUtil {
	
	private  Service srv; //声明一个服务  
    private  SerialModemGateway gateway;  
    
    private static YidongSmsUtil instance = null;   
    private static Object lock = new Object(); 
	
    public static YidongSmsUtil getInstance() {   
        if(instance==null){  
            synchronized (lock) {  
                if(instance==null)  
                    instance = new YidongSmsUtil();  
           }  
        }  
        return instance;  
    }
    private YidongSmsUtil(){  
        try {  
           init();  
       } catch (Exception e) {  
           e.printStackTrace();  
       }  
    }
    
    public void init()throws Exception  
    { 
    	System.out.println(Library.getLibraryDescription());//获取类库描述  
        System.out.println("Version: " + Library.getLibraryVersion());//获取类库版本  
        srv = new Service(); //初始化服务  
        OutboundNotification outboundNotification = new OutboundNotification();  
        //115200是波特率，一般为9600。可以通过超级终端测试出来，声明网关 第一个参数为:网关ID，第二个是本机上短信猫的com口名称，第三是波特率，第四是短信猫生产厂商，第五设备的型号（可选）  
        gateway = new SerialModemGateway("modem."+PropertieUtil.loadPropertie("sms_yidong_com_index"),
				PropertieUtil.loadPropertie("sms_yidong_com"), Integer.parseInt(PropertieUtil.loadPropertie("sms_yidong_pote")), "wavecom", "17254");
       //不接受短信
        gateway.setSimPin(PropertieUtil.loadPropertie("yidong_pin"));
        gateway.setInbound(false);
		gateway.setOutbound(true);
		gateway.setOutboundNotification(outboundNotification);
		srv.addGateway(gateway);
		srv.startService();
		System.out.println("Modem Information:");
		System.out.println(" Manufacturer: " + gateway.getManufacturer()); 
		System.out.println(" Model: " + gateway.getModel()); 
		System.out.println(" Serial No: " + gateway.getSerialNo());
		System.out.println(" SIM IMSI: " + gateway.getImsi());
		System.out.println(" Signal Level: " + gateway.getSignalLevel() + "%");
		System.out.println(" Battery Level: " + gateway.getBatteryLevel() + "%");
		System.out.println();
	}
    public void close()throws Exception{
    	gateway.stopGateway(); 
    	srv.stopService(); 
    } 
    public Boolean sendSingleMessage(String PhoneNum,String Mesg) throws Exception {
    	OutboundMessage msg = new OutboundMessage(PhoneNum, Mesg);
    	msg.setEncoding(MessageEncodings.ENCUCS2);
    	Boolean result = srv.sendMessage(msg); 
    	return result; 
    }
			
    public class OutboundNotification implements IOutboundMessageNotification {
    	public void process(String gatewayId, OutboundMessage msg) {
    		System.out.println("Outbound handler called from Gateway: " + gatewayId); System.out.println(msg); 
    	} 
    }
}