import org.scalatest.{Matchers, FlatSpec}

class TweetStoreSpec extends FlatSpec with Matchers {

  val store: TweetStore = new TweetStore
  "formatTweetsAsListicle" should "return appropriate list based on input" in {
    val tweets = Array("one tweet", "two tweets", "fish tweets", "blue tweets")
    val expectedResult = "1. one tweet\n2. two tweets\n3. fish tweets\n4. blue tweets"

    store.formatTweetsAsListicle(tweets) shouldBe expectedResult
  }

  "generateGroup" should "return ten similar tweets" in {
    val gen = store.generateGroup()_
    val result = gen("b", Array("a", "ab", "abc", "abcd", "abcde", "abcdef", "abcdefg", "abcdefgh", "abcdefghi", "abcdefghij", "abcdefghijk"))
    result shouldNot contain ("abcdefghijk")
  }

  "howRelated" should "return a measure of commonality between two tweets" in {
    val comparison = store.howRelated("one fish is big")_
    comparison("two fish is big", "two asparagus is big") shouldBe true
    comparison("two asparagus is big", "two fish is big") shouldBe false
    comparison("two fish is small", "one asparagus is big") shouldBe true
    comparison("two asparagus is big", "two fish is small") shouldBe false
    comparison("two fish is big", "one fish is not big") shouldBe true
    comparison("two asparagus is big", "asparagus is delicious") shouldBe true
  }
}
