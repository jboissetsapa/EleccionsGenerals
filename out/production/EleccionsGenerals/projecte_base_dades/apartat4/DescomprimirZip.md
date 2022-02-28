### Llei d'hondt


La funció basic es:

        public static boolean descomprimirZip(){

        //Obtenim el directori actual
        Path pathActual = Paths.get(System.getProperty("user.dir"));

        zips = origin.list();

        //Concatenem el directori actual amb un subdirectori "dades" i afegim el fitxer "prova.zip"
        String nomFitxer = zips[0];
        zipId = (zips[0].substring(0,2) + zips[0].substring(4,zips[0].indexOf("_")));
        System.out.println(zipId);

        String unzipDir = "temp";

        Path pathFitxer = Paths.get(pathActual.toString(), "origen", nomFitxer);
        Path pathUnzipDir = Paths.get(pathActual.toString(), unzipDir);

        // Create zip file stream.
        try (ZipArchiveInputStream fitxerZip = new ZipArchiveInputStream(
                new BufferedInputStream(new FileInputStream(pathFitxer.toString())))) {

            ZipArchiveEntry entrada;
            while ((entrada = fitxerZip.getNextZipEntry()) != null) {

                File file = new File(Paths.get(pathActual.toString(), unzipDir, entrada.getName()).toString());

                Files.createDirectories(pathUnzipDir);

                // copiem el contingut del fitxer.
                IOUtils.copy(fitxerZip, new FileOutputStream(file));

            }

            return true;
        } catch (IOException e) {

            return  false;

        }


    }


La funcio per a moure els zips un cop tractats es:

    public static void procesarZips( boolean procesatCorrectament){
        boolean hiHaFitxers;
        File temporal = new File ("temp");

        fileZip = new File("origen\\" + zips[0]);
        File tractats = new File("tractats");
        File errors = new File("errors");

        try {
            if (procesatCorrectament) {
                FileUtils.moveFileToDirectory(fileZip,tractats,false);
            }else{
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
    
    
La primera funcio genera una variable,  zipId, que agafa el tipus d'eleccio i la data per a
cambiar el nom dels fixers .DAT automaticament
    
    
    
