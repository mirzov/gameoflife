import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "gameoflife"
    val appVersion      = "1.0"

    val appDependencies = Seq(
	    "joda-time" % "joda-time" % "2.1"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      // Add your own project settings here      
    )

}
