# EleccionesEscalera

Proyecto de ejemplo Android + Java que ejemplifica el uso de un Fragment a modo de componente y un pequeño login. 

Este proyecto se basa en la idea de votar al presidente de la comunidad de vecinos de los archiconocidos candidatos de la serie *Aquí no hay quien viva*. Los datos de los candidatos, sus partidos y demás se precargan en una pequeña base de datos en la que también se precargan los datos de los usuarios que pueden acceder para votar.

El proyecto tiene la peculiaridad de implementar un botón que limita la votación a un máximo de 3 votos, que deben ser a candidatos distintos. Este botón está desarrollado como un componente en un fragmento separado de la activity que se incluye en la misma para poder ser reutilizado.

Temas tratados en el ejemplo:

- Listeners
- Lambdas
- SQLite
- SQLiteOpenHelper
- Cursores
- Fragments como Componentes
- Login sencillo