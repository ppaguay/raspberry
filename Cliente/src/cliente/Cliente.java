/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author Alex
 */
public class Cliente {

    static {
        try {
            // System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "info");
            // System.setProperty("org.slf4j.simpleLogger.log.com.github.sarxos.webcam.ds.v4l4j", "trace");
            // Webcam.setDriver(new V4l4jDriver());
        } catch (Exception e) {
            System.out.println("error ->" + e.getMessage());
        }

    }

    // public final static int SOCKET_PORT = 8888;      // you may change this
    // public final static String SERVER = "127.0.0.1";  // localhost
    // public final static String FILE_TO_SEND = "E:\\img\\assasing.png";  // you may change this, I give a
    public static void main(String[] args) throws IOException {
        int bytesRead;
        int current = 0;

        OutputStream os = null;

        Socket sock = null;
        try {
            Webcam webcam = null;
            List<Webcam> lstWebcam = Webcam.getWebcams();
            System.out.println("* Lista de Camaras :");
            int idCamera = 0;
            int totalCameras = lstWebcam.size();
            for (int i = 0; i < lstWebcam.size(); i++) {
                System.out.println("#-- N: " + i + ": camara ->" + lstWebcam.get(i).getName().toString());
            }

            do {
                System.out.println("Seleccione su Numero de Camara:\n");
                Scanner s2 = new Scanner(System.in);
                idCamera = s2.nextInt();
                System.out.println("Camara Seleccionado: " + idCamera);

            } while ((idCamera < 0) && (idCamera >= totalCameras));
            webcam = lstWebcam.get(idCamera);
            System.out.println("Ingrese la ip del servidor Socket  ejemplo: 192.168.43.105 ");
            Scanner s = new Scanner(System.in);
            String host = s.next();
            System.out.println("Puerto del servidor socket Ejemplo 8888 ");
            Scanner s2 = new Scanner(System.in);
            int puerto = s2.nextInt();
            System.out.println("Connecting...");

            //Webcam webcam = Webcam.getDefault();
            //webcam.setViewSize(new Dimension(176, 144));//windows
            // webcam.setViewSize(new Dimension(160, 120));//raspberry
            webcam.setViewSize(new Dimension(640, 480)); // Vga
            //webcam.setViewSize(new Dimension(320, 240)); // Vga
            webcam.open();
            long tiempo1, tiempo2, tiempo3;
            FileWriter fw = null;
            fw = new FileWriter("LogClienteJava.txt");
            PrintWriter guardar = new PrintWriter(fw);
            String linea;
            linea = ("Ini,FCI e ITx,Fin TX\n");
            guardar.write(linea);
            guardar.flush();
            while (true) {
                try {
                    current++;
                    sock = new Socket(host, puerto);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    tiempo1 = new java.util.Date().getTime(); //Tiempo de inicio de obtencion de la imagen
                    ImageIO.write(webcam.getImage(), "jpg", baos);
                    byte[] mybytearray = baos.toByteArray();
                    tiempo2 = new java.util.Date().getTime(); //Tiempo fin de captura de imagen  Inicio transmision
                    // System.out.println("Sending " + current + "(" + mybytearray.length + " bytes)");
                    os = sock.getOutputStream();
                    os.write(mybytearray, 0, mybytearray.length);
                    os.flush();
                    tiempo3 = new java.util.Date().getTime();    //Tiempo fin de tx
                    linea = (tiempo1 + "," + tiempo2 + "," + tiempo3 + "\n");
                    guardar.write(linea);
                    guardar.flush();
                    System.out.println("Done.");
                    if (os != null) {
                        os.close();
                    }
                    if (sock != null) {
                        sock.close();
                    }
                    Thread.sleep(30);
                } catch (Exception e) {
                    System.out.println("Error al capturar");
                }

            }

        } finally {
//
//            if (os != null) {
//                os.close();
//            }
//            if (sock != null) {
//                sock.close();
//            }
        }
    }

}
