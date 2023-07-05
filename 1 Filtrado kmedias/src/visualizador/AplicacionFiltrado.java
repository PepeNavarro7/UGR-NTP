package visualizador;

import base.convergencia.*;
import base.imagen.Imagen;
import base.imagen.Utilidades;
import base.inicializacion.ModoInicializacion;
import kmedias.KMedias;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * clase para proporcionar la interfaz de la aplicacion
 * de filtrado de imagenes
 */
public class AplicacionFiltrado extends JFrame{
   /**
    * ancho predeterminado del panel
    */
   private final static int anchoPanel = 370;

   /**
    * alto predeterminado del panel
    */
   private final static int altoPanel = 420;

   /**
    * alto predeterminado de la ventana de texto
    */
   private final static int altoTexto = 370;

   /**
    * ubicacion de la imagen en el panel
    */
   private CanvasImagen canvas = null;

   /**
    * boton de seleccion de inicializacion aleatoria
    */
   private JRadioButton muestreoAleatorio = null;

   /**
    * boton de seleccion de inicializacion mediante
    * muestreo uniforme
    */
   private JRadioButton muestreoUniforme = null;

   /**
    * boton de seleccion de inicializacion mediante seleccion
    * uniforme en el rango de colores
    */
   private JRadioButton seleccionUniforme = null;

   /**
    * boton de seleccion del criterio de parada por
    * numero de iteraciones
    */
   private JRadioButton iteraciones = null;

   /**
    * boton de seleccion del criterio de parada por
    * estabilidad
    */
   private JRadioButton estabilidad = null;

   /**
    * boton de seleccion del criterio de parada por
    * nivel de ruido
    */
   private JRadioButton ruido = null;

   /**
    * campo para indicar el numero de colores a seleccionar
    */
   private JSpinner numeroColores = null;

   /**
    * campo para indicar el maximo numero permitido de iteraciones
    */
   private JSpinner maxIteraciones = null;

   /**
    * campo para indicar el umbral de estabilidad
    */
   private JSpinner umbralEstabilidad = null;

   /**
    * campo para indicar el umbral de ruido
    */
   private JSpinner umbralRuido = null;

   /**
    * campo de texto para mostrar informacion sobre la aplicacion
    * del ruido
    */
   private JTextArea info = null;

   /**
    * constructor del marco de aplicacion
    */
   public AplicacionFiltrado(){
      // se asigna el título
      setTitle("Aplicación de K-means para filtrado de colores");

      // se asigna operacion de cierre por defecto
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      // se asigna el layout
      setLayout(new BorderLayout());

      // se crea panel a la derecha para botones de seleccion
      // de opciones ejecucion, botones de ejecucion (panel de
      // controles) y texto con informacion del proceso
      JPanel panelDerecho = crearPanelOpciones();
      
      // se agrega al marco
      add(panelDerecho, BorderLayout.EAST);

      // se crean la opciones del menu
      JMenuBar barra = crearMenuOpciones();

      // se agrega la barra
      setJMenuBar(barra);
      
      // se crea canvas
      canvas = new CanvasImagen();

      // se informa de las propiedades de la imagen
      informarTexto();

      // se integra en el panel general mediante un panel con
      // barra deslizadora
      JScrollPane panelBarra = new JScrollPane(canvas);

      // se agrega al panel general, en el centro
      add(panelBarra, BorderLayout.CENTER);

      // se produce el dimensionado del marco
      dimensionar();

      // se hace visible
      setVisible(true);
   }

   /**
    * metodo de creacion del panel de opciones. Consta de un panel
    * al que se adjuntan dos paneles adicionales: uno con los
    * controles de seleccion de acciones y otro con la ventana
    * en que se muestra la informacion asociada
    * @return panel con controles para acciones
    */
   private JPanel crearPanelOpciones(){
      // se crea panel a la derecha para botones de
      // opciones
      JPanel panelDerecho = new JPanel();

      // se asigna el borde
      panelDerecho.setBorder(BorderFactory.createEtchedBorder(
              EtchedBorder.LOWERED));

      // se asigna gestor de posicionado
      panelDerecho.setLayout(new BorderLayout());

      // se crean los controles para las opciones y se agregan
      // al panel
      JPanel panelControles = crearControles();
      panelDerecho.add(panelControles, BorderLayout.NORTH);

      // se obtiene el tamaño del panel de controles
      Dimension dimensionPanel = panelControles.getPreferredSize();

      // se crea la zona con informacion sobre la ejecucion
      JPanel infoPanel = new JPanel();

      info = new JTextArea();
      JScrollPane scroll = new JScrollPane(info);
      scroll.setPreferredSize(new Dimension(anchoPanel - 10, altoTexto - 100));
      scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

      // se agregan ambos elementos al panel
      //infoPanel.add(info);
      infoPanel.add(scroll);

      // se agrega al panel
      panelDerecho.add(infoPanel, BorderLayout.SOUTH);

      // se devuelve el panel
      return panelDerecho;
   }

   /**
    * metodo para creacion del panel general de parametrizacion del
    * algoritmo de filtrado
    * @return panel de controles
    */
   private JPanel crearControles(){
      // creacion del panel
      JPanel panelControles = new JPanel();

      // se asigna gestor de posicionado: GridBagLayout. El contenido
      // del mismo sera:
      // |----------------------------------
      // | numero de colores | spinner seleccion |
      // |                 separador             |
      // |           modo de sel. de colores     |
      // | muestreo aletaorio |                  |
      // | muestreo uniforme  |                  |
      // | seleccion uniforme |                  |
      // |                 separador             |
      // |           criterio de parada          |
      // | iteraciones       | spinner           |
      // | estabildad        | spinner           |
      // | relacion ruido    | spinner           |
      // |                 separador             |
      // | boton aplicar     | boton recargar    |
      //
      panelControles.setLayout(new GridBagLayout());
      //panelControles.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));

      // se crea el control para el numero de colores deseados
      crearPanelColores(panelControles);

      // se crea un separador
      crearSeparador(panelControles, 1, 0, 2);

      // se crea el panel para la seleccion de forma de inicializacion
      // (a partir de la fila 2)
      crearPanelInicializacion(panelControles, 2);

      // se crea un separador
      crearSeparador(panelControles, 6, 0, 2);

      // se crea el panal para seleccion del modo de convergencia
      // los elementos ocupan a partir de la fila 7
      crearPanelConvergencia(panelControles, 7);

      // se crea un separador
      crearSeparador(panelControles, 11, 0, 2);

      // se crean los botones de accion
      crearPanelAccion(panelControles, 12);

      // se devuelve el panel
      return panelControles;
   }

   /**
    * metodo para creacion del panel de control del
    * numero de colores a seleccionar
    * @param panel panel donde se agregan los controles
    */
   private void crearPanelColores(JPanel panel){
      // se crea la etiqueta
      JLabel etiqueta = new JLabel("Número de colores");

      // se agrega al panel
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.VERTICAL;
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(10, 15, 0, 0);
      panel.add(etiqueta, c);

      // se crea el elemento de seleccion del valor
      numeroColores = new JSpinner(new SpinnerNumberModel(2, 2, 500, 1));

      // se agrega al panel
      c = new GridBagConstraints();
      c.fill = GridBagConstraints.VERTICAL;
      c.gridx = 1;
      c.gridy = 0;
      c.insets = new Insets(10, 15, 0, 0);
      panel.add(numeroColores, c);

      // se fila la dimension del campo de texto
      numeroColores.setPreferredSize(new Dimension(100, 30));
   }

   /**
    * metodo de creacion del panel donde se ubican los controles
    * para seleccionar el modo de inicializacion deseado
    * @param panel panel donde se agregan los controles
    * @param filaInicio fila inicial
    */
   private void crearPanelInicializacion(JPanel panel, int filaInicio){
      // se crea borde para el panel
      JLabel etiqueta = new JLabel("Modo de seleccion de colores");

      // se agrega la etiqueta al panel: en fila 2, columna 0
      // pero ocupando las dos columnas
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.VERTICAL;
      c.gridx = 0;
      c.gridy = filaInicio;
      c.gridwidth = 2;
      c.insets = new Insets(10, 0, 0, 0);
      panel.add(etiqueta, c);

      // crea grupo de botones
      ButtonGroup botones = new ButtonGroup();

      // creacion de los botones de opciones
      int fila = filaInicio + 1;
      for(ModoInicializacion modo : ModoInicializacion.values()){
         // creacion del boton
         JRadioButton boton = null;
         switch (modo){
            case MUESTREO_ALEATORIO :
                boton = new JRadioButton("Muestreo aleatorio");
                boton.setSelected(true);
                muestreoAleatorio = boton;
                break;
            case MUESTREO_UNIFORME:
               boton = new JRadioButton("Muestreo uniforme");
               muestreoUniforme = boton;
               break;
            case SELECCION_UNIFORME:
               boton = new JRadioButton("Seleccion uniforme");
               seleccionUniforme = boton;
               break;
         }

         // se agrega al grupo
         botones.add(boton);

         // se agrega al panel: en las filas 3, 4 y 5 y
         // columna 0
         GridBagConstraints bc = new GridBagConstraints();
         bc.fill = GridBagConstraints.HORIZONTAL;
         bc.gridx = 0;
         bc.gridy = fila;
         bc.insets = new Insets(10, 30, 0, 0);
         panel.add(boton, bc);

         // se incrementa el contador de fila
         fila = fila+1;
      }
   }

   /**
    * metodo de creacion del panel con las opciones de convergencia
    * @param panel panel donde se agregan los controles
    * @param filaInicio fila inicial
    */
   private void crearPanelConvergencia(JPanel panel, int filaInicio){

      // se crea la etiqueta
      JLabel etiqueta = new JLabel("Criterio de convergencia");

      // se agrega la etiqueta al panel: fila 7, columna 0,
      // ocupando dos columnas
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.VERTICAL;
      c.gridx = 0;
      c.gridy = filaInicio;
      c.gridwidth = 2;
      c.insets = new Insets(10, 0, 0, 0);
      panel.add(etiqueta, c);

      // se crea grupo de botones
      ButtonGroup botones = new ButtonGroup();

      int fila = filaInicio + 1;

      // creacion de los botones de opciones
      for(ModoConvergencia modo : ModoConvergencia.values()){

         // se crea el correspondiente selector de valores
         JSpinner selector = null;
         JRadioButton boton = null;
         switch (modo){
            case ITERACIONES :
               boton = new JRadioButton("Máx. iteraciones");
               boton.setSelected(true);
               iteraciones = boton;
               selector = new JSpinner(new SpinnerNumberModel(10, 1, 50, 1));
               maxIteraciones = selector;
               break;
            case ESTABILIDAD:
               boton = new JRadioButton("Estabilidad (/1000)");
               estabilidad = boton;
               selector = new JSpinner(new SpinnerNumberModel(10.0,
                       1, 100.0, 1.0));
               umbralEstabilidad = selector;
               break;
            case RUIDO:
               boton = new JRadioButton("Ruido");
               ruido = boton;
               selector = new JSpinner(new SpinnerNumberModel(40, 10, 80, 1));
               umbralRuido = selector;
               break;
         }

         // se agrega el boton al grupo
         botones.add(boton);

         // se agregan al panel el boton y el selection
         GridBagConstraints bc = new GridBagConstraints();
         bc.fill = GridBagConstraints.HORIZONTAL;
         bc.gridx = 0;
         bc.gridy = fila;
         bc.insets = new Insets(5, 30, 0, 0);
         panel.add(boton, bc);

         // se agrega el selector
         bc = new GridBagConstraints();
         bc.fill = GridBagConstraints.VERTICAL;
         bc.gridx = 1;
         bc.gridy = fila;
         bc.insets = new Insets(5, 15, 0, 0);
         panel.add(selector, bc);
         selector.setPreferredSize(new Dimension(100, 30));

         // se incrementa el contador de fila
         fila++;
      }
   }

   /**
    * metodo de creacion del panel con los botones de accion
    * @param panel panel donde se agregan los controles
    * @param fila fila inicial
    */
   private void crearPanelAccion(JPanel panel, int fila){

      // se crea el boton de compresion
      JButton compresion = new JButton("Aplicar");
      compresion.setPreferredSize(new Dimension(50, 50));

      // se posiciona en el panel
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = fila;
      c.insets = new Insets(5, 10, 0, 0);
      panel.add(compresion, c);

      // se le asigna la funcion de accion asociada
      compresion.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            ejecutarFiltrado();
         }
      });


      // se agrega el boton de recarga de imagen
      JButton recarga = new JButton("Recargar");
      recarga.setPreferredSize(new Dimension(140, 50));

      // se posiciona
      c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.gridy = fila;
      c.insets = new Insets(5, 0, 0, 10);
      panel.add(recarga, c);

      // asigna la funcion de accion
      recarga.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            ejecutarRecarga();
         }
      });
   }

   /**
    * creacion del menu de opciones
    * @return menu creado
    */
   private JMenuBar crearMenuOpciones(){
      // se crea lka barra de menu
      JMenuBar barra = new JMenuBar();

      // se crea menu Archivo
      JMenu menuArchivo = new JMenu("Archivo");

      // se crean los elementos
      JMenuItem abrir = new JMenuItem("Abrir....");

      // se agrega la accion asociada
      abrir.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // se crea el selector de archivos
            JFileChooser selector = new JFileChooser("./data");
            if(selector.showOpenDialog(AplicacionFiltrado.this) == JFileChooser.APPROVE_OPTION){
               cargarArchivo(selector.getSelectedFile().getPath());
            }
         }
      });

      // se agrega al menu
      menuArchivo.add(abrir);

      // se crea la opcion de salvar
      JMenuItem salvar = new JMenuItem("Salvar....");

      // se agrega la opcion asociada
      salvar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JFileChooser selector = new JFileChooser("./data");
            if(selector.showSaveDialog(AplicacionFiltrado.this) == JFileChooser.APPROVE_OPTION){
               salvarArchivo(selector.getSelectedFile().getPath());
            }
         }
      });

      // se agrega la opcion al menu
      menuArchivo.add(salvar);

      // se crea la opcion de salir
      JMenuItem salir = new JMenuItem(("Salir...."));

      // se agrega la accion asociada
      salir.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            System.exit(0);
         }
      });

      // se agrega la opcion de salir
      menuArchivo.add(salir);

      // se agrega el menu de archivo a la barra
      barra.add(menuArchivo);

      // se devuelve la barra
      return barra;
   }

   /**
    * metodo de creacion de separador
    * @param panel panel donde se agrega el separador
    * @param fila fila de posicionado
    * @param columna columna de posicionado
    * @param ancho ancho del separador
    */
   private void crearSeparador(JPanel panel, int fila, int columna, int ancho){
      // se agrega separador
      JLabel separador1 = new JLabel("");

      // se agrega al panel
      separador1.setBorder(BorderFactory.createLineBorder(Color.red, 1));
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = columna;
      c.gridy = fila;
      c.gridwidth = ancho;
      c.insets = new Insets(10, 0, 0, 0);
      panel.add(separador1, c);
   }

   /**
    * metodo para ejecutar el filtrado
    */
   private void ejecutarFiltrado(){
      double tiempo = System.currentTimeMillis();
      // se llama al metodo factoria para crear los objetos necesarios
      KMedias kmedias = KMedias.factoria(obtenerContadorColores(), obtenerModoInicializacion(),
              obtenerModoConvergencia(), obtenerMaximoIteraciones(),
              obtenerUmbralEstabilidad(), obtenerUmbralRuido(),
              canvas.obtenerImagen());

      // se informa sobre los criterios usados
      informarTexto(obtenerModoInicializacion(), obtenerModoConvergencia());

      // se llama al metodo ejecutar
      Imagen filtrada = kmedias.ejecutar();
      canvas.asignarImagen(filtrada);
      tiempo = System.currentTimeMillis() - tiempo;

      // se muestra informacion por la ventana de texto
      // se obtiene el numero de iteraciones usadas del objeto kmedias
      informarTexto(tiempo, kmedias.obtenerIteraciones());
   }

   /**
    * metodo para determinar el modo de inicializacion deseado
    * @return modo de inicializacion seleccionado
    */
   private ModoInicializacion obtenerModoInicializacion(){
      ModoInicializacion modo = null;
      if(muestreoAleatorio.isSelected()){
         modo = ModoInicializacion.MUESTREO_ALEATORIO;
      }
      else{
         if(muestreoUniforme.isSelected()){
            modo = ModoInicializacion.MUESTREO_UNIFORME;
         }
         else{
            if(seleccionUniforme.isSelected()) {
               modo = ModoInicializacion.SELECCION_UNIFORME;
            }
         }
      }

      // se devuelve la estrategia seleccionada
      return modo;
   }

   /**
    * metodo para determinar el modo de convergencia seleccionado
    * @return modo de convergencia seleccionado
    */
   private ModoConvergencia obtenerModoConvergencia(){
      ModoConvergencia modo = null;
      if(iteraciones.isSelected()){
         modo = ModoConvergencia.ITERACIONES;
      }
      else{
         if(estabilidad.isSelected()){
            modo = ModoConvergencia.ESTABILIDAD;
         }
         else{
            if(ruido.isSelected()) {
               modo = ModoConvergencia.RUIDO;
            }
         }
      }

      return modo;
   }

   /**
    * metodo de acceso al numero de colores seleccionado
    * @return contador seleccionado de colores
    */
   private int obtenerContadorColores(){
      return Integer.parseInt(numeroColores.getValue().toString());
   }

   /**
    * metodo de acceso al maximo de iteraciones
    * @return maximo numero de iteraciones seleccionado
    */
   private int obtenerMaximoIteraciones() {
      return Integer.parseInt(maxIteraciones.getValue().toString());
   }

   /**
    * metodo de acceso al umbral de estabilidad
    * @return umbral para parada por estabilidad
    */
   private double obtenerUmbralEstabilidad(){
      return Double.parseDouble(umbralEstabilidad.getValue().toString())/1000.0;
   }

   /**
    * metodo de acceso al umbral de ruido
    * @return umbral para parada por ruido
    */
   private double obtenerUmbralRuido(){
      return Double.parseDouble(umbralRuido.getValue().toString());
   }

   /**
    * metodo de recarga de la imagen abierta mediante el menu o
    * cargada inicialmente
    */
   private void ejecutarRecarga(){
      if(canvas != null){
         canvas.cargarFichero(canvas.obtenerRuta());

         // se vuelve a dimensionar
         dimensionar();
      }
   }

   /**
    * metodo para cargar archivo
    * @param nombre nombre del archivo a cargar
    */
   private void cargarArchivo(String nombre){
      canvas.cargarFichero(nombre);

      // se vuelve a dimensionar
      dimensionar();

      // se muestra informacion en la ventana de texto
      informarTexto();
   }

   /**
    * metodo para salvar archivo
    * @param nombre nombre del archivo a salvar
    */
   private void salvarArchivo(String nombre){
      Utilidades.salvarImagen(canvas.obtenerImagen(), nombre);
      Utilidades.salvarFicheroCSV(canvas.obtenerImagen(), "./data/procesado.csv");
   }

   /**
    * metodo de dimensionado de la ventana de la aplicacion
    */
   private void dimensionar(){
      // se obtiene la dimension del canvas con la
      // imagen
      Dimension dimension = canvas.getPreferredSize();

      // se asigna la dimension con la suma de los
      // anchos y el maximo de los altos
      int ancho = (int)dimension.getWidth() + anchoPanel + 10;
      int alto = altoPanel + altoTexto;
      if(dimension.getHeight() > (altoPanel + altoTexto)){
         alto = (int)dimension.getHeight() + altoTexto + 60;
      }

      // se asigna el tamaño final
      setSize(new Dimension(ancho, alto));
   }

   /**
    * metodo para mostrar informacion por la ventana de texto
    */
   private void informarTexto(){
      // se obtiene el nombre del archivo
      String nombreImagen = canvas.obtenerRuta();

      // se elimina la informacion de la ruta
      int posUltimoSeparador = nombreImagen.lastIndexOf(File.separator);
      String nombreSolo = nombreImagen.substring(posUltimoSeparador + 1);
      long numeroColores = canvas.obtenerNumeroColores();
      int alto = canvas.obtenerAlto();
      int ancho = canvas.obtenerAncho();

      info.append("-----------------------\n");
      info.append(nombreSolo + "\n");
      info.append("numero de colores: " + numeroColores + "\n");
      info.append("ancho: " + ancho + " alto: " + alto + "\n");
   }

   /**
    * metodo para mostrar informacion por la ventana de texto
    * @param tiempo tiempo de ejecucion
    * @param iteraciones iteraciones usadas
    */
   private void informarTexto(double tiempo, int iteraciones){
      // se muestra informacion de tiempo y numero de iteraciones
      info.append("**************************************\n");
      info.append("tiempo de ejecucion: " + tiempo + "\n");
      info.append("numero de iteraciones: " + iteraciones + "\n");
   }

   private void informarTexto(ModoInicializacion modoInicializacion,
                              ModoConvergencia modoConvergencia){
      info.append("Inicializacion: " + modoInicializacion.toString() + "\n");
      info.append("Convergencia: " + modoConvergencia.toString() + "\n");
      switch(modoConvergencia){
         case ITERACIONES :
            info.append("max. iteraciones: " + obtenerMaximoIteraciones() + "\n");
            break;
         case ESTABILIDAD:
            info.append("estabilidad: " + obtenerUmbralEstabilidad() + "\n");
            break;
         case RUIDO:
            info.append("ruido: " + obtenerUmbralRuido() + "\n");
            break;
      }
   }

   /**
    * metodo main para lanzar la aplicacion
    * @param args argumentos del programa principal
    */
   public static void main(String args[]){
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }catch(Exception e){
         System.out.println("error fijando estilo a interfaz");
         System.out.println(e);
      }
      AplicacionFiltrado marco = new AplicacionFiltrado();
      marco.repaint();
   }
}
