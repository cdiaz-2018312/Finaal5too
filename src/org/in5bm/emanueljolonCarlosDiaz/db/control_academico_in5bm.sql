DROP DATABASE IF EXISTS control_academico_in5bm;
CREATE DATABASE control_academico_in5bm;
USE control_academico_in5bm;

-- -------------------------------------------------DDL ----------------------------------------------------------------

CREATE TABLE alumnos(
carne VARCHAR(16) not null,
nombre1 VARCHAR(16) not null,
nombre2 VARCHAR (16),
nombre3 VARCHAR (16),
apellido1 VARCHAR(16),
apellido2 VARCHAR (16) NOT NULL,
PRIMARY KEY (carne)
);
CREATE TABLE salones(
id_salon VARCHAR(5)  NOT NULL,
descripcion  VARCHAR(45),
capacidad_max INT ,
edificio VARCHAR(16),
nivel INT,
PRIMARY KEY (id_salon)
);


CREATE TABLE carreras_tecnicas(
codigo_tecnico VARCHAR (10) NOT NULL,
carrera VARCHAR (45) NOT NULL,
grado VARCHAR(10) NOT NULL,
seccion CHAR(1) not null,
jornada VARCHAR(10) not null,
PRIMARY KEY (codigo_tecnico)
);



DROP TABLE IF EXISTS instructores;
create table instructores(
id_instructor INT NOT NULL AUTO_INCREMENT,
nombre1 VARCHAR (15) not null,
nombre2 VARCHAR (15),
nombre3 VARCHAR (15),
apellido1 VARCHAR (15) not null,
apellido2 VARCHAR (15),
direccion VARCHAR (50),
email VARCHAR (45) not null,
telefono VARCHAR (25) not null,
fecha_de_nacimiento VARCHAR (20),
PRIMARY KEY (id_instructor)
);

DROP TABLE IF EXISTS horarios;
CREATE TABLE horarios (
id_horario INT NOT NULL AUTO_INCREMENT,
horario_inicio TIME NOT NULL,
horario_final TIME NOT NULL,
lunes TINYINT(1),
martes TINYINT(1),
miercoles TINYINT(1),
jueves TINYINT(1),
viernes TINYINT(1),
PRIMARY KEY (id_horario)
);

DROP TABLE IF EXISTS cursos;
CREATE TABLE cursos(
id_curso INT NOT NULL AUTO_INCREMENT,
nombre_curso VARCHAR(16) NOT NULL,
ciclo YEAR,
cupo_maximo INT,
cupo_min INT,
carrera_tecnica_id VARCHAR(15) NOT NULL,
horario_id INT not null,
salon_id VARCHAR(5) not null,
instructor_id int not null,
PRIMARY KEY (id_curso),
CONSTRAINT fk_carrera_tecnica_id
FOREIGN KEY (carrera_tecnica_id) REFERENCES carreras_tecnicas (codigo_tecnico)
ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_horario_id
FOREIGN KEY (horario_id) REFERENCES horarios (id_horario)
ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_salon_id
FOREIGN KEY (salon_id) REFERENCES salones (id_salon)
ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_instructor_id
FOREIGN KEY (instructor_id) REFERENCES instructores(id_instructor)
ON delete cascade on update cascade 
);

DROP TABLE IF EXISTS asignacion_alumnos;
CREATE TABLE asignacion_alumnos(
id INT NOT NULL auto_increment,
alumno_id VARCHAR(16) NOT NULL,
curso_id INT NOT NULL,
fecha_asignacion DATETIME,
PRIMARY KEY(id),
CONSTRAINT fk_alumno_id
FOREIGN KEY (alumno_id)
REFERENCES alumnos(carne)
ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_curso_id
FOREIGN KEY (curso_id)
REFERENCES cursos(id_curso)
ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS rol; 
CREATE TABLE IF NOT EXISTS rol (
id INT NOT NULL,
descripcion VARCHAR (50) NOT NULL,
PRIMARY KEY (id)
);

DROP TABLE IF EXISTS usuario;
CREATE TABLE IF NOT EXISTS usuario (
usuario VARCHAR (20) NOT NULL,
pass VARCHAR (10),
nombre VARCHAR (50) NOT NULL,
rol_id INT NOT NULL,
PRIMARY KEY (usuario),
CONSTRAINT fk_usuario_rol FOREIGN KEY (rol_id) REFERENCES rol(id)
);


-- Procedimientos almacenados
-- Alumnos-------------------------------------------------------
-- alumnos read
DELIMITER $$

DROP PROCEDURE
 IF  EXISTS sp_alumnos_create$$

	CREATE PROCEDURE sp_alumnos_create(IN _carne VARCHAR(16), IN _nombre1 VARCHAR(16), IN _nombre2 VARCHAR(16),
		IN _nombre3 VARCHAR(16), IN _apellido1 VARCHAR(16), IN _apellido2 VARCHAR(16))
	BEGIN
		INSERT INTO alumnos (carne,nombre1,nombre2,nombre3,apellido1,apellido2)
		VALUES(_carne,_nombre1,_nombre2,_nombre3,_apellido1,_apellido2);
END $$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_read$$

	CREATE PROCEDURE sp_alumnos_read()
	BEGIN
		SELECT * FROM alumnos;
END $$
DElimiter ;

-- READ BY ID

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_read_id$$
	CREATE PROCEDURE sp_alumnos_read_id(IN _carne VARCHAR(16))
	BEGIN
		SELECT * FROM alumnos WHERE alumnos.carne = _carne;
END $$ 
DElimiter ;




-- Update alumnos

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_update$$

	CREATE PROCEDURE sp_alumnos_update(IN _carne VARCHAR(16),IN _nombre1 VARCHAR(16), IN _nombre2 VARCHAR(16), IN _nombre3 VARCHAR(16),
		IN _apellido1 VARCHAR(16), IN _apellido2 VARCHAR(16))
	BEGIN
		UPDATE alumnos SET
		nombre1 = _nombre1,
		nombre2 = _nombre2,
		nombre3 = _nombre3,
		apellido1 = _apellido1,
		apellido2 = _apellido2
		WHERE carne = _carne;
END $$
DElimiter ; 

-- alumnos delete

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_delete$$

	CREATE PROCEDURE sp_alumnos_delete(IN _carne VARCHAR(16))
	BEGIN
		DELETE FROM alumnos WHEre carne = _carne;
END $$ 
DElimiter ;

-- Salones----------------------------------------------------------------------------------------------------------------------------------
-- salones Create-------------------------------------------------

DELIMITER $$
DROP PROCEDURE IF  EXISTS sp_salones_create $$
	CREATE PROCEDURE sp_salones_create(IN _id_salon VARCHAR(5), IN _descripcion VARCHAR(45), 
		IN _capacidad_max INT, IN _edificio VARCHAR (16), IN _nivel INT)
	BEGIN
		INSERT INTO salones (id_salon,descripcion,capacidad_max,edificio,nivel)
		VALUES(_id_salon,_descripcion,_capacidad_max,_edificio,_nivel);
END $$
DElimiter ;

-- Salones read

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read$$
	CREATE PROCEDURE sp_salones_read()
	BEGIN
		SELECT * FROM salones;
END $$
DElimiter ;

-- READ BY ID
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read_id$$

	CREATE PROCEDURE sp_salones_read_id(IN _id_salon VARCHAR(5))
	BEGIN
		SELECT * FROM salones WHERE id_salon = _id_salon;
END $$
DElimiter ;

-- salones update
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_update$$

	CREATE PROCEDURE sp_salones_update(IN _descripcion VARCHAR(45), 
    IN _capacidad_max INT, IN _edificio VARCHAR (16), IN _nivel INT , IN _id_salon VARCHAR(5))
	BEGIN
	UPDATE salones SET
    descripcion = _descripcion,
    capacidad_max = _capacidad_max,
    edificio = _edificio,
    nivel = _nivel
    WHERE
    id_salon = _id_salon;
END $$
DElimiter ;

-- salones delete
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_delete$$
	CREATE PROCEDURE sp_salones_delete(IN _id_salon VARCHAR(5))
	BEGIN
		DELETE FROM salones
		WHERE
		id_salon = _id_salon;
END $$
DElimiter ;

-- carreras Tecnicas------------------------------------------------------------------------------------------

-- carreras tecnicas create
DELIMITER $$
DROP PROCEDURE IF  EXISTS sp_carreras_tecnicas_create$$
	CREATE PROCEDURE sp_carreras_tecnicas_create(IN _codigo_tecnico VARCHAR(10), IN _carrera VARCHAR(45), 
		IN _grado VARCHAR(10), IN _seccion CHAR(1), IN _jornada VARCHAR (10))
	BEGIN
		INSERT INTO carreras_tecnicas (codigo_tecnico,carrera,grado,seccion,jornada)
		VALUES(_codigo_tecnico,_carrera,_grado,_seccion,_jornada);
END $$
     
     -- carreras tecnicas read
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read$$
	CREATE PROCEDURE sp_carreras_tecnicas_read()
	BEGIN
		SELECT * FROM carreras_tecnicas;
END $$
DElimiter ;

-- READ BY ID

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read_id $$
	CREATE PROCEDURE sp_carreras_tecnicas_read_id(IN _codigo_tecnico VARCHAR(10))
	BEGIN
		SELECT * FROM carreras_tecnicas WHERE codigo_tecnico = _codigo_tecnico;
END $$
DElimiter ;

--  carreras_tecnicas update
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_update$$

	CREATE PROCEDURE sp_carreras_tecnicas_update(IN _carrera VARCHAR(45), 
		IN _grado VARCHAR(10), IN _seccion CHAR(1), IN _jornada VARCHAR (10), IN _codigo_tecnico VARCHAR(8))
	BEGIN
		UPDATE carerras_tecnicas SET
		carrera = _carrera,
		grado = _grado,
		seccion = _seccion,
		jornada = _jornada
		WHERE
		codigo_tecnico = _codigo_tecnico;
END $$
DElimiter ;

-- DELETE carreras_tecnicas
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_delete$$

	CREATE PROCEDURE sp_carreras_tecnicas_delete(IN _codigo_tecnico VARCHAR(10))
	BEGIN
		DELETE FROM carreras_tecnicas
		WHERE
		codigo_tecnico = _codigo_tecnico;
END $$
DElimiter ;



-- Instructores ----------------------------------------------------------------------------

DELIMITER $$
DROP PROCEDURE IF  EXISTS sp_instructores_create$$
	CREATE PROCEDURE sp_instructores_create(IN _nombre1 VARCHAR(15), IN _nombre2 VARCHAR(15),
		IN _nombre3 VARCHAR(15), IN _apellido1 VARCHAR(15), IN _apellido2 VARCHAR(15),
		IN _direccion VARCHAR(50), IN _email VARCHAR(45), IN _telefono VARCHAR(25), IN _fecha_de_nacimiento VARCHAR(20))
	BEGIN
		INSERT INTO instructores (nombre1,nombre2,nombre3,apellido1,apellido2,direccion,email,telefono,fecha_de_nacimiento)
		VALUES(_nombre1,_nombre2,_nombre3,_apellido1,_apellido2,_direccion,_email,_telefono,_fecha_de_nacimiento);
END $$
Delimiter ;

-- Instructores Read 
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read$$
	CREATE PROCEDURE sp_instructores_read()
	BEGIN
		SELECT * FROM instructores;
END $$
DElimiter ;
-- Instructores Read by id
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read_id $$

	CREATE PROCEDURE sp_instructores_read_id(IN _id_instructor INT)
	BEGIN
		SELECT * FROM instructores WHERE id_instructor= _id_instructor;
END $$
DElimiter ;

--  INSTRUCTORES update

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_update $$
	CREATE PROCEDURE sp_instructores_update(IN _nombre1 VARCHAR(15), IN _nombre2 VARCHAR(15),
		IN _nombre3 VARCHAR(15), IN _apellido1 VARCHAR(15), IN _apellido2 VARCHAR(15),
		IN _direccion VARCHAR(50), IN _email VARCHAR(45), IN _telefono VARCHAR(25), IN _fecha_de_nacimiento VARCHAR(20), IN _id_instructor INT)
	BEGIN
		UPDATE instructores SET
		nombre1 = _nombre1,
		nombre2 = _nombre2,
		nombre3 = _nombre3,
		apellido1 = _apellido1,
		apellido2 = _apellido2,
		direccion = _direccion,
		email = _email,
		telefono = _telefono,
		fecha_de_nacimiento =_fecha_de_nacimiento
		WHERE id_instructor = _id_instructor;
END $$
DElimiter ;


-- instructores DELETE 
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_delete $$
	CREATE PROCEDURE sp_instructores_delete (IN _id_instructor INT)
	BEGIN
		DELETE FROM instructores WHERE id_instructor =_id_instructor;
END $$ 
DElimiter ;

-- horarios -----------------------------------------------------------------------
-- horarios create
DELIMITER $$
DROP PROCEDURE IF  EXISTS sp_horarios_create $$
	CREATE PROCEDURE sp_horarios_create(IN _horario_inicio TIME, 
		IN _horario_final TIME, IN _lunes TINYINT, IN _martes TINYINT, IN _miercoles TINYINT,
		IN _jueves TINYINT, IN _viernes TINYINT)
	BEGIN
		INSERT INTO horarios (horario_inicio,horario_final,lunes,martes,miercoles,
		jueves,viernes)
		VALUES(_horario_inicio,_horario_final,_lunes,_martes,_miercoles,_jueves,viernes);
END $$
        
 -- Horarios Read 
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_read$$
	CREATE PROCEDURE sp_horarios_read()
	BEGIN
		SELECT * FROM horarios;
END $$
DElimiter ;

-- horarios Read by id

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_read_id$$
	CREATE PROCEDURE sp_horarios_read_id(IN _id_horario INT)
	BEGIN
		SELECT * FROM horarios WHERE id_horario = _id_horario;
END $$
DElimiter ;

-- horarios Update 
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_update$$

	CREATE PROCEDURE sp_horarios_update (IN _horario_inicio TIME, 
		IN _horario_final TIME, IN _lunes TINYINT, IN _martes TINYINT, IN _miercoles TINYINT,
		IN _jueves TINYINT, IN _viernes TINYINT, IN _id INT)
	BEGIN
		UPDATE horarios SET
		horario_inicio = _horario_inicio,
		horario_final = _horario_final,
		lunes =  _lunes,
		martes = _martes,
		miercoles = _miercoles,
		jueves = _jueves,
		viernes = _viernes
		WHERE
		id_horario = _id_horario;
END $$
DElimiter ;

-- DELETE horarios

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_delete$$
	CREATE PROCEDURE sp_horarios_delete(IN _id_horario INT)
	BEGIN
		DELETE FROM horarios
		WHERE
		id_horario = _id_horario;
END $$
DElimiter ;

-- cursos--------------------------------------------------------------------------------
-- cursos create


DELIMITER $$
DROP PROCEDURE IF  EXISTS sp_cursos_create$$

	CREATE PROCEDURE sp_cursos_create(IN _nombre_curso VARCHAR(16), IN _ciclo YEAR,
		IN _cupo_max INT , IN _cupo_min INT, IN _carrera_tecnica_id VARCHAR(15),
		IN _horario_id INT, IN _instructor_id INT, IN _salon_id VARCHAR(5))
	BEGIN
		INSERT INTO cursos (nombre_curso,ciclo,cupo_max,cupo_min,carrera_tecnica_id,
		horario_id,instructor_id,salon_id)
		VALUES(_nombre_curso,_ciclo,_cupo_max,_cupo_min,_carrera_tecnica_id,_horario_id
		,_instructor_id, _salon_id);
END $$
  DElimiter ;

  -- cursos Read 
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read$$

	CREATE PROCEDURE sp_cursos_read()
	BEGIN
		SELECT * FROM cursos;
END $$
DElimiter ;

-- curso Read by id
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_cursos_read_id$$
	CREATE PROCEDURE sp_cursos_read_id(IN _id_curso INT)
	BEGIN
		SELECT * FROM cursos WHERE id_curso = _id_curso;
END $$  

-- cursos UPDATE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_update$$

	CREATE PROCEDURE sp_cursos_update (IN _nombre_curso VARCHAR(16), IN _ciclo YEAR,
		IN _cupo_max INT , IN _cupo_min INT, IN _carrera_tecnica_id VARCHAR(15),
		IN _horario_id INT, IN _instructor_id INT, IN _salon_id INT, IN _id_curso INT)
	BEGIN
		UPDATE cursos SET
		nombre_curso = _nombre_curso,
		ciclo = _ciclo,
		cupo_max = _cupo_max,
		cupo_minimo = _cupo_min,
		carrera_tecnica_id = _carrera_tecnica_id,
		horario_id = _horario_id,
		instructor_id = _instructor_id,
        salon_id = _salon_id
		WHERE
		id_curso = _id_curso;
END $$
DElimiter ;

-- DELETE cursos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_delete$$

	CREATE PROCEDURE sp_cursos_delete(IN _id_curso INT)
	BEGIN
		DELETE FROM cursos
		WHERE
		id_curso = _id_curso;
END $$
DElimiter ;

-- asignaciones_alumnos-------------------------------------------------------------
-- asig_alumnos create

DELIMITER $$
DROP PROCEDURE IF  EXISTS sp_asignacion_alumnos_create$$
	CREATE PROCEDURE sp_asignacion_alumnos_create(IN _alumno_id VARCHAR(16),IN _cursos_id INT,IN _fecha_asignacion DATETIME)
	BEGIN
		INSERT INTO asignacion_alumnos (alumno_id,curso_id,fecha_asignacion)
		VALUES(_alumno_id,_curso_id,_fecha_asignacion);
END $$
   DElimiter ;
   DELIMITER $$
DROP PROCEDURE IF  EXISTS sp_asignaciones_alumnos_create$$
	CREATE PROCEDURE sp_asignaciones_alumnos_create(IN _alumno_id VARCHAR(16), IN _fecha_asignacion DATETIME)
	BEGIN
		INSERT INTO asignacion_alumnos (alumno_id,fecha_asignacion)
		VALUES(_alumno_id,_fecha_asignacion);
END $$
   DElimiter ;
     
  -- asignacion_alumnos Read 
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignacion_alumnos_read$$
CREATE PROCEDURE sp_asignacion_alumnos_read()
	BEGIN
		SELECT * FROM asignacion_alumnos;
END $$ 
DElimiter ;

-- asignacion_alumnos Read by id

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignacion_alumnos_id_read$$
CREATE PROCEDURE sp_asignacion_alumnos_id_read(IN _id INT)
	BEGIN
		SELECT * FROM asignacion_alumnos WHERE id = _id;
END $$	
DELIMITER ;


-- asignacion_alumnos UPDATE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignacion_alumnos_update$$

	CREATE PROCEDURE sp_asignacion_alumnos_update(IN _alumno_id VARCHAR(16), IN _curso_id INT, IN _fecha_asignacion DATETIME,
		IN _id INT)
	BEGIN
		UPDATE asignacion_alumnos SET
		alumno_id = _alumno_id,
		cursos_id = _curso_id,
		fecha_asignacion = _fecha_asignacion
		WHERE id = _id;
END $$
DElimiter ;

-- delete asig_alumnos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignacion_alumnos_delete$$

	CREATE PROCEDURE sp_asignacion_alumnos_delete(IN _id INT)
	BEGIN
		DELETE FROM asignacion_alumnos WHERE id =_id;
END $$ 
DElimiter ;
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignacion_alumnos_report$$
CREATE PROCEDURE sp_asignacion_alumnos_report()
	BEGIN
		SELECT a.id,
        a.alumno_id,
        CONCAT(
        alum.nombre1," ",alum.apellido1
        ) AS nombreYapellido,
        a.curso_id,
        a.fecha_asignacion  
        FROM 
        asignacion_alumnos As a INNER JOIN alumnos As alum
        ON 
			a.alumno_id=alum.carne;
END $$ 
DElimiter ;


DELIMITER $$
DROP PROCEDURE IF EXISTS sp_login_read $$
CREATE PROCEDURE  sp_login_read ()
begin 
SELECT * FROM usuario;
END $$
DELIMITER ; 


INSERT INTO alumnos (carne, nombre1,nombre2,nombre3,apellido1,apellido2)VALUES 
("2020323","Juan","Carlos","Gabriel","García","Dubón"),("2022421","Courtn","Kevin","Louis","Kingston","Street"),
("2018312","Ernesto","ALdair","Eduardo","Sanum","Pérez"),("2020991","Yeremi","Aldair","Jeremias","Pérez","Solorzano"),
("2019484","Jose","Alfredo","Fernando","Bonilla","Quéme"),("2018315","Juan","Carlos","Josue","Ramirez","Fuentes"),
("2017112","Luis","Edurdo","José","Restrepo","Patiño"),("2022866","Rodrigo","Jose","Carlos","Bonilla","Quiroz"),
("2021581","Carlos","Fernando","Aaron","Juarez","Alveño"),("2021331","Ronald","José","Andres","Chinchilla","Tzuruy");

INSERT INTO salones(id_salon,descripcion,capacidad_max,edificio,nivel)VALUES
("A1","Salon con cielo falso pintado",30,"EF1",1),("B1","Salon de practicas ",25,"EF1",3),
("C2","Salon de Laboratorio",40,"EF1",2),("D1","Salon amplio con 3 sillas zurdas",40,"EF2",1),
("F3","Salon pequeño ",25,"EF2",3),("G2","salon Mediano",30,"EF2",2),
("H3","Salon mediano",30,"EF3",3),("J1","Salon mediano-grande",35,"EF3",1),
("K2","Salon mediano-grande",35,"EF3",2),("L1","salon pequeño",25,"EF1",1);

INSERT INTO carreras_tecnicas (codigo_tecnico,carrera,grado,seccion,jornada) VALUES
("IN5BM","Informatica","5to",'B',"Matutina"),("IN5BV","Informatica","5to",'B',"Vespertina"),
("EL4BM","Electronica","4to",'B',"Matutina"),("EL4BV","Electronica","4to",'B',"Vespertina"),
("DI6AM","Dibujo","6to",'A',"Matutina"),("DI6AV","Dibujo","6to",'A',"Vespertina"),
("ELE5AM","Electricidad","5to",'A',"Matutina"),("ELE5AV","Electricidad","5to",'A',"Vespertina"),
("ME5BM","Mécanica","5to",'B',"Matutina"),("ME5BV","Mécanica","5to",'B',"Vespertina");


INSERT INTO instructores (nombre1,nombre2,nombre3,apellido1,apellido2,direccion,email,telefono,fecha_de_nacimiento)VALUES 
("Jose","Salomon","Jesús","Fernández","Fernándezz","zona 3","Fernandez@gmail.com","23133424","1956/03/12"),("Luis","Roberto","Aldair","Diaz","Chacón","zona 11","cdiaz@gmail.com","86422580","1993/04/23"),
("Luisa","Josefa","Fernanda","Dominguez","Dubon","zona5","Dub@gmail.com","12357949","1987/04/21"),("Stephany","Matilde","Anastacia","Flores","Enriquez","zona 15","Florez@gmail.com","12656937","1996/06/12"),
("Ana","Elisabeth","Arely","Pinto","Pérez","zona2","Perezz@gmail.com","3413168","2004/12/12"),("Genesis","Fernada","Uril","Cobra","Foquillo","zona 18","Foqui@gmail.com","18523451","1933/12/09"),
("Esmeralda","Alberta","Patricia","Campos","Gordillo","zona 4","Cxmp@gmail.com","3421218","2001/09/21"),("Juan","Carlos","Rodrigo","Diaz","Espinoza","zona 6","Espi@gmail.com","3414313","1998/03/28"),
("JOrge","Estuardo","Alberto","Dominguez","paz","zona 13","pa@gmail.com","213273912","1969/10/30"),("Bryan","José","Jesús","Ulil","Lopez","zona 10","jose@gmail.com","57344538","1987/01/29");
INSERT INTO horarios (id_horario,horario_inicio,horario_final,lunes,martes,miercoles,jueves,viernes)VALUES 
(1,'07:05:00','09:05:00',0,0,1,0,0),(2,'09:05:00','12:05:00',0,0,1,0,0),
(3,'07:05:00','11:05:00',1,0,0,0,0),(4,'11:05:00','12:05:00',1,1,0,0,0),
(5,'07:05:00','07:35:00',0,1,0,0,0),(6,'07:35:00','12:00:00',0,1,0,0,0),
(7,'07:05:00','10:05:00',0,0,0,1,0),(8,'10:05:00','12:05:00',0,0,0,1,0),
(9,'07:05:00','09:00:00',0,0,0,0,1),(10,'09:00:00','12:00:00',0,0,0,0,1);
Insert into cursos(id_curso,nombre_curso,ciclo,cupo_maximo,cupo_min,carrera_tecnica_id,horario_id,salon_id,instructor_id)VALUES
(1,"Matematica",2022,30,10,"IN5BM",1,"A1",1),(2,"Sociales",2022,30,10,"IN5BV",2,"B1",2),
(3,"Geografía",2022,30,10,"EL4BM",3,"C2",3),(4,"Estadistica",2022,30,10,"EL4BV",4,"D1",4),
(5,"Ingles",2022,30,10,"DI6AM",5,"F3",5),(6,"TIC´s",2022,30,10,"DI6AV",6,"G2",6),
(7,"Literatura",2022,30,10,"ELE5AM",7,"H3",7),(8,"Quimica",2022,30,10,"ELE5AV",8,"J1",8),
(9,"Física",2022,30,10,"ME5BM",9,"K2",9),(10,"Fílosofía",2022,30,10,"ME5BV",10,"L1",10);

INSERT into asignacion_alumnos(fecha_asignacion,alumno_id,curso_id)VALUES
('2021-11-04 07:00:00',2020323,1),('2021-11-03 07:00:00',2020991,2),
('2021-11-05 07:00:00',2022421,3),('2021-11-01 07:00:00',2018312,4),
('2021-11-08 07:00:00',2019484,5),('2021-11-21 07:00:00',2018315,6),
('2021-11-10 07:00:00',2017112,7),('2021-11-03 07:00:00',2022866,8),
('2021-11-21 07:00:00',2021581,9),('2021-11-09 07:00:00',2021331,10);

INSERT INTO rol (id,descripcion)VALUES 
(1,"Administrador"), (2,"Estandar");
INSERT INTO usuario(usuario,pass,nombre,rol_id ) VALUES 
("root","admin","Carlos Díaz",1),("kinal","12345","Luis Pérez",2);

call sp_asignacion_alumnos_report();
CALL sp_login_read();