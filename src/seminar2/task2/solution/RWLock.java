package seminar2.task2.solution;

public class RWLock {

    private int reader_count = 0;
    private int writer_que = 0;
    private boolean writing = false;

    public RWLock(){
    }

    public synchronized void acquireRead(){
        try {
            while (writing || writer_que > 0){
                wait();
            }
            reader_count++;

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void acquireWrite(){
        writer_que++;

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
        writer_que--;
        writing = false;
        notifyAll();
    }

}
