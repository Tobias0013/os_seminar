# (This is personal notes)

## Task 1
This is a summary of task 1.

### requirements:
- [X] Solve reader-writer problem
- [X] A reader should wait only if a writer has locked the data
- [X] A writer must wait if readers or writers are active
- [X] Use java synchronized and wait/notify

### Questions
1. How can you see that we have an inconsistent state?
2. Try to formulate why we have this problem?
3. How can you identify the starvation problem?

---
### Notes from solution 
solved using synchronized and wait/notify in RWLlock class.

keeps track of how many readers are reading
keep track of if there is a writer writing (mutual exclusion)

if reader wants to read
check if writer write
then
read

if writer want to write (mutual exclusion)
check if there are any readers that are reading
check if other writer writes
then
write

Problem:
writer starving - writer only write in the end because reader gets priority
why reader priority:
* Readers are allowed to read concurrently unless there is an active writer.
* Writers are given exclusive access to the resource, and they wait if there are active readers or another writer.

---
### Notes from alt1 (alternative solution 1)
(This kinda goes in to task 2)

solved using synchronized and wait/notify in Data class.

Added synchronized to read and write func in Data class
This ensures that only one process can access the Data class at a time.

does not keep track of how many readers reading
does not keep track if writer is writing

Problems:
does not appear that i have the same problems as in sol1,
no apparent starvation of writers.

This is because I do not prioritize the readers. 
I just limit it to one thread that can enter the Data obj. 