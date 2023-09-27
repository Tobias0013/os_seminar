
first in first out (last set first out) code logic:
1. if myRam[](myPageTable[] has lower than myNumberOfFrames pages loaded into physical memory) is not full  
    1.1. increment nextFreeFramePosition  
    1.2. sets the next free frame to be in page table.
2. else  
    2.1. check what was the las set frame/(page) in myPageTable[]  
    2.2. replace the old myPageTable[] reference with the new one  





I need to do:
a way to check if the myRAM[] is full
    check if x number of pages are loaded into physical memory
        where x is the size number of frames



a way to keep track of the oldest set value in myPageTable.
    needs to know which one is set but in reverse order

solution one: (maybe there is a better way)
have a var that starts at 0 (because 0 will be the first set first (the oldest variable set first)
    then make it count up untill reached myNumberOfFrames then start over.

solution two:
create new queue pageFIFO
    when page is loaded the pageNumber of the page is added to the queue

when physical memory is full I take a page number from the queue "remove"/replace it with the new
    and sets the next free frame to be in page table

Replace loaded page:
Overwrite the old loaded frame to match the new page
set old page in myPageTable[] to -1 bcs it is no longer in physical memory
set new page in myPageTable[] to where it is bcs its now in physical memory

