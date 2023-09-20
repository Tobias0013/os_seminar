## Task 3
This is a summary of task3
This task have 3 parts.

### Part 1)
This part is only a theoretical part to further understanding of problem.
### requirements:
- [X] Compile original code and run

### Questions
1. Can you detect in written output that too many chopsticks are used?

### Part 2)
### requirements:
- [X] Implement a version where a philosopher waits if a chopstick is already used by another philosopher
- [X] Each philosopher should first pick up the left and then the right chopstick
- [X] When running code it should be possible for a deadlock to occur
    - [ ] (optional) If it is hard to get a deadlock, modify the times in sleep

### Part 3)
### requirements:
- [X] Implement a deadlock free implementation

---

### Notes from deadlockSolution (part 1)

a deadlock occurs if I change time that the philosopher waits to pick up chopstick.

could do this with synchronized and use the original boolean array.

---

### Notes from solution (part2)
could do this with semaphores like in part 1, just added the `!chopstick[((n + 1) % 5)]` 
with semaphores in getLeftChopstick method.


"you could have solved this: to make every even philosopher pick up right first and every odd philosopher pick up left stick firs."


Mutual Exclusion (or Exclusive Control):
At least one resource must be non-shareable, meaning that only one process or thread can access it at a time.
This condition ensures that once a process acquires a resource, it has exclusive control over that resource.

Hold and Wait: (id did)
Processes must hold at least one resource and must be waiting to acquire additional resources that are currently
held by other processes. In other words, a process that is already holding some resources can request additional
resources while still holding its existing ones.

No Preemption:
Resources cannot be forcibly taken away from a process. Resources can only be released voluntarily by the process that holds them.
This condition ensures that a process cannot be interrupted and have its resources taken away, which could lead to inconsistency.

Circular Wait:
There must exist a circular chain of two or more processes, where each process is waiting for a resource held by the next process in the chain.
This circular chain creates a cycle of dependencies that prevents any process in the cycle from proceeding.