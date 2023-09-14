public class Main {
    public static void main(String[] args) throws InterruptedException {
        MemorySpace memorySpace = new MemorySpace();
        String process1 = "process1";
        System.out.println("Process 1 created");
        String process2 = "process2";
        System.out.println("Process 2 created");
        Thread thread1 = new Thread(()->{
            try {
                memorySpace.enter(process1);
            } catch (NotAuthorizedException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(()->{
            try {
                memorySpace.enter(process2);
            } catch (NotAuthorizedException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
        thread1.interrupt();
    }
}

class MemorySpace {

    private boolean isAvailable;
    public MemorySpace() {
        isAvailable=true;
    }
    public void enter(String process) throws NotAuthorizedException, InterruptedException {
        if (!isAvailable) {
            System.out.println(process + " is trying to enter critical section that is not available");
            throw new NotAuthorizedException("Not authorized to enter critical section");
        }
        if(Thread.interrupted()){
            System.out.println(process + " is trying to enter critical section but was interrupted");
            throw new InterruptedException("Interrupted while waiting to enter critical section");
        }
        System.out.println(process + " entered critical section");
        isAvailable = false;
    }

}

class NotAuthorizedException extends Exception {
    public NotAuthorizedException(String s) {
        super("Not authorized to enter critical section");
    }
}

