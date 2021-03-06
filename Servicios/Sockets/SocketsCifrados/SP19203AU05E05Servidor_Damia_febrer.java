package Servicios.Sockets.SocketsCifrados;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SP19203AU05E05Servidor_Damia_febrer {

    public static String cleanString(String dirty){
        String clean = "";

        for(int i = 0; i < dirty.length(); i++){
            if(dirty.charAt(i) != 00){
                clean += dirty.charAt(i);
            }
        }

        return clean;
    }

    public static byte[] removeBase64(String encoded){
        return Base64.getDecoder().decode(encoded);
    }

    public static byte[] descifrarMensaje(byte[] encriptado, SecretKey key) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decripted = cipher.doFinal(encriptado);
        return decripted;
    }
    public static void main(String[] args) {
        
        try{
            ServerSocket servidor = new ServerSocket(44014);
            Socket nuevo = servidor.accept();

            InputStream is = nuevo.getInputStream();

            //Reicibimos la llave
            byte[] clave = new byte[32];            
            is.read(clave); 

            //Quitamos el Base64 a la llave que nos mandan del cliente
            byte[] decoded = removeBase64(new String(clave));

            //Reconstruimos la llave que nos pasa el cliente
            SecretKey sk = new SecretKeySpec(decoded, "AES");

            //Recbimos el mensaje del cliente
            byte[] delCliente = new byte[200];
            is.read(delCliente);    
            
            String mensaje = new String(delCliente);
            mensaje = cleanString(mensaje);

            //Quitamos el Base64 del mensaje que nos pasa el cliente
            byte[] sinBase = removeBase64(mensaje);

            System.out.println("Encripted Message " + new String(sinBase));

            //Desciframos el mensaje
            byte[] descifrado = descifrarMensaje(sinBase, sk);
            mensaje = new String(descifrado);

            System.out.print("Decripted Message: " + new String(descifrado));

            nuevo.close();
            servidor.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}