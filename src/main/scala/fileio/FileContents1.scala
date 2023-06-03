package fileio.filecontents1


import java.io.File
import scala.io.Source

object FileContents1:

    def fileContainsQuestion(file: File): Boolean =
        val source = Source.fromFile(file)
        try 
            source.getLines().toSeq.headOption.map { line =>
                line.trim.endsWith("?")    
            }.getOrElse(false)
        finally 
            source.close()



    def emphasizeFileContents(file: File): String =
        val source = Source.fromFile(file)
        try 
            source.getLines().toSeq.headOption.map { line =>
                line.trim.toUpperCase    
            }.getOrElse("")
        finally 
            source.close()



    def withFileContents[A](file: File, fn: String => A, default: A): A =
        val source = Source.fromFile(file)
        try 
            source.getLines().toSeq.headOption.map { line =>
                fn(line)   
            }.getOrElse(default)
        finally 
            source.close()


    // The loan pattern
    def withFileContentsLoan[A](file: File, default: A)(fn: String => A): A =
        val source = Source.fromFile(file)
        try 
            source.getLines().toSeq.headOption.map { line =>
                fn(line)   
            }.getOrElse(default)
        finally 
            source.close()
    




