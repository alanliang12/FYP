import java.util
import antlrWork._
import org.antlr.v4.runtime.{Token, Parser}
import org.antlr.v4.runtime.misc.Interval
import org.antlr.v4.runtime.tree._
import java.io._
import scala.util._
import experimentJ8._

/**
 * Created by hao-linliang on 06/10/15.
 */
class firstObf extends JavaBaseListener {

  val l = new util.ArrayList[String]
  val map = new util.HashMap[String, String]()
  val storeNode = new util.ArrayList[TerminalNode]()


  def startSetup(t:ParseTree):Unit={
    // could store parse tree instead of making arraylist to store nodes
    val walker = new ParseTreeWalker
    walker.walk(this,t)

  }



  override def visitTerminal(node: TerminalNode): Unit = {

    storeNode.add(node)

    if (node.getSymbol.getText == "<") {

      test(node)

    }
//    else if(node.getSymbol.getType == 100 && !storeNode.isEmpty){
//      println("run this part")
//      val tempChecker = storeNode.get(node.getSymbol.getTokenIndex-1)
//      if(tempChecker.getSymbol.getType == 100 ){
//
//        test(node)
//      }
//    }
    else {

    val temp = node.getSymbol().getText()
    val checker = node.getSymbol.getType()

    def matchTest(i: Int): Unit = i match {
      case 100 => changeIdentifier(temp)
      case _ => l.add(temp)
    }
    matchTest(checker)
   }
  }

  def test(node:TerminalNode):Unit={
    val tempNode = storeNode.get(node.getSymbol.getTokenIndex - 1)
    l.add(tempNode.getSymbol.getText)
    l.add(node.getSymbol.getText)
    map.remove(tempNode.getSymbol.getText)
    l.remove(tempNode.getSymbol.getTokenIndex)
   // storeNode.clear()

  }

  def changeIdentifier(s:String):Unit = {
    if(s == "String"){
      l.add(s)
    }else {
      if (map.containsKey(s)) {
        l.add(map.get(s))
      } else {
        val change = scramble(s)
        l.add(change)
        map.put(s, change)
      }
    }
  }

  def scramble(s:String):String={

    val alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()

    var temp = s.hashCode
    val r = new Random()
    val tempString = temp ^ r.nextInt(999999)
    tempString.toString.toCharArray
    var x = 0
    var finalString=""
    while(x < tempString.toString.toCharArray.length){

        finalString += alphabet(r.nextInt(52))

      x+=1
    }
    finalString

  }

}
