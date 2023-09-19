package seminar2.task3.nod;

public class Table { // This is "going to be" without deadlock :D

	private int nbrOfChopsticks;
	private boolean chopstick[]; // true if chopstick[i] is available

	//private Semaphore[] forks;

	public Table(int nbrOfSticks) {
		nbrOfChopsticks = nbrOfSticks;
		chopstick = new boolean[nbrOfChopsticks];
		for (int i = 0; i < nbrOfChopsticks; i++) {
			chopstick[i] = true;
		}
		/*
		//added
		forks = new Semaphore[nbrOfSticks];
		for (int i = 0; i < nbrOfChopsticks; i++) {
			forks[i] = new Semaphore(1);
		}

		 */
	}

	public synchronized void getLeftChopstick(int n) {
		while (!chopstick[n] || !chopstick[((n + 1) % 5)]){
			try {
				wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		chopstick[n] = false;
		/*
		try {
			forks[n].acquire();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		 */
	}

	public synchronized void getRightChopstick(int n) {
		int pos = n + 1;
		if (pos == nbrOfChopsticks)
			pos = 0;
		while (!chopstick[pos]){
			try {
				wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		chopstick[pos] = false;
		/*
		try {
			forks[pos].acquire();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		 */
	}

	public synchronized void releaseLeftChopstick(int n) {
		chopstick[n] = true;
		notifyAll();
		//forks[n].release();
	}

	public synchronized void releaseRightChopstick(int n) {
		int pos = n + 1;
		if (pos == nbrOfChopsticks)
			pos = 0;
		chopstick[pos] = true;
		notifyAll();
		//forks[pos].release();

	}
}
