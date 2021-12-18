package LemonTTB.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

/**
 * The type Command description.
 */
public class CommandDescription {
    private String description;
    private ArgumentDescription[] argumentDescriptions;

    /**
     * Instantiates a new Command description.
     *
     * @param description          the description
     * @param argumentDescriptions the argument descriptions
     */
    public CommandDescription(String description, ArgumentDescription[] argumentDescriptions) {
        this.description = description;
        this.argumentDescriptions = argumentDescriptions;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get argument descriptions argument description [ ].
     *
     * @return the argument description [ ]
     */
    public ArgumentDescription[] getArgumentDescriptions() {
        return argumentDescriptions;
    }

    /**
     * Sets argument descriptions.
     *
     * @param argumentDescriptions the argument descriptions
     */
    public void setArgumentDescriptions(ArgumentDescription[] argumentDescriptions) {
        this.argumentDescriptions = argumentDescriptions;
    }

    public static EmbedBuilder parseDescriptionAsEmbed(Command command, String title) {
        CommandDescription commandDescription = command.getCommandDescription();
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle(title);
        embed.setDescription(commandDescription.getDescription());
        for (int i = 0; i < commandDescription.getArgumentDescriptions().length; i++) {
            embed.addBlankField(false);
            embed.addField("Argument: ", commandDescription.getArgumentDescriptions()[i].getIdentifier(), true);
            embed.addField("Type: ", commandDescription.getArgumentDescriptions()[i].getType(), true);
            embed.addField("Required: ", Boolean.toString(commandDescription.getArgumentDescriptions()[i].isRequired()), true);
            embed.addField("Description: ", commandDescription.getArgumentDescriptions()[i].getDescription(), false);
        }

        return  embed;
    }

    public static String parseDescriptionAsString(Command command, String title) {
        CommandDescription commandDescription = command.getCommandDescription();

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("Name: ").append(title).append("\n");
        sb.append("Description: ").append(commandDescription.getDescription()).append("\n");
        sb.append("Arguments:").append("\n");
        for (int i = 0; i < commandDescription.getArgumentDescriptions().length; i++) {
            sb.append("    ");
            sb.append("Argument: ").append(commandDescription.getArgumentDescriptions()[i].identifier);
            sb.append("\n").append("    ").append("    ");
            sb.append("Type: ").append(commandDescription.getArgumentDescriptions()[i].getType());
            sb.append("\n").append("    ").append("    ");
            sb.append("Required: ").append(commandDescription.getArgumentDescriptions()[i].isRequired());
            sb.append("\n").append("    ").append("    ");
            sb.append("Description: ").append(commandDescription.getArgumentDescriptions()[i].getDescription());
            sb.append("\n");
        }

        return  sb.toString();
    }

    /**
     * The type Argument description.
     */
    public static class ArgumentDescription {
        private String identifier;
        private String type;
        private boolean required;
        private String description;

        /**
         * Instantiates a new Argument description.
         *
         * @param identifier  the identifier
         * @param description the description
         * @param type        the type
         * @param required    the required
         */
        public ArgumentDescription(String identifier, String type, boolean required, String description) {
            this.identifier = identifier;
            this.type = type;
            this.required = required;
            this.description = description;
        }

        /**
         * Gets identifier.
         *
         * @return the identifier
         */
        public String getIdentifier() {
            return identifier;
        }

        /**
         * Sets identifier.
         *
         * @param identifier the identifier
         */
        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        /**
         * Gets type.
         *
         * @return the type
         */
        public String getType() {
            return type;
        }

        /**
         * Sets type.
         *
         * @param type the type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * Is optional boolean.
         *
         * @return the boolean
         */
        public boolean isRequired() {
            return required;
        }

        /**
         * Sets optional.
         *
         * @param required the optional
         */
        public void setRequired(boolean required) {
            this.required = required;
        }

        /**
         * Gets description.
         *
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * Sets description.
         *
         * @param description the description
         */
        public void setDescription(String description) {
            this.description = description;
        }
    }
}
