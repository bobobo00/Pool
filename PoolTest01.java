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

public class PoolTest01 {
    private static BlockingQueue<Runnable> pqueue=new ArrayBlockingQueue<Runnable>(5);
    private Thread[] works=new Thread[5];

    public PoolTest01(){
        for (int i = 0; i <5 ; i++) {
            works[i]=new Work(pqueue);
            works[i].start();
        }
    }

    private class Work extends Thread{
        BlockingQueue<Runnable> pqueue;
        public Work( BlockingQueue<Runnable> pqueue){
            this.pqueue=pqueue;
        }
        public void run(){
            while(!isInterrupted()){
                try {
                    Runnable cmd=pqueue.take();
                    cmd.run();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    public void execute(Runnable run) throws InterruptedException {
        pqueue.put(run);
    }

    public void stop(){
        for (int i = 0; i <5 ; i++) {
            works[i].interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PoolTest01 pool=new PoolTest01();
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
        pool.stop();

    }
}
