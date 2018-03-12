package com.lichao;

import java.io.File;

import android.graphics.Rect;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.Surface;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class Test1 extends UiAutomatorTestCase {
	
	public static void main(String[] args) {
		String jarName = "Demo";
		String testClass = "com.lichao.Test1";
		String testName = "testBrowse";
		String androidId = "7";
		new UiAutomatorHelper(jarName, testClass, testName, androidId);
	}
	
	public void testBrowse() throws Exception{
		UiDevice.getInstance().sleep();
		sleep(2000);
		if (!UiDevice.getInstance().isScreenOn()) {
			UiDevice.getInstance().wakeUp();//点亮
		}
		sleep(2000);
		//解锁 [0,1184][720,1280] [304,1213][416,1251]
		UiDevice.getInstance().swipe(360, 1220, 360, 800, 20);
		UiObject browserObject = new UiObject(new UiSelector().text("浏览器"));
		browserObject.clickAndWaitForNewWindow();
		sleep(2000);
		UiObject textObject = new UiObject(new UiSelector().resourceId("com.android.browser:id/search_hint"));
		textObject.click();
		UiObject editObject = new UiObject(new UiSelector().className("android.widget.EditText"));
		editObject.click();
		editObject.setText("www.baidu.com");
		UiDevice.getInstance().pressEnter();
		sleep(3000);
		UiDevice.getInstance().setOrientationLeft();
		sleep(2000);
		UiDevice.getInstance().takeScreenshot(new File("/sdcard/browse.png"));
	}
	
	/**  按键API  */
	public void testPress() {
//		UiDevice.getInstance().pressMenu();//按菜单键
		UiDevice.getInstance().pressKeyCode(KeyEvent.KEYCODE_A);//小写a
		UiDevice.getInstance().pressKeyCode(KeyEvent.KEYCODE_A, 1);//大写A
	}
	
	/**  获取坐标和坐标点击  */
	public void testClick() throws UiObjectNotFoundException {
//		UiDevice.getInstance().click(300, 720);
		//点击屏幕的中点
//		int h = UiDevice.getInstance().getDisplayHeight();
//		int w = UiDevice.getInstance().getDisplayWidth();
//		UiDevice.getInstance().click(h/2, w/2);
		//获取矩形区域
		UiObject appShop = new UiObject(new UiSelector().resourceId("com.miui.home:id/icon_container"));
		Rect r = appShop.getBounds();
		int x0 = r.left;
		int y0 = r.top;
		int x1 = r.right;
		int y1 = r.bottom;
		
		int centerx = r.centerX();
		int centery = r.centerY();
		
		System.out.println("[" + x0 + "," + y0 + "]");//左上
		System.out.println("[" + x1 + "," + y1 + "]");//右下
		System.out.println("[" + centerx + "," + centery + "]");//中点
	}
	
	/**  拖拽和滑动  */
	public void dragAndSwipe() {
		//[196,649][352,829]
		//steps步长：设置拖拽的速度，越大拖拽越慢
		int startX, startY, endX, endY, steps;
		startX = (352 - 196)/2 + 196;
		startY = (829 - 649)/2 + 649;
		endX = startX;
		endY = startY - 500;
		steps = 10;
		UiDevice.getInstance().drag(startX, startY, endX, endY, steps);
		
//		int h = UiDevice.getInstance().getDisplayHeight();
//		int w = UiDevice.getInstance().getDisplayWidth();
//		
//		UiDevice.getInstance().swipe(w-50, h/2, 50, h/2, 10);//往右边滑动
		
		//画一个手势
//		Point p1 = new Point();
//		Point p2 = new Point();
//		Point p3 = new Point();
//		Point p4 = new Point();
//		
//		p1.x = 277; p1.y = 318;
//		p2.x = 746; p2.y = 335;
//		p3.x = 784; p3.y = 814;
//		p4.x = 221; p4.y = 840;
//		
//		Point[] pp = {p1, p2, p3, p4};
//		UiDevice.getInstance().swipe(pp, 50);
		
	}
	
	/** 旋转屏幕   */
	public void testOrientation() throws RemoteException {
		if(UiDevice.getInstance().isNaturalOrientation()){//判断是否自然状态
			UiDevice.getInstance().setOrientationLeft();//向左转
		}
		
//		int a = UiDevice.getInstance().getDisplayRotation();//取到屏幕方向状态
//		if (a == Surface.ROTATION_0){//0度
//			
//		} else if(a == Surface.ROTATION_90) {
//			
//		} else if(a == Surface.ROTATION_180) {
//			
//		} else if(a == Surface.ROTATION_270) {
//			
//		}
	}
	
	/** 灭屏和唤醒   */
	public void testWakeupAndSleep() throws RemoteException {
		if(UiDevice.getInstance().isScreenOn()){
			UiDevice.getInstance().sleep();
		}
	}
	
	/** 截图   */
	public void testScreen() {
		UiDevice.getInstance().takeScreenshot(new File("/sdcard/testscreen.png"));
	}
	
	/** 等待空闲  */
	public void testIdle() {
		//[368,1090][524,1270]
		UiDevice.getInstance().click(440, 1180);
		//默认为10s,测试若20s无响应则报错
		UiDevice.getInstance().waitForIdle(20000);
	}
	
	/** 获取当前的包名  */
	public void testGetPackage() {
		String packageName = UiDevice.getInstance().getCurrentPackageName();
		System.out.println(packageName);
		
		UiDevice.getInstance().openNotification();//打开通知栏
		UiDevice.getInstance().openQuickSettings();//打开快速设置
		
		UiDevice.getInstance().dumpWindowHierarchy("notification.xml");//当前界面的布局信息
	}
	
	
}
