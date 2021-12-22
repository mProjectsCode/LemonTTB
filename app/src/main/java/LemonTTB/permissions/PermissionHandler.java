package LemonTTB.permissions;

import LemonTTB.App;
import LemonTTB.Config;
import LemonTTB.Logger.Logger;
import LemonTTB.users.User;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PermissionHandler {
    public final static Logger LOGGER = Logger.getLogger(PermissionHandler.class);

    public Gson gson;

    public PermissionHandler() {
        gson = new Gson();
        // on startup make sure the bot owner always has the owner permission
        if (Objects.equals(Config.options.botOwner, null) || Objects.equals(Config.options.botOwner, "")) {
            LOGGER.logWarning("The bot owner is not set in the config. Please set it.");
        } else {
            User botOwner = App.userHandler.getUserFromID(Config.options.botOwner);
            resetPermissions(botOwner);
            addPermissions(botOwner, Permission.OWNER);
        }
    }

    public boolean hasPermissions (@NotNull String id, Permission[] permissions) {
        return App.userHandler.getUserFromID(id).hasPermissions(permissions);
    }

    public void addPermissions (@NotNull User user, Permission[] permissions) {
        Permission[] userPermissions = user.getPermissions();
        Permission[] newUserPermissions = new Permission[userPermissions.length + permissions.length];

        System.arraycopy(userPermissions, 0, newUserPermissions, 0, userPermissions.length);
        System.arraycopy(permissions, 0, newUserPermissions, userPermissions.length, permissions.length);

        user.setPermissions(newUserPermissions);

        App.userHandler.saveUser(user);
    }

    public void addPermissions (@NotNull String id, Permission[] permissions) {
        User user = App.userHandler.getUserFromID(id);
        addPermissions(user, permissions);
    }

    public void addPermissions (@NotNull User user, Permission permission) {
        addPermissions(user, new Permission[] {permission});
    }

    public void addPermissions (@NotNull String id, Permission permission) {
        User user = App.userHandler.getUserFromID(id);
        addPermissions(user, new Permission[] {permission});
    }

    public void resetPermissions (@NotNull User user) {
        user.setPermissions(new Permission[0]);

        App.userHandler.saveUser(user);
    }

    public void resetPermissions (@NotNull String id) {
        User user = App.userHandler.getUserFromID(id);
        resetPermissions(user);
    }
}
