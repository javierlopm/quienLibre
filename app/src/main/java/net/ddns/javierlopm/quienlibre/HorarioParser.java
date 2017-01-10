package net.ddns.javierlopm.quienlibre;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static android.os.SystemClock.sleep;

/**
 * Created by javierlopm on 09/01/16.
 */
public class HorarioParser {
    Element tabla;
    Context c;
    String usbid;
    String clave;

    public final String logTag = "HorarioParser";

    HorarioParser(Context cc, String usbid, String clave){
        c = cc;
        this.clave = clave;
        this.usbid = usbid;
    }

    public boolean agregarHorario(){
        try{
            tabla = descargarHorario(); // LLeva usuario y password
            extraerBloques();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private void extraerBloques(){
        LinkedList bloques = new LinkedList<BloquePropio>();
        Elements filas = tabla.getElementsByTag("tr"); // Aqui explota con usuario y clave equivocadas
        Materia mActual = new Materia(c);
        BloquePropio bp = new BloquePropio(c);
        String codigoActual = "";
        String [] horas;
        String [] salon;

        for (int i = filas.size()-1; i > 0  ; i--) {
            // Las materias

            Elements elems = filas.get(i).getElementsByTag("td");
            if (esMateria(filas.get(i))) {
                String s = elems.first().text();
                datosMateria(s);
                mActual.editar(datosMateria(s)[1].toLowerCase(), datosMateria(s)[0]); // Ser'a eficiente? o calcula doble?
                mActual.agregar();

            }
        }

        for (int k = 1; k < filas.size() ; k++) {

            if (! esMateria(filas.get(k))){

                Elements elems = filas.get(k).getElementsByTag("td");
                Log.v(logTag, " Asignatura  ================");

                if (elems.size() == 7){
                    // El 0 es el salon
//                    Log.v(logTag, codigoActual);
//                    Log.v(logTag, elems.get(0).ownText().substring(2));
                    for (int j = 1; j < 7 ; j++) {
//                        Log.v(logTag,"Entrando");
                        if (elems.get(j).ownText().length()>1) {
//                            Log.v(logTag, j + "| " + Dias.values()[j - 1] + " " + elems.get(j).ownText().length());
//                            Log.v(logTag, elems.get(0).ownText().substring(2,5) + "-" + elems.get(0).ownText().substring(5,8));
                            horas = elems.get(j).ownText().split("-");

                            for (int i = Integer.parseInt(horas[0]);
                                 i <= Integer.parseInt(horas[1]) && i < 9;
                                 i++)
                            {
                                bp.edit(i,
                                        Dias.values()[j-1],
                                        Edificio.valueOf(elems.get(0).ownText().substring(2,5)),
                                        Integer.parseInt(elems.get(0).ownText().substring(5,8)),
                                        codigoActual);
                                bp.agregar();
                                Log.v(logTag, bp.toString());
                            }
                        }
                    }
                }
                else if (elems.size() == 10){
                    //El 0 es la asignatura
                    codigoActual = elems.get(0).ownText();
                    // El 3 es el salon

//                    Log.v(logTag, codigoActual);
//                    Log.v(logTag, elems.get(3).ownText().substring(2,5) + "-" + elems.get(3).ownText().substring(5,8));
                    for (int j = 4; j < 10 ; j++) {
                        if (elems.get(j).ownText().length()>1){
//                            Log.v(logTag, j + "| " + Dias.values()[j-4] +" "+  elems.get(j).ownText()); // Longitud 1 es vacio
                            horas = elems.get(j).ownText().split("-");

                            for (int i = Integer.parseInt(horas[0]);
                                 i <= Integer.parseInt(horas[1]) && i < 9;
                                 i++)
                            {
                                bp.edit(i,
                                        Dias.values()[j-4],
                                        Edificio.valueOf(elems.get(3).ownText().substring(2,5)),
                                        Integer.parseInt(elems.get(3).ownText().substring(5,8)),
                                        codigoActual);
                                bp.agregar();
                                Log.v(logTag, bp.toString());
                            }
                        }
                    }
                }
            }
        }


    }



    private boolean esMateria(Element e){
        return e.hasAttr("id");
    }

    private String []datosMateria(String input){
        String [] s ;

        s = input.split("]");
        s[0] = s[0].replace("[","");

        return s;
    }

    private Element descargarHorario(){

        Element el = null;
        Log.e("test1","starting to download horario");
        HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new NullX509TrustManager()}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e){
            Log.e("Get Url1", "Error in downloading: " + e.toString());
        }

        Log.e("test1","Changing policy");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        Log.e("test1","trust manager stuff");

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        Log.e("test1","installing all trust manager");

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            Log.e("Ssl error"," Fail trying to ignore security\n" + e.toString());
        }



        // Now you can access an https URL without having the certificate in the truststore
        try {
            Log.e("test1","new url ");

            URL url = new URL("https://secure.dst.usb.ve/login?service=http%3A%2F%2Fcomprobante.dii.usb.ve%2FCAS%2Flogin.do");

            
//            URL url = new URL("https://secure.dst.usb.ve/login?service=https%3A%2F%2Fcomprobante.asignaturas.usb.ve%2FnuevoComprobante%2FCAS%2Flogin.do");
//            URL url = new URL("https://secure.dst.usb.ve/login?service=https%3A%2F%2Fcomprobante.asignaturas.usb.ve%2Fsecure%2FgenerarComprobante.do");

            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
            Log.e("test1","reading from connection ");
            StringBuilder response = new StringBuilder();
            String inputLine;
            try{

                Log.e("test1","reading response");
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                Log.e("test1","starting to parse");
                Document pagina = Jsoup.parse(response.toString());

                Log.e("test1","making new request");
                URL urlpost = new URL("https://secure.dst.usb.ve"+pagina.getElementById("fm1").attr("action"));
                HttpsURLConnection conn = (HttpsURLConnection) urlpost.openConnection();
                conn.setRequestMethod("POST");
                Log.e("test1","added method");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                Log.e("test1","created array list");
                nameValuePairs.add(new BasicNameValuePair("username", "11-10552" )); // usbid
                nameValuePairs.add(new BasicNameValuePair("password", "musicalternativa")); // clave
                Log.e("test2","added  username and pass");
                nameValuePairs.add(new BasicNameValuePair("warn", "true"));
                nameValuePairs.add(new BasicNameValuePair("lt", pagina.getElementsByTag("input").get(3).val()));
                nameValuePairs.add(new BasicNameValuePair("_eventId", "submit"));
                Log.e("test3","added events");


                pagina = null;

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(nameValuePairs));
                writer.flush();
                writer.close();
                os.close();

                Log.e("test3","writer closed");

                conn.connect();
                sleep(3);
                Log.e("test3","connected");
                in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
                Log.e("test3","bulding response");
                response = new StringBuilder();
//                Log.v("Prueba 2.0", "entrando");
                while ((inputLine = in.readLine()) != null) {
//                    Log.v("Prueba 3.0", inputLine);
                    Log.e("on ciclo",inputLine);
                    response.append(inputLine);
                }
                in.close();
                Log.e("parse","JUST READ " + response.toString() + "from " + conn.getURL());

                url = new URL("https://comprobante.dii.usb.ve/CAS/login.do");
                connection = url.openConnection();
                in = new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream()));
                Log.e("test1","reading from connection ");
                response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();


//                Log.v("Prueba 3", response.toString());
//                in.close();
                Log.e("test3","input read done?REAL DONE NOW");

//                url = new URL("https://comprobante.asignaturas.usb.ve/nuevoComprobante/publico/salir.do?param=pasar");
//                connection = url.openConnection();
                Log.e("parse","trying to parse " + response.toString());
                Document paginaComprobante = Jsoup.parse(response.toString());
                Log.e("test3","new connection created");

                Log.e("test3","opening table");
                Log.e("test table",  paginaComprobante.toString());

                Element tabla = paginaComprobante.getElementsByTag("table").get(4);
                Log.e("test3","table open");

//                String horario = tabla.getElementsByTag("tr").size().toString();
//                HorarioParser h = new HorarioParser(tabla,this);
//                h.extraerBloques();

                el = tabla;


            } catch (Exception e){
                Log.e("Get Url2", "Error in downloading: " + e.toString());
            }

        } catch (Exception e) {
            Log.e("Get Url3", "Error in downloading: " + e.toString());
        }
        return el;
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
