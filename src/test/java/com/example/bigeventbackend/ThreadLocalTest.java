//package com.example.bigeventbackend;
//
//import org.junit.jupiter.api.Test;
//
//public class ThreadLocalTest {
//
//    @Test
//    public void testThreadLocal(){
//        ThreadLocal tl = new ThreadLocal();
//
//
//        new Thread(()->{
//            tl.set("萧炎");
//            System.out.println(Thread.currentThread().getName()+":"+tl.get());
//            System.out.println(Thread.currentThread().getName()+":"+tl.get());
//            System.out.println(Thread.currentThread().getName()+":"+tl.get());
//        },"Blue").start();
//
//        new Thread(()->{
//            tl.set("药尘");
//            System.out.println(Thread.currentThread().getName()+":"+tl.get());
//            System.out.println(Thread.currentThread().getName()+":"+tl.get());
//            System.out.println(Thread.currentThread().getName()+":"+tl.get());
//        },"Green").start();
//    }
//}
