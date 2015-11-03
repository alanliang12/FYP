import antlrWork.JavaBaseListener
import org.antlr.v4.runtime.tree._
import java.util._
import scala.util._

/**
 * Created by hao-linliang on 07/10/15.
 */

class secondObf extends JavaBaseListener{



  override def visitTerminal(node: TerminalNode): Unit ={

    //if token 22, else token 15

    val typeOfToken = node.getSymbol.getType





  }


}
