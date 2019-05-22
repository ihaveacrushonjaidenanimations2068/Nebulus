package mod.mcreator;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Blocks;
import net.minecraft.entity.Entity;

import java.util.HashMap;

public class mcreator_solidifiedSolurtonRightClickedOnBlock extends nebulus.ModElement {

	public static void executeProcedure(java.util.HashMap<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure solidifiedSolurtonRightClickedOnBlock!");
			return;
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure solidifiedSolurtonRightClickedOnBlock!");
			return;
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure solidifiedSolurtonRightClickedOnBlock!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure solidifiedSolurtonRightClickedOnBlock!");
			return;
		}
		int x = (int) dependencies.get("x");
		int y = (int) dependencies.get("y");
		int z = (int) dependencies.get("z");
		World world = (World) dependencies.get("world");
		if (((world.getBlockState(new BlockPos((int) x, (int) y, (int) z))).getBlock() == mcreator_altar.block.getDefaultState().getBlock())) {
			if (!world.isRemote) {
				Entity entityToSpawn = new mcreator_waterGolem.EntitywaterGolem(world);
				if (entityToSpawn != null) {
					entityToSpawn.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360F, 0.0F);
					world.spawnEntity(entityToSpawn);
				}
			}
		} else {
			world.setBlockState(new BlockPos((int) x, (int) y, (int) z), Blocks.FLOWING_WATER.getDefaultState(), 3);
		}
	}
}
