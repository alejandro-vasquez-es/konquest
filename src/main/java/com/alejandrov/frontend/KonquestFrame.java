/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.alejandrov.frontend;

import com.alejandrov.backend.Mapa;
import com.alejandrov.backend.MotorJuego;
import com.alejandrov.backend.Posicion;
import com.alejandrov.backend.listas.Lista;
import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.componentes.Cuadro;
import com.alejandrov.frontend.planetas.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * @author aleja
 */
public class KonquestFrame extends javax.swing.JFrame {

    final private int ALTURA_MAIN;
    private JPanel cuadricula;
    private Cuadro[][] cuadros;
    private Cuadro[] cuadrosClickeados;
    private int indiceCuadrosClickeados;
    private MotorJuego juego;
    private DefaultListModel<String> modelAreaMensajes;

    public KonquestFrame() {

        initComponents();

        setLocationRelativeTo(null);
        ALTURA_MAIN = Center.getHeight() - 10;
        Messages.setVisible(false);
        cuadrosClickeados = new Cuadro[2];
        indiceCuadrosClickeados = 0;

    }

    public void crearCuadricula(MotorJuego juego) {
        Mapa mapa = juego.getMapa();
        int columnas = mapa.getColumnas();
        int filas = mapa.getFilas();
        cuadricula = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        Border lineaNegra = Cuadro.LINEA_NEGRA;
        cuadricula.setBorder(lineaNegra);
        cuadricula.setBackground(new Color(156, 156, 156));

        int ladoCuadro = (int) Math.floor(ALTURA_MAIN / filas);

        cuadricula.setMinimumSize(new Dimension(ladoCuadro * columnas, ladoCuadro * filas));
        cuadricula.setMaximumSize(new Dimension(ladoCuadro * columnas, ladoCuadro * filas));

        cuadros = new Cuadro[columnas][filas];

        for (int i = 0; i < columnas; i++) {
            for (int j = 0; j < filas; j++) {
                Cuadro cuadro = new Cuadro(juego);
                cuadros[i][j] = cuadro;

                cuadro.setPreferredSize(new Dimension(ladoCuadro, ladoCuadro));
                cuadro.setBorder(lineaNegra);
                c.fill = GridBagConstraints.BOTH;
                c.gridx = i;
                c.gridy = j;
                cuadricula.add(cuadro, c);
            }
        }

        Center.add(cuadricula);
    }

    public void deseleccionarCuadros() {
        for (int i = 0; i < 2; i++) {
            if (cuadrosClickeados[i] != null) {
                cuadrosClickeados[i].deselccionar();
                cuadrosClickeados[i] = null;
                indiceCuadrosClickeados = 0;
            }
        }
    }

    public void agregarFlotaAterrizada(String mensaje) {
        modelAreaMensajes.addElement(mensaje);
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void terminarTurnoJugador() throws ListaException {
        if (juego.getIndiceJugadorActivo() == 2)
            actualizarAreaMensajes();
        juego.terminarTurnoJugador();
        deseleccionarCuadros();
        setinstruccionLabel();
        setToolTips();
        JOptionPane.showMessageDialog(this, "Turno del jugador " + juego.getJugadorActivo().getNombre());
        cuadricula.revalidate();
        cuadricula.repaint();
    }

    public void prepararFrame(final MotorJuego juego) throws ListaException {
        this.juego = juego;
        getMessages().setVisible(true);
        getCenter().removeAll();
        getCenter().revalidate();
        getCenter().repaint();
        ImageIcon imagen = new ImageIcon(getClass().getResource("/imagenes/mapa/" + juego.getMapa().getTipo() + ".jpg"));
        setFondo(imagen);
        crearCuadricula(juego);
        JButton deseleccionarButton = new JButton("Deseleccionar");
        deseleccionarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionarCuadros();
            }
        });
        Center.add(deseleccionarButton);
        setToolTips();
        iniciarAreaMensajes();
        setinstruccionLabel();
        terminarJuegoButton.setEnabled(true);
        terminarTurnoButton.setEnabled(true);
        calcularDistanciaButton.setEnabled(true);
        consultaFlotaButton.setEnabled(true);
        mandarNavesButton.setEnabled(true);
        navesTextField.setEnabled(true);
        deseleccionarCuadros();
    }

    public void iniciarAreaMensajes() {
        modelAreaMensajes = new DefaultListModel<String>();
        areaMensajes.setModel(modelAreaMensajes);
    }

    public void actualizarAreaMensajes() {
        String guiones = "";
        for (int i = 0; i < 260; i++) {
            guiones += "-";
        }
        modelAreaMensajes.addElement(guiones);
        modelAreaMensajes.addElement("Turno " + (juego.getTurno() + 1));
    }

    public void setinstruccionLabel() {
        instruccionLabel.setText("  Turno del jugador \"" + juego.getJugadorActivo().getNombre() + "\", seleccione el planeta de origen");
    }

    public void setToolTips() throws ListaException {
        setToolTipsPlanetasJugador();
        setToolTipsPlanetasNeutrales();
        setToolTipsPlanetasFantasmas();
        setToolTipsPlanetasZombies();
    }

    public void setToolTipsPlanetasNeutrales() throws ListaException {
        Lista<PlanetaNeutral> planetas = juego.getMapa().getPlanetasNeutrales();
        for (int i = 0; i < planetas.obtenerLongitud(); i++) {
            PlanetaNeutral planeta = planetas.obtenerContenido(i);
            Posicion pos = planeta.getPosicion();
            String tooltip = planeta.toString(new boolean[]{juego.getMapa().seMuestranNavesNeutrales(), juego.getMapa().seMuestranEstadisticasPlanetasNeutrales()});
            cuadros[pos.getColumna()][pos.getFila()].setToolTipText(tooltip);
        }
    }

    public void setToolTipsPlanetasZombies() throws ListaException {
        Lista<PlanetaZombie> planetas = juego.getMapa().getPlanetasZombie();
        for (int i = 0; i < planetas.obtenerLongitud(); i++) {
            PlanetaZombie planeta = planetas.obtenerContenido(i);
            Posicion pos = planeta.getPosicion();
            String tooltip = "Planeta Zombie";
            cuadros[pos.getColumna()][pos.getFila()].setToolTipText(tooltip);
        }
    }

    public void setToolTipsPlanetasFantasmas() throws ListaException {
        Lista<PlanetaFantasma> planetas = juego.getMapa().getPlanetasFantasma();
        for (int i = 0; i < planetas.obtenerLongitud(); i++) {
            PlanetaFantasma planeta = planetas.obtenerContenido(i);
            Posicion pos = planeta.getPosicion();
            String tooltip = planeta.toString(new boolean[]{juego.getMapa().seMuestranNavesNeutrales(), juego.getMapa().seMuestranEstadisticasPlanetasNeutrales()});
            cuadros[pos.getColumna()][pos.getFila()].setToolTipText(tooltip);
        }
    }

    public void setToolTipsPlanetasJugador() throws ListaException {
        Lista<PlanetaJugador> planetas = juego.getMapa().getPlanetasJugador();
        for (int i = 0; i < planetas.obtenerLongitud(); i++) {
            PlanetaJugador planeta = planetas.obtenerContenido(i);
            Posicion pos = planeta.getPosicion();
            boolean esCiego = juego.getMapa().esMapaCiego();
            if (((PlanetaJugador) planeta).getJugador().equals(juego.getJugadorActivo())) {
                esCiego = false;
            }
            String tooltip = planeta.toString(esCiego);
            cuadros[pos.getColumna()][pos.getFila()].setToolTipText(tooltip);
        }
    }

    public void calcularDistancia() {
        if (hayCuadrosSeleccionados()) {
            int distancia = Mapa.medirDistancia(cuadrosClickeados[0].getPlaneta().getPosicion(), cuadrosClickeados[1].getPlaneta().getPosicion());
            JOptionPane.showMessageDialog(this, "La distancia entre ambos planetas es de " + distancia + " años luz", "Distancia entre planetas", JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(this, "Por favor selecciona dos planetas para realizar la medición", "Selección de planetas", JOptionPane.WARNING_MESSAGE);

        }
    }


    public boolean hayCuadrosSeleccionados() {
        return cuadrosClickeados[0] != null && cuadrosClickeados[1] != null;
    }

    public void consultarFlota() {
        FlotasFrame flotasFrame = new FlotasFrame(juego.getJugadorActivo().getFlotas(), juego.getTurno(), juego.getJugadorActivo(), this);
        flotasFrame.setLocationRelativeTo(this);
        flotasFrame.setVisible(true);
    }

    private void enviarNaves() throws ValidacionesException, ListaException {
        validacioneEnviarNaves();
        int naves = Integer.parseInt(navesTextField.getText());
        juego.nuevaFlota(cuadrosClickeados, naves);
        setToolTips();
        JOptionPane.showMessageDialog(this, "El envío de naves se realizó exitósamente, puedes visualizar más información en el botón de \"Consulta de Flota\"");
        navesTextField.setText("");
        deseleccionarCuadros();
    }

    public void validacioneEnviarNaves() throws ValidacionesException {
        //cuadros seleccionados
        if (!hayCuadrosSeleccionados()) {
            throw new ValidacionesException("Por favor selecciona dos planetas para enviar naves", this);
        }

        //no hay naves escritas
        if (navesTextField.getText().equals("")) {
            deseleccionarCuadros();
            navesTextField.setText("");
            throw new ValidacionesException("Por favor escribe cuántas naves quieres mandar", this);
        }

        //el planeta no tiene naves suficientes
        if (Integer.parseInt(navesTextField.getText()) > cuadrosClickeados[0].getPlaneta().getCantidadNaves()) {
            deseleccionarCuadros();
            navesTextField.setText("");
            throw new ValidacionesException("El planeta seleccionado no tiene las naves suficientes", this);
        }

        // no se pueden conquistar planetas zombies
        if (cuadrosClickeados[1].getPlaneta() instanceof PlanetaZombie) {
            deseleccionarCuadros();
            navesTextField.setText("");
            throw new ValidacionesException("No se pueden conquistar planetas zombies", this);
        }
    }

    public JPanel getMessages() {
        return Messages;
    }

    public JPanel getCenter() {
        return Center;
    }

    public Cuadro[][] getCuadros() {
        return cuadros;
    }

    public void setFondo(ImageIcon icon) {
        fondoLabel.setIcon(icon);
    }

    public Cuadro[] getCuadrosClickeados() {
        return cuadrosClickeados;
    }

    public int getIndiceCuadrosClickeados() {
        return indiceCuadrosClickeados;
    }

    public void aumentarIndiceCuadrosClickeados() {
        if (indiceCuadrosClickeados == 1) {
            navesTextField.requestFocus();
        }
        indiceCuadrosClickeados++;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        North = new javax.swing.JPanel();
        menuAcciones = new javax.swing.JPanel();
        nuevoButton = new javax.swing.JButton();
        terminarJuegoButton = new javax.swing.JButton();
        terminarTurnoButton = new javax.swing.JButton();
        calcularDistanciaButton = new javax.swing.JButton();
        consultaFlotaButton = new javax.swing.JButton();
        menuInstrucciones = new javax.swing.JPanel();
        instruccionLabel = new javax.swing.JLabel();
        Right = new javax.swing.JPanel();
        navesMandarLabel = new javax.swing.JLabel();
        navesTextField = new javax.swing.JTextField();
        mandarNavesButton = new javax.swing.JButton();
        Center = new javax.swing.JPanel();
        Messages = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaMensajes = new javax.swing.JList<>();
        fondoLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Konquest");
        setMaximumSize(new java.awt.Dimension(1100, 680));
        setMinimumSize(new java.awt.Dimension(1100, 680));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        North.setPreferredSize(new java.awt.Dimension(1100, 80));
        North.setLayout(new javax.swing.BoxLayout(North, javax.swing.BoxLayout.Y_AXIS));

        menuAcciones.setPreferredSize(new java.awt.Dimension(1100, 38));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
        flowLayout1.setAlignOnBaseline(true);
        menuAcciones.setLayout(flowLayout1);

        nuevoButton.setText("Nuevo");
        nuevoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoButtonActionPerformed(evt);
            }
        });
        menuAcciones.add(nuevoButton);

        terminarJuegoButton.setText("Terminar Juego");
        terminarJuegoButton.setEnabled(false);
        menuAcciones.add(terminarJuegoButton);

        terminarTurnoButton.setText("Terminar turno");
        terminarTurnoButton.setEnabled(false);
        terminarTurnoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                terminarTurnoButtonActionPerformed(evt);
            }
        });
        menuAcciones.add(terminarTurnoButton);

        calcularDistanciaButton.setText("Calcular distancia");
        calcularDistanciaButton.setEnabled(false);
        calcularDistanciaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcularDistanciaButtonActionPerformed(evt);
            }
        });
        menuAcciones.add(calcularDistanciaButton);

        consultaFlotaButton.setText("Consulta de flota");
        consultaFlotaButton.setEnabled(false);
        consultaFlotaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultaFlotaButtonActionPerformed(evt);
            }
        });
        menuAcciones.add(consultaFlotaButton);

        North.add(menuAcciones);

        menuInstrucciones.setBackground(new java.awt.Color(167, 167, 167));
        menuInstrucciones.setPreferredSize(new java.awt.Dimension(1100, 42));
        menuInstrucciones.setLayout(new java.awt.BorderLayout());
        menuInstrucciones.add(instruccionLabel, java.awt.BorderLayout.LINE_START);

        Right.setBackground(new java.awt.Color(167, 167, 167));
        Right.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        navesMandarLabel.setText("Naves a mandar:");
        Right.add(navesMandarLabel);

        navesTextField.setEnabled(false);
        navesTextField.setPreferredSize(new java.awt.Dimension(60, 28));
        Right.add(navesTextField);

        mandarNavesButton.setText("Enviar Naves");
        mandarNavesButton.setEnabled(false);
        mandarNavesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mandarNavesButtonActionPerformed(evt);
            }
        });
        Right.add(mandarNavesButton);

        menuInstrucciones.add(Right, java.awt.BorderLayout.LINE_END);

        North.add(menuInstrucciones);

        getContentPane().add(North, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        Center.setBorder(null);
        Center.setOpaque(false);
        Center.setPreferredSize(new java.awt.Dimension(1100, 400));
        getContentPane().add(Center, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, -1, -1));

        Messages.setBorder(javax.swing.BorderFactory.createTitledBorder("Mensajes"));
        Messages.setPreferredSize(new java.awt.Dimension(1100, 200));

        areaMensajes.setBackground(new java.awt.Color(51, 51, 51));
        areaMensajes.setForeground(new java.awt.Color(199, 199, 199));
        jScrollPane1.setViewportView(areaMensajes);

        javax.swing.GroupLayout MessagesLayout = new javax.swing.GroupLayout(Messages);
        Messages.setLayout(MessagesLayout);
        MessagesLayout.setHorizontalGroup(
            MessagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1072, Short.MAX_VALUE)
        );
        MessagesLayout.setVerticalGroup(
            MessagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
        );

        getContentPane().add(Messages, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, -1, -1));

        fondoLabel.setOpaque(true);
        fondoLabel.setPreferredSize(new java.awt.Dimension(1100, 680));
        getContentPane().add(fondoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nuevoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoButtonActionPerformed
        MapaDesignFrame mapaDesign = new MapaDesignFrame(this);
        mapaDesign.setLocationRelativeTo(this);
        mapaDesign.setVisible(true);
        this.setEnabled(false);
        revalidate();
        repaint();
    }//GEN-LAST:event_nuevoButtonActionPerformed

    private void calcularDistanciaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcularDistanciaButtonActionPerformed
        calcularDistancia();
    }//GEN-LAST:event_calcularDistanciaButtonActionPerformed

    private void mandarNavesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mandarNavesButtonActionPerformed
        try {
            enviarNaves();
        } catch (ValidacionesException e) {
            e.printStackTrace();
        } catch (ListaException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_mandarNavesButtonActionPerformed

    private void consultaFlotaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultaFlotaButtonActionPerformed
        consultarFlota();
    }//GEN-LAST:event_consultaFlotaButtonActionPerformed

    private void terminarTurnoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_terminarTurnoButtonActionPerformed
        try {
            terminarTurnoJugador();
        } catch (ListaException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_terminarTurnoButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Center;
    private javax.swing.JPanel Messages;
    private javax.swing.JPanel North;
    private javax.swing.JPanel Right;
    private javax.swing.JList<String> areaMensajes;
    private javax.swing.JButton calcularDistanciaButton;
    private javax.swing.JButton consultaFlotaButton;
    private javax.swing.JLabel fondoLabel;
    private javax.swing.JLabel instruccionLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton mandarNavesButton;
    private javax.swing.JPanel menuAcciones;
    private javax.swing.JPanel menuInstrucciones;
    private javax.swing.JLabel navesMandarLabel;
    private javax.swing.JTextField navesTextField;
    private javax.swing.JButton nuevoButton;
    private javax.swing.JButton terminarJuegoButton;
    private javax.swing.JButton terminarTurnoButton;
    // End of variables declaration//GEN-END:variables
}
