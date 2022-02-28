### Llei d'hondt


Utilitzant la consulta:

        SELECT p.nom, sum(em.vots_emesos), sum(em.vots_blanc), sum(em.vots_nuls) 
            FROM eleccions_municipis em 
            INNER JOIN municipis m on m.municipi_id = em.municipi_id 
            INNER JOIN provincies p  on m.provincia_id = p.provincia_id  
            GROUP BY p.nom 
            HAVING p.nom = ?;


I la consulta:

        SELECT c.nom_curt, vp.candidats_obtinguts 
            FROM candidatures c 
            INNER JOIN vots_candidatures_prov vp ON c.candidatura_id = vp.candidatura_id 
            INNER JOIN provincies p ON p.provincia_id = vp.provincia_id 
            WHERE p.nom = ? 
            ORDER BY vp.candidats_obtinguts DESC;

Obtemim totes les dades necessaries per mostrar:
        
        PROVINCIA GIRONA
        -----------------------------
        Vots: XXXX
        Vots blanc: XXX
        Vots nuls: XXX
        Nom partit 1: 3
        Nom partit 2: 2
        Nom partit 3: 1
Utilitzant el System.out.print seguent les mostrem:

    Resultats obtinguts amb la primera select:

        String nomProv = rs.getString(1);
        int votsEmesos = rs.getInt(2);
        int votsBlanc = rs.getInt(3);
        int votsNuls = rs.getInt(4);

        System.out.println("PROVINCIA " + nomProv.toUpperCase(Locale.ROOT));
        System.out.println("----------------------");
        System.out.println("Vots: " + votsEmesos);
        System.out.println("Vots blanc: " + votsBlanc);
        System.out.println("Vots nuls: " + votsNuls);

    Resultats obtinguts amb la segona select:

        String nomCandidautra = rs.getString(1);
        int escons = rs.getInt(2);
        
        System.out.println(nomCandidautra + ": " + escons);