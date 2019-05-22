package mod.mcreator;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.util.EnumHelper;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.Item;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.Minecraft;

public class mcreator_nebulized extends nebulus.ModElement {

	public static Item helmet;
	public static Item body;
	public static Item legs;
	public static Item boots;
	static {
		ItemArmor.ArmorMaterial enuma = EnumHelper.addArmorMaterial("NEBULIZED", "nebulized", 25, new int[]{8, 6, 7, 2}, 18, null, 3.1415926535f);
		helmet = (new ItemArmor(enuma, 0, EntityEquipmentSlot.HEAD)).setUnlocalizedName("nebulizedhelmet");
		helmet.setMaxStackSize(1);
		body = (new ItemArmor(enuma, 0, EntityEquipmentSlot.CHEST)).setUnlocalizedName("nebulizedbody");
		body.setMaxStackSize(1);
		legs = (new ItemArmor(enuma, 0, EntityEquipmentSlot.LEGS)).setUnlocalizedName("nebulizedlegs");
		legs.setMaxStackSize(1);
		boots = (new ItemArmor(enuma, 0, EntityEquipmentSlot.FEET)).setUnlocalizedName("nebulizedboots");
		boots.setMaxStackSize(1);
		helmet.setRegistryName("nebulizedhelmet");
		ForgeRegistries.ITEMS.register(helmet);
		body.setRegistryName("nebulizedbody");
		ForgeRegistries.ITEMS.register(body);
		legs.setRegistryName("nebulizedlegs");
		ForgeRegistries.ITEMS.register(legs);
		boots.setRegistryName("nebulizedboots");
		ForgeRegistries.ITEMS.register(boots);
	}

	@Override
	public void load(FMLInitializationEvent event) {
		if (event.getSide() == Side.CLIENT) {
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
					.register(helmet, 0, new ModelResourceLocation("nebulus:nebulizedhelmet", "inventory"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
					.register(body, 0, new ModelResourceLocation("nebulus:nebulizedbody", "inventory"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
					.register(legs, 0, new ModelResourceLocation("nebulus:nebulizedlegs", "inventory"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
					.register(boots, 0, new ModelResourceLocation("nebulus:nebulizedboots", "inventory"));
		}
		helmet.setCreativeTab(mcreator_nebulustandw.tab);
		body.setCreativeTab(mcreator_nebulustandw.tab);
		legs.setCreativeTab(mcreator_nebulustandw.tab);
		boots.setCreativeTab(mcreator_nebulustandw.tab);
	}
}
