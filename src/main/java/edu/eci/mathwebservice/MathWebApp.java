package edu.eci.mathwebservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import spark.Request;
import static spark.Spark.get;
import static spark.Spark.port;

/**
 *
 * @author Carlos Medina
 */
public class MathWebApp {

    public static void main(String[] args) {
        port(getPort());
        get("/", (req, res) -> showForm());
        get("/square", (req, res) -> calculateValue(req));
    }

    private static String showForm() {
        String form = "<html>"
                + "<head>"
                + " <title>Math Web App</title>"
                + "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">"
                + " <script src=\"https://code.jquery.com/jquery-3.2.1.min.js\"\n"
                + "                integrity=\"sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=\"\n"
                + "                crossorigin=\"anonymous\"></script>"
                + "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\" integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\" crossorigin=\"anonymous\"></script>"
                + "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" crossorigin=\"anonymous\"></script>"
                + "</head>"
                + "<body>"
                + " <div class='container'>"
                + "     <div class='row'>"
                + "         <div class='col-md-3'></div>"
                + "         <div class='col-md-6'>"
                + "             <h2 style='text-align:center; margin-top:10%;'>Math Web Form</h2>"
                + "             <form style='margin-top:5%;' action='/square'>"
                + "                 <div class='form-group'>"
                + "                     <label for='inputNumero'>Ingrese el número a calcular el cuadrado:</label>"
                + "                     <input class='form-control' name='value' type='number' id='inputNumero' placeholder='Ingrese el número'>"
                + "                     <button class='btn btn-primary' type='submit' style='margin-top:5%;'>Enviar</button>"
                + "                 </div>"
                + "             </form>"
                + "         </div>"
                + "         <div class='col-md-3'></div>"
                + "     </div>"
                + " </div>"
                + "</body>"
                + "</html>";
        return form;
    }

    private static String calculateValue(Request req) {
        String param = req.queryParams("value");
        URL url = null;
        try {
            url = new URL("https://ksieitjb63.execute-api.us-east-1.amazonaws.com/Beta?value=" + param);
        } catch (MalformedURLException ex) {
            System.out.println("La URL no es correcta.");
        }
        BufferedReader bf = null;
        String line = "";
        String page = "";
        try {
            bf = new BufferedReader(new InputStreamReader(url.openStream()));
            line = bf.readLine();
            while (line != null) {
                page += line;
                line = bf.readLine();
            }
        } catch (IOException ex) {
            System.out.println("Error I/O");
        }
        return page;
    }

    static int getPort() {
        int port = 4567; //default port if heroku-port isn't set
        if (System.getenv("PORT") != null) {
            port = Integer.parseInt(System.getenv("PORT"));
        }
        return port;
    }
}
