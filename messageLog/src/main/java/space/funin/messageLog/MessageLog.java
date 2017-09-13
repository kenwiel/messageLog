package space.funin.messageLog;


import java.util.ArrayList;
import java.util.List;

import com.google.common.util.concurrent.FutureCallback;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.Server;

/**
 * Hello world!
 *
 */
public class MessageLog 
{
    public static void main( String[] args )
    {
        DiscordAPI api = Javacord.getApi("MzA3MzIwMzE1MTc0NzE1NDEz.DGtDcA.IG7fCDSYtHPVwrlKBBcf3dkdhOg", true); // true means it's a bot

        api.connect(new FutureCallback<DiscordAPI>() {
            public void onSuccess(final DiscordAPI api) {
                api.setAutoReconnect(true);
                
            	List<Server> serverList = new ArrayList<Server>(api.getServers());
                
                for(Server s : serverList) {
                	api.registerListener(new MyMessageListener(s));
                }
                
            }

            public void onFailure(Throwable t) {
              // login failed
              t.printStackTrace();
            }
          });
    }
}
