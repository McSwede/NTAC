package net.newtownia.NTAC.Checks.Movement;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MovementBase implements Listener
{
    private Map<UUID, Long> playerStartMoveTimes;
    private Map<UUID, Location> playerStartMoveLocations;

    private ArrayList<AbstractMovementCheck> movementChecks;

    int newMoveTimeThreshold = 500;

    public MovementBase()
    {
        playerStartMoveLocations = new HashMap<>();
        playerStartMoveTimes = new HashMap<>();
        movementChecks = new ArrayList<>();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e)
    {
        Player p = e.getPlayer();
        UUID pUUID = p.getUniqueId();

        if(!playerStartMoveTimes.containsKey(pUUID))
        {
            playerStartMoveTimes.put(pUUID, System.currentTimeMillis());
            playerStartMoveLocations.put(pUUID, e.getFrom());
        }
        else
        {
            if(hasPlayerMoveTimePassed(pUUID, newMoveTimeThreshold))
            {
                playerStartMoveTimes.put(pUUID, System.currentTimeMillis());
                playerStartMoveLocations.put(pUUID, e.getFrom());
            }
        }

        for (AbstractMovementCheck check : movementChecks)
            check.onPlayerMove(e);
    }

    public boolean hasPlayerMoveTimePassed(Player p, int milliseconds)
    {
        return System.currentTimeMillis() >= playerStartMoveTimes.get(p.getUniqueId()) + milliseconds;
    }

    public boolean hasPlayerMoveTimePassed(UUID pUUID, int milliseconds)
    {
        return System.currentTimeMillis() >= playerStartMoveTimes.get(pUUID) + milliseconds;
    }

    public Location getPlayerMoveStartLocation(Player p)
    {
        if(!playerStartMoveLocations.containsKey(p.getUniqueId()))
            return null;

        return playerStartMoveLocations.get(p.getUniqueId());
    }

    public void registerMovementCheck(AbstractMovementCheck movementCheck)
    {
        movementChecks.add(movementCheck);
    }

    public void unregisterMovementCheck(AbstractMovementCheck movementCheck)
    {
        movementChecks.remove(movementCheck);
    }
}