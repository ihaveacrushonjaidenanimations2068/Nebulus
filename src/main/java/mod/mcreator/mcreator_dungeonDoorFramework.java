package mod.mcreator;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import net.minecraft.util.BlockRenderLayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block;

public class mcreator_dungeonDoorFramework extends nebulus.ModElement {

	public static Block block;
	static {
		block = new BlockCustom().setHardness(2F).setResistance(10F).setLightLevel(0F).setUnlocalizedName("dungeondoorframework").setLightOpacity(0)
				.setCreativeTab(null);
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		block.setHarvestLevel("pickaxe", 1);
		block.setRegistryName("dungeondoorframework");
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	@Override
	public void load(FMLInitializationEvent event) {
		if (event.getSide() == Side.CLIENT) {
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
					.register(Item.getItemFromBlock(block), 0, new ModelResourceLocation("nebulus:dungeondoorframework", "inventory"));
		}
	}

	public static class BlockCustom extends Block {

		public BlockCustom() {
			super(Material.ANVIL);
			setSoundType(SoundType.METAL);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public BlockRenderLayer getBlockLayer() {
			return BlockRenderLayer.SOLID;
		}

		@Override
		public boolean isOpaqueCube(IBlockState state) {
			return false;
		}
	}
}
