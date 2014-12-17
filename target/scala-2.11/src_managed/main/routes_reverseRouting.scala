// @SOURCE:/home/s5pan/Projects/df/conf/routes
// @HASH:a781390d4f6834cdec69c77cf1c82547a17628d1
// @DATE:Wed Dec 17 07:27:32 PST 2014

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.Router.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._
import _root_.controllers.Assets.Asset
import _root_.play.libs.F

import Router.queryString


// @LINE:16
// @LINE:13
// @LINE:10
// @LINE:9
// @LINE:6
package controllers {

// @LINE:13
class ReverseBot {


// @LINE:13
def talk(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "bot/talk")
}
                        

}
                          

// @LINE:10
// @LINE:9
class ReverseTest {


// @LINE:10
def set(key:String): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "test/set/" + implicitly[PathBindable[String]].unbind("key", dynamicString(key)))
}
                        

// @LINE:9
def get(key:String): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "test/get/" + implicitly[PathBindable[String]].unbind("key", dynamicString(key)))
}
                        

}
                          

// @LINE:16
class ReverseAssets {


// @LINE:16
def at(file:String): Call = {
   implicit val _rrc = new ReverseRouteContext(Map(("path", "/public")))
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                        

}
                          

// @LINE:6
class ReverseApplication {


// @LINE:6
def index(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix)
}
                        

}
                          
}
                  


// @LINE:16
// @LINE:13
// @LINE:10
// @LINE:9
// @LINE:6
package controllers.javascript {
import ReverseRouteContext.empty

// @LINE:13
class ReverseBot {


// @LINE:13
def talk : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Bot.talk",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "bot/talk"})
      }
   """
)
                        

}
              

// @LINE:10
// @LINE:9
class ReverseTest {


// @LINE:10
def set : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Test.set",
   """
      function(key) {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "test/set/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("key", encodeURIComponent(key))})
      }
   """
)
                        

// @LINE:9
def get : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Test.get",
   """
      function(key) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "test/get/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("key", encodeURIComponent(key))})
      }
   """
)
                        

}
              

// @LINE:16
class ReverseAssets {


// @LINE:16
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        

}
              

// @LINE:6
class ReverseApplication {


// @LINE:6
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
   """
)
                        

}
              
}
        


// @LINE:16
// @LINE:13
// @LINE:10
// @LINE:9
// @LINE:6
package controllers.ref {


// @LINE:13
class ReverseBot {


// @LINE:13
def talk(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   play.api.Play.maybeApplication.map(_.global).getOrElse(play.api.DefaultGlobal).getControllerInstance(classOf[controllers.Bot]).talk(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Bot", "talk", Seq(), "POST", """ Bot""", _prefix + """bot/talk""")
)
                      

}
                          

// @LINE:10
// @LINE:9
class ReverseTest {


// @LINE:10
def set(key:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   play.api.Play.maybeApplication.map(_.global).getOrElse(play.api.DefaultGlobal).getControllerInstance(classOf[controllers.Test]).set(key), HandlerDef(this.getClass.getClassLoader, "", "controllers.Test", "set", Seq(classOf[String]), "POST", """""", _prefix + """test/set/$key<[^/]+>""")
)
                      

// @LINE:9
def get(key:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   play.api.Play.maybeApplication.map(_.global).getOrElse(play.api.DefaultGlobal).getControllerInstance(classOf[controllers.Test]).get(key), HandlerDef(this.getClass.getClassLoader, "", "controllers.Test", "get", Seq(classOf[String]), "GET", """ Test""", _prefix + """test/get/$key<[^/]+>""")
)
                      

}
                          

// @LINE:16
class ReverseAssets {


// @LINE:16
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      

}
                          

// @LINE:6
class ReverseApplication {


// @LINE:6
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   play.api.Play.maybeApplication.map(_.global).getOrElse(play.api.DefaultGlobal).getControllerInstance(classOf[controllers.Application]).index(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "index", Seq(), "GET", """ Home page""", _prefix + """""")
)
                      

}
                          
}
        
    