
// //////////////////////////////////////////////////////////////////
// ORDERED COUNT OF CHARACTERS
// Count the number of occurrences of each character 
// in a String and return it as a (list of tuples).
// For empty lists, return an empty list

// example:
    // "abracadabra" => List((a, 5), (b, 2), (r, 2), (c, 1), (d, 1))

object SolutionOrderedCount {
    def orderedCount(str: String): List[(String, Int)] =
        str.distinct.map( char => (char, str.count(_ == char))).toList
}


// //////////////////////////////////////////////////////////////////
// FIND PIVOT INDEX

// Given an array of integers, calculate the pivot-index of this array
// - The pivot index is the index where the sum of the elements strictly
//  to the left of the index is equal to the sum of all the elements
//  strictly to the right

// EX:
    // INPUT = [1, 7, 3, 6, 5, 6]
    // OUTPUT = 3

object SolutionPivotIndex {
    import scala.annotation.tailrec
    def pivotIndex(nums: Array[Int]): Int = {
        @tailrec
        def pivotAcc(idx: Int, leftSum: Int, rightSum: Int): Int = {
            if (idx == nums.length)  -1
            else if (leftSum == rightSum - nums(idx))  idx
            else pivotAcc(idx + 1, leftSum + nums(idx), rightSum - nums(idx)) 
         }
         pivotAcc(0, 0, nums.sum)
    }
}

// //////////////////////////////////////////////////////////////////
// RUNNING SUM OF 1D ARRAY

// Given an array, we define a running sum of an array as:
    //  runningSum[i] = sum(array[0] ... array[i])

// ... return the running sum of a array of Ints

object SolutionRunningSum {
    def runningSum(xs: Array[Int]): Int = {
        if (xs.isEmpty)  xs.empty
        else xs.tail.scanLeft(xs.head)( _ + _ )
    }
}


// //////////////////////////////////////////////////////////////////
// ISOMORPHIC STRINGS
// 
// Given two strings s and t, determine if they are isomorphic.
// Two strings s and t are isomorphic if the characters in s can be replaced to get t.

// All occurrences of a character must be replaced with another character while preserving the order 
// of characters. No two characters may map to the same character, but a character may map to itself.

// Input: s = "egg", t = "add"
// Output: true

// Input: s = "foo", t = "bar"
// Output: false
// 
object SolutionIsomorphic {
        def isIsomorphic(s: String, t: String) = {
            val mapSValues = s.zipWithIndex.groupBy(_._1).mapValues(_.map(_._2)).toMap.values.toSet
            println(mapSValues) //Set(Vector(0), Vector(1, 2))
            val mapTValues = t.zipWithIndex.groupBy(_._1).mapValues(_.map(_._2)).toMap.values.toSet
            println(mapTValues) //Set(Vector(0), Vector(1, 2))
            mapSValues.equals(mapTValues)
        } 

        def isIsomorphic2(s: String, t: String): Boolean = {
            s.lazyZip(t).groupBy(_._1).forall(_._2.toSet.size == 1) &&
            s.lazyZip(t).groupBy(_._2).forall(_._2.toSet.size == 1)
        }
        // explanation:
            // If any char at string S has mapped to more than one char at string T
            // or any two chars at string S has mapped to a same char at string T, 
            // the two strings are not isomorphic      
}

// //////////////////////////////////////////////////////////////////
// IS SUBSEQUENCE
// 
// Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
// 
// A subsequence of a string is a new string that is formed from the original string by 
// deleting some (can be none) of the characters without disturbing the relative positions
// of the remaining characters. (i.e., "ace" is a subsequence of "abcde" while "aec" is not).
// 
// Input: s = "abc", t = "ahbgdc"
// Output: true
// 
// Input: s = "axc", t = "ahbgdc"
// Output: false

object Solution {
    def isSubsequence(s: String, t: String): Boolean = {
        if (s.isEmpty) true
        else if (t.isEmpty) false
        else if (s.head == t.head) isSubsequence(s.tail, t.tail)
        else isSubsequence(s, t.tail)
    }
}

// //////////////////////////////////////////////////////////////////
// MERGE TWO SORTED LISTS

// You are given the heads of two sorted linked lists list1 and list2.
// 
// Merge the two lists in a one sorted list. The list should be made by 
// splicing together the nodes of the first two lists.
// 
// Return the head of the merged linked list.

// Input: list1 = [1,2,4], list2 = [1,3,4]
// Output: [1,1,2,3,4,4]

object SolutionMergeSort {
    class ListNode(var _x: Int = 0) {
        var next: ListNode = null
        var x: Int = _x
    }

    def mergeTwoLists1(list1: ListNode, list2: ListNode): ListNode = {
        (list1, list2) match {
            case (null, list2) => list2
            case (list1, null) => list1
            case (list1, list2) if (list1.x <= list2.x) => {
                list1.next = mergeTwoLists(list1.next, list2)
                list1
            }
            case (list1, list2) if (list1.x > list2.x) => {
                list2.next = mergeTwoLists(list1, list2.next)
                list2
            }
        }
    }

    def mergeTwoLists2(l1: ListNode, l2: ListNode): ListNode = {
      if (l1 == null) l2
      else if (l2 == null) l1
      else if (l1.x < l2.x) {
        l1.next = mergeTwoLists(l1.next, l2)
        l1
      } else {
        l2.next = mergeTwoLists(l1, l2.next)
        l2
      }
    }

    def mergeTwoLists(l1: ListNode, l2: ListNode): ListNode = {
        val l3 = ListNode(0, null)
        def mergeTwoListsRecursive(l1: ListNode, l2: ListNode, l3:ListNode): Unit = {
            (l1, l2) match {
                case (l1: ListNode, l2: ListNode) => 
                    if (l1.x < l2.x) {  
                        l3.next = l1
                        mergeTwoListsRecursive(l1.next, l2, l1)
                    }
                    else { 
                        l3.next = l2
                        mergeTwoListsRecursive(l1, l2.next, l2)
                    }
                case (l1: ListNode, null) => l3.next = l1  // if l2 is empty merge the remaining of l1
                case (null, l2: ListNode) => l3.next = l2  // if l1 is empty merge the remaining of l2
                case (null, null) => // if both l1 and l2 are empty do nothing
            }
        }
        mergeTwoListsRecursive(l1,l2,l3)
        l3.next
    }
}

// //////////////////////////////////////////////////////////////////
// REVERSE LINKED LIST
// 
// Given the head of a singly linked list, reverse the list, and return the reversed list.
// 
// Input: head = [1,2,3,4,5]
// Output: [5,4,3,2,1]
// 
// Input: head = [1,2]
// Output: [2,1]

object SolutionReversed {
    class ListNode(var _x: Int = 0) {
        var next: ListNode = null
        var x: Int = _x
    }

    import scala.annotation.tailrec
    def reverseList(head: ListNode): ListNode = {
        @tailrec
        def loop(head: ListNode, result: ListNode) : ListNode = {
            head match {
                case null => result
                case h => {
                    val current = h.next
                    h.next = result
                    loop(current, h)
                }
            }
        }
                
        loop(head, null)
    }

    def reverseList2(head: ListNode): ListNode = {
        
        def loop(list: ListNode, prev: ListNode): ListNode = {
            list match {
                case currHead: ListNode =>
                  val next: ListNode = currHead.next
                  currHead.next = prev
                  loop(next, currHead)
                case _ =>
                  prev;
            }
        }
        
        loop(head, null)
    }
}

// //////////////////////////////////////////////////////////////////
// MIDDLE OF THE LINKED LIST
// 
// Given the head of a singly linked list, return the middle node of the linked list.
// 
// If there are two middle nodes, return the second middle node.
// 
// Input: head = [1,2,3,4,5]
// Output: [3,4,5]
// Explanation: The middle node of the list is node 3.
// 
// Input: head = [1,2,3,4,5,6]
// Output: [3,4,5]
// Explanation: The middle node of the list is node 3.

object SolutionMiddle {
    class ListNode(var _x: Int = 0) {
        var next: ListNode = null
        var x: Int = _x
    }

    def middleNode(head: ListNode): ListNode = {
        def findMid(slowhead: ListNode, fasthead: ListNode): ListNode = {
            if (fasthead.next == null) slowhead
            else if (fasthead.next.next==null) slowhead.next
            else findMid(slowhead.next, fasthead.next.next)
        }
        findMid(head, head)
    }

    def middleNode2(head: ListNode): ListNode = {
        @scala.annotation.tailrec
        def middleRec(curr:ListNode, middle:ListNode, step: Int): ListNode = {
            if(curr != null){
                val newMiddle = if(step % 2 == 0) middle else middle.next
                middleRec(curr.next, newMiddle, step+1)
            } else{
                middle
            }
        }
        middleRec(head, head, 0)
    }
    
}

// //////////////////////////////////////////////////////////////////
// LINKED LIST CYCLE II
// 
// Given the head of a linked list, return the node where the cycle begins. If there is no cycle, return null.
//
// There is a cycle in a linked list if there is some node in the list that can be reached again 
// by continuously following the next pointer. Internally, pos is used to denote the index of the 
// node that tail's next pointer is connected to (0-indexed). It is -1 if there is no cycle.
// Note that pos is not passed as a parameter.
// 
// Do not modify the linked list.

// Input: head = [3,2,0,-4], pos = 1
// Output: tail connects to node index 1
// Explanation: There is a cycle in the linked list, where tail connects to the second node.
// 
// Input: head = [1,2], pos = 0
// Output: tail connects to node index 0
// Explanation: There is a cycle in the linked list, where tail connects to the first node.
// 
//Input: head = [1], pos = -1
// Output: no cycle
// Explanation: There is no cycle in the linked list.

object SolutionCycle {
    class ListNode(var _x: Int = 0) {
        var next: ListNode = null
        var x: Int = _x
    }
    
    import scala.annotation.tailrec
    @tailrec
    def recurse(node: ListNode, table: Map[ListNode, Boolean]): ListNode = {
        Option(node) match {
            case None => null
            case Some(n) => {
                if (table.get(n).isDefined) n
                else recurse(n.next, table + (n -> true))
            }
        }
        recurse(head, Map()) 
    }
}

