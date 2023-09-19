package seminar2.task2.reentrantReadWriteLock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLock {

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    public RWLock(){
    }

    public void acquireRead(){
        lock.readLock().lock();
    }

    public void acquireWrite(){
        lock.writeLock().lock();
    }

    public void releaseRead(){
        lock.readLock().unlock();
    }

    public void releaseWrite(){
        lock.writeLock().unlock();
    }
}
