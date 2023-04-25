import java.net.*;

import java.time.LocalTime;

class ServerBerkeley {

    public LocalTime average(LocalTime t1, LocalTime... times){
        long nanosSum = t1.toNanoOfDay();
        for (LocalTime clock : times){
            nanosSum += clock.toNanoOfDay();
        }
        return LocalTime.ofNanoOfDay(nanosSum/(1+times.length));
    }

    public static void main(String args[]) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        while (true) {
            DatagramPacket receivePacket
                    = new DatagramPacket(receiveData,
                            receiveData.length);
            serverSocket.receive(receivePacket);
            // String sentence = new String(
            //         receivePacket.getData());
            InetAddress IPAddress
                    = receivePacket.getAddress();
            int port = receivePacket.getPort();
            LocalTime currentTime = LocalTime.now();

            sendData = currentTime.toString().getBytes();            
            
            DatagramPacket sendPacket
                    = new DatagramPacket(sendData,
                            sendData.length,
                            IPAddress, port);
            serverSocket.send(sendPacket);

            
            System.exit(port);
            serverSocket.close();

        }
    }
}