package space.funin.messageLog;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.embed.EmbedBuilder;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.listener.message.MessageCreateListener;
import de.btobastian.javacord.listener.message.MessageDeleteListener;
import de.btobastian.javacord.listener.message.MessageEditListener;

public class MyMessageListener implements MessageCreateListener, MessageEditListener, MessageDeleteListener {
    private Channel log;
    private Server server;
    
    public MyMessageListener(Server server) {
    	List<Channel> channelList = new ArrayList<Channel>(server.getChannels());
    	for(Channel c : channelList) {
    		if (c.getName().equalsIgnoreCase("message_log"))
    			this.log = c;
    	}
    	this.server = server;
    }
    
    public void onMessageCreate(DiscordAPI api, Message message) {
    	Channel channel = message.getChannelReceiver();
    	Server messageServer = channel.getServer();
    	if(!messageServer.getId().equals(server.getId()))
    		return;
    	if(message.getAuthor().isBot())
    		return;
    	if(message.getContent().startsWith("!!id")) {
    		List<Role> mentions = message.getMentionedRoles();
    		EmbedBuilder eb = new EmbedBuilder().setColor(Color.CYAN);
    		for(Role r : mentions) {
				eb.addField(r.getName(), r.getId(), false);
    		}
    		channel.sendMessage("", eb);
    	}
    }

    public void onMessageDelete(DiscordAPI api, Message message) {
        Channel channel = message.getChannelReceiver();
        Server server = channel.getServer();
        User author = message.getAuthor();
        
        if(!server.getId().equals(log.getServer().getId()))
        	return;
        
        EmbedBuilder eb = new EmbedBuilder().setTitle("**Message Deleted:**").setColor(Color.RED);
        eb.addField("**Author: **", author.getName(), false)
        .addField("**Channel: **", channel.getMentionTag(), false)
        .addField("**Content: **", message.getContent(), false);
        
        log.sendMessage("", eb);
    }

    public void onMessageEdit(DiscordAPI api, Message message, String oldContent) {
        Channel channel = message.getChannelReceiver();
        User author = message.getAuthor();
        Server server = channel.getServer();
        
        if(!server.getId().equals(log.getServer().getId()))
        	return;
        
        EmbedBuilder eb = new EmbedBuilder().setTitle("**Message Deleted:**").setColor(Color.ORANGE);
        eb.addField("**Author: **", author.getName(), false)
        .addField("**Channel: **", channel.getMentionTag(), false)
        .addField("**New Content: **", message.getContent(), false)
        .addField("**Old Content: **", oldContent, false);
        
        log.sendMessage("", eb);
    }
    
}