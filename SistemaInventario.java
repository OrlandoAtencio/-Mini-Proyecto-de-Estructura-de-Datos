import java.io.*;

// Clase Nodo: representa un producto en el inventario
class Nodo {
    int key;           
    String name;       
    int stock;       
    Nodo left;       
    Nodo right;        

    // Constructor
    public Nodo(int key, String name, int stock) {
        this.key = key;
        this.name = name;
        this.stock = stock;
        this.left = null;
        this.right = null;
    }

    // Método para mostrar información del nodo
    public void mostrarInfo() {
        System.out.println("Código: " + key + " | Producto: " + name + " | Stock: " + stock);
    }
}

// Clase ArbolInventario
class ArbolInventario {
    Nodo raiz;  

    // Constructor
    public ArbolInventario() {
        this.raiz = null;
    }

    // 1. CREAR NODO
    public Nodo crearNodo(int key, String name, int stock) {
        return new Nodo(key, name, stock);
    }

    // 2. INSERTAR NODO
    public void insertarNodo(int key, String name, int stock) {
        Nodo nuevoNodo = crearNodo(key, name, stock);
        if (raiz == null) {
            raiz = nuevoNodo;
            System.out.println("Producto insertado como raíz");
        } else {
            insertarRecursivo(raiz, nuevoNodo);
        }
    }

    // Método auxiliar para insertar recursivamente
    private void insertarRecursivo(Nodo actual, Nodo nuevoNodo) {
        if (nuevoNodo.key < actual.key) {
            if (actual.left == null) {
                actual.left = nuevoNodo;
                System.out.println("Producto insertado exitosamente");
            } else {
                insertarRecursivo(actual.left, nuevoNodo);
            }
        } else if (nuevoNodo.key > actual.key) {
            if (actual.right == null) {
                actual.right = nuevoNodo;
                System.out.println("Producto insertado exitosamente");
            } else {
                insertarRecursivo(actual.right, nuevoNodo);
            }
        } else {
            System.out.println("Error: Ya existe un producto con código " + nuevoNodo.key);
        }
    }

    // BUSCAR NODO
    public Nodo buscarNodo(int key) {
        return buscarRecursivo(raiz, key);
    }

    // Método auxiliar para buscar recursivamente
    private Nodo buscarRecursivo(Nodo actual, int key) {
        if (actual == null) {
            return null;
        }
        if (key == actual.key) {
            return actual;
        } else if (key < actual.key) {
            return buscarRecursivo(actual.left, key);
        } else {
            return buscarRecursivo(actual.right, key);
        }
    }

    // 3. EDITAR NODO
    public void editarNodo(int key, String nuevoNombre, int nuevoStock) {
        Nodo nodo = buscarNodo(key);
        if (nodo == null) {
            System.out.println("Error: No se encontró producto con código " + key);
            return;
        }
        System.out.println("\n~   Datos Actuales ~");
        nodo.mostrarInfo();
        if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
            nodo.name = nuevoNombre;
        }
        if (nuevoStock >= 0) {
            nodo.stock = nuevoStock;
        }
        System.out.println("\n--  Datos Actualizados  --");
        nodo.mostrarInfo();
        System.out.println("Producto editado exitosamente");
    }

    // 4. ELIMINAR NODO
    public void eliminarNodo(int key) {
        raiz = eliminarRecursivo(raiz, key);
    }

    // Método auxiliar para eliminar recursivamente
    private Nodo eliminarRecursivo(Nodo actual, int key) {
        if (actual == null) {
            System.out.println("Error: No se encontró producto con código " + key);
            return null;
        }

        if (key < actual.key) {
            actual.left = eliminarRecursivo(actual.left, key);
        } else if (key > actual.key) {
            actual.right = eliminarRecursivo(actual.right, key);
        } else {
            if (actual.left == null && actual.right == null) {
                System.out.println("Producto eliminado");
                return null;
            }
            if (actual.left == null) {
                System.out.println("Producto eliminado");
                return actual.right;
            }
            if (actual.right == null) {
                System.out.println("Producto eliminado");
                return actual.left;
            }
            Nodo sucesor = encontrarMinimo(actual.right);
            actual.key = sucesor.key;
            actual.name = sucesor.name;
            actual.stock = sucesor.stock;
            actual.right = eliminarRecursivo(actual.right, sucesor.key);
            System.out.println("Producto eliminado");
        }
        return actual;
    }

    // Encontrar el nodo con valor mínimo
    private Nodo encontrarMinimo(Nodo nodo) {
        while (nodo.left != null) {
            nodo = nodo.left;
        }
        return nodo;
    }

    // 5. RECORRIDO INORDEN
    public void recorridoInorden() {
        System.out.println("\n--- Recorrido InOrden (Inventario Ordenado) ---");
        if (raiz == null) {
            System.out.println("El inventario está vacío");
        } else {
            inordenRecursivo(raiz);
        }
    }

    private void inordenRecursivo(Nodo nodo) {
        if (nodo != null) {
            inordenRecursivo(nodo.left);
            nodo.mostrarInfo();
            inordenRecursivo(nodo.right);
        }
    }

    // RECORRIDO PREORDEN
    public void recorridoPreorden() {
        System.out.println("\n=== RECORRIDO PREORDEN ===");
        if (raiz == null) {
            System.out.println("El inventario está vacío");
        } else {
            preordenRecursivo(raiz);
        }
    }

    private void preordenRecursivo(Nodo nodo) {
        if (nodo != null) {
            nodo.mostrarInfo();
            preordenRecursivo(nodo.left);
            preordenRecursivo(nodo.right);
        }
    }

    // RECORRIDO POSTORDEN
    public void recorridoPostorden() {
        System.out.println("\n--- Recorrido PosOrden ---");
        if (raiz == null) {
            System.out.println("El inventario está vacío");
        } else {
            postordenRecursivo(raiz);
        }
    }

    private void postordenRecursivo(Nodo nodo) {
        if (nodo != null) {
            postordenRecursivo(nodo.left);
            postordenRecursivo(nodo.right);
            nodo.mostrarInfo();
        }
    }
}

// Clase principal con el menú
public class SistemaInventario {
    public static void mostrarMenu() {
        System.out.println("\n Sistema de Gestion de Inventario");
        System.out.println("-----------------------------------------");
        System.out.println("1. Agregar producto");
        System.out.println("2. Buscar producto");
        System.out.println("3. Editar producto");
        System.out.println("4. Eliminar producto");
        System.out.println("5. Ver inventario ordenado (Inorden)");
        System.out.println("6. Ver recorrido Preorden");
        System.out.println("7. Ver recorrido Postorden");
        System.out.println("8. Cargar datos de prueba");
        System.out.println("9. Salir");
    }

    public static void cargarDatosPrueba(ArbolInventario arbol) {
        System.out.println("\n  Cargando datos de prueba ");
        arbol.insertarNodo(50, "Laptop HP", 15);
        arbol.insertarNodo(30, "Mouse Logitech", 80);
        arbol.insertarNodo(70, "Monitor Samsung", 25);
        arbol.insertarNodo(20, "Teclado Mecánico", 45);
        arbol.insertarNodo(40, "Webcam HD", 60);
        arbol.insertarNodo(60, "Auriculares Bluetooth", 35);
        arbol.insertarNodo(80, "Impresora Epson", 10);
        System.out.println("\nDatos de prueba cargados exitosamente");
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArbolInventario arbol = new ArbolInventario();
        String aux;

        while (true) {
            mostrarMenu();
            System.out.print("\nSeleccione una opción: ");
            int opcion = 0;
            try {
                aux = br.readLine();
                opcion = Integer.parseInt(aux);
            } catch (NumberFormatException e) {
                System.out.println("Error: El número no es válido");
                continue;
            }

            switch (opcion) {
                case 1: // Agregar producto
                    System.out.println("\n--- Agregar Producto ---");
                    try {
                        System.out.print("Ingrese código del producto: ");
                        aux = br.readLine();
                        int codigo = Integer.parseInt(aux);
                        System.out.print("Ingrese nombre del producto: ");
                        String nombre = br.readLine();
                        System.out.print("Ingrese cantidad en stock: ");
                        aux = br.readLine();
                        int stock = Integer.parseInt(aux);
                        if (nombre.isEmpty()) {
                            System.out.println("Error: El nombre no puede estar vacío");
                            break;
                        }
                        if (stock < 0) {
                            System.out.println("Error: El stock no puede ser negativo");
                            break;
                        }
                        arbol.insertarNodo(codigo, nombre, stock);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese datos válidos");
                    }
                    break;

                case 2: // Buscar producto
                    System.out.println("\n--- Buscar Producto ---");
                    try {
                        System.out.print("Ingrese código del producto: ");
                        aux = br.readLine();
                        int codigo = Integer.parseInt(aux);
                        Nodo nodo = arbol.buscarNodo(codigo);
                        if (nodo != null) {
                            System.out.println("\nProducto encontrado:");
                            nodo.mostrarInfo();
                        } else {
                            System.out.println("No se encontró producto con código " + codigo);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un código válido");
                    }
                    break;

                case 3: // Editar producto
                    System.out.println("\n--- Editar Producto ---");
                    try {
                        System.out.print("Ingrese código del producto a editar: ");
                        aux = br.readLine();
                        int codigo = Integer.parseInt(aux);
                        Nodo nodo = arbol.buscarNodo(codigo);
                        if (nodo == null) {
                            System.out.println("No se encontró producto con código " + codigo);
                            break;
                        }
                        System.out.print("Ingrese nuevo nombre (Enter para mantener actual): ");
                        String nuevoNombre = br.readLine();
                        System.out.print("Ingrese nuevo stock (-1 para mantener actual): ");
                        aux = br.readLine();
                        int nuevoStock = Integer.parseInt(aux);
                        arbol.editarNodo(codigo, nuevoNombre, nuevoStock);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese datos válidos");
                    }
                    break;

                case 4: // Eliminar producto
                    System.out.println("\n--- Eliminar Producto ---");
                    try {
                        System.out.print("Ingrese código del producto a eliminar: ");
                        aux = br.readLine();
                        int codigo = Integer.parseInt(aux);
                        arbol.eliminarNodo(codigo);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un código válido");
                    }
                    break;

                case 5: // Ver inventario ordenado
                    arbol.recorridoInorden();
                    break;

                case 6: // Ver recorrido Preorden
                    arbol.recorridoPreorden();
                    break;

                case 7: // Ver recorrido Postorden
                    arbol.recorridoPostorden();
                    break;

                case 8: // Cargar datos de prueba
                    cargarDatosPrueba(arbol);
                    break;

                case 9: // Salir
                    System.out.println("\nGracias por usar el sistema. ¡Hasta pronto!");
                    br.close();
                    return;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
}