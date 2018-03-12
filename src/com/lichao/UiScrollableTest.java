package com.lichao;

import android.os.Bundle;

import com.android.uiautomator.core.Configurator;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.core.UiWatcher;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class UiScrollableTest extends UiAutomatorTestCase{

	public static void main(String[] args) {
		String jarName = "Demo";
		String testClass = "com.lichao.UiScrollableTest";
		String testName = "testSetText";
		String androidId = "7";
		new UiAutomatorHelper(jarName, testClass, testName, androidId);
	}

	/**  快速滚动  */
	public void testFling() throws UiObjectNotFoundException{
		UiScrollable scrollable = new UiScrollable(new UiSelector().className("android.widget.ListView"));
		scrollable.flingBackward();//向上滚动
		sleep(3000);
		scrollable.flingForward();//向下滚动
		
		scrollable.flingToBeginning(5);//maxSwipes滚动次数
		scrollable.flingToEnd(5);//滚动5次到结尾
		
	}
	
	/**  滚动找到条件查找  getChildByText  --- true表示滚动 */
	public void testScroll() throws UiObjectNotFoundException{
		UiScrollable scrollable = new UiScrollable(new UiSelector().className("android.widget.ListView"));
		UiObject mq = scrollable.getChildByText(new UiSelector().className("android.widget.TextView"), "MQ", true);
		mq.click();
	}
	
	/**  滚动找到条件查找,设置滚动次数 */
	public void testSwipCount() throws UiObjectNotFoundException{
		UiScrollable scrollable = new UiScrollable(new UiSelector().className("android.widget.ListView"));
		UiObject textView = new UiScrollable(new UiSelector().text("MQ"));
		scrollable.setMaxSearchSwipes(2);//设置最大滚动次数，默认为30次
		scrollable.scrollIntoView(textView);
		textView.click();
	}
	
	/**  滚动区域设置 */
	public void testDeadZone() throws UiObjectNotFoundException{
		UiScrollable scrollable = new UiScrollable(new UiSelector().className("android.widget.ListView"));
		double dead = scrollable.getSwipeDeadZonePercentage();
		System.out.println("不滑动区域百分比:" + dead);
		scrollable.setSwipeDeadZonePercentage(0.15);//值越大，可滑动区域越小，0.5不滑动
		scrollable.scrollForward();
		
	}
	
	/**  快速滚动 */
	public void testScrollIntoView() throws UiObjectNotFoundException{
		UiScrollable scrollable = new UiScrollable(new UiSelector().className("android.widget.ListView"));
		UiSelector selector = new UiSelector().text("DCIM");
//		scrollable.scrollIntoView(selector);
		
		scrollable.scrollToBeginning(10);//快速移动到开始
		scrollable.scrollToBeginning(10, 5);
		scrollable.scrollToEnd(10);//快速移动到结尾
		scrollable.scrollToEnd(10, 5);
	}
	
	public void testVertial() throws UiObjectNotFoundException{
		UiScrollable scrollable = new UiScrollable(new UiSelector().className("android.support.v4.view.ViewPager"));
		scrollable.setAsHorizontalList();//水平
		scrollable.scrollBackward();
		sleep(2000);
		scrollable.scrollForward();
		sleep(2000);
		scrollable.setAsVerticalList();//垂直
		scrollable.scrollForward();
	}
	
	/**  监听器，监听测试时候被打断，来电，闹铃等 */
	public void testWatcher() throws UiObjectNotFoundException{
		//注册监听
		UiDevice.getInstance().registerWatcher("phone", new UiWatcher() {
			
			@Override
			public boolean checkForCondition() {
				
				
				return false;
			}
		});
		//执行测试
		UiObject vol = new UiObject(new UiSelector().className("android.widget.FrameLayout")
				.index(2)).getChild(new UiSelector().text("声音"));
		for(int i = 0; i < 20; i++){
			vol.clickAndWaitForNewWindow();
			sleep(2000);
			UiDevice.getInstance().pressBack();
			sleep(2000);
			System.out.println("循环次数:" + i);
		}
	}
	
	/** 滑动 */
	public void testAction(){
		long actionTimeout = Configurator.getInstance().getActionAcknowledgmentTimeout();
		System.out.println("默认延时时间:" + actionTimeout);
		
		int x = UiDevice.getInstance().getDisplayWidth();
		int y = UiDevice.getInstance().getDisplayHeight();
		UiDevice.getInstance().swipe(x-50, y/2, 50, y/2, 10);
		UiDevice.getInstance().swipe(x-50, y/2, 50, y/2, 10);
		
		Configurator.getInstance().setWaitForSelectorTimeout(1);//设置延时时间为1S
		sleep(2000);
		UiDevice.getInstance().swipe(50, y/2, x-50, y/2, 10);
		UiDevice.getInstance().swipe(50, y/2, x-50, y/2, 10);
		Configurator.getInstance().setWaitForSelectorTimeout(actionTimeout);//改变完成后要还原
		
	}
	
	/** 输入 */
	public void testKey() throws UiObjectNotFoundException{
		UiObject edit = new UiObject(new UiSelector().focused(true));
		edit.setText("lichao");
		
		sleep(2000);
		Configurator.getInstance().setKeyInjectionDelay(500);
		edit.setText("hello");
	}
	
	/** 滑动 */
	public void testsetScroll() throws UiObjectNotFoundException{
		UiScrollable view = new UiScrollable(new UiSelector().className("android.widget.ListView"));
		view.flingToBeginning(5);
		
		sleep(2000);
		Configurator.getInstance().setScrollAcknowledgmentTimeout(2000);
		view.flingToEnd(5);
	}
	
	/**  从命令行传入数据  */
	public void testCall() throws UiObjectNotFoundException{
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressHome();
		
		UiObject call = new UiObject(new UiSelector().text("拨号"));
		call.clickAndWaitForNewWindow();
		sleep(2000);
		
		Bundle b = getParams();
		String phone = (String)b.get("phone");//-e key value -e phone 10086
		for(int i=0; i<phone.length(); i++){
			String num = phone.charAt(i) + "";
			UiObject callnum = new UiObject(new UiSelector().text(num));
			callnum.click();
			sleep(1000);
		}
		
	}
	
	/**  清除应用数据  */
	public void testClear() throws UiObjectNotFoundException{
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressHome();
		
		Bundle b = getParams();
		String ctrl = (String) b.get("ctrl");// -e ctrl true
		boolean isClear = Boolean.valueOf(ctrl);
		if(isClear){
			//执行清理
			
		}else{
			//不执行清理
			System.out.println("不执行清理");
		}
		
		UiObject call = new UiObject(new UiSelector().text("浏览器"));
		call.clickAndWaitForNewWindow();
		sleep(2000);
		
	}
	
	public void testquicklyClick(){
		int x = UiDevice.getInstance().getDisplayWidth();
		int y = UiDevice.getInstance().getDisplayHeight();
		quickylyClick(2, x/2, y/2);
		sleep(2000);
		quickylyClick(2, x/2, y/2);
	}
	
	public void testSetText(){
		setTextKeyCode("MyisLichao3140");
	}
	
	/**
	 * 快速点击API
	 * @param num
	 * @param x
	 * @param y
	 */
	public void quickylyClick(int num, int x, int y){
		long timeout = Configurator.getInstance().getActionAcknowledgmentTimeout();
		System.out.println("timeout=" + timeout);
		Configurator.getInstance().setActionAcknowledgmentTimeout(50);
		for(int i=0; i<num; i++){
			UiDevice.getInstance().click(x, y);
		}
		Configurator.getInstance().setActionAcknowledgmentTimeout(timeout);
	}
	
	/**
	 * 快速输入
	 * @param input
	 */
	public void setTextKeyCode(String input){
		long timeout = Configurator.getInstance().getActionAcknowledgmentTimeout();
		Configurator.getInstance().setActionAcknowledgmentTimeout(0);
		for(int i=0; i<input.length(); i++){
			char c=input.charAt(i);
			if(c>=48 && c<=57){//数字
				UiDevice.getInstance().pressKeyCode(c-41);
			} else if(c>=97 && c<=122){//小写字母
				UiDevice.getInstance().pressKeyCode(c-68);
			} else if(c>=65 && c<=90){//大写字母
				UiDevice.getInstance().pressKeyCode(c-36, 1);
			}
		}
		Configurator.getInstance().setActionAcknowledgmentTimeout(timeout);
	}
}
