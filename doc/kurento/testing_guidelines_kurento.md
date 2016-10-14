This project is part of FIWARE.

#Kurento: non functional test guidelines

The Stream Oriented GE is a development framework that provides an abstraction layer for multimedia capabilities, allowing non-expert developers to include interactive media components to their applications. 

##Testing environment

Due to the protocol used by Kurento, is not posible to use an usual performance testing tool. We need to use a Selenium script instead, executing it in multiple browsers (90 in the stress scenarios).

In order to do that, we need: 

* An Ubuntu server machine for deploying Kurento.
* An example application which use the Loopback functionality from Kurento.
* Nine Windows Virtual Machines able to open 10 Google Chrome Browsers. This Google Chrome Browsers must be able to use Fake Cam.

##Test execution

###Preliminary setup

Once the HW necessary for the test described previosly at "Testing Environmment" chapter have been setup, the following preliminary steps need to be accomplished before to start the test process:
In order to do the installation of Kurento, the easiest way is to add the Kurento repository to apt sources, and install it via apt-get. For this, is needed to execute these commands:

    echo "deb http://ubuntu.kurento.org trusty kms6" | sudo tee /etc/apt/sources.list.d/kurento.list
    wget -O - http://ubuntu.kurento.org/kurento.gpg.key | sudo apt-key add -
    sudo apt-get update
    sudo apt-get install kurento-media-server-6.0


This will install Kurento Media Server.

If changing some of the default configuration is needed, then this file can be edited:

* /etc/kurento/kurento.conf.json

In order to start or stop Kurento Media Server, we can use these commands:

`sudo service kurento-media-server-6.0 start`
`sudo service kurento-media-server-6.0 stop`

Furthermore, is needed to deploy an example application which use the Loopback functionality from Kurento. For this, it can be used the one in the folder kurento-hello-world, inside scripts folder.
Then, the following command must be executed:
`mvn compile exec:java`

An instance of Maven installed in the system is mandatory in order to do this.

##Testing step by step

The test of Kurento must be done in three steps:

1. Script configuration:

In order to run the performance test scripts, two files can be found under "scripts" folder. The one called "KurentoClientLB.bat" is the launcher (executable) who invoke the another file (KurentoClientLB.jar). This bat file must be configured in this way:

* LINE 7 -> `if %contador% LSS 5 ( ` -> Number of browser instances to open
* LINE 10 -> `java -jar c:\KurentoSE\KurentoClientLB.jar` -> Path to jar file
* LINE 11 -> `TIMEOUT /T 240` -> Wait time (in seconds) until open the next browser instance
* LINE 14 -> `TIMEOUT /T 14400` -> Total time (in seconds) until stop

The tests were performed with 90 total browser instances and 90 minutes of duration for stress scenario. For stability scenario, they were 20 browser instances and 4 hours of test duration. For both cases, the ramp-up were one virtual user (browser instance) every minute.

For stress test, nine virtual machines were used, and the script was configured this way:

* LINE 7 -> `if %contador% LSS 10 ( ` -> 10 browser instances for each virtual machine. 
* LINE 11 -> `TIMEOUT /T 540` -> For a wait time of a minute between virtual users start, it must be configured 60 * number of virtual machines. 540 seconds in this case.
* LINE 14 -> `TIMEOUT /T 4920` -> 90 minutes up. The total time then will be 90 + last virtual machine starting time (each virtual machine start a minute later than the last one). Then as the last machine starts at 8 minutes, it has to be up for 90-8 = 82 minutes (4920 seconds).

For stability test, the way to configure it is analogous. 

2. WebRTC metrics monitoring:

Since the execution is not performed with an usual performance testing tool, and the protocol is not HTTP, is necessary to gather specific metrics for WebRTC protocol.

For this purpose, a simple java library has been developed. It can be found in the "kms_monitoring" folder. 

To gather the metrics, the script command.bat must be run in the machine where example Loobpack application is deployed. It has to be configured first this way:

* `java -cp lib/*:kms-monitoring-java-6.0.0-SNAPSHOT.jar org.kurento.tutorial.kmsmonitor.Recolector ws://127.0.0.1:8888/kurento 30000`  => The last number means the time in milliseconds between each gathering. The kurento host has to be configured too.

At the end of the test, the process must be stopped.

3. Script execution:

For executing the script, the file KurentoClientLB.bat must be run. In each virtual machine, the starting must be sequentally, with the ramp-up time in between. For example, it the ramp-up is one minute, then the file must be run at minute 0 in the first virtual machine, in the minute 1 in the second virtual machine, etc.

It's recommended to use some kind of cron system in order to do this.

It´s highly recommended too to use a hardware resources monitoring tool, in order to measure things like memory and CPU usages, free memory, etc.

##Expected results

As output from the script and monitoring tool, a .dat file (in csv format) is generated. This file can be used for plotting different kinds of charts, like threads number, response times, number of errores, responses per second, etc. A plotting tool (like gnuplot) is needed in order to do this.

Additionally, if a monitoring tool has been used, then different data from it is collected too. Depending on the monitoring tool, it can generate charts directly, or it can be used another tool for plotting the output from monitoring tool (for example, sar from systat library can be used for monitoring, and k-sar tool for plotting the sar output)
