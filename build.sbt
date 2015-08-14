organization := "com.gonitro.research"

name := "abstract_data"

version := "0.0.1"

// use sbt-dev-settings to configure

import com.nitro.build._

import PublishHelpers._

// scala & java

// we want to update to JVM 8 ASAP !
// since we know that we want to be able to use this stuff w/ Spark,
// we unfortunately have to limit ourselves to jvm 7.
// once this gets resolved, we'll update: https://issues.apache.org/jira/browse/SPARK-6152
lazy val jvmVer = JvmRuntime.Jvm7

CompileScalaJava.librarySettings()

javaOptions := JvmRuntime.settings()

// dependencies and their resolvers

resolvers ++= Seq(
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)

libraryDependencies ++= Seq(
  // math & ml
  "org.spire-math" %% "spire" % "0.9.1", 
  "org.scalanlp" %% "breeze" % "0.11.2",
  "org.scalanlp" %% "nak" % "1.3",
  // Testing
  "org.scalatest" %% "scalatest" % "2.2.4" % Test
)

// publishing settings

Publish.settings(
  repo = Repository.github("Nitro", name.toString),
  developers =
    Seq(
      Dev("mgreaves", "Malcolm Greaves")
    ),
  art = ArtifactInfo.sonatype,
  lic = License.apache20
)

// unit test configuration

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")

testOptions in Test += Tests.Argument("-oF")

fork in Test := false

parallelExecution in Test := true

