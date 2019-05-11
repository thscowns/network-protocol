package com.mir.Service;


import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONException;
import org.json.JSONObject;

import Global.Global;

public class Connect {
	/*
	 * Connect 
	 * 
	 */
	
	//1-1. Californium���� �����ϴ� CoapClient ��ü ���� �� ����
	//fill () in here
	CoapClient client=new CoapClient();
	
	

	public void session() throws JSONException {
		
		//1-2. ������ CoapServer URI �ּ� ����
		// �ǽ��Ͽ��� CoAP Server�� connect URI �ϼ��ϱ� 
		// Fill () in  here
		String uri =  "coap://" + Global.serverIP + ":" + Global.coapServerPort + "/" + "connect";		
		System.out.println("ConnectURI "+uri);
		client.setURI(uri);
		
		//1-3. CoapServer���� ���� JSONObject �޽��� ����
		JSONObject json = new JSONObject();
		json.put("DeviceID", Global.SYSTEMID);
		json.put("State", "off");
		json.put("Mode", Global.Mode);
		json.put("Power", "0");
		
		
		//1-4. JSON�� String���� ��ȯ�Ͽ� Server���� PUT���� ���� �� Response�� �α� ���
		String payload = json.toString();
		System.out.println("Connect Request:"+payload);
		
		//CoapResponse�� ����ؼ� Server�� post �޼ҵ带 ����ؼ� ������ Response �� �ޱ� (client ��ü ���)
		// Fill () in  here
		CoapResponse resp1 = client.post(payload, MediaTypeRegistry.APPLICATION_JSON);
		System.out.println("Connect Response:"+resp1.getResponseText());
        
		
		//2. Run CoAP Client Report URI 
		com.mir.Service.Report report = new com.mir.Service.Report();
		report.start();
		
		//1. Mode�� ���� ���� 
		if (Global.Mode.equals("pull")) {
			com.mir.Service.Control control = new com.mir.Service.Control();
			control.start();
		}
		
		else {
			com.mir.Service.Observe observe=new com.mir.Service.Observe();
			observe.start();
		}


	}
	

}
