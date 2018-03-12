package com.lichao;

import android.graphics.Point;
import android.view.KeyEvent;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class TestObject extends UiAutomatorTestCase {
	
	public static void main(String[] args) {
		String jarName = "TestObject";
		String testClass = "com.lichao.TestObject";
		String testName = "testGesture";
		String androidId = "7";
		new UiAutomatorHelper(jarName, testClass, testName, androidId);
	}
	
	/** 点击和长按  */
	public void testClick() throws UiObjectNotFoundException {
		UiObject stor = new UiObject(new UiSelector().resourceId("com.miui.home:id/icon_icon"));
		stor.click();//点击
		stor.clickAndWaitForNewWindow();//点击窗口等待
		stor.clickBottomRight();//点击右下角
		
		stor.longClick();//长按
		UiDevice.getInstance().swipe(533, 612, 535, 615, 300);//实现长按效果，步长要长
	}
	
	/** 拖拽   */
	public void testDrag() throws UiObjectNotFoundException{
		UiObject object1 = new UiObject(new UiSelector().text("相册"));
		UiObject object2 = new UiObject(new UiSelector().text("联系人"));
		
		object2.dragTo(350, 1705-500, 10);
		object2.dragTo(object1, 30);//要合并,拖拽速度要慢		
	}
	
	/** 清除文本   */
	public void testSetText() throws UiObjectNotFoundException{
//		UiObject edit = new UiObject(new UiSelector().resourceId("com.android.mms:id/embedded_text_editor"));
//		edit.setText("hello lichao");
//		sleep(2000);
//		edit.clearTextField();//长按删除，在输入号码的编辑框会出错
		
		UiObject recipient = new UiObject(new UiSelector().resourceId("com.android.mms:id/recipients_viewer_head"));
		UiObject rec = new UiObject(new UiSelector().text("收信人"));
		recipient.setText("lichao");
		sleep(1000);
		//将光标移动到行尾，使用backspace删除
		while (!rec.exists()) {
			UiDevice.getInstance().pressKeyCode(KeyEvent.KEYCODE_MOVE_END);
			UiDevice.getInstance().pressKeyCode(KeyEvent.KEYCODE_DEL);
		}
	}
	
	/** 获取对象的属性     */
	public void testGet() throws UiObjectNotFoundException {
		UiObject r = new UiObject(new UiSelector().resourceId("com.android.mms:id/recipients_viewer_head"));
		String rec = r.getText();
		System.out.println("HINT:" + rec);
		System.out.println("CLASS:" + r.getClassName());
		System.out.println("PACKET_NAME:" + r.getPackageName());
		System.out.println("RECT:" + r.getBounds().left);
//		assertEquals("收信人", rec);//断元
	}
	
	/** 获取子类  父类  */
	public void testNode() throws UiObjectNotFoundException{
		UiObject down = new UiObject(new UiSelector().className("android.widget.FrameLayout").index(5));
		
		//子类
		UiObject download = down.getChild(new UiSelector().resourceId("com.android.fileexplorer:id/file_owner"));
		download.click();
	}
	
	/** 获取开关值  */
	public void testIs() throws UiObjectNotFoundException{
		UiObject wlan = new UiObject(new UiSelector().resourceId("android:id/checkbox"));
		
		if(!wlan.isChecked()){//把WIFI开关打开
			wlan.click();
		}
	}
	
	/** 手势操作    */
	public void testGesture() throws UiObjectNotFoundException{
		UiObject object = new UiObject(new UiSelector().resourceId("com.miui.gallery:id/photoview"));
		//单手指
//		object.pinchIn(80, 20);//缩小
//		sleep(2000);
//		object.pinchOut(80, 20);//放大
		
		//两个手指
		Point startPoint1, startPoint2, endPoint1, endPoint2;
		startPoint1 = new Point();
		startPoint2 = new Point();
		endPoint1 = new Point();
		endPoint2 = new Point();
		
		startPoint1.x = 157; startPoint1.y = 183;
		startPoint2.x = 122; startPoint2.y = 455;
		
		endPoint1.x = 948; endPoint1.y = 195;
		endPoint2.x = 930; endPoint2.y = 493;
		object.performTwoPointerGesture(startPoint1, startPoint2, endPoint1, endPoint2, 50);
	}
	
	/** 判断是否存在    */
	public void testExist(){
		
	}
}
