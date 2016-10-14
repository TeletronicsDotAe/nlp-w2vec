package ae.teletronics.nlp.w2vec

import org.apache.log4j.{Level, Logger}
import org.junit.{After, Before, Test}

/**
  * Created by trym on 11-10-2016.
  */
class W2VecManagerTest {

  private var loggerLevel: org.apache.log4j.Level = _

  @Before
  def setup: Unit = {
    loggerLevel = Logger.getRootLogger.getLevel
    Logger.getRootLogger().setLevel(Level.OFF);
    Logger.getLogger("org.apache.spark").setLevel(Level.OFF)
  }

  @After
  def teardown: Unit = {
    Logger.getRootLogger().setLevel(loggerLevel);
  }

  @Test
  def testUsedInSameContext() = {
    val w2VecManager = new W2VecManager("src/main/resources/spark-w2vec-text8-model")

    measure("anarchist", w2VecManager)
    measure("hero", w2VecManager)

    w2VecManager.shutDown()
  }
  private def measure(term: String, manager: W2VecManager) = {
    val startT = System.currentTimeMillis()
    manager.usedInSameContext(term, 40).foreach(println)
    println(s"\nusedInSameContext(${term}) took ${System.currentTimeMillis() - startT}")
  }

}
