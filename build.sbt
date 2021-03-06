name := "txparsers"

version := "0.1"

scalaVersion := "2.13.1"

//https://github.com/ruippeixotog/scala-scraper
libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "2.2.0"

//http://nrinaudo.github.io/kantan.csv/
libraryDependencies += "com.nrinaudo" %% "kantan.csv" % "0.6.0"
libraryDependencies += "com.nrinaudo" %% "kantan.csv-java8" % "0.6.0"

libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.2" % "test"

testFrameworks += new TestFramework("utest.runner.Framework")