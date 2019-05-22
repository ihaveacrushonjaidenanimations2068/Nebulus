package mod.mcreator;

import net.minecraft.world.World;
import net.minecraft.util.EnumParticleTypes;

import java.util.HashMap;

public class mcreator_emberiumBazookaWhileBulletFlyingTick extends nebulus.ModElement {

	public static void executeProcedure(java.util.HashMap<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure emberiumBazookaWhileBulletFlyingTick!");
			return;
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure emberiumBazookaWhileBulletFlyingTick!");
			return;
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure emberiumBazookaWhileBulletFlyingTick!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure emberiumBazookaWhileBulletFlyingTick!");
			return;
		}
		int x = (int) dependencies.get("x");
		int y = (int) dependencies.get("y");
		int z = (int) dependencies.get("z");
		World world = (World) dependencies.get("world");
		world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, x, y, z, 0, 1, 0);
	}
}
