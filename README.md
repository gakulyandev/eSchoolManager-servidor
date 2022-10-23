# eSchoolManager-servidor

Projecte de servidor escoltant de peticions per al Projecte final del CFGS Desenvolupament d'aplicacions multiplataforma (DAM) que gestiona un centre educatiu de necessitats especials.


**Execució del servidor**

1. Clona el repositori:\
`$ git clone https://github.com/gakulyandev/eSchoolManager-servidor.git eSchoolManager`

2. Crea un fitxer amb variables d'entorn a la carpeta eSchoolManager amb les següents dades:\
`PORT=<port exposat>`\
`MYSQL_HOST=<nom del host mysql>`\
`MYSQL_DATABASE=<nom de la base de dades>`\
`MYSQL_USER=<nom de l'usuari>`\
`MYSQL_PASSWORD=<contrasenya>`

3. Executa els contenidors (necessari tenir docker instal·lat):\
`$ cd eSchoolManager`\
`$ docker-compose up -d`

4. Comprova el funcionament:\
`$ cd docker logs esm_server`\
Resposta esperada:\
`Esperant client...`
 
