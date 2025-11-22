// Caso de Estudio: Sistema de Gestión de Inventario con Árbol Binario

// Contexto: 
/*
Una empresa desea implementar un módulo interno para gestionar el inventario de productos. 
Debido a que la cantidad de artículos crece constantemente y se requiere una búsqueda rápida, 
el equipo de desarrollo decide usar una estructura de datos tipo Árbol Binario de Búsqueda (ABB).
 */


// Objetivo:
/*
Cada producto se representará como un nodo, donde:
key → código numérico del producto
name → nombre del producto
stock → cantidad disponible
*/

// PARTE 4: BORRAR NODOS
/*
El sistema debe soportar los tres casos clásicos al eliminar un nodo:

1. Nodo sin hijos (hoja)
   - Se elimina directamente

2. Nodo con un hijo
   - El hijo sustituye al nodo eliminado

3. Nodo con dos hijos
   - Usar el sucesor inorden (nodo más pequeño del subárbol derecho)
   - Reemplazar el valor del nodo con el sucesor
   - Eliminar el sucesor
*/

// NOTA: Las clases Producto y Nodo deben ser proporcionadas por la Parte 1
// NOTA: El método insertar() debe ser proporcionado por la Parte 2

public class borrarNodo {
    
    // Método público para iniciar la búsqueda de un nodo a eliminar
    public static Nodo eliminarProducto(Nodo raiz, int codigo) {
        if (raiz == null) {
            System.out.println("El árbol está vacío.");
            return null;
        }
        
        return eliminarNodo(raiz, codigo);
    }
    
    // Método recursivo privado para eliminar un nodo
    private static Nodo eliminarNodo(Nodo nodo, int codigo) {
        if (nodo == null) {
            System.out.println("Producto con código " + codigo + " no encontrado.");
            return null;
        }
        
        // Buscar el nodo a eliminar usando la propiedad del ABB
        if (codigo < nodo.producto.codigo) {
            // El código está en el subárbol izquierdo
            nodo.izquierda = eliminarNodo(nodo.izquierda, codigo);
        } else if (codigo > nodo.producto.codigo) {
            // El código está en el subárbol derecho
            nodo.derecha = eliminarNodo(nodo.derecha, codigo);
        } else {
            // Se encontró el nodo a eliminar
            System.out.println("Eliminando: " + nodo.producto);
            
            // CASO 1: Nodo sin hijos (hoja)
            if (nodo.izquierda == null && nodo.derecha == null) {
                return null; // Se elimina directamente
            }
            
            // CASO 2: Nodo con un solo hijo (derecho)
            if (nodo.izquierda == null) {
                return nodo.derecha; // El hijo derecho sustituye al nodo
            }
            
            // CASO 2: Nodo con un solo hijo (izquierdo)
            if (nodo.derecha == null) {
                return nodo.izquierda; // El hijo izquierdo sustituye al nodo
            }
            
            // CASO 3: Nodo con dos hijos
            // Encontrar el sucesor inorden (nodo más pequeño del subárbol derecho)
            Nodo sucesor = encontrarMinimoNodo(nodo.derecha);
            
            // Reemplazar el producto del nodo actual con el del sucesor
            nodo.producto = sucesor.producto;
            
            // Eliminar el sucesor del subárbol derecho
            nodo.derecha = eliminarNodo(nodo.derecha, sucesor.producto.codigo);
        }
        
        return nodo;
    }
    
    // Método auxiliar para encontrar el nodo con el código más pequeño
    // (usado para hallar el sucesor inorden)
    private static Nodo encontrarMinimoNodo(Nodo nodo) {
        while (nodo.izquierda != null) {
            nodo = nodo.izquierda;
        }
        return nodo;
    }
}
