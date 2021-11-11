import org.threeten.bp.LocalDate

object Transaction{
  def generateRandom = {
    val r = scala.util.Random
    val amount = (r.nextDouble * 100 ).round
    val date = LocalDate.of(2019,5, r.nextInt(30) + 1)
    val descriptions = List("AH", "Vomar", "Telfort", "Belastingdienst", "Waternet", "Ziggo", "Lidl", "ING bank", "FEBO", "Restaurant", "Bar", "Kapsalon")

    Transaction(amount, descriptions(r.nextInt(10)), date)
  }
}

case class Transaction(amount: Double, description: String, date: LocalDate)




