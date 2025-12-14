# CRUD de Videojuegos - Proyecto Android

## Descripción del Proyecto

Esta aplicación es un proyecto de estudio desarrollado en Kotlin para Android. El objetivo es implementar un sistema CRUD (Crear, Leer, Actualizar y Borrar) completo para gestionar una lista de videojuegos. La aplicación permite visualizar una lista de juegos, añadir nuevos elementos, editar los existentes y eliminarlos de la lista.

## Características Principales

*   **Visualización de datos**: Muestra una lista de videojuegos en un `RecyclerView`, cargando las imágenes desde una URL.
*   **Creación (Create)**: Un botón flotante permite abrir una nueva pantalla para añadir un nuevo juego a la lista.
*   **Lectura (Read)**: La pantalla principal lee y muestra la lista completa de juegos.
*   **Actualización (Update)**: Cada elemento de la lista tiene un botón para abrir una pantalla de edición y guardar los cambios.
*   **Borrado (Delete)**: Cada elemento tiene un botón para eliminarlo directamente de la lista.

## Tecnologías y Librerías Utilizadas

*   **Lenguaje**: Kotlin
*   **Arquitectura**: Aplicación de varias actividades.
*   **Vistas y UI**:
    *   `RecyclerView`: Para mostrar listas de datos de forma eficiente.
    *   `ViewBinding`: Para acceder a las vistas de forma segura, reemplazando `findViewById`.
    *   `Material Components`: Para los componentes de la interfaz, como `CardView` y `FloatingActionButton`.
*   **Imágenes**:
    *   `Glide`: Para cargar y cachear imágenes desde URLs de forma asíncrona.

## Estructura del Proyecto y Métodos Clave

A continuación se describen los componentes más importantes del proyecto.

### `MainActivity.kt`

Es la actividad principal y actúa como el "cerebro" de la aplicación. Se encarga de mostrar la lista y coordinar las acciones de añadir, editar y borrar.

#### Métodos a Destacar: `registerForActivityResult`

'''kotlin
// Launcher para EDITAR un juego (lógica nueva y segura)
private val editarJuegoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    if (result.resultCode == Activity.RESULT_OK) {
        // ... Lógica para recibir el juego editado ...
        if (juegoEditado != null) {
            // Buscamos la posición correcta usando el ID del juego
            val index = juegos.listajuegos.indexOfFirst { it.id == juegoEditado.id }
            if (index != -1) {
                // Actualizamos el elemento en la posición correcta y segura
                juegos.listajuegos[index] = juegoEditado
                adapter.notifyItemChanged(index)
            }
        }
    }
}
'''

Este bloque de código es la forma moderna en Android de lanzar una actividad y esperar un resultado. Reemplaza al antiguo método `onActivityResult`. En este caso, cuando `EditarJuego` termina, `editarJuegoLauncher` recibe el `juegoEditado`. En lugar de confiar en una posición (que puede ser incorrecta), **busca el índice del juego usando su ID único**, lo que hace que el proceso de actualización sea robusto y seguro.

### `aptadorjuego.kt`

Es el adaptador que conecta la lista de datos (`listajuegos`) con el `RecyclerView`. Su única responsabilidad es mostrar los datos y notificar cuando el usuario interactúa con los botones.

#### Patrón de Diseño: `OnJuegoClickListener`

'''kotlin
// 1. El adaptador recibe un "listener" (oyente) en su constructor.
class aptadorjuego(
    private val listajuegos: MutableList<Juego>,
    private val listener: OnJuegoClickListener
) : RecyclerView.Adapter<...>() {

    // 2. Se define una interfaz con las acciones que el usuario puede realizar.
    interface OnJuegoClickListener {
        fun onEditClick(juego: Juego)
        fun onDeleteClick(position: Int)
    }

    // 3. En onBindViewHolder, los botones no ejecutan la lógica, solo notifican al listener.
    override fun onBindViewHolder(...) {
        // ...
        holder.btnEdit.setOnClickListener {
            listener.onEditClick(juego) // Notifica que se pulsó "editar"
        }
    }
}
'''

Este es un patrón de diseño muy importante. El adaptador no sabe *qué hacer* cuando se pulsa un botón; simplemente **avisa a quien le interese (en este caso, a `MainActivity`)**. Esto desacopla la lógica de la presentación: el adaptador solo muestra datos y `MainActivity` decide si borrar un elemento, abrir otra pantalla, etc.

### `daoclass/Juego.kt`

Es la clase de datos (`data class`) que representa el modelo de un videojuego.

#### Anotación `@Parcelize`

La anotación `@Parcelize` automatiza el proceso de "empaquetar" un objeto `Juego` para que pueda ser enviado de una actividad a otra a través de un `Intent`. Sin esto, tendríamos que escribir manualmente mucho código para guardar y recuperar cada uno de los campos (título, precio, etc.).

### `recursos/juegos.kt`

Este objeto actúa como una fuente de datos de prueba. En una aplicación real, estos datos vendrían de una base de datos local (como Room) o de una API remota.

## Cómo Ejecutar el Proyecto

1.  Clona este repositorio en tu máquina local.
2.  Abre el proyecto con Android Studio.
3.  Espera a que Gradle sincronice todas las dependencias.
4.  Ejecuta la aplicación en un emulador o en un dispositivo físico.

[video de la ejecucio](https://youtu.be/d4qfxFLxwew)

