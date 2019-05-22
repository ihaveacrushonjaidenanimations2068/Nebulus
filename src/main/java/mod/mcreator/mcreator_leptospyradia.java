package mod.mcreator;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.BiomeDictionary;

import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.WorldServer;
import net.minecraft.world.World;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Rotation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Mirror;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.Block;

import java.util.Random;

public class mcreator_leptospyradia extends nebulus.ModElement {

	public static BiomeGenCustom biome;
	static {
		Biome.BiomeProperties properties = new Biome.BiomeProperties("leptospyradia");
		properties.setRainfall(0.5F);
		properties.setBaseHeight(0.1F);
		properties.setHeightVariation(0.2F);
		properties.setTemperature(0.5F);
		properties.setWaterColor(-26113);
		biome = new BiomeGenCustom(properties);
	}

	@Override
	public void load(FMLInitializationEvent event) {
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, BiomeDictionary.Type.FOREST);
	}

	static class BiomeGenCustom extends Biome {

		public BiomeGenCustom(Biome.BiomeProperties properties) {
			super(properties);
			setRegistryName("leptospyradia");
			topBlock = mcreator_nEbuliteMeteorouter.block.getDefaultState();
			fillerBlock = mcreator_nebuliteMeteorinner.block.getDefaultState();
			decorator.generateFalls = false;
			decorator.treesPerChunk = 3;
			decorator.flowersPerChunk = 10;
			decorator.grassPerChunk = 10;
			decorator.deadBushPerChunk = 0;
			decorator.mushroomsPerChunk = 0;
			decorator.bigMushroomsPerChunk = 0;
			decorator.reedsPerChunk = 0;
			decorator.cactiPerChunk = 0;
			decorator.sandPatchesPerChunk = 0;
			decorator.gravelPatchesPerChunk = 0;
			this.spawnableMonsterList.clear();
			this.spawnableCreatureList.clear();
			this.spawnableWaterCreatureList.clear();
			this.spawnableCaveCreatureList.clear();
			this.spawnableCreatureList.add(new SpawnListEntry(mcreator_astronaut.Entityastronaut.class, 40, 1, 5));
		}

		@SideOnly(Side.CLIENT)
		@Override
		public int getGrassColorAtPos(BlockPos pos) {
			return -26113;
		}

		@SideOnly(Side.CLIENT)
		@Override
		public int getFoliageColorAtPos(BlockPos pos) {
			return -26113;
		}

		@SideOnly(Side.CLIENT)
		@Override
		public int getSkyColorByTemp(float currentTemperature) {
			return -256;
		}

		@Override
		public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
			return new CustomTree();
		}
	}

	static class CustomTree extends WorldGenAbstractTree {

		CustomTree() {
			super(false);
		}

		@Override
		public boolean generate(World world, Random par2Random, BlockPos pos) {
			if (world.isRemote)
				return false;
			Template template = ((WorldServer) world).getStructureTemplateManager().getTemplate(world.getMinecraftServer(),
					new ResourceLocation("nebulus", "nebulite_meteor"));
			if (template == null)
				return false;
			Block ground = world.getBlockState(pos).getBlock();
			Block ground2 = world.getBlockState(pos.add(0, -1, 0)).getBlock();
			if (!(ground == mcreator_nEbuliteMeteorouter.block.getDefaultState().getBlock()
					|| ground == mcreator_nebuliteMeteorinner.block.getDefaultState().getBlock()
					|| ground2 == mcreator_nEbuliteMeteorouter.block.getDefaultState().getBlock() || ground2 == mcreator_nebuliteMeteorinner.block
					.getDefaultState().getBlock()))
				return false;
			Rotation rotation = Rotation.NONE;
			int rot = par2Random.nextInt(3);
			if (rot == 0)
				rotation = Rotation.NONE;
			else if (rot == 1)
				rotation = Rotation.CLOCKWISE_90;
			else if (rot == 2)
				rotation = Rotation.CLOCKWISE_180;
			else if (rot == 3)
				rotation = Rotation.COUNTERCLOCKWISE_90;
			Mirror mirror = Mirror.NONE;
			int mir = par2Random.nextInt(2);
			if (mir == 0)
				mirror = Mirror.NONE;
			else if (mir == 1)
				mirror = Mirror.LEFT_RIGHT;
			else if (mir == 2)
				mirror = Mirror.FRONT_BACK;
			BlockPos placeTo = pos.add(template.getSize().getX() / -2, 0, template.getSize().getZ() / -2);
			IBlockState iblockstate = world.getBlockState(placeTo);
			world.notifyBlockUpdate(placeTo, iblockstate, iblockstate, 3);
			template.addBlocksToWorldChunk(world, placeTo, new PlacementSettings().setRandom(par2Random).setRotation(rotation).setMirror(mirror)
					.setChunk((ChunkPos) null).setReplacedBlock((Block) null).setIgnoreStructureBlock(false).setIgnoreEntities(false));
			return true;
		}

		@Override
		protected boolean canGrowInto(Block blockType) {
			return super.canGrowInto(blockType);
		}

		@Override
		public void generateSaplings(World worldIn, Random random, BlockPos pos) {
		}

		@Override
		protected void setDirtAt(World worldIn, BlockPos pos) {
		}

		@Override
		public boolean isReplaceable(World world, BlockPos pos) {
			return true;
		}
	}
}
