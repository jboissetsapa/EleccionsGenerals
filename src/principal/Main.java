package projecte_base_dades;

import org.apache.commons.io.FileUtils;
import java.io.IOException;
import projecte_base_dades.activitat.insertx;


public class Main {

    public static void main(String[] args) {





        char descomprimit = 'e';
        try {
            while (!FileUtils.isEmptyDirectory(DescomprimirZip.origin)) {
                descomprimit = DescomprimirZip.descomprimirZip();




                DescomprimirZip.procesarZips(descomprimit);
            }
        } catch (IOException x){

        }


    }
}
