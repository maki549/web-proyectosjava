package vista; 
// Define que esta clase pertenece al paquete 'vista', el cual normalmente contiene las interfaces gráficas (Vistas) en un patrón MVC.
import controlador.ControladorCRUDEstudiantes;
// Importa la clase ControladorCRUDEstudiantes que gestiona la lógica entre la vista y el modelo.
import java.awt.Color;
// Importa la clase Color para usar colores personalizados en la interfaz (por ejemplo, para resaltar una fila)
import java.awt.event.KeyEvent;
// Importa la clase KeyEvent que permite manejar eventos del teclado (por ejemplo, si el usuario presiona una tecla).
import javax.swing.JOptionPane;
// Importa la clase JOptionPane que permite mostrar cuadros de diálogo como mensajes, confirmaciones o alertas.
import javax.swing.table.DefaultTableModel;
// Importa el modelo de tabla que se usa para manejar los datos que se muestran en un JTable.
import modelo.ArchivoOrgnDir;
// Importa la clase ArchivoOrgnDir que pertenece al modelo y maneja la lectura y escritura en el archivo.
public class VistaCRUDEstudiantes extends javax.swing.JFrame {
// Esta clase define una ventana gráfica (hereda de JFrame) que se usará como interfaz principal del CRUD.
    ControladorCRUDEstudiantes objControladorCRUDEst;
    // Declara un objeto del controlador que conectará esta vista con el modelo.
    ArchivoOrgnDir objArchivo; // Pertenece a modelo
    // Declara un objeto del modelo que maneja directamente los datos del archivo.
    private int filaResaltada = -1; // -1 significa que no hay fila resaltada
    // Esta variable guarda el índice de la fila resaltada en la tabla. Se usa para darle color a una fila si es necesario.
    public VistaCRUDEstudiantes() {
        // Constructor de la clase, se ejecuta al crear una nueva ventana VistaCRUDEstudiantes.
        initComponents();
        // Método generado por NetBeans (o cualquier GUI Builder) que inicializa todos los componentes gráficos (botones, cajas de texto, tabla, etc.).
        this.objArchivo = new ArchivoOrgnDir();
        // Crea una nueva instancia del modelo, que permite trabajar con el archivo de estudiantes.
        this.objControladorCRUDEst = new ControladorCRUDEstudiantes(this, this.objArchivo);
        // Crea una nueva instancia del controlador, pasándole como parámetros esta vista (para que pueda acceder a los componentes) y el modelo.
        this.objControladorCRUDEst.llenarTabla(); 
        // Llama al método llenarTabla del controlador para que la tabla de estudiantes se llene con los datos leídos del archivo.
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNumControlBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblEstudiantes = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Agenda de Estudiantes");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Tecnológico del Valle de Oaxaca");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Agenda de Estudiantes");

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar232px.png"))); // NOI18N
        btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarMouseClicked(evt);
            }
        });
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        btnBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBuscarKeyPressed(evt);
            }
        });

        jtblEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Num. Control", "Nombre", "Apellidos", "Semestre", "Grupo", "Carrera"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtblEstudiantes);

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/agregar48px.png"))); // NOI18N
        btnAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarMouseClicked(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar48px.png"))); // NOI18N
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/editar48px.png"))); // NOI18N
        btnEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAgregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 25, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNumControlBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscar)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(txtNumControlBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregar)
                    .addComponent(btnEliminar)
                    .addComponent(btnEditar))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMouseClicked
        // TODO add your handling code here:                
        FrmAgregarEstudiante objAgregarEstudiante = new FrmAgregarEstudiante(this.objControladorCRUDEst);
        objAgregarEstudiante.setVisible(true);
    }//GEN-LAST:event_btnAgregarMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        // TODO add your handling code here:
        FrmEliminarEstudiante objEliminarEstudiante = new FrmEliminarEstudiante(this.objControladorCRUDEst);
        objEliminarEstudiante.setVisible(true);
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseClicked
        // TODO add your handling code here:
        FrmModificarEstudiante objModificarEstudiante = new FrmModificarEstudiante(this.objControladorCRUDEst);
        objModificarEstudiante.setVisible(true);
    }//GEN-LAST:event_btnEditarMouseClicked

    private void btnBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarMouseClicked

    private void btnBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBuscarKeyPressed
        // TODO add your handling code here:
         
    }//GEN-LAST:event_btnBuscarKeyPressed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
    // Este método se ejecuta automáticamente cuando el usuario hace clic en el botón "Buscar".
    String numControlBuscar = txtNumControlBuscar.getText().trim();
    // Se obtiene el texto escrito en la caja de texto para buscar por número de control.
    // .trim() elimina espacios al inicio y al final.
    if (!numControlBuscar.isEmpty()) {
        // Verifica que el campo no esté vacío
        String[] datosEstudiante = objControladorCRUDEst.buscarRegistro(numControlBuscar);
        // Se llama al controlador para buscar el registro en el archivo usando el número de control.
        // Si se encuentra, se regresa un arreglo con los datos del estudiante; si no, regresa null.
        if (datosEstudiante != null) {
            // Si se encontró un registro en el archivo:
            DefaultTableModel model = (DefaultTableModel) jtblEstudiantes.getModel();
            // Se obtiene el modelo de la tabla para poder acceder a sus filas y columnas.
            boolean encontrado = false;
            // Variable para saber si el número de control también fue encontrado en la tabla visual.
            for (int i = 0; i < model.getRowCount(); i++) {
                // Se recorre cada fila de la tabla
                Object valorCelda = model.getValueAt(i, 0);
                // Se obtiene el valor de la primera columna (número de control) de la fila actual.
                if (valorCelda != null) {
                    String numControl = valorCelda.toString().trim();
                    // Se convierte a texto y se eliminan espacios.
                    if (numControl.equals(numControlBuscar)) {
                        // Si el número de control de la fila coincide con el que se está buscando:
                        jtblEstudiantes.setRowSelectionInterval(i, i);
                        // Selecciona visualmente esa fila en la tabla.
                        jtblEstudiantes.scrollRectToVisible(jtblEstudiantes.getCellRect(i, 0, true));
                        // Desplaza la vista de la tabla para que la fila encontrada sea visible.
                        filaResaltada = i;
                        // Guarda el índice de la fila resaltada (posiblemente para cambiarle el color después).
                        jtblEstudiantes.repaint();
                        // Vuelve a dibujar la tabla (útil si hay un render especial para la fila resaltada).
                        encontrado = true;
                        break; // Sale del ciclo porque ya encontró la fila.
                    }
                }
            }
            if (!encontrado) {
                // Si el número de control existía en el archivo, pero no se encontró en la tabla visual:
                JOptionPane.showMessageDialog(this, "Estudiante no encontrado en la tabla.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            // Si no se encontró el número de control en el archivo (datosEstudiante es null):
            JOptionPane.showMessageDialog(this, "Estudiante no encontrado en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        // Si el usuario no ingresó ningún número de control para buscar:
        JOptionPane.showMessageDialog(this, "Por favor, ingresa un número de control para buscar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
    txtNumControlBuscar.setText("");
    // Limpia la caja de texto después de buscar.
    }//GEN-LAST:event_btnBuscarActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaCRUDEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaCRUDEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaCRUDEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaCRUDEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaCRUDEstudiantes().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jtblEstudiantes;
    private javax.swing.JTextField txtNumControlBuscar;
    // End of variables declaration//GEN-END:variables
}
