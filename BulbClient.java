package com.augment.golden.lifxswitchwatch;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BulbClient{
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    public BulbClient(){
        try{
            socket = new DatagramSocket();
            address = InetAddress.getByName("255.255.255.255");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(){
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 56700);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    private String sendAndReceiveMessage(){
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 56700);
            socket.send(packet);
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            byte[] receivedMessage = packet.getData();
            String message = "";
            for (byte b : receivedMessage)
                message += byteToHex(b);

            return message;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }

        return "";
    }

    public void dimLight(){
        String hex = "31000034000000000000000000000000000000000000000000000000000000006600000000000000001111000000040000";
        buf = hexStringToByteArray(hex);
        sendMessage();
    }

    public void brightenLight(){
        String hex = "3100003400000000000000000000000000000000000000000000000000000000660000000000000000FFFF000000040000";
        buf = hexStringToByteArray(hex);
        sendMessage();
    }

    public void turnOnLight(){
        buf = BuildMessage.turnOnPower();
        sendMessage();
    }

    public void turnOffLight(){
        buf = BuildMessage.turnOffPower();
        sendMessage();
    }

    public int getBrightness(){
        buf = BuildMessage.turnOnPower();
        String hex = sendAndReceiveMessage();

        return 55;
    }

    public void setBrightness(int brightness){
        String brightHex = BuildMessage.decimal2hex(brightness);
        while(brightHex.length() < 4)
            brightHex = "0" + brightHex;

        String hex = "3100003400000000000000000000000000000000000000000000000000000000660000000000000000" + brightHex + "000000040000";
        buf = hexStringToByteArray(hex);
        sendMessage();
    }

    public void close(){
        socket.close();
    }

    private byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }
    private String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }
}
