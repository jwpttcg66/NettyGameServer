package com.snowcattle.game.common.util;

public class ThreadUtil {

	public static String getThreadTree(){
		ThreadGroup root = Thread.currentThread().getThreadGroup();
		while(root.getParent() != null){
			root = root.getParent();
		}
		StringBuffer buffer = new StringBuffer();

		buffer.append(root.toString()).append("\r");
		visit(root, 1, buffer);
		return buffer.toString();
	}

	public static void visit(ThreadGroup group, int level, StringBuffer buffer){
		int numThreads = group.activeCount();
		Thread[] threads = new Thread[numThreads*2];
		numThreads = group.enumerate(threads, false);
		for(int i=0; i<numThreads; i++){
			Thread t = threads[i];
			for(int j=0; j<level; j++){
				buffer.append("  ");
			}
			buffer.append(t.toString()).append("\r");
		}

		int numGroups = group.activeGroupCount();
		ThreadGroup[] groups = new ThreadGroup[numGroups*2];
		numGroups = group.enumerate(groups, false);
		for(int i=0; i<numGroups; i++){
			for(int j=0; j<level; j++){
				buffer.append("  ");
			}
			buffer.append(groups[i].toString()).append("\r");
			visit(groups[i], level+1, buffer);
		}
	}

	public static Thread findThread(String name){
		return findThread(getThreadRoot(), name);
	}

	public static Thread findThread(ThreadGroup group, String name){
		int numThreads = group.activeCount();
		Thread[] threads = new Thread[numThreads*2];
		numThreads = group.enumerate(threads, false);
		for(int i=0; i<numThreads; i++){
			Thread t = threads[i];
			// 只比较前缀
			if(name.indexOf(t.getName()) == 0){
				return t;
			}
		}

		int numGroups = group.activeGroupCount();
		ThreadGroup[] groups = new ThreadGroup[numGroups*2];
		numGroups = group.enumerate(groups, false);
		for(int i=0; i<numGroups; i++){
			Thread t = findThread(groups[i], name);
			if(t != null){
				return t;
			}
		}

		return null;

	}

	public static ThreadGroup getThreadRoot(){
		ThreadGroup root = Thread.currentThread().getThreadGroup();
		while(root.getParent() != null){
			root = root.getParent();
		}
		return root;
	}


	public static String getThreadStack(Thread t){
		StackTraceElement[] stacks = t.getStackTrace();
		StringBuffer buffer = new StringBuffer();
		for(StackTraceElement stack : stacks){
			String filename = stack.getFileName();
			if(filename == null){
				filename = "NULL";
			}
			String className = stack.getClassName();
			String methodName = stack.getMethodName();
			int line = stack.getLineNumber();
			buffer.append(String.format("%s.%s(%s:%d)\r", className, methodName, filename, line));
		}
		return buffer.toString();
	}

	public static int getThreadsCount(){
		// 已经包括子线程了
		return getThreadRoot().activeCount();
	}


//	public static void main(String[] args){
//		System.out.println(ThreadUtil.getThreadTree());
//
//		Thread t = ThreadUtil.findThread("main");
//
//		System.out.println(getThreadStack(t));
//
//		System.out.println(getThreadsCount());
//	}
}
