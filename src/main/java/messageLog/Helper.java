package messageLog;

import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageDeleteEvent;
import org.javacord.api.event.message.MessageEditEvent;

import java.util.Optional;

public abstract class Helper {

    public static ServerTextChannel findChannel(Server server) {
        Optional<ServerTextChannel> channel = server.getChannelsByNameIgnoreCase("message_log").stream()
                .map(ServerChannel::asServerTextChannel)
                .filter(Optional::isPresent)
                .map(Optional::get).findFirst();

        return channel.orElse(null);
    }

    public static void sendMessage(MessageDeleteEvent event) {
        extract(event.getServer(), event.getMessage(), EditDelete.DELETE);
    }

    public static void sendMessage(MessageEditEvent event) {
        extract(event.getServer(), event.getMessage(), EditDelete.EDIT);

    }

    private static void extract(Optional<Server> server, Optional<Message> messageArg, EditDelete editDelete) {
        if (server.isPresent()) {
            ServerTextChannel target = findChannel(server.get());
            if (messageArg.isPresent()) {
                Message message = messageArg.get();
                sendMessage(message, target, editDelete);
            }
        }
    }

    private static void sendMessage(Message message, ServerTextChannel channel, EditDelete editDelete) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor(message.getAuthor());
        eb.setColor(editDelete.getColor());
        eb.setDescription(message.getContent());
        if (message.getServerTextChannel().isPresent()) {
            eb.setFooter("In channel: " + message.getServerTextChannel().get().getName());
        } else {
            eb.setFooter("In unknown channel.");
        }

        channel.sendMessage(eb);
    }
}
