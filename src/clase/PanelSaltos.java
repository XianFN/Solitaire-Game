package clase;
import java.awt.Color;
import java.awt.Component;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import clase.Layout.EscuchadorClasico;
import clase.Layout.EscuchadorSaltos;

import java.awt.BorderLayout;
import java.util.Random;

public class PanelSaltos extends JFrame{
	private static String baraja[][]=new String[40][40];
	private static int indices[]= new int[40];
	private static int contadoClick=0;
	private static String posicionPrimeraCarta;
	private static int numCartasActuales=39;
	private static int numMovimientos=0;
	private static JButton primerBotonPulsado;

	public PanelSaltos(JPanel panel) {
	
		panel.setLayout(null);
		//JLabel label1 = new JLabel("Saltos");
	//	label1.setBounds(40,600,100,100);
		
		botones(panel);
		System.out.println("holaaa");
		
	//	panel.add(label1);
		
}
	public PanelSaltos(String[][]baraja, int[] indices, int numCartasActuales,int numMovimientos) {
		this.baraja=baraja;
		this.indices=indices;
		this.numCartasActuales=numCartasActuales;
		this.numMovimientos=numMovimientos;
		
	}
	
	
	public  String[][] getBaraja() {
		return baraja;
	}
	public  int[] getIndices() {
		return indices;
	}
	public  int getNumCartasActuales() {
		return numCartasActuales;
	}
	public  int getNumMovimientos() {
		return numMovimientos;
	}
	public  void setBaraja(String[][] baraja) {
		this.baraja = baraja;
		
	}
	public  void setIndices(int[] indices) {
		this.indices = indices;
	}
	public  void setNumCartasActuales(int numCartasActuales) {	
		this.numCartasActuales = numCartasActuales;
	}
	public  void setNumMovimientos(int numMovimientos) {
		this.numMovimientos = numMovimientos;
	}
	
	
	
	public void botones(JPanel panel) {
		 ListenerBotones lisB=new ListenerBotones();
	 String barajaEntera[]= {"AO","2O","3O","4O","5O","6O","7O","SO","CO","RO","AC","2C","3C","4C","5C","6C","7C","SC","CC","RC","AE","2E","3E","4E","5E","6E","7E","SE","CE","RE","AB","2B","3B","4B","5B","6B","7B","SB","CB","RB"};

		int vertical=20;
		int contador=0;
		int horizontal=15;
		
		
		//JButton dia=new JButton();
	//	panel.add(dia);
		for (int i = 0; i <= 39; i++) {
						
				String nombreBoton="b"+Integer.toString(i);
				System.out.println(nombreBoton);
		
				 JButton $nombreBoton= new JButton();
				$nombreBoton.setName(nombreBoton);

				if (contador==10) {
					contador =0;
					vertical=vertical+142;
					horizontal=15;
					
				}
				$nombreBoton.setBounds(horizontal,vertical,87,133);	
			
				String rutaCartaAleatoria=rellenarArray(i,barajaEntera);
				System.out.println(getClass());
				try {
					$nombreBoton.setIcon(new ImageIcon (getClass().getResource(rutaCartaAleatoria)));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				System.out.println(contador+"      "+horizontal+"       vertical    "+vertical);
				panel.add($nombreBoton);
				contador=contador+1;
				horizontal=horizontal+100;
			
				
				$nombreBoton.addActionListener(lisB);
				
		}
		guardarMovimientos();
		
	}


	public static String rellenarArray(int i,String barajaCompleta[]) {
	
		int numero;
		do {
			Random random = new Random();
			    numero=random.nextInt(40);
		System.out.println(numero+"  aleatorio");	
	
		System.out.println(barajaCompleta[numero]);
		}while(barajaCompleta[numero]==null);
		baraja[i][0]=barajaCompleta[numero];
 		indices[i]=1;//llenamos los indices con 1
 		barajaCompleta[numero]=null;
		String rutaSalida="/CARTASESPANOLAS/"+baraja[i][0]+".jpg";
		System.out.println(rutaSalida);
		return rutaSalida;
	}

	public class ListenerBotones implements ActionListener{
		
		public void actionPerformed (ActionEvent boton) {
			if(contadoClick==0) {
		//		System.out.println(((JComponent) boton.getSource()).getName());
				 posicionPrimeraCarta=(((JComponent) boton.getSource()).getName());
				 primerBotonPulsado=(JButton) boton.getSource();
				 primerBotonPulsado.setContentAreaFilled(false);
				 primerBotonPulsado.setFocusPainted(false);
				 primerBotonPulsado.setBorder(new LineBorder(Color.BLUE,4));
				 contadoClick=contadoClick+1;
				 
		}else {
		//	System.out.println(((JComponent) boton.getSource()).getName());
			primerBotonPulsado.setBorder(BorderFactory.createEmptyBorder());
			String posicionSegundaCarta=(((JComponent) boton.getSource()).getName());
			comprobarSalto(posicionPrimeraCarta,posicionSegundaCarta);
			contadoClick=0;
		}
			
			
		}
			
		
		
	}
	public void comprobarSalto(String carta1, String carta2) {
		System.out.println("boto pulsado, la carta : "+carta1+ " y la carta : "+carta2);
		int salida;

		System.out.println("El numero total de componentes es: "+Layout.panelSaltos.getComponentCount());
		Integer posicion1 = Integer.valueOf(carta1.substring(1,carta1.length()));
		Integer posicion2 = Integer.valueOf(carta2.substring(1,carta2.length()));
		System.out.println(posicion1+" "+ posicion2+" Resta: "+(posicion1-posicion2) );

		 if (posicion1-posicion2==3) {
			 System.out.println("vale 3");
			 if(baraja[posicion1][0].charAt(0)==baraja[posicion2][0].charAt(0)||baraja[posicion1][0].charAt(1)==baraja[posicion2][0].charAt(1)) {
				 salida= moverCarta(posicion1,posicion2,carta1,carta2,3,numCartasActuales);
				 System.out.println("SE MUEVE LA CARTA "+carta1+" POR LA CARTA "+carta2);
				 if (salida==0) {//cuando la salida es 0 significa que se acaba de eliminar una columna
						numCartasActuales--;//reducimos uno al contador de cuantas columnas de cartas quedan
					}
				 System.out.println("No coincide la carta, es erroneo");
			 }
		 }else if(posicion1-posicion2==1) {
				 System.out.println("vale 1");
				 if(baraja[posicion1][0].charAt(0)==baraja[posicion2][0].charAt(0)||baraja[posicion1][0].charAt(1)==baraja[posicion2][0].charAt(1)) {
					 System.out.println("SE MUEVE LA CARTA "+carta1+" POR LA CARTA "+carta2);
					 salida=moverCarta(posicion1,posicion2,carta1,carta2,1,numCartasActuales);
					 if (salida==0) {//cuando la salida es 0 significa que se acaba de eliminar una columna
							numCartasActuales--;//reducimos uno al contador de cuantas columnas de cartas quedan
						}
			 }
				 System.out.println("No coincide la carta, es erroneo");
		 }else {
			 System.out.println("NO SE PUEDE MOVER");
		 }
		
		
		
		
		
		 }
		
	
	public static int moverCarta(int poscarta1 ,int poscarta2,String bot1carta, String bot2carta,int cuantasPosiciones,int tamanho) {
		numMovimientos++;
		//entrada: la matriz cartas y el vector indi, el indiceCarta que indica en que posicion es ka carta que queremos mover[i][0] , cuantasPosiciones, si coincide con la anterior o con la 3 anterior, y el numero total de columnas
		String nuevaCarta = ("CARTASESPANOLAS/"+baraja[poscarta2][0]+".jpg");
		if (cuantasPosiciones==3){
			
			for (int k = indices[poscarta2]-1; k >= 0; k--) {//movemos toda la matriz de la carta donde se va a colocar la nueva un valor abajo
				baraja[poscarta2][k+1]=baraja[poscarta2][k];
			}
			baraja[poscarta2][0]=baraja[poscarta1][0];//guardamos el valor de la que se mueve en la primera posicon del nuevo lugar[][0]
			if(indices[poscarta1]==1) {//si el indice de la carta que movemos es 1 signifa que no va a quedar ninguna, entonces movemos todas las cartas siguientes en la matriz
				for (int i = poscarta1; i < tamanho; i++) {
					for (int j = 0; j < 40; j++) {
						baraja[i][j]=baraja[i+1][j];
			
					}
					indices[i]=indices[i+1];//movemos los indices tambien, cuando se elimine una columna
					String MoverTodasLasCartas = ("/CARTASESPANOLAS/"+baraja[i][0]+".jpg");
					JButton botonMover=(JButton) Layout.panelSaltos.getComponent(i);
					
					botonMover.setIcon(new ImageIcon (PanelSaltos.class.getResource( MoverTodasLasCartas)));
				}
				indices[poscarta2]=indices[poscarta2]+1;//aumentamos en 1 el indice de la nueva carta
				baraja[tamanho][0]=null;//ponemos el valor null a la ultima columna, que a sido copiada en la anterior
				indices[tamanho]=0;//hacemos lo mismo con el indice
				Component botonEliminar= Layout.panelSaltos.getComponent(tamanho);
				botonEliminar.setVisible(false);
			//	Layout.panelSaltos.remove(botonEliminar);
				
				String nuevaCarta1 = ("/CARTASESPANOLAS/"+baraja[poscarta2][0]+".jpg");
				System.out.println(nuevaCarta1);
	
				JButton botonMover1=(JButton) Layout.panelSaltos.getComponent(poscarta2);
				botonMover1.setIcon(new ImageIcon (PanelSaltos.class.getResource(nuevaCarta1)));
	
				guardarMovimientos();
				return 0;//devolvemos que ocurrio un salto de columna
			}else {
			for (int h = 0; h <indices[poscarta1] ; h++) {//si el indice de la carta no es 1 simplemente en la columna que quitamos la carta movemos todas las siguientes cartas arriba, dejando en la posicion[][0] la carta que está "arriba" del monton
				baraja[poscarta1][h]=baraja[poscarta1][h+1];
			}
			indices[poscarta2]=indices[poscarta2]+1;//aumentamos 1 la posicion donde se va la carta
			indices[poscarta1]=indices[poscarta1]-1;//descontamos 1 en la posicion donde se fue la carta
			String nuevaCarta1 = ("/CARTASESPANOLAS/"+baraja[poscarta1][0]+".jpg");
			String nuevaCarta2 = ("/CARTASESPANOLAS/"+baraja[poscarta2][0]+".jpg");
			JButton botonMover1=(JButton) Layout.panelSaltos.getComponent(poscarta1);
			botonMover1.setIcon(new ImageIcon (PanelSaltos.class.getResource(nuevaCarta1)));
			JButton botonMover2=(JButton) Layout.panelSaltos.getComponent(poscarta2);
			botonMover2.setIcon(new ImageIcon (PanelSaltos.class.getResource(nuevaCarta2)));
			guardarMovimientos();
			return 1;//la salida es 1 ya que no hubo salto de columna
			}
			
		}else {//cuando el salto es la inmediata de la izquierda
				
			for (int k = indices[poscarta2]-1; k >= 0; k--) {
				baraja[poscarta2][k+1]=baraja[poscarta2][k];
			}
			baraja[poscarta2][0]=baraja[poscarta1][0];
			if(indices[poscarta1]==1) {
				for (int i = poscarta1; i < tamanho; i++) {
					for (int j = 0; j < 40; j++) {
						baraja[i][j]=baraja[i+1][j];
					}
					indices[i]=indices[i+1];
					String MoverTodasLasCartas = ("/CARTASESPANOLAS/"+baraja[i][0]+".jpg");
					JButton botonMover=(JButton) Layout.panelSaltos.getComponent(i);
					botonMover.setIcon(new ImageIcon (PanelSaltos.class.getResource(MoverTodasLasCartas)));
				}
				indices[poscarta2]=indices[poscarta2]+1;
				baraja[tamanho][0]=null;
				indices[tamanho]=0;
				Component botonEliminar= Layout.panelSaltos.getComponent(tamanho);
				botonEliminar.setVisible(false);
				//Layout.panelSaltos.remove(botonEliminar);
				String nuevaCarta1 = ("/CARTASESPANOLAS/"+baraja[poscarta2][0]+".jpg");
				System.out.println(nuevaCarta1);
			//	String nuevaCarta2 = ("CARTASESPANOLAS/"+baraja[poscarta2][0]+".jpg");
				JButton botonMover1=(JButton) Layout.panelSaltos.getComponent(poscarta2);
				botonMover1.setIcon(new ImageIcon (PanelSaltos.class.getResource(nuevaCarta1)));
				guardarMovimientos();
				return 0;
			}else {
			for (int h = 0; h <indices[poscarta1] ; h++) {
				baraja[poscarta1][h]=baraja[poscarta1][h+1];
			
			}
			indices[poscarta2]=indices[poscarta2]+1;
			indices[poscarta1]=indices[poscarta1]-1;
			String nuevaCarta1 = ("/CARTASESPANOLAS/"+baraja[poscarta1][0]+".jpg");
			String nuevaCarta2 = ("/CARTASESPANOLAS/"+baraja[poscarta2][0]+".jpg");
			JButton botonMover1=(JButton) Layout.panelSaltos.getComponent(poscarta1);
			botonMover1.setIcon(new ImageIcon (PanelSaltos.class.getResource(nuevaCarta1)));
			JButton botonMover2=(JButton) Layout.panelSaltos.getComponent(poscarta2);
			botonMover2.setIcon(new ImageIcon (PanelSaltos.class.getResource(nuevaCarta2)));
			guardarMovimientos();
			return 1;
			}
	
		}

	}
	public static void hacer(){
		
		int j=0,salida=0,eliminarCarta;
		do {
			// 	public static int moverCarta(int poscarta1 ,int poscarta2,String bot1carta, String bot2carta,int cuantasPosiciones,int tamanho) {
	
			 if (j<3&&j>0) {//si estamos antes de la columna 3 es imposible que coicida la carta con la 3 anterior ya que no existe
				if(baraja[j][0].charAt(0)==baraja[j-1][0].charAt(0) || baraja[j][0].charAt(1)==baraja[j-1][0].charAt(1)){    //comprobamos si coicide la carta con la carta a la izquierda o 3 mas a la izquierda.
					salida=1;
					eliminarCarta= moverCarta(j,j-1,null,null,1,numCartasActuales);//llamamos a la funcion movercarta
					if (eliminarCarta==0) {//cuando la salida es 0 significa que se acaba de eliminar una columna
						numCartasActuales--;//reducimos uno al contador de cuantas columnas de cartas quedan
					}
					
				}
			}else if(j>0) {//si estamos en la columna 0 no podemos comprobar nada, entonces continua
				if(baraja[j][0].charAt(0)==baraja[j-3][0].charAt(0) || baraja[j][0].charAt(1)==baraja[j-3][0].charAt(1)) {
					salida=1;
					eliminarCarta=moverCarta(j,j-3,null,null,3,numCartasActuales);//llamamos a la funcion movercarta					
		
					if (eliminarCarta==0) {//cuando la salida es 0 significa que se acaba de eliminar una columna
						numCartasActuales--;//reducimos uno al contador de cuantas columnas de cartas quedan
					}
			
					
			}else if(baraja[j][0].charAt(0)==baraja[j-1][0].charAt(0)||baraja[j][0].charAt(1)==baraja[j-1][0].charAt(1)){
				salida=1;
				eliminarCarta=moverCarta(j,j-3,null,null,3,numCartasActuales);//llamamos a la funcion movercarta					
				if (eliminarCarta==0) {//cuando la salida es 0 significa que se acaba de eliminar una columna
					numCartasActuales--;//reducimos uno al contador de cuantas columnas de cartas quedan
				}
				}
			}
			 
			j++;//aumentamos la iteracion en 1
		
		}while(salida==0);
				return ;
		//si el numero de columnas y la valiable de iteracion se iguala significa que ya no hay ams movimientos posibles

	}
		
		
		
public static void resolver(){
		
		int j=0,eliminarCarta;
		do {
			// 	public static int moverCarta(int poscarta1 ,int poscarta2,String bot1carta, String bot2carta,int cuantasPosiciones,int tamanho) {
	
			 if (j<3&&j>0) {//si estamos antes de la columna 3 es imposible que coicida la carta con la 3 anterior ya que no existe
				if(baraja[j][0].charAt(0)==baraja[j-1][0].charAt(0) || baraja[j][0].charAt(1)==baraja[j-1][0].charAt(1)){    //comprobamos si coicide la carta con la carta a la izquierda o 3 mas a la izquierda.
				
					eliminarCarta= moverCarta(j,j-1,null,null,1,numCartasActuales);//llamamos a la funcion movercarta
					if (eliminarCarta==0) {//cuando la salida es 0 significa que se acaba de eliminar una columna
						numCartasActuales--;//reducimos uno al contador de cuantas columnas de cartas quedan
					}
					j=0;
					
				}
			}else if(j>0) {//si estamos en la columna 0 no podemos comprobar nada, entonces continua
				if(baraja[j][0].charAt(0)==baraja[j-3][0].charAt(0) || baraja[j][0].charAt(1)==baraja[j-3][0].charAt(1)) {
				
					eliminarCarta=moverCarta(j,j-3,null,null,3,numCartasActuales);//llamamos a la funcion movercarta					
		
					if (eliminarCarta==0) {//cuando la salida es 0 significa que se acaba de eliminar una columna
						numCartasActuales--;//reducimos uno al contador de cuantas columnas de cartas quedan
					}
					j=0;
			
					
			}else if(baraja[j][0].charAt(0)==baraja[j-1][0].charAt(0)||baraja[j][0].charAt(1)==baraja[j-1][0].charAt(1)){

				eliminarCarta=moverCarta(j,j-3,null,null,3,numCartasActuales);//llamamos a la funcion movercarta					
				if (eliminarCarta==0) {//cuando la salida es 0 significa que se acaba de eliminar una columna
					numCartasActuales--;//reducimos uno al contador de cuantas columnas de cartas quedan
				}
				j=0;
				}
			}
			 
			j++;//aumentamos la iteracion en 1
		
		}while(j!=numCartasActuales+1);
				return ;
		//si el numero de columnas y la valiable de iteracion se iguala significa que ya no hay ams movimientos posibles

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
	         
	            
	            pw.println("Solitario saltos");
	            pw.println("Tengo todas las cartas extraidas desde el principio, esta linea no me es necesaria");
	           
	  
	            for (int i = 0; i <= numCartasActuales; i++) {
					for (int j = indices[i]; j >= 1; j--) {
						pw.print(baraja[i][j-1]);
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
		
		
	
	public static void actualizarIcon() {
		
		for (int i = 0; i < numCartasActuales+1; i++) {
			String nuevaCarta = ("/CARTASESPANOLAS/"+baraja[i][0]+".jpg");
			JButton botonMover1=(JButton) Layout.panelSaltos.getComponent(i);
			botonMover1.setVisible(true);
			botonMover1.setIcon(new ImageIcon (PanelSaltos.class.getResource(nuevaCarta)));
			
			
			
		}
		for (int i = numCartasActuales+1; i <40; i++) {
			JButton botonMover1=(JButton) Layout.panelSaltos.getComponent(i);
			botonMover1.setVisible(false);
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
	        
	        archivo = new File ("movimientos2.txt");
	            try {
	            	
			        if (numMovimientos==0) {
						archivo.delete();
					}else {
						System.out.println("AUXILIAR");
	        	EscitorCopia = new FileWriter("auxiliar.txt");
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
		             
		   
		             
		          //   System.out.println(archivo.delete());
		             try {
		             System.out.println("borrado");
		            // File antiguo=new File("movimientos2.txt");
		            // Path nuevoRuta = Paths.get("movimientos.txt");
		             
		             Path yourFile = Paths.get("auxiliar.txt");
		             Files.move(yourFile, yourFile.resolveSibling("movimientos2.txt"),REPLACE_EXISTING);
		             }catch (Exception e) {
						System.out.println("No se pudo borrar");
					}
		             rNuevo.close();
		             wNuevo.close();
		             
	           //  antiguo.delete();
	           
	        //     Path yourFile = Paths.get("auxiliar.txt");

	      //       Files.move(yourFile, yourFile.resolveSibling("movimientos.txt"),REPLACE_EXISTING);
	           //  nuevoFichero.renameTo(file2);
	        	
	             } catch (Exception e) {
		 	            e.printStackTrace();
	             }
					}
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	            
	        try{
	 
	          
	        	fichero = new FileWriter("movimientos2.txt",true);
	            pw = new PrintWriter(fichero);
	         
	            pw.println("#"+numMovimientos);
	       
	           
	  
	            for (int i = 0; i <= numCartasActuales; i++) {
	            	
					for (int j = indices[i]; j >= 1; j--) {
						pw.print(baraja[i][j-1]);
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
	

