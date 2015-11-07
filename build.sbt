// use sbt-dev-settings to configure
import com.nitro.build._
import PublishHelpers._

// GAV

lazy val pName = "abstract_data"
lazy val semver = SemanticVersion(0, 0, 3, isSnapshot = false)
organization := "io.malcolmgreaves"
name := pName
version := semver.toString

// scala & java

//                         :::   NOTE   :::
// we want to update to JVM 8 ASAP !
// since we know that we want to be able to use this stuff w/ Spark,
// we unfortunately have to limit ourselves to jvm 7.
// once this gets resolved, we'll update: 
// [JIRA Issue]     https://issues.apache.org/jira/browse/SPARK-6152

lazy val devConfig = {
  import CompileScalaJava._
  Config.spark
}
scalaVersion := "2.11.7"
CompileScalaJava.librarySettings(devConfig)
javaOptions := JvmRuntime.settings(devConfig.jvmVer)

// dependencies and their resolvers

resolvers := Seq(
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)
// for simulacrum
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full)
libraryDependencies ++= Seq(
  // algebra, typeclasses, functional 
  "com.github.mpilquist" %% "simulacrum" % "0.4.0",
  "org.spire-math" %% "algebra" % "0.3.1",
  // Testing
  "org.scalatest" %% "scalatest" % "2.2.4" % Test
)

// doc hacks

sources in (Compile, doc) ~= (_ filter (_.getName endsWith "ToMap.scala"))
sources in (Compile, doc) ~= (_ filter (_.getName endsWith "Sum.scala"))
sources in (Compile, doc) ~= (_ filter (_.getName endsWith "DataOps.scala"))

// publishing settings

Publish.settings(
  repo = Repository.github("malcolmgreaves", pName),
  developers =
    Seq(
      Developer("mgreaves", "Malcolm Greaves", "greaves.malcolm@gmail.com", new URL("https", "github.com", "/malcolmgreaves"))
    ),
  art = ArtifactInfo.sonatype(semver),
  lic = License.apache20
)

// unit test configuration

fork in Test := false
parallelExecution in Test := true
