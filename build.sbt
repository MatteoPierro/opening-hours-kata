name := "opening-hours-kata"

organization := "com.matteopierro"

version := "0.0.1"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test" withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.12.1" % "test" withSources() withJavadoc()
)

scalacOptions ++= Seq("-feature", "-language:implicitConversions")

initialCommands := "import com.matteopierro.openinghourskata._"