package seminar3.solution;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Queue;

public class MemoryManager {

	private int myNumberOfPages;
	private int myPageSize; // In bytes
	private int myNumberOfFrames;
	private int[] myPageTable; // -1 if page is not in physical memory
	private byte[] myRAM; // physical memory RAM
	private RandomAccessFile myPageFile;
	private int myNextFreeFramePosition = 0;
	private int myNumberOfpageFaults = 0;
	private int myPageReplacementAlgorithm = 0;

	// added by me
	private Queue<Integer> myPageQueue;

	public MemoryManager(int numberOfPages, int pageSize, int numberOfFrames, String pageFile,
			int pageReplacementAlgorithm) {

		myNumberOfPages = numberOfPages;
		myPageSize = pageSize;
		myNumberOfFrames = numberOfFrames;
		myPageReplacementAlgorithm = pageReplacementAlgorithm;

		initPageTable();
		myRAM = new byte[myNumberOfFrames * myPageSize];

		try {

			myPageFile = new RandomAccessFile(pageFile, "r");

		} catch (FileNotFoundException ex) {
			System.out.println("Can't open page file: " + ex.getMessage());
		}

		// added by me
		if (myPageReplacementAlgorithm > 0){
			myPageQueue = new LinkedList<>();
		}
	}

	private void initPageTable() {
		myPageTable = new int[myNumberOfPages];
		for (int n = 0; n < myNumberOfPages; n++) {
			myPageTable[n] = -1;
		}
	}

	public byte readFromMemory(int logicalAddress) {
		int pageNumber = getPageNumber(logicalAddress);
		int offset = getPageOffset(logicalAddress);

		if (myPageTable[pageNumber] == -1) {
			pageFault(pageNumber);
		}

		// added by me
		if (myPageReplacementAlgorithm == Seminar3.LRU_PAGE_REPLACEMENT){
			// change pos of the most recently used page in queue
			myPageQueue.remove(pageNumber);
			myPageQueue.add(pageNumber);
		}

		int frame = myPageTable[pageNumber];
		int physicalAddress = frame * myPageSize + offset; // frame * myPageSize + b
		byte data = myRAM[physicalAddress];

		System.out.print("Virtual address: " + logicalAddress);
		System.out.print(" Physical address: " + physicalAddress);
		System.out.println(" Value: " + data);

		return data;
	}

	private int getPageNumber(int logicalAddress) {
		// Implement by student in task one

		String bin = intToBinString(logicalAddress);
		String[] split = splitString(bin);
		return binStringToInt(split[0]);
	}

	private int getPageOffset(int logicalAddress) {
		// Implement by student in task one

		String bin = intToBinString(logicalAddress);
		String[] split = splitString(bin);
		return binStringToInt(split[1]);
	}

	private void pageFault(int pageNumber) {
		// added by me
		myNumberOfpageFaults++;

		if (myPageReplacementAlgorithm == Seminar3.NO_PAGE_REPLACEMENT)
			handlePageFault(pageNumber);

		if (myPageReplacementAlgorithm == Seminar3.FIFO_PAGE_REPLACEMENT)
			handlePageFaultFIFO(pageNumber);

		if (myPageReplacementAlgorithm == Seminar3.LRU_PAGE_REPLACEMENT)
			handlePageFaultLRU(pageNumber);

		readFromPageFileToMemory(pageNumber);
	}

	private void readFromPageFileToMemory(int pageNumber) { // läser in alla "offsets" / hela pagen in i myRam (fysiska minnet)
		try {
			int frame = myPageTable[pageNumber];
			myPageFile.seek(pageNumber * myPageSize);
			for (int b = 0; b < myPageSize; b++) {
				myRAM[frame * myPageSize + b] = myPageFile.readByte();
			}
			int x = 0;
		} catch (IOException ex) {

		}
	}

	public int getNumberOfPageFaults() {
		return myNumberOfpageFaults;
	}

	private void handlePageFault(int pageNumber) {
		// Implement by student in task one
		// This is the simple case where we assume same size of physical and logical
		// memory
		// nextFreeFramePosition is used to point to next free frame position

		myPageTable[pageNumber] = myNextFreeFramePosition;
		myNextFreeFramePosition++;
	}

	private void handlePageFaultFIFO(int pageNumber) {
		// Implement by student in task two
		// this solution allows different size of physical and logical memory
		// page replacement using FIFO
		// Note depending on your solution, you might need to change parts of the
		// supplied code, this is allowed.

		pageReplacement(pageNumber);
	}

	private void handlePageFaultLRU(int pageNumber) {
		// Implement by student in task three
		// this solution allows different size of physical and logical memory
		// page replacement using LRU
		// Note depending on your solution, you might need to change parts of the
		// supplied code, this is allowed.

		pageReplacement(pageNumber);
	}

	//------------------below methods added by me------------------
	private String[] splitString(String str){
		int mid = str.length() / 2;
		return new String[]{
				str.substring(0, mid),
				str.substring(mid)
		};
	}

	private String intToBinString(int i){
		String bin = Integer.toBinaryString(i);

		while (bin.length() < 16){
			bin = "0" + bin;
		}
		return bin;
	}

	private int binStringToInt(String bin){
		return Integer.parseInt(bin,2);
	}

	private void pageReplacement(int pageNumber){
		int framesLoadedCount = 0; // want to do better

		// Test if works in other handlePageFault [could be a local class variable]
		// Checks if myPageTable[] has loaded in (int)myNumberOfFrames pages into memory
		for (int page : myPageTable) {
			if (page != -1) {
				framesLoadedCount++;
			}
		}

		if (framesLoadedCount != myNumberOfFrames) { // kanske moste vara (myNumberOfFrames - 1)
			myPageTable[pageNumber] = myNextFreeFramePosition; // This is setting myPageTable[pageNumber] to the empty frame
			myPageQueue.add(pageNumber);
			myNextFreeFramePosition++;
			return;
		}

		// gets the first set page
		int currentFirstSetPage = myPageQueue.poll();

		// sets myNextFreeFramePosition to the first set page(that is about to be removed)
		myNextFreeFramePosition = myPageTable[currentFirstSetPage];

		// marks that the current first set page is not in physical memory
		myPageTable[currentFirstSetPage] = -1;

		// marks that the current page shall overwrite the old frame in physical memory
		myPageTable[pageNumber] = myNextFreeFramePosition;

		// adds new page number to the back of the queue
		myPageQueue.add(pageNumber);
	}

}
