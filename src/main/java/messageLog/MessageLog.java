package messageLog;

import org.javacord.api.AccountType;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class MessageLog {

    public static void main(String[] args) {
        new MessageLog(args[0]);
    }

    public MessageLog(String token) {
        connect(token);
    }

    private void connect(String token) {
        DiscordApi api = new DiscordApiBuilder().setToken(token).setAccountType(AccountType.BOT).login().join();

        api.addMessageDeleteListener(Helper::sendMessage);


        api.addMessageEditListener(Helper::sendMessage);
    }

}
