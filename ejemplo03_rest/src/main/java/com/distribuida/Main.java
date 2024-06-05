package com.distribuida;

import com.distribuida.db.Persona;
import com.distribuida.servicios.ServicioPersona;
import com.google.gson.Gson;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

import java.util.List;

public class Main {

    static SeContainer container;

    static List<Persona> listarPersonas(Request req, Response res) {
        res.type("application/json");
        var servicio = container.select(ServicioPersona.class).get();
        return servicio.findAll();
    }

    static Persona buscarPersona(Request req, Response res) {
        res.type("application/json");

        String id = req.params(":id");
        var servicio = container.select(ServicioPersona.class).get();
        var persona = servicio.findById(Integer.valueOf(id));

        if (persona == null) {
            halt(404, "Persona no encontrada");
        }
        return persona;
    }

    static Persona insertarPersona(Request req, Response res) {
        res.type("application/json");

        Gson gson = new Gson();

        Persona nuevaPersona = gson.fromJson(req.body(), Persona.class);

        if (nuevaPersona == null) {
            System.out.println("Ingrese los datos");
        }

        var servicio = container.select(ServicioPersona.class).get();

        servicio.insert(nuevaPersona);

        res.status(201);
        res.body(gson.toJson(nuevaPersona));

        System.out.println("Se insertó la persona");

        return nuevaPersona;
    }

    static String eliminarPersona(Request req, Response res) {
        res.type("application/json");

        String id = req.params(":id");

        var servicio = container.select(ServicioPersona.class).get();
        var persona = servicio.findById(Integer.valueOf(id));

        if(persona != null) {
            servicio.delete(Integer.valueOf(id));
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

        var servicio =  container.select(ServicioPersona.class).get();
        var persona = servicio.findById(personaAcualizada.getId());

        if(persona != null){
            persona.setNombre(personaAcualizada.getNombre());
            persona.setDireccion(personaAcualizada.getDireccion());
            persona.setEdad(personaAcualizada.getEdad());
            servicio.merge(persona);
            halt(200, "Se actualizó la persona");
        }

        halt(404, "No se actualizar esa persona");

        return "";
    }

    public static void main(String[] args) {

        container = SeContainerInitializer.newInstance().initialize();

        port(8080);
        Gson json = new Gson();
        get("/personas", Main::listarPersonas, json::toJson);
        get("/personas/:id", Main::buscarPersona, json::toJson);
        post("/personas", Main::insertarPersona, json::toJson);
        delete("/personas/:id", Main::eliminarPersona);
        put("/personas", Main::actualizarPersona);

    }
}