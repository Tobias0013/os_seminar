package seminar2.task1.solution;

public class RWLock {

    private int reader_count = 0;
    private boolean writing = false;

    public RWLock(){
    }

    public synchronized void acquireRead(){
        try {
            while (writing){
                wait();
            }
            reader_count++;

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void acquireWrite(){
        try {
            while (reader_count > 0 || writing){
                wait();
            }
            writing = true;

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void releaseRead(){
        reader_count--;
        if (reader_count == 0){
            notifyAll();
        }
    }

    public synchronized void releaseWrite(){
        writing = false;
        notifyAll();
    }

}
