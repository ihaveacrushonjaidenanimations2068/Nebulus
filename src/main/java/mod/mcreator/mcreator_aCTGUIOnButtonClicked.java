package mod.mcreator;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.inventory.IInventory;

import java.util.HashMap;

public class mcreator_aCTGUIOnButtonClicked extends nebulus.ModElement {

	public static void executeProcedure(java.util.HashMap<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure aCTGUIOnButtonClicked!");
			return;
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure aCTGUIOnButtonClicked!");
			return;
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure aCTGUIOnButtonClicked!");
			return;
		}
		if (dependencies.get("guiinventory") == null) {
			System.err.println("Failed to load dependency guiinventory for procedure aCTGUIOnButtonClicked!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure aCTGUIOnButtonClicked!");
			return;
		}
		int x = (int) dependencies.get("x");
		int y = (int) dependencies.get("y");
		int z = (int) dependencies.get("z");
		HashMap guiinventory = (HashMap) dependencies.get("guiinventory");
		World world = (World) dependencies.get("world");
		if (((new ItemStack((new Object() {

			public Item getItem(int sltid) {
				IInventory inv = (IInventory) guiinventory.get("inherited");
				if (inv != null) {
					ItemStack stack = inv.getStackInSlot(sltid);
					if (stack != null)
						return stack.getItem();
				}
				return null;
			}
		}.getItem((int) (0))), (int) (1)).getItem() == new ItemStack(mcreator_nebulatedice.block, (int) (1)).getItem()) && (new ItemStack(
				(new Object() {

					public Item getItem(int sltid) {
						IInventory inv = (IInventory) guiinventory.get("inherited");
						if (inv != null) {
							ItemStack stack = inv.getStackInSlot(sltid);
							if (stack != null)
								return stack.getItem();
						}
						return null;
					}
				}.getItem((int) (1))), (int) (1)).getItem() == new ItemStack(mcreator_emberium.block, (int) (1)).getItem()))) {
			{
				TileEntity inv = world.getTileEntity(new BlockPos((int) x, (int) y, (int) z));
				if (inv != null && (inv instanceof TileEntityLockableLoot))
					((TileEntityLockableLoot) inv).setInventorySlotContents((int) (2), new ItemStack(mcreator_motenNebulatedIce.block, (int) (1)));
			}
		}
	}
}
