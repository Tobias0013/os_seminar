# Seminar 2
This is a list of info/specs for the different tasks in seminar 2.

---

## Task 1
This is a summary of task 1.  
Note:  
Note that when running the code for the first time, you will get index out of bounds, this
due to that getPageNumber(), getPageOffset() and handlePageFault() is not implemented in
MemoryManager.class.


### requirements:
- [X] Download the code from Canvas and make sure that you can compile it
- [X] Implement getPageNumber()
- [X] Implement getPageOffset()
- [X] Implement the handlePageFault() method such that a page is loaded into a free frame in the
  physical memory in case of page fault
- [X] Run the application and testCaseOne that is supplied in the Seminar3.class. to verify your
  results.

---

## Task 1 Comments/Thoughts

This task was completed during lecture 7. But I had already started to work on this task.
#### I solved the code in handlePageFault() the same as in the lecture.

    private void handlePageFault(int pageNumber) {
    // Implement by student in task one
    // This is the simple case where we assume same size of physical and logical
    // memory
    // nextFreeFramePosition is used to point to next free frame position
    
            myNumberOfpageFaults++; // Needs to be here
    
            myPageTable[pageNumber] = myNextFreeFramePosition; // This is setting myPageTable[pageNumber] to the empty frame
            myNextFreeFramePosition++;
        }

The getPageNumber(), getPageOffset() I did different from the lecture.

#### Lecture solution:
Lecture solved the task with bit shifting.

	private int getPageNumber(int logicalAddress) {
		// Implement by student in task one
		//System.out.println("For the page number we get: ");
		//System.out.println(logicalAddress >> 8);
		return logicalAddress >> 8;

	}

	private int getPageOffset(int logicalAddress) {
		// Implement by student in task one

		return logicalAddress & 0b0000000011111111;
	}

#### My solution:
I solved the task by converting the logical address to a binary string.  
Then I split the binary string in half.  
Then I convert the two new binary strings to integers.

	private int getPageNumber(int logicalAddress) {
		// Implement by student in task one
		String bin = intToBin(logicalAddress);
		String[] split = splitMiddleString(bin);
		int pageNmr = binToInt(split[0]);
		return pageNmr;
	}

	private int getPageOffset(int logicalAddress) {
		// Implement by student in task one
		String bin = intToBin(logicalAddress);
		String[] split = splitMiddleString(bin);
		int pageOffset = binToInt(split[1]);
		return pageOffset;
	}

	//-------------------------added by me-------------------------

	private String[] splitMiddleString(String str){
		int mid = str.length() / 2;
		return new String[]{
				str.substring(0, mid),
				str.substring(mid)
		};
	}

	private String intToBin(int i){
		String bin = "";

		bin = Integer.toBinaryString(i);

		while (bin.length() < 16){
			bin = "0" + bin;
		}

		return bin;
	}

	private int binToInt(String bin){
		int decimal = Integer.parseInt(bin,2);
		return decimal;
	}

	//-------------------------------------------------------------

---

## Task 2
This is a summary of task 2.  
In this task you will have a lower number of frames in physical memory than pages.

Note:  
that depending on your solution, you might need to change parts of the supplied code, this is
allowed.

### requirements:
- [X] Use FIFO as a page replacement algorithm and implement the method handlePageFaultFIFO()
- [X] Run the testcaseTwo to verify your results.


### Questions
1. How many page faults will you get with 128 frames?  
   Number of page faults: 536  
   Expected: 536  
2. For 64?  
   Number of page faults: 759  
   Expected: 759  
3. For 32?  
   Number of page faults: 880  
   Expected: 880  

---

## Task 2 Comments/Thoughts
I solved this task by adding a new Queue as a class variable.  
A page is added when loaded into primary memory, when memory is full, 
and we need to swap a page the first one in the queue is the first one in there for the first one out. 

    private Queue<Integer> myPageQueue;

Then I created two new methods isRAMFull() and replacePage().  
isRAMFull() is pretty self-explanatory.

	private boolean isRAMFull(){
		int framesLoadedCount = 0;

		for (int page : myPageTable) {
			if (page != -1) {
				framesLoadedCount++;
			}
		}
		return framesLoadedCount == myNumberOfFrames;
	}

replacePage() replaces the next page in queue with the new one.

	private void replacePage(int pageNumber){
		// gets next page in queue
		int currentFirstSetPage = myPageQueue.poll();

		// sets myNextFreeFramePosition to the page that will get replaced
		myNextFreeFramePosition = myPageTable[currentFirstSetPage];

		// marks that the current first set page is not in physical memory
		myPageTable[currentFirstSetPage] = -1;

		// marks that the current page shall overwrite the old frame in physical memory
		myPageTable[pageNumber] = myNextFreeFramePosition;

		// adds new page number to the back of the queue
		myPageQueue.add(pageNumber);
	}

This is my code in the handlePageFaultFIFO() it is pretty self-explanatory.

	private void handlePageFaultFIFO(int pageNumber) {
		// Implement by student in task two
		// this solution allows different size of physical and logical memory
		// page replacement using FIFO
		// Note depending on your solution, you might need to change parts of the
		// supplied code, this is allowed.

		if (!isRAMFull()){
			handlePageFault(pageNumber);
			myPageQueue.add(pageNumber);
			return;
		}

		replacePage(pageNumber);
	}


## Task 3
This is a summary of task 3.

Note:  
that depending on your solution, you might need to change parts of the supplied code, this is
allowed.

### requirements:
- [X] Use LRU as a page replacement algorithm and implement the method handlePageFaultLRU()
- [X] Run the testcaseThree to verify your results.


### Questions
1. How many page faults will you get with 128 frames?  
   Number of page faults: 534  
   Expected: 534  
2. For 64?  
   Number of page faults: 753  
   Expected: 753  
3. For 32?  
   Number of page faults: 878  
   Expected: 878  

---

## Task 3 Comments/Thoughts
I solved this task by using the same Queue as in task2.  
A page is added when loaded into primary memory, when memory is full,
and we need to swap a page the first one in the queue is the first one in there for the first one out.  

    private Queue<Integer> myPageQueue;

But to implement LRU I also added this to readFromMemory() method.  
This makes it so that every time the page is used it gets added to the back of the queue,
it becomes the most recently used aka the furthest from getting swapped.

    if (myPageReplacementAlgorithm == Seminar3.LRU_PAGE_REPLACEMENT){
    // change pos of the most recently used page in queue
    myPageQueue.remove(pageNumber);
    myPageQueue.add(pageNumber);
    }

This is my code in the handlePageFaultLRU() it is pretty self-explanatory.

	private void handlePageFaultLRU(int pageNumber) {
		// Implement by student in task three
		// this solution allows different size of physical and logical memory
		// page replacement using LRU
		// Note depending on your solution, you might need to change parts of the
		// supplied code, this is allowed.

		if (!isRAMFull()){
			handlePageFault(pageNumber);
			myPageQueue.add(pageNumber);
			return;
		}

		replacePage(pageNumber);
	}

---

## Extra
This is extra stuff I want to do after completed the basic requirements.

### Will do:

### Will maybe do
- [ ] Refactor task1 to byte shifting

---









