package algorithms.sorting.bubble


object BubbleSort:

    extension(array: Array[Int])
        // mutable
        def bubbleSort(): Unit = 
            for (k <- 1 until array.length; j <- 0 until array.length - 1)
                if (array(j) > array(j + 1))
                    val temp = array(j)
                    array(j) = array(j + 1)
                    array(j + 1) = temp


        // with idiomatic for comprehension
        def bubbleSort2(): Unit = 
            for 
                k <- 1 until array.length 
                j <- 0 until array.length - 1
                if (array(j) > array(j + 1))
            do
                val temp = array(j)
                array(j) = array(j + 1)
                array(j + 1) = temp


        // optimize by sorting until array.length - k
        def bubbleSort3(): Unit = 
            for (k <- 1 until array.length; j <- 0 until array.length - k)
                (array(j), array(j + 1)) match
                    case (x, y) if x > y => 
                        array(j) = y
                        array(j + 1) = x
                    case _ =>
        

        def bubbleSort4(): Unit = 
            for 
                k <- 1 until array.length
                j <- 0 until array.length - k
                if (array(j) > array(j + 1))
            do (array(j), array(j + 1)) match
                    case (x, y) => 
                        array(j) = y
                        array(j + 1) = x

                

// sudo
// bubbleSort(array)
//     n = length(array)
//     for (k = 1 to n - 1)
//       for (j = 0 until n - 1)
//         if (array[j] > array[j + 1])
//           swap(array, j, j + 1)

