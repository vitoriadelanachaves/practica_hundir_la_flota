package hundir_la_flota;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hundir_la_flota 
{
    public static void JuegaPartida(int filas, int columnas, int lanchas, int buques, int acorazados, int portaaviones, int max_disparos)
    {
        char[][] tablero = CrearTablero(filas, columnas);
        InsertaBarcos(tablero, lanchas, buques, acorazados, portaaviones);

        System.out.println("\nTablero inicial (oculto):\n");
        MuestraTableroOculto(tablero);

        int disparos_restantes = max_disparos;
        while (disparos_restantes > 0 && QuedanBarcos(tablero)) 
        {
            System.out.printf("\nDisparos restantes: %d\n", disparos_restantes);
            int[] coordenadas = PideCoordenadasDisparo(tablero);
            RealizaDisparo(tablero, coordenadas[0], coordenadas[1]);

            System.out.println("\nEstado actual del tablero:\n");
            MuestraTableroOculto(tablero);
            disparos_restantes--;
        }

        if (QuedanBarcos(tablero)) 
        {
            System.out.println("\nHas perdido. Aún quedan barcos en el tablero.\n");
        } else 
        {
            System.out.println("\n¡Felicidades! Has hundido todos los barcos.\n");
        }
    }
    
    public static void MuestraTableroOculto(char[][] tablero) 
    {
        System.out.print("   ");
        for (int j = 0; j < tablero[0].length; j++)
        {
            System.out.printf("%3d", j + 1);
        }
        System.out.println();

        for (int i = 0; i < tablero.length; i++) 
        {
            System.out.printf("%3c", (char) ('A' + i));
            for (int j = 0; j < tablero[i].length; j++)
            {
                if (tablero[i][j] == 'L' || tablero[i][j] == 'B' || tablero[i][j] == 'Z' || tablero[i][j] == 'P')
                {
                    System.out.print("  -");
                } 
                else
                {
                    System.out.printf("%3c", tablero[i][j]);
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
                    return true;
                }
            }
        }
        return false;
    }

    public static char[][] CrearTablero(int filas, int columnas)
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
    {
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
        int fila = -1, columna = -1;
        boolean valido = false;

        while (!valido) 
        {
            try 
            {
                System.out.print("Introduce la fila (A, B, C...): ");
                String filaEntrada = sc.nextLine().trim().toUpperCase();

                if (filaEntrada.length() != 1 || filaEntrada.charAt(0) < 'A' || filaEntrada.charAt(0) >= 'A' + tablero.length) 
                {
                    System.out.println("Fila no válida. Introduce una letra entre A y " + (char) ('A' + tablero.length - 1));
                    continue;
                }

                fila = filaEntrada.charAt(0) - 'A';

                System.out.print("Introduce la columna (1, 2, 3...): ");
                if (!sc.hasNextInt()) 
                {
                    System.out.println("Columna no válida. Introduce un número entre 1 y " + tablero[0].length);
                    sc.nextLine(); // Limpiar entrada
                    continue;
                }

                columna = sc.nextInt() - 1;
                sc.nextLine(); // Limpiar buffer

                if (columna < 0 || columna >= tablero[0].length) 
                {
                    System.out.println("Columna no válida. Introduce un número entre 1 y " + tablero[0].length);
                    continue;
                }

                valido = true;

            } catch (Exception e) 
            {
                System.out.println("Error en la entrada. Intenta nuevamente.");
                sc.nextLine(); // Limpiar buffer
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

        if (opcion == 1)
            JuegaPartida(10, 10, 5, 2, 1, 1, 50);
        else if (opcion == 2)
            JuegaPartida(10, 10, 8, 3, 2, 1, 40);
        else if (opcion == 3)
            JuegaPartida(10, 10, 10, 4, 3, 2, 30);
        else if (opcion == 4) 
        {
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
        }
    }
}

