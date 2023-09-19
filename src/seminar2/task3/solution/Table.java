package seminar2.task3.solution;

import java.util.concurrent.Semaphore;

public class Table { // This is with deadlock

	private int nbrOfChopsticks;
	private boolean chopstick[]; // true if chopstick[i] is available

	private Semaphore[] forks;

	public Table(int nbrOfSticks) {
		nbrOfChopsticks = nbrOfSticks;
		chopstick = new boolean[nbrOfChopsticks];
		for (int i = 0; i < nbrOfChopsticks; i++) {
			chopstick[i] = true;
		}

		//added
		forks = new Semaphore[nbrOfSticks];
		for (int i = 0; i < nbrOfChopsticks; i++) {
			forks[i] = new Semaphore(1);
		}
	}

	public void getLeftChopstick(int n) {
		//chopstick[n] = false;
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
		//chopstick[pos] = false;
		try {
			forks[pos].acquire();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void releaseLeftChopstick(int n) {
		//chopstick[n] = true;
		forks[n].release();
	}

	public void releaseRightChopstick(int n) {
		int pos = n + 1;
		if (pos == nbrOfChopsticks)
			pos = 0;
		//chopstick[pos] = true;
		forks[pos].release();

	}
}
