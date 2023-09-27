# Seminar 2
This is a list of info/specs for the different tasks in seminar 2.

### The virtual memory we will use has the following specification:

1. 256 entries in the page table
2. Page and frame size is equal to 256 bytes
3. 256 frames in the physical memory (task 1)
4. The pagefile is located in BACKING_STORE.bin. The code for reading from this file is already
   fixed in the given java code
5. The integer values representing the logical addresses are in addresses.txt  
   (logical addresses are what the cpu thinks the adress is)

256 = 2^8

Page table is 256 entries long  
Page frame size is 256 bytes (2^8 bytes) (frame space = 8 bit)  
256 frames in physical memory (task 1) (meaning 1 frame is 1 byte)

Page number - a "page number" refers to a numerical identifier or index that is used to identify
a specific page of memory within a process's virtual address space or within physical memory


---

## Task 1
This is a summary of task 1.  
Note:  
Note that when running the code for the first time, you will get index out of bounds, this
due to that getPageNumber(), getPageOffset() and handlePageFault() is not implemented in
MemoryManager.class.


### requirements:
- [ ] Download the code from Canvas and make sure that you can compile it
- [ ] Implement the handlePageFault() method such that a page is loaded into a free frame in the
  physical memory in case of page fault
- [ ] Run the application and testCaseOne that is supplied in the Seminar3.class. to verify your
  results.

---

## Task 2
This is a summary of task 1.  
In this task you will have a lower number of frames in physical memory than pages.  

Note:  
that depending on your solution, you might need to change parts of the supplied code, this is
allowed.

### requirements:
- [ ] Use FIFO as a page replacement algorithm and implement the method handlePageFaultFIFO()
- [ ] Run the testcaseTwo to verify your results.


### Questions
1. How many page faults will you get with 128 frames?
2. For 64?
3. For 32?

---

## Task 1
This is a summary of task 1.  

Note:  
that depending on your solution, you might need to change parts of the supplied code, this is
allowed.

### requirements:
- [ ] Implement the least recently used page replacement algorithm
- [ ] Run the testcaseThree to verify your results.


### Questions
1. How many page faults will you get with 128 frames?
2. For 64?
3. For 32?

---