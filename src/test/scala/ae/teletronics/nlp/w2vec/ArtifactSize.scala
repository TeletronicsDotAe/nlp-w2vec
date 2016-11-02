package ae.teletronics.nlp.w2vec

import java.io.File

import org.apache.commons.io.FileUtils
import org.junit.Test

import scala.io.Source

/**
  * Created by trym on 01-11-2016.
  */
class ArtifactSize {

  @Test
  def showSize(): Unit = {
    val input = new File("C:\\temp\\percipio-artifacts.txt")
//    val input = new File("C:\\temp\\artifacts.txt")
    Source
      .fromFile(input)
      .getLines()
      .map(s => {
        val dependency = s.substring(s.indexOf("- ") + 2)
        val gatvd = dependency.split(':')
        //          println(dependency)
        val groupDir = gatvd(0).replace('.', '\\')
        val artifact = gatvd(1)
        val at = gatvd(2)
        val version = gatvd(3)
        val f = new File(s"C:\\Users\\trym\\.m2\\repository\\${groupDir}\\${artifact}\\${version}\\${artifact}-${version}.${at}")
        (f.getName, f.length())
      })
      .toList
      .sortBy(_._2)
      .map(t => (t._1, FileUtils.byteCountToDisplaySize(t._2)))
      .foreach(println)
  }

  @Test
  def sumSize(): Unit = {
    val input = new File("C:\\temp\\percipio-artifacts.txt")
//    val input = new File("C:\\temp\\artifacts.txt")
    val s = Source
      .fromFile(input)
      .getLines()
      .map(s => {
        val dependency = s.substring(s.indexOf("- ") + 2)
        val gatvd = dependency.split(':')
        //          println(dependency)
        if (gatvd.length < 4) println(dependency)
        val groupDir = gatvd(0).replace('.', '\\')
        val artifact = gatvd(1)
        val at = gatvd(2)
        val version = gatvd(3)
        val f = new File(s"C:\\Users\\trym\\.m2\\repository\\${groupDir}\\${artifact}\\${version}\\${artifact}-${version}.${at}")
        f.length()
      })
      .foldLeft[Long](0)(((r,c) => r+c))
      println(FileUtils.byteCountToDisplaySize(s))
  }

}
