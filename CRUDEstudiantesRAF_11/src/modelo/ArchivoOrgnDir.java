/*Organización directa: permite acceder directamente a una posición del archivo sin tener que leerlo completo. 
Esto se logra calculando la posición en bytes donde debe escribirse o leerse un registro, basado en una clave 
única (como el número de control).
trim: método de la clase String que elimina los espacios en blanco al inicio y al final de una cadena de texto.*/
package modelo;//La clase pertenece al paquete modelo, que normalmente agrupa la lógica de datos o negocio.
import java.io.*;// Se importa todo lo necesario para manejar archivos (como File, RandomAccessFile, IOException)
import java.text.DecimalFormat;//Se usa para formatear números, en este caso el semestre (como "01", "02"...).
import java.util.logging.Level;//Sirve para definir la severidad de los mensajes que se van a registrar en el log del sistema (como errores, advertencias, información, etc.).
import java.util.logging.Logger;//Para registrar errores cuando algo falla con el archivo.
public class ArchivoOrgnDir { //Se define la clase que manejará archivos binarios con organización directa.
    private File fichero;//Representa el archivo físico.
    private RandomAccessFile raf;/*Permite leer y escribir datos en cualquier posición del archivo
                                 (esto es lo que permite la "organización directa").*/
    //Tamaños fijos para cada campo de un registro
    private final int NUMCONTROL_LEN = 8;//
    private final int NOMBRE_LEN     = 20;//
    private final int APELLIDOS_LEN  = 20;//
    private final int SEMESTRE_LEN   = 2;//
    private final int GRUPO_LEN      = 1;
    private final int CARRERA_LEN    = 30;
//TAMREG: tamaño total en bytes de cada registro. Esto es clave para calcular en qué posición se debe leer o escribir un registro.
    private final int TAMREG         = NUMCONTROL_LEN + NOMBRE_LEN + APELLIDOS_LEN + SEMESTRE_LEN + GRUPO_LEN + CARRERA_LEN;
//Se usa para saber cuántos registros hay en total en el archivo.    
    private int totReg;
    
/* Constructor
Funcion:Inicializa las variables. El archivo aún no está abierto ni creado.*/
    public ArchivoOrgnDir() {//Este constructor inicializa los atributos de la clase cuando se crea un objeto de tipo ArchivoOrgnDir.
        this.fichero = null;//fichero es una variable de tipo File, que representa un archivo físico en el disco.
/*Inicializa el atributo fichero como null, lo que significa que aún no se ha asociado con ningún archivo real.
Es una buena práctica para indicar que no se está trabajando con ningún archivo todavía. */     
        this.raf = null;//Es una variable de tipo RandomAccessFile, una clase que permite leer y escribir en cualquier parte de un archivo directamente (acceso aleatorio).
/*Inicializa la variable raf como null porque todavía no se ha abierto ningún archivo con acceso aleatorio.*/        
        this.totReg = 0;//Es un contador entero que guarda el total de registros en el archivo.
//Lo inicia en 0 porque aún no se han contado ni leído registros del archivo.        
    }
    
//Este método prepara el archivo con el que se quiere trabajar, y verifica si ya existe en el sistema de archivos. 
    private boolean establecerFlujo(String nombreArchivo) {
//Crea un objeto File que representa un archivo con el nombre o ruta que se pasa como parámetro (nombreArchivo).
        fichero = new File(nombreArchivo);
/*Asigna a fichero una referencia a ese archivo. No lo abre ni lo crea todavía,
solo establece un objeto File que apunta a ese archivo.
Para que las operaciones posteriores (abrir, leer, escribir, borrar) sepan a qué archivo se refieren.*/                  
        return fichero.exists();//Verifica si el archivo ya existe en el disco.
/*Devuelve true si el archivo existe, o false si no. Es útil para verificar antes de 
intentar abrir o escribir en el archivo.    */
    }
    
/* abrirArchivoRAF método intenta abrir el archivo representado por fichero usando la clase RandomAccessFile, 
que permite leer y escribir en cualquier parte del archivo.    */
    public boolean abrirArchivoRAF(String nombreArchivo) {
        try {//Todas las operaciones de (entrada/salida) en Java pueden lanzar excepciones, como cuando el disco está lleno o el archivo está cerrado.
//Calcula en qué parte del archivo se debe escribir el registro. 
            establecerFlujo(nombreArchivo);
            //Esta línea abre el archivo con el modo indicado, y se lo asigna a raf.
            this.raf = new RandomAccessFile(nombreArchivo, "rw");
/*fichero ya debe estar apuntando a un archivo válido.
modo puede ser:
"r" solo lectura
"rw"lectura y escritura*/
            return true;// Si se pudo abrir el archivo correctamente, se retorna true.
        } catch (FileNotFoundException ex) {// Si el archivo no se puede abrir, esta parte atrapa el error.
            Logger.getLogger(ArchivoOrgnDir.class.getName()).log(Level.SEVERE, null, ex);
            return false;//Devuelve false si ocurrió un error (por ejemplo, si no existe el archivo o no tienes permisos).
        }
    }
    
    public void cerrarArchivo() {//Este método cierra el archivo que se estaba usando, solo si ya estaba abierto.
/*raf es un objeto de tipo RandomAccessFile que sirve para leer y escribir archivos.
raf != null verifica si el archivo está abierto. Si está cerrado, sería null, y no se hace nada.
raf.close(); cierra correctamente el archivo para liberar la memoria y evitar errores o archivos dañados.
Si ocurre un error al cerrar, lo muestra con el Logger (no detiene el programa, pero registra el error).*/  
        try {//Todas las operaciones de E/S (entrada/salida) en Java pueden lanzar excepciones, como cuando el disco está lleno o el archivo está cerrado.
//Calcula en qué parte del archivo se debe escribir el registro. 
            if (raf != null) raf.close();
        } catch (IOException ex) {
            Logger.getLogger(ArchivoOrgnDir.class.getName()).log(Level.SEVERE, null, ex);
        }//Devuelve false si ocurrió un error (por ejemplo, si no existe el archivo o no tienes permisos).
    }
    
    private static String ajustar(String s, int longitud) {
    //Este método ajusta un texto (String) a una longitud fija, recortando o rellenando con espacios.
        return s.length() > longitud ? s.substring(0, longitud) : String.format("%-" + longitud + "s", s);
/*Este método ajusta un texto (String) a una longitud fija, recortando o rellenando con espacios.
Explicación:
Si el texto (s) es más largo que longitud, lo recorta con substring(0, longitud).
Si el texto es más corto, lo rellena con espacios en blanco hasta que tenga justo la longitud especificada.
Si guardo "Jose" en un campo de 10 caracteres, esto te devuelve: "Jose "
Si quieres guardar "GuillermoPérez" en un campo de 10 caracteres, esto te devuelve: "Guillermo"
Esto es importante porque en archivos de longitud fija, cada campo debe ocupar el mismo espacio siempre.*/
    }
    
    private long establecerPosicionByte(String numeroControl) {
//Calcula en qué posición del archivo (en bytes) debe escribirse o leerse un registro específico, usando el número de control como clave.
        return ((Long.parseLong(numeroControl.substring(5))) - 1) * TAMREG;
/*numeroControl.substring(5)  Extrae los últimos dígitos del número de control.
Ejemplo: "23920248" se queda con "248" (desde el carácter 5 en adelante). 
Long.parseLong(...) Convierte "248" a un número: 248. (2 - 1) * 81 
-1  Se le resta 1 para que empiece desde posición 0.
* TAMREG Se multiplica por el tamaño fijo de un registro, para saber en qué byte exacto del archivo está ese registro.*/
    }
    
    //cualquier otra clase (dentro o fuera del paquete modelo) puede llamar a este método. y void que no retorna ningun valor
    public void escribirRegistro(String numControl, String nombre, String apellidos, int semestre, char grupo, String carrera) {
//Este método escribe un registro completo en el archivo, en una posición específica según el número de control del estudiante.        
        try {//Todas las operaciones de (entrada/salida) en Java pueden lanzar excepciones, como cuando el disco está lleno o el archivo está cerrado.
//Calcula en qué parte del archivo se debe escribir el registro.            
            long pos = establecerPosicionByte(numControl);//devuelve un índice en bytes según la fórmula de organización directa.
            raf.seek(pos);//Mueve el puntero del archivo a esa posición.
/*Luego escribe cada campo ajustado a una longitud fija (rellenado o recortado), usando codificación "ISO-8859-1" 
para que los caracteres ocupen solo 1 byte (ASCII extendido). 
            Los campos que se escriben:
            numControl: con longitud NUMCONTROL_LEN=8
raf.write(ajustar(...).getBytes("ISO-8859-1")):Recorta o rellena el String para que siempre ocupe exactamente longitud
caracteres.
Necesario para que los registros sean de tamaño fijo.
.getBytes("ISO-8859-1"):Convierte el String resultante en un array de bytes usando la codificación ISO-8859-1.
Cada carácter ocupa exactamente 1 byte, lo cual simplifica el cálculo de posiciones en el archivo.
raf.write(byte[]):Escribe esos bytes en el archivo a partir de la posición actual del cursor.
Repite esto para cada campo en orden: número de control, nombre, apellidos, semestre, grupo y carrera.*/           
            raf.write(ajustar(numControl, NUMCONTROL_LEN).getBytes("ISO-8859-1"));
            raf.write(ajustar(nombre, NOMBRE_LEN).getBytes("ISO-8859-1"));
            raf.write(ajustar(apellidos, APELLIDOS_LEN).getBytes("ISO-8859-1"));
            raf.write(new DecimalFormat("00").format(semestre).getBytes("ISO-8859-1"));
            raf.write(String.valueOf(grupo).getBytes("ISO-8859-1"));
            raf.write(ajustar(carrera, CARRERA_LEN).getBytes("ISO-8859-1"));
        } catch (IOException ex) {
            Logger.getLogger(ArchivoOrgnDir.class.getName()).log(Level.SEVERE, null, ex);
        }//Sin el bloque try/catch, un IOException haría que el método propagara la excepción y posiblemente cerrara la aplicación.
    }
    
/*Metodo obtenerMatrizregistro:lee todos los registros válidos guardados en un archivo de acceso aleatorio (RandomAccessFile) y los devuelve como 
una matriz de cadenas (String[][]). 
Cada fila representa un registro, y cada columna un dato: número de control, nombre, apellidos, semestre, grupo y carrera.
*/
public String[][] obtenerMatrizRegistros(int numCol) {
/*public: Puede ser llamado desde otras clases.
String[][]: Devuelve una matriz de cadenas, es decir, una estructura de varias filas y columnas con texto.
int numCol: Indica cuántas columnas debe tener cada fila. (Se espera que sea 6 en este caso, uno por cada campo).*/    
    try {//Se usa para manejar errores de lectura de archivo.
        long longitudArchivo = raf.length();//btiene el tamaño total del archivo en bytes.
//Esto es necesario para saber cuántos registros completos hay en el archivo.
        this.totReg = (int) (longitudArchivo / TAMREG);
/*TAMREG es el tamaño de un registro en bytes (debe ser definido en tu clase).
Divide el tamaño total del archivo entre el tamaño de un registro, lo que da como resultado el número total de registros (totReg).
Se hace un cast a int porque length() devuelve un long.*/
        java.util.ArrayList<String[]> listaRegistros = new java.util.ArrayList<>();
/*Se crea una lista dinámica para almacenar cada registro leído como un arreglo de strings (String[]).
Luego, al final, se convierte en una matriz (String[][])*/
        for (int i = 0; i < totReg; i++) {
/*int i = 0	Se declara la variable i e inicia en 0. Representa el índice del registro actual.
i < totReg	Mientras i sea menor que totReg, el ciclo continúa. Es decir, se repite hasta que se hayan procesado todos los registros.
i++	Incrementa i en 1 al final de cada vuelta. Significa: "pasa al siguiente registro".*/
            raf.seek(i * (long) TAMREG);
/* Mueve el cursor al inicio del i-ésimo registro en el archivo.
Multiplica el índice por el tamaño del registro para encontrar la posición correcta en bytes.*/
            byte[] buf = new byte[TAMREG];
//Se lee un registro completo del archivo (que está en formato binario o bytes) y lo guardas en buf.
/*Se crea un buffer de bytes del tamaño de un registro.
readFully(buf) lee los bytes del archivo en esa posición y los guarda en el buffer.*/
            raf.readFully(buf);

        String numControl = new String(buf, 0, NUMCONTROL_LEN, "ISO-8859-1").trim();
// Entonces, esta línea reconstruye el campo número de control desde los bytes leídos en buf.
            /*new String Convierte una parte del arreglo de bytes buf en un String de texto legible.
buf	Es el arreglo de bytes que contiene todo el registro leído.
0	Es la posición inicial dentro de buf desde donde quieres empezar a leer. En este caso, desde el inicio.
NUMCONTROL_LEN	Es la cantidad de bytes que vas a leer desde buf para formar el campo numControl.
"ISO-8859-1"	Es el formato de codificación de caracteres. Sirve para interpretar los bytes como texto. Es común en archivos binarios antiguos.
.trim()	Elimina espacios en blanco al principio y al final del texto (porque los campos están alineados con espacios)..*/
            if (numControl.equals("00000000") || numControl.isEmpty()) continue;
/*Esta línea salta registros inválidos o vacíos.
Si el número de control está vacío o es "00000000", simplemente no lo toma en cuenta (pasa al siguiente con continue).*/
            String[] fila = new String[numCol];
/*Crea un arreglo de cadenas (fila) que representará un registro completo en forma de texto, con columnas como 
si fuera una tabla.*/
            fila[0] = numControl;
            fila[1] = new String(buf, NUMCONTROL_LEN, NOMBRE_LEN, "ISO-8859-1").trim();
/*buf	Arreglo de bytes que tiene todo el registro.
NUMCONTROL_LEN	Posición inicial donde empieza el nombre.
NOMBRE_LEN	Cuántos bytes se usan para el nombre.
new String(..., ..., ..., "ISO-8859-1")	Convierte esa parte en texto.
.trim()	Elimina espacios de sobra.*/
            fila[2] = new String(buf, NUMCONTROL_LEN + NOMBRE_LEN, APELLIDOS_LEN, "ISO-8859-1").trim();
/* Empieza después del nombre, y lee lo que corresponde a los apellidos.
(la posición inicial es: lo que ocupan el número de control y el nombre).*/
            fila[3] = new String(buf, NUMCONTROL_LEN + NOMBRE_LEN + APELLIDOS_LEN, SEMESTRE_LEN, "ISO-8859-1").trim();
            /*Lee el campo del semestre, que viene justo después de apellidos.*/
            fila[4] = new String(buf, NUMCONTROL_LEN + NOMBRE_LEN + APELLIDOS_LEN + SEMESTRE_LEN, GRUPO_LEN, "ISO-8859-1").trim();
            /*Lee el campo del grupo, que está después del semestre*/
            fila[5] = new String(buf, NUMCONTROL_LEN + NOMBRE_LEN + APELLIDOS_LEN + SEMESTRE_LEN + GRUPO_LEN, CARRERA_LEN, "ISO-8859-1").trim();
            /*Lee el campo de la carrera, que viene al final.*/
            listaRegistros.add(fila);//Agrega la fila (registro ya convertido a texto y separado por campos) a la lista de registros leídos.
        }

        String[][] registros = new String[listaRegistros.size()][numCol];
        /*Se convierte la ArrayList en una matriz (String[][]), porque eso es más fácil de usar en interfaces gráficas o tablas.
Devuelve la matriz completa con todos los registros válidos.*/
        return listaRegistros.toArray(registros);
    } catch (IOException ex) {
        Logger.getLogger(ArchivoOrgnDir.class.getName()).log(Level.SEVERE, null, ex);
        return null;//esto es por si ocurre un error lo atrapa y hace que no se cierre abruptamente
    }
}
    public String[] buscarDato(String nc) {
        try {
            long pos = establecerPosicionByte(nc);
// calcula la posición en el archivo donde se encuentra el registro con número de control nc.
            raf.seek(pos);//Mueve el "cursor" del archivo (RandomAccessFile) a esa posición para poder leer el registro.
            byte[] buf = new byte[TAMREG];
/*Se crea un arreglo de bytes del tamaño del registro (TAMREG).
Se leen todos los bytes del registro completo y se almacenan en buf.*/
            raf.readFully(buf);
            String nControl = new String(buf, 0, NUMCONTROL_LEN, "ISO-8859-1").trim();
            /*Se convierte la primera parte de buf (desde el byte 0 hasta NUMCONTROL_LEN) en texto.
.trim() elimina espacios en blanco sobrantes.*/
            if (!nControl.equals(nc) || nControl.equals("00000000")) return null;
/*Si el número de control no es igual al que buscabas (nc), o si está vacío ("00000000"),
se descarta el registro y retorna null.*/
            return new String[]{
                nControl,
                new String(buf, NUMCONTROL_LEN, NOMBRE_LEN, "ISO-8859-1").trim(),
/*Fila	Campo extraído	Desde qué posición en buf	Cuántos bytes	Qué representa
[0]	nControl	0	NUMCONTROL_LEN	Número de control
[1]	nombre	NUMCONTROL_LEN	NOMBRE_LEN	Nombre del alumno
[2]	apellidos	NUMCONTROL_LEN + NOMBRE_LEN	APELLIDOS_LEN	Apellidos
[3]	semestre	Suma anterior + APELLIDOS_LEN	SEMESTRE_LEN	Semestre
[4]	grupo	Suma anterior + SEMESTRE_LEN	GRUPO_LEN	Grupo
[5]	carrera	Suma anterior + GRUPO_LEN	CARRERA_LEN	Carrera o programa*/
                new String(buf, NUMCONTROL_LEN + NOMBRE_LEN, APELLIDOS_LEN, "ISO-8859-1").trim(),
                new String(buf, NUMCONTROL_LEN + NOMBRE_LEN + APELLIDOS_LEN, SEMESTRE_LEN, "ISO-8859-1").trim(),
                new String(buf, NUMCONTROL_LEN + NOMBRE_LEN + APELLIDOS_LEN + SEMESTRE_LEN, GRUPO_LEN, "ISO-8859-1").trim(),
                new String(buf, NUMCONTROL_LEN + NOMBRE_LEN + APELLIDOS_LEN + SEMESTRE_LEN + GRUPO_LEN, CARRERA_LEN, "ISO-8859-1").trim()
            };
        } catch (IOException ex) {
            Logger.getLogger(ArchivoOrgnDir.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }//Si hay una excepción (por ejemplo, se intenta leer fuera del archivo), se registra el error y el método devuelve null.
    }
    public void eliminarLinea(String numControl) {
        try {//Todas las operaciones de E/S (entrada/salida) en Java pueden lanzar excepciones, como cuando el disco está lleno o el archivo está cerrado.
//Calcula en qué parte del archivo se debe escribir el registro. 
            long pos = establecerPosicionByte(numControl);
            //Se calcula en qué posición del archivo está el registro que tiene el número de control que se quiere eliminar.
            raf.seek(pos);//Mueve el puntero del archivo (RandomAccessFile) exactamente al inicio de ese registro.
            raf.write(ajustar("00000000", NUMCONTROL_LEN).getBytes("ISO-8859-1"));
/*ajustar("00000000", NUMCONTROL_LEN):
Asegura que la cadena "00000000" tenga el mismo tamaño en caracteres que el campo del número de control (NUMCONTROL_LEN).
Si el campo es más largo que 8 caracteres, la función lo rellena con espacios o ceros (dependiendo de cómo esté escrita ajustar())
.getBytes("ISO-8859-1"):
Convierte la cadena "00000000" a un arreglo de bytes en el formato adecuado del archivo.
raf.write(...):
Escribe solo esa parte del campo del número de control en el archivo.
Así el resto del registro (nombre, apellidos, etc.) queda intacto, pero ya no se reconocerá este registro como válido.*/
        } catch (IOException ex) {
            Logger.getLogger(ArchivoOrgnDir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void crearLinea(String linea) {
        /*Este método convierte una línea de texto en un registro estructurado
y llama a otro método para guardarlo en el archivo binario.*/
        String[] datos = linea.split(",");//Toma la cadena de entrada linea y la divide por comas.
        if (datos.length == 6) {
            /*Verifica que se hayan capturado exactamente 6 partes
(esto es importante para evitar errores si el formato está mal).
Si no hay 6 partes, no hace nada.*/
            escribirRegistro(datos[0], datos[1], datos[2], Integer.parseInt(datos[3]), datos[4].charAt(0), datos[5]);
    //Si los datos son correctos, llama al método escribirRegistro con los valores convertidos al tipo adecuado.
        }
    }
    public void eliminarArchivo(String archivoEliminar) {
        /*elimina un archivo del sistema de archivos. El archivo que se elimina es el que se pasa como
parámetro en archivoEliminar, pero antes verifica si puede establecer el flujo hacia ese archivo 
(es decir, si el archivo existe y se puede abrir correctamente).*/
        if (this.establecerFlujo(archivoEliminar)) {
        //Si se puede establecer el flujo (es decir, el archivo existe y se puede trabajar con él), se ejecuta lo siguiente.
            fichero.delete();
            //Elimina físicamente el archivo del disco usando el método delete() de la clase File.
//Si el archivo fue encontrado y no está siendo usado por otro proceso, será eliminado.
        }
    }
    public void renombrarArchivo(String archivoRenombrar, String nuevoNombre) {
        //Si el archivo existe, guarda la referencia en la variable this.fichero (de tipo File) y devuelve true.
//Solo si se puede acceder correctamente al archivo original, continúa con el cambio de nombre.
        if (this.establecerFlujo(archivoRenombrar)) {
            File nuevo = new File(nuevoNombre);
            //Crea un nuevo objeto de tipo File que representa el nuevo nombre del archivo.
            this.fichero.renameTo(nuevo);
            //Aquí se intenta renombrar o mover el archivo actual (fichero) al nuevo nombre.
        }
    }
    public boolean validaControl(String numControl) {
        /*ste método valida que el número de control (numControl) cumpla con un formato específico:
Debe contener exactamente 8 dígitos numéricos (del 0 al 9).
Si lo cumple, devuelve true.
Si no lo cumple, devuelve false.
se usa para verificar si una cadena de texto (String) coincide exactamente 
con un patrón especificado mediante una expresión regular (regex).*/
        return numControl.matches("^[0-9]{8}$");
    }
    
}

