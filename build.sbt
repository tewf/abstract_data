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

// for simulacrum
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full)

// if your project uses multiple Scala versions, use this for cross building
addCompilerPlugin("org.spire-math" % "kind-projector" % "0.6.3" cross CrossVersion.binary)

libraryDependencies ++= Seq(
  // math & ml
  "org.spire-math" %% "spire" % "0.9.1", 
  "org.scalanlp" %% "breeze" % "0.11.2",
  // algebra, typeclasses, functional 
  "com.github.mpilquist" %% "simulacrum" % "0.4.0",
  "org.spire-math" %% "algebra" % "0.3.1",
  // TODO : REMOVE and put into separate repo!
  "org.apache.flink" % "flink-scala"   % "0.9.0",
//  "org.apache.flink" % "flink-clients" % "0.9.0",
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

// TODO change back to false once we're done w/ Flink
fork in Test := true

parallelExecution in Test := true

