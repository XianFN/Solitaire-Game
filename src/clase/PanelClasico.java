package clase;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.LineBorder;


public class PanelClasico extends JFrame {
	public static String barajaDestapar[]=new String[24];
	public static String barajaMesa[][]=new String [7][20];
	public static String barajaAses[][]=new String [4][13];
	public static int contadorBaraja=0;
	private static int contadoClick=0;
	private static int numMovimientos=0;
	private static JButton primerBotonPulsado;
	private static int tipoPrimeraCarta;
	private static int contadorBotonOculto[]=new int [7];
		
		public PanelClasico(JLayeredPane panel) {
			
			try
	        {
			FileWriter fichero = new FileWriter("movimientosClasico.txt");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			panel.setLayout(null);
			//panel.setLayout(new OverlayLayout(panel));
			System.out.println("Clasico");
			
			botones(panel);
			
			
	}

		public static String[] getBarajaDestapar() {
			return barajaDestapar;
		}

		public static String[][] getBarajaMesa() {
			return barajaMesa;
		}

		public static String[][] getBarajaAses() {
			return barajaAses;
		}

		public static int getContadorBaraja() {
			return contadorBaraja;
		}

		public static int getNumMovimientos() {
			return numMovimientos;
		}

		public static int[] getContadorBotonOculto() {
			return contadorBotonOculto;
		}

		public static void setBarajaDestapar(String[] barajaDestapar) {
			PanelClasico.barajaDestapar = barajaDestapar;
		}

		public static void setBarajaMesa(String[][] barajaMesa) {
			PanelClasico.barajaMesa = barajaMesa;
		}

		public static void setBarajaAses(String[][] barajaAses) {
			PanelClasico.barajaAses = barajaAses;
		}

		public static void setContadorBaraja(int contadorBaraja) {
			PanelClasico.contadorBaraja = contadorBaraja;
		}

		public static void setNumMovimientos(int numMovimientos) {
			PanelClasico.numMovimientos = numMovimientos;
		}

		public static void setContadorBotonOculto(int[] contadorBotonOculto) {
			PanelClasico.contadorBotonOculto = contadorBotonOculto;
		}

		public void botones(JLayeredPane panel) {
			ListenerNuevaCarta lisNueCar=new ListenerNuevaCarta();
			ListenerCartaBaraja lisCarBar=new ListenerCartaBaraja();
			ListenerCartaMesa lisCarMes= new ListenerCartaMesa();
			ListenerCartaAses lisCarAse= new ListenerCartaAses();
			
			// BOTONES BARAJA DESCUBIERTA  //
			JButton barajaReves = new JButton();
			barajaReves.setName("barajaReves");
			JButton barajaDescubierta = new JButton();
			barajaDescubierta.setName("b2");
			
			barajaReves.setBounds(50, 20, 91,137);
			barajaDescubierta.setBounds(150, 20, 91,137);
			panel.add(barajaReves);
			panel.add(barajaDescubierta);
			barajaReves.addActionListener(lisNueCar);
			barajaDescubierta.addActionListener(lisCarBar);
			// BOTONES ZONA DE ASES //	
			
			JButton A1 = new JButton();
			JButton A2 = new JButton();
			JButton A3 = new JButton();
			JButton A4 = new JButton();
		
			A1.setBounds(500, 20, 91,137);
			A1.setName("A0");
			System.out.println(A1.getName());
			A2.setBounds(591, 20, 91,137);
			A2.setName("A1");
			A3.setBounds(682, 20, 91,137);
			A3.setName("A2");
			A4.setBounds(773, 20, 91,137);
			A4.setName("A3");
			
			panel.add(A1);
			panel.add(A2);
			panel.add(A3);
			panel.add(A4);
			
			A1.addActionListener(lisCarAse);
			A2.addActionListener(lisCarAse);
			A3.addActionListener(lisCarAse);
			A4.addActionListener(lisCarAse);
			
			//BOTONES ZONA DE CARTAS //
			
			int horizontal=100;
			for (int i = 1; i < 8; i++) {
				
				System.out.println(i);
				int vertical= 170;
				for (int j = 20; j >= 1; j--) {
					String nombreBoton=(i-1)+"_"+(j-1);
				//	String nombreBoton="mesa";
					System.out.println(nombreBoton);
					
					JButton $nombreBoton= new JButton();
					$nombreBoton.setName(nombreBoton);
					if(j>i) {
						$nombreBoton.setVisible(false);
					}
			
					if(j!=i&&j<i) {
						
						$nombreBoton.setEnabled(false);
						$nombreBoton.setOpaque(true);
					}
					if (j<i) {
						
						ImageIcon icon = new ImageIcon(PanelClasico.class.getResource("/OTRASIMAGENES/fondo.jpg"));
						Image scaleImage = icon.getImage().getScaledInstance(91, 137,Image.SCALE_DEFAULT);
						ImageIcon icon2 = new ImageIcon(scaleImage);
						
						$nombreBoton.setIcon(icon2);	
					}
					
					$nombreBoton.setBounds(horizontal+100*i,vertical+j*20,91,139);	
				
			//		String rutaCartaAleatoria=rellenarArray(i,barajaEntera);
			//		$nombreBoton.setIcon(new ImageIcon (rutaCartaAleatoria));
		//			System.out.println(i+"j vale: "+j +"horizontal:  "+horizontal+"       vertical    "+vertical);
					panel.add($nombreBoton,j,-1);
					$nombreBoton.addActionListener(lisCarMes);
				//	setComponentZOrder($nombreBoton, j);
				//	contador=contador+1;
				//	horizontal=horizontal+100;
				
					
				//	$nombreBoton.addActionListener(lisB);
				}
			}
			rellenarArray();
			guardarMovimientos();
			for (int i = 0; i < 24; i++) {
				System.out.println(barajaDestapar[i]+" ");
			}
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 20; j++) {
					System.out.print(barajaMesa[i][j]+" ");
					
				}
				System.out.println();
			}
			cambiarIcon();
			
		}
		public void rellenarArray() {
			String barajaEntera[]= {"AC","2C","3C","4C","5C","6C","7C","8C","9C","TC","JC","QC","KC","AD","2D","3D","4D","5D","6D","7D","8D","9D","TD","JD","QD","KD","AH","2H","3H","4H","5H","6H","7H","8H","9H","TH","JH","QH","KH","AS","2S","3S","4S","5S","6S","7S","8S","9S","TS","JS","QS","KS"};
//			public static String barajaDestapar[]=new String[24];
//			public static String barajaMesa[][]=new String [7][20];
//			public static String barajaAses[][]=new String [4][13];
			
			
			int numero,contadorColumna=0,contadorFila=0;
		
			for (int j = 0; j < 52; j++) {
				
			
			
			do {
				Random random = new Random();
				    numero=random.nextInt(52);
		//	System.out.println(numero+"  aleatorio");	
		
		//	System.out.println(barajaEntera[numero]);
			}while(barajaEntera[numero]==null);
			if (j<24) {
				barajaDestapar[j]=barajaEntera[numero];
			} else {
				if (contadorFila!=contadorColumna) {
					barajaMesa[contadorColumna][contadorFila]=barajaEntera[numero];
					contadorFila++;
				}else {
					barajaMesa[contadorColumna][contadorFila]=barajaEntera[numero];
			
					contadorFila=0;
					contadorColumna++;
					
				}
					
				}
			barajaEntera[numero]=null;
			}
			for (int i = 0; i < 4; i++) {
				for (int i2 = 0; i2 < 13; i2++) {
					barajaAses[i][i2]=null;
				}
			}
			/*
			baraja[i][0]=barajaCompleta[numero];
	 		indices[i]=1;//llenamos los indices con 1
	 		barajaCompleta[numero]=null;
			String rutaSalida="CARTASESPANOLAS/"+baraja[i][0]+".jpg";
			System.out.println(rutaSalida);
			return rutaSalida;
			*/
		}
		public void cambiarIcon() {
//			public static String barajaDestapar[]=new String[24];
//			public static String barajaMesa[][]=new String [7][20];
//			public static String barajaAses[][]=new String [4][13];
	//		System.out.println("El numero total de componentes es: "+Layout.panelClasico.getComponentCount());
			
			JButton asd=(JButton) Layout.panelClasico.getComponent(133);
		//	System.out.println("       "+asd.getName());
			
			// DE 140,141 COMPONENTES SON LOS BOTONES DE LA BARAJA A DESCUBIR //
			if (barajaDestapar[0]==null) {
				
				
			}else {
			//	ImageIcon icon = new ImageIcon("fondo.jpg");
				ImageIcon icon = new ImageIcon(PanelClasico.class.getResource("/OTRASIMAGENES/fondo2.jpg"));
				Image scaleImage = icon.getImage().getScaledInstance(95, 142,Image.SCALE_DEFAULT);
				ImageIcon icon2 = new ImageIcon(scaleImage);
				JButton botonMover=(JButton) Layout.panelClasico.getComponent(140);
				botonMover.setIcon(icon2);
			}
			String cartaDescubierta = ("CARTASFRANCESAS/"+barajaDestapar[contadorBaraja]+".png");
		//	System.out.println(cartaDescubierta);
			JButton botonMover=(JButton) Layout.panelClasico.getComponent(141);
		//	botonMover.setBackground(Color.red);
			botonMover.setOpaque(true);
		//	System.out.println(	botonMover.getName());
			botonMover.setIcon(new ImageIcon (cartaDescubierta));
			// DE (142,145) SON LOS BOTONES DE LOS ASES//
			
			if (barajaAses[0][0]==null) {
				ImageIcon icon = new ImageIcon(PanelClasico.class.getResource("/OTRASIMAGENES/asesfondo.jpg"));
				Image scaleImage = icon.getImage().getScaledInstance(91, 137,Image.SCALE_DEFAULT);
				ImageIcon icon2 = new ImageIcon(scaleImage);
				JButton boton=(JButton) Layout.panelClasico.getComponent(142);
				boton.setIcon(icon2);
				
			}else {
				JButton boton=(JButton) Layout.panelClasico.getComponent(142);
				String cartaMesa = ("/CARTASFRANCESAS/"+barajaAses[0][0]+".png");
		//		System.out.println(cartaMesa);
		
				boton.setOpaque(true);
		//		System.out.println(	boton.getName());
				boton.setIcon(new ImageIcon (PanelClasico.class.getResource(cartaMesa)));
			}
			if (barajaAses[1][0]==null) {
				ImageIcon icon = new ImageIcon(PanelClasico.class.getResource("/OTRASIMAGENES/asesfondo.jpg"));
				Image scaleImage = icon.getImage().getScaledInstance(91, 137,Image.SCALE_DEFAULT);
				ImageIcon icon2 = new ImageIcon(scaleImage);
				JButton boton=(JButton) Layout.panelClasico.getComponent(143);
				boton.setIcon(icon2);
				
			}else {
				JButton boton=(JButton) Layout.panelClasico.getComponent(143);
				String cartaMesa = ("/CARTASFRANCESAS/"+barajaAses[1][0]+".png");
			//	System.out.println(cartaMesa);
		
				boton.setOpaque(true);
		//		System.out.println(	boton.getName());
				boton.setIcon(new ImageIcon (PanelClasico.class.getResource(cartaMesa)));
			}
			if (barajaAses[2][0]==null) {
				ImageIcon icon = new ImageIcon(PanelClasico.class.getResource("/OTRASIMAGENES/asesfondo.jpg"));
				Image scaleImage = icon.getImage().getScaledInstance(91, 137,Image.SCALE_DEFAULT);
				ImageIcon icon2 = new ImageIcon(scaleImage);
				JButton boton=(JButton) Layout.panelClasico.getComponent(144);
				boton.setIcon(icon2);
				
			}else {
				JButton boton=(JButton) Layout.panelClasico.getComponent(144);
				String cartaMesa = ("/CARTASFRANCESAS/"+barajaAses[2][0]+".png");
		//		System.out.println(cartaMesa);
		
				boton.setOpaque(true);
		//		System.out.println(	boton.getName());
				boton.setIcon(new ImageIcon (PanelClasico.class.getResource(cartaMesa)));
			}
			if (barajaAses[3][0]==null) {
				ImageIcon icon = new ImageIcon(PanelClasico.class.getResource("/OTRASIMAGENES/asesfondo.jpg"));
				Image scaleImage = icon.getImage().getScaledInstance(91, 137,Image.SCALE_DEFAULT);
				ImageIcon icon2 = new ImageIcon(scaleImage);
				JButton boton=(JButton) Layout.panelClasico.getComponent(145);
				boton.setIcon(icon2);
				
			}else {
				JButton boton=(JButton) Layout.panelClasico.getComponent(145);
				String cartaMesa = ("/CARTASFRANCESAS/"+barajaAses[3][0]+".png");
			//	System.out.println(cartaMesa);
		
				boton.setOpaque(true);
		//		System.out.println(	boton.getName());
				boton.setIcon(new ImageIcon (PanelClasico.class.getResource(cartaMesa)));
			}

			
			// DE (0,139) SON BOTONES DE LA MESA EN ORDEN DE C1_20,C2_20... ETC SIENDO 139  C7_1//
			for (int i = 0; i < 140; i++) {
				
		
			JButton boton2=(JButton) Layout.panelClasico.getComponent(i);
			if (boton2.isEnabled()&&boton2.isVisible()) {
				int resto= i%7;
				int cociente=i/7;
			//	System.out.println("resto es : "+resto+" y el cociente es:  "+cociente+"i vale"+i);
				if (barajaMesa[resto][19-cociente]==null && cociente==19){
					ImageIcon icon = new ImageIcon(PanelClasico.class.getResource("/OTRASIMAGENES/asesfondo.jpg"));
					Image scaleImage = icon.getImage().getScaledInstance(91, 137,Image.SCALE_DEFAULT);
					ImageIcon icon2 = new ImageIcon(scaleImage);
					
					boton2.setIcon(icon2);
					
				}else {
				String cartaMesa = ("/CARTASFRANCESAS/"+barajaMesa[resto][19-cociente]+".png");
				boton2.setOpaque(true);
				//	System.out.println(	boton2.getName());
						boton2.setIcon(new ImageIcon (PanelClasico.class.getResource(cartaMesa)));
				}
		//		System.out.println(cartaMesa);
			

			} 
			}
						
			//146
			
			
			
			//String MoverTodasLasCartas = ("CARTASESPANOLAS/"+baraja[i][0]+".jpg");
			//JButton botonMover=(JButton) Layout.panelSaltos.getComponent(i);
			//botonMover.setIcon(new ImageIcon (MoverTodasLasCartas));;
			
		}
			

		
		
		
public char numeroSiguiente(String carta) {
	char valor=' ';
	switch(carta.charAt(0)) {
	case 'A':
		valor='2';
		break;
	case '2':
		valor='3';
		break;
	case '3':
		valor='4';
		break;
	case '4':
		valor='5';
		break;
	case '5':
		valor='6';
		break;
	case '6':
		valor='7';
		break;
	case '7':
		valor='8';
		break;
	case '8':
		valor='9';
		break;
	case '9':
		valor='T';
		break;
	case 'T':
		valor='J';
		break;
	case 'J':
		valor='Q';
		break;
	case 'Q':
		valor='K';
		break;
	case 'K':
		
		break;
	
	
	
	
	}
	System.out.println("VALOR : "+valor);
	return valor;
}

public void moverCarta(int tipoMovimiento,JButton botonAMover,JButton botonAMoverse,int posicionCarta1x,int posicionCarta1y,int posicionCarta2x,int posicionCarta2y) {
	/*
	 
	 //VARIABLES GLOBALES
	
	public static String barajaDestapar[]=new String[24];
	public static String barajaMesa[][]=new String [7][20];
	public static String barajaAses[][]=new String [4][13];
	public static int contadorBaraja=0;
	private static int contadoClick=0;
	private static JButton primerBotonPulsado;
	private static int tipoPrimeraCarta;
		
	ENTRADA, TIPO CARTA 0= DE BARAJA A MESA
						1=DE MESA A MESA
						2=DE ASES A MESA
						3=DE BARAJA A ASES
						4=DE MESA A ASES
	
	 */
	numMovimientos++;
	switch(tipoMovimiento) {
	case 0:
		// DE BARAJA A MESA//
		System.out.println("BARAJA DESTAPAR VALE:  ");
		for (int i = 0; i < 24; i++) {
			System.out.print(barajaDestapar[i]);
		}
		if (posicionCarta2y==0&&barajaMesa[posicionCarta2x][0]==null) {
			barajaMesa[posicionCarta2x][posicionCarta2y]=barajaDestapar[posicionCarta1x];
			System.out.println("Mover solo K");
			barajaDestapar[posicionCarta1x]=null;
			moverArrayBarajaDestapar(posicionCarta1x);
			guardarMovimientos();
			cambiarIcon();
			break;
		}else {
			barajaMesa[posicionCarta2x][posicionCarta2y+1]=barajaDestapar[posicionCarta1x];
		}
		
		System.out.println("");
		barajaDestapar[posicionCarta1x]=null;
		moverArrayBarajaDestapar(posicionCarta1x);
		
		try {
			System.out.println("Habilito el siguiente");
		
			int i = ((19-posicionCarta2y)*7+posicionCarta2x-7);//siguiente boton
			JButton boton=(JButton) Layout.panelClasico.getComponent(i);
			System.out.println("i vale:  "+i);
			boton.setVisible(true);
			boton.setEnabled(true);
			
			} catch (Exception e) {
			
			}
		
	
		
		
		System.out.println("BARAJA DESTAPAR VALE:  ");
		for (int j = 0; j < 24; j++) {
			System.out.print(barajaDestapar[j]);
			
		}
		guardarMovimientos();
		cambiarIcon();
		
		break;
	case 1:
		// MOVER CARTA MESA A MESA//
		boolean salida=false;
		int iNuevo = ((19-posicionCarta2y)*7+posicionCarta2x-7);//siguiente boton
		int iAntiguo = ((19-posicionCarta1y)*7+posicionCarta1x);//siguiente boton
		if (iNuevo>125&&posicionCarta2y==0&&barajaMesa[posicionCarta2x][posicionCarta2y]==null) {
			System.out.println("holaaaaaa");
			iNuevo+=7;
		}
		comprobarDescubrirCartaMesa(posicionCarta1x,posicionCarta1y);
		JButton haySiguiente=new JButton();
		if (posicionCarta2y==0&&barajaMesa[posicionCarta2x][posicionCarta2y]==null) {
			posicionCarta2y--;
		}
		do {
			System.out.println("");
			
			barajaMesa[posicionCarta2x][posicionCarta2y+1]=barajaMesa[posicionCarta1x][posicionCarta1y];
			barajaMesa[posicionCarta1x][posicionCarta1y]=null;
		
			
			try {
				System.out.println("Habilito el siguiente");
				
				JButton botonNuevo=(JButton) Layout.panelClasico.getComponent(iNuevo);
				JButton botonAntiguo=(JButton) Layout.panelClasico.getComponent(iAntiguo);
				System.out.println("i vale:  "+iNuevo + " "+iAntiguo);
				
				botonNuevo.setVisible(true);
				botonNuevo.setEnabled(true);
				if (iAntiguo<133) {
					botonAntiguo.setVisible(false);
				}
			
				//comprobarDescubrirCartaMesa(posicionCarta1x,posicionCarta1y);
				iAntiguo-=7;
				iNuevo-=7;
				posicionCarta2y++;
				posicionCarta1y++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			try {
				haySiguiente= (JButton) Layout.panelClasico.getComponent(iAntiguo);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("SALIDAAAAA");
				salida=true;
			}
			
		}while(haySiguiente.isVisible()&&!salida);
		
	
		
	
		
		guardarMovimientos();
		cambiarIcon();
		
		
		break;
	case 2:
								// SE MUEVE ASES A MESA//
		
		
		
		System.out.println("");
		barajaMesa[posicionCarta2x][posicionCarta2y+1]=barajaAses[posicionCarta1x][0];
		barajaAses[posicionCarta1x][0]=null;
		moverArrayAsesReves(posicionCarta1x);
		
		try {
			System.out.println("Habilito el siguiente");
			int i = ((19-posicionCarta2y)*7+posicionCarta2x-7);//siguiente boton
			JButton boton=(JButton) Layout.panelClasico.getComponent(i);
			System.out.println("i vale:  "+i);
			boton.setVisible(true);
			boton.setEnabled(true);
			
			} catch (Exception e) {
			
			}
		
	
		
		
		guardarMovimientos();
		cambiarIcon();
		
		break;
	case 3:
		// DE BARAJA A ASES//
		System.out.println("BARAJA DESTAPAR VALE:  ");
		for (int i = 0; i < 24; i++) {
			System.out.print(barajaDestapar[i]);
		}
		
		moverArrayAses(posicionCarta2x);
		System.out.println("");
		barajaAses[posicionCarta2x][0]=barajaDestapar[posicionCarta1x];
		barajaDestapar[posicionCarta1x]=null;
		moverArrayBarajaDestapar(posicionCarta1x);
		System.out.println("BARAJA DESTAPAR VALE:  ");
		for (int j = 0; j < 24; j++) {
			System.out.print(barajaDestapar[j]);
			
		}
		guardarMovimientos();
		cambiarIcon();
		
		break;
	case 4:
		// DE MESA A ASES//
		for (int i = 0; i < 13; i++) {
			System.out.print(barajaAses[posicionCarta2x][i]);
		}
		
		moverArrayAses(posicionCarta2x);
		System.out.println("");
		barajaAses[posicionCarta2x][0]=barajaMesa[posicionCarta1x][posicionCarta1y];
		
		barajaMesa[posicionCarta1x][posicionCarta1y]=null;
		for (int j = 0; j < 13; j++) {
			System.out.print(barajaAses[posicionCarta2x][j]);
			
		}
		comprobarDescubrirCartaMesa(posicionCarta1x,posicionCarta1y);
		guardarMovimientos();
		cambiarIcon();
		break;
	
	}
	
	
	
	
	
}
public void moverArrayBarajaDestapar(int posicionX) {
	
	for (int i = posicionX; i < 24; i++) {
		try {
		
				barajaDestapar[i]=barajaDestapar[i+1];
				if(barajaDestapar[i]==barajaDestapar[i+1]) {
					
					
					barajaDestapar[i+1]=null;
					
			}
		
		} catch (Exception e) {
			
		}
	}
	if(contadorBaraja==0) {
		
	}else {
	contadorBaraja--;
	}
}
public void moverArrayAses(int posicionX) {
	for (int i = 12; i >= 1; i--) {
		try {
			barajaAses[posicionX][i]=barajaAses[posicionX][i-1];
		} catch (Exception e) {
			
		}
	}
	
	
}
public void moverArrayAsesReves(int posicionX) {
	for (int i = 0; i < 12; i++) {
		try {
			barajaAses[posicionX][i]=barajaAses[posicionX][i+1];
		} catch (Exception e) {
			
		}
	}
	
	
}
public void comprobarDescubrirCartaMesa(int posicionX,int posicionY) {
	//por ejemplo si entra 4,3

	int i = ((19-posicionY)*7+posicionX);
	JButton boton=(JButton) Layout.panelClasico.getComponent(i);
	System.out.println("i vale:  "+i);
	if (i<133) {
		
	
	boton.setVisible(false);
	boton.setEnabled(false);
	}
	JButton botonAnterior;
	try {
		System.out.println("try");
		botonAnterior=(JButton) Layout.panelClasico.getComponent(i+7);
		if (!botonAnterior.isEnabled()) {
			System.out.println("Habilito el anterior");
			botonAnterior.setEnabled(true);
			botonAnterior.setVisible(true);
		}
	} catch (Exception e) {
	
	}

		
	
	
	/*
	JButton boton=(JButton) Layout.panelClasico.getComponent(i);
	if (boton2.isEnabled()&&boton2.isVisible()) {
		int resto= i%7;
		int cociente=i/7;
		System.out.println("resto es : "+resto+" y el cociente es:  "+cociente);
		String cartaMesa = ("CARTASFRANCESAS/"+barajaMesa[resto][19-cociente]+".png");
		System.out.println(cartaMesa);
	
	//	botonMover.setBackground(Color.red);
		boton2.setOpaque(true);
		System.out.println(	boton2.getName());
		boton2.setIcon(new ImageIcon (cartaMesa));
		*/
}
public void bloquearBotones() {
	if (contadorBaraja==23||barajaDestapar[contadorBaraja+1]==null) {
		JButton botonBaraja=(JButton) Layout.panelClasico.getComponent(140);
		botonBaraja.setEnabled(false);
	}else {
		JButton botonBaraja=(JButton) Layout.panelClasico.getComponent(140);
		botonBaraja.setEnabled(true);
	}
	
	for (int i = 0; i < 7; i++) {
		System.out.println(contadorBotonOculto[i]);
		int contador=0;
		for (int j = 0; j < 20; j++) {
			System.out.print(barajaMesa[i][j]);
			int indice = ((19-j)*7+i);
			JButton boton=(JButton) Layout.panelClasico.getComponent(indice);
			if (contador<contadorBotonOculto[i]) {
		
				boton.setEnabled(false);
				boton.setVisible(true);
				ImageIcon icon = new ImageIcon(PanelClasico.class.getResource("/OTRASIMAGENES/fondo.jpg"));
				Image scaleImage = icon.getImage().getScaledInstance(91, 137,Image.SCALE_DEFAULT);
				ImageIcon icon2 = new ImageIcon(scaleImage);
				boton.setIcon(icon2);
				contador++;
			}else {
				
			
				if(barajaMesa[i][j]==null){
					boton.setVisible(false);
					boton.setEnabled(false);
				}else {
					boton.setEnabled(true);
					boton.setVisible(true);
				}
				if(barajaMesa[i][j]==null&&j==0){
					boton.setEnabled(true);
					
				}
			}
			if(barajaMesa[i][j]!=null||j==0){
				boton.setVisible(true);
			}else {
				boton.setVisible(false);
			}
			
		}
		System.out.println();
	}
	
}
public class ListenerNuevaCarta implements ActionListener{
		
		public void actionPerformed (ActionEvent boton) {
			
			if (contadorBaraja==23||barajaDestapar[contadorBaraja+1]==null) {
				JButton boton2 =(JButton) boton.getSource();
				boton2.setEnabled(false);
				cambiarIcon();
				//contadorBaraja++;
			}else {
				
			
			contadorBaraja++;
			
			cambiarIcon();
			}
		}
}
public class ListenerCartaBaraja implements ActionListener{
	
	public void actionPerformed (ActionEvent boton) {
		System.out.println("PULSADO");
			if(contadoClick==0) {
			primerBotonPulsado=(JButton) boton.getSource();
			 primerBotonPulsado.setContentAreaFilled(false);
			 primerBotonPulsado.setFocusPainted(false);
			 primerBotonPulsado.setBorder(new LineBorder(Color.BLUE,4));
			contadoClick++;
			tipoPrimeraCarta=0;
		}else {
			primerBotonPulsado.setBorder(BorderFactory.createEmptyBorder());

			JButton segundoBotonPulsado=(JButton) boton.getSource();
			int tipoSegundaCarta=0;

			
			switch(tipoPrimeraCarta) {
			
				case 0:
					System.out.println("No puedes volver a pulsar la baraja");
					break;
					
				case 1:
					System.out.println("No se puede mover a la baraja");
					/*
					String carta1=primerBotonPulsado.getName();
					String carta2=segundoBotonPulsado.getName();
					System.out.println("carta1= "+carta1+"carta2= "+carta2);
					Integer posicionx = Integer.valueOf(carta1.substring(0));				
					Integer posiciony = Integer.valueOf(carta1.substring(2,carta2.length()));
					char siguienteValor=numeroSiguiente(carta1);
					System.out.println(posicionx+" "+ posiciony );
					if (barajaDestapar[contadorBaraja].charAt(0)=='K') {
						if (barajaMesa[posicionx][posiciony]==null) {
							System.out.println("Se mueve");
						} else {
							System.out.println("No se puede mover");

						}
						
						
					} else if (barajaDestapar[contadorBaraja].charAt(1)=='D'||barajaDestapar[contadorBaraja].charAt(1)=='H') {
						if ((barajaMesa[posicionx][posiciony].charAt(1)=='C'||barajaMesa[posicionx][posiciony].charAt(1)=='S')&&(barajaDestapar[contadorBaraja].charAt(0)== siguienteValor)) {
							System.out.println("Se mueve2");
						}else System.out.println("No se puede mover");
					}else if ((barajaMesa[posicionx][posiciony].charAt(1)=='D'||barajaMesa[posicionx][posiciony].charAt(1)=='H')&&(barajaDestapar[contadorBaraja].charAt(0)== siguienteValor)) {
						
						System.out.println("Se mueve 3");
					}
					System.out.println("No se puede mover");
					*/
					break;
					
				case 2:
					System.out.println("No se puede mover a la baraja");
					break;
				
				
			
			}
			contadoClick=0;
		}
	}
}
public class ListenerCartaMesa implements ActionListener{
	
	public void actionPerformed (ActionEvent boton) {
		System.out.println("PULSADO");
		if(contadoClick==0) {
		primerBotonPulsado=(JButton) boton.getSource();
		 primerBotonPulsado.setContentAreaFilled(false);
		 primerBotonPulsado.setFocusPainted(false);
		 primerBotonPulsado.setBorder(new LineBorder(Color.BLUE,4));
		contadoClick++;
		tipoPrimeraCarta=1;
	}else {
		primerBotonPulsado.setBorder(BorderFactory.createEmptyBorder());

		JButton segundoBotonPulsado=(JButton) boton.getSource();
		int tipoSegundaCarta=1;
		String carta1,carta2;
		Integer posicionx,posiciony,posicion2x,posicion2y;
		char siguienteValor,valorCartaDondeSeMueve;
		
		switch(tipoPrimeraCarta) {
		
			case 0:
				//SE MUEVE DE LA BARAJA A LA MESA//
				carta1=primerBotonPulsado.getName();
				 carta2=segundoBotonPulsado.getName();
				System.out.println("carta1= "+carta1+"carta2= "+carta2+"los subsctirngs son:  "+carta2.substring(0,1)+"   "+carta2.substring(2,carta2.length()));

				 posicionx = Integer.valueOf(carta2.substring(0,1));				
				 posiciony = Integer.valueOf(carta2.substring(2,carta2.length()));
				//siguienteValor=numeroSiguiente(barajaDestapar[contadorBaraja]);
				 //valorCartaDondeSeMueve =barajaMesa[posicionx][posiciony].charAt(0);
			//	System.out.println(posicionx+" "+ posiciony+" y su valor es: "+barajaMesa[posicionx][posiciony] );
		//		System.out.println("El valor es: "+barajaDestapar[contadorBaraja].charAt(1)+"  "+barajaMesa[posicionx][posiciony].charAt(1)+" numero  "+barajaMesa[posicionx][posiciony].charAt(0));
				if (barajaDestapar[contadorBaraja].charAt(0)=='K') {
					if (barajaMesa[posicionx][posiciony]==null) {
						System.out.println("Se mueve");
						moverCarta(0,primerBotonPulsado,segundoBotonPulsado,contadorBaraja, 0, posicionx, posiciony);
					} else {
						System.out.println("No se puede mover 1");

					}
					
					
				} else {
					if (barajaMesa[posicionx][posiciony]==null) {
						System.out.println("SOLO PUEDEN IR REYES");
						break;
					}
					siguienteValor=numeroSiguiente(barajaDestapar[contadorBaraja]);
					 valorCartaDondeSeMueve =barajaMesa[posicionx][posiciony].charAt(0);
				
					
					if (barajaDestapar[contadorBaraja].charAt(1)=='D'||barajaDestapar[contadorBaraja].charAt(1)=='H') {
					System.out.println(" una vale :"+siguienteValor+"y otra :" + valorCartaDondeSeMueve +"   " );
					System.out.println(siguienteValor==valorCartaDondeSeMueve);
					if ((barajaMesa[posicionx][posiciony].charAt(1)=='C'||barajaMesa[posicionx][posiciony].charAt(1)=='S')&&(siguienteValor==valorCartaDondeSeMueve)) {
						System.out.println("Se mueve2");
						moverCarta(0,primerBotonPulsado,segundoBotonPulsado,contadorBaraja, 0, posicionx, posiciony);
					}else System.out.println("No se puede mover");
				}else if ((barajaMesa[posicionx][posiciony].charAt(1)=='D'||barajaMesa[posicionx][posiciony].charAt(1)=='H')&&(siguienteValor==valorCartaDondeSeMueve)) {
					
					System.out.println("Se mueve 3");
					moverCarta(0,primerBotonPulsado,segundoBotonPulsado,contadorBaraja, 0, posicionx, posiciony);
				}else System.out.println("No se puede mover 2");
				}
				break;
				
			case 1:
				//SE MUEVE UNA DE LA MESA A LA MESA//
				 carta1=primerBotonPulsado.getName();
				 carta2=segundoBotonPulsado.getName();
				// System.out.println(carta1.substring(0,1)+"   "+carta1.substring(2,carta1.length()));
			//	System.out.println("carta1= "+carta1+"carta2= "+carta2+"los subsctirngs son:  "+carta2.substring(0,1)+"   "+carta2.substring(2,carta2.length())+" "+carta1.substring(0,1)+"   "+carta1.substring(2,carta1.length()));
				
				 posicionx = Integer.valueOf(carta2.substring(0,1));				
				 posiciony = Integer.valueOf(carta2.substring(2,carta2.length()));
				 posicion2x = Integer.valueOf(carta1.substring(0,1));				
				 posicion2y = Integer.valueOf(carta1.substring(2,carta1.length()));
				 if (posicionx==posicion2x||barajaMesa[posicion2x][posicion2y]==null) {
					System.out.println("No puedes mover en el mismo montón");
					break;
				}
		
			//	System.out.println(posicionx+" "+ posiciony+" y su valor es: "+barajaMesa[posicionx][posiciony] );
		//		System.out.println("El valor es: "+barajaMesa[posicion2x][posicion2y].charAt(1)+"  "+barajaMesa[posicionx][posiciony].charAt(1)+" numero  "+barajaMesa[posicionx][posiciony].charAt(0));
				if (barajaMesa[posicionx][posiciony]==null) {
					if (barajaMesa[posicion2x][posicion2y].charAt(0)=='K') {
						System.out.println("Se mueve");
						moverCarta(1,primerBotonPulsado,segundoBotonPulsado,posicion2x, posicion2y, posicionx, posiciony);

					} else {
						System.out.println("No se puede mover 1");
						break;

					}
					
					
				} else {
					 siguienteValor=numeroSiguiente(barajaMesa[posicion2x][posicion2y]);
					 valorCartaDondeSeMueve =barajaMesa[posicionx][posiciony].charAt(0);
				
					if (barajaMesa[posicion2x][posicion2y].charAt(1)=='D'||barajaMesa[posicion2x][posicion2y].charAt(1)=='H') {
					System.out.println(" una vale :"+siguienteValor+"y otra :" + valorCartaDondeSeMueve +"   " );
					System.out.println(siguienteValor==valorCartaDondeSeMueve);
					if ((barajaMesa[posicionx][posiciony].charAt(1)=='C'||barajaMesa[posicionx][posiciony].charAt(1)=='S')&&(siguienteValor==valorCartaDondeSeMueve)) {
						System.out.println("Se mueve2");
						moverCarta(1,primerBotonPulsado,segundoBotonPulsado,posicion2x, posicion2y, posicionx, posiciony);

					}else System.out.println("No se puede mover");
				}else if ((barajaMesa[posicionx][posiciony].charAt(1)=='D'||barajaMesa[posicionx][posiciony].charAt(1)=='H')&&(siguienteValor==valorCartaDondeSeMueve)) {
					
					System.out.println("Se mueve 3");
					moverCarta(1,primerBotonPulsado,segundoBotonPulsado,posicion2x, posicion2y, posicionx, posiciony);

				}else System.out.println("No se puede mover 2");
				}
				break;
			
				
			case 2:
				//SE MUEVE DE LA ZONA DE ASES A LA MESA// 
				 carta1=primerBotonPulsado.getName();
				 carta2=segundoBotonPulsado.getName();
				
				System.out.println("carta1= "+carta1+"carta2= "+carta2+"los subsctirngs son:  "+carta2.substring(0,1)+"   "+carta2.substring(2,carta2.length()));

				 posicionx = Integer.valueOf(carta2.substring(0,1));				
				 posiciony = Integer.valueOf(carta2.substring(2,carta2.length()));
				 posicion2x = Integer.valueOf(carta1.substring(1,carta1.length()));
				 posicion2y=0;
				 if (barajaAses[posicion2x][posicion2y]==null) {
					 System.out.println("No hay ningun valor en Ases");
						break;
					}
				 siguienteValor=numeroSiguiente(barajaAses[posicion2x][posicion2y]);
				 valorCartaDondeSeMueve =barajaMesa[posicionx][posiciony].charAt(0);
				System.out.println(posicionx+" "+ posiciony+" y su valor es: "+barajaMesa[posicionx][posiciony] );
				System.out.println("El valor es: "+barajaAses[posicion2x][posicion2y].charAt(1)+"  "+barajaMesa[posicionx][posiciony].charAt(1)+" numero  "+barajaMesa[posicionx][posiciony].charAt(0));
				if (barajaAses[posicion2x][posicion2y].charAt(0)=='K') {
					if (barajaMesa[posicionx][posiciony]==null) {
						System.out.println("Se mueve");
						moverCarta(2,primerBotonPulsado,segundoBotonPulsado,posicion2x, 0, posicionx, posiciony);

					} else {
						System.out.println("No se puede mover 1");

					}
					
					
				} else if (barajaAses[posicion2x][posicion2y].charAt(1)=='D'||barajaAses[posicion2x][posicion2y].charAt(1)=='H') {
					System.out.println(" una vale :"+siguienteValor+"y otra :" + valorCartaDondeSeMueve +"   " );
					System.out.println(siguienteValor==valorCartaDondeSeMueve);
					if ((barajaMesa[posicionx][posiciony].charAt(1)=='C'||barajaMesa[posicionx][posiciony].charAt(1)=='S')&&(siguienteValor==valorCartaDondeSeMueve)) {
						System.out.println("Se mueve2");
						moverCarta(2,primerBotonPulsado,segundoBotonPulsado,posicion2x, 0, posicionx, posiciony);
					}else System.out.println("No se puede mover");
				}else if ((barajaMesa[posicionx][posiciony].charAt(1)=='D'||barajaMesa[posicionx][posiciony].charAt(1)=='H')&&(siguienteValor==valorCartaDondeSeMueve)) {
					
					System.out.println("Se mueve 3");
					moverCarta(2,primerBotonPulsado,segundoBotonPulsado,posicion2x, 0, posicionx, posiciony);
				}else System.out.println("No se puede mover 2");
				
				break;
			
			
		
		}
		contadoClick=0;
	}
}
}
public class ListenerCartaAses implements ActionListener{
	
	public void actionPerformed (ActionEvent boton) {
		System.out.println("PULSADO");
		if(contadoClick==0) {
		primerBotonPulsado=(JButton) boton.getSource();
		 primerBotonPulsado.setContentAreaFilled(false);
		 primerBotonPulsado.setFocusPainted(false);
		 primerBotonPulsado.setBorder(new LineBorder(Color.BLUE,4));
		contadoClick++;
		tipoPrimeraCarta=2;
	}else {
		primerBotonPulsado.setBorder(BorderFactory.createEmptyBorder());

		JButton segundoBotonPulsado=(JButton) boton.getSource();
		int tipoSegundaCarta=2;
		String carta1,carta2;
		Integer posicionx,posiciony,posicion2x,posicion2y;
		char siguienteValor=0,valorCartaDondeSeMueve;
		

		
		switch(tipoPrimeraCarta) {
		
			case 0:
				//SE MUEVE DE LA BARAJA A LA ZONA DE ASES//
				carta1=primerBotonPulsado.getName();
				 carta2=segundoBotonPulsado.getName();
			//	System.out.println("carta1= "+carta1+"carta2= "+carta2+"los subsctirngs son:  "+carta2.substring(0,1)+"   "+carta2.substring(2,carta2.length()));

				posicionx = Integer.valueOf(carta2.substring(1,carta2.length()));
				posiciony=0;
				 if (barajaAses[posicionx][0]!=null) {
					 siguienteValor=numeroSiguiente(barajaAses[posicionx][posiciony]);
				}else if (barajaDestapar[contadorBaraja].charAt(0)!='A') {
					System.out.println("NO SE PUEDE MOVER, SOLO PERMITIDO EMPEZAR CON ASES");
					break;
				}
				
				 valorCartaDondeSeMueve =barajaDestapar[contadorBaraja].charAt(0);
				System.out.println(posicionx+" "+ posiciony+" y su valor es: "+barajaAses[posicionx][posiciony] );
				//System.out.println("El valor es: "+barajaDestapar[contadorBaraja].charAt(1)+"  "+barajaMesa[posicionx][posiciony].charAt(1)+" numero  "+barajaMesa[posicionx][posiciony].charAt(0));
				if (barajaDestapar[contadorBaraja].charAt(0)=='A') {
					if (barajaAses[posicionx][posiciony]==null) {
						System.out.println("Se mueve");
						moverCarta(3,primerBotonPulsado,segundoBotonPulsado,contadorBaraja, 0, posicionx, posiciony);
					} else {
						System.out.println("No se puede mover 1");

					}
					
					
				} else if (barajaDestapar[contadorBaraja].charAt(1)==barajaAses[posicionx][posiciony].charAt(1)&&siguienteValor==valorCartaDondeSeMueve) {
					System.out.println(" una vale :"+siguienteValor+"y otra :" + valorCartaDondeSeMueve +"   " );
					System.out.println(siguienteValor==valorCartaDondeSeMueve);
					moverCarta(3,primerBotonPulsado,segundoBotonPulsado,contadorBaraja, 0, posicionx, posiciony);
				}else System.out.println("No se puede mover 2");
				
				
				break;
				
			case 1:
				//SE MUEVE UNA CARTA DE LA MESA A LA ZONA DE ASES//
				
				 carta1=primerBotonPulsado.getName();
				 carta2=segundoBotonPulsado.getName();
			

				 posicion2x = Integer.valueOf(carta1.substring(0,1));				
				 posicion2y = Integer.valueOf(carta1.substring(2,carta1.length()));
				 posicionx = Integer.valueOf(carta2.substring(1,carta2.length()));
				 posiciony=0;
				 if (barajaAses[posicionx][posiciony]!=null) {
					 siguienteValor=numeroSiguiente(barajaAses[posicionx][posiciony]);
				}else if (barajaMesa[posicion2x][posicion2y].charAt(0)!='A') {
					System.out.println("NO SE PUEDE MOVER, SOLO PERMITIDO EMPEZAR CON ASES");
					break;
				}
				
				 valorCartaDondeSeMueve =barajaMesa[posicion2x][posicion2y].charAt(0);
				System.out.println(posicionx+" "+ posiciony+" y su valor es: "+barajaMesa[posicionx][posiciony] );
				//System.out.println("El valor es: "+barajaAses[posicion2x][posicion2y].charAt(1)+"  "+barajaMesa[posicionx][posiciony].charAt(1)+" numero  "+barajaMesa[posicionx][posiciony].charAt(0));
				if (barajaMesa[posicion2x][posicion2y].charAt(0)=='A') {
					if (barajaAses[posicionx][posiciony]==null) {
						System.out.println("Se mueve");
						moverCarta(4,primerBotonPulsado,segundoBotonPulsado,posicion2x,posicion2y, posicionx, posiciony);
					} else {
						System.out.println("No se puede mover 1");

					}
					
					
				} else if (barajaMesa[posicion2x][posicion2y].charAt(1)==barajaAses[posicionx][posiciony].charAt(1)&&siguienteValor==valorCartaDondeSeMueve) {
					System.out.println(" una vale :"+siguienteValor+"y otra :" + valorCartaDondeSeMueve +"   " );
					
					System.out.println(siguienteValor==valorCartaDondeSeMueve);
					moverCarta(4,primerBotonPulsado,segundoBotonPulsado,posicion2x,posicion2y, posicionx, posiciony);
				
				}else System.out.println("No se puede mover 2");
				
				
				break;
			case 2:
				System.out.println("No puedes mover las cartas en la zona de los ASES");
				break;
			
			
		
		}
		contadoClick=0;
	}
}
}

public static void salvar(String ruta) {
	 FileWriter archivo = null;
	 PrintWriter pw=null;
	  try{
			String formato=ruta.substring(ruta.length()-4,ruta.length());
			System.out.println("FORMATO   :"+formato);
          	if (!formato.equals(".txt")) {
				ruta=ruta+".txt";
			}
		    archivo = new FileWriter(ruta);
            pw = new PrintWriter(archivo);
         
            //LINEA 1 IMPRIMIR NOMBRE//
            pw.println("Solitario clásico");
            //LINEA 2 CARTAS EN LA BARAJA SIN DESTAPAR//
            for (int i = contadorBaraja; i < 20; i++) {
				if (barajaDestapar[i]!=null) {
					pw.print(barajaDestapar[i]);
				}
			}
            
           pw.println();
           //LINEA 3 CARTAS BARAJA DESTAPADAS//
            for (int i = 0; i < contadorBaraja; i++) {
				if (barajaDestapar[i]!=null) {
					pw.print(barajaDestapar[i]);
				}
			}
           pw.println();
           
          //LINEA 4-10 CADA MONTON SEPARADO POR UN * LAS VISIBLES Y NO//
           for (int i = 0; i < 7; i++) {
        	    boolean asterisco=true, flag=true;
				for (int j = 0; j < 20; j++) {
				
					int indice = ((19-j)*7+i);
					JButton boton=(JButton) Layout.panelClasico.getComponent(indice);
					if(!boton.isEnabled()) {
						asterisco=false;
					}else {
						asterisco=true;
					}
					if (asterisco&&flag) {
						flag=false;
						pw.print("*");
					}
					if (barajaMesa[i][j]!=null) {
						pw.print(barajaMesa[i][j]);
					}
					
				}
				pw.println();
          }
           //LINEAS 11-14 ZONA DE LOS ASES//
           for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				if (barajaAses[i][j]!=null) {
					pw.print(barajaAses[i][j]);
				}
			
			}
			pw.println();
		}
   
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != archivo)
              archivo.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
}
	
	
public static void guardarMovimientos() {
	
	FileWriter fichero=null;
    PrintWriter pw=null;
    
    
    FileWriter EscitorCopia=null;
    PrintWriter wNuevo=null;
    File archivo = null;
    FileReader LectorOriginal = null;
    BufferedReader rNuevo = null;
    
    archivo = new File ("movimientosClasico.txt");
        try {
        	
	        if (numMovimientos==0) {
				archivo.delete();
			}else {
				System.out.println("AUXILIAR");
    	EscitorCopia = new FileWriter("auxiliar2.txt");
    	wNuevo = new PrintWriter(EscitorCopia);
    	LectorOriginal = new FileReader (archivo);
         rNuevo = new BufferedReader(LectorOriginal);
    	
         String linea;
         String limitador= "#"+numMovimientos;
     
         try {
        	 wNuevo.println("AUXILIAR");
             if(archivo.length()>0) {
            	 System.out.println(limitador);
            while((linea=rNuevo.readLine())!=null) {
            	if (linea.equals(limitador)) {
					System.out.println("LIMITADOR");
            		break;
				}
            	wNuevo.println(linea);
            	
            }   	  
          
 	        } 
             LectorOriginal.close();
             EscitorCopia.close();
             
   
             
        
             try {
             System.out.println("borrado");
        
             
             Path yourFile = Paths.get("auxiliar2.txt");
             Files.move(yourFile, yourFile.resolveSibling("movimientosClasico.txt"),REPLACE_EXISTING);
             }catch (Exception e) {
				System.out.println("No se pudo borrar");
			}
             rNuevo.close();
             wNuevo.close();
             

    	
         } catch (Exception e) {
 	            e.printStackTrace();
         }
			}
    } catch (Exception e) {
        e.printStackTrace();
    } 
        
    
    
    try{
		 
    	fichero = new FileWriter("movimientosClasico.txt",true);
        pw = new PrintWriter(fichero);
     
        pw.println("#"+numMovimientos);
	    
      
     
        //LINEA 1 IMPRIMIR NOMBRE//
  
        //LINEA 2 CARTAS EN LA BARAJA SIN DESTAPAR//
        for (int i = contadorBaraja; i < 20; i++) {
			if (barajaDestapar[i]!=null) {
				pw.print(barajaDestapar[i]);
			}
		}
        
       pw.println();
       //LINEA 3 CARTAS BARAJA DESTAPADAS//
        for (int i = 0; i < contadorBaraja; i++) {
			if (barajaDestapar[i]!=null) {
				pw.print(barajaDestapar[i]);
			}
		}
       pw.println();
       
      //LINEA 4-10 CADA MONTON SEPARADO POR UN * LAS VISIBLES Y NO//
       for (int i = 0; i < 7; i++) {
    	    boolean asterisco=true, flag=true;
			for (int j = 0; j < 20; j++) {
			
				int indice = ((19-j)*7+i);
				JButton boton=(JButton) Layout.panelClasico.getComponent(indice);
				if(!boton.isEnabled()) {
					asterisco=false;
				}else {
					asterisco=true;
				}
				if (asterisco&&flag) {
					flag=false;
					pw.print("*");
				}
				if (barajaMesa[i][j]!=null) {
					pw.print(barajaMesa[i][j]);
				}
				
			}
			pw.println();
      }
       //LINEAS 11-14 ZONA DE LOS ASES//
       for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 13; j++) {
			if (barajaAses[i][j]!=null) {
				pw.print(barajaAses[i][j]);
			}
		
		}
		pw.println();
	}

        pw.close();

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
       try {
       // Nuevamente aprovechamos el finally para 
       // asegurarnos que se cierra el fichero.
       if (null != fichero)
          fichero.close();
       } catch (Exception e2) {
          e2.printStackTrace();
       }
    }
}


	
}