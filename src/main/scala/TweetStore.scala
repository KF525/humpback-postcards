import twitter4j._
import Math._

import scala.util.Random

class TweetStore extends StatusAdapter {
  var someTweets: Array[String] = Array("")
  val rand = new Random(System.currentTimeMillis())

  override def onStatus(status: Status) = {
    if (!status.getText.contains("@"))
      someTweets = someTweets :+ status.getText
    if (someTweets.length % 100 == 0) printListicle
  }

  def printListicle = {
    val baseTweet = someTweets(rand.nextInt(someTweets.length))
    println("this is the base tweet!: " + baseTweet)
    val generator = generateGroup(howRelated)_
    println(formatTweetsAsListicle(generator(baseTweet, someTweets)))
  }


  def generateGroup(comp: (String) => (String, String) => Boolean = lengthProximityFn)(base: String, someTweets: Array[String]): Array[String] = {
    someTweets.sortWith(comp(base)).take(10)
  }

  def lengthProximityFn (base: String)(a: String, b: String): Boolean = {
    abs(a.length - base.length) < abs(b.length - base.length)
  }

  def howRelated (base: String)(a: String, b: String): Boolean = {
    val withoutMentionsOrLinks = base.split(" ").filterNot(s => s.startsWith("http"))
    val byLongest: Array[String] = withoutMentionsOrLinks.sortBy(-1 * _.length)
    val longest: String = byLongest(0)
    if(a.contains(longest))
      true
    else
      false
  }

  def formatTweetsAsListicle(groupedTweets: Array[String]) = {
    val numberedTweets = groupedTweets.zipWithIndex.map {case (tweet, index) => s"${index + 1}. $tweet"}
    numberedTweets.mkString("\n")
  }

}


//Array[(Map(String => Int), String]

object Comparison extends Enumeration {
  type Comparison = Value
  val LessThan, GreaterThan, Equal = Value
}


case class BaseTweet(base: String) {
  def compare(tweet1: String, tweet2: String) = ???
}
