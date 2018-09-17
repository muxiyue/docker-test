package lock;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

class MutextNode {
    Object item;

    MutextNode next;

    Mutex lock = new Mutex();

    // 每一个节点都持有一个锁
    MutextNode(Object x, MutextNode n) {
        item = x;
        next = n;
    }
}

class List {
    protected MutextNode head;
    // 指向列表的头
    // 使用Java的synchronized保护head域
    // (我们当然可以使用Mutex，但是这儿似乎没有这样做的必要

    protected synchronized MutextNode getHead() {
        return head;
    }

    boolean search(Object x) throws InterruptedException {
        MutextNode p = getHead();
        if (p == null)
            return false;
        // (这儿可以更加紧凑，但是为了演示的清楚，各种情况都分别进行处理)
        p.lock.acquire();
        // Prime loop by acquiring first lock.
        // (If the acquire fails due to
        // interrupt, the method will throw
        // InterruptedException now,
        // so there is no need for any
        // further cleanup.)
        for (; ; ) {
            if (x.equals(p.item)) {
                p.lock.release();
                // 释放当前节点的锁
                return true;
            }
            else {
                MutextNode nextp = p.next;
                if (nextp == null) {
                    p.lock.release();
                    // 释放最后持有的锁
                    return false;
                }
                else {
                    try {
                        nextp.lock.acquire();
                        // 在释放当前锁之前获取下一个节点的锁
                    }
                    catch (InterruptedException ex) {
                        p.lock.release();
                        // 如果获取失败，也释放当前的锁 throw ex;
                    }
                    p.lock.release();
                    // 释放上个节点的锁，现在已经持有新的锁了
                    p = nextp;
                }
            }
        }
    }

    synchronized void add(Object x) {
        // 使用synchronized保护head域
        head = new MutextNode(x, head);
    }
    // ... other similar traversal and update methods ...

    public static void main(String[] args) throws InterruptedException {
        List list = new List();
        list.add("aaa");
        System.out.println(list.search("aaa"));
    }
}