package com.cking.smsinterception;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;


public class SmsReceiver extends BroadcastReceiver{
	//方案1
		private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
		public void onReceive(Context context, Intent intent) {
			System.out.println("the message from another people haved received by SmessReceiver!");

			String sendNum = null;
			if (intent.getAction().equals(SMS_RECEIVED)) 
			{
				 Bundle bundle = intent.getExtras();
				 if (bundle != null) 
				 {
					 
					 Object[] pdus = (Object[]) bundle.get("pdus");
					 SmsMessage[] messages = new SmsMessage[pdus.length];
					 for (int i = 0; i < pdus.length; i++) 
					 {
						 messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
						 sendNum=messages[i].getOriginatingAddress();
						 System.out.println("the send num is"+messages[i].getOriginatingAddress());
					 }
					  for (SmsMessage message : messages)
					  {
						 
						   String msg = message.getMessageBody();
						   int length=msg.length();
						   
						   if(!SmsReceiver.parse(msg))						//普通短信流程
						   {
							   System.out.println("it is a common message!");
							   abortBroadcast ();
						   }
						   else 							   
						   {
							   String content=msg.toLowerCase().replaceAll(" ", "");//查询短信流程			
							   if("cxwz".equals(content)||"查询位置".equals(content)||"$CKING(LOC:QUERY)#E".equals(msg))
							   {
								   System.out.println("it is a Query message!");
								   Intent intent01=new Intent();
								   	intent01.putExtra("message", sendNum);				//获得短信发送者的号码
									intent01.setClass(context, QueryDataSaveService.class);
									context.startService(intent01);
								   
							   }
							   
							   else 															
								   
							   {
								   if((length>=7)&& msg.substring(0,7).equals("$CLDLOC") )//领航/位置/导航短信流程
								   {
								   	System.out.println("it is a Location message!");
								   	String msgCon=msg.substring(8);
								   	Intent intent02=new Intent();
								    SimpleDateFormat     sdf=new   SimpleDateFormat( "yyyyMMddHHmmss ");               
							        Date   now   =   new   Date(); 
							        String   test   =   sdf.format(now).toString(); 
							       	intent02.putExtra("datatime", test);
								   	intent02.putExtra("srcnum", sendNum);
								   	intent02.putExtra("message", msgCon);
									intent02.setClass(context, LocationDataSaveService.class);
									context.startService(intent02);
								   }
								   else						/***********注册相关指令****************/
								   {
									   if((length>=4) && "CMD".equals(msg.substring(0, 3)))
									   {
										   if((length>=8) && "TN4".equals(msg.substring(4, 7)))//设置呼叫中心号码
										   {											   //(CMD,TN4,4001156666)
											   String callNum03=msg.substring(8);
											   System.out.println(callNum03);	
											   Intent intent03=new Intent();
											   intent03.putExtra("callNumber03",callNum03);			
											   intent03.setClass(context, ParametersSetService.class);
											   context.startService(intent03);
											   
										   }
										   else if((length>=7) && "QTN".equals(msg.substring(4, 7)))//  后台查询(CMD,QTN) 
										   {											 
											   Intent intent04=new Intent();
											   intent04.putExtra("QuePraReq", "QuePraReq");	
											   intent04.setClass(context, ParametersSetService.class);
											   context.startService(intent04);
											   
											   
										   }
										   else if((length>=8) && "TNS".equals(msg.substring(4, 7)))		//服务器回复注册成功短信
										   {																//(CMD,TNS,4001156666,0000,02,00,0,0,00)
											   String[] strs=msg.split(",");
											   String callnum05=strs[2];
											   String servertype05=strs[4];
											 
											   Intent intent05=new Intent();
											   intent05.putExtra("callNumber05", callnum05);
											   intent05.putExtra("servertype05", servertype05);
											   intent05.setClass(context, ParametersSetService.class);
											   context.startService(intent05);
											   
										   }
										   else if((length>=8) && "TN3".equals(msg.substring(4, 7))) //修改服务类型
										   {														 //(CMD,TN3,02)
											   String str=msg.substring(8).trim();
											   Intent intent06=new Intent();
											   intent06.putExtra("modifyservertype", str);	
											   intent06.setClass(context, ParametersSetService.class);
											   context.startService(intent06);
										   }
										   else if((length>=7) && "TNC".equals(msg.substring(4, 7)))//取消服务注册
										   {														//(CMD,TNC)
											   Intent intent07=new Intent();
											   intent07.putExtra("cancelserver", "00");	
											   intent07.setClass(context, ParametersSetService.class);
											   context.startService(intent07);		   
										   }
										   else 										//设置短信中心号码
										   {											//(CMD,007,007,15220110571,0)
											  
											   String[] strs=msg.split(",");
											   String smscennum08=strs[3];
											   Intent intent08=new Intent();
											   intent08.putExtra("smscennum08", smscennum08);	
											   intent08.setClass(context, ParametersSetService.class);
											   context.startService(intent08);
											   
										   }
									   
									   
									   }
								   }
								   
								   
								   
							   }
	
							}
						  

					  }
				  }
				 
				 
		
			 }
			
		}
		public static  Boolean parse(String str)
		{
			int len=str.length();
			String content=str.toLowerCase().replaceAll(" ", "");
			 if("cxwz".equals(content)||"查询位置".equals(content)||"$CKING(LOC:QUERY)#E".equals(str))
				 			return true;

			 if((len>=10)&& str.substring(0,7).equals("$CLDLOC") &&  str.substring(len-2,len).equals("#E"))
					   		return true;
			 if((len>=4) && "CMD".equals(str.substring(0, 3)))
				 			return true;
			 return false;
				  
				
			
			
		}
		
		
		
		
		

	}






