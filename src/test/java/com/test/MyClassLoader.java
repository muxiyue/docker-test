package com.test;

import java.net.URL;
import java.net.URLClassLoader;

public class MyClassLoader extends URLClassLoader {

	public MyClassLoader(URL[] urls) {
		super(urls);
	}

	// 打破双亲模式，保证自己的类会被自己的classloader加载
	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		Class c = findLoadedClass(name);
		if (c == null) {
			try {
				c=findClass(name);
			} catch (Exception e) {
			}
		}
		if(c==null){
			c=super.loadClass(name, resolve);
		}
		return c;
	}

}
