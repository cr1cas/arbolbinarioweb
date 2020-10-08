/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolbinarioweb.controlador;

//import arbolbinario.modelo.ArbolBinario;
//import arbolbinario.modelo.Nodo;
import arbolbinario.modelo.excepciones.ArbolBinarioException;
import arbolbinarioweb.controlador.util.JsfUtil;
import avl.modelo.ArbolBinarioAVL;
import avl.modelo.NodoAVL;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;

/**
 *
 * @author carloaiza
 */
@Named(value = "avlControlador")
@SessionScoped
public class AVLControlador implements Serializable {

    private DefaultDiagramModel model;
    
    private DefaultDiagramModel modelArbol2;

    private ArbolBinarioAVL arbol = new ArbolBinarioAVL();
    private int dato;
    private int dato2;
    private boolean verInOrden = false;

    private boolean verPosOrden = false;
    private boolean verPorNiveles = false;
    private boolean verNivelOrdenado = false;
    
    private boolean verHojas = false;
    private boolean verPodar = false;
    private boolean verBalance = false;
    
    private boolean verRamaMayor = false;
    
    private String datoscsv = "5,6,7";//,-8,10,59,28,80,78,90";
    private int terminado;
    private ArbolBinarioAVL arbolTerminados = new ArbolBinarioAVL();

    public ArbolBinarioAVL getArbolTerminados() {
        return arbolTerminados;
    }

    public void setArbolTerminados(ArbolBinarioAVL arbolTerminados) {
        this.arbolTerminados = arbolTerminados;
    }

    public int getTerminado() {
        return terminado;
    }

    public void setTerminado(int terminado) {
        this.terminado = terminado;
    }

    
    public String getDatoscsv() {
        return datoscsv;
    }

    public void setDatoscsv(String datoscsv) {
        this.datoscsv = datoscsv;
    }

    public boolean isVerInOrden() {
        return verInOrden;
    }

    public void setVerInOrden(boolean verInOrden) {
        this.verInOrden = verInOrden;
    }

    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }

    public ArbolBinarioAVL getArbol() {
        return arbol;
    }

    public void setArbol(ArbolBinarioAVL arbol) {
        this.arbol = arbol;
    }

    public int getDato2() {
        return dato2;
    }

    public void setDato2(int dato2) {
        this.dato2 = dato2;
    }

    public boolean isVerPosOrden() {
        return verPosOrden;
    }

    public void setVerPosOrden(boolean verPosOrden) {
        this.verPosOrden = verPosOrden;
    }

    public boolean isVerPorNiveles() {
        return verPorNiveles;
    }

    public void setVerPorNiveles(boolean verPorNiveles) {
        this.verPorNiveles = verPorNiveles;
    }

    public boolean isVerNivelOrdenado() {
        return verNivelOrdenado;
    }

    public void setVerNivelOrdenado(boolean verNivelOrdenado) {
        this.verNivelOrdenado = verNivelOrdenado;
    }

    public boolean isVerHojas() {
        return verHojas;
    }

    public void setVerHojas(boolean verHojas) {
        this.verHojas = verHojas;
    }

    public boolean isVerPodar() {
        return verPodar;
    }

    public void setVerPodar(boolean verPodar) {
        this.verPodar = verPodar;
    }

    public boolean isVerBalance() {
        return verBalance;
    }

    public void setVerBalance(boolean verBalance) {
        this.verBalance = verBalance;
    }

    public boolean isVerRamaMayor() {
        return verRamaMayor;
    }

    public void setVerRamaMayor(boolean verRamaMayor) {
        this.verRamaMayor = verRamaMayor;
    }

    
    /**
     * Creates a new instance of ArbolBinarioControlador
     */
    public AVLControlador() {

    }

    public void adicionarNodo() {
        try {
            arbol.adicionarNodo(dato, arbol.getRaiz());
            JsfUtil.addSuccessMessage("El dato ha sido adicionado");
            dato = 0;
            pintarArbol();

        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }

    public void habilitarInOrden() {
        try {
            arbol.isLleno();
            verInOrden = true;
        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }

    public DefaultDiagramModel getModelArbol2() {
        return modelArbol2;
    }

    public void setModelArbol2(DefaultDiagramModel modelArbol2) {
        this.modelArbol2 = modelArbol2;
    }
    
    public DefaultDiagramModel getModel() {
        return model;
    }

    public void setModel(DefaultDiagramModel model) {
        this.model = model;
    }

    public void pintarArbol() {

        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);
        model.setConnectionsDetachable(false);
        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle("{strokeStyle:'#404a4e', lineWidth:2}");
        connector.setHoverPaintStyle("{strokeStyle:'#20282b'}");
        model.setDefaultConnector(connector);
        pintarArbol(arbol.getRaiz(), model, null, 30, 0);

    }

    private void pintarArbol(NodoAVL reco, DefaultDiagramModel model, Element padre, int x, int y) {

        if (reco != null) {
            Element elementHijo = new Element(reco);

            elementHijo.setX(String.valueOf(x) + "em");
            elementHijo.setY(String.valueOf(y) + "em");
            elementHijo.setStyleClass("ui-diagram-element-busc");
            if (padre != null) {
                elementHijo.addEndPoint(new DotEndPoint(EndPointAnchor.TOP));
                DotEndPoint conectorPadre = new DotEndPoint(EndPointAnchor.BOTTOM);
                padre.addEndPoint(conectorPadre);
                model.connect(new Connection(conectorPadre, elementHijo.getEndPoints().get(0)));

            }

            model.addElement(elementHijo);

            pintarArbol(reco.getIzquierda(), model, elementHijo, x - 5, y + 5);
            pintarArbol(reco.getDerecha(), model, elementHijo, x + 5, y + 5);
        }
    }

    public void extraerDatos() {
        try {
            arbol.setRaiz(null);
            arbol.llenarArbol(datoscsv);
            pintarArbol();
            datoscsv = "";
        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage("Los datos ingresados no tienen el formato separado por comas");
        }
    }

    
    public void balancear(){
        arbol.balancear(arbol.getRaiz());
        pintarArbol();
        
    }
    
    /**
     * @author Cristian Ospina
     * @author Cristian Castañeda Espitia
     * habilitarPosOrden sirve para mostrar los datos cuando se de click sobre el boton
     */
    public void habilitarPosOrden() {
        try {
            arbol.isLleno();
            verPosOrden = true;
        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }
    
    /**
     * @author Cristian Ospina
     * @author Cristian Castañeda Espitia
     * habilitarPosNiveles sirve para mostrar los datos cuando se de click sobre el boton
     */
    public void habilitarPorNiveles() {
        try {
            arbol.isLleno();
            verPorNiveles = true;
        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }
    
    
    /**
     * @author Cristian Ospina
     * @author Cristian Castañeda Espitia
     * habilitarNivelOrdenado sirve para mostrar los datos cuando se de click sobre el boton
     */
    public void habilitarNivelOrdenado() {
        try {
            arbol.isLleno();
            verNivelOrdenado = true;
        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }
    
    public void habilitarHojas() {
        try {
            arbol.isLleno();
            verHojas = true;
        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }
    
    public void habilitarPodar() {
        try {
            arbol.isLleno();
            verPodar = true;
        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }
    
    public void habilitarBalance() {
        try {
            arbol.isLleno();
            verBalance = true;
        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }
    
    
    public void habilitarRamaMayor() {
        try {
            arbol.isLleno();
            verRamaMayor = true;
        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }
    
    /**
     * @author Cristian Ospina
     * @author Cristian Castañeda Espitia
     * darHojas - metodo para mostrar las hojas del arbol pintado
     * @return 
     */
    public String darHojas() {
        ArrayList it = this.arbol.getHojas();
        return (recorrido(it, "Hojas del Arbol"));
    }
    
   

    public void buscarTerminadosEn() {
        for (Element ele : model.getElements()) {
            ele.setStyleClass("ui-diagram-element");
            int numTerm = Integer.parseInt(ele.getData().toString());
            if (numTerm < 0) {
                numTerm *= -1;
            }
            if (numTerm % 10 == terminado) {
                ele.setStyleClass("ui-diagram-element-busc");
            }
        }
    }

    public void encontrarTerminadosEn() {
        try {
            arbolTerminados = new ArbolBinarioAVL();
            encontrarTerminadosEn(arbol.getRaiz());
            pintarArbolTerminados();
        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage("Ocurrio un error generando el árbol de terminados" + ex);
        }
    }

    private void encontrarTerminadosEn(NodoAVL reco) throws ArbolBinarioException {
        if (reco != null) {
            int numTerm= reco.getDato();
            if(numTerm<0)
            {
                numTerm *=-1;
            }
            if(numTerm%10==terminado)
            {
                arbolTerminados.adicionarNodo(reco.getDato(), arbolTerminados.getRaiz());
            }
            encontrarTerminadosEn(reco.getIzquierda());
            encontrarTerminadosEn(reco.getDerecha());
        }
    }

    public void pintarArbolTerminados() {

        modelArbol2 = new DefaultDiagramModel();
        modelArbol2.setMaxConnections(-1);
        modelArbol2.setConnectionsDetachable(false);
        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle("{strokeStyle:'#404a4e', lineWidth:2}");
        connector.setHoverPaintStyle("{strokeStyle:'#20282b'}");
        modelArbol2.setDefaultConnector(connector);
        pintarArbolTerminados(arbolTerminados.getRaiz(), modelArbol2, null, 30, 0);

    }

    private void pintarArbolTerminados(NodoAVL reco, DefaultDiagramModel model, Element padre, int x, int y) {

        if (reco != null) {
            Element elementHijo = new Element(reco.getDato());

            elementHijo.setX(String.valueOf(x) + "em");
            elementHijo.setY(String.valueOf(y) + "em");

            if (padre != null) {
                elementHijo.addEndPoint(new DotEndPoint(EndPointAnchor.TOP));
                DotEndPoint conectorPadre = new DotEndPoint(EndPointAnchor.BOTTOM);
                padre.addEndPoint(conectorPadre);
                model.connect(new Connection(conectorPadre, elementHijo.getEndPoints().get(0)));

            }

            model.addElement(elementHijo);

            pintarArbolTerminados(reco.getIzquierda(), model, elementHijo, x - 5, y + 5);
            pintarArbolTerminados(reco.getDerecha(), model, elementHijo, x + 5, y + 5);
        }
    }
    
    /**
     * author Cristian Ospina
     * @author Cristian Castañeda Espitia
     * metodo para imprimir los nodos e identificar el nivel de cada nodo
     * @return - retorno un arreglo con los nodos
     */
    public String imprimirPorNiveles() {
        ArrayList it = this.arbol.impNiveles();
        return (recorrido(it, "Imprimir Por niveles"));
    }
    
    
    private String recorrido(ArrayList it, String msg) {
        int i = 0;
        String r = msg + "\n";
        while (i < it.size()) {
            r += "\t" + it.get(i).toString() + "\n";
            i++;
        }
        return (r);
    }
    
    
    
    //Nivel Ordenado
    public String porNivel(){
        this.arbol.alturaArbol();
        ArrayList it = this.arbol.imprimirNivel();
        return (recorrido(it, "Imprimir Por niveles en orden!!!"));
    }

    //Buscar Nodo en el Árbol
     public String esta(Integer dato) {
        boolean siEsta = this.arbol.buscar(dato2);
        String r = "El dato:" + dato.toString() + "\n";
        r += siEsta ? "Si se encuentra en el arbol" : "No se encuentra en el arbol";
        return (r);
    }
     
      public void esta() {
        try {
            arbol.adicionarNodo(dato, arbol.getRaiz());
            
            JsfUtil.addSuccessMessage("El dato ha sido adicionado");
            dato2 = 0;
            pintarArbol();

        } catch (ArbolBinarioException ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }
     
      public void podar(){
          arbol.podar();
          pintarArbol();
      }
      
      public void borrarMayor(){
          arbol.borrarMayor();
          pintarArbol();
      }
      
      public void borrarMenor(){
          arbol.borrarMenor();
          pintarArbol();
      }
      
      
//      //eliminar nivel
//      public void eliminarNivel(){
//          arbol.eliminarNivel();
//          pintarArbol();
//      }
      
          
      //multiplicar valores
      public void multiplicarValores(){
          arbol.multiplicarValores();
          pintarArbol();
      }
    
}

