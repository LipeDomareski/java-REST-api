package com.lipe.controller;

import com.lipe.model.Task;
import com.lipe.service.TaskService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;

public class TaskController implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if (method.equals("GET")){
            String path = exchange.getRequestURI().getPath();

            if (path.equals("/tasks")||path.equals("/tasks/")) {
                var tasks = service.findtasks();

                String jsonResponse = mapper.writeValueAsString(tasks);

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, jsonResponse.length());

                OutputStream os = exchange.getResponseBody();
                os.write(jsonResponse.getBytes());
                os.close();
            }else {
                try {
                    String idString = path.substring(path.lastIndexOf("/")+1);
                    Long id = Long.parseLong(idString);

                    Task serchedTaks = service.findTaskById(id);

                    String jsonResponse = mapper.writeValueAsString(serchedTaks);

                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, jsonResponse.length());

                    OutputStream os = exchange.getResponseBody();
                    os.write(jsonResponse.getBytes());
                    os.close();
                }catch (IllegalArgumentException e){
                    String errorMenssage = e.getMessage();
                    exchange.getResponseHeaders().set("Content-type", "text/plain");
                    exchange.sendResponseHeaders(400, errorMenssage.length());

                    OutputStream os = exchange.getResponseBody();
                    os.write(errorMenssage.getBytes());
                    os.close();
                }
            }
        }

        if (method.equals("POST")){
            try {
                Task reciveTask = mapper.readValue(exchange.getRequestBody(), Task.class);
                Task createdTask = service.create(reciveTask);

                String jsonResponse = mapper.writeValueAsString(createdTask);

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(201, jsonResponse.length());

                OutputStream os = exchange.getResponseBody();
                os.write(jsonResponse.getBytes());
                os.close();
            } catch (IllegalArgumentException e){
                String errorMenssage = e.getMessage();
                exchange.getResponseHeaders().set("Content-type", "text/plain");
                exchange.sendResponseHeaders(400, errorMenssage.length());

                OutputStream os = exchange.getResponseBody();
                os.write(errorMenssage.getBytes());
                os.close();
            }
        }

        if(method.equals("DELETE")){
           try {
                String path = exchange.getRequestURI().getPath();
                String idString = path.substring(path.lastIndexOf("/")+1);
                Long id = Long.parseLong(idString);

                service.deleteTask(id);

                exchange.sendResponseHeaders(204, -1 );
                OutputStream os = exchange.getResponseBody();
                os.close();

            }catch (IllegalArgumentException e){
                String errorMenssage = e.getMessage();
                exchange.getResponseHeaders().set("Content-type", "text/plain");
                exchange.sendResponseHeaders(400, errorMenssage.length());

                OutputStream os = exchange.getResponseBody();
                os.write(errorMenssage.getBytes());
                os.close();
           }
        }

        if (method.equals("PUT")){
            try {
                String path = exchange.getRequestURI().getPath();
                String idString = path.substring(path.lastIndexOf("/")+1);
                Long id = Long.parseLong(idString);

                Task recivedTask = mapper.readValue(exchange.getRequestBody(), Task.class);
                Task updatedTask = service.updateTask(id, recivedTask);

                String jsonResponse = mapper.writeValueAsString(updatedTask);

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, jsonResponse.length());

                OutputStream os = exchange.getResponseBody();
                os.write(jsonResponse.getBytes());
                os.close();

            }catch (IllegalArgumentException e){
                String errorMenssage = e.getMessage();
                exchange.getResponseHeaders().set("Content-type", "text/plain");
                exchange.sendResponseHeaders(400, errorMenssage.length());

                OutputStream os = exchange.getResponseBody();
                os.write(errorMenssage.getBytes());
                os.close();
            }
        }



    }

    private TaskService service;
    private ObjectMapper mapper;

    public TaskController (TaskService service){
        this.service = service;
        this.mapper = new ObjectMapper();
    }

}
