package LemonTTB.users;

import LemonTTB.Logger.Logger;
import LemonTTB.permissions.Permission;

public class User {
    public final static Logger LOGGER = Logger.getLogger(User.class);

    public final Permission[] defaultPermissions = new Permission[] {Permission.COMMAND};
    private Permission[] permissions;
    private String id;

    public User (String id) {
        this.id = id;
        this.permissions = new Permission[0];
    }

    public Permission[] getPermissions() {
        return permissions;
    }

    public void setPermissions (Permission[] permissions) {
        this.permissions = permissions;
    }

    public boolean hasPermission (Permission permission) {
        for (Permission permission1 : defaultPermissions) {
            if (permission1.equals(permission) || permission1.value > permission.value) {
                return true;
            }
        }

        for (Permission permission1 : permissions) {
            if (permission1.equals(permission) || permission1.value > permission.value) {
                return true;
            }
        }

        return false;
    }

    public boolean hasPermissions (Permission[] permissions) {
        for (Permission permission1 : permissions) {
            if (!hasPermission(permission1)) {
                return false;
            }
        }

        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
