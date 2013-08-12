/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.skywars.game;

import java.util.HashMap;
import java.util.Map;
import net.daboross.bukkitdev.skywars.api.game.SkyCurrentGameTracker;
import net.daboross.bukkitdev.skywars.events.PrepairGameStartEvent;
import net.daboross.bukkitdev.skywars.events.PrepairPlayerLeaveGameEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 *
 * @author daboross
 */
public class CurrentGames implements Listener, SkyCurrentGameTracker {

    private final Map<String, Integer> currentlyInGame = new HashMap<String, Integer>();

    private void setGameID(String player, int gameID) {
        currentlyInGame.put(player.toLowerCase(), Integer.valueOf(gameID));
    }

    @Override
    public boolean isInGame(String player) {
        return currentlyInGame.containsKey(player.toLowerCase());
    }

    @Override
    public int getGameID(String player) {
        Integer val = currentlyInGame.get(player.toLowerCase());
        return val == null ? -1 : val.intValue();
    }

    @EventHandler
    public void onPlayerLeaveGame(PrepairPlayerLeaveGameEvent evt) {
        currentlyInGame.remove(evt.getPlayer().getName().toLowerCase());
    }

    @EventHandler
    public void onGameStart(PrepairGameStartEvent evt) {
        int id = evt.getId();
        for (String name : evt.getNames()) {
            setGameID(name, id);
        }
    }
}
