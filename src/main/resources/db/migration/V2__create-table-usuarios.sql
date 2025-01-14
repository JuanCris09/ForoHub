CREATE TABLE Usuario (
    id INT auto_increment,
    nombre VARCHAR(255) NOT NULL,
    correoElectronico VARCHAR(255) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    perfiles VARCHAR(255) NOT NULL,

    primary key(id)
);
