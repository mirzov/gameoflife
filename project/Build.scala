import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "gameoflife"
    val appVersion      = "1.0"
    val scalaVersion    = "2.10.0-RC3"

    val appDependencies = Seq(
	"joda-time" % "joda-time" % "2.1",
	"org.scala-lang" % "scala-actors" % "2.10.0-RC3",
	"org.scalatest" % "scalatest_2.10.0-RC3" % "2.0.M5-B1" % "test"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      // Add your own project settings here      
    )

}
