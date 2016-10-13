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


##Testing step by step

The test of Kurento must be done in two steps:

1. Script configuration:

There are 4 scripts, one for each scenario.

Host IP and port must be defined. For this purpose, the following User Defined variables in each Jmeter script must be configured:

* HOST -> IP to the host where Kurento is deployed. Depending on the scenario, this IP has to be Kurento-CEP or Kurento-Broker IP (Kurento-CEP for Kurento Cep Stress Scenario, and Kurento-Broker for all the rest).
* PORT -> Port where Kurento is listening. Depending on the scenario, this port has to be Kurento-CEP or Kurento-Broker Port. (Kurento-CEP for Kurento Cep Stress Scenario, and Kurento-Broker for all the rest).

Furthermore, the test duration must be configured. It is recommended to use the planificator from the threads group. The duration for the stress tests was 45 minutes, 75 minutes for the load test, and 6 hours in the stability case.

Finally, in order to get the results, the path to the results file must be configured in the Simple Data Writer from the script.

2. Script execution:

For executing the script, the following command must be typed in the jmeter bin folder:

`./jmeter.sh -n -t /path/to/script/script_name.jmx`

It´s recommended to execute the command in background and nohup mode:

`nohup ./jmeter.sh -n -t /path/to/script/script_name.jmx &`

It´s highly recommended to use a hardware resources monitoring tool, in order to measure things like memory and CPU usages, free memory, etc.

##Expected results

As output from the script, a .dat file (in csv format) is generated. This file can be used for plotting different kinds of charts, like threads number, response times, number of errores, responses per second, etc. A plotting tool (like gnuplot) is needed in order to do this.

Additionally, if a monitoring tool has been used, then different data from it is collected too. Depending on the monitoring tool, it can generate charts directly, or it can be used another tool for plotting the output from monitoring tool (for example, sar from systat library can be used for monitoring, and k-sar tool for plotting the sar output)
