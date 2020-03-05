package Servicios;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.*;
import java.net.*;

public class SP19202AU03E01_Damia_Febrer{
    public static void main(String[] args) {
        try{
            Socket cliente = new Socket();
            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            cliente.connect(addr);
            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));
            DataInputStream input = new DataInputStream(cliente.getInputStream());
            String msg = "mensaje del cliente";
            br.write(msg);
            System.out.println(input);
            cliente.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}