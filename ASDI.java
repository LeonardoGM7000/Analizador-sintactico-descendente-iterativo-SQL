import java.util.List;
import java.util.Stack;

public class ASDI implements Parser {

    // Definimos las variables
    private final List<Token> tokens;
    Stack<String> pila = new Stack<>();
    String a;
    String X;
    Token A;
    boolean hayErrores = false;

    // Creamos constructor de la clase7
    public ASDI(List<Token> tokens) {
        this.tokens = tokens;
    }

    // Reescribimos el método para ingresar la tabla
    @Override
    public boolean parse() {
        // Declaramos la tabla
        
        String[][] tabla = {
            { "",   "select",               "from",     "distinct",         "*",        ",",            "id",           ".",            "$" },
            { "Q",  "select D from T",      "",         "",                 "",         "",             "",             "",             "" },
            { "D",  "",                     "",         "distinct P",       "P",        "",             "P",            "",             "" },
            { "P",  "",                     "",         "",                 "*",        "",             "A",            "",             "" },
            { "A",  "",                     "",         "",                 "",         "",             "A2 A1",        "",             "" },
            { "A1", "",                     "E",        "",                 "",         ", A",          "",             "",             "" },
            { "A2", "",                     "",         "",                 "",         "",             "id A3",        "",             "" },
            { "A3", "",                     "E",        "",                 "",         "E",            "",             ". id",         "" },
            { "T",  "",                     "",         "",                 "",         "",             "T2 T1",        "",             "" },
            { "T1", "",                     "",         "",                 "",         ", T",          "",             "",             "E" },
            { "T2", "",                     "",         "",                 "",         "",             "id T3",        "",             "" },
            { "T3", "",                     "",         "",                 "",         "E",            "id",           "",             "E" }
    };

   analizarTokens(tokens, tabla);

   if(A.tipo == TipoToken.EOF && !hayErrores){
        System.out.println("Consulta correcta");
        return true;
    } else {
        System.out.println("Se encontraron errores");
    }

    return false; 
       
    }

    

    // Declaramos función para analizar 
    private void analizarTokens(List<Token> tokens, String[][] tabla){

        // Declaramos e inicializamos las variables
        int ip = 0;
        pila.push("$");
        pila.push("Q");

        // Iniciamos a con el primer token
        A = tokens.get(ip);
        a = A.lexema;

        // Inicializamos X con el último elemento ingresado a la pila
        X = pila.peek();

        while(!X.equals("$")){

            // Verificamos si a contiene un identificador
            if(A.tipo == TipoToken.IDENTIFICADOR){
                a = "id";
            }

            // X es a (X == a)
            if(X.equals(a)){

                pila.pop();
                ip++;
                A = tokens.get(ip);
                a = A.lexema;
            
            }

            // X es un terminal
            else if(esTerminal(tabla, X)){

                System.out.println("Error token " + (ip + 1) + ": Simbolo terminal no esperado");
                hayErrores = true;
                break;
            }

            // tabla [X, a] == "" (entrada de error)
            else if(buscarTabla(tabla, X, a).equals("")){
                System.out.println("Error token " + (ip + 1) + ": Producción no válida");
                hayErrores = true;
                break;
            }

            // tabla [X, a] == PRODUCCIÓN
            else{

                String aux = buscarTabla(tabla, X, a);
                System.out.println(X+" -> "+aux);
                // Sacamos de la pila
                pila.pop();
                String[] produccion_array = aux.split(" ");

                // Verificamos que la producción no genere Epsilon (E) para no ingresarla a la pila

                if(!produccion_array[0].equals("E")){

                    for(int i = produccion_array.length-1; i >= 0; i--)
                    pila.push(produccion_array[i]);
                }
                
            }


            // Reasignamos el valor de X con el último elemneto ingresado a la pila
            X = pila.peek();
        }

    }

    // Declaramos funciones auxiliares
    private boolean esTerminal(String[][] tabla, String X){

        for (int i = 0; i < tabla[0].length; i++) {
            if (tabla[0][i].equals(X)) {
                return true;
            }
        }

        return false;
    }

    private String buscarTabla(String [][] tabla, String X, String a){

        int fila = 0;
        int columna = 0;

        // Buscamos no terminal
        for (int i = 0; i < tabla.length; i++) {
            if (tabla[i][0].equals(X)) {
                fila = i;
                break;
            }
        }

        // Buscamos terminal
        for (int j = 0; j < tabla[0].length; j++) {
            if (tabla[0][j].equals(a)) {
                columna = j;
                break;
            }
        }
    
        return tabla[fila][columna];
    }
   

}