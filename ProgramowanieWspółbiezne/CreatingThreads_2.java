class Singer extends Thread {

    private int number;

    public Singer(int i){
        super("Singer-" + i); // thread name
        this.number = i;
    }

    public void run(){
        try{

            int next = number - 1;

            if(next >= 0){
                System.out.println(
                    number + " bottles of beer on the wall, " +
                    number + " bottles of beer. Take one down and pass it around, " +
                    next + " bottles of beer on the wall."
                );
            }

            Thread.sleep(100);

        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}

public class CreatingThreads_2 {

    public static void main(String [] args) {

        Singer[] threads = new Singer[100];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Singer(100 - i);
        }

        for (Thread t : threads) {
            t.start();
        }
    }
}