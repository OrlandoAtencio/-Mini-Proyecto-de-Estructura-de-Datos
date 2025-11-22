import java.util.Scanner;

// Clase Nodo: representa un producto en el inventario
class Nodo {
    int key;           // Código del producto
    String name;       // Nombre del producto
    int stock;         // Cantidad disponible
    Nodo left;         // Hijo izquierdo
    Nodo right;        // Hijo derecho
    
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

// Clase ArbolInventario: maneja el árbol binario de búsqueda
class ArbolInventario {
    Nodo root;  // Raíz del árbol
    
    // Constructor
    public ArbolInventario() {
        this.root = null;
    }
    
    // 1. CREAR NODO
    public Nodo crearNodo(int key, String name, int stock) {
        return new Nodo(key, name, stock);
    }
    
    // 2. INSERTAR NODO
    public void insertarNodo(int key, String name, int stock) {
        Nodo nuevoNodo = crearNodo(key, name, stock);
        
        if (root == null) {
            root = nuevoNodo;
            System.out.println("Producto insertado como raíz");
        } else {
            insertarRecursivo(root, nuevoNodo);
        }
    }
    
    // Método auxiliar para insertar recursivamente
    private void insertarRecursivo(Nodo actual, Nodo nuevoNodo) {
        if (nuevoNodo.key < actual.key) {
            // Ir a la izquierda
            if (actual.left == null) {
                actual.left = nuevoNodo;
                System.out.println("Producto insertado exitosamente");
            } else {
                insertarRecursivo(actual.left, nuevoNodo);
            }
        } else if (nuevoNodo.key > actual.key) {
            // Ir a la derecha
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
        return buscarRecursivo(root, key);
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
        
        System.out.println("\n--- Datos Actuales ---");
        nodo.mostrarInfo();
        
        if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
            nodo.name = nuevoNombre;
        }
        if (nuevoStock >= 0) {
            nodo.stock = nuevoStock;
        }
        
        System.out.println("\n--- Datos Actualizados ---");
        nodo.mostrarInfo();
        System.out.println("Producto editado exitosamente");
    }
    
    // 4. ELIMINAR NODO
    public void eliminarNodo(int key) {
        root = eliminarRecursivo(root, key);
    }
    
    // Método auxiliar para eliminar recursivamente
    private Nodo eliminarRecursivo(Nodo actual, int key) {
        if (actual == null) {
            System.out.println("Error: No se encontró producto con código " + key);
            return null;
        }
        
        // Buscar el nodo
        if (key < actual.key) {
            actual.left = eliminarRecursivo(actual.left, key);
        } else if (key > actual.key) {
            actual.right = eliminarRecursivo(actual.right, key);
        } else {
            // Nodo encontrado
            
            // Caso 1: Nodo sin hijos
            if (actual.left == null && actual.right == null) {
                System.out.println("Producto eliminado (sin hijos)");
                return null;
            }
            
            // Caso 2: Nodo con un solo hijo
            if (actual.left == null) {
                System.out.println("Producto eliminado (un hijo derecho)");
                return actual.right;
            }
            if (actual.right == null) {
                System.out.println("Producto eliminado (un hijo izquierdo)");
                return actual.left;
            }
            
            // Caso 3: Nodo con dos hijos
            // Encontrar el sucesor inorden (menor del subárbol derecho)
            Nodo sucesor = encontrarMinimo(actual.right);
            
            // Copiar datos del sucesor
            actual.key = sucesor.key;
            actual.name = sucesor.name;
            actual.stock = sucesor.stock;
            
            // Eliminar el sucesor
            actual.right = eliminarRecursivo(actual.right, sucesor.key);
            System.out.println("Producto eliminado (dos hijos)");
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
    
    // 5. RECORRIDO INORDEN (Izquierda - Raíz - Derecha)
    public void recorridoInorden() {
        System.out.println("\n=== RECORRIDO INORDEN (Inventario Ordenado) ===");
        if (root == null) {
            System.out.println("El inventario está vacío");
        } else {
            inordenRecursivo(root);
        }
    }
    
    private void inordenRecursivo(Nodo nodo) {
        if (nodo != null) {
            inordenRecursivo(nodo.left);
            nodo.mostrarInfo();
            inordenRecursivo(nodo.right);
        }
    }
    
    // RECORRIDO PREORDEN (Raíz - Izquierda - Derecha)
    public void recorridoPreorden() {
        System.out.println("\n=== RECORRIDO PREORDEN ===");
        if (root == null) {
            System.out.println("El inventario está vacío");
        } else {
            preordenRecursivo(root);
        }
    }
    
    private void preordenRecursivo(Nodo nodo) {
        if (nodo != null) {
            nodo.mostrarInfo();
            preordenRecursivo(nodo.left);
            preordenRecursivo(nodo.right);
        }
    }
    
    // RECORRIDO POSTORDEN (Izquierda - Derecha - Raíz)
    public void recorridoPostorden() {
        System.out.println("\n=== RECORRIDO POSTORDEN ===");
        if (root == null) {
            System.out.println("El inventario está vacío");
        } else {
            postordenRecursivo(root);
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
        System.out.println("\n==================================================");
        System.out.println("       SISTEMA DE GESTIÓN DE INVENTARIO");
        System.out.println("==================================================");
        System.out.println("1. Agregar producto");
        System.out.println("2. Buscar producto");
        System.out.println("3. Editar producto");
        System.out.println("4. Eliminar producto");
        System.out.println("5. Ver inventario ordenado (Inorden)");
        System.out.println("6. Ver recorrido Preorden");
        System.out.println("7. Ver recorrido Postorden");
        System.out.println("8. Cargar datos de prueba");
        System.out.println("9. Salir");
        System.out.println("==================================================");
    }
    
    public static void cargarDatosPrueba(ArbolInventario arbol) {
        System.out.println("\n--- Cargando datos de prueba ---");
        arbol.insertarNodo(50, "Laptop HP", 15);
        arbol.insertarNodo(30, "Mouse Logitech", 80);
        arbol.insertarNodo(70, "Monitor Samsung", 25);
        arbol.insertarNodo(20, "Teclado Mecánico", 45);
        arbol.insertarNodo(40, "Webcam HD", 60);
        arbol.insertarNodo(60, "Auriculares Bluetooth", 35);
        arbol.insertarNodo(80, "Impresora Epson", 10);
        System.out.println("\nDatos de prueba cargados exitosamente");
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArbolInventario arbol = new ArbolInventario();
        
        while (true) {
            mostrarMenu();
            System.out.print("\nSeleccione una opción: ");
            
            int opcion = 0;
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
            } catch (Exception e) {
                System.out.println("Error: Ingrese un número válido");
                scanner.nextLine(); // Limpiar buffer
                continue;
            }
            
            switch (opcion) {
                case 1: // Agregar producto
                    System.out.println("\n--- AGREGAR PRODUCTO ---");
                    try {
                        System.out.print("Ingrese código del producto: ");
                        int codigo = scanner.nextInt();
                        scanner.nextLine();
                        
                        System.out.print("Ingrese nombre del producto: ");
                        String nombre = scanner.nextLine();
                        
                        System.out.print("Ingrese cantidad en stock: ");
                        int stock = scanner.nextInt();
                        scanner.nextLine();
                        
                        if (nombre.isEmpty()) {
                            System.out.println("Error: El nombre no puede estar vacío");
                            break;
                        }
                        if (stock < 0) {
                            System.out.println("Error: El stock no puede ser negativo");
                            break;
                        }
                        
                        arbol.insertarNodo(codigo, nombre, stock);
                    } catch (Exception e) {
                        System.out.println("Error: Ingrese datos válidos");
                        scanner.nextLine();
                    }
                    break;
                
                case 2: // Buscar producto
                    System.out.println("\n--- BUSCAR PRODUCTO ---");
                    try {
                        System.out.print("Ingrese código del producto: ");
                        int codigo = scanner.nextInt();
                        scanner.nextLine();
                        
                        Nodo nodo = arbol.buscarNodo(codigo);
                        if (nodo != null) {
                            System.out.println("\nProducto encontrado:");
                            nodo.mostrarInfo();
                        } else {
                            System.out.println("No se encontró producto con código " + codigo);
                        }
                    } catch (Exception e) {
                        System.out.println("Error: Ingrese un código válido");
                        scanner.nextLine();
                    }
                    break;
                
                case 3: // Editar producto
                    System.out.println("\n--- EDITAR PRODUCTO ---");
                    try {
                        System.out.print("Ingrese código del producto a editar: ");
                        int codigo = scanner.nextInt();
                        scanner.nextLine();
                        
                        Nodo nodo = arbol.buscarNodo(codigo);
                        if (nodo == null) {
                            System.out.println("No se encontró producto con código " + codigo);
                            break;
                        }
                        
                        System.out.print("Ingrese nuevo nombre (Enter para mantener actual): ");
                        String nuevoNombre = scanner.nextLine();
                        
                        System.out.print("Ingrese nuevo stock (-1 para mantener actual): ");
                        int nuevoStock = scanner.nextInt();
                        scanner.nextLine();
                        
                        arbol.editarNodo(codigo, nuevoNombre, nuevoStock);
                    } catch (Exception e) {
                        System.out.println("Error: Ingrese datos válidos");
                        scanner.nextLine();
                    }
                    break;
                
                case 4: // Eliminar producto
                    System.out.println("\n--- ELIMINAR PRODUCTO ---");
                    try {
                        System.out.print("Ingrese código del producto a eliminar: ");
                        int codigo = scanner.nextInt();
                        scanner.nextLine();
                        
                        arbol.eliminarNodo(codigo);
                    } catch (Exception e) {
                        System.out.println("Error: Ingrese un código válido");
                        scanner.nextLine();
                    }
                    break;
                
                case 5: // Ver inventario ordenado (Inorden)
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
                    scanner.close();
                    return;
                
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
}