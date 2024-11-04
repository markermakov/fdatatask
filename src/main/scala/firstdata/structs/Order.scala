package firstdata.structs

import scala.util.hashing.Hashing.default

case class Order(name: String, operationType: String, stockName: String, price: Int, count: Int) {
  val originalHash: Int =
    default.hash(operationType ++ stockName ++ price.toString ++ count.toString)
  val customHash: Int = {
    val reverseType: String = if (operationType == "s") "b" else "s"
    default.hash(reverseType ++ stockName ++ price.toString ++ count.toString)
  }
}