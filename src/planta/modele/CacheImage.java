package planta.modele;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;


public class CacheImage {
    private static final Map<String, Image> cache = new HashMap<>();

    public static Image get(String url, int larg, int haut) {
        return cache.computeIfAbsent(url, key -> new Image(key, larg, haut, false, true));
    }
}
