# simple client to work with sso


deploy server part first on the wildfly server:

[(service-jee)](https://github.com/nmajorov/keycloak_training/tree/master/keycloak-quickstarts/service-jee-jaxrs)



Settings for maven for the fedora24:

        #!/bin/sh
        export M2_HOME=/usr/share/maven
        export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk
        export PATH=$JAVA_HOME/bin:$M2_HOME/bin:$PATH


Run following script:



            ./call_secured.sh





