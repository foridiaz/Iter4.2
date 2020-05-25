package uniandes.isis2304.parranderos.interfazApp;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.sun.source.doctree.StartElementTree;

public class VentanaChecks extends JDialog implements ActionListener{
	private JCheckBox check1,check2,check3,check4,check5,check6,check7,check8,check9,check10,check11,check12,check13,check14,check15,check16,check17,check18,check19;
	private JButton boton;
	private ArrayList<String> cad;
	private InterfazParranderosApp interfaz;
	public VentanaChecks(InterfazParranderosApp pInterfaz) {
		interfaz=pInterfaz;	
		setSize(400, 400);
		setLayout(new BorderLayout());
		cad=new ArrayList<String>();
		JPanel central=new JPanel();
		central.setLayout(new GridLayout(10,2));
		check1=new JCheckBox();
		check1.setText("bañera");
		central.add(check1);
		check2=new JCheckBox();
		check2.setText("yacuzzi");
		central.add(check2);
		check3=new JCheckBox();
		check3.setText("sala");
		central.add(check3);
		check4=new JCheckBox();
		check4.setText("cocineta");
		check5=new JCheckBox();
		check5.setText("restaurante");
		check6=new JCheckBox();
		check6.setText("piscina");
		check7=new JCheckBox();
		check7.setText("WiFi");
		check8=new JCheckBox();
		check8.setText("TV cable");
		check9=new JCheckBox();
		check9.setText("recepción 24h");
		check10=new JCheckBox();
		check10.setText("comidas");
		check11=new JCheckBox();
		check11.setText("acceso a cocina");
		check12=new JCheckBox();
		check12.setText("baño privado");
		check13=new JCheckBox();
		check13.setText("baño compartido");
		check14=new JCheckBox();
		check14.setText("Hab individual");
		check15=new JCheckBox();
		check15.setText("Hab Compartida");
		check16=new JCheckBox();
		check16.setText("Servicios Públicos");
		check17=new JCheckBox();
		check17.setText("Administración");
		check18=new JCheckBox();
		check18.setText("menaje");
		check19=new JCheckBox();
		check19.setText("amoblamiento básico");
		central.add(check4);
		central.add(check5);
		central.add(check6);
		central.add(check7);
		central.add(check8);
		central.add(check9);
		central.add(check10);
		central.add(check11);
		central.add(check12);
		central.add(check13);
		central.add(check14);
		central.add(check15);
		central.add(check16);
		central.add(check17);
		central.add(check18);
		central.add(check19);
		add(central,BorderLayout.CENTER);
		boton=new JButton();
		boton.setText("Aceptar");
		boton.addActionListener(this);
		add(boton,BorderLayout.SOUTH);
		setModal(true);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==boton) {
			valideSeleccion();
			interfaz.setCaracteristicas(cad);
			this.dispose();
		}
	}
	private void valideSeleccion() {
		if (check1.isSelected()) {
			this.cad.add(check1.getText());
		}
		if (check2.isSelected()) {
			this.cad.add(check2.getText());
		}
		if (check3.isSelected()) {
			this.cad.add(check3.getText());
		}
		if (check4.isSelected()) {
			this.cad.add(check4.getText());
		}
		if (check5.isSelected()) {
			this.cad.add(check5.getText());
		}
		if (check6.isSelected()) {
			this.cad.add(check6.getText());
		}
		if (check7.isSelected()) {
			this.cad.add(check7.getText());
		}
		if (check8.isSelected()) {
			this.cad.add(check8.getText());
		}
		if (check9.isSelected()) {
			this.cad.add(check9.getText());
		}
		if (check10.isSelected()) {
			this.cad.add(check10.getText());
		}
		if (check11.isSelected()) {
			this.cad.add(check11.getText());
		}
		if (check12.isSelected()) {
			this.cad.add(check12.getText());
		}
		if (check13.isSelected()) {
			this.cad.add(check13.getText());
		}
		if (check14.isSelected()) {
			this.cad.add(check14.getText());
		}
		if (check15.isSelected()) {
			this.cad.add(check15.getText());
		}
		if (check16.isSelected()) {
			this.cad.add(check16.getText());
		}
		if (check17.isSelected()) {
			this.cad.add(check17.getText());
		}
		if (check18.isSelected()) {
			this.cad.add(check18.getText());
		}
		if (check19.isSelected()) {
			this.cad.add(check19.getText());
		}
	}
}