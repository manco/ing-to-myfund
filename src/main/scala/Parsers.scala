import java.io.{File, FileInputStream, InputStreamReader}

import MyFundOps._

object Parsers extends App {
  private val ingInput = new InputStreamReader(new FileInputStream("historia-headless.csv"), "windows-1250")
  private val myFundOutput = new File("myfund-ing.csv")
  val rows = Ing.toMyFund(ingInput).collect { case Right(r) => r }
  rows.writeCsv(myFundOutput)
}