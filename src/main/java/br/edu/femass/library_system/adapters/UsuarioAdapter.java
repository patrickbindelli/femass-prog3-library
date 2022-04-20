package br.edu.femass.library_system.adapters;

import br.edu.femass.library_system.model.Usuario;
import com.google.gson.*;

import java.lang.reflect.Type;

public class UsuarioAdapter implements JsonSerializer<Usuario>, JsonDeserializer<Usuario>{
    @Override
    public JsonElement serialize(Usuario usuario, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(usuario.getClass().getSimpleName()));
        Gson gson = new Gson();
        result.add("properties", (JsonObject)gson.toJsonTree(usuario) );
        return result;
    }

    @Override
    public Usuario deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String typeOf = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");


        try {
            Class<? extends Usuario> a = (Class<? extends Usuario>) Class.forName("br.edu.femass.library_system.model." + typeOf);
            Gson gson = new Gson();

            return gson.fromJson(element,a);
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + typeOf, cnfe);
        }
    }
}
