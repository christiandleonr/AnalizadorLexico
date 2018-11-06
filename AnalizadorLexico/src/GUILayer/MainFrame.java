package GUILayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class MainFrame extends JFrame implements ActionListener {
    // Menu
    private JMenuBar menuApp;
    private JMenu opciones;
    private JMenuItem salir;
    // Imagenes
    private JLabel imgPrincipal;
    // Entradas
    private JTextField textField;
    private JTextArea textArea;
    // Paneles
    private JPanel panelSur, panelEste, panelOeste, panelCentro;
    // Etiquetas
    private JLabel resultado, ingreseP;
    // Botones
    private JButton botonAnalizar, botonAgregar;
    // Scroll View
    private JScrollPane scrollPane;
    // Vector

    private Vector <String> errores = new Vector<>();
    // Alfabeto
    private String alfabeto[] = {"","",""};
    // String
    private String cadena,palabras[],mensaje;
    // Boleeans
    private boolean error;
    // Integer
    private int bandera;
    // Datos para el analizador


    public MainFrame(String titulo)
    {
        super(titulo);
        setSize(800,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents()
    {
        menu();
        zonaCentro();
        zonaNorte();
        zonaSur();
        zonaEste();
        zonaOeste();
    }
    private void menu(){
        menuApp = new JMenuBar();

        // Menu Opciones
        opciones = new JMenu("Opciones");
        opciones.setMnemonic('O');
        opciones.setToolTipText("Menu de opciones");

        salir = new JMenuItem("Salir");
        salir.addActionListener(this);
        salir.setMnemonic('S');
        salir.setToolTipText("Salir de la aplicacion");
        opciones.add(salir);

        menuApp.add(opciones);

        // Cambiar el menu del frame
        setJMenuBar(menuApp);
    }
    private void zonaCentro(){
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setBounds(new Rectangle(30,30,100,200));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane,BorderLayout.CENTER);
    }
    private void zonaNorte(){
        imgPrincipal = new JLabel(new ImageIcon("imgPrincipal.png"));
        add(imgPrincipal,BorderLayout.NORTH);
    }
    private void zonaSur(){
        panelSur = new JPanel();
        panelSur.setLayout(new BoxLayout(panelSur, BoxLayout.Y_AXIS));

        resultado = new JLabel();

        panelSur.add(resultado);
        add(panelSur,BorderLayout.SOUTH);
    }
    private void zonaEste(){
        panelEste = new JPanel();
        panelEste.setLayout(new BoxLayout(panelEste,BoxLayout.Y_AXIS));

        ingreseP = new JLabel("Agregue al alfabeto");
        panelEste.add(ingreseP);

        textField = new JTextField();
        panelEste.add(textField);

        botonAgregar = new JButton("Agregar");
        botonAgregar.addActionListener(this);
        panelEste.add(botonAgregar);

        add(panelEste,BorderLayout.EAST);
    }
    private void zonaOeste(){
        panelOeste = new JPanel();

        botonAnalizar = new JButton("Analizar");
        botonAnalizar.addActionListener(this);
        panelOeste.add(botonAnalizar);

        add(panelOeste,BorderLayout.WEST);
    }
    private void agregarA(){
        //alfabeto.add(textField.getText());
        textField.setText("");
    }
    private boolean analisisLexico(){
        cadena = textArea.getText();
        cadena = cadena.replaceAll("\n",",");
        cadena = cadena.replaceAll(" ",",");
        palabras = cadena.split(",");

        bandera = 0;
        error = false;
        errores.removeAllElements();
        for(int i = 0; i<palabras.length; i++){
            if((!palabras[i].matches("[a-zA-Z].*") &&
               !palabras[i].matches("[0-99].") &&
               !palabras[i].matches("[>,<,>=,<=,==,!=].*") &&
               !palabras[i].matches("[boleean,char,int,float].*") &&
               !palabras[i].matches("[class,if,else].*") &&
               !palabras[i].matches("[{,},(,),;,,].*")) ||
               palabras[i].matches(".*[@#!&|].*") )
            {

                errores.add((i+1) + " " +palabras[i]);
                error = true;
            }
        }
        mensaje = "!Error¡ los siguientes token no se aceptan : ";
        if(errores.size() != 0){
            for(int i =0; i<errores.size(); i++){
                mensaje = mensaje+" "+errores.get(i);
            }
            resultado.setText(mensaje);
            resultado.setForeground(Color.RED);
        }
        return error;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == salir)
            System.exit(NORMAL);

        if(e.getSource() == botonAnalizar){
            if(!analisisLexico()){
                resultado.setText("Analisis lexico terminado, no se encontraron fallas");
                resultado.setForeground(Color.GREEN);
            }
        }

        if(e.getSource() == botonAgregar){
            agregarA();
        }
    }
}
