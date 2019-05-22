package mod.mcreator;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import net.minecraft.util.ResourceLocation;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ItemStack;

public class mcreator_vALTAR extends nebulus.ModElement {

	@Override
	public void load(FMLInitializationEvent event) {
		ItemStack recStack = new ItemStack(mcreator_altar.block, (int) (1));
		Object[] recipe = {"012", "345", "678", '0', Ingredient.fromStacks(new ItemStack(mcreator_refinedVsand.block, (int) (1))), '1',
				Ingredient.fromStacks(new ItemStack(mcreator_volcanicDust.block, (int) (1))), '2',
				Ingredient.fromStacks(new ItemStack(mcreator_refinedVsand.block, (int) (1))), '3',
				Ingredient.fromStacks(new ItemStack(mcreator_volcanicnebbricksslab.block, (int) (1))), '4',
				Ingredient.fromStacks(new ItemStack(mcreator_emberium.block, (int) (1))), '5',
				Ingredient.fromStacks(new ItemStack(mcreator_volcanicnebbricksslab.block, (int) (1))), '6',
				Ingredient.fromStacks(new ItemStack(mcreator_volcanicnebbricksslab.block, (int) (1))), '7',
				Ingredient.fromStacks(new ItemStack(mcreator_volcanicnebbricksslab.block, (int) (1))), '8',
				Ingredient.fromStacks(new ItemStack(mcreator_volcanicnebbricksslab.block, (int) (1))),};
		GameRegistry.addShapedRecipe(new ResourceLocation("nebulus:valtar"), new ResourceLocation("custom"), recStack, recipe);
	}
}
