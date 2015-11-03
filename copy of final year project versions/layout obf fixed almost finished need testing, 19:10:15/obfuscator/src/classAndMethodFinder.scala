import java.util
import java.util._
import antlrWork._
import org.antlr.v4.runtime.tree.TerminalNode
import org.antlr.v4.runtime.tree._
import org.antlr.v4.runtime.{Token, Parser}
import experimentJ8._



/**
 * Created by hao-linliang on 12/10/15.
 */
class classAndMethodFinder extends Java8BaseListener {


  val classMethodHolder = new util.ArrayList[String]()
  val nodeHolder = new util.ArrayList[TerminalNode]()

  def startSetup(t:ParseTree):Unit={
    val walker = new ParseTreeWalker
    walker.walk(this,t)
  }

  override def visitTerminal(node: TerminalNode): Unit ={

    nodeHolder.add(node)
    val checker = node.getSymbol.getType

    def matchTest(i:Int):Unit = i match{
      case 102 => checkForClass(node)
      case 59 => checkForMethod(node)
      case _ =>
    }
    matchTest(checker)
  }


  def checkForClass(node:TerminalNode): Unit ={
    val previousNode = nodeHolder.get(node.getSymbol.getTokenIndex-1)
    if(previousNode.getSymbol.getType == 9){
      classMethodHolder.add(node.getSymbol.getText)
    }
  }

  def checkForMethod(node:TerminalNode):Unit ={

    var previousNode = nodeHolder.get(node.getSymbol.getTokenIndex-1)

    if(previousNode.getSymbol.getType == 58){

      while(previousNode.getSymbol.getType!= 57){
        previousNode = nodeHolder.get(previousNode.getSymbol.getTokenIndex-1)
      }
      previousNode = nodeHolder.get(previousNode.getSymbol.getTokenIndex-1)
      classMethodHolder.add(previousNode.getSymbol.getText)
    }


  }

}
