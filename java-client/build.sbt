name := "Simple jaxrs client over keycloak server"

version := "0.1.0"

organization := "ch.redhat.keycloak"

scalaVersion := "2.11.7"

resolvers ++= Seq("snapshots"     at "https://oss.sonatype.org/content/repositories/snapshots" + "http://repository.jboss.org/nexus/content/repositories/release",
                  "staging"       at "https://oss.sonatype.org/content/repositories/staging",
                  "releases"      at "https://oss.sonatype.org/content/repositories/releases"
                 )

resolvers += Resolver.mavenLocal

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  val liftVersion = "3.0.1"
  Seq(
    "org.keycloak" % "keycloak-adapter-core" % "3.1.0.Final" % "compile",
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.8.2" % "compile",
    "org.apache.logging.log4j" % "log4j-core" % "2.8.2" % "compile",
    "org.jboss.resteasy" % "resteasy-client" % "3.1.3.Final" % "compile",
    "javax.xml.bind" % "jaxb-api" % "2.2.11" % "compile",
    "org.eclipse.persistence" % "org.eclipse.persistence.moxy" % "2.5.2" % "compile",
    "com.novocode" % "junit-interface" % "0.11" % "test",
    "junit" % "junit" % "4.10" % "test" 
  )
}

