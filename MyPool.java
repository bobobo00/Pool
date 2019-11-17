package Pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @ClassName PoolTest01
 * @Description TODO
 * @Auther danni
 * @Date 2019/11/16 14:48]
 * @Version 1.0
 **/

public class MyPool {
    private static BlockingQueue<Runnable> pqueue=new ArrayBlockingQueue<Runnable>(5);
    private int maxThreads=5;
    private  int currentThreads=0;
    private Thread[] works=new Thread[maxThreads];



    private static class Work extends Thread{
        BlockingQueue<Runnable> pqueue;
        public Work( BlockingQueue<Runnable> pqueue){
            this.pqueue=pqueue;
        }
        public void run(){
            while(!isInterrupted()){
                try {
                    Runnable cmd=pqueue.take();
                    cmd.run();
                    if(pqueue.isEmpty()){
                        break;
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    public void execute(Runnable run) throws InterruptedException {
        if(currentThreads==maxThreads){
            pqueue.put(run);
        }else {
            Work work=new Work(pqueue);
            works[currentThreads++]=work;
            work.start();
            pqueue.put(run);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        MyPool pool=new MyPool();
        pool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("第一次");
            }
        });

        pool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("第二次");
            }
        });

        pool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("第三次");
            }
        });

        pool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("第四次");
            }
        });

        pool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("第五次");
            }
        });

        pool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("第六次");
            }
        });
        System.out.println(pool.currentThreads);
    }
}
