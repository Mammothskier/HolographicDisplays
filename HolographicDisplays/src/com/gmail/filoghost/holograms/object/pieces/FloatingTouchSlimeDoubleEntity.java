package com.gmail.filoghost.holograms.object.pieces;

import org.bukkit.World;

import com.gmail.filoghost.holograms.exception.SpawnFailedException;
import com.gmail.filoghost.holograms.nms.interfaces.HologramWitherSkull;
import com.gmail.filoghost.holograms.nms.interfaces.TouchSlime;
import com.gmail.filoghost.holograms.object.HologramBase;

import static com.gmail.filoghost.holograms.HolographicDisplays.nmsManager;

public class FloatingTouchSlimeDoubleEntity extends FloatingDoubleEntity {

	private static final double VERTICAL_OFFSET = -0.3;

	private TouchSlime slime;
	private HologramWitherSkull skull;
	
	public FloatingTouchSlimeDoubleEntity() {
	}
	
	@Override
	public void spawn(HologramBase parent, World bukkitWorld, double x, double y, double z) throws SpawnFailedException {
		despawn();
		
		slime = nmsManager.spawnTouchSlime(bukkitWorld, x, y + VERTICAL_OFFSET, z, parent);
		skull = nmsManager.spawnHologramWitherSkull(bukkitWorld, x, y + VERTICAL_OFFSET, z, parent);
		
		// Let the slime ride the wither skull.
		skull.setPassengerNMS(slime);

		slime.setLockTick(true);
		skull.setLockTick(true);
	}

	@Override
	public void despawn() {
		if (slime != null) {
			slime.killEntityNMS();
			slime = null;
		}
		
		if (skull != null) {
			skull.killEntityNMS();
			skull = null;
		}
	}
	
	public boolean isSpawned() {
		return slime != null && skull != null;
	}
	
	@Override
	public void teleport(double x, double y, double z) {
		if (skull != null) {
			skull.setLocationNMS(x, y + VERTICAL_OFFSET, z);
			skull.sendUpdatePacketNear();
		}
	}
}
