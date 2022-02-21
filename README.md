# backendSr

Demo API Marvel

La solución incluye los siguientes artefactos: 

Luego de clonar los fuentes, deberá seguir los siguientes pasos:

1 . Creacioón de network, se debe ejecutar el siguiente comando, esta red permite que los contenedores en docker se puedan comunicar directamente, 
por lo que las referencias a ips cambian al nombre del servicio en las configuraciones:

	docker network create marvel-network
	
2 . Creación de base de datos. Para efectos prácticos, se realizará una instalación Express de MS SQL 2019, se adjunta el docker-compose con la imagen
	este archivo se encuentra en el directorio /ms-sql de este repositorio.
	
	Contenido de docker-compose.yml
	
		version: "3.1"

		services:
		  sqlserver:
			image: mcr.microsoft.com/mssql/server:2019-CU3-ubuntu-18.04
			user: '0:0'
			container_name: sqlserver2019
			ports:
			  - 1433:1433
			environment:
			  ACCEPT_EULA: Y
			  SA_PASSWORD: 123456789!aA
			  MSSQL_PID: Express
			volumes:
			  - C:\Users\HP\Documents\volumes\ms-sql_data:/var/opt/mssql/data
		networks:
		  default:
			external: true
			name: marvel-network
			
	Se usa usuario 'sa' y clave 123456789!aA, esquema master.dbo, se recomienda antes de iniciar el contenedor, cambiar la ruta del volumen.

	Para construir la imagen e iniciar el contenedor, se debe ejecutar el siguiente comando:
	
		docker-compose up -d --build


3 .  Creación de Tablas users y transaction_log, se debe ingresar a base de datos, previamente iniciada docker y ejecutar los siguientes scripts DDL.

		-- master.dbo.users definition

		-- Tabla para el control de acceso de usuarios con JWT en API 

		CREATE TABLE master.dbo.users (
			id int IDENTITY(0,1) NOT NULL,
			username varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
			password varchar(200) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
			status bit NULL,
			created_date datetime DEFAULT getdate() NULL,
			CONSTRAINT user_PK PRIMARY KEY (id),
			CONSTRAINT user_UN UNIQUE (username)
		);
		
		CREATE UNIQUE NONCLUSTERED INDEX user_UN ON master.dbo.users (username);
		
		
		-- master.dbo.transaction_log definition

		-- Historial de consultas realizadas

		CREATE TABLE master.dbo.transaction_log (
			id int IDENTITY(0,1) NOT NULL,
			username varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
			api varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
			api_method varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
			response_code varchar(5) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
			response_description varchar(1000) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
			created_date datetime DEFAULT getdate() NULL,
			uuid varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
		);
		


4 . Complicación de Artefactos. Ubicarse en cada uno de los directorios ejecutar el comando "mvn clean install", en el siguiente orden de carpetas:

	- marvel-common-lib/
	- service-discovery-marvel/
	- config-server-marvel/
    - api-middleware-marvel/
	- api-mobile-marvel/
	
	
5 . Crear las imagenes de Springboot ejecutando el siguiente comando en el directorio donde se encuentra el archivo dokerfile de cada artefacto.

	comando: docker build -t springboot_app_name:tag .
		
	Por ejemplo para generar la imagen service-discovery-marvel con un tag para el dia 21-02-2022 se deberá ejecutar el comando de la siguiente manera:
		
	docker build -t service-discovery-marvel:20220221.01 .
	
	Y asi para el resto de proyectos:

- 	docker build -t config-server-marvel:20220221.01 .

- 	docker build -t api-middleware-marvel:20220221.01 .

- 	docker build -t api-mobile-marvel:20220221.01 .


6 . Ejecutar docker-compose, para iniciar los servicios de discovery y config, asi como los dos API de consumo de servicios Marvel(R), se debe ubicar en la raiz del repositorio descargado
    y ejecutar el siguinete comando:
	
	"docker-compose up -d"
	
	¡Importante!: Debe cambiar la referencia de las rutas fisicas a las carpetas de volumenes que tiene configurado cada contendor en el archivo docker-compose.
	
	
## Authentication JWT

	* Login y generación de Token
	
	El API api-mobile-marvel, cuenta con autenticación de usuarios, por lo que se requiere generar token JWT, ingresando nombre de usuario y contraseña.

	POST http://localhost:8082/v1.0/auth
	
	Request Body:  {
			  "password": "miclave",
			  "username": "miusuario"
			}
			
	El servicio genera la siquiente respuesta, donde tomaremos el access_token, para agregarlo en un header Authorization como token tipo Bearer.
	
	Response Body	
	{
	  "access_token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjIiLCJleHAiOjE2NDU0NDAyMDgsImlhdCI6MTY0NTQzNjYwOH0.ea-F13ix-ObZ7LuVSriF1HPQ_1TlmYsvgoS-fwd1wNQyDvhjjjfJND1Zr9yawskrsiF00ExfDoCq_TqHe9KElw",
	  "userName": "miusuario",
	  "type": "Bearer",
	  "issued_at": 1645436608996,
	  "expires_in": "3600000"
	}
	
	* Creacion de Usuarios
	
	Para crear un nuevo usuario para poder consultar el API se tiene el servicio, si no esta registrado un nombre de usuario permite la creación de lo contrario nnodeja registrar usuarios repetidos.
	
	POST: http://localhost:8082/v1.0/signup
	Body:
			{
			  "password": "otraclave",
			  "username": "utrousuario"
			}
			
			
## Documentación de API

	Para referencia de API, de deja a disposición Swagger en la ruta, donde se puede consultar los parametros de entrada y tipos de error principales de cada microservicio.
	
	http://localhost:8082/v1.0/swagger-ui.html#/ 