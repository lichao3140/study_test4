package com.lichao;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class UiCollectionTest extends UiAutomatorTestCase{

	public static void main(String[] args) {
		String jarName = "Demo";
		String testClass = "com.lichao.UiCollectionTest";
		String testName = "testInstance";
		String androidId = "7";
		new UiAutomatorHelper(jarName, testClass, testName, androidId);
	}
	
	/** 遍历文本选择子类  getChildByText*/
	public void testChild() throws UiObjectNotFoundException{
		UiCollection collection = new UiCollection(new UiSelector().className("android.widget.ListView"));
		//搜到所有的TextView
		UiSelector childPattern = new UiSelector().className("android.widget.TextView");
		String text = "360Log";
		UiObject log = collection.getChildByText(childPattern, text);
		log.click();
	}
	
	/** 遍历文本选择子类  getChildByDescription*/
	public void testDesc() throws UiObjectNotFoundException{
		UiCollection collection = new UiCollection(new UiSelector().resourceId("com.android.contacts:id/dialpad_huawei_container"));
		
		UiSelector childPattern = new UiSelector().className("android.widget.FrameLayout");
		String text = "5";
		UiObject five = collection.getChildByDescription(childPattern, text);
		five.click();	
	}
	
	/** getChildByInstance*/
	public void testInstance() throws UiObjectNotFoundException{
		UiCollection collection = new UiCollection(new UiSelector().resourceId("com.android.contacts:id/dialpad_huawei_container"));		
		UiSelector childPattern = new UiSelector().className("android.widget.FrameLayout");
		
		UiObject child1 = collection.getChildByInstance(childPattern, 0);
		UiObject child0 = collection.getChildByInstance(childPattern, 10);
		UiObject child00 = collection.getChildByInstance(childPattern, 10);
		UiObject child8 = collection.getChildByInstance(childPattern, 7);
		UiObject child6 = collection.getChildByInstance(childPattern, 5);
		
		child1.click();
		sleep(500);
		child0.click();
		sleep(500);
		child00.click();
		sleep(500);
		child8.click();
		sleep(500);
		child6.click();
		sleep(500);
	}
	
	/** 获取某种条件组件的数量*/
	public void testCount() {
		UiCollection collection = new UiCollection(new UiSelector().index(0));
		int textCount = collection.getChildCount(new UiSelector().className("android.widget.TextView"));
		System.out.println("TextView count:" + textCount);
		
	}
}
