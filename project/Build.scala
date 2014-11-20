import sbt._
import sbt.Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

object ApplicationBuild extends Build {

  lazy val serviceDependencies = Seq(
    "com.yammer.dropwizard" % "dropwizard-core" % "0.6.2",
    "com.sun.mail" % "javax.mail" % "1.5.1",
    "com.sun.jersey" % "jersey-core" % "1.17.1"
  )

  lazy val commonDependencies = Seq(
    "uk.gov.defra" % "capd-common" % "1.0.2"
  )

  lazy val serviceTestDependencies = Seq(
    "org.easytesting" % "fest-assert-core" % "2.0M10" % "test",
    "com.novocode" % "junit-interface" % "0.11" % "test",
    "com.yammer.dropwizard" % "dropwizard-testing" % "0.6.2" % "test"
  )

  val appReleaseSettings = Seq(
    // Publishing options:
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { x => false },
    publishTo <<= version { (v: String) =>
      val nexus = "https://defranexus.kainos.com/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("sonatype-snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("sonatype-releases"  at nexus + "content/repositories/releases")
    },
    credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
  )

  def defaultResolvers = Seq(
    "DEFRA Nexus Release repo" at "https://defranexus.kainos.com/content/repositories/releases/"
  )

  def commonSettings = Defaults.defaultSettings ++ Seq(
    organization := "uk.gov.defra",
    autoScalaLibrary := false,
    crossPaths := false,
    exportJars := false,
    scalaVersion := "2.10.2",
    resolvers ++= defaultResolvers
  )

  def standardSettingsWithAssembly = commonSettings ++ assemblySettings ++ appReleaseSettings ++ Seq(
    mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
    {
      case "about.html" => MergeStrategy.rename
      case x => old(x)
    }
  })

  lazy val root = Project("sendmail", file("."), settings = Defaults.defaultSettings ++ appReleaseSettings ++ Seq(
    name := "uk.gov.defra.capd.mail",
    resolvers ++= defaultResolvers,
      libraryDependencies ++= commonDependencies
  )) aggregate(SendEmailService, SendEmailApi)

  lazy val SendEmailService: Project = Project("sendmail-service", file("uk.gov.defra.capd.mail.service"),
    settings = standardSettingsWithAssembly ++ Seq(
      name := "uk.gov.defra.capd.mail.service",
      libraryDependencies ++= commonDependencies ++ serviceDependencies ++ serviceTestDependencies
    )) dependsOn(SendEmailApi)

  lazy val SendEmailApi = Project("sendmail-api", file("uk.gov.defra.capd.mail.api"),
    settings = standardSettingsWithAssembly ++ Seq(
      name := "uk.gov.defra.capd.mail.api",
      libraryDependencies ++= commonDependencies
    ))
}
