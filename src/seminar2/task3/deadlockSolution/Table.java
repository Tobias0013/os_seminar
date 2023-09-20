package seminar2.task3.deadlockSolution;

import java.util.concurrent.Semaphore;

public class Table { // This is with deadlock

	private int nbrOfChopsticks;
	private Semaphore[] forks;

	public Table(int nbrOfSticks) {
		forks = new Semaphore[nbrOfSticks];
		for (int i = 0; i < nbrOfChopsticks; i++) {
			forks[i] = new Semaphore(1);
		}
	}

	public void getLeftChopstick(int n) {
		try {
			forks[n].acquire();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void getRightChopstick(int n) {
		int pos = n + 1;
		if (pos == nbrOfChopsticks)
			pos = 0;
		try {
			forks[pos].acquire();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void releaseLeftChopstick(int n) {
		forks[n].release();
	}

	public void releaseRightChopstick(int n) {
		int pos = n + 1;
		if (pos == nbrOfChopsticks)
			pos = 0;
		forks[pos].release();

	}
}
