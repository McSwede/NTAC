package net.newtownia.NTAC.Utils;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;

/**
 * Created by Vinc0682 on 17.06.2016.
 */
public class Velocity
{
    private Location startLocation;
    private Vector velocity;

    public Velocity(Location startLocation, Vector velocity)
    {
        this.startLocation = startLocation;
        this.velocity = velocity;
    }

    public Velocity(PlayerVelocityEvent event)
    {
        this.startLocation = event.getPlayer().getLocation();
        this.velocity = event.getVelocity();
    }

    public Location getStartLocation()
    {
        return startLocation;
    }

    public Vector getVelocity()
    {
        return velocity;
    }

    public double getVelocityDistance()
    {
        double vX = velocity.getX();
        double vY = velocity.getY();
        double vZ = velocity.getZ();
        return Math.sqrt(vX * vX + vY * vY + vZ * vZ);
    }

    public boolean isWithinVelocityRange(Location current)
    {
        if (!startLocation.getWorld().getName().equals(current.getWorld().getName()))
            return false;

        double dX = current.getX() - startLocation.getX();
        double dY = current.getY() - startLocation.getY();
        double dZ = current.getZ() - startLocation.getZ();
        double currentDistance = Math.sqrt(dX * dX + dY * dY + dZ * dZ);

        return currentDistance < getVelocityDistance() * 4;
    }
}
