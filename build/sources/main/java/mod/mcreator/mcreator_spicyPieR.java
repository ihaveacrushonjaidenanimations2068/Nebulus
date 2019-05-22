package mod.mcreator;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import net.minecraft.util.ResourceLocation;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ItemStack;

public class mcreator_spicyPieR extends nebulus.ModElement {

	@Override
	public void load(FMLInitializationEvent event) {
		ItemStack recStack = new ItemStack(mcreator_spicyPie.block, (int) (1));
		Object[] recipe = {" 1 ", "345", "678", '1', Ingredient.fromStacks(new ItemStack(mcreator_emberrus.block, (int) (1))), '3',
				Ingredient.fromStacks(new ItemStack(mcreator_decrystallizedApple.block, (int) (1))), '4',
				Ingredient.fromStacks(new ItemStack(mcreator_groundemberium.block, (int) (1))), '5',
				Ingredient.fromStacks(new ItemStack(mcreator_decrystallizedApple.block, (int) (1))), '6',
				Ingredient.fromStacks(new ItemStack(mcreator_nebulus.block, (int) (1))), '7',
				Ingredient.fromStacks(new ItemStack(mcreator_decrystallizedApple.block, (int) (1))), '8',
				Ingredient.fromStacks(new ItemStack(mcreator_nebulus.block, (int) (1))),};
		GameRegistry.addShapedRecipe(new ResourceLocation("nebulus:spicypier"), new ResourceLocation("custom"), recStack, recipe);
	}
}
