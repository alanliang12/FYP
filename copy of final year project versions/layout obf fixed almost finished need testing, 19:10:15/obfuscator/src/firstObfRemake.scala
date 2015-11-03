import java.util

import experimentJ8.{Java8Parser, Java8BaseListener}
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.{TerminalNode, ParseTreeWalker}

import scala.util.Random

/**
 * Created by hao-linliang on 18/10/15.
 */
class firstObfRemake extends Java8BaseListener{


  val methodClassStorage = new util.ArrayList[String]()
  val obfuscatedWords = new util.HashMap[String, String]()
  val storeNode = new util.ArrayList[TerminalNode]()
  val wordsToObfuscate = new util.ArrayList[String]()
  val finalObfuscatedList = new util.ArrayList[String]()


  def startSetup(t:ParserRuleContext, list:util.ArrayList[String]):Unit={

    methodClassStorage.addAll(list)

    val walker = new ParseTreeWalker
    walker.walk(this,t)


  }


  // need to do this part
  override def enterPackageDeclaration(ctx: Java8Parser.PackageDeclarationContext):Unit = {




 }

  // need testing
  override def enterSingleStaticImportDeclaration(ctx: Java8Parser.SingleStaticImportDeclarationContext):Unit ={

    val check = ctx.Identifier()

    if(methodClassStorage.contains(check.getText)){

      wordsToObfuscate.add(check.getText)
    }


  }

  // should work need more testing
  override def enterNormalClassDeclaration(ctx: Java8Parser.NormalClassDeclarationContext):Unit={


    val check = ctx.Identifier()

    if(methodClassStorage.contains(check.getText)){

      wordsToObfuscate.add(check.getText)

    }



  }

  //should work
  override def enterVariableDeclaratorId(ctx: Java8Parser.VariableDeclaratorIdContext):Unit ={


    val check = ctx.Identifier()

    wordsToObfuscate.add(check.getText)

  }

  // need testing
  override def enterUnannClassType(ctx: Java8Parser.UnannClassTypeContext):Unit ={

    val check = ctx.Identifier()

    if(methodClassStorage.contains(check.getText)){

      wordsToObfuscate.add(check.getText)
    }


  }

  // not to sure on this, need testing still
  override def enterUnannClassType_lf_unannClassOrInterfaceType(ctx: Java8Parser.UnannClassType_lf_unannClassOrInterfaceTypeContext):Unit={

    val check = ctx.Identifier()

    if(methodClassStorage.contains(check.getText)){

      wordsToObfuscate.add(check.getText)
    }


  }

  // not to sure on this, need testing still
  override def enterUnannClassType_lfno_unannClassOrInterfaceType(ctx: Java8Parser.UnannClassType_lfno_unannClassOrInterfaceTypeContext): Unit= {

    val check = ctx.Identifier()

    if(methodClassStorage.contains(check.getText)){

      wordsToObfuscate.add(check.getText)
    }

  }

  // need testing not sure what unaann etc means
  override def enterUnannTypeVariable(ctx: Java8Parser.UnannTypeVariableContext):Unit ={

    wordsToObfuscate.add(ctx.Identifier().getText)
  }

  // need to check if its working
  override def enterMethodDeclarator(ctx: Java8Parser.MethodDeclaratorContext):Unit ={

    val check = ctx.Identifier()

    if (methodClassStorage.contains(check.getText)) {

      wordsToObfuscate.add(check.getText)
    }

  }

//  // works need more testing though incase
//  override def enterMethodDeclaration(ctx: Java8Parser.MethodDeclarationContext):Unit ={
//
//    val check = ctx.methodHeader().methodDeclarator().Identifier()
//
//
//    if (methodClassStorage.contains(check.getText)) {
//
//      wordsToObfuscate.add(check.getText)
//    }
//
//
//  }

  // should be working need testing, not picking up the identifier should check this !!!!!!!
  override def enterReceiverParameter(ctx: Java8Parser.ReceiverParameterContext):Unit={

    val check = ctx.Identifier()
    wordsToObfuscate.add(check.getText)

  }

  // check if it need to have an if statement to check
  override def enterSimpleTypeName(ctx: Java8Parser.SimpleTypeNameContext):Unit ={

    wordsToObfuscate.add(ctx.Identifier().getText)

  }

  // havent checked if it works yet, should i obf?
  override def enterEnumDeclaration(ctx: Java8Parser.EnumDeclarationContext):Unit ={

    val check = ctx.Identifier()

    if (methodClassStorage.contains(check.getText)) {

      wordsToObfuscate.add(check.getText)
    }



  }

  // need to testt, this is the constant, should i obf?
  override def enterEnumConstant(ctx: Java8Parser.EnumConstantContext):Unit ={

    val check = ctx.Identifier()

    if (methodClassStorage.contains(check.getText)) {

      wordsToObfuscate.add(check.getText)
    }

  }

  // need to check with the javalistener interface more testing
  override def enterNormalInterfaceDeclaration(ctx: Java8Parser.NormalInterfaceDeclarationContext):Unit = {

    val check = ctx.Identifier

    if (methodClassStorage.contains(check.getText)) {

      wordsToObfuscate.add(check.getText)
    }


  }

  // should these be removed? havent finished implementing
  override def enterAnnotationTypeDeclaration(ctx: Java8Parser.AnnotationTypeDeclarationContext):Unit ={

    ctx.Identifier()

  }

  //should be removed? havent implemented
  override def enterAnnotationTypeElementDeclaration(ctx: Java8Parser.AnnotationTypeElementDeclarationContext):Unit={


  }

  // should this be removed too?
  override def enterElementValuePair(ctx: Java8Parser.ElementValuePairContext):Unit ={

    wordsToObfuscate.add(ctx.Identifier().getText)
  }

  // look at this for control flow obfuscation too, need to be tested aswell
  override def enterLabeledStatement(ctx: Java8Parser.LabeledStatementContext):Unit ={

    wordsToObfuscate.add(ctx.Identifier().getText)

  }

  // again can look at this for control flow obf, need to tet
  override def enterLabeledStatementNoShortIf(ctx: Java8Parser.LabeledStatementNoShortIfContext):Unit ={

    wordsToObfuscate.add(ctx.Identifier().getText)
  }

  //should i obf?
  override def enterEnumConstantName(ctx: Java8Parser.EnumConstantNameContext):Unit ={

    wordsToObfuscate.add(ctx.Identifier().getText)
  }

  // obfuscate this if i obfuscate labeled statements
  override def enterBreakStatement(ctx: Java8Parser.BreakStatementContext):Unit ={

    wordsToObfuscate.add(ctx.Identifier().getText)

  }

  // obfuscate this if i obfuscate labeled statements
  override def enterContinueStatement(ctx: Java8Parser.ContinueStatementContext):Unit ={

    wordsToObfuscate.add(ctx.Identifier().getText)

  }

  // need to test this to check for the list of identifiers
  override def enterClassInstanceCreationExpression(ctx: Java8Parser.ClassInstanceCreationExpressionContext):Unit ={

    var x =0
    val identList = ctx.Identifier()
    while(x < identList.size() ) {
      var temp = identList.get(x)
      if (methodClassStorage.contains(temp.getText)){
        wordsToObfuscate.add(temp.getText)
      }
      x+=1
    }
  }

  //need testing
  override def enterClassInstanceCreationExpression_lf_primary(ctx: Java8Parser.ClassInstanceCreationExpression_lf_primaryContext):Unit={

    val check = ctx.Identifier()
    if (methodClassStorage.contains(check.getText)){
      wordsToObfuscate.add(check.getText)
    }

  }

  // need testing
  override def enterClassInstanceCreationExpression_lfno_primary(ctx: Java8Parser.ClassInstanceCreationExpression_lfno_primaryContext):Unit ={

    var x =0
    val identList = ctx.Identifier()
    while(x < identList.size() ) {
      var temp = identList.get(x)
      if (methodClassStorage.contains(temp.getText)){
        wordsToObfuscate.add(temp.getText)
      }
      x+=1
    }

  }

  // need testing
  override  def enterFieldAccess(ctx: Java8Parser.FieldAccessContext):Unit ={

    val check = ctx.Identifier()
    if (methodClassStorage.contains(check.getText)){
      wordsToObfuscate.add(check.getText)
    }


  }

  //need testing
  override def enterFieldAccess_lf_primary(ctx: Java8Parser.FieldAccess_lf_primaryContext):Unit ={

    val check = ctx.Identifier()
    if (methodClassStorage.contains(check.getText)){
      wordsToObfuscate.add(check.getText)
    }

  }

  //need testing
  override def enterFieldAccess_lfno_primary(ctx: Java8Parser.FieldAccess_lfno_primaryContext):Unit ={

    val check = ctx.Identifier()
    if (methodClassStorage.contains(check.getText)){
      wordsToObfuscate.add(check.getText)
    }

  }

  // this is breaking whyyyy?
//  override def enterMethodInvocation(ctx: Java8Parser.MethodInvocationContext):Unit={
//
//    val check = ctx.Identifier()
//    println(check.getText)
//    if (methodClassStorage.contains(check.getText)){
//      wordsToObfuscate.add(check.getText)
//    }
//  }

  override def enterMethodInvocation_lf_primary(ctx: Java8Parser.MethodInvocation_lf_primaryContext):Unit ={

    val check = ctx.Identifier()
    if (methodClassStorage.contains(check.getText)){
      wordsToObfuscate.add(check.getText)
    }
  }

  override def enterMethodInvocation_lfno_primary(ctx: Java8Parser.MethodInvocation_lfno_primaryContext):Unit ={

    val check = ctx.Identifier()
    if (methodClassStorage.contains(check.getText)){
      wordsToObfuscate.add(check.getText)
    }

  }

  // java 8 stuff should i do this?
  override def enterMethodReference(ctx: Java8Parser.MethodReferenceContext):Unit ={

  }

  // java 8 stuff havent imple
  override def enterMethodReference_lf_primary(ctx: Java8Parser.MethodReference_lf_primaryContext):Unit={

  }

  // java8 stuff havent imple
  override def enterMethodReference_lfno_primary(ctx: Java8Parser.MethodReference_lfno_primaryContext):Unit={

  }

  // java 8 stuff havent imple
  override def enterLambdaParameters(ctx: Java8Parser.LambdaParametersContext):Unit ={


  }

  // i think this is java 8 too
  override def enterInferredFormalParameterList(ctx: Java8Parser.InferredFormalParameterListContext):Unit ={

  }


  override def visitTerminal(node: TerminalNode): Unit = {
    storeNode.add(node)
  }

  def startObf():Unit ={

    var x = 0
    while(x != storeNode.size()){
      val temp = storeNode.get(x)
      if(temp.getSymbol.getType == 102){

        changeIdentifier(temp.getText)

      }else{

        temp.getText
        finalObfuscatedList.add(temp.getText)

      }
      x+=1
    }


  }

  def changeIdentifier(s:String):Unit = {

    if(wordsToObfuscate.contains(s)){
      if (obfuscatedWords.containsKey(s)) { // checks if it has already been obfuscated
        finalObfuscatedList.add(obfuscatedWords.get(s))
      } else {
        val change = scramble(s) // obfuscate
        finalObfuscatedList.add(change)
        obfuscatedWords.put(s, change)
      }
    }else{

      finalObfuscatedList.add(s)
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
