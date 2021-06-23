package org.marko.practices;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DownloadImage {

    private static final String fileImage = "1024px-FC_Bayern_München_logo_%282017%29.svg.png";
    private static final String HTTPS_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg/" + fileImage;

    public static void main(String[] args) {

        URL url = null;
        HttpsURLConnection connection = null;
        try {
            url = new URL(HTTPS_URL);
            connection = (HttpsURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = new FileOutputStream(fileImage);) { //creamos un objeto para vincular la imagen

            byte[] buffer = new byte[2048];// creamos un buffer para almacenar los bytes de la imagen
            int length = 0;
            while((length = inputStream.read(buffer)) != -1){//se lee hasta el final del archivo
                outputStream.write(buffer,0,length);// escribe los datos del archivo de salida
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
