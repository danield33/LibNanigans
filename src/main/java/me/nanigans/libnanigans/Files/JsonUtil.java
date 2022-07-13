package me.nanigans.libnanigans.Files;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

    private final JavaPlugin plugin;
    private static final GsonBuilder gsonBuilder = new GsonBuilder()
            .registerTypeAdapter(new TypeToken<Map<String, Object>>() {
            }.getType(), new CustomizedObjectTypeAdapter());

    private Map<String, Object> map;
    private final String path;
    private final File file;

    public JsonUtil(JavaPlugin plugin, String filePath){
        this.plugin = plugin;
        this.path = filePath;
        this.file = new File(plugin.getDataFolder(), path);
        if(!file.exists())
            makeConfigFile(this.file);

        this.map = this.getData(null);
    }

    /**
     * Gets data from a saved map so that you don't have to open the file every time
     * @param path the path in the map to what you're looking for
     * @param <T> the type of data
     * @return the specified value found in the map from the path
     */
    public <T> T get(String path){

        if(path == null || path.isEmpty())
            return (T) this.map;

        final String[] paths = path.split("\\.");
        Object currObj = this.map;

        for(String s : paths){
            if(currObj instanceof Map){
                currObj = ((Map<?, ?>) currObj).get(s);
            }else{
                return (T) currObj;
            }
        }
        return (T) currObj;

    }

    /**
     * Gets json data from the specified file and path in that file
     *
     * @return the data found at the path in the file
     */
    public <T> T getData(String path) {

        if (!file.exists()) {
            makeConfigFile(file);
        }

        try {
            JSONParser jsonParser = new JSONParser();
            Object parsed = jsonParser.parse(new FileReader(file));
            JSONObject jsonObject = (JSONObject) parsed;

            JSONObject currObject = (JSONObject) jsonObject.clone();
            if (path == null) return (T) currObject;
            String[] paths = path.split("\\.");

            for (String s : paths) {

                if (currObject.get(s) instanceof JSONObject)
                    currObject = (JSONObject) currObject.get(s);
                else return (T) currObject.get(s);

            }

            return (T) currObject;
        } catch (IOException | ParseException ignored) {
            return null;
        }
    }

    /**
     * Gets data within the specified map
     *
     * @param map  the map to get data from
     * @param path the path of the data separated by '.'
     * @param <T>  the type of data
     * @return information found at path
     */
    public static <T> T getFromMap(Map<String, Object> map, String path) {

        final String[] split = path.split("\\.");

        Map<String, Object> curObject = map;

        for (String s : split) {
            if (curObject.get(s) instanceof Map)
                curObject = ((Map<String, Object>) curObject.get(s));
            else return (T) curObject.get(s);
        }

        return (T) curObject;

    }

    public void makeConfigFile(File file) {

        plugin.saveResource(file.getName(), false);
        try {
            Gson gson = gsonBuilder.create();
            map = gson.fromJson(new FileReader(file), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}