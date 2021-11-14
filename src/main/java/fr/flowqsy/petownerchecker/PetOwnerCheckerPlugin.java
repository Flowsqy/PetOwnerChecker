package fr.flowqsy.petownerchecker;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.logging.Logger;

public class PetOwnerCheckerPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        final Logger logger = getLogger();
        final File dataFolder = getDataFolder();

        if (!checkDataFolder(dataFolder)) {
            logger.warning("Can not write in the directory : " + dataFolder.getAbsolutePath());
            logger.warning("Disable the plugin");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        final YamlConfiguration configuration = initFile(dataFolder, "config.yml");

        Bukkit.getPluginManager().registerEvents(new PetOwnerCheckerListener(configuration), this);
    }

    private boolean checkDataFolder(File dataFolder) {
        if (dataFolder.exists())
            return dataFolder.canWrite();
        return dataFolder.mkdirs();
    }

    private YamlConfiguration initFile(File dataFolder, String fileName) {
        final File file = new File(dataFolder, fileName);
        if (!file.exists()) {
            try {
                Files.copy(Objects.requireNonNull(getResource(fileName)), file.toPath());
            } catch (IOException ignored) {
            }
        }

        return YamlConfiguration.loadConfiguration(file);
    }

}