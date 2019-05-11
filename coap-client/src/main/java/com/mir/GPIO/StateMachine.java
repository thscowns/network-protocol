package com.mir.GPIO;
import com.pi4j.wiringpi.SoftPwm;
import Global.Global;

public class StateMachine extends Thread {
	/*��������� GPIO ����
	 * 1. ��������� Pin_NUMBER ����
	 * 2. softPwmCreate ��������� Dimming�� ���� 0 �ּ� 100 �ִ�
	 * 3. Server�κ��� ���� ���� ���� 
	 */
	private String control;
	private int power;
	final int PIN_NUMBER = 1;
	public StateMachine(String control,String Power) {

		this.control = control;
		this.power = Integer.parseInt(Power);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		//dimming �� ���� ���ϱ� 0~ 100����
		//Fill in here()
		SoftPwm.softPwmCreate(PIN_NUMBER,0,100);

		System.out.println("=========");
		System.out.println("LED CONTROL");
		System.out.println("Control = " + control);
		System.out.println("DIMMING =" + power);
		System.out.println("=========");

		

		if (control.equals("ON")) {
			//dimming �� �ִ� �� �Է�
			//Fill in here()
			SoftPwm.softPwmWrite(PIN_NUMBER, power);
			System.out.println("PIN_NUMBER" + PIN_NUMBER);
			System.out.println("ON!");
		}

		else {
			//dimming �� �ּ� �� �Է�
			SoftPwm.softPwmWrite(PIN_NUMBER, 0);
			System.out.println("PIN_NUMBER" + PIN_NUMBER);
			System.out.println("OFF!");
		}
		Global.setState(control);

	}

}
