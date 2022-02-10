package projecte_base_dades;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;

public class DescomprimirZip {
    public static int comptador=0;
    public static String[] zips;
    public static File fileZip;
    public static File origin = new File ("origin");;



    public static char descomprimirZip(){

        //Obtenim el directori actual
        Path pathActual = Paths.get(System.getProperty("user.dir"));

        zips = origin.list();

        comptador = 0;
        while (comptador < 1) {
            //Concatenem el directori actual amb un subdirectori "dades" i afegim el fitxer "prova.zip"
            String nomFitxer = zips[0];

            String unzipDir = "temp";


            Path pathFitxer = Paths.get(pathActual.toString(), "origin", nomFitxer);
            Path pathUnzipDir = Paths.get(pathActual.toString(), unzipDir);

            // Create zip file stream.
            try (ZipArchiveInputStream fitxerZip = new ZipArchiveInputStream(
                    new BufferedInputStream(new FileInputStream(pathFitxer.toString())))) {

                ZipArchiveEntry entrada;
                while ((entrada = fitxerZip.getNextZipEntry()) != null) {
                    // Print values from entry.
                    System.out.println(entrada.getName());
                    System.out.println(entrada.getMethod()); // ZipEntry.DEFLATED is int 8

                    File file = new File(Paths.get(pathActual.toString(), unzipDir, entrada.getName()).toString());
                    System.out.println("Unzipping - " + file);

                    Files.createDirectories(pathUnzipDir);

                    // copiem el contingut del fitxer.
                    IOUtils.copy(fitxerZip, new FileOutputStream(file));

                }
                comptador++;
                return 'd';
            } catch (IOException e) {
                comptador++;
                continue;

            }
        }




        return 'd';
    }

    public static void procesarZips( char procesatCorrectament){
        boolean hiHaFitxers;
        File temporal = new File ("temp");

        fileZip = new File("origin\\" + zips[0]);
        File tractats = new File("tractats");
        File errors = new File("errors");

        try {
            if (procesatCorrectament == 'd') {
                FileUtils.moveFileToDirectory(fileZip,tractats,false);
            }else if (procesatCorrectament == 'e'){
                FileUtils.moveFileToDirectory(fileZip,errors,false);
            }

        } catch (IOException a) {
            a.printStackTrace();
        }


        try{
            while (!FileUtils.isEmptyDirectory(temporal)){
                String [] fixers = temporal.list();
                File documentAEliminar = new File( "temp\\"+ fixers[0]);
                FileUtils.deleteQuietly(documentAEliminar);
            }
        }catch (IOException x){
            x.printStackTrace();
        }


    }




}
