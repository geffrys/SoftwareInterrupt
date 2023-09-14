
public class Main {
    public static void main(String[] args){
        MemorySpace memorySpace = new MemorySpace();
        String process1 = "process 1";
        System.out.println("Process 1 created.");
        String process2 = "process 2";
        System.out.println("Process 2 created.");
        Thread thread1 = new Thread(()->{
            try {
                Thread.sleep(2000);
                memorySpace.enter(process1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(()->{
            try {
                memorySpace.enter(process2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // an alternative flow of interruption
        Thread thread3 = new Thread(() -> {
            int counter = 10;
            while(counter >0 ){
                System.out.println("cpu time: " + counter);
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    System.out.println(e.getMessage());
                }
                counter--;
            }
            thread2.interrupt();
        });
        thread1.start();
        thread2.start();
        thread3.start();

    }
}

class MemorySpace {
    private boolean isAvailable;
    public MemorySpace() {
        isAvailable=true;
    }
    public void enter(String process) throws  InterruptedException {
        if (!isAvailable) {
            throw new InterruptedException(process+" are trying to enter critical section that is not available.");
        }
        if(Thread.interrupted()){
            isAvailable = true;
            throw new InterruptedException("process interrupted");
        }
        System.out.println(process + " has entered to critical section.");
        isAvailable = false;
    }
}

