package mod.mcreator;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.client.model.obj.OBJLoader;

import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

@Mod(modid = nebulus.MODID, version = nebulus.VERSION, dependencies = "required-after:forge@[14.23.5.2768]")
public class nebulus implements IFuelHandler, IWorldGenerator {

	public static final String MODID = "nebulus";
	public static final String VERSION = "4.4.0.4";
	@SidedProxy(clientSide = "mod.mcreator.ClientProxynebulus", serverSide = "mod.mcreator.CommonProxynebulus")
	public static CommonProxynebulus proxy;
	@Instance(MODID)
	public static nebulus instance;
	public static final List<ModElement> elements = new ArrayList<>();

	@Override
	public int getBurnTime(ItemStack fuel) {
		for (ModElement element : elements) {
			int ret = element.addFuel(fuel);
			if (ret != 0)
				return ret;
		}
		return 0;
	}

	@Override
	public void generate(final Random random, int chunkX, int chunkZ, final World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		final int f_chunkX = chunkX * 16;
		final int f_chunkZ = chunkZ * 16;
		if (world.provider.getDimension() == -1)
			elements.forEach(element -> element.generateNether(world, random, f_chunkX, f_chunkZ));
		if (world.provider.getDimension() == 0)
			elements.forEach(element -> element.generateSurface(world, random, f_chunkX, f_chunkZ));
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		GameRegistry.registerFuelHandler(this);
		GameRegistry.registerWorldGenerator(this, 5);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		elements.forEach(element -> element.load(event));
		proxy.registerRenderers(this);
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		elements.forEach(element -> element.serverLoad(event));
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		if (event.getSide() == Side.CLIENT)
			OBJLoader.INSTANCE.addDomain("nebulus");
		elements.forEach(element -> {
			element.instance = this.instance;
			element.preInit(event);
		});
		ResourceLocation sound0 = new ResourceLocation("nebulus", "mob.test.hurt");
		ForgeRegistries.SOUND_EVENTS.register(new net.minecraft.util.SoundEvent(sound0).setRegistryName(sound0));
		ResourceLocation sound1 = new ResourceLocation("nebulus", "mob.test.death");
		ForgeRegistries.SOUND_EVENTS.register(new net.minecraft.util.SoundEvent(sound1).setRegistryName(sound1));
		ResourceLocation sound2 = new ResourceLocation("nebulus", "mob.test.ambient");
		ForgeRegistries.SOUND_EVENTS.register(new net.minecraft.util.SoundEvent(sound2).setRegistryName(sound2));
		ResourceLocation sound3 = new ResourceLocation("nebulus", "block.prodline.noise");
		ForgeRegistries.SOUND_EVENTS.register(new net.minecraft.util.SoundEvent(sound3).setRegistryName(sound3));
	}

	public static class GuiHandler implements IGuiHandler {

		@Override
		public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
			if (id == mcreator_lChest.GUIID)
				return new mcreator_lChest.GuiContainerMod(world, x, y, z, player);
			if (id == mcreator_aTGUI.GUIID)
				return new mcreator_aTGUI.GuiContainerMod(world, x, y, z, player);
			if (id == mcreator_aCTGUI.GUIID)
				return new mcreator_aCTGUI.GuiContainerMod(world, x, y, z, player);
			return null;
		}

		@Override
		public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
			if (id == mcreator_lChest.GUIID)
				return new mcreator_lChest.GuiWindow(world, x, y, z, player);
			if (id == mcreator_aTGUI.GUIID)
				return new mcreator_aTGUI.GuiWindow(world, x, y, z, player);
			if (id == mcreator_aCTGUI.GUIID)
				return new mcreator_aCTGUI.GuiWindow(world, x, y, z, player);
			return null;
		}
	}

	static {
		elements.add(new mcreator_nebulizedgrass());
		elements.add(new mcreator_nebulizeddirt());
		elements.add(new mcreator_nebulusBlocks());
		elements.add(new mcreator_invertedNebulizedGrass());
		elements.add(new mcreator_dullNebulizedDirt());
		elements.add(new mcreator_volcanicnebbricksslab());
		elements.add(new mcreator_nebulizedstone());
		elements.add(new mcreator_nebuwillowLeaves());
		elements.add(new mcreator_nebuwillowLog());
		elements.add(new mcreator_nebuForest());
		elements.add(new mcreator_nebulus());
		elements.add(new mcreator_volcanicSand());
		elements.add(new mcreator_volcanicDust());
		elements.add(new mcreator_nebulusMaterials());
		elements.add(new mcreator_volcanicOre());
		elements.add(new mcreator_volcanicDeserts());
		elements.add(new mcreator_altar());
		elements.add(new mcreator_altarOnBlockRightclicked());
		elements.add(new mcreator_cinderKey());
		elements.add(new mcreator_dungeonKeyholeOnBlockRightclicked());
		elements.add(new mcreator_dungeonKeyhole());
		elements.add(new mcreator_nebuwillowSaplingOnBlockRightclicked());
		elements.add(new mcreator_nebuwillowSapling());
		elements.add(new mcreator_nebulusUblocks());
		elements.add(new mcreator_nebulusdbandi());
		elements.add(new mcreator_wothy());
		elements.add(new mcreator_dungeonDoorFramework());
		elements.add(new mcreator_dungeondoorframe());
		elements.add(new mcreator_refinedVsand());
		elements.add(new mcreator_nebulusc4());
		elements.add(new mcreator_nebulustandw());
		elements.add(new mcreator_nebulizerBulletHitsBlock());
		elements.add(new mcreator_nebulizer());
		elements.add(new mcreator_choleraStone());
		elements.add(new mcreator_nebulizedFruit());
		elements.add(new mcreator_cheripiteOre());
		elements.add(new mcreator_emberium());
		elements.add(new mcreator_emberiumOre());
		elements.add(new mcreator_vALTAR());
		elements.add(new mcreator_lichenium());
		elements.add(new mcreator_nebulizedGas());
		elements.add(new mcreator_emberGrass());
		elements.add(new mcreator_emberrusBandtandwandrm());
		elements.add(new mcreator_nebulizedgrassOnBlockRightclicked());
		elements.add(new mcreator_emberrock());
		elements.add(new mcreator_emberoakLog());
		elements.add(new mcreator_emberoakLeaves());
		elements.add(new mcreator_decrystallizedApple());
		elements.add(new mcreator_emberPlains());
		elements.add(new mcreator_emberrus());
		elements.add(new mcreator_emberoakSapling());
		elements.add(new mcreator_embersapRightClickedOnBlock());
		elements.add(new mcreator_embersap());
		elements.add(new mcreator_emberoakSaplingOnBlockRightclicked());
		elements.add(new mcreator_nebulizingSolutionRightClickedOnBlock());
		elements.add(new mcreator_nebulizingSolution());
		elements.add(new mcreator_test2());
		elements.add(new mcreator_test3());
		elements.add(new mcreator_lChest());
		elements.add(new mcreator_emberoakPlanks());
		elements.add(new mcreator_waterGolem());
		elements.add(new mcreator_nebulizedGlass());
		elements.add(new mcreator_astronaut());
		elements.add(new mcreator_emptyJar());
		elements.add(new mcreator_nEbuliteMeteorouter());
		elements.add(new mcreator_nebuliteMeteorinner());
		elements.add(new mcreator_solurton());
		elements.add(new mcreator_choleratisIV());
		elements.add(new mcreator_choleratis());
		elements.add(new mcreator_leptospyradia());
		elements.add(new mcreator_leptospyradiaa());
		elements.add(new mcreator_nebulized());
		elements.add(new mcreator_reverseEmberizerandNEbulizerRightClickedOnBlock());
		elements.add(new mcreator_reverseEmberizerandNEbulizer());
		elements.add(new mcreator_jar());
		elements.add(new mcreator_emptyJarRightClickedOnBlock());
		elements.add(new mcreator_productionline());
		elements.add(new mcreator_table());
		elements.add(new mcreator_tracker());
		elements.add(new mcreator_nebulusdec());
		elements.add(new mcreator_prodline());
		elements.add(new mcreator_prodlineOnBlockRightclicked());
		elements.add(new mcreator_groundemberium());
		elements.add(new mcreator_volcanicbricks());
		elements.add(new mcreator_chair());
		elements.add(new mcreator_embmaterials());
		elements.add(new mcreator_nebuwillowDoorItem());
		elements.add(new mcreator_nebuwillowDoor());
		elements.add(new mcreator_nebuwillowDoorItemRightClickedOnBlock());
		elements.add(new mcreator_spicyPie());
		elements.add(new mcreator_spicyPieR());
		elements.add(new mcreator_lampoff());
		elements.add(new mcreator_lampcaseOnBlockRightclicked());
		elements.add(new mcreator_lampcase());
		elements.add(new mcreator_lampon());
		elements.add(new mcreator_lampoffOnBlockRightclicked());
		elements.add(new mcreator_emberoakDoor());
		elements.add(new mcreator_nEbuliteMeteor());
		elements.add(new mcreator_lABYRINTHcHEST());
		elements.add(new mcreator_welcome());
		elements.add(new mcreator_solidifiedSolurtonRightClickedOnBlock());
		elements.add(new mcreator_solidifiedSolurton());
		elements.add(new mcreator_mysteriousEgg());
		elements.add(new mcreator_lagSphere());
		elements.add(new mcreator_celestialgrass());
		elements.add(new mcreator_celestialore());
		elements.add(new mcreator_tuberculosiabandi());
		elements.add(new mcreator_solrootLog());
		elements.add(new mcreator_solrootLeaves());
		elements.add(new mcreator_celestialrock());
		elements.add(new mcreator_nebulatedice());
		elements.add(new mcreator_tuberculosiaPlains());
		elements.add(new mcreator_tuberculosia());
		elements.add(new mcreator_tuberculos());
		elements.add(new mcreator_lorepagec4());
		elements.add(new mcreator_nebulusLorebookRightClickedOnBlock());
		elements.add(new mcreator_nebulusLorebook());
		elements.add(new mcreator_lorebookBlock());
		elements.add(new mcreator_moltenNEbulatedIce());
		elements.add(new mcreator_eye());
		elements.add(new mcreator_disappearinggeye());
		elements.add(new mcreator_etherealGrass());
		elements.add(new mcreator_etherealStone());
		elements.add(new mcreator_etherootLeaves());
		elements.add(new mcreator_etherootLog());
		elements.add(new mcreator_hardEtherealStone());
		elements.add(new mcreator_etherealAltarOnBlockRightclicked());
		elements.add(new mcreator_etherealPlains());
		elements.add(new mcreator_lumeniasoPortalTriggerUsed());
		elements.add(new mcreator_lumeniaso());
		elements.add(new mcreator_daemonicAltar());
		elements.add(new mcreator_tallEtherealGRass());
		elements.add(new mcreator_peeringEyeFilament());
		elements.add(new mcreator_moltennIceDispensers());
		elements.add(new mcreator_motenNebulatedIce());
		elements.add(new mcreator_moltennIceDispensersOnBlockRightclicked());
		elements.add(new mcreator_solrootPlanks());
		elements.add(new mcreator_solrootCraftingTable());
		elements.add(new mcreator_solrootDoor());
		elements.add(new mcreator_etherootSaplingOnBlockRightclicked());
		elements.add(new mcreator_etherootSapling());
		elements.add(new mcreator_hythalumCoralBlock());
		elements.add(new mcreator_aquaticTable());
		elements.add(new mcreator_aTGUI());
		elements.add(new mcreator_aquaticTableOnBlockRightclicked());
		elements.add(new mcreator_aCTGUIOnButtonClicked());
		elements.add(new mcreator_aCTGUI());
		elements.add(new mcreator_hythalumTurtle());
		elements.add(new mcreator_hythaliumCoral());
		elements.add(new mcreator_hythaliumDistilledWater());
		elements.add(new mcreator_hythaliumOceans());
		elements.add(new mcreator_hythaliumGrass());
		elements.add(new mcreator_hythaliumGlaciers());
		elements.add(new mcreator_squirralien());
		elements.add(new mcreator_squirralienTongue());
		elements.add(new mcreator_squirralienSword());
		elements.add(new mcreator_squirraliumBricks());
		elements.add(new mcreator_hythaliumplains());
		elements.add(new mcreator_hymelium());
		elements.add(new mcreator_celestialAltar());
		elements.add(new mcreator_infesticaRealmstone());
		elements.add(new mcreator_infesticaRealmstoneRightClickedOnBlock());
		elements.add(new mcreator_infesticaPortal());
		elements.add(new mcreator_weakEmberiumDrill());
		elements.add(new mcreator_infesticaPortalMobplayerColidesBlock());
		elements.add(new mcreator_emberiumGrenade());
		elements.add(new mcreator_emberiumBazookaWhileBulletFlyingTick());
		elements.add(new mcreator_emberiumBazookaBulletHitsBlock());
		elements.add(new mcreator_emberiumBazooka());
		elements.add(new mcreator_infoBlock());
		elements.add(new mcreator_volcanicIdolBlock());
		elements.add(new mcreator_examplePot());
		elements.add(new mcreator_shrinePlan());
		elements.add(new mcreator_volcanicIdolItem());
	}

	public static class ModElement {

		public static Object instance;

		public void load(FMLInitializationEvent event) {
		}

		public void generateNether(World world, Random random, int chunkX, int chunkZ) {
		}

		public void generateSurface(World world, Random random, int chunkX, int chunkZ) {
		}

		public void serverLoad(FMLServerStartingEvent event) {
		}

		public void preInit(FMLPreInitializationEvent event) {
		}

		public void registerRenderers() {
		}

		public int addFuel(ItemStack fuel) {
			return 0;
		}
	}
}
