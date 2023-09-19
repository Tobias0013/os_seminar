# Seminar 2

This is a list of info/specs for the different tasks in seminar 2.

This is what was stated regarding where I should/can do the solution:

`
"All modifications that you need to do for task 1 and 2, can be done in the class RWLock."
`

`
"All modifications that you need to do for task 3 can be done in the class Table"
`

## Task 1
This is a summary of task 1.

### requirements:
- [ ] Solve reader-writer problem
- [ ] A reader should wait only if a writer has locked the data
- [ ] A writer must wait if readers or writers are active
- [ ] Use java synchronized and wait/notify

### Questions
1. How can you see that we have an inconsistent state?
2. Try to formulate why we have this problem?
3. How can you identify the starvation problem?

---

## Task 2
This is a summary of task 2. 
This task have 2 parts.

### Part 1)
Builds on the requirements of task 1.
### requirements:
- [ ] Solve problem with starving writers
- [ ] A reader is not allowed to start if there are writers waiting for access to the data

### Part 2)
Does not build on any other tasks or parts.
### requirements:
- [ ] Use the built-in java library (ReentrantReadWriteLock) to solve reader-writer problem
- [ ] Set fairness to true

---

## Task 3
This is a summary of task3
This task have 3 parts.

### Part 1)
This part is only a theoretical part to further understanding of problem.
### requirements:
- [ ] Compile original code and run

### Questions
1. Can you detect in written output that too many chopsticks are used?

### Part 2)
### requirements:
- [ ] Implement a version where a philosopher waits if a chopstick is already used by another philosopher
- [ ] Each philosopher should first pick up the left and then the right chopstick
- [ ] When running code should a deadlock occur
  - [ ] (optional) If it is hard to get a deadlock, modify the times in sleep

### Part 3)
### requirements:
- [ ] Implement a deadlock free implementation

## Final observations (questions)
1. How did you implement the task?
2. Why did you solve it in the way you did it?
3. What differences in behaviour did you notice?




