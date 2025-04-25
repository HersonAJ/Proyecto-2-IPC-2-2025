-- Crear la base de datos (si no existe)
CREATE DATABASE IF NOT EXISTS Salon_De_Belleza;
USE Salon_De_Belleza;

-- Tabla Usuarios 
CREATE TABLE IF NOT EXISTS Usuarios (
    ID_Usuario INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(255) NOT NULL,
    DPI VARCHAR(20) NOT NULL,
    Telefono VARCHAR(15) NOT NULL,
    Direccion VARCHAR(255),
    Correo VARCHAR(255) UNIQUE NOT NULL, -- UNIQUE para que un correo solo tenga una cuenta
    Contraseña VARCHAR(255) NOT NULL,
    Foto_Perfil MEDIUMBLOB,
    Descripción TEXT,  
    Rol ENUM('Cliente', 'Empleado', 'Administrador', 'Marketing', 'Servicios') NOT NULL,
    Estado ENUM('Activo', 'Inactivo') NOT NULL
);

-- Tabla Servicios 
CREATE TABLE IF NOT EXISTS Servicios (
    ID_Servicio INT AUTO_INCREMENT PRIMARY KEY,
    Nombre_Servicio VARCHAR(255) NOT NULL,
    Descripción TEXT,
    Duración INT NOT NULL,
    Precio DECIMAL(10,2) NOT NULL,
    Estado ENUM('Visible', 'Oculto') NOT NULL,
    Imagen LONGBLOB, 
    ID_Encargado INT,
    Catalogo_PDF LONGBLOB,
    FOREIGN KEY (ID_Encargado) REFERENCES Usuarios(ID_Usuario)
);

-- Tabla para relacionar trabajadores (Empleados) con los servicios que pueden realizar
CREATE TABLE IF NOT EXISTS Trabajadores_Servicios (
    ID_Empleado INT NOT NULL,
    ID_Servicio INT NOT NULL,
    PRIMARY KEY (ID_Empleado, ID_Servicio),
    FOREIGN KEY (ID_Empleado) REFERENCES Usuarios(ID_Usuario) ON DELETE CASCADE,
    FOREIGN KEY (ID_Servicio) REFERENCES Servicios(ID_Servicio) ON DELETE CASCADE
);


-- Tabla Citas
CREATE TABLE IF NOT EXISTS Citas (
    ID_Cita INT AUTO_INCREMENT PRIMARY KEY,
    ID_Servicio INT,
    ID_Cliente INT,
    ID_Empleado INT,
    Fecha_Cita DATE NOT NULL,
    Hora_Cita TIME NOT NULL,
    Estado ENUM('Pendiente', 'Atendida', 'Cancelada', 'No Presentado') NOT NULL,
    Factura_Generada BOOLEAN NOT NULL DEFAULT 0,
    FOREIGN KEY (ID_Servicio) REFERENCES Servicios(ID_Servicio),
    FOREIGN KEY (ID_Cliente) REFERENCES Usuarios(ID_Usuario),
    FOREIGN KEY (ID_Empleado) REFERENCES Usuarios(ID_Usuario)
);

-- Tabla Facturas
CREATE TABLE IF NOT EXISTS Facturas (
    ID_Factura INT AUTO_INCREMENT PRIMARY KEY,
    ID_Cita INT NOT NULL, 
    ID_Cliente INT NOT NULL, 
    ID_Empleado INT NOT NULL, 
    ID_Servicio INT NOT NULL, 
    Total DECIMAL(10,2) NOT NULL, 
    Fecha_Factura DATE NOT NULL, 
    Detalles TEXT, 
    FOREIGN KEY (ID_Cita) REFERENCES Citas(ID_Cita),
    FOREIGN KEY (ID_Cliente) REFERENCES Usuarios(ID_Usuario),
    FOREIGN KEY (ID_Empleado) REFERENCES Usuarios(ID_Usuario),
    FOREIGN KEY (ID_Servicio) REFERENCES Servicios(ID_Servicio)
);

-- Tabla Anuncios
CREATE TABLE IF NOT EXISTS Anuncios (
    ID_Anuncio INT AUTO_INCREMENT PRIMARY KEY, 
    Nombre_Anunciante VARCHAR(255) NOT NULL,  
    Contacto_Anunciante VARCHAR(255),         
    Tipo_Anuncio ENUM('Texto', 'Imagen', 'Video') NOT NULL, 
    Contenido_Texto TEXT,                     
    URL_Imagen VARCHAR(255),                  
    URL_Video VARCHAR(255),                   
    Precio_Diario DECIMAL(10, 2) NOT NULL,    
    Tiempo_Duración INT NOT NULL,             
    Fecha_Creacion DATE NOT NULL,             
    Fecha_Fin DATE GENERATED ALWAYS AS (DATE_ADD(Fecha_Creacion, INTERVAL Tiempo_Duración DAY)), -- Calculada dinámicamente
    Estado ENUM('Activo', 'Inactivo', 'Caducado') NOT NULL, 
    ID_Marketing INT,                         
    FOREIGN KEY (ID_Marketing) REFERENCES Usuarios(ID_Usuario)
);


CREATE TABLE IF NOT EXISTS Historial_Anuncios (
    ID_Visualizacion INT AUTO_INCREMENT PRIMARY KEY, 
    ID_Anuncio INT NOT NULL,                         
    Fecha_Visualizacion DATETIME NOT NULL,           
    URL_Mostrada VARCHAR(255),                       
    FOREIGN KEY (ID_Anuncio) REFERENCES Anuncios(ID_Anuncio) ON DELETE CASCADE
);



-- Tabla Lista_Negra
CREATE TABLE IF NOT EXISTS Lista_Negra (
    ID_Lista INT AUTO_INCREMENT PRIMARY KEY,
    ID_Cliente INT,
    ID_Cita INT, 
    Motivo TEXT,
    Estado ENUM('En Lista', 'Permitido') NOT NULL,
    FOREIGN KEY (ID_Cliente) REFERENCES Usuarios(ID_Usuario),
    FOREIGN KEY (ID_Cita) REFERENCES Citas(ID_Cita)
);

-- Tabla Horarios
CREATE TABLE IF NOT EXISTS Horarios (
    ID_Horario INT AUTO_INCREMENT PRIMARY KEY,
    Hora_Apertura TIME NOT NULL,
    Hora_Cierre TIME NOT NULL,
    Día_Semana TINYINT NOT NULL, 
    ID_Empleado INT NULL, 
    FOREIGN KEY (ID_Empleado) REFERENCES Usuarios(ID_Usuario)
);

-- Tabla Pagos_Anuncios
CREATE TABLE IF NOT EXISTS Pagos_Anuncios (
    ID_Pago INT AUTO_INCREMENT PRIMARY KEY,      
    ID_Anuncio INT NOT NULL,                     
    Monto DECIMAL(10, 2) NOT NULL,               
    Precio_Por_Dia_Aplicado DECIMAL(10, 2) NOT NULL, 
    Fecha_Pago DATE NOT NULL,                    
    Comprador VARCHAR(255) NOT NULL,             
    FOREIGN KEY (ID_Anuncio) REFERENCES Anuncios(ID_Anuncio) ON DELETE CASCADE
);


-- Tabla Hobbies: almacena cada hobbie
CREATE TABLE IF NOT EXISTS Hobbies (
    ID_Hobbie INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(255) NOT NULL UNIQUE
);

-- Tabla Usuario_Hobbies: relacional (muchos a muchos) entre Usuarios y Hobbies
CREATE TABLE IF NOT EXISTS Usuario_Hobbies (
    ID_Usuario INT NOT NULL,
    ID_Hobbie INT NOT NULL,
    PRIMARY KEY (ID_Usuario, ID_Hobbie),
    FOREIGN KEY (ID_Usuario) REFERENCES Usuarios(ID_Usuario) ON DELETE CASCADE,
    FOREIGN KEY (ID_Hobbie) REFERENCES Hobbies(ID_Hobbie) ON DELETE CASCADE
);

-- Tabla Anuncio_Hobbies: relacional (muchos a muchos) entre Anuncios y Hobbies
CREATE TABLE IF NOT EXISTS Anuncio_Hobbies (
    ID_Anuncio INT NOT NULL,
    ID_Hobbie INT NOT NULL,
    PRIMARY KEY (ID_Anuncio, ID_Hobbie),
    FOREIGN KEY (ID_Anuncio) REFERENCES Anuncios(ID_Anuncio) ON DELETE CASCADE,
    FOREIGN KEY (ID_Hobbie) REFERENCES Hobbies(ID_Hobbie) ON DELETE CASCADE
);

--Tabla Precio_Diario_Anuncios para configurar el precio diario segun el tipo de anuncios
CREATE TABLE IF NOT EXISTS Precio_Diario_Anuncios (
    Tipo_Anuncio ENUM('Texto', 'Imagen', 'Video') NOT NULL PRIMARY KEY, 
    Precio_Diario DECIMAL(10, 2) NOT NULL                              
);

-- Insertar un usuario administrador inicial
INSERT INTO Usuarios (Nombre, DPI, Telefono, Direccion, Correo, Contraseña, Foto_Perfil, Descripción, Rol, Estado)
VALUES (
    'Administrador', 
    '0000000000000', 
    '0000000000', 
    'Dirección Admin', 
    'admin@salon.com', 
    'MTIz', -- '123' codificado en Base64
    NULL,
    'Administrador del sistema',
    'Administrador',
    'Activo'
);
