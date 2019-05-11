package com.tipw.coap.resource;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONException;
import org.json.JSONObject;

import com.tipw.global.*;

//observer
import org.eclipse.californium.core.coap.CoAP.Type;

public class ConnectResource extends CoapResource {

	
	/*
	 * Start Connect Resource
	 * 
	 * 1. Apply "connect"resource through Constructor 
	 * 2. Post Handler
	 * 3. Add Resources 
	 * 
	 */ 
	
	
	//1. Apply "connect"resource through Constructor 
	public ConnectResource(String name) {
		super(name);
	}
	
	

	@Override
	public void handlePOST(CoapExchange exchange) {
		/*
		 * 2. Post Handler
			2-1. JSON Parsing requested value from Device(Client)
			2-2. Make a Response value with JSONObject
			2-3. Response Values to Client
			2-4. Requested value Save into Data Structure
		 * 
		 */
		
		
		try {
			//2-1. JSON Parsing requested value from Device(Client)
			
			String id, state, mode, power;
			JSONObject parsedObject = new JSONObject(exchange.getRequestText().toString());
			
			// *() Fill in here 
			// From Client, JSONObject key "DeviceID" Parsing & save local variable id 
			id=parsedObject.getString("DeviceID");
			// From Client, JSONObject key "State" Parsing & save local variable state
			state=parsedObject.getString("State");
			// From Client, JSONObject key "Mode" Parsing & save local variable mode
			mode=parsedObject.getString("Mode");
			power = parsedObject.getString("Power");
			
			System.out.println("CONNECT DEVICE");
			System.out.println("=========");
			System.out.println("DEVICE ID:" + id);
			System.out.println("DEVICE State:" + state);
			System.out.println("DEVICE Mode:" + mode);
			System.out.println("DEVICE Mode:" + power);
			System.out.println("=========");
			
	
			

			
			//2-2. Make a Response value with JSONObject
			JSONObject json = new JSONObject();
			json.put("Response", "sucess");
			String payload = json.toString();
			
			//2-3. Response Values to Client
			// * () Fill in here
			exchange.respond(ResponseCode.CONTENT, payload , MediaTypeRegistry.APPLICATION_JSON);
			
			
			
			//2-4. Requested value Save into Data Structure
			DeviceInfo dev_info = new DeviceInfo(id, state, mode,"100");
			Global.device_list.put(id, dev_info);
			

			
			/*
			 * 	3.Add Resources 
			 		3-1. Add DeviceID Resource to Report Resource
			 		3-2. Add DeviceID Resource to Control Resource
			 */
		
			//3-1. Add DeviceID Resource to Report Resource : report/Device1
			// * Fill in here
		    Global.getReport_resource().add(new ReportResource(id));
			
			//3-2. Add DeviceID Resource to Control Resource : control/Device1
			// * Fill in here
		    Global.getControl_resource().add(new ControlResource(id));
		    
		    
		    if(mode.equals("push")) {
		    	ObserveResource obs_resource =new ObserveResource(id);
		    	Global.getObserve_resource().add(obs_resource);
		    	dev_info.setResource(obs_resource);
		    }
		    
			
		} catch (JSONException e) {
			exchange.respond(ResponseCode.BAD_REQUEST, "Wrong Access");
		}
	

	}
}
