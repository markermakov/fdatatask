import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable.HashMap

class AppTest extends AnyFlatSpec with Matchers {
  "Application" should "check doubling orders for sellers" in {
    //Checking doubling orders for sellers
    val testResult = Application.calculateResult("src/tech_task/test/test_clients.txt", "src/tech_task/test/test1.txt")
    testResult("C1").toString shouldEqual "Client(C1,40,HashMap(A -> 10, B -> 5, C -> 14, D -> 15))"
    testResult("C2").toString shouldEqual "Client(C2,210,HashMap(A -> 30, B -> 10, C -> -4, D -> 1))"
    testResult("C3").toString shouldEqual "Client(C3,50,HashMap(A -> 0, B -> 0, C -> 0, D -> 0))"
  }

  "Application" should "check doubling orders for buyers" in {
    //Checking doubling orders for buyers
    val testResult = Application.calculateResult("src/tech_task/test/test_clients.txt", "src/tech_task/test/test3.txt")
    testResult("C1").toString shouldEqual "Client(C1,133,HashMap(A -> 7, B -> 5, C -> 10, D -> 15))"
    testResult("C5").toString shouldEqual "Client(C5,167,HashMap(A -> 3, B -> 0, C -> 5, D -> 5))"
  }

  "Application" should "calculate orders file with no agreements correctly" in {
    //Orders file with no agreements
    val testResult = Application.calculateResult("src/tech_task/test/test_clients.txt", "src/tech_task/test/test2.txt")
    testResult("C1").toString shouldEqual "Client(C1,100,HashMap(A -> 10, B -> 5, C -> 10, D -> 15))"
    testResult("C2").toString shouldEqual "Client(C2,150,HashMap(A -> 30, B -> 10, C -> 0, D -> 1))"
    testResult("C4").toString shouldEqual "Client(C4,100,HashMap(A -> 40, B -> 0, C -> 0, D -> 0))"
    testResult("C6").toString shouldEqual "Client(C6,100,HashMap(A -> 1, B -> 5, C -> 5, D -> 0))"
    testResult("C8").toString shouldEqual "Client(C8,10,HashMap(A -> 0, B -> 0, C -> 5, D -> 10))"
    testResult("C9").toString shouldEqual "Client(C9,0,HashMap(A -> 0, B -> 0, C -> 0, D -> 0))"
  }

  "Application" should "calculate regular 10 rows of orders correctly" in {
    val testResult = Application.calculateResult("src/tech_task/test/test_clients.txt", "src/tech_task/test/test4.txt")
    testResult("C1").toString shouldEqual "Client(C1,89,HashMap(A -> 10, B -> 5, C -> 12, D -> 12))"
    testResult("C2").toString shouldEqual "Client(C2,182,HashMap(A -> 27, B -> 10, C -> -2, D -> 1))"
    testResult("C3").toString shouldEqual "Client(C3,50,HashMap(A -> 0, B -> 0, C -> 0, D -> 0))"
    testResult("C4").toString shouldEqual "Client(C4,100,HashMap(A -> 40, B -> 0, C -> 0, D -> 0))"
    testResult("C5").toString shouldEqual "Client(C5,158,HashMap(A -> 8, B -> 0, C -> 5, D -> 5))"
    testResult("C6").toString shouldEqual "Client(C6,130,HashMap(A -> -4, B -> 5, C -> 5, D -> 0))"
    testResult("C9").toString shouldEqual "Client(C9,-9,HashMap(A -> 0, B -> 0, C -> 0, D -> 3))"
  }
}
