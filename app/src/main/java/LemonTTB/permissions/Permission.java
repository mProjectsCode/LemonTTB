package LemonTTB.permissions;

import java.util.Locale;
import java.util.Objects;

/**
 * The values represent levels.
 * A permission of a certain level has automatically all permissions with a lower level.
 */
public enum Permission {
    COMMAND(1),
    AUDIO(2),
    MODERATION(2),
    ADMIN(4),
    OWNER(5);

    public final int value;

    private Permission(int value) {
        this.value = value;
    }

    public static Permission parsePermission(String str) {
        return switch (str.toLowerCase()) {
            case "command" -> COMMAND;
            case "audio" -> AUDIO;
            case "moderation" -> MODERATION;
            case "admin" -> ADMIN;
            case "owner" -> OWNER;
            default -> null;
        };
    }
}
