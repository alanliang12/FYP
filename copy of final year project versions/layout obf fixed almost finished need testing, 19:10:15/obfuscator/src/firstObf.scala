import java.util
import antlrWork._
import org.antlr.v4.runtime.{Token, Parser}
import org.antlr.v4.runtime.misc.Interval
import org.antlr.v4.runtime.tree._
import scala.util._
import org.antlr.v4.runtime.ParserRuleContext
import experimentJ8._

/**
 * Created by hao-linliang on 06/10/15.
 */
class firstObf extends Java8BaseListener {

  val l = new util.ArrayList[String]
  val map = new util.HashMap[String, String]()
  val unobfuscatedKeyWords = new util.ArrayList[String]()
  val storeNode = new util.ArrayList[TerminalNode]()
  val methodClassStorage = new util.ArrayList[String]()





  def startSetup(t:ParserRuleContext, list:util.ArrayList[String]):Unit={


    methodClassStorage.addAll(list)
    // could store parse tree instead of making arraylist to store nodes
    val walker = new ParseTreeWalker
    walker.walk(this,t)



  }

//  override def  enterEveryRule (ctx: ParserRuleContext):Unit= {
//
//
//
//
//
//  }

//  override def enterQualifiedName(ctx: Java8Parser.QualifiedNameContext):Unit = {
//
//    println("enter")
//
//  }
//
//  override def exitQualifiedName(ctx: Java8Parser.QualifiedNameContext):Unit = {
//
//
//    println("exit")
//  }

  override def enterMethodInvocation(ctx: Java8Parser.MethodInvocationContext):Unit={


  //  println("hi")

  //  println(ctx.Identifier())

  }

  override def enterArrayCreationExpression(ctx: Java8Parser.ArrayCreationExpressionContext):Unit ={

    //ctx.getTokens(102)

  //  println(ctx.getText)

  }

  override def enterUnannArrayType(ctx: Java8Parser.UnannArrayTypeContext):Unit ={

    if(ctx.start.getType == 102){
      unobfuscatedKeyWords.add(ctx.start.getText)
    }
  //  println(ctx.getText+"unarray")



  }

  override def visitTerminal(node: TerminalNode): Unit = {

        storeNode.add(node)
  }

  def traverseNodes():Unit ={

    def matchTest(i: Int, node:TerminalNode): Unit = i match {
      case 68 => test(node)
      case 102 => checkForKeyWord(node)
      case _ => l.add(node.getText)
    }
    var x = 0
    for(x <- 0 to storeNode.size()-1){
      var node = storeNode.get(x)
      val checker = node.getSymbol.getType()
      matchTest(checker,node)
    }

  }


  def checkForKeyWord(node:TerminalNode): Unit ={

    if(storeNode.isEmpty){
      changeIdentifier(node.getSymbol.getText)
    }else
      if(node.getSymbol.getText == "String"){
      l.add(node.getText)
      }else
    if(methodClassStorage.contains(node.getText)) {
      changeIdentifier(node.getText)

    }else  {

      val temp = storeNode.get(node.getSymbol.getTokenIndex-1)
      def matchTest(i: Int): Unit = i match{
        case 65 => if(methodClassStorage.contains(node.getText)){
          changeIdentifier(node.getText)
        }else{
          l.add(node.getText)
        }
        case 102 => test(node)
        case _ => changeIdentifier(node.getSymbol.getText)
      }
      matchTest(temp.getSymbol.getType)
    }

  }



  def test(node:TerminalNode):Unit={

    val tempNode = storeNode.get(node.getSymbol.getTokenIndex - 1)  // get previous node to current
    l.add(tempNode.getSymbol.getText)
    if(node.getSymbol.getText == "<"){
      l.add(node.getSymbol.getText)
    }else{
      changeIdentifier(node.getSymbol.getText)
    }
    unobfuscatedKeyWords.add(tempNode.getSymbol.getText)
    l.remove(tempNode.getSymbol.getTokenIndex)
    map.remove(tempNode.getSymbol.getText)

  }

  def changeIdentifier(s:String):Unit = {
    if(unobfuscatedKeyWords.contains(s)){
      l.add(s)
    }else {
      if (map.containsKey(s)) { // checks if it has already been obfuscated
        l.add(map.get(s))
      } else {
        val change = scramble(s) // obfuscate
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
