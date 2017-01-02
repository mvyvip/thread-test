package com.lt.thread.test04;

/**
 * 业务整体需要使用完整的synchronized，保持业务的原子性
 * 原子性就是说一个操作不可以被中途cpu暂停然后调度, 即不能被中断, 要不就执行完, 要不就不执行. 如果一个操作是原子性的, 那么在多线程环境下, 就不会出现变量被修改等奇怪的问题.
 */
public class DirtyRead {
    private String username = "admin";
    private String password = "123";

    public synchronized void setValue(String username, String password){
        this.username = username;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.password = password;

        System.out.println("setValue最终结果：username = " + username + " , password = " + password);
    }

    public void getValue(){
        System.out.println("getValue方法得到：username = " + this.username + " , password = " + this.password);
    }

    public static void main(String[] args) throws Exception{

        final DirtyRead dr = new DirtyRead();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                dr.setValue("z3", "456");
            }
        });
        t1.start();
        Thread.sleep(1000);

        /**
         * t1设置了值  然后sleep   再来getValue 会取到错误的值 想要正确的 方法上面都加 synchronized 修饰
         */
        dr.getValue();
    }

}
