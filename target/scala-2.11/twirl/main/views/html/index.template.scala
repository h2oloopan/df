
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._

import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import views.html._

/**/
object index extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply():play.twirl.api.HtmlFormat.Appendable = {
      _display_ {

Seq[Any](format.raw/*1.1*/("""<!DOCTYPE html>
<html>
    <head>
        <title>Dialogue Framework</title>
        <link rel="stylesheet" href=""""),_display_(/*5.39*/routes/*5.45*/.Assets.at("stylesheets/bootstrap.css")),format.raw/*5.84*/("""">
        <link rel="stylesheet" media="screen" href=""""),_display_(/*6.54*/routes/*6.60*/.Assets.at("stylesheets/main.css")),format.raw/*6.94*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(/*7.59*/routes/*7.65*/.Assets.at("images/favicon.png")),format.raw/*7.97*/("""">
        <script data-main=""""),_display_(/*8.29*/routes/*8.35*/.Assets.at("javascripts/loader.js")),format.raw/*8.70*/("""" src=""""),_display_(/*8.78*/routes/*8.84*/.Assets.at("javascripts/lib/require.js")),format.raw/*8.124*/(""""></script>
    </head>
    <body>
    	<script type="text/x-handlebars">
    		"""),format.raw/*12.7*/("""{"""),format.raw/*12.8*/("""{"""),format.raw/*12.9*/("""outlet"""),format.raw/*12.15*/("""}"""),format.raw/*12.16*/("""}"""),format.raw/*12.17*/("""
    	"""),format.raw/*13.6*/("""</script>
    </body>
</html>
"""))}
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}
              /*
                  -- GENERATED --
                  DATE: Wed Dec 17 07:27:32 PST 2014
                  SOURCE: /home/s5pan/Projects/df/app/views/index.scala.html
                  HASH: 6878eb311fe368cdb6f593edbeae60ad12c399be
                  MATRIX: 798->0|938->114|952->120|1011->159|1093->215|1107->221|1161->255|1248->316|1262->322|1314->354|1371->385|1385->391|1440->426|1474->434|1488->440|1549->480|1656->560|1684->561|1712->562|1746->568|1775->569|1804->570|1837->576
                  LINES: 29->1|33->5|33->5|33->5|34->6|34->6|34->6|35->7|35->7|35->7|36->8|36->8|36->8|36->8|36->8|36->8|40->12|40->12|40->12|40->12|40->12|40->12|41->13
                  -- GENERATED --
              */
          