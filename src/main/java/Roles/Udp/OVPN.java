package Roles.Udp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class OVPN {
    public static void main(String[] args) throws Exception {
        final String host = "193.48.143.86";
        final int port = 80;

//        StringBuilder conf = new StringBuilder();
//        BufferedReader reader;
//        try {
//            reader = new BufferedReader(new FileReader(
//                    "//Users/user/Documents/EsipreCreteil/EsipeCreteil.ovpn"));
//            String line = reader.readLine();
//            while (line != null) {
//                conf.append(line);
//                // read next line
//                line = reader.readLine();
//                conf.append("\n");
//            }
//            reader.close();
//        } catch (Exception e){}

        InetAddress address = InetAddress.getByName(host);
        DatagramSocket socket = new DatagramSocket();
        while (true) {
            String conf = "^\u001E����9g=�Ft\n" +
                    ":���\u001C���Q�����O�]P��D;Y/-\u001F��\u0010S��\f;{���?o���ht�l���sG��r(���V����K����y�\u0010���\u001B���\u0007����W.\u007Fn�x[9���";
            byte[] confBuff = conf.getBytes();
            DatagramPacket request = new DatagramPacket(confBuff, confBuff.length, address, port);
            socket.send(request);

            byte[] buffer = new byte[512];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);


            System.out.println(buffer);
            System.out.println();

            Thread.sleep(10000);
        }
    }
}
