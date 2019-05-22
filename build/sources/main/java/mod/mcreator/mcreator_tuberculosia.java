package mod.mcreator;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.BiomeDictionary;

import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.Block;

import java.util.Random;

public class mcreator_tuberculosia extends nebulus.ModElement {

	public static BiomeGenCustom biome;
	static {
		Biome.BiomeProperties properties = new Biome.BiomeProperties("tuberculosia");
		properties.setRainfall(0.5F);
		properties.setBaseHeight(0.1F);
		properties.setHeightVariation(0.2F);
		properties.setTemperature(0.5F);
		properties.setWaterColor(-52225);
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
			setRegistryName("tuberculosia");
			topBlock = mcreator_celestialrock.block.getDefaultState();
			fillerBlock = mcreator_celestialrock.block.getDefaultState();
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
			return -3355444;
		}

		@SideOnly(Side.CLIENT)
		@Override
		public int getFoliageColorAtPos(BlockPos pos) {
			return -3355444;
		}

		@SideOnly(Side.CLIENT)
		@Override
		public int getSkyColorByTemp(float currentTemperature) {
			return -65281;
		}

		@Override
		public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
			return new CustomTree();
		}
	}

	static class CustomTree extends WorldGenAbstractTree {

		public CustomTree() {
			super(false);
		}

		@Override
		public boolean generate(World worldIn, Random rand, BlockPos position) {
			int i = rand.nextInt(3) + 10;
			boolean flag = true;
			if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getHeight()) {
				for (int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
					int k = 1;
					if (j == position.getY()) {
						k = 0;
					}
					if (j >= position.getY() + 1 + i - 2) {
						k = 2;
					}
					BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
					for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
						for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
							if (j >= 0 && j < worldIn.getHeight()) {
								if (!this.isReplaceable(worldIn, blockpos$mutableblockpos.setPos(l, j, i1))) {
									flag = false;
								}
							} else {
								flag = false;
							}
						}
					}
				}
				if (!flag) {
					return false;
				} else {
					Block ground = worldIn.getBlockState(position).getBlock();
					Block ground2 = worldIn.getBlockState(position.add(0, -1, 0)).getBlock();
					if (!(ground == mcreator_celestialrock.block.getDefaultState().getBlock()
							|| ground == mcreator_celestialrock.block.getDefaultState().getBlock()
							|| ground2 == mcreator_celestialrock.block.getDefaultState().getBlock() || ground2 == mcreator_celestialrock.block
							.getDefaultState().getBlock()))
						return false;
					IBlockState state = worldIn.getBlockState(position.down());
					if (position.getY() < worldIn.getHeight() - i - 1) {
						state.getBlock().onPlantGrow(state, worldIn, position.down(), position);
						int k2 = 3;
						int l2 = 0;
						for (int i3 = position.getY() - 3 + i; i3 <= position.getY() + i; ++i3) {
							int i4 = i3 - (position.getY() + i);
							int j1 = 1 - i4 / 2;
							for (int k1 = position.getX() - j1; k1 <= position.getX() + j1; ++k1) {
								int l1 = k1 - position.getX();
								for (int i2 = position.getZ() - j1; i2 <= position.getZ() + j1; ++i2) {
									int j2 = i2 - position.getZ();
									if (Math.abs(l1) != j1 || Math.abs(j2) != j1 || rand.nextInt(2) != 0 && i4 != 0) {
										BlockPos blockpos = new BlockPos(k1, i3, i2);
										state = worldIn.getBlockState(blockpos);
										if (state.getBlock().isAir(state, worldIn, blockpos) || state.getBlock().isLeaves(state, worldIn, blockpos)
												|| state.getBlock() == mcreator_nebulatedice.block.getDefaultState().getBlock()) {
											this.setBlockAndNotifyAdequately(worldIn, blockpos, mcreator_emberoakLeaves.block.getDefaultState());
										}
									}
								}
							}
						}
						for (int j3 = 0; j3 < i; ++j3) {
							BlockPos upN = position.up(j3);
							state = worldIn.getBlockState(upN);
							if (state.getBlock().isAir(state, worldIn, upN) || state.getBlock().isLeaves(state, worldIn, upN)
									|| state.getBlock() == mcreator_nebulatedice.block.getDefaultState().getBlock()) {
								this.setBlockAndNotifyAdequately(worldIn, position.up(j3), mcreator_solrootLog.block.getDefaultState());
							}
						}
						return true;
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		}

		private void placeFruits(World worldIn, int p_181652_2_, BlockPos pos, EnumFacing side) {
			this.setBlockAndNotifyAdequately(worldIn, pos, mcreator_nebuwillowSapling.block.getDefaultState());
		}

		private void addVine(World worldIn, BlockPos pos) {
			this.setBlockAndNotifyAdequately(worldIn, pos, mcreator_nebulatedice.block.getDefaultState());
		}

		private void addHangingVine(World worldIn, BlockPos pos) {
			this.addVine(worldIn, pos);
			int i = 4;
			for (BlockPos blockpos = pos.down(); worldIn.isAirBlock(blockpos) && i > 0; --i) {
				this.addVine(worldIn, blockpos);
				blockpos = blockpos.down();
			}
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
