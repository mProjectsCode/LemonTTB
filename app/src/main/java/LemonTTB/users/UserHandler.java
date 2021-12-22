package LemonTTB.users;

import LemonTTB.App;
import LemonTTB.IOHelper;
import LemonTTB.Logger.Logger;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class UserHandler {
    public final static Logger LOGGER = Logger.getLogger(UserHandler.class);

    public Gson gson;

    public UserHandler(){
        gson = new Gson();
    }

    public User getUserFromID (@NotNull String id) {
        User user = null;
        File userFile = getFileFromID(id);

        if (!userFile.exists()) {
            user = new User(id);
            String userJSON = gson.toJson(user);
            try {
                IOHelper.write(userFile, userJSON);
            } catch (IOException e) {
                App.exit("Could not write user data file.", e);
            }
        } else {
            String userFileContent = IOHelper.load(userFile);
            user = gson.fromJson(userFileContent, User.class);
        }

        return user;
    }

    public void saveUser(User user) {
        try {
            IOHelper.write(getFileFromID(user.getId()), gson.toJson(user));
        } catch (IOException e) {
            App.exit("Could not write user data file.", e);
        }
    }

    public String getFileNameFromID (@NotNull String id) {
        return id.concat("-LemonTTB-user.txt");
    }

    public File getFileFromID (@NotNull String id) {
        return new File(App.userPath, getFileNameFromID(id));
    }
}
