package me.nanigans.libnanigans.Files;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class YamlGenerator {
    private FileConfiguration data;
    private final JavaPlugin plugin;
    private final File file;
    private boolean isNew = false;

    public YamlGenerator(JavaPlugin plugin, String filePath){
        this.plugin = plugin;
        this.file = new File(filePath);
        load();
        
    }
    
    private void load() {
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
                this.isNew = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.data = YamlConfiguration.loadConfiguration(this.file);
    }
    
    
    public FileConfiguration getData() {
        return this.data;
    }

    public void save() {
        try {
            this.data.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadData() {
        this.data = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean isNew() {
        return isNew;
    }

    /**
     * Creates a new directory with respect to the path
     *
     * @param path the path to make the directory
     * @throws IOException error for when it fails
     */
    public static void createFolder(JavaPlugin plugin, String path) throws IOException {

        File file = new File(plugin.getDataFolder().getAbsolutePath() + "/" + path);
        if (!file.exists()) {
            Path paths = Paths.get(plugin.getDataFolder().getAbsolutePath() + "/" + path);
            Files.createDirectories(paths);
        }
    }

    /**
     * Returns a {@link Map} representative of the passed Object that represents
     * a section of a YAML file. This method neglects the implementation of the
     * section (whether it be {@link ConfigurationSection} or just a
     * {@link Map}), and returns the appropriate value.
     *
     * @param o    The object to interpret
     * @param deep If an object is a {@link ConfigurationSection}, {@code true} to do a deep search
     * @return A {@link Map} representing the section
     * @version 0.1.0
     * @since 0.1.0
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> getConfigSectionValue(Object o, boolean deep) {
        if (o == null) {
            return null;
        }
        Map<String, T> map;
        if (o instanceof ConfigurationSection) {
            map = (Map<String, T>) ((ConfigurationSection) o).getValues(deep);
        } else if (o instanceof Map) {
            map = (Map<String, T>) o;
        } else {
            return null;
        }
        return map;
    }

}