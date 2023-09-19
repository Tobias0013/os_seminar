package seminar2.task2.reentrant;

import java.util.concurrent.locks.ReentrantLock;

public class RWLock {

    private final ReentrantLock lock = new ReentrantLock(true);

    public RWLock(){
    }

    public void acquireRead(){
        lock.lock();
    }

    public void acquireWrite(){
        lock.lock();
    }

    public void releaseRead(){
        lock.unlock();
    }

    public void releaseWrite(){
        lock.unlock();
    }

}
