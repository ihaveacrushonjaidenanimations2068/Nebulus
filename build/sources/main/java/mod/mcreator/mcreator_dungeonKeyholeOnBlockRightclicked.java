package mod.mcreator;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;

import java.util.HashMap;

public class mcreator_dungeonKeyholeOnBlockRightclicked extends nebulus.ModElement {

	public static void executeProcedure(java.util.HashMap<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			System.err.println("Failed to load dependency entity for procedure dungeonKeyholeOnBlockRightclicked!");
			return;
		}
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure dungeonKeyholeOnBlockRightclicked!");
			return;
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure dungeonKeyholeOnBlockRightclicked!");
			return;
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure dungeonKeyholeOnBlockRightclicked!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure dungeonKeyholeOnBlockRightclicked!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		int x = (int) dependencies.get("x");
		int y = (int) dependencies.get("y");
		int z = (int) dependencies.get("z");
		World world = (World) dependencies.get("world");
		if (((((world.getBlockState(new BlockPos((int) x, (int) (y + 1), (int) z))).getBlock() == mcreator_volcanicbricks.block.getDefaultState()
				.getBlock()) && ((world.getBlockState(new BlockPos((int) x, (int) (y - 1), (int) z))).getBlock() == mcreator_volcanicbricks.block
				.getDefaultState().getBlock())) && (new ItemStack(((entity instanceof EntityLivingBase) ? ((EntityLivingBase) entity)
				.getHeldItemMainhand().getItem() : null), (int) (1)).getItem() == new ItemStack(mcreator_cinderKey.block, (int) (1)).getItem()))) {
			world.setBlockToAir(new BlockPos((int) x, (int) (y + 1), (int) z));
			world.setBlockToAir(new BlockPos((int) x, (int) y, (int) z));
			world.setBlockToAir(new BlockPos((int) x, (int) (y - 1), (int) z));
		}
	}
}
