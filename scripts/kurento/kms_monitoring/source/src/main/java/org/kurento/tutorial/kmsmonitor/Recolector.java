package org.kurento.tutorial.kmsmonitor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Recolector {

public static void main(String[] args)
{
    KmsStats stats;
	Inbound inbound;
	Outbound outbound;	
	BufferedWriter writer = null;

  try {
	  KmsMonitor monitor = new KmsMonitor (args[0]);

            String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());


            File logFile = new File(timeLog);
FileOutputStream fis = new FileOutputStream(logFile);
PrintStream out = new PrintStream(fis);
System.setOut(out);            

		System.out.println("jitter;fractionLost;inDeltaNacks;inDeltaPlis;inByteCount;packetLostCount;rtt;outDeltaPlis;outDeltaNacks;outByteCount;targetBitrate \n");

		while (true) {
		stats = monitor.updateStats();
		inbound = stats.getWebRtcStats().getInbound();
		outbound = stats.getWebRtcStats().getOutbound();
		double jitter = inbound.getJitter();
		double fractionLost = inbound.getFractionLost();
		double inDeltaNacks = inbound.getDeltaNacks();
		double inDeltaPlis = inbound.getDeltaPlis();
		long inByteCount = inbound.getByteCount();
		long packetLostCount = inbound.getPacketLostCount();
		double rtt = outbound.getRtt();
	 	double outDeltaPlis = outbound.getDeltaPlis();
		double outDeltaNacks = outbound.getDeltaNacks();
		long outByteCount = outbound.getByteCount();
		double targetBitrate = outbound.getTargetBitrate();
		System.out.println(jitter + ";" + fractionLost+ ";" + inDeltaNacks + ";" + inDeltaPlis + ";" + inByteCount + ";" + packetLostCount + ";" + rtt + ";" + outDeltaPlis + ";" + outDeltaNacks + ";" + outByteCount + ";" + targetBitrate + "\n");
		Thread.sleep(Integer.parseInt(args[1]));
        }
    } catch (InterruptedException | java.io.FileNotFoundException e) {
        
	e.printStackTrace();
    }
	}

}