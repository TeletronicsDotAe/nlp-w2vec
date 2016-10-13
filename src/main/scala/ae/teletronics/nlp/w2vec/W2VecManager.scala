package ae.teletronics.nlp.w2vec

import java.io.File

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}

/**
  * Created by trym on 11-10-2016.
  */
class W2VecManager(modelPath:String = "spark-w2vec-text8-model") extends Lifecycle {
  private lazy val sc: SparkContext = initSC()
  private lazy val model = word2VecModel(sc)

  override def shutDown(): Unit = sc.stop()

  def usedInSameContext(term: String): List[(String, Double)] = model.findSynonyms(term, 40).toList

  private def word2VecModel(sc: SparkContext) = {
    if (new File(modelPath).exists()) {
      Word2VecModel.load(sc, modelPath)
    } else {
      val file = "C:\\Users\\trym\\Downloads\\text8" // http://mattmahoney.net/dc/text8.zip
      //      val file = "C:\\Users\\trym\\Downloads\\text8_partaa_partaa"
      val input = sc.textFile(file).map(line => line.split(" ").toSeq)
      val word2vec = new Word2Vec() // https://spark.apache.org/docs/2.0.1/api/java/org/apache/spark/mllib/feature/Word2Vec.html
      //      word2vec.setMinCount(100) // TODO this should resemble the size of the learning data (default is 5)
      val model: Word2VecModel = word2vec.fit(input)
      model.save(sc, modelPath)
      model
    }
  }

  private def initSC() = {
    val conf = new SparkConf().setAppName("Word2Vector").setMaster("local")
    new SparkContext(conf)
  }

}
