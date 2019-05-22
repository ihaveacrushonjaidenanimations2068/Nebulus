package mod.mcreator;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.client.model.ModelLoader;

import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;

import java.util.Random;

public class mcreator_moltenNEbulatedIce extends nebulus.ModElement {

	public static BlockFluidClassic block = null;
	public static Item item = null;
	private static Fluid fluid = null;
	static {
		FluidRegistry.enableUniversalBucket();
		ResourceLocation still = new ResourceLocation("blocks/mnistill");
		ResourceLocation flowing = new ResourceLocation("blocks/moltennebuliteice");
		fluid = new Fluid("moltennebulatedice", still, flowing).setLuminosity(57).setDensity(1000).setViscosity(997).setGaseous(false);
		FluidRegistry.registerFluid(fluid);
		block = new BlockFluidClassic(fluid, Material.LAVA) {
		};
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		FluidRegistry.addBucketForFluid(fluid);
		block.setUnlocalizedName("moltenNEbulatedIce");
		block.setRegistryName("fluid." + block.getFluid().getName());
		ForgeRegistries.BLOCKS.register(block);
		item = new ItemBlock(block);
		item.setRegistryName("moltenNEbulatedIce");
		ForgeRegistries.ITEMS.register(item);
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		if (server != null && server.worlds.length > 0) {
			World world = server.worlds[0];
			if (!world.isRemote) {
				try {
					registerFluidModel();
				} catch (Exception ignored) {
				}
			}
		} else if (server == null) {
			try {
				registerFluidModel();
			} catch (Exception ignored) {
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private void registerFluidModel() {
		Item item = Item.getItemFromBlock(block);
		ModelBakery.registerItemVariants(item);
		ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {

			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return new ModelResourceLocation("nebulus:moltennebulatedice", "moltennebulatedice");
			}
		});
		ModelLoader.setCustomStateMapper(block, new StateMapperBase() {

			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation("nebulus:moltennebulatedice", "moltennebulatedice");
			}
		});
	}

	@Override
	public void generateSurface(World world, Random random, int chunkX, int chunkZ) {
		int i = chunkX + random.nextInt(16) + 8;
		int j = random.nextInt(256);
		int k = chunkZ + random.nextInt(16) + 8;
		new WorldGenLakes(block).generate(world, random, new BlockPos(i, j, k));
	}
}
