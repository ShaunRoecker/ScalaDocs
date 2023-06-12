package functional.typeclasses


object TypeClasses3:

    trait Combiner[T]:
        def combine(a: T, b: T): T



    object CombinerInstances:

        given Combiner[Int] with
            def combine(a: Int, b: Int): Int = a + b

        
        implicit object stringCombo extends Combiner[String]:
            def combine(a: String, b: String): String = s"${a}++${b}"

        
        implicit object mapCombo extends Combiner[Map[String, Int]]:
            override def combine(map1: Map[String, Int], map2: Map[String, Int]): Map[String, Int] =
                map1.foldLeft(map2) { case (map, (key, value)) =>
                    map.get(key) match
                    case Some(newvalue) => map + (key -> (value + newvalue))
                    case None => map + (key -> value)    
                }
    


    object TypeClassInterface3:
        
        def join[T: Combiner](a: T, b: T): T =
            implicitly[Combiner[T]].combine(a, b)


        extension[T](a: T)
            def joinWith(b: T)(implicit ev: Combiner[T]): T =
                ev.combine(a, b)
        
        implicit class CombineOps[T](a: T)(implicit ev: Combiner[T]) {
            def join(b: T): T = ev.combine(a, b)
        }
                


    object PreMapCombo:

        def combineMaps[K, V](map1: Map[K, V], map2: Map[K, V])(using ev: Combiner[V]): Map[K, V] =
            map1.foldLeft(map2) { case (map, (key, value)) =>
                map.get(key) match
                    case Some(newValue) => map + (key -> (ev.combine(newValue, value)))
                    case None => map + (key -> value)
            }

        // another way
        def combineMaps2[K, V: Combiner](map1: Map[K, V], map2: Map[K, V]): Map[K, V] =
            val ev = implicitly[Combiner[V]]
            map1.foldLeft(map2) { case (map, (key, value)) =>
                map.get(key) match
                    case Some(newValue) => map + (key -> (ev.combine(newValue, value)))
                    case None => map + (key -> value)
            }





  






