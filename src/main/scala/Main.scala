object Main extends App {
  val result = Application.calculateResult("src/tech_task/clients.txt", "src/tech_task/orders.txt")
  Application.writeResult("src/tech_task/result.txt", result)
}
