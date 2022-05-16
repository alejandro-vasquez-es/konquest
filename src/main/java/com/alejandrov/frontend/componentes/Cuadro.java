package com.alejandrov.frontend.componentes;

import com.alejandrov.backend.MotorJuego;
import com.alejandrov.frontend.KonquestFrame;
import com.alejandrov.frontend.planetas.Planeta;
import com.alejandrov.frontend.planetas.PlanetaJugador;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cuadro extends JPanel implements Cloneable {

    private Planeta planeta;
    private boolean clicked;
    private MotorJuego juego;

    public final static Border LINEA_NEGRA = BorderFactory.createLineBorder(new Color(40, 40, 40, 255), 1);
    public final static Border LINEA_ROJA = BorderFactory.createLineBorder(new Color(194, 31, 47, 255), 2);

    public Cuadro(final MotorJuego juego) {
        this.juego = juego;
        clicked = false;
        setOpaque(false);
        setLayout(null);

        addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt) {
                KonquestFrame frame = juego.getFrame();
                if (planeta != null) {
                    if (planeta instanceof PlanetaJugador && ((PlanetaJugador) planeta).getJugador().equals(juego.getJugadorActivo())) {
                        if (frame.getIndiceCuadrosClickeados() == 0) {
                            seleccionar();
                            frame.getCuadrosClickeados()[0] = Cuadro.this;
                            frame.aumentarIndiceCuadrosClickeados();
                        } else if (frame.getIndiceCuadrosClickeados() == 1 && !frame.getCuadrosClickeados()[0].equals(Cuadro.this)) {
                            seleccionar();
                            frame.getCuadrosClickeados()[1] = Cuadro.this;
                            frame.aumentarIndiceCuadrosClickeados();
                        }
                    } else if(frame.getIndiceCuadrosClickeados() == 0){
                        JOptionPane.showMessageDialog(frame, "El planeta que has seleccionado no pertenece a " + juego.getJugadorActivo().getNombre(), "Error al seleccionar el planeta", JOptionPane.WARNING_MESSAGE);
                    }else if(frame.getIndiceCuadrosClickeados() == 1){
                        seleccionar();
                        frame.getCuadrosClickeados()[1] = Cuadro.this;
                        frame.aumentarIndiceCuadrosClickeados();
                    }

                }
            }

        });
    }

    public void seleccionar() {
        if (!isClicked()) {
            setBorder(LINEA_ROJA);
            setIcon();
            clicked = true;
        }
    }

    public void deselccionar() {
        if (isClicked()) {
            setBorder(LINEA_NEGRA);
            setIcon();
            clicked = false;
        }
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setPlaneta(Planeta planeta) {
        this.planeta = planeta;
    }

    public void setIcon() {
        int ladoCuadro = getPreferredSize().width;
        int padding = (int) Math.floor(ladoCuadro * 0.08);
        ladoCuadro -= padding * 2;
        ladoCuadro -= 2; //border

        JLabel nombre = new JLabel(planeta.getNombre());
        add(nombre);
        nombre.setBounds(3,0,getPreferredSize().width,18);

        JLabel bg = new JLabel();
        add(bg);
        bg.setBounds(padding,padding,ladoCuadro,ladoCuadro);

        escalarImagen(ladoCuadro, bg);

    }

    public void escalarImagen(int ladoCuadro, JLabel bg) {
        Image img = planeta.getImagen().getImage();
        Image imgScale = img.getScaledInstance(ladoCuadro, ladoCuadro, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        bg.setIcon(scaledIcon);
    }

    public Planeta getPlaneta() {
        return planeta;
    }

}
