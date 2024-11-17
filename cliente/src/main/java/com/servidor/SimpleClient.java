package com.servidor;
    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.io.PrintWriter;
    import java.net.Socket;
    
    public class SimpleClient {
        private static final String HOST = "localhost";
        private static final int PORT = 12345;
    
        public static void main(String[] args) {
            try (Socket socket = new Socket(HOST, PORT);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
    
                // Enviar datos de prueba
                out.println("cedulaEjemplo");
                out.println("contraseñaEjemplo");
    
                // Leer la respuesta
                String response = in.readLine();
                System.out.println("Respuesta del servidor: " + response);
    
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }