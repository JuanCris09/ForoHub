CREATE TABLE topicos (
    id INT auto_increment,
    titulo VARCHAR(100) NOT NULL,
    mensaje varchar(100) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    status int NOT NULL,
    autor varchar(100) NOT NULL,
    curso VARCHAR(100) NOT NULL,

    primary key(id)
);
