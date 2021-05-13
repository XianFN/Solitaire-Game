package clase;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;



public class Layout extends JFrame{
	public static JPanel panelSaltos = new JPanel();
	public static PanelSaltos p;
	public static PanelClasico pC;
	public static  JLayeredPane panelClasico = new JLayeredPane();
	//public File guardarSaltos;
	public FileWriter guardarSaltos;
	public File guardarClasico;
	public String rutaEstadisticas=null;
	public String rutaSaltos = null;
	public String rutaClasico=null;
	public String actualPanel="";
	
		public Layout() {
		
			setBounds(1024/ 4,768/6,1024,768);
			
			setTitle("Solitario");
			
			setResizable(false);
			
			initComponents();
			
			
		}
		
		private void initComponents() {
			
		//	JPanel panelmenu = new JPanel();
			JMenu mnArchivo,mnNuevo,mnEditar, mnHistorial;
			JMenuItem mnClasico,mnSaltos,mnCargar,mnSalvar,mnSalvarcomo,mnSalir,mnDeshacer,mnHacer,mnResolver,mnEstadisticas,mnFicheroEstadisticas, mnAyuda;
			EscuchadorClasico escC=new EscuchadorClasico();
			EscuchadorSaltos escS = new EscuchadorSaltos();
			EscuchadorDesHacer escDesH = new EscuchadorDesHacer();
			EscuchadorHacer escHac = new EscuchadorHacer();
			EscuchadorResolver escRes = new EscuchadorResolver();
			EscuchadorCargar escCar = new EscuchadorCargar();
			EscuchadorSalvar escSal = new EscuchadorSalvar();
			EscuchadorSalvarComo escSalComo = new EscuchadorSalvarComo();
			EscuchadorSalir escSalir= new EscuchadorSalir();
			EscuchadorAyuda escAyu= new EscuchadorAyuda();
			EscuchadorFicheroEstadisticas escFicEst=new EscuchadorFicheroEstadisticas();
		
			
			
			JMenuBar barra = new JMenuBar();
			
			mnArchivo = new JMenu ("Archivo");
			
			barra.add(mnArchivo);
			
			mnNuevo= new JMenu ("Nuevo");
			 
			mnArchivo.add(mnNuevo);
			
			System.out.println("HOLA");
			
			mnClasico = new JMenuItem("Clasico");

			mnSaltos = new JMenuItem("Saltos");
			
			mnNuevo.add(mnClasico);
			mnNuevo.add(mnSaltos);
					
			mnCargar= new JMenuItem("Cargar");
			mnSalvar= new JMenuItem("Salvar");
			mnSalvarcomo= new JMenuItem("Salvar Como");
			mnSalir= new JMenuItem("Salir");
			
			mnArchivo.add(mnCargar);
			mnArchivo.add(mnSalvar);
			mnArchivo.add(mnSalvarcomo);
			mnArchivo.add(mnSalir);
		
			mnEditar = new JMenu ("Editar");
			barra.add(mnEditar);
			
			mnDeshacer= new JMenuItem("Deshacer");
			mnHacer= new JMenuItem("Hacer");
			mnResolver= new JMenuItem("Resolver");
			
			mnEditar.add(mnDeshacer);
			mnEditar.add(mnHacer);
			mnEditar.add(mnResolver);
			
		
			mnHistorial = new JMenu ("Historial");
			barra.add(mnHistorial);
			
			mnEstadisticas= new JMenuItem("Estadísticas");
			mnFicheroEstadisticas= new JMenuItem("Fichero Estadisticas");
			
			mnHistorial.add(mnEstadisticas);
			mnHistorial.add(mnFicheroEstadisticas);
			
			
			mnAyuda = new JMenuItem ("Ayuda");
			barra.add(mnAyuda);
			
			//EVITAR QUE AYUDA SE EXPANDA HACIA LA DERECHA//
			barra.add(Box.createRigidArea(new Dimension(800,0)));
			
			
			
		
			
		//	panelmenu.add(barra);
			
			
			panelSaltos.setBackground(new Color(0, 165, 74));
			panelSaltos.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panelSaltos.setVisible(false);
			panelSaltos.setName("Saltos");
			
			panelClasico.setBackground(new Color(0, 165, 74));
			
		
			panelClasico.setVisible(false);

			panelClasico.setOpaque(true);
			panelClasico.setName("Clasico");
			setJMenuBar (barra);
			
			
			
			mnClasico.addActionListener(escC);
			mnSaltos.addActionListener(escS);
			mnDeshacer.addActionListener(escDesH);
			mnHacer.addActionListener(escHac);
			mnResolver.addActionListener(escRes);
			mnCargar.addActionListener(escCar);
			mnSalvar.addActionListener(escSal);
			mnSalvarcomo.addActionListener(escSalComo);
			mnSalir.addActionListener(escSalir);
			mnAyuda.addActionListener(escAyu);
			mnFicheroEstadisticas.addActionListener(escFicEst);
		}
		public void actualizarEstadisticas(int tipoSolitario,int tipo) {
			//Ruta es la ruta del archivo
			//tipoSolitario es la entrada de que solitario vamos a hacer el cambio de estadistica siendo 2 el solitario clasico y 1 saltos
			//tipo, siendo 1 nueva partida y 2 partida completada
			
			if (tipo==1) {
				FileWriter fichero=null;
		        PrintWriter pw=null;
		        
		        FileWriter EscitorCopia=null;
		        PrintWriter wNuevo=null;
		        File archivo = null;
		        FileReader LectorOriginal = null;
		        BufferedReader rNuevo = null;
		        
		        archivo = new File (rutaEstadisticas);
		            try {
		            	
				    System.out.println("Nueva partida");
		        	EscitorCopia = new FileWriter("auxiliar.txt");
		        	wNuevo = new PrintWriter(EscitorCopia);
		        	LectorOriginal = new FileReader (archivo);
		            rNuevo = new BufferedReader(LectorOriginal);
		        	
		             String linea;
		            
		         
		             try {
		            	
			             if(archivo.length()>0) {
			            	 System.out.println("no está vacio");
			            	 //linea 1
			            	linea=rNuevo.readLine();
				            	
			            	wNuevo.println(linea);
			            	if (tipoSolitario==1) {
			            			//linea 2
			            		linea=rNuevo.readLine();
			            	
			            		
			            		int numero=Integer.parseInt(linea);
			            		numero++;
			            		wNuevo.println(numero);
			            		
			            		//copio exactamente las siguientes lineas
			            		//linea 3
			            		linea=rNuevo.readLine();
				            	wNuevo.println(linea);
				            	//linea 4
				            	linea=rNuevo.readLine();
				            	wNuevo.println(linea);
				            	//linea 5
				            	linea=rNuevo.readLine();
				            	wNuevo.println(linea);
				            	//linea 6
				            	linea=rNuevo.readLine();
				            	wNuevo.println(linea);
							}else {
								
										         //linea 2    	
								linea=rNuevo.readLine();
								wNuevo.println(linea);
												//linea 3
								linea=rNuevo.readLine();
								wNuevo.println(linea);
												//linea 4
								linea=rNuevo.readLine();
				            	wNuevo.println(linea);
												//linea 5
								linea=rNuevo.readLine();
			            		int numero=Integer.parseInt(linea);
			            		numero++;
			            		wNuevo.println(numero);
			            		//copio exactamente las siguientes lineas
			            		
				            
				            	//linea 6
				            
				            	
				            	linea=rNuevo.readLine();
				            	wNuevo.println(linea);
							}
			            	
			            	
			           
			          
			 	        }else {
			 	        	//ESTA VACIO
			 	        	 System.out.println("está vacio");
			            	 //linea 1
			            	linea=rNuevo.readLine();
				            	
			            	wNuevo.println(linea);
			            	if (tipoSolitario==1) {
			            			//linea 2
			            		linea=rNuevo.readLine();
			            	
			            		
			            		int numero=1;
			            		wNuevo.println(numero);
			            		
			            		//copio exactamente las siguientes lineas
			            		//linea 3
			            		linea=rNuevo.readLine();
				            	wNuevo.println(linea);
				            	//linea 4
				            	linea=rNuevo.readLine();
				            	wNuevo.println(linea);
				            	//linea 5
				            	linea=rNuevo.readLine();
				            	wNuevo.println(linea);
				            	//linea 6
				            	linea=rNuevo.readLine();
				            	wNuevo.println(linea);
							}else {
								
										         //linea 2    	
								linea=rNuevo.readLine();
								wNuevo.println(linea);
												//linea 3
								linea=rNuevo.readLine();
								wNuevo.println(linea);
												//linea 4
								linea=rNuevo.readLine();
				            	wNuevo.println(linea);
												//linea 5
								linea=rNuevo.readLine();
								int numero=1;
			            		wNuevo.println(numero);
			            		//copio exactamente las siguientes lineas
			            	
				            
				            	//linea 6
				            
				            	
				            	linea=rNuevo.readLine();
				            	wNuevo.println(linea);
							}
			 	        }
			             
			             try {
				             System.out.println("borrado");
				       
				             
				             Path yourFile = Paths.get("auxiliar.txt");
				             Files.move(yourFile, yourFile.resolveSibling(rutaEstadisticas),REPLACE_EXISTING);
				             }catch (Exception e) {
								System.out.println("No se pudo borrar");
							}
				             rNuevo.close();
				             wNuevo.close();
				             
			    
			        	
			             } catch (Exception e) {
				 	            e.printStackTrace();
			             }
							
			     
		            } catch (Exception e) {
		 	            e.printStackTrace();
	             }
					 
			   
				
				
				
			}else {
				FileWriter fichero=null;
		        PrintWriter pw=null;
		        
		        FileWriter EscitorCopia=null;
		        PrintWriter wNuevo=null;
		        File archivo = null;
		        FileReader LectorOriginal = null;
		        BufferedReader rNuevo = null;
		        
		        archivo = new File (rutaEstadisticas);
		            try {
		            	
				    System.out.println("Nueva partida");
		        	EscitorCopia = new FileWriter("auxiliar.txt");
		        	wNuevo = new PrintWriter(EscitorCopia);
		        	LectorOriginal = new FileReader (archivo);
		            rNuevo = new BufferedReader(LectorOriginal);
		        	
		             String linea;
		            
		         
		             try {
		            	
			             if(archivo.length()>0) {
			            	 System.out.println("no está vacio");
			            	 //linea 1
			            	linea=rNuevo.readLine();
				            	
			            	wNuevo.println(linea);
			            	if (tipoSolitario==1) {
			            			//linea 2
			            		linea=rNuevo.readLine();
				            	wNuevo.println(linea);
			            		
			            		//linea 3
				            	linea=rNuevo.readLine();
			            	
			            		
			            		int numero=Integer.parseInt(linea);
			            		numero++;
			            		wNuevo.println(numero);
			            		
			            		//copio exactamente las siguientes lineas
				            	//linea 4
				            	linea=rNuevo.readLine();
				            	wNuevo.println(linea);
				            	//linea 5
				            	linea=rNuevo.readLine();
				            	wNuevo.println(linea);
				            	//linea 6
				            	linea=rNuevo.readLine();
				            	wNuevo.println(linea);
							}else {
								
										         //linea 2    	
								linea=rNuevo.readLine();
								wNuevo.println(linea);
												//linea 3
								linea=rNuevo.readLine();
								wNuevo.println(linea);
												//linea 4
								linea=rNuevo.readLine();
				            	wNuevo.println(linea);
												//linea 5
							
			            	
			            		linea=rNuevo.readLine();
				            	wNuevo.println(linea);
				            
				            				//linea 6
				            
				            	
				            	linea=rNuevo.readLine();
			            		int numero=Integer.parseInt(linea);
			            		numero++;
			            		wNuevo.println(numero);
							}
			            	
			            	
			           
			          
			 	        }else {
			 	        	//ESTA VACIO
			 	        	 System.out.println("está vacio");
			            	 //linea 1
			            	linea=rNuevo.readLine();
				            	
			            	wNuevo.println(linea);
			            	if (tipoSolitario==1) {
			            			//linea 2
			            		linea=rNuevo.readLine();
				            	wNuevo.println(linea);
			            	
			            		//linea 3
			            		linea=rNuevo.readLine();
			            	
			            		
			            		int numero=1;
			            		wNuevo.println(numero);
			            		//copio exactamente las siguientes lineas
				            	//linea 4
				            	linea=rNuevo.readLine();
				            	wNuevo.println(linea);
				            	//linea 5
				            	linea=rNuevo.readLine();
				            	wNuevo.println(linea);
				            	//linea 6
				            	linea=rNuevo.readLine();
				            	wNuevo.println(linea);
							}else {
								
										         //linea 2    	
								linea=rNuevo.readLine();
								wNuevo.println(linea);
												//linea 3
								linea=rNuevo.readLine();
								wNuevo.println(linea);
												//linea 4
								linea=rNuevo.readLine();
				            	wNuevo.println(linea);
												//linea 5
				            	linea=rNuevo.readLine();
				            	wNuevo.println(linea);
				            
				            	//linea 6
				            
				            	
				        		linea=rNuevo.readLine();
								int numero=1;
			            		wNuevo.println(numero);
			            		//copio exactamente las siguientes lineas
			            		
							}
			 	        }
			             
			             try {
				             System.out.println("borrado");
				       
				             
				             Path yourFile = Paths.get("auxiliar.txt");
				             Files.move(yourFile, yourFile.resolveSibling(rutaEstadisticas),REPLACE_EXISTING);
				             }catch (Exception e) {
								System.out.println("No se pudo borrar");
							}
				             rNuevo.close();
				             wNuevo.close();
				             
			    
			        	
			             } catch (Exception e) {
				 	            e.printStackTrace();
			             }
							
			     
		            } catch (Exception e) {
		 	            e.printStackTrace();
	             }
					 
			   
				
				
				
				
				
				
				
			}
			
			
			
			
		}

	public class EscuchadorClasico implements ActionListener{
		
		public void actionPerformed (ActionEvent arg0) {
			
			{
				System.out.println("Clasico");
				panelClasico.setBackground(new Color(0, 165, 74));
				panelSaltos.setVisible(false);
				getContentPane().add(panelClasico, BorderLayout.CENTER);
				panelClasico.setVisible(true);
				actualPanel="Clasico";
		
				try {
					String barajaMesa[][]=new String [7][20];
					 int contadorBotonOculto[]= {0,1,2,3,4,5,6};
					System.out.println("trysadasd");
					try
			        {
					FileWriter fichero = new FileWriter("movimientosClasico.txt");
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
					pC.setContadorBaraja(0);
					 pC.setNumMovimientos(0);
					 pC.setBarajaMesa(barajaMesa);
					
					System.out.println("asdasdasdas");
					pC.setContadorBotonOculto(contadorBotonOculto);
				
					pC.rellenarArray();
					pC.bloquearBotones(); 
					pC.	cambiarIcon();
					
		
			
				
			 
					 
				    
				} catch (Exception e) {
					pC= new PanelClasico(panelClasico);
				}
			}
			
		}
		
		
	}
	public class EscuchadorSaltos implements ActionListener{
		
		public void actionPerformed (ActionEvent arg0) {
			
			{
				System.out.println("Saltos");
				
			/*
			

				JPanel panelSaltos = new JPanel();
				panelSaltos.setBackground(new Color(0, 165, 74));
				getContentPane().add(panelSaltos, BorderLayout.CENTER);
				panelSaltos.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				panelSaltos.setVisible(true);
				
			*/
			
				
				panelClasico.setVisible(false);
				getContentPane().add(panelSaltos, BorderLayout.CENTER);
				
				panelSaltos.setVisible(true);
				
				actualPanel="Saltos";
				try {
					System.out.println("trysadasd");
			
					p.setNumCartasActuales(39);
					System.out.println("asdasdasdas");
					 p.setNumMovimientos(0);
					 String barajaCompleta[]= {"AO","2O","3O","4O","5O","6O","7O","SO","CO","RO","AC","2C","3C","4C","5C","6C","7C","SC","CC","RC","AE","2E","3E","4E","5E","6E","7E","SE","CE","RE","AB","2B","3B","4B","5B","6B","7B","SB","CB","RB"};
					 for (int i = 0; i <= 39; i++) {
				
					System.out.println("HOLAAA"+i);
					p.rellenarArray(i, barajaCompleta);
				
			 
					 }
				     
				        PanelSaltos.actualizarIcon();
				} catch (Exception e) {
					System.out.println("catcasdsad");
					 p= new PanelSaltos(panelSaltos);
				}
			
				 /*
				   p.setBaraja(baraja);
			        p.setIndices(indices);
			        p.setNumCartasActuales(numeroColumna);
			        p.setNumMovimientos(NumMovimientosNuevo);
			        PanelSaltos.actualizarIcon();
			        */
			

			}
			
		}
		
			
		}
public class EscuchadorCargar implements ActionListener{
		
		public void actionPerformed (ActionEvent arg0) throws HeadlessException{
			if (actualPanel=="") {
			
				System.out.println("Cargar");
				JFileChooser jF1= new JFileChooser(); 
					String rutaCargar = ""; 
					try{ 
					if(jF1.showSaveDialog(null)==jF1.APPROVE_OPTION){ 
						rutaCargar = jF1.getSelectedFile().getAbsolutePath(); 
					
					}else {
						return;
					}
					//p.cargar(ruta);
					}catch (Exception ex){ 
					ex.printStackTrace(); 
					}
					
		
					  File archivo = null;
				      FileReader fr = null;
				      BufferedReader br = null;
				      String tipoSolitario;
				      
				     

				      try {
				         // Apertura del fichero y creacion de BufferedReader para poder
				         // hacer una lectura comoda (disponer del metodo readLine()).
				         archivo = new File (rutaCargar);
				         fr = new FileReader (archivo);
				         br = new BufferedReader(fr);

				        
				     
				         tipoSolitario=br.readLine();
				         if(tipoSolitario.equals("Solitario clásico")){
				        	 System.out.println("Clasico");
								panelClasico.setBackground(new Color(0, 165, 74));
								panelSaltos.setVisible(false);
								getContentPane().add(panelClasico, BorderLayout.CENTER);
								panelClasico.setVisible(true);
								actualPanel="Clasico";
								pC= new PanelClasico(panelClasico);
				        	 
				         }else if(tipoSolitario.equals("Solitario saltos")){
				        		panelClasico.setVisible(false);
								getContentPane().add(panelSaltos, BorderLayout.CENTER);
								
								panelSaltos.setVisible(true);
								
								actualPanel="Saltos";
								 p= new PanelSaltos(panelSaltos);
						}
				      }catch (Exception e) {
						System.out.println("Error");
					}
			}
			if (actualPanel=="Clasico") {
				// CARGAR CLASICO//
				
				System.out.println("Cargar");
				JFileChooser jF1= new JFileChooser(); 
					String rutaCargar = ""; 
					try{ 
					if(jF1.showSaveDialog(null)==jF1.APPROVE_OPTION){ 
						rutaCargar = jF1.getSelectedFile().getAbsolutePath(); 
					
					}else {
						return;
					}
					//p.cargar(ruta);
					}catch (Exception ex){ 
					ex.printStackTrace(); 
					}
					
				/*
				 * 	public static String barajaDestapar[]=new String[24];
					public static String barajaMesa[][]=new String [7][20];
					public static String barajaAses[][]=new String [4][13];
					public static int contadorBaraja=0;
					private static int contadoClick=0;
					private static int numMovimientos=0;
					private static JButton primerBotonPulsado;
					private static int tipoPrimeraCarta;
				 */
					  File archivo = null;
				      FileReader fr = null;
				      BufferedReader br = null;
				      
				      String barajaDestapar[]=new String [24];
				 	  String barajaMesa[][]=new String [7][20];
				      String barajaAses[][]=new String [4][13];
				      int contadorBotonOculto[]=new int [7];
				      
				     

				      try {
				         // Apertura del fichero y creacion de BufferedReader para poder
				         // hacer una lectura comoda (disponer del metodo readLine()).
				         archivo = new File (rutaCargar);
				         fr = new FileReader (archivo);
				         br = new BufferedReader(fr);

				        
				     
				         br.readLine();
				         String linea,linea2,carta;
				        
				         linea=br.readLine();
				         linea2=br.readLine().concat(linea);
				         int contadorBaraja=linea2.length()/2-linea.length()/2;
				         int numeroFila=0;
				  //       System.out.println(contadorBaraja);
				         //LEO CARTAS DE LA BARAJA, QUE ES LA LINEA 2 Y 3 SIENDO LA LONGITUD DE LAS 2 LINEAS MENOS LA PRIMERA, MI CONTADOR DE CARTAS DESCUBIERTAS//
				         for (int i = 0; i < linea2.length(); i+=2) {
			        		 
			        		 carta= linea2.substring(i, i+2);
			        		 
			        		 barajaDestapar[numeroFila]=carta;
			        //		 System.out.println(barajaDestapar[numeroFila]);
			        		 numeroFila++;
						}
				         //SIGUIENTES 7 LINEAS PERTENECEN A CADA UNO DE LOS MONTONES SIENDO * EL DILIMITADOR ENTRE OCULTA/NO    //
				         
				         
				         for (int i = 0; i < 7; i++) {
							
				        	    linea=br.readLine();
				        	    numeroFila=0;
				        	 
				        	    for (int j = 0; j < linea.length()-1; j+=2) {
				        	    	System.out.println(linea.substring(j,j+1));
				        	    	char asterisco=linea.charAt(j);
					        		 if (linea.charAt(j)=='*') {
										contadorBotonOculto[i]=numeroFila;
										System.out.println(numeroFila+"   "+contadorBotonOculto[i]);
										j--;//resto uno para ajustar el desajuste dado que * ocupa solo un lugar//
									}else {
					        		 carta= linea.substring(j, j+2);
					        	//	 System.out.println(carta);
					        		 barajaMesa[i][numeroFila]=carta;
					        		 numeroFila++;
					        		 
									} 
								}
	    
				        	    
						}
				         //SIGUIENTES 4 LINEAS LA ZONA DE LOS ASES//
				         
				         for (int i = 0; i < 4; i++) {
				        	 linea=br.readLine();
				        	 numeroFila=0;
				        	 for (int j = 0; j < linea.length(); j+=2) {
				        		 
				        		 carta= linea.substring(j, j+2);
				        		 barajaAses[i][numeroFila]=carta;
				        		 numeroFila++;
							}
						}
				     
					        System.out.println("ejecuto ");
					        pC.setBarajaDestapar(barajaDestapar);
					        pC.setBarajaMesa(barajaMesa);
					        pC.setBarajaAses(barajaAses);
					        pC.setContadorBaraja(contadorBaraja);
					        pC.setContadorBotonOculto(contadorBotonOculto);
					        pC.setNumMovimientos(0);
					        pC.bloquearBotones();
					        pC.cambiarIcon();
					

				      } catch(Exception e){
					         e.printStackTrace();
					      }finally{
					         // En el finally cerramos el fichero, para asegurarnos
					         // que se cierra tanto si todo va bien como si salta 
					         // una excepcion.
					         try{                    
					            if( null != fr ){   
					               fr.close();     
					            }                  
					         }catch (Exception e2){ 
					            e2.printStackTrace();
					         }
					      }
				
				
			}else if(actualPanel=="Saltos"){
				
				
			
			System.out.println("Cargar");
			JFileChooser jF1= new JFileChooser(); 
				String rutaCargar = ""; 
				try{ 
				if(jF1.showSaveDialog(null)==jF1.APPROVE_OPTION){ 
					rutaCargar = jF1.getSelectedFile().getAbsolutePath(); 
				
				}else {
					return;
				}
				//p.cargar(ruta);
				}catch (Exception ex){ 
				ex.printStackTrace(); 
				}
				
				
			
				
				  File archivo = null;
			      FileReader fr = null;
			      BufferedReader br = null;
			      String baraja[][]=new String[40][40];
			      int indices[]= new int[40];
			      int numeroColumna=0;

			      try {
			         // Apertura del fichero y creacion de BufferedReader para poder
			         // hacer una lectura comoda (disponer del metodo readLine()).
			         archivo = new File (rutaCargar);
			         fr = new FileReader (archivo);
			         br = new BufferedReader(fr);

			         // Lectura del fichero
			     
			         br.readLine();
			         br.readLine();
			         String linea,carta;
			         

			         while((linea=br.readLine())!=null) {
			        	 int numeroFila=(linea.length()/2)-1;
			        	 indices[numeroColumna]=numeroFila+1;
			        	 for (int i = 0; i < linea.length(); i+=2) {
			        		 
			        		 carta= linea.substring(i, i+2);
			        		 baraja[numeroColumna][numeroFila]=carta;
			        		 numeroFila--;
						}
			        	 
			        	 numeroColumna++;
			        	
			         }
			         
			         for (int i = 0; i < 40; i++) {
			        	 for (int j = 0; j < 40; j++) {
							System.out.print(baraja[i][j]);
						}
			        	
						System.out.println();
					}
			        for (int i = 0; i < 40; i++) {
						System.out.print(" "+indices[i]);
					}
				        System.out.println("ejecuto ");
				        p.setBaraja(baraja);
				        p.setIndices(indices);
				        p.setNumCartasActuales(numeroColumna-1);
				        p.setNumMovimientos(0);
				        PanelSaltos.actualizarIcon();
				

			      } catch(Exception e){
				         e.printStackTrace();
				      }finally{
				         // En el finally cerramos el fichero, para asegurarnos
				         // que se cierra tanto si todo va bien como si salta 
				         // una excepcion.
				         try{                    
				            if( null != fr ){   
				               fr.close();     
				            }                  
				         }catch (Exception e2){ 
				            e2.printStackTrace();
				         }
				      }
		}
}
}
public class EscuchadorSalvar implements ActionListener{
		
		public void actionPerformed (ActionEvent arg0) throws HeadlessException{
			
			{
				
				if (actualPanel=="") {
					System.out.println("Debes Empezar una partida para guardar");
					
				}else if (actualPanel=="Clasico") {
					
					System.out.println("Salvar clasico");
					if (rutaClasico!=null) {
						pC.salvar(rutaClasico);
					}else{
					
					JFileChooser jF1= new JFileChooser(); 
					
					try{ 
					if(jF1.showSaveDialog(null)==jF1.APPROVE_OPTION){ 
						rutaClasico = jF1.getSelectedFile().getAbsolutePath(); 
					if(new File(rutaClasico).exists()) 
					{ 
					if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog((Component) arg0.getSource(),"El fichero existe,deseas reemplazarlo?","Titulo",JOptionPane.YES_NO_OPTION)) {
						
					} else {
						return ;
					} 
					}
					}else {
						return;
					}
					pC.salvar(rutaClasico);
					
					}catch (Exception ex){ 
					ex.printStackTrace(); 
					}
					
					}
					
				}else {
									//SALVAR SALTOS//
				System.out.println("Salvar Saltos");
				
				if (rutaSaltos!=null) {
					p.salvar(rutaSaltos);
				}else{
				
				JFileChooser jF1= new JFileChooser(); 
				
				try{ 
				if(jF1.showSaveDialog(null)==jF1.APPROVE_OPTION){ 
					rutaSaltos = jF1.getSelectedFile().getAbsolutePath(); 
				if(new File(rutaSaltos).exists()) 
				{ 
				if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog((Component) arg0.getSource(),"El fichero existe,deseas reemplazarlo?","Titulo",JOptionPane.YES_NO_OPTION)) {
					
				} else {
					return ;
				} 
				}
				}else {
					return;
				}
				p.salvar(rutaSaltos);
				
				}catch (Exception ex){ 
				ex.printStackTrace(); 
				}
				
				} 
			}
			}
		}
			
		}
	public class EscuchadorSalvarComo implements ActionListener{
		
		public void actionPerformed (ActionEvent arg0) throws HeadlessException{
			
			{
				if (actualPanel=="") {
					System.out.println("Debes Empezar una partida para guardar");
					return;
				}
					
					
				
				JFileChooser jF1= new JFileChooser(); 
				String ruta="";
				try{ 
				if(jF1.showSaveDialog(null)==jF1.APPROVE_OPTION){ 
				ruta = jF1.getSelectedFile().getAbsolutePath(); 
				if(new File(ruta).exists()) 
				{ 
				if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog((Component) arg0.getSource(),"El fichero existe,deseas reemplazarlo?","Titulo",JOptionPane.YES_NO_OPTION)) {
						
				} else {
						return ;
				
				} 
				}
				}else {
					return;
				}
				System.out.println(actualPanel);
				 if (actualPanel=="Clasico") {
					rutaClasico=ruta;
					pC.salvar(ruta);
				}else {
					rutaSaltos=ruta;
					p.salvar(ruta);
				}
				}catch (Exception ex){ 
				ex.printStackTrace(); 
				}
				
				} 
		}
	}
			
		
		
			
		
public class EscuchadorDesHacer implements ActionListener{
		
		public void actionPerformed (ActionEvent arg0) {
			
			
				if (actualPanel=="") {
					System.out.println("Debes Empezar una partida para guardar");
					
				}else if (actualPanel=="Clasico") {
					  File archivo = null;
				      FileReader fr = null;
				      BufferedReader br = null;
				      
				      String barajaDestapar[]=new String [24];
				 	  String barajaMesa[][]=new String [7][20];
				      String barajaAses[][]=new String [4][13];
				      int contadorBotonOculto[]=new int [7];
				      
				     

				      try {
				         // Apertura del fichero y creacion de BufferedReader para poder
				         // hacer una lectura comoda (disponer del metodo readLine()).
				         archivo = new File ("movimientosClasico.txt");
				         fr = new FileReader (archivo);
				         br = new BufferedReader(fr);

				         int numeroColumna=0;
					      if (p.getNumMovimientos()<=0) {
							return;
						}
				         int NumMovimientosNuevo= p.getNumMovimientos()-1;
				         String posicionDatos="#"+NumMovimientosNuevo;
				         String limitador="#"+p.getNumMovimientos();
				         System.out.println(posicionDatos);
				         String linea,carta;
				         if(NumMovimientosNuevo>=0) {
				         while(!( linea=br.readLine()).equals(posicionDatos)) {
				        	
				         }
				         }
				         while(!(linea=br.readLine()).equals(limitador)) {
				        	 
				        	
				        
				         
				         br.readLine();
				         
				        
				         linea=br.readLine();
				         linea=br.readLine().concat(linea);
				         int contadorBaraja=linea.length()/2-linea.length()/2;
				         int numeroFila=0;
				  //       System.out.println(contadorBaraja);
				         //LEO CARTAS DE LA BARAJA, QUE ES LA LINEA 2 Y 3 SIENDO LA LONGITUD DE LAS 2 LINEAS MENOS LA PRIMERA, MI CONTADOR DE CARTAS DESCUBIERTAS//
				         for (int i = 0; i < linea.length(); i+=2) {
			        		 
			        		 carta= linea.substring(i, i+2);
			        		 
			        		 barajaDestapar[numeroFila]=carta;
			        //		 System.out.println(barajaDestapar[numeroFila]);
			        		 numeroFila++;
						}
				         //SIGUIENTES 7 LINEAS PERTENECEN A CADA UNO DE LOS MONTONES SIENDO * EL DILIMITADOR ENTRE OCULTA/NO    //
				         
				         
				         for (int i = 0; i < 7; i++) {
							
				        	    linea=br.readLine();
				        	    numeroFila=0;
				        	 
				        	    for (int j = 0; j < linea.length()-1; j+=2) {
				        	    	System.out.println(linea.substring(j,j+1));
				        	    	char asterisco=linea.charAt(j);
					        		 if (linea.charAt(j)=='*') {
										contadorBotonOculto[i]=numeroFila;
										System.out.println(numeroFila+"   "+contadorBotonOculto[i]);
										j--;//resto uno para ajustar el desajuste dado que * ocupa solo un lugar//
									}else {
					        		 carta= linea.substring(j, j+2);
					        	//	 System.out.println(carta);
					        		 barajaMesa[i][numeroFila]=carta;
					        		 numeroFila++;
					        		 
									} 
								}
	    
				        	    
						}
				         //SIGUIENTES 4 LINEAS LA ZONA DE LOS ASES//
				         
				         for (int i = 0; i < 4; i++) {
				        	 linea=br.readLine();
				        	 numeroFila=0;
				        	 for (int j = 0; j < linea.length(); j+=2) {
				        		 
				        		 carta= linea.substring(j, j+2);
				        		 barajaAses[i][numeroFila]=carta;
				        		 numeroFila++;
							}
						}
				     
					        System.out.println("ejecuto ");
					        pC.setBarajaDestapar(barajaDestapar);
					        pC.setBarajaMesa(barajaMesa);
					        pC.setBarajaAses(barajaAses);
					        pC.setContadorBaraja(contadorBaraja);
					        pC.setContadorBotonOculto(contadorBotonOculto);
					        pC.setNumMovimientos(0);
					        pC.bloquearBotones();
					        pC.cambiarIcon();
					
				         }
				      } catch(Exception e){
					         e.printStackTrace();
					      }finally{
					         // En el finally cerramos el fichero, para asegurarnos
					         // que se cierra tanto si todo va bien como si salta 
					         // una excepcion.
					         try{                    
					            if( null != fr ){   
					               fr.close();     
					            }                  
					         }catch (Exception e2){ 
					            e2.printStackTrace();
					         }
					      }
				      
				}else {
						// DESHACER DE SALTOS//
					
				
				System.out.println("DesHacer");
				
				  File archivo = null;
			      FileReader fr = null;
			      BufferedReader br = null;
			      String baraja[][]=new String[40][40];
			      int indices[]= new int[40];
			      int numeroColumna=0;
			      if (p.getNumMovimientos()<=0) {
					return;
				}
			      try {
			         // Apertura del fichero y creacion de BufferedReader para poder
			         // hacer una lectura comoda (disponer del metodo readLine()).
			         archivo = new File ("movimientos2.txt");
			         fr = new FileReader (archivo);
			         br = new BufferedReader(fr);

			         // Lectura del fichero
			     
			         int NumMovimientosNuevo= p.getNumMovimientos()-1;
			         String posicionDatos="#"+NumMovimientosNuevo;
			         String limitador="#"+p.getNumMovimientos();
			         System.out.println(posicionDatos);
			         String linea,carta;
			         if(NumMovimientosNuevo>=0) {
			         while(!( linea=br.readLine()).equals(posicionDatos)) {
			        	
			         }
			         }
			         while(!(linea=br.readLine()).equals(limitador)) {
			        	 
			        	 if ( (linea != null) && (!linea.equals("")) ) {
						
			        	 int numeroFila=(linea.length()/2)-1;
			        	 indices[numeroColumna]=numeroFila+1;
			        	 for (int i = 0; i < linea.length(); i+=2) {
			        		 
			        		 carta= linea.substring(i, i+2);
			        		 baraja[numeroColumna][numeroFila]=carta;
			        		 numeroFila--;
						}
			        	 
			        	 numeroColumna++;
			        	 }
			         }
			         
			         
			         for (int i = 0; i < 40; i++) {
			        	 for (int j = 0; j < 40; j++) {
							System.out.print(baraja[i][j]);
						}
			        	
						System.out.println();
					}
			        for (int i = 0; i < 40; i++) {
						System.out.print(" "+indices[i]);
					}
				        System.out.println("ejecuto ");
				        p.setBaraja(baraja);
				        p.setIndices(indices);
				        p.setNumCartasActuales(numeroColumna-1);
				        p.setNumMovimientos(NumMovimientosNuevo);
				        PanelSaltos.actualizarIcon();
					   //   p=new PanelSaltos(baraja,indices,numeroColumna,p.getNumMovimientos()-1);
			      }
			      catch(Exception e){
			         e.printStackTrace();
			      }finally{
			         // En el finally cerramos el fichero, para asegurarnos
			         // que se cierra tanto si todo va bien como si salta 
			         // una excepcion.
			         try{                    
			            if( null != fr ){   
			               fr.close();     
			            }                  
			         }catch (Exception e2){ 
			            e2.printStackTrace();
			         }
			      }
			

			
			
		}
		
}
}
public class EscuchadorHacer implements ActionListener{
	
	public void actionPerformed (ActionEvent arg0) {
		
		
			if (actualPanel=="") {
				System.out.println("Debes Empezar una partida para guardar");
				
			}else if (actualPanel=="Clasico") {
				System.out.println("Hacer");
				
			}else {
				
				
			
			System.out.println("Hacer");
			
			PanelSaltos.hacer();
		}
		
	}
	
		
	}
public class EscuchadorResolver implements ActionListener{
	
	public void actionPerformed (ActionEvent arg0) {
		
		
			if (actualPanel=="") {
				System.out.println("Debes Empezar una partida para guardar");
				
			}else if (actualPanel=="Clasico") {
				
			}else {
				
				
			}
			System.out.println("Resolver");
			
			PanelSaltos.resolver();
		
		
	}
	
		
	}
public class EscuchadorSalir implements ActionListener{
	
	public void actionPerformed (ActionEvent arg0) {
		
		 
		        String ObjButtons[] = {"Si","No"};
		        int salir = JOptionPane.showOptionDialog(null,"¿Quieres guardar antes de salir?","Comprobar Salir",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
		        if(salir==JOptionPane.NO_OPTION)
		        {
		            System.exit(0);
		        }
		        if (salir==JOptionPane.YES_OPTION) {
		        	{
						
						if (actualPanel=="") {
							System.out.println("No hay ninguna partida para guardar");
							System.exit(0);
							
						}else if (actualPanel=="Clasico") {
							
							System.out.println("Salvar clasico");
							if (rutaClasico!=null) {
								pC.salvar(rutaClasico);
							}else{
							
							JFileChooser jF1= new JFileChooser(); 
							
							try{ 
							if(jF1.showSaveDialog(null)==jF1.APPROVE_OPTION){ 
								rutaClasico = jF1.getSelectedFile().getAbsolutePath(); 
							if(new File(rutaClasico).exists()) 
							{ 
							if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog((Component) arg0.getSource(),"El fichero existe,deseas reemplazarlo?","Titulo",JOptionPane.YES_NO_OPTION)) {
								
							} else {
								return ;
							} 
							}
							}
							pC.salvar(rutaClasico);
							
							}catch (Exception ex){ 
							ex.printStackTrace(); 
							}
							
							}
							System.exit(0);
						}else {
											//SALVAR SALTOS//
						System.out.println("Salvar Saltos");
						
						if (rutaSaltos!=null) {
							p.salvar(rutaSaltos);
						}else{
						
						JFileChooser jF1= new JFileChooser(); 
						
						try{ 
						if(jF1.showSaveDialog(null)==jF1.APPROVE_OPTION){ 
							rutaSaltos = jF1.getSelectedFile().getAbsolutePath(); 
						if(new File(rutaSaltos).exists()) 
						{ 
						if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog((Component) arg0.getSource(),"El fichero existe,deseas reemplazarlo?","Titulo",JOptionPane.YES_NO_OPTION)) {
							
						} else {
							return ;
						} 
						}
						}
						p.salvar(rutaSaltos);
						
						}catch (Exception ex){ 
						ex.printStackTrace(); 
						}
						
						} 
					
						System.exit(0);
				}
					
				
				}
		        	
		        
		    }
	}
	
		
	}

public class EscuchadorFicheroEstadisticas implements ActionListener{
		
		public void actionPerformed (ActionEvent arg0) throws HeadlessException{
			
			
				
				
					String NuevarutaEstadisticas="" ;
					
					JFileChooser jF1= new JFileChooser(); 
					
					try{ 
					if(jF1.showSaveDialog(null)==jF1.APPROVE_OPTION){ 
						NuevarutaEstadisticas = jF1.getSelectedFile().getAbsolutePath(); 
					
					
					
					
					
					
					
					FileWriter fichero=null;
			        PrintWriter pw=null;
			        
			        FileWriter EscitorCopia=null;
			        PrintWriter wNuevo=null;
			        File archivo = null;
			        FileReader LectorOriginal = null;
			        BufferedReader rNuevo = null;
			        
			        archivo = new File (rutaEstadisticas);
			            try {
			            	
					       
								System.out.println("AUXILIAR");
			        	EscitorCopia = new FileWriter(NuevarutaEstadisticas);
			        	wNuevo = new PrintWriter(EscitorCopia);
			        	LectorOriginal = new FileReader (archivo);
			             rNuevo = new BufferedReader(LectorOriginal);
			        
			         
			             try {
			            	 String linea;
				             if(archivo.length()>0) {
				          
				            while((linea=rNuevo.readLine())!=null) {
				            	
				            	wNuevo.println(linea);
				            	
				            }   	  
				          
				 	        } 
				             LectorOriginal.close();
				             EscitorCopia.close();
				             
				   
				             
				     
				             try {
				             System.out.println("borrado");
				          
				             
				             Path yourFile = Paths.get(NuevarutaEstadisticas);
				             Files.move(yourFile, yourFile.resolveSibling(rutaEstadisticas),REPLACE_EXISTING);
				             }catch (Exception e) {
								System.out.println("No se pudo borrar");
							}
				             rNuevo.close();
				             wNuevo.close();
				             
			         
			             } catch (Exception e) {
				 	            e.printStackTrace();
			             }
			            }catch (Exception ex){ 
							ex.printStackTrace(); 
						}
			        }
					}catch (Exception e) {
			            e.printStackTrace();
			        } 
					
					}
		}

					
public class EscuchadorAyuda implements ActionListener{
	
	public void actionPerformed (ActionEvent arg0) {
		
		System.out.println("AYUDA");
		ImageIcon icon2=new ImageIcon(getClass().getResource("/OTRASIMAGENES/fondo2.jpg"));
		Image scaleImage = icon2.getImage().getScaledInstance(91, 137,Image.SCALE_DEFAULT);
		ImageIcon icon = new ImageIcon(scaleImage);
		if (actualPanel=="") {
			
			 JOptionPane.showMessageDialog(null,"                                                                                                           BIENVENIDO AL SOLITARIO \n\n"
			 		+ "Para empezar una partida, selecciona en Nuevo y el tipo de solitario que desea: \n\n"
			 		+ "        1-SOLITARIO CLASICO: Solitario Clasico con la baraja Inglesa, que consiste en conseguir que todas las cartas estén ordenadas por palos de menos a mayor \n"
			 		+ "        2-SOLITARIO SALTOS: Un solitario con cartas de la baraja española que consite en mover la maxima cantidad de cartas a la izquierda\n\n"
			 		+ "Si necesita más informacion sobre el funcionamiento de los solitarios, entra en uno y pulsa de nuevo el boton Ayuda \n"
			 		+ "\n\n                                                                               Que disfrute del Solitario. Autor Xián García Nogueira","Ayuda Menú",JOptionPane.PLAIN_MESSAGE,icon);
			
		}else if (actualPanel=="Clasico") {
			
			 JOptionPane.showMessageDialog(null,"                                                                                                           SOLITARIO CLASICO \n\n"
			 		+ "-El objetivo de este solitario es conseguir que todas las cartas acaben en la parte superior derecha, ordenadas por palo de menor a mayor. REGLAS:\n\n"
			 		+ "        1-Solo se puede extraer una vez todas las cartas de la baraja, ten cuidado de no desperdiciarlas \n"
			 		+ "        2-En la mesa se pueden colocar las cartas de forma descendente y siempre variando de color (rojo,negro) de las cartas \n"
			 		+ "        3-Cuando queda libre una posicion en la mesa, solo se pueden colocar Reyes(K) \n"
			 		+ "        4-En las 4 casillas de la parte superior derecha tiene que empezar desde los Ases y acabar con el K de su correspondiente palo, en orden ascendente \n\n"
			 		+ "Mucha suerte, que disfute! "
			 		+ "\n                                                                               Que disfrute del Solitario. Autor Xián García Nogueira","Ayuda Menú",JOptionPane.PLAIN_MESSAGE,icon);
			
		}else if (actualPanel=="Saltos") {
			 JOptionPane.showMessageDialog(null,"                                                                                                           SOLITARIO SALTOS \n\n"
				 		+ "-El objetivo de este solitario es conseguir que todas las cartas acaben en los menores 'montones' posibles, siguiendo las siguientes REGLAS:\n\n"
				 		+ "        1-Se empieza desde la parte superior izquierda y continuando por la derecha \n"
				 		+ "        2-Se ha de mover una carta, cuando coincida en palo o en numéro con su inmediata vecina a la izquierda, o case con la tercera carta a la izquierda \n        Siendo permitidos X-XY siendo Y la carta y X los posibles cambios\n"
				 		+ "        3-Si los 2 movimientos son posibles -1 y -3 se debería priorizar siempre el de 3 a la izquierda \n"
				 		+ "        4-El juego acaba cuando no quedan más movimientos posibles \n\n"
				 		+ "Dispone del boton DesHacer,Hacer,Resolver en la pestaña 'Editar' que ayudará antes posiblles errores o momentos de bloqueo\n\n"
				 		+ "Mucha suerte, que disfute! "
				 		+ "\n                                                                               Que disfrute del Solitario. Autor Xián García Nogueira","Ayuda Menú",JOptionPane.PLAIN_MESSAGE,icon);
		}
			
		
		       
		   
		      
	}
	

	

}
}
