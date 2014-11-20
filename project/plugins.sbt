// Comment to get more information during initialization
logLevel := Level.Warn

// The repositories
resolvers ++= Seq(
   "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
   "DEFRA Nexus - releases" at "https://defranexus.kainos.com/content/repositories/releases/"
)

resolvers += Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.11.2")