package hundir_la_flota;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hundir_la_flota 
{
    //funcion para crear partida donde en la funcion main pondremos valores correspondientes a los parametros proporcionados
    public static void JuegaPartida(int filas, int columnas, int lanchas, int buques, int acorazados, int portaaviones, int max_disparos)
    {
        char[][] tablero = CrearTablero(filas, columnas); //aqui crearemos la variable tablero que contendrá el valor retornado por la función 'CrearTablero' que es una matriz de tipo 'char'
        InsertaBarcos(tablero, lanchas, buques, acorazados, portaaviones);// ya con nuestro tablero creado llamamos la funcion InsertaBarcos que es para insertar el número de barcos indicados en la funcion

        System.out.println("\nTablero inicial:\n"); 
        MuestraTableroOculto(tablero); //tablero con los barcos insertados ocultos para el jugador

        int disparos_restantes = max_disparos; //variable que controla la cantidad de disparos que le quedan al jugador
        while (disparos_restantes > 0 && QuedanBarcos(tablero)) //bucle que ejecuta la partida mientras queden disparos y barcos
        {
            System.out.printf("\nDisparos restantes: %d\n", disparos_restantes);
            int[] coordenadas = PideCoordenadasDisparo(tablero); //pide al jugador las coordenadas donde quiere disparar(lamamos la funcion 'PideCoordenadasDisparo' que retorna un vector con las coordenadas insertadas por el jugador)
            RealizaDisparo(tablero, coordenadas[0], coordenadas[1]); //ejecuta el disparo en el tablero en las coordenadas indicadas

            System.out.println("\nEstado actual del tablero:\n");
            MuestraTableroOculto(tablero); //Muestra el estado del tablero despues del disparo
            disparos_restantes--; // resta intentos
        }

        if (QuedanBarcos(tablero)) //verifica si aún quedan barcos al finalizar los intentos
        {
            System.out.println("\nHas perdido. Aún quedan barcos en el tablero.\n");
        } else 
        {
            System.out.println("\n¡Felicidades! Has hundido todos los barcos.\n");
        }
    }
    
    public static void MuestraTableroOculto(char[][] tablero) 
    {
        System.out.print("   "); //formato de encabezado para las columnas
        for (int j = 0; j < tablero[0].length; j++)
        {
            System.out.printf("%3d", j ); //imprime los números de las columnas
        }
        System.out.println();

        for (int i = 0; i < tablero.length; i++) 
        {
            System.out.printf("%3c", (char) ('A' + i)); //imprime las letras de las filas
            for (int j = 0; j < tablero[i].length; j++)
            {
                if (tablero[i][j] == 'L' || tablero[i][j] == 'B' || tablero[i][j] == 'Z' || tablero[i][j] == 'P')
                {
                    System.out.print("  -"); //oculta los barcos del tablero
                } 
                else
                {
                    System.out.printf("%3c", tablero[i][j]); //muestra el resto de los elementos del tablero
                }
            }
            System.out.println();
        }
    }

    public static boolean QuedanBarcos(char[][] tablero)
    {
        for(int i = 0; i < tablero.length; i++)
        {
            for(int j = 0; j < tablero[i].length; j++)
            {
                if(tablero[i][j] == 'L' || tablero[i][j] == 'B' || 
                   tablero[i][j] == 'Z' || tablero[i][j] == 'P')
                {
                    return true; //recojemos todo el vector y si encuentra al menos un barco, retorna verdadero
                }
            }
        }
        return false;
    }

    public static char[][] CrearTablero(int filas, int columnas) // crea un tablero vacio
    {
        char tablero[][] = new char[filas][columnas];

        for (int i = 0; i < tablero.length; i++) 
        {
            for (int j = 0; j < tablero[i].length; j++)
            {
                tablero[i][j] = '-';
            }
        }
        return tablero;
    }

    public static void InsertaBarcos(char[][] tablero, int lanchas, int buques, int acorazados, int portaaviones)
    { //funcion que llama las funciones de insercion de barcos y las pone en un bucle 'while' de acuerdo con el numero de barcos seleccionados
        int contador_lanchas = 0, contador_buques = 0, contador_acorazados = 0, contador_portaaviones = 0;

        while (contador_lanchas < lanchas) 
        {
            InsertaLancha(tablero);
            contador_lanchas++;
        }
        while (contador_buques < buques) 
        {
            InsertaBuque(tablero);
            contador_buques++;
        }
        while (contador_acorazados < acorazados)
        {
            InsertaAcorazado(tablero);
            contador_acorazados++;
        }
        while (contador_portaaviones < portaaviones)
        {
            InsertaPortaaviones(tablero);
            contador_portaaviones++;
        }
    }
    /*
        RESUMEN DE LAS FUNCIONES DE INSERCION DE BARCOS
        
        La forma mas facil para mi fue declarar el objeto 'Random' para seleccionar aleatoriamente las coordenadas (fila, columna)
        tambien para cada una de las funciones de insercion cree una variable booleana que verifica si el barco fue insertado correctamente
        de acuerdo con las restricciones indicadas de espacio (si hay espacio suficiente hacia derecha o no o si es apenas una lancha 
        asegurar que va a ser insertada en un sitio que este vacio). Los portaaviones es igual solo que verifica si hay espacio en la columna hacia abajo.
    */
   
    public static void InsertaLancha(char[][] tablero)
    {
        Random random = new Random();
        boolean insertado = false;

        while (!insertado)
        {
            int fila_random = random.nextInt(tablero.length);
            int columna_random = random.nextInt(tablero[0].length);

            if (tablero[fila_random][columna_random] == '-') 
            {
                tablero[fila_random][columna_random] = 'L';
                insertado = true;
            }
        }
    }

    public static void InsertaBuque(char[][] tablero) 
    {
        Random random = new Random();
        boolean insertado = false;

        while (!insertado)
        {
            int fila_random = random.nextInt(tablero.length);
            int columna_random = random.nextInt(tablero[0].length - 2);

            if (tablero[fila_random][columna_random] == '-' &&
                tablero[fila_random][columna_random + 1] == '-' &&
                tablero[fila_random][columna_random + 2] == '-') 
            {
                tablero[fila_random][columna_random] = 'B';
                tablero[fila_random][columna_random + 1] = 'B';
                tablero[fila_random][columna_random + 2] = 'B';
                insertado = true;
            }
        }
    }

    public static void InsertaAcorazado(char[][] tablero) 
    {
        Random random = new Random();
        boolean insertado = false;

        while (!insertado)
        {
            int fila_random = random.nextInt(tablero.length);
            int columna_random = random.nextInt(tablero[0].length - 3);

            if (tablero[fila_random][columna_random] == '-' &&
                tablero[fila_random][columna_random + 1] == '-' &&
                tablero[fila_random][columna_random + 2] == '-' &&
                tablero[fila_random][columna_random + 3] == '-')
            {
                tablero[fila_random][columna_random] = 'Z';
                tablero[fila_random][columna_random + 1] = 'Z';
                tablero[fila_random][columna_random + 2] = 'Z';
                tablero[fila_random][columna_random + 3] = 'Z';
                insertado = true;
            }
        }
    }

    public static void InsertaPortaaviones(char[][] tablero)
    {
        Random random = new Random();
        boolean insertado = false;

        while (!insertado) 
        {
            int fila_random = random.nextInt(tablero.length - 4);
            int columna_random = random.nextInt(tablero[0].length);

            if (tablero[fila_random][columna_random] == '-' &&
                tablero[fila_random + 1][columna_random] == '-' &&
                tablero[fila_random + 2][columna_random] == '-' &&
                tablero[fila_random + 3][columna_random] == '-' &&
                tablero[fila_random + 4][columna_random] == '-')
            {
                tablero[fila_random][columna_random] = 'P';
                tablero[fila_random + 1][columna_random] = 'P';
                tablero[fila_random + 2][columna_random] = 'P';
                tablero[fila_random + 3][columna_random] = 'P';
                tablero[fila_random + 4][columna_random] = 'P';
                insertado = true;
            }
        }
    }

    public static int[] PideCoordenadasDisparo(char[][] tablero) 
    {
        Scanner sc = new Scanner(System.in);
        int fila = -1, columna = -1; //Inicializamos fila y columna con valores de -1 para garantizar que el bucle no se ejecute con valores válidos hasta que el jugador ingrese correctamente las coordenadas.
        boolean valido = false;

        while (!valido) 
        {
            try 
            {
                System.out.print("Introduce la fila (A, B, C...): ");
                String filaEntrada = sc.nextLine().trim().toUpperCase(); //pedir la fila como una String utilizamos '.trim()' para garantizar que no vayan los espacios y '.toUpperCase()' para garantizar que independiente si es minuscula o mayuscula se va a interpretar como mayuscula

                if (filaEntrada.length() != 1 || filaEntrada.charAt(0) < 'A' || filaEntrada.charAt(0) >= 'A' + tablero.length) //se ejecuta hasta que las coordenadas proporcionadas por el jugador sean válidas (es decir, que la fila y la columna estén dentro de los límites del tablero). El bucle continuará pidiendo coordenadas hasta que se ingresen correctamente.
                {
                    System.out.println("Fila no válida. Introduce una letra entre A y " + (char) ('A' + tablero.length - 1));
                }

                fila = filaEntrada.charAt(0) - 'A'; // aqui convertimos el valor e las letras en valores numericos enteros
                // por ejemplo: el valor de A en int es 65, y vamos decir que el valor isertado por el jugador es B
                //esto quiere decir que el valor asiciado a B sera B - A = 66 - 65 = 1.

                System.out.print("Introduce la columna (1, 2, 3...): ");
                columna = sc.nextInt() - 1;
                sc.nextLine(); // Limpiar linea

                if (columna < 0 || columna >= tablero[0].length - 1) 
                {
                   throw new IllegalArgumentException();
                }

                valido = true;

            } 
            catch (InputMismatchException e) 
            {
                System.out.println("Error en la entrada. Intenta nuevamente.");
                System.err.println("Columna no válida. Introduce un número entre 0 y " + (tablero[0].length - 1));
                sc.nextLine(); // Limpiar linea
            }
            catch(IllegalArgumentException e)
            {
                System.err.println("Columna no válida. Introduce un número entre 0 y " + (tablero[0].length - 1));
            }
        }
        return new int[]{fila, columna};
    }

    public static void RealizaDisparo(char[][] tablero, int fila, int columna) 
    {
        switch (tablero[fila][columna]) 
        {
            case '-':
                tablero[fila][columna] = 'A';
                System.out.println("Agua!");
                break;
            case 'L':
            case 'B':
            case 'Z':
            case 'P':
                tablero[fila][columna] = 'X';
                System.out.println("Tocado!");
                break;
            default:
                System.out.println("Ya disparaste en esta posición.");
                break;
        }
    }

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("====================================");
        System.out.println("\t\tMENÚ");
        System.out.println("====================================");
        System.out.println("1 - Fácil");
        System.out.println("2 - Medio");
        System.out.println("3 - Difícil");
        System.out.println("4 - Personalizado");

        int opcion = 0;
        while (opcion < 1 || opcion > 4) 
        {
            System.out.print("opción: ");
            try 
            {
                opcion = sc.nextInt();
            } 
            catch (InputMismatchException e) 
            {
                System.out.println("Has introducido un número fuera del rango establecido (1-4)");
                sc.nextLine();
            }
        }

        switch (opcion)
        {
            case 1:
                JuegaPartida(10, 10, 5, 3, 1, 1, 50);
                break;
            case 2:
                JuegaPartida(10, 10, 2, 1, 1, 1, 30);
                break;
            case 3:
                JuegaPartida(10, 10, 1, 1, 0, 0, 10);
                break;
            case 4:
                System.out.print("Introduce las filas del tablero: ");
                int filas = sc.nextInt();
                System.out.print("Introduce las columnas del tablero: ");
                int columnas = sc.nextInt();
                System.out.print("Introduce el número de lanchas: ");
                int lanchas = sc.nextInt();
                System.out.print("Introduce el número de buques: ");
                int buques = sc.nextInt();
                System.out.print("Introduce el número de acorazados: ");
                int acorazados = sc.nextInt();
                System.out.print("Introduce el número de portaaviones: ");
                int portaaviones = sc.nextInt();
                System.out.print("Introduce el número máximo de disparos: ");
                int max_disparos = sc.nextInt();
                JuegaPartida(filas, columnas, lanchas, buques, acorazados, portaaviones, max_disparos);
                break;
            default:
                break;
        }
    }
}

