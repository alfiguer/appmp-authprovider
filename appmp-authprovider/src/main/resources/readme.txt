1. open terminal
2. cd /Volumes/AFG-DATA/OCL-PRJ/3233-7-RF18/src/applications/appmp-authprovider/src/main/resources
Use the WebLogic MBeanMaker to Generate the MBean Type
3. java -DMDF=MercadoPublicoAuthentication.xml -Dfiles=/Volumes/AFG-DATA/OCL-PRJ/3233-7-RF18/src/applications/appmp-authprovider/src/main/java/cl/mercadopublico/poc/security/provider/auth/mbeans -DcreateStubs=true weblogic.management.commo.WebLogicMBeanMaker
generate:
MercadoPublicoAuthenticatorMBean
MercadoPublicoAuthenticatorImpl


Use the WebLogic MBeanMaker to Create the MBean JAR File (MJF)
1. open terminal
2. cd $WL_HOME/server/bin
3. . ./setWLSEnv.sh
4. export CLASSPATH=$HOME/.m2/repository/org/apache/httpcomponents/httpcore/4.4.10/httpcore-4.4.10.jar:$HOME/.m2/repository/commons-logging/commons-logging/1.0.4/commons-logging-1.0.4.jar:$HOME/.m2/repository/commons-codec/commons-codec/1.10/commons-codec-1.10.jar:$HOME/.m2/repository/org/apache/httpcomponents/httpclient/4.5.6/httpclient-4.5.6.jar:$HOME/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.9.6/jackson-databind-2.9.6.jar:$CLASSPATH
5. echo $CLASSPATH
/Users/alfiguer/.m2/repository/org/apache/httpcomponents/httpcore/4.4.10/httpcore-4.4.10.jar:/Users/alfiguer/.m2/repository/commons-logging/commons-logging/1.0.4/commons-logging-1.0.4.jar:/Users/alfiguer/.m2/repository/commons-codec/commons-codec/1.10/commons-codec-1.10.jar:/Users/alfiguer/.m2/repository/org/apache/httpcomponents/httpclient/4.5.6/httpclient-4.5.6.jar:/Users/alfiguer/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.9.6/jackson-databind-2.9.6.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_172.jdk/Contents/Home/lib/tools.jar:/Users/alfiguer/Developer/oracle/product/fmw/12.2.1.2.0/wlserver/modules/features/wlst.wls.classpath.jar:/Users/alfiguer/Developer/oracle/product/fmw/12.2.1.2.0/wlserver/server/lib/weblogic.jar
6. cd /Volumes/AFG-DATA/OCL-PRJ/3233-7-RF18/src/applications/appmp-authprovider
7. ant all
..
..
[copy] Copying 1 file to /Users/alfiguer/Developer/oracle/product/fmw/12.2.1.2.0/wlserver/server/lib/mbeantypes
8. cd $DOMAIN_HOME
9. ./startWebLogic.sh

export CLASSPATH=$JAVA_HOME/lib/tools.jar:$CLASSPATH

4. java -DMJF=MercadoPublicoAuthProvider -Dfiles=/Volumes/AFG-DATA/OCL-PRJ/3233-7-RF18/src/applications/appmp-authprovider/src/main/java/ weblogic.management.commo.WebLogicMBeanMaker
ejecutar . ./setWLSEnv.sh

