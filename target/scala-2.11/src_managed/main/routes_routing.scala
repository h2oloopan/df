// @SOURCE:/home/s5pan/Projects/df/conf/routes
// @HASH:a781390d4f6834cdec69c77cf1c82547a17628d1
// @DATE:Wed Dec 17 07:27:32 PST 2014


import play.core._
import play.core.Router._
import play.core.Router.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._
import _root_.controllers.Assets.Asset
import _root_.play.libs.F

import Router.queryString

object Routes extends Router.Routes {

import ReverseRouteContext.empty

private var _prefix = "/"

def setPrefix(prefix: String) {
  _prefix = prefix
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" }


// @LINE:6
private[this] lazy val controllers_Application_index0_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix))))
private[this] lazy val controllers_Application_index0_invoker = createInvoker(
play.api.Play.maybeApplication.map(_.global).getOrElse(play.api.DefaultGlobal).getControllerInstance(classOf[controllers.Application]).index(),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "index", Nil,"GET", """ Home page""", Routes.prefix + """"""))
        

// @LINE:9
private[this] lazy val controllers_Test_get1_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("test/get/"),DynamicPart("key", """[^/]+""",true))))
private[this] lazy val controllers_Test_get1_invoker = createInvoker(
play.api.Play.maybeApplication.map(_.global).getOrElse(play.api.DefaultGlobal).getControllerInstance(classOf[controllers.Test]).get(fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Test", "get", Seq(classOf[String]),"GET", """ Test""", Routes.prefix + """test/get/$key<[^/]+>"""))
        

// @LINE:10
private[this] lazy val controllers_Test_set2_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("test/set/"),DynamicPart("key", """[^/]+""",true))))
private[this] lazy val controllers_Test_set2_invoker = createInvoker(
play.api.Play.maybeApplication.map(_.global).getOrElse(play.api.DefaultGlobal).getControllerInstance(classOf[controllers.Test]).set(fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Test", "set", Seq(classOf[String]),"POST", """""", Routes.prefix + """test/set/$key<[^/]+>"""))
        

// @LINE:13
private[this] lazy val controllers_Bot_talk3_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("bot/talk"))))
private[this] lazy val controllers_Bot_talk3_invoker = createInvoker(
play.api.Play.maybeApplication.map(_.global).getOrElse(play.api.DefaultGlobal).getControllerInstance(classOf[controllers.Bot]).talk(),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Bot", "talk", Nil,"POST", """ Bot""", Routes.prefix + """bot/talk"""))
        

// @LINE:16
private[this] lazy val controllers_Assets_at4_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
private[this] lazy val controllers_Assets_at4_invoker = createInvoker(
controllers.Assets.at(fakeValue[String], fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
        
def documentation = List(("""GET""", prefix,"""@controllers.Application@.index()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """test/get/$key<[^/]+>""","""@controllers.Test@.get(key:String)"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """test/set/$key<[^/]+>""","""@controllers.Test@.set(key:String)"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """bot/talk""","""@controllers.Bot@.talk()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]]
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:6
case controllers_Application_index0_route(params) => {
   call { 
        controllers_Application_index0_invoker.call(play.api.Play.maybeApplication.map(_.global).getOrElse(play.api.DefaultGlobal).getControllerInstance(classOf[controllers.Application]).index())
   }
}
        

// @LINE:9
case controllers_Test_get1_route(params) => {
   call(params.fromPath[String]("key", None)) { (key) =>
        controllers_Test_get1_invoker.call(play.api.Play.maybeApplication.map(_.global).getOrElse(play.api.DefaultGlobal).getControllerInstance(classOf[controllers.Test]).get(key))
   }
}
        

// @LINE:10
case controllers_Test_set2_route(params) => {
   call(params.fromPath[String]("key", None)) { (key) =>
        controllers_Test_set2_invoker.call(play.api.Play.maybeApplication.map(_.global).getOrElse(play.api.DefaultGlobal).getControllerInstance(classOf[controllers.Test]).set(key))
   }
}
        

// @LINE:13
case controllers_Bot_talk3_route(params) => {
   call { 
        controllers_Bot_talk3_invoker.call(play.api.Play.maybeApplication.map(_.global).getOrElse(play.api.DefaultGlobal).getControllerInstance(classOf[controllers.Bot]).talk())
   }
}
        

// @LINE:16
case controllers_Assets_at4_route(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        controllers_Assets_at4_invoker.call(controllers.Assets.at(path, file))
   }
}
        
}

}
     