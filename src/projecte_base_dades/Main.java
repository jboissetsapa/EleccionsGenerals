package projecte_base_dades;

import org.apache.commons.io.FileUtils;
import projecte_base_dades.apartat1.*;
import projecte_base_dades.apartat4.DescomprimirZip;
import projecte_base_dades.apartat5.lleidHondt;
import java.util.Scanner;
import java.io.IOException;


public class Main {

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        importarDadesZip();

        System.out.println("Vols realitzar la llei d'Hondt?");
        String desicio = scan.nextLine();
        System.out.println();
        if (desicio.equalsIgnoreCase("Si") || desicio.equalsIgnoreCase("Yes") || desicio.equalsIgnoreCase("S") ||
        desicio.equalsIgnoreCase("y")) lleiDhondt();
    }

    public static void importarDadesZip() {
        int bucle = 1;
        boolean descomprimit;

        try {
            while (!FileUtils.isEmptyDirectory(DescomprimirZip.origin)) {
                descomprimit = DescomprimirZip.descomprimirZip();
                if (ImportComunAutonom.comprovar()) {
                    ImportComunAutonom.importarDades();
                    System.out.println("Importació de comunitats autònomes fet");
                    ImportProvincies.importarDades();
                    System.out.println("Importació de provincies fet");
                    ImportMunicpis.importarDades();
                    System.out.println("Importació de municipis fet");
                }
                ImportDadesBasiques.importarDades();
                System.out.println("Importació de eleccions fet");
                ImportEleccionsMunicpis.importarDades();
                System.out.println("Importació de eleccions municipals fet");
                ImportCandidatures.importarDades();
                System.out.println("Importació de candidatures fet");
                ImportarPersones.importarDades();
                System.out.println("Importació de persones fet");
                ImportarCandidats.importarDades();
                System.out.println("Importació de candidats fet");
                ImportVotsAutonomic.importarDades();
                System.out.println("Importació de vots autonòmics fet");
                ImportVotsProvincials.importarDades();
                System.out.println("Importació de vots provincials fet");
                /*ImportVotsMunicipals.importarDades();
                System.out.println("Importació de vots municipals fet");*/

                DescomprimirZip.procesarZips(descomprimit);
                bucle++;
            }
        } catch (IOException x){
        }
    }

    public static void lleiDhondt() {

        int repeteix;

        do {
            try {
                System.out.println("Escriu la província:");
                String provincia = scan.nextLine();
                provincia.trim();
                lleidHondt.resultatProvincies(provincia);

                System.out.println("Vols mirar una altra?");
                String desicio = scan.nextLine();

                if (desicio.equalsIgnoreCase("Si") || desicio.equalsIgnoreCase("Yes") ||
                        desicio.equalsIgnoreCase("S") || desicio.equalsIgnoreCase("y")) {
                    repeteix = 1;
                } else repeteix = 0;

            } catch (Exception e) {
                System.out.println("La província no es correcte. Si us plau, introdueixi una província amb la primera lletra en majúscula");
                repeteix = 1;
            }
        }while (repeteix == 1);

    }
}
