package ctci.chapter2


 

object RemoveNthFromEnd:
  extension(node: ListNode)
    def length: Int =
      @annotation.tailrec
      def go(node: ListNode, len: Int): Int =
        if (node == null) len
        else go(node.next, len + 1)
      go(node, 0)

    def removeNth(n: Int, pos: Int, len: Int): ListNode = 
      if (node == null)
          null
      else if (len - pos == n)
          node.next.removeNth(n, pos + 1, len)
      else
          ListNode(node.x, node.next.removeNth(n, pos + 1, len))
    

  def removeNthFromEnd(node: ListNode, n: Int): ListNode = 
      val len = node.length
      if (n > len) node
      else 
        node.removeNth(n, 0, len)