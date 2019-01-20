package com.study.design.mode.samples;

/**
 * 
 * @Description: java中提供的观察者设计模式
 * @author leeSamll
 * @date 2018年11月25日
 *
 */
import java.util.Observable;
import java.util.Observer;

public class ObserverSample {

	public static void main(String[] args) {
		
		//创建主题
		Observable subject1 = new Observable() {
			//通知观察者变化的数据data
			public synchronized void notifyObservers(Object data) {
				//设置 java.util.Observable.changed = true表示发生了改变
				setChanged();
				//调用父类的notifyObservers方法通知观察者发生变化 
				//调用链java.util.Observable.notifyObservers(Object)->java.util.Observer.update(Observable, Object)
				super.notifyObservers(data);
			}
		};

		//添加观察者
		subject1.addObserver(new Observer() {
			//主题回调观察者的update方法通知改变
			@Override
			public void update(Observable o, Object arg) {
				System.out.println("观察者1收到通知被更新了..." + arg);
			}
		});

		//添加观察者
		subject1.addObserver(new Observer() {
			//主题回调观察者的update方法通知改变
			@Override
			public void update(Observable o, Object arg) {
				System.out.println("观察者2收到通知被更新了..." + arg);
			}
		});

		//通知改变
		subject1.notifyObservers("change1");
		subject1.notifyObservers("change2");
	}
}
