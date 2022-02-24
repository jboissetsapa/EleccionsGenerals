package projecte_base_dades;

import org.apache.commons.io.FileUtils;

import projecte.sapa.apartat4.DescomprimirZip;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        boolean descomprimit;
        try {
            while (!FileUtils.isEmptyDirectory(DescomprimirZip.origin)) {
                descomprimit = DescomprimirZip.descomprimirZip();





                DescomprimirZip.procesarZips(descomprimit);
            }
        } catch (IOException x){

        }

    }
}
