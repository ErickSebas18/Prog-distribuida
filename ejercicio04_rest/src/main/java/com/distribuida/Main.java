package com.distribuida;

import com.distribuida.db.Persona;
import com.distribuida.service.PersonaService;
import com.google.gson.Gson;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spark.Request;
import spark.Response;

import java.util.List;

import static spark.Spark.*;

public class Main {

    private static AnnotationConfigApplicationContext context;

    private static PersonaService personaService;

    static Persona findById(Request req, Response res){

        res.type("application/json");

        String id = req.params(":id");

        var persona = personaService.findById(Integer.valueOf(id));

        if(persona == null){
            halt("No se pudo encontrar la persona");
        }

        return persona;
    }

    static List<Persona> findAll(Request req, Response res){
        res.type("application/json");

        return personaService.findAll();

    }

    static Persona insertarPersona(Request req, Response res) {
        res.type("application/json");

        Gson gson = new Gson();

        Persona nuevaPersona = gson.fromJson(req.body(), Persona.class);


        if (nuevaPersona == null) {
            System.out.println("Ingrese los datos");
        }

        personaService.insert(nuevaPersona);

        res.status(201);
        res.body(gson.toJson(nuevaPersona));

        System.out.println("Se insertó la persona");

        return nuevaPersona;
    }

    static String eliminarPersona(Request req, Response res) {
        res.type("application/json");

        String id = req.params(":id");

        var persona = personaService.findById(Integer.valueOf(id));

        if(persona != null) {
            personaService.delete(Integer.valueOf(id));
            halt(200, "Se eliminó a esa persona");
        } else {
            halt(404, "No se pudo eliminar a esa persona");
        }
        return "";
    }

    static String actualizarPersona(Request req, Response res){
        res.type("application/json");

        Gson json = new Gson();
        Persona personaAcualizada = json.fromJson(req.body(), Persona.class);

        var persona = personaService.findById(personaAcualizada.getId());

        if(persona != null){
            persona.setNombre(personaAcualizada.getNombre());
            persona.setDireccion(personaAcualizada.getDireccion());
            persona.setEdad(personaAcualizada.getEdad());
            personaService.update(persona);
            halt(200, "Se actualizó la persona");
        }

        halt(404, "No se actualizar esa persona");

        return "";
    }

    public static void main(String[] args) {

        context = new AnnotationConfigApplicationContext("com.distribuida");

        personaService = context.getBean(PersonaService.class);

        Gson gson = new Gson();

        port(8080);

        get("/personas/:id", Main::findById, gson::toJson);
        get("/personas", Main::findAll, gson::toJson);
        post("/personas", Main::insertarPersona, gson::toJson);
        delete("/personas/:id", Main::eliminarPersona, gson::toJson);
        put("/personas", Main::actualizarPersona, gson::toJson);
    }



}