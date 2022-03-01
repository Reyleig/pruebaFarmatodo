# pruebaFarmatodo
 para correr la prueba se agregan los scripts a la bd que estan en la carpeta static/sql
 el el proyecto tiene por defecto esta configuracion de conexion a bd 
url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: admin

las urls para consumir los api son los siguientes:
para obtener los datos de los episodios, se maneja una peticion get con un pathParam al final de la url 
    http://localhost:2900/prueba/getDataEpisode/{id}
    
    ** Ejemplo: http://localhost:2900/prueba/getDataEpisode/28

para obtener los numeros felices se maneja peticion post se recibe un json como el siguiente
    http://localhost:2900/prueba/happyNumber

    {
        "numbers": [
                        6,
                        33,
                        331,
                        2019,
                        123
                    ]
    }

para la sumatoria de los numeros se maneja una peticion get con un pathParam al final de la url
    http://localhost:2900/prueba/sumNaturalNumbers/{number}   
        *** Ejemplo:http://localhost:2900/prueba/sumNaturalNumbers/5   
