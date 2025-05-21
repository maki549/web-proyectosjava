//Controlador
package controlador;
import javax.swing.table.DefaultTableModel;
import modelo.ArchivoOrgnDir;
import vista.VistaCRUDEstudiantes;
public class ControladorCRUDEstudiantes {
    public VistaCRUDEstudiantes objVistaCRUDEst; //se usa para acceder a la interfaz gráfica.
    private ArchivoOrgnDir objArchivo;//maneja las operaciones con archivos (lectura, escritura, etc.).
    private DefaultTableModel modelo;//estructura que representa los datos para una tabla (JTable).
    public ControladorCRUDEstudiantes(VistaCRUDEstudiantes vista, ArchivoOrgnDir archivo) {
        this.objVistaCRUDEst = vista;
        this.objArchivo = archivo;
        this.modelo = null;
        /*Asocia el controlador con una vista y un archivo.
        Inicializa el modelo de tabla en null.*/
    }
    public void llenarTabla() {
        String[] columnas = {"Num.Control", "Nombre", "Apellidos", "Semestre", "Grupo", "Carrera"};
        //Define los nombres de las columnas para la tabla.
        this.objArchivo = new ArchivoOrgnDir();
        //Crea una nueva instancia del manejador de archivos.
        if (this.objArchivo.abrirArchivoRAF("estudiantes.dat")) {
            //Abre el archivo estudiantes.dat con acceso aleatorio.
            String[][] filas = this.objArchivo.obtenerMatrizRegistros(columnas.length);
            //Obtiene una matriz con los registros del archivo (tantas columnas como las definidas).
            this.objArchivo.cerrarArchivo();
            //Cierra el archivo.
            if (filas != null) {
                modelo = new DefaultTableModel(filas, columnas);
                this.objVistaCRUDEst.jtblEstudiantes.setModel(modelo);
            } else {
                System.out.println("No se pudieron leer los registros del archivo.");
            }//Mensajes de error si no se pudo leer o abrir el archivo.
        } else {
            System.out.println("Error al abrir el archivo para llenar la tabla.");
        }//Si se leyeron registros, se crea un modelo de tabla y se muestra en la vista.
    }
    public void guardarRegistro(String nc, String nom, String ape, int sem, char gpo, String carrera) {
        this.objArchivo = new ArchivoOrgnDir();
       //Se crea una nueva instancia para escribir datos.
        if (this.objArchivo.abrirArchivoRAF("estudiantes.dat")) {
            //Abre el archivo de estudiantes.
            this.objArchivo.escribirRegistro(nc, nom, ape, sem, gpo, carrera);
            //Escribe un nuevo registro con los datos recibidos.
            this.objArchivo.cerrarArchivo();
            this.llenarTabla();
            //Cierra el archivo y actualiza la tabla de la vista con los nuevos datos.
        } else {
            System.out.println("Error al abrir el archivo para guardar.");
        }
    }
    public String[] buscarRegistro(String nc) {
        /*Se inicializa una variable para guardar los datos encontrados.
Se crea una nueva instancia del archivo.*/

        String[] datos = null;
        this.objArchivo = new ArchivoOrgnDir();

        if (this.objArchivo.abrirArchivoRAF("estudiantes.dat")) {
            datos = this.objArchivo.buscarDato(nc);
            //Abre el archivo y busca el dato con el número de control (nc).
            this.objArchivo.cerrarArchivo();
        } else {
            System.out.println("Error al abrir el archivo para buscar.");
        }
        return datos;//Cierra el archivo y devuelve el arreglo de datos del estudiante encontrado (o null si no se encontró).
    }
    public boolean validaNumControl(String numControl) {
        //Verifica si el número de control cumple el formato correcto usando una expresión regular (8 dígitos).
        this.objArchivo = new ArchivoOrgnDir();
        return this.objArchivo.validaControl(numControl);
    }
    public void eliminarRegistro(String nc) {
        this.objArchivo = new ArchivoOrgnDir();
//Abre el archivo y "borra" el registro con ese número de control (probablemente sobrescribiendo con "00000000").  
        if (this.objArchivo.abrirArchivoRAF("estudiantes.dat")) {
            this.objArchivo.eliminarLinea(nc);
            this.objArchivo.cerrarArchivo();
            this.llenarTabla();
        } else {
            System.out.println("Error al abrir el archivo para eliminar.");
        }//Actualiza la tabla para reflejar los cambios.
    }
    public void editarRegistro(String nc, String nuevoNom, String nuevoApe, int nuevoSem, char nuevoGpo, String nuevaCarrera) {
        this.objArchivo = new ArchivoOrgnDir();
//Prepara los nuevos datos para ser escritos.
        if (this.objArchivo.abrirArchivoRAF("estudiantes.dat")) {
            this.objArchivo.escribirRegistro(nc, nuevoNom, nuevoApe, nuevoSem, nuevoGpo, nuevaCarrera);
           //Escribe los nuevos datos en la posición del mismo número de control (sobrescribe).
            this.objArchivo.cerrarArchivo();
            //Cierra el archivo y actualiza la tabla para reflejar los cambios.
            this.llenarTabla();
        } else {
            System.out.println("Error al abrir el archivo para editar.");
        }
    }
    /*llenarTabla()	Lee todos los registros y los muestra en la tabla de la vista.
guardarRegistro(...)	Guarda un nuevo estudiante en el archivo y actualiza la tabla.
buscarRegistro(nc)	Devuelve los datos de un estudiante según su número de control.
validaNumControl(...)	Verifica que el número de control tenga 8 dígitos.
eliminarRegistro(nc)	Marca un registro como eliminado y actualiza la tabla.
editarRegistro(...)	Sobrescribe un registro existente con datos nuevos y actualiza la tabla.
*/
}
