import org.jsoup.Connection;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;

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

        //c) Indicar la cantidad de imágenes <img> dentro de los párrafos del HTML.
        //select(): Filtra seleccionando  desde un elemento en específico, en este caso los párrafos y busca dentro de ellos <img> que es la etiqueta de imágenes.
        System.out.println("c) La cantidad de imágenes dentro de los párrafos de HTML es: " + docHTML.select("p img").size());

        //d) Indicar la cantidad de formularios <form> que contiene el HTML categorizado por el método POST o GET.
        //form.attr: Facilita para poder tomar un atributo que tenga una etiqueta <form>.
        //equalsIgnoreCase: Facilita para poder ver a qué String está igualado sin tomar en cuenta mayúscula o minúscula.
        //FormElement: Objeto para los formularios.
        //getElementsByTag: Obtiene todos los elementos con el nombre de la etiqueta específica.
        int[] cantGetPost = new int[]{0,0};
        for(FormElement form : docHTML.getElementsByTag("form").forms()){
            if(form.attr("method").equals("get")){
                cantGetPost[0]++;
            }
            if(form.attr("method").equalsIgnoreCase("post")){
                cantGetPost[1]++;
            }
        }

        System.out.println("\n\nd) " +
                "\n     La cantidas de formularios que usan el método GET es: " + cantGetPost[0] +
                "\n     La cantidad de formularios que usan el método POST es: " + cantGetPost[1]);


        int contInput = 1;
        int countForm = 1;

        //e) Para cada formulario mostrar los campos de tipo input y su respectivo tipo que contiene en el documento HTML.
        System.out.println("\n\ne) Inputs dentro de los forms con sus tipos: ");
        System.out.println("Número del Formulario\t| Número del Input\t| Tipo del Input ");
        System.out.println("------------------------------------------------------------");
        for(FormElement form : docHTML.getElementsByTag("form").forms()){
            for(Element inputsEncontrados: form.getElementsByTag("input")){
                System.out.println("    Formulario #" + countForm + "\t\t\t\tInput #" + contInput + "\t\t\t" + inputsEncontrados.attr("type"));
                contInput++;

            }
            countForm++;
        }

        int contForm = 1;
        Document docNuevoImprimir;

        System.out.println("\n\nf) La petición al servidor con los parámetros y el header dado: ");
        for(Element form: docHTML.getElementsByTag("form").forms()){
            Elements tipoMetodoPost = form.getElementsByAttributeValueContaining("method", "post");
            if(tipoMetodoPost.isEmpty()){
                System.out.println("No existe ningún form con el método POST. ");
            }
            for(Element elementPost : tipoMetodoPost){
                try{
                    System.out.println("El formulario #" + contForm + " encontrado: ");
                    String conseguirURLAbsoluta = elementPost.absUrl("action");
                    docNuevoImprimir = Jsoup.connect(conseguirURLAbsoluta).data("asignatura", "practica1")
                            .header("matricula", "20140795").post();
                    System.out.println("Resultado: ");
                    System.out.println("\n\n");
                    System.out.println(docNuevoImprimir.body().toString());

                }
                catch (IOException e){
                    e.printStackTrace();

                }
            }
            contForm++;
        }



    }

}
