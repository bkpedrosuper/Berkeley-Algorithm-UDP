package berkeley;
import java.net.*;


import java.time.format.DateTimeFormatter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

class BerkeleyCalculator {

        public static void main(String args[]) throws Exception {
        InetAddress addr = InetAddress.getLocalHost();
        String hostname = addr.getHostName();
        System.out.println("Hostname: " + hostname);

        DatagramSocket clientSocket = new DatagramSocket();
        String[] pairs = {"ens2", "ens3"};
        ArrayList<TimeInfo> clocks = new ArrayList<TimeInfo>();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the max RTT allowed: ");
        int rtt_max = scanner.nextInt();
        TimeInfo host_clock = new TimeInfo("H(" + hostname+")", LocalTime.now(), 0, LocalTime.now(), LocalTime.now(), 0);

        clocks.add(host_clock);

        scanner.close();

        for(String pair : pairs) {
                LocalTime sendTime = LocalTime.now();
                InetAddress IPAddress
                        = InetAddress.getByName(pair);
                byte[] sendData = new byte[1024];
                byte[] receiveData = new byte[1024];

                DatagramPacket sendPacket
                        = new DatagramPacket(sendData, sendData.length,
                                IPAddress, 9876);
                clientSocket.send(sendPacket);
                DatagramPacket receivePacket
                        = new DatagramPacket(receiveData,
                                receiveData.length);
                clientSocket.receive(receivePacket);
                String modifiedSentence
                        = new String(receivePacket.getData()).trim();

                LocalTime receiveTime = LocalTime.now();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS");
                LocalTime parsedTime = LocalTime.parse(modifiedSentence, formatter);

                // Calculate the RTT
                long rtt = java.time.Duration.between(sendTime, receiveTime).toMillis();
                
                TimeInfo clock = new TimeInfo(pair, parsedTime, rtt, LocalTime.now(), LocalTime.now(), 0);
                // Calculate Estimated Time
                clock.calculateEstimatedTime(rtt);
                clocks.add(clock);
        }

        // Calculate Mean Time

        LocalTime mean_time = TimeInfo.calculateMeans(clocks, rtt_max);

        // Setting Mean and adjustments
        
        for(TimeInfo clock : clocks) {
              clock.setMean(mean_time);
              clock.calculateAdjustments();
        }

        // PRINT TABLE

        String formatTable = "%-10s %-25s %-10s %-25s %-25s %-10s%n";

        System.out.printf(formatTable, "Name", "Leitura", "RTT", "Estimado", "Média", "Ajustes (ms)");
        for(TimeInfo clock : clocks) {
                System.out.printf(formatTable, clock.getName(), clock.getTime().toString(), clock.getRtt(), clock.getEstimatedTime().toString(), clock.getmean().toString(), clock.getAdjustments());
        }

        clientSocket.close();
        }
}