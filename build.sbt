name := """df"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "com.typesafe.play.plugins" %% "play-plugins-redis" % "2.3.1",
  "mysql" % "mysql-connector-java" % "5.1.28"
)

resolvers += "Sedis repository" at "http://pk11-scratch.googlecode.com/svn/trunk/"
