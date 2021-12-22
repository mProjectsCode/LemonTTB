package LemonTTB.nameMappings;

import LemonTTB.Config;

import java.util.Map;

public class NameMappingsHandler {
    public void createNameMapping(String userName, String name) {
        Config.options.nameMappings.put(userName, name);
        Config.save();
    }

    public String deleteNameMapping(String userName) {
        String name = Config.options.nameMappings.remove(userName);
        Config.save();
        return name;
    }

    public String deleteNameMappingForName(String name) {
        String name1 = Config.options.nameMappings.remove(getUserNameFromName(name));
        Config.save();
        return name1;
    }

    public String getUserNameFromName(String name) {
        String userName = null;
        for (Map.Entry<String, String> entry : Config.options.nameMappings.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
