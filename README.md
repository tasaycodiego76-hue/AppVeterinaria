# 🐾 App Veterinaria

Aplicación Android para gestionar mascotas con operaciones CRUD completas.

## ✨ Funcionalidades

* Registrar nuevas mascotas
* Buscar mascotas por ID
* Actualizar información
* Eliminar registros
* Listado de mascotas
* Validaciones de formularios

## 📋 Requisitos

* Android Studio 4.0+
* Java JDK 8+
* API REST en puerto 3000

## 🚀 Instalación y Ejecución

1. **Clonar proyecto:**
```bash
git clone https://github.com/tasaycodiego76-hue/AppVeterinaria.git

Abrir en Android Studio:

File → Open → Seleccionar carpeta del proyecto
Esperar que se sincronicen las dependencias


Configurar API:

Editar archivos registros.java y Buscar.java
Cambiar la IP por la de tu servidor:



private final String URL = "http://192.168.18.186:3000/mascotas/";

Conectar dispositivo Android o iniciar emulador
Ejecutar aplicación:

Clic en botón Run 
La app se instala automáticamente



🔧 Uso
Registrar: Menú → Registrar Mascota → Llenar formulario
Gestionar: Menú → Buscar Mascota → Ingresar ID → Actualizar/Eliminar
🌐 API Endpoints

POST /mascotas - Crear
GET /mascotas/:id - Buscar
PUT /mascotas/:id - Actualizar
DELETE /mascotas/:id - Eliminar

📂 Archivos principales

MainActivity.java - Menú principal
registros.java - Registro de mascotas
Buscar.java - Búsqueda y gestión

🌿 Ramas del proyecto

main - Proyecto original básico
RamaAppVeterinaria - Versión completa con funcionalidades CRUD

Crear rama para desarrollo:
```
# Ver rama actual
```
git branch
```
# Crear nueva rama
```
git checkout -b RamaAppVeterinaria
```
# Subir rama a GitHub
```
git push -u origin RamaAppVeterinaria
```
# Actualizar cambios después
```
git add .
git commit -m "Funcionalidades CRUD AppVeterinaria completada"
git push origin RamaAppVeterinaria
