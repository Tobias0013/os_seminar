package seminar2.task3.solution;

public class Table { // This is without deadlock

	private int nbrOfChopsticks;
	private boolean chopstick[]; // true if chopstick[i] is available

	public Table(int nbrOfSticks) {
		nbrOfChopsticks = nbrOfSticks;
		chopstick = new boolean[nbrOfChopsticks];
		for (int i = 0; i < nbrOfChopsticks; i++) {
			chopstick[i] = true;
		}
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
	}

	public synchronized void releaseLeftChopstick(int n) {
		chopstick[n] = true;
		notifyAll();
	}

	public synchronized void releaseRightChopstick(int n) {
		int pos = n + 1;
		if (pos == nbrOfChopsticks)
			pos = 0;
		chopstick[pos] = true;
		notifyAll();
	}
}
