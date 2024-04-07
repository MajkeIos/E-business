import mill._
import $ivy.`com.lihaoyi::mill-contrib-playlib:`,  mill.playlib._

object shop extends PlayModule with SingleModule {

  def scalaVersion = "2.13.13"
  def playVersion = "3.0.2"
}
