class Worker extends Thread {

    public Worker(String name){
        super(name);
    }

    public void run(){
        try{
            System.out.println("Hello from: " + this.getName());
            Thread.sleep(1000);
            System.out.println("Bye from: " + this.getName());
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}

public class CreatingThreads_1 {
    public static void main(String [] args) {
        
        Thread t1 = new Worker("Alice");
        Thread t2 = new Worker("Bob");
        Thread t3 = new Worker("Charles");

        t1.start();
        t2.start();
        t3.start();
    }
}