package firstdata.structs

import scala.collection.mutable.HashMap

case class Client(name: String, balanceUSD: Int, stocks: HashMap[String, Int])
