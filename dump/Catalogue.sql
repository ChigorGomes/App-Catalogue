--
-- File generated with SQLiteStudio v3.2.1 on ter nov 20 10:34:46 2018
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: catalogado
DROP TABLE IF EXISTS catalogado;

CREATE TABLE catalogado (
    idCatalogado INTEGER NOT NULL
                         PRIMARY KEY AUTOINCREMENT,
    status       INTEGER NOT NULL
);


-- Table: catalogo
DROP TABLE IF EXISTS catalogo;

CREATE TABLE catalogo (
    idCatalogo    INTEGER NOT NULL
                          PRIMARY KEY AUTOINCREMENT,
    nomeEspecie   TEXT    NOT NULL,
    observacoes   TEXT    NOT NULL,
    localizacao   TEXT    NOT NULL,
    fotoEspecie   BLOB    NOT NULL,
    idTipoEspecie INTEGER NOT NULL,
    idUsuario     INTEGER NOT NULL,
    idCatalogado  INTEGER NOT NULL,
    FOREIGN KEY (
        idUsuario
    )
    REFERENCES tipoEspecie (idEspecie),
    FOREIGN KEY (
        idTipoEspecie
    )
    REFERENCES usuario (idUsuario) 
);


-- Table: tipoEspecie
DROP TABLE IF EXISTS tipoEspecie;

CREATE TABLE tipoEspecie (
    idEspecie   INTEGER NOT NULL
                        PRIMARY KEY AUTOINCREMENT,
    tipoEspecie TEXT    NOT NULL
);


-- Table: tipoUsuario
DROP TABLE IF EXISTS tipoUsuario;

CREATE TABLE tipoUsuario (
    idTipoUsuario INTEGER NOT NULL
                          PRIMARY KEY AUTOINCREMENT,
    tipo          TEXT    NOT NULL
);


-- Table: usuario
DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
    idUsuario      INTEGER NOT NULL
                           PRIMARY KEY AUTOINCREMENT,
    nome           TEXT    NOT NULL,
    email          TEXT    NOT NULL,
    sexo           TEXT    NOT NULL,
    dataNascimento TEXT    NOT NULL,
    senha          TEXT    NOT NULL,
    idTipoUsuario  INTEGER NOT NULL,
    FOREIGN KEY (
        idTipoUsuario
    )
    REFERENCES tipoUsuario (idTipoUsuario) 
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
