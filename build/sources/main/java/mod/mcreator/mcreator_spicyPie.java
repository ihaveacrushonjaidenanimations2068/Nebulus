package mod.mcreator;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemFood;
import net.minecraft.item.Item;
import net.minecraft.item.EnumAction;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.Minecraft;

public class mcreator_spicyPie extends nebulus.ModElement {

	public static Item block;
	static {
		block = (Item) (new CustomItemFood(4, 0.3F, false)).setAlwaysEdible();
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		block.setMaxStackSize(64);
		block.setCreativeTab(mcreator_nebulusMaterials.tab);
	}

	@Override
	public void load(FMLInitializationEvent event) {
		if (event.getSide() == Side.CLIENT)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
					.register(block, 0, new ModelResourceLocation("nebulus:spicypie", "inventory"));
	}

	public static class CustomItemFood extends ItemFood {

		public CustomItemFood(int par2, float par3, boolean par4) {
			super(par2, par3, par4);
			setUnlocalizedName("spicypie");
			setRegistryName("spicypie");
			ForgeRegistries.ITEMS.register(this);
		}

		public EnumAction getItemUseAction(ItemStack par1ItemStack) {
			return EnumAction.EAT;
		}
	}
}
