package menus;

import controllers.EscribirXML;
import controllers.LeerXML;

import java.nio.file.Path;
import java.util.Scanner;

import static libs.UserData.pedirRutaXML;

public class MenuPrincipal {
    private boolean salir = false;
    private Scanner sc = new Scanner(System.in);
    private LeerXML leerXML = new LeerXML();
    private EscribirXML escribirXML = new EscribirXML();

    public void muestraMenu() {
        String opcion;
        do {
            System.out.println("Elige una opcion:");
            System.out.println("1. Leer todos los nodos del fichero XML.");
            System.out.println("2. Leer los nodos con contenido de texto del fichero XML.");
            System.out.println("3. Leer sabiendo las etiquetas.");
            System.out.println("4. Escribir XML.");
            System.out.println("0. Salir");
            opcion = this.pideOpcion();
            this.procesaOpcion(opcion);
        } while (!salir);
    }

    private String pideOpcion() {
        return this.sc.nextLine();
    }

    private void procesaOpcion(String opcion) {
        switch (opcion) {
            case "0" -> salir = true;

            case "1" -> {
                Path p = pedirRutaXML("Introduce la ruta del archivo: ");
                System.out.println(leerXML.leerTodosNodos(p));
                }
            case "2" -> {
                Path p = pedirRutaXML("Introduce la ruta del archivo: ");
                System.out.println(leerXML.leerArchivoFiltrado(p));
            }
            case "3" -> {
                Path p = pedirRutaXML("Introduce la ruta del archivo: ");
                System.out.println(leerXML.leerNodosConClases(p));
            }
            case "4" -> {
                Path p = pedirRutaXML("Introduce el nombre del archivo a crear: ");
                System.out.println(escribirXML.escribir(p));
            }
            default -> System.out.println("Opción incorrecta");
        }
    }
}
