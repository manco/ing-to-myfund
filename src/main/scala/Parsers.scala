import java.io.{File, FileInputStream, InputStreamReader}

import MyFundOps._

object Parsers extends App {

  bps()

  def bps() {
    val bpsInput = new FileInputStream("TRANSAKCJE.htm")
    val myFundOutput = new File("myfund-bps.csv")

    val rows = BPS.toMyFund(bpsInput)
    rows.writeCsv(myFundOutput)
  }

  def ing() {
    val ingInput = new InputStreamReader(new FileInputStream("historia-headless.csv"), "windows-1250")
    val myFundOutput = new File("myfund-ing.csv")
    val rows = Ing.toMyFund(ingInput).collect { case Right(r) => r }
    rows.writeCsv(myFundOutput)
  }

}

