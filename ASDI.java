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


            // Reasignamos el valor de X con el último elemneto ingresado a la pila
            X = pila.peek();
        }

    }


   

}