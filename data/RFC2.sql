SELECT *
FROM OPERADOR JOIN (SELECT ID_OPERADOR, SUM(GANANCIA) AS GANANCIA_TOTAL, SUM(VECES) AS VECES
FROM (SELECT contrato.id AS CONTRATO_ID, contratohabhotel.id_hotel AS ID_OPERADOR
FROM CONTRATO JOIN CONTRATOHABHOTEL
                ON CONTRATO.ID=CONTRATOHABHOTEL.ID_CONTRATO
      UNION                
SELECT contrato.id AS CONTRATO_ID, contratohabhostal.id_hostal AS ID_OPERADOR
FROM CONTRATO JOIN CONTRATOHABHOSTAL
                ON CONTRATO.ID=CONTRATOHABHOSTAL.ID_CONTRATO
        UNION
SELECT CONTRATO.ID AS CONTRATO_ID, contratohabuniversitaria.id_vivienda AS ID_OPERADOR
FROM CONTRATO JOIN CONTRATOHABUNIVERSITARIA
              ON CONTRATO.ID=CONTRATOHABUNIVERSITARIA.ID_CONTRATO
        UNION      
SELECT CONTRATO_ID, VIVIENDA_FAMILIAR.ID_PERSONA_NATURAL AS ID_OPERADOR
FROM VIVIENDA_FAMILIAR JOIN (SELECT CONTRATO.ID AS CONTRATO_ID , CONTRATO_HAB_VIVIENDA.ID_VIVIENDA AS ID_VIVIENDA
                            FROM CONTRATO JOIN CONTRATO_HAB_VIVIENDA
                                          ON CONTRATO.ID=CONTRATO_HAB_VIVIENDA.ID_CONTRATO)
                      ON VIVIENDA_FAMILIAR.ID=ID_VIVIENDA
        UNION
SELECT CONTRATO_ID, APARTAMENTO.ID_PERSONA_NATURAL AS ID_OPERADOR
FROM APARTAMENTO JOIN (SELECT CONTRATO.ID AS CONTRATO_ID , CONTRATO_APARTAMENTO.ID_APARTAMENTO AS ID_APARTAMENTO
                       FROM CONTRATO JOIN CONTRATO_APARTAMENTO
                                     ON CONTRATO.ID=CONTRATO_APARTAMENTO.ID_CONTRATO)
                      ON APARTAMENTO.ID=ID_APARTAMENTO
        UNION
SELECT CONTRATO_ID, VIVIENDA_FAMILIAR.ID_PERSONA_NATURAL AS ID_OPERADOR
FROM VIVIENDA_FAMILIAR JOIN (SELECT CONTRATO.ID AS CONTRATO_ID, CONTRATO_CLIENTE_ESPORADICO.ID_VIVIENDA AS ID_VIVIENDA
                            FROM CONTRATO JOIN CONTRATO_CLIENTE_ESPORADICO
                                          ON CONTRATO.ID=CONTRATO_CLIENTE_ESPORADICO.ID_CONTRATO)
                      ON VIVIENDA_FAMILIAR.ID=ID_VIVIENDA) UNIONES LEFT JOIN (
SELECT ID_CONT, SUM(COSTO) AS GANANCIA, COUNT(ID_CONT) AS VECES
FROM ( SELECT RES.ID AS ID_RES,CTS.ID AS ID_CONT,CTS.COSTO AS COSTO,RES.FECHA_REALIZACIOM AS FECHA_REALIZACIO
       FROM CONTRATO CTS JOIN (SELECT * 
			       FROM RESERVA
			       WHERE FECHA_REALIZACIOM LIKE '%2020%') RES
		         ON RES.ID_CONTRATO=CTS.ID) 
GROUP BY ID_CONT) GANANCIAS
ON GANANCIAS.ID_CONT=uniones.contrato_id
GROUP BY ID_OPERADOR)
              ON OPERADOR.ID=ID_OPERADOR
              ORDER BY GANANCIA_TOTAL ASC;