### CATEGORÍA 1


1.1
Selecciona la **candidatura_id, nom_curt i nom_llarg** de les candidatures que tinguin la paraula 'partit','partido',...en el seu nom llarg.

        SELECT candidatura_id, nom_curt, nom_llarg
            FROM candidatures
        WHERE nom_llarg REGEXP "PARTIDO | PARTIT";

1.2
Selecciona el número de **candidatures i la eleccio_id** on la seva eleccio_id = 1, nombra les columnes “Num. Candidatures” i “Eleccio”.

        SELECT COUNT(candidatura_id) AS "Num. Candidatures" , eleccio_id AS "Eleccio"
            FROM candidatures
        WHERE eleccio_id = 1
        GROUP BY eleccio_id;


1.3
Selecciona el **municipi_id, nom** de tots els municipis on provincia_id = 17.
(*COMPROVAR QUE FUNCIONA*)

        SELECT municipi_id, nom
            FROM municipis
        WHERE provincia_id = 17;

1.4
Selecciona la **comunitat_aut_id i el número de provincies** de les comunitats autonomes que tinguin mes de 5 provincies.

        SELECT comunitat_aut_id, COUNT(provincia_id)
            FROM provincies
        GROUP BY comunitat_aut_id
        HAVING COUNT(provincia_id) > 5;

1.5
Selecciona de les **candidatures_id i la suma dels vots de les comunitats autonomes**, les candidatures de les quals han rebut mes de 100.000 vots en la suma de totes les comunitats autonomes, selecciona nomes les 5 que tenen mes vots.

        SELECT candidatura_id, SUM(vots)
            FROM vots_candidatures_ca
        GROUP BY candidatura_id
        HAVING SUM(vots) > 100000
        ORDER BY SUM(vots) DESC
        LIMIT 5;

1.6
Seleccione la **persona_id i el seu nom en format** *“Cog1 Cog2, nom”* que tenen el sexe asignat a dona i han nascut entre el 1960 i 1980.
(*COMPROVAR SI FUNCIONA*)

        SELECT persona_id, CONCAT(cognom1," ", cognom2,"," ,nom) AS "Nom Complet"
        FROM persones
        WHERE sexe = 'F' AND YEAR(data_naixement) BETWEEN 1960 AND 1980;

1.7
Selecciona de la **provincia 52, els candidats, vots i candidats obtinguts**.
Si vots < 10.000 asigna a la columna “Baix”, entre 10.000 i 30.000 = Mig, mes de 30.000 = “Alt”. Anomena la columna “Nivell Vots”

        SELECT provincia_id, candidatura_id,
        		CASE
                    WHEN vots < 10000 THEN "Baix"
                    WHEN vots BETWEEN 10000 AND 35000 THEN "Mig"
                    WHEN vots > 35000 THEN "Alt"
                        END AS "Nivell Vots", candidats_obtinguts
        	FROM vots_candidatures_prov
        WHERE provincia_id = 52;

1.8
Selecciona la mitjana de vots que ha obtingut la candidatura = 20 en la la comunitat autonoma 7.

---

### CATEGORIA 2

---

### CATEGORIA 3

---

### CATEGORIA 4

Mostra tota la taula de vots de comunitat autonoma i la menor i major quantitat de vots obtinguts per una candidatura a nivell comunitat autonomica,
ordena-ho per la comunitat autonoma i posa els alies seguents:
ID Comunitat Autonoma, Candidatura ID, Vots, Menor quantitat de vots aconseguits per candidatura en total i Mayor quantitat de vots aconseguits per candidatura en total.

SELECT comunitat_autonoma_id as "ID Comunitat Autonoma", candidatura_id as "Candidatura ID", vots as Vots
,IFNULL(NTH_VALUE(vots, 1) over (partition by candidatura_id order by vots ASC), "") as "Menor quantitat de vots aconseguits per candidatura en total"
,IFNULL(NTH_VALUE(vots, 1) over (partition by candidatura_id order by vots DESC), "") as "Mayor quantitat de vots aconseguits per candidatura en total"
FROM vots_candidatures_ca
ORDER BY comunitat_autonoma_id;

