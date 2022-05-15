package com.alejandrov.frontend.componentes;

import com.alejandrov.frontend.planetas.Planeta;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Cuadro extends JLabel {

    private Planeta planeta;

    public Cuadro() {
    }

    public boolean tienePlaneta() {
        return (planeta.equals(null));
    }

    public void setPlaneta(Planeta planeta) {
        this.planeta = planeta;
    }

    public void setIcon() {
        int ladoCuadro = getPreferredSize().width;
        int padding = (int) Math.floor(ladoCuadro * 0.08);
        ladoCuadro -= padding * 2;

        Border margin = BorderFactory.createEmptyBorder(padding, padding, padding, padding);
        Border borde = BorderFactory.createCompoundBorder(getBorder(),margin);
        setBorder(borde);

        Image img = planeta.getImagen().getImage();
        Image imgScale = img.getScaledInstance(ladoCuadro, ladoCuadro, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        setIcon(scaledIcon);
        setText("Hola");
    }
}
