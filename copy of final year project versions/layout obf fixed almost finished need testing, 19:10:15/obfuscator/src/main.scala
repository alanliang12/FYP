/**
 * Created by hao-linliang on 06/10/15.
 */

import java.io.{FileWriter, File}
import java.util

import antlrWork._
import org.antlr.v4.runtime._
import org.antlr.v4.runtime.tree._
import experimentJ8._

object main {
  val lines = scala.io.Source.fromFile("/Users/hao-linliang/Desktop/obfuscator/src/antlrWork/testFile").mkString
 //  val lines = scala.io.Source.fromFile("/Users/hao-linliang/Desktop/obfuscator/src/antlrWork/JavaListener.java").mkString


  val tester = new classAndMethodFinder

  tester.startSetup(setup(lines))

  val layout = new firstObfRemake

  layout.startSetup(setup(lines),tester.classMethodHolder)

  layout.startObf()

  writeToFile(layout.finalObfuscatedList)


  //    val layout = new firstObf
  //    layout.startSetup(setup(lines),tester.classMethodHolder)

  //    layout.traverseNodes()

  // writeToFile(layout.l)

  var firstObfFile = new util.ArrayList[String]()


  def main(args: Array[String]) {




  }

  def setup(s:String): ParserRuleContext ={

    val input = new ANTLRInputStream(s)
    val lexer = new Java8Lexer(input)
    val tokens = new CommonTokenStream(lexer)
    val parser = new Java8Parser(tokens)
    var tree = new ParserRuleContext()
    tree = parser.compilationUnit()

    //val tree = parser.compilationUnit()

    tree
  }

//  def layoutObf(t:ParseTree): Unit ={
//    val walker = new ParseTreeWalker()
//    val obf = new firstObf()
//
//    walker.walk(obf,t)
//
//
//    firstObfFile = obf.l
//  }



  def writeToFile(l: util.ArrayList[String]): Unit = {

    val file = new File("test.java")
    file.createNewFile()
    val fw = new FileWriter(file)
    var x = 0
    val y = l.size() - 1
    while (x < y) {
      var temp = l.get(x)
      fw.write(temp + " ")
      fw.flush()
      x += 1
    }
  }

  def writeToFile2(l: util.ArrayList[String]): Unit = {

    val file = new File("test2.java")
    file.createNewFile()
    val fw = new FileWriter(file)
    var x = 0
    val y = l.size() - 1
    while (x < y) {
      var temp = l.get(x)
      fw.write(temp + " ")
      fw.flush()
      x += 1
    }
  }

  def prep(l :util.ArrayList[String]): String = {

    var a =0
    var s =""
    while(a < l.size()){
      s += " " +l.get(a)+" "
      a+=1
    }
    s
  }


}
