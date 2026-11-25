package com.lipe;

import com.lipe.controller.TaskController;
import com.lipe.model.Task;
import com.lipe.repository.TaskRepository;
import com.lipe.service.TaskService;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Main {
   public static void main(String[] args) throws IOException {

        TaskRepository repository = new TaskRepository();
        TaskService service = new TaskService(repository);
        TaskController controller = new TaskController(service);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/tasks", controller);

        server.setExecutor(null);
        server.start();

        System.out.println("Server is running in the port 8080!");

        }
    }
