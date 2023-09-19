
## Task 2
This is a summary of task 2.
This task have 2 parts.

### Part 1)
Builds on the requirements of task 1.
### requirements:
- [X] Solve reader-writer problem
- [X] Solve problem with starving writers
- [X] A reader is not allowed to start if there are writers waiting for access to the data

---

### Part 2)
Does not build on any other tasks or parts.
### requirements:
- [ ] Use the built-in java library (ReentrantReadWriteLock) to solve reader-writer problem
- [ ] Set fairness to true

---
### Notes from solution (part 1)
solved by adding writer_que in RWLock
writer_que:
increment when writer is waiting
decrement when writer is finished

by making a reader wait if there is a reader in que then the reader gets priority


Problem:
(apparently) reader starving (I only noticed that it happen rarely. But I can se that it might happen)
why writer priority:
All reader are supposed to wait if there are any writer in que.

---
### Notes for part 2

I have a reentrantReadWriteLock that uses reentrantReadWriteLock to make fairness. (This is what the task wants.)