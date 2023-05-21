package ctci.chapter2

// Delete the middle node of a linked-list



object DeleteMiddleNode:
    extension(node: ListNode)
        def length: Int = 
            @annotation.tailrec
            def go(node: ListNode, acc: Int): Int =
                if (node == null) acc
                else go(node.next, acc + 1)
            go(node, 0)
                  


object Solution1:
    
    private def findMid(prev: ListNode, slow: ListNode, fast: ListNode): ListNode = 
        if (fast == null || fast.next == null) prev
        else findMid(slow, slow.next, fast.next.next)
    
    
    def deleteMiddle(head: ListNode): ListNode = 
        val beforeMid = findMid(null, head, head)
        if (beforeMid == null) null
        else 
            val mid = beforeMid.next
            beforeMid.next = mid.next
            head
        
    
