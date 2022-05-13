/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.alejandrov.frontend;

import com.alejandrov.backend.Mapa;
import com.alejandrov.backend.MotorJuego;
import com.alejandrov.backend.enums.TipoPlanetas;
import com.alejandrov.backend.enums.TiposJugador;
import com.alejandrov.backend.enums.TiposMapa;
import com.alejandrov.backend.jugador.Jugador;
import com.alejandrov.backend.listas.Lista;
import com.alejandrov.backend.listas.ListaException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * @author aleja
 */
public class MapaDesignFrame extends javax.swing.JFrame {

    private DefaultTableModel model;
    private JFrame parent;
    private int indiceColor;
    Lista<String> nombres;
    MotorJuego juego;

    public MapaDesignFrame(JFrame parent) {
        this.parent = parent;
        initComponents();
        String[] tiposDeMapa = new String[]{
                TiposMapa.VIA_LACTEA.nombre,
                TiposMapa.ANDROMEDA.nombre,
                TiposMapa.CIRCINUS.nombre
        };
        tipoComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(tiposDeMapa));
        reiniciarIndiceColores();
        nombres = new Lista<String>();
        initTablaJugadores();
    }

    public void initTablaJugadores() {
        model = new DefaultTableModel();
        nombres = new Lista<String>();

        model.addColumn("nombre");
        model.addColumn("tipo");

        agregarJugador(TiposJugador.HUMANO.valor);
        agregarJugador(TiposJugador.IA_FACIL.valor);

        tablaJugadores.setModel(model);

        TableColumn columnaTipo = tablaJugadores.getColumnModel().getColumn(1);

        JComboBox comboBox = new JComboBox();
        comboBox.addItem(TiposJugador.HUMANO.valor);
        comboBox.addItem(TiposJugador.IA_DIFICIL.valor);
        comboBox.addItem(TiposJugador.IA_FACIL.valor);

        columnaTipo.setCellEditor(new DefaultCellEditor(comboBox));
    }

    // private Mapa crearMapa() {
    private Mapa crearMapa() throws ValidacionesException {
        sonOpcionesValidas();
        String nombre = nombreTextField.getText();
        int filas = ((Integer) alturaSpinner.getValue());
        int columnas = ((Integer) anchuraSpinner.getValue());
        boolean generarAlAzar = alAzarCheckbox.isSelected();
        boolean esMapaCiego = mapaCiegoCheckbox.isSelected();
        boolean esAcumulativo = produccionAcumuladaCheckbox.isSelected();
        int turnosMaximos = ((Integer) finalizacionSpinner.getValue());
        String tipo = TiposMapa.values()[tipoComboBox.getSelectedIndex()].nombre;
        int planetasNeutrales = ((Integer) planetasNeutralesSpinner.getValue());
        boolean mostrarNavesNeutrales = mostrarNavesCheckbox.isSelected();
        boolean mostrarEstadisticasPlanetasNeutrales = mostrarEstadísticasCheckbox.isSelected();
        int produccionPlanetasNeutrales = ((Integer) produccionSpinner.getValue());
        return new Mapa(nombre, filas, columnas, generarAlAzar, esMapaCiego, esAcumulativo, turnosMaximos, tipo,
                planetasNeutrales, mostrarNavesNeutrales, mostrarEstadisticasPlanetasNeutrales,
                produccionPlanetasNeutrales);
    }

    public Jugador[] crearJugadores() {
        int totalJugadores = tablaJugadores.getRowCount();
        Jugador[] jugadores = new Jugador[totalJugadores];

        for (int i = 0; i < totalJugadores; i++) {
            String nombre = tablaJugadores.getValueAt(i, 0).toString();
            String tipo = tablaJugadores.getValueAt(i, 1).toString();

            jugadores[i] = new Jugador(nombre, tipo,TipoPlanetas.values()[indiceColor].name().toLowerCase());
            indiceColor++;
        }

        return jugadores;
    }

    public void agregarJugador(String tipo) {
        model.addRow(new Object[]{crearNombreJugador(), tipo});
    }

    public void eliminarJugadorSeleccionado() throws ListaException {
        if (tablaJugadores.getSelectedRow() != -1) {
            String jugadorEliminado = tablaJugadores.getValueAt(tablaJugadores.getSelectedRow(), 0).toString();
            model.removeRow(tablaJugadores.getSelectedRow());
            nombres.eliminarContenido(jugadorEliminado);
            JOptionPane.showMessageDialog(this, "Se ha eliminado al jugador");
        }
    }

    public void eliminarJugador(int row) throws ListaException {
            String jugadorEliminado = tablaJugadores.getValueAt(row, 0).toString();
            model.removeRow(row);
            nombres.eliminarContenido(jugadorEliminado);
    }

    public String crearNombreJugador() {
        int numeroJugador = 1;
        String nombre = "Jugador " + numeroJugador;
        while (nombres.incluye(nombre)) {
            nombre = "Jugador " + numeroJugador;
            numeroJugador++;
        }
        nombres.agregar(nombre);
        return nombre;
    }

    private void sonOpcionesValidas() throws ValidacionesException {// validaciones

        // no menos de dos jugadores
        if (tablaJugadores.getRowCount() < 2) {
            AñadirButton.requestFocus();
            throw new ValidacionesException("Debe de haber al menos 2 jugadores", this);
        }

        // el nombre del mapa no existe
        if (nombreTextField.getText().equals("")) {
            nombreTextField.requestFocus();
            throw new ValidacionesException("El mapa debe tener nombre", this);
        }

        // dimensiones erroneas
        if (((Integer) alturaSpinner.getValue()) < 1 || ((Integer) anchuraSpinner.getValue()) < 1) {
            alturaSpinner.requestFocus();
            throw new ValidacionesException("El mapa debe tener dimensiones positivas mayores a 1", this);
        }

        // finalización menor a 1
        if (((Integer) finalizacionSpinner.getValue()) < 1) {
            finalizacionSpinner.requestFocus();
            throw new ValidacionesException("Los turnos máximos (finalización) no pueden ser menor a 1", this);
        }

        //spinners negativos
        if (((Integer) planetasNeutralesSpinner.getValue()) < 0 || ((Integer) produccionSpinner.getValue()) < 0) {
            throw new ValidacionesException("No pueden haber valores negativos ", this);
        }

        //mas de 8 jugadores
        if (tablaJugadores.getRowCount() > 8) {
            throw new ValidacionesException("No pueden haber más de 8 jugadores", this);
        }
    }

    public void configurarJuego() throws ValidacionesException {
        Mapa mapa = crearMapa();
        Jugador[] jugadores = crearJugadores();
        juego = new MotorJuego(jugadores, mapa);
        mapa.setJugadores(jugadores);
        ConfiguracionPlanetasJugadorFrame configPlanetasFrame = new ConfiguracionPlanetasJugadorFrame(this, juego);
        configPlanetasFrame.setLocationRelativeTo(this);
        configPlanetasFrame.setVisible(true);
        this.setEnabled(false);

    }

    public void reiniciarIndiceColores() {
        indiceColor = 0;
    }

    public void guardarArchivoConfiguracion() throws ValidacionesException {
        Mapa mapa = crearMapa();
        mapa.setJugadores(crearJugadores());
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setToolTipText("Guardar Archivo");
        fileChooser.setCurrentDirectory(new File("."));

        int response = fileChooser.showSaveDialog(this);

        if (response == JFileChooser.APPROVE_OPTION) {
            mapa.guardarArchivo(fileChooser.getSelectedFile().getAbsolutePath());
            JOptionPane.showMessageDialog(this,"Se ha guardado la configuración correctamente", "Guardado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void abrirArchivoConfiguracion() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setToolTipText("Seleccionar Archivo");
        fileChooser.setCurrentDirectory(new File("."));

        int response = fileChooser.showOpenDialog(this);

        if (response == JFileChooser.APPROVE_OPTION) {
            File archivo = new File(fileChooser.getSelectedFile().getAbsolutePath());

            try {
                FileInputStream fis = new FileInputStream(archivo);
                ObjectInputStream ois;
                while (fis.available() > 0) {
                    ois = new ObjectInputStream(fis);
                    Mapa mapa = (Mapa) ois.readObject();
                    nombreTextField.setText(mapa.getNombre());
                    planetasNeutralesSpinner.setValue(mapa.getPlanetasNeutrales());
                    anchuraSpinner.setValue(mapa.getColumnas());
                    alturaSpinner.setValue(mapa.getFilas());
                    alAzarCheckbox.setSelected(mapa.esAlAzar());
                    mapaCiegoCheckbox.setSelected(mapa.esMapaCiego());
                    produccionAcumuladaCheckbox.setSelected(mapa.esAcumulativo());
                    mostrarNavesCheckbox.setSelected(mapa.seMuestranNavesNeutrales());
                    mostrarEstadísticasCheckbox.setSelected(mapa.seMuestranEstadisticasPlanetasNeutrales());
                    produccionSpinner.setValue(mapa.getProduccionPlanetasNeutrales());
                    finalizacionSpinner.setValue(mapa.getTurnosMaximos());
                    tipoComboBox.setSelectedItem(mapa.getTipo());
                    cargarJugadores(mapa.getJugadores());
                }
                JOptionPane.showMessageDialog(this,"El archivo se ha cargado correctamente", "Cargado", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void cargarJugadores(Jugador[] jugadores) {
        limpiarTabla();
        int totalJugadores = jugadores.length;
        nombres = new Lista<String>();

        for (int i = 0; i < totalJugadores; i++) {
            String nombre = jugadores[i].getNombre();
            String tipo = jugadores[i].getTipo();
            nombres.agregar(nombre);

            model.addRow(new Object[]{nombre, tipo});
        }
    }

    public void limpiarTabla(){
        while(model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    @Override
    public void dispose() {
        parent.setEnabled(true);
        super.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        nombreTextField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        Setup = new javax.swing.JPanel();
        jugadores = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaJugadores = new javax.swing.JTable();
        buttons = new javax.swing.JPanel();
        AñadirButton = new javax.swing.JButton();
        eliminarButton = new javax.swing.JButton();
        Mapa = new javax.swing.JPanel();
        labelAndSpinner = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        planetasNeutralesSpinner = new javax.swing.JSpinner();
        labelAndSpinner1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        anchuraSpinner = new javax.swing.JSpinner();
        labelAndSpinner2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        alturaSpinner = new javax.swing.JSpinner();
        jSeparator1 = new javax.swing.JSeparator();
        dueño = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        porcentajeMuerte = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        planetasNeutralesSpinner1 = new javax.swing.JSpinner();
        producción = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        planetasNeutralesSpinner2 = new javax.swing.JSpinner();
        alAzarCheckbox = new javax.swing.JCheckBox();
        Opciones = new javax.swing.JPanel();
        mapaCiegoCheckbox = new javax.swing.JCheckBox();
        produccionAcumuladaCheckbox = new javax.swing.JCheckBox();
        neutrales = new javax.swing.JPanel();
        mostrarNavesCheckbox = new javax.swing.JCheckBox();
        mostrarEstadísticasCheckbox = new javax.swing.JCheckBox();
        producción1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        produccionSpinner = new javax.swing.JSpinner();
        finalizacion = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        finalizacionSpinner = new javax.swing.JSpinner();
        finalizacion1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        tipoComboBox = new javax.swing.JComboBox<>();
        Buttons = new javax.swing.JPanel();
        aceptarButton = new javax.swing.JButton();
        cancelarButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Diseño de mapa");
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        jLabel10.setText("Nombre del mapa");
        header.add(jLabel10);

        nombreTextField.setText("Configuraciones recomendadas");
        nombreTextField.setPreferredSize(new java.awt.Dimension(200, 28));
        header.add(nombreTextField);

        jButton1.setText("Guardar configuración");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        header.add(jButton1);

        jButton2.setText("Abrir configuración");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        header.add(jButton2);

        getContentPane().add(header);

        Setup.setPreferredSize(new java.awt.Dimension(366, 360));
        Setup.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 22, 20));

        jugadores.setBorder(javax.swing.BorderFactory.createTitledBorder("Jugadores"));
        jugadores.setPreferredSize(new java.awt.Dimension(200, 320));
        jugadores.setLayout(new javax.swing.BoxLayout(jugadores, javax.swing.BoxLayout.Y_AXIS));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(180, 280));

        tablaJugadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tablaJugadores);

        jugadores.add(jScrollPane2);

        buttons.setPreferredSize(new java.awt.Dimension(172, 35));

        AñadirButton.setText("Añadir");
        AñadirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AñadirButtonActionPerformed(evt);
            }
        });
        buttons.add(AñadirButton);

        eliminarButton.setText("Eliminar");
        eliminarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarButtonActionPerformed(evt);
            }
        });
        buttons.add(eliminarButton);

        jugadores.add(buttons);

        Setup.add(jugadores);

        Mapa.setBorder(javax.swing.BorderFactory.createTitledBorder("Mapa"));
        Mapa.setPreferredSize(new java.awt.Dimension(200, 320));
        Mapa.setLayout(new javax.swing.BoxLayout(Mapa, javax.swing.BoxLayout.Y_AXIS));

        labelAndSpinner.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setText("Planetas neutrales:");
        labelAndSpinner.add(jLabel1);

        planetasNeutralesSpinner.setModel(new javax.swing.SpinnerNumberModel(5, 1, null, 1));
        planetasNeutralesSpinner.setMinimumSize(new java.awt.Dimension(40, 40));
        planetasNeutralesSpinner.setPreferredSize(new java.awt.Dimension(50, 28));
        labelAndSpinner.add(planetasNeutralesSpinner);

        Mapa.add(labelAndSpinner);

        labelAndSpinner1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel2.setText("Anchura:");
        labelAndSpinner1.add(jLabel2);

        anchuraSpinner.setModel(new javax.swing.SpinnerNumberModel(10, 1, null, 1));
        anchuraSpinner.setMinimumSize(new java.awt.Dimension(40, 40));
        anchuraSpinner.setPreferredSize(new java.awt.Dimension(50, 28));
        labelAndSpinner1.add(anchuraSpinner);

        Mapa.add(labelAndSpinner1);

        labelAndSpinner2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel3.setText("Altura");
        labelAndSpinner2.add(jLabel3);

        alturaSpinner.setModel(new javax.swing.SpinnerNumberModel(10, 1, null, 1));
        alturaSpinner.setMinimumSize(new java.awt.Dimension(40, 40));
        alturaSpinner.setPreferredSize(new java.awt.Dimension(50, 28));
        labelAndSpinner2.add(alturaSpinner);

        Mapa.add(labelAndSpinner2);
        Mapa.add(jSeparator1);

        dueño.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel4.setText("Dueño:");
        dueño.add(jLabel4);

        jTextField1.setText("Disponible");
        jTextField1.setEnabled(false);
        jTextField1.setPreferredSize(new java.awt.Dimension(100, 28));
        dueño.add(jTextField1);

        Mapa.add(dueño);

        porcentajeMuerte.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel5.setText("Pocentaje muertes:");
        porcentajeMuerte.add(jLabel5);

        planetasNeutralesSpinner1.setEnabled(false);
        planetasNeutralesSpinner1.setMinimumSize(new java.awt.Dimension(40, 40));
        planetasNeutralesSpinner1.setPreferredSize(new java.awt.Dimension(40, 28));
        planetasNeutralesSpinner1.setValue(0.4);
        porcentajeMuerte.add(planetasNeutralesSpinner1);

        Mapa.add(porcentajeMuerte);

        producción.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel6.setText("Producción:");
        producción.add(jLabel6);

        planetasNeutralesSpinner2.setEnabled(false);
        planetasNeutralesSpinner2.setMinimumSize(new java.awt.Dimension(40, 40));
        planetasNeutralesSpinner2.setPreferredSize(new java.awt.Dimension(50, 28));
        planetasNeutralesSpinner2.setValue(10);
        producción.add(planetasNeutralesSpinner2);

        Mapa.add(producción);

        alAzarCheckbox.setText("Al Azar");
        alAzarCheckbox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        alAzarCheckbox.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        Mapa.add(alAzarCheckbox);

        Setup.add(Mapa);

        Opciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));
        Opciones.setPreferredSize(new java.awt.Dimension(200, 320));
        Opciones.setLayout(new javax.swing.BoxLayout(Opciones, javax.swing.BoxLayout.Y_AXIS));

        mapaCiegoCheckbox.setText("Mapa ciego");
        Opciones.add(mapaCiegoCheckbox);

        produccionAcumuladaCheckbox.setText("Producción acumulativa");
        Opciones.add(produccionAcumuladaCheckbox);

        neutrales.setBorder(javax.swing.BorderFactory.createTitledBorder("Neutrales"));
        neutrales.setLayout(new javax.swing.BoxLayout(neutrales, javax.swing.BoxLayout.Y_AXIS));

        mostrarNavesCheckbox.setText("Mostrar naves");
        neutrales.add(mostrarNavesCheckbox);

        mostrarEstadísticasCheckbox.setText("Mostrar estadísticas");
        neutrales.add(mostrarEstadísticasCheckbox);

        producción1.setPreferredSize(new java.awt.Dimension(110, 15));
        producción1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel7.setText("Producción:");
        producción1.add(jLabel7);

        produccionSpinner.setModel(new javax.swing.SpinnerNumberModel(10, 1, null, 1));
        produccionSpinner.setMinimumSize(new java.awt.Dimension(40, 40));
        produccionSpinner.setPreferredSize(new java.awt.Dimension(50, 28));
        producción1.add(produccionSpinner);

        neutrales.add(producción1);

        Opciones.add(neutrales);

        finalizacion.setPreferredSize(new java.awt.Dimension(110, 15));
        finalizacion.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel8.setText("Finalización");
        finalizacion.add(jLabel8);

        finalizacionSpinner.setModel(new javax.swing.SpinnerNumberModel(12, 1, null, 1));
        finalizacionSpinner.setMinimumSize(new java.awt.Dimension(40, 40));
        finalizacionSpinner.setPreferredSize(new java.awt.Dimension(50, 28));
        finalizacion.add(finalizacionSpinner);

        Opciones.add(finalizacion);

        finalizacion1.setPreferredSize(new java.awt.Dimension(110, 15));
        finalizacion1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel9.setText("Tipo de mapa:");
        finalizacion1.add(jLabel9);

        tipoComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "mapa1", "mapa2", "mapa3" }));
        finalizacion1.add(tipoComboBox);

        Opciones.add(finalizacion1);

        Setup.add(Opciones);

        getContentPane().add(Setup);

        Buttons.setPreferredSize(new java.awt.Dimension(700, 40));
        Buttons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        aceptarButton.setText("Aceptar");
        aceptarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarButtonActionPerformed(evt);
            }
        });
        Buttons.add(aceptarButton);

        cancelarButton.setText("Cancelar");
        cancelarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarButtonActionPerformed(evt);
            }
        });
        Buttons.add(cancelarButton);

        getContentPane().add(Buttons);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelarButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelarButtonActionPerformed
        this.dispose();
    }// GEN-LAST:event_cancelarButtonActionPerformed

    private void AñadirButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_AñadirButtonActionPerformed
        agregarJugador(TiposJugador.HUMANO.valor);
    }// GEN-LAST:event_AñadirButtonActionPerformed

    private void eliminarButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_eliminarButtonActionPerformed
        try {
            eliminarJugadorSeleccionado();
        } catch (ListaException e) {
            e.printStackTrace();
        }
    }// GEN-LAST:event_eliminarButtonActionPerformed

    private void aceptarButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_aceptarButtonActionPerformed
        try {
            configurarJuego();
        } catch (ValidacionesException e) {
            e.printStackTrace();
        }
    }// GEN-LAST:event_aceptarButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        try {
            guardarArchivoConfiguracion();
        } catch (ValidacionesException e) {
            System.out.println("Error al guardar");
        }
    }// GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
        abrirArchivoConfiguracion();
    }// GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AñadirButton;
    private javax.swing.JPanel Buttons;
    private javax.swing.JPanel Mapa;
    private javax.swing.JPanel Opciones;
    private javax.swing.JPanel Setup;
    private javax.swing.JButton aceptarButton;
    private javax.swing.JCheckBox alAzarCheckbox;
    private javax.swing.JSpinner alturaSpinner;
    private javax.swing.JSpinner anchuraSpinner;
    private javax.swing.JPanel buttons;
    private javax.swing.JButton cancelarButton;
    private javax.swing.JPanel dueño;
    private javax.swing.JButton eliminarButton;
    private javax.swing.JPanel finalizacion;
    private javax.swing.JPanel finalizacion1;
    private javax.swing.JSpinner finalizacionSpinner;
    private javax.swing.JPanel header;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel jugadores;
    private javax.swing.JPanel labelAndSpinner;
    private javax.swing.JPanel labelAndSpinner1;
    private javax.swing.JPanel labelAndSpinner2;
    private javax.swing.JCheckBox mapaCiegoCheckbox;
    private javax.swing.JCheckBox mostrarEstadísticasCheckbox;
    private javax.swing.JCheckBox mostrarNavesCheckbox;
    private javax.swing.JPanel neutrales;
    private javax.swing.JTextField nombreTextField;
    private javax.swing.JSpinner planetasNeutralesSpinner;
    private javax.swing.JSpinner planetasNeutralesSpinner1;
    private javax.swing.JSpinner planetasNeutralesSpinner2;
    private javax.swing.JPanel porcentajeMuerte;
    private javax.swing.JCheckBox produccionAcumuladaCheckbox;
    private javax.swing.JSpinner produccionSpinner;
    private javax.swing.JPanel producción;
    private javax.swing.JPanel producción1;
    private javax.swing.JTable tablaJugadores;
    private javax.swing.JComboBox<String> tipoComboBox;
    // End of variables declaration//GEN-END:variables
}