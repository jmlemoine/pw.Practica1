import org.jsoup.Connection;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException{

        //Se digita la URL.
        System.out.println("Escriba la URL de la página que desee: ");
        Scanner url = new Scanner(System.in);
        String pagina = url.next();

        //Conexión a la página digitada a través de la librería Jsoup, creando una conexión HTTP.
        Connection.Response conexion = Jsoup.connect(pagina).execute();

        //a) Indicar la cantidad de líneas del recurso retornado.
        //body(): Se obtiene el cuerpo del recurso como un string.
        //length: Se muestra el tamaño del cuerpo después de la función split("\n") haber dividido el cuerpo por cada salto de línea.
        String cuerpo = conexion.body();
        int cantLineas = cuerpo.split("\n").length;
        System.out.println("\na) La cantidad de líneas del recurso retornado es: "+cantLineas + "\n");

        //b) Indicar la cantidad de párrafos <p> que contiene el documento HTML.
        // get(): Obtiene el documento HTML de la URL introducida.
        // connect(): Se conecta a la página al igual que como se utilizó para ejecutar una conexión con execute() anteriormente.
        // getElementsByTag("p"): Obtiene todos los elementos que tengan una etiqueta <p>.
        // size(): Se obtiene la cantidad de párrafos que hay en el arreglo de elementos.
        Document docHTML = Jsoup.connect(pagina).get();
        int cantParrafos = docHTML.getElementsByTag("p").size();
        System.out.println("\nb) La cantidad de párrafos que contiene el documento HTML es: "+cantParrafos + "\n");


    }

}
