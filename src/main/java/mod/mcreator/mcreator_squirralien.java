package mod.mcreator;

import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.Minecraft;

import java.util.Iterator;
import java.util.ArrayList;

public class mcreator_squirralien extends nebulus.ModElement {

	public static int mobid = 30;
	public static int mobid2 = 31;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		EntityRegistry.registerModEntity(new ResourceLocation("nebulus:squirralien"), Entitysquirralien.class, "squirralien", mobid, instance, 64, 1,
				true, -3407872, -13369549);
	}

	private Biome[] allbiomes(net.minecraft.util.registry.RegistryNamespaced<ResourceLocation, Biome> in) {
		Iterator<Biome> itr = in.iterator();
		ArrayList<Biome> ls = new ArrayList<Biome>();
		while (itr.hasNext())
			ls.add(itr.next());
		return ls.toArray(new Biome[ls.size()]);
	}

	@Override
	public void registerRenderers() {
		RenderLiving customRender = new RenderLiving(Minecraft.getMinecraft().getRenderManager(), new Modelsquirralein(), 0) {

			protected ResourceLocation getEntityTexture(Entity par1Entity) {
				return new ResourceLocation("squirralien (2).png");
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(Entitysquirralien.class, customRender);
	}

	public static class Entitysquirralien extends EntityMob {

		public Entitysquirralien(World world) {
			super(world);
			setSize(0.6f, 1.8f);
			experienceValue = 5;
			this.isImmuneToFire = false;
			setNoAI(!true);
			enablePersistence();
			this.tasks.addTask(1, new EntityAIWander(this, 1));
			this.tasks.addTask(2, new EntityAILookIdle(this));
			this.tasks.addTask(3, new EntityAISwimming(this));
			this.tasks.addTask(4, new EntityAILeapAtTarget(this, (float) 0.8));
			this.tasks.addTask(5, new EntityAIPanic(this, 1.2));
			this.targetTasks.addTask(6, new EntityAIHurtByTarget(this, false));
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(mcreator_squirralienSword.block, (int) (1)));
		}

		@Override
		public EnumCreatureAttribute getCreatureAttribute() {
			return EnumCreatureAttribute.UNDEFINED;
		}

		@Override
		protected boolean canDespawn() {
			return false;
		}

		@Override
		protected Item getDropItem() {
			return new ItemStack(mcreator_squirralienSword.block, (int) (1)).getItem();
		}

		@Override
		public net.minecraft.util.SoundEvent getAmbientSound() {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.enderdragon.growl"));
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.enderdragon.hurt"));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.endermen.death"));
		}

		@Override
		protected float getSoundVolume() {
			return 1.0F;
		}

		@Override
		protected void applyEntityAttributes() {
			super.applyEntityAttributes();
			this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(36D);
			if (this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null)
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3D);
		}
	}

	// Made with Blockbench
	// Paste this code into your mod.
	public static class Modelsquirralein extends ModelBase {

		private final ModelRenderer bone;
		private final ModelRenderer bone2;
		private final ModelRenderer bone3;
		private final ModelRenderer bone4;
		private final ModelRenderer bone5;
		private final ModelRenderer bone6;
		private final ModelRenderer bone7;
		private final ModelRenderer bone8;
		private final ModelRenderer bone9;

		public Modelsquirralein() {
			textureWidth = 96;
			textureHeight = 96;
			bone = new ModelRenderer(this);
			bone.setRotationPoint(0.0F, 24.0F, 0.0F);
			bone.cubeList.add(new ModelBox(bone, 42, 24, -6.0F, -1.0F, -4.0F, 5, 1, 5, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 4, 87, -6.0F, -1.0F, -5.0F, 1, 1, 1, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 0, 87, -4.0F, -1.0F, -5.0F, 1, 1, 1, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 52, 84, -2.0F, -1.0F, -5.0F, 1, 1, 1, 0.0F, false));
			bone2 = new ModelRenderer(this);
			bone2.setRotationPoint(0.0F, 24.0F, 0.0F);
			bone2.cubeList.add(new ModelBox(bone2, 22, 24, 0.0F, -1.0F, -4.0F, 5, 1, 5, 0.0F, false));
			bone2.cubeList.add(new ModelBox(bone2, 48, 84, 0.0F, -1.0F, -5.0F, 1, 1, 1, 0.0F, false));
			bone2.cubeList.add(new ModelBox(bone2, 44, 84, 2.0F, -1.0F, -5.0F, 1, 1, 1, 0.0F, false));
			bone2.cubeList.add(new ModelBox(bone2, 40, 84, 4.0F, -1.0F, -5.0F, 1, 1, 1, 0.0F, false));
			bone3 = new ModelRenderer(this);
			bone3.setRotationPoint(0.0F, 24.0F, 0.0F);
			bone3.cubeList.add(new ModelBox(bone3, 16, 77, 2.0F, -7.0F, -2.0F, 1, 6, 1, 0.0F, false));
			bone3.cubeList.add(new ModelBox(bone3, 12, 77, -4.0F, -7.0F, -2.0F, 1, 6, 1, 0.0F, false));
			bone3.cubeList.add(new ModelBox(bone3, 8, 77, 2.0F, -13.0F, -1.0F, 1, 6, 1, 0.0F, false));
			bone3.cubeList.add(new ModelBox(bone3, 4, 77, -4.0F, -13.0F, -1.0F, 1, 6, 1, 0.0F, false));
			bone3.cubeList.add(new ModelBox(bone3, 0, 77, -4.0F, -7.0F, -2.0F, 1, 6, 1, 0.0F, false));
			bone3.cubeList.add(new ModelBox(bone3, 32, 69, 2.0F, -7.0F, -2.0F, 1, 6, 1, 0.0F, false));
			bone3.cubeList.add(new ModelBox(bone3, 20, 69, -4.0F, -20.0F, 0.0F, 1, 7, 1, 0.0F, false));
			bone3.cubeList.add(new ModelBox(bone3, 16, 69, 2.0F, -20.0F, 0.0F, 1, 7, 1, 0.0F, false));
			bone4 = new ModelRenderer(this);
			bone4.setRotationPoint(0.0F, 0.0F, 0.0F);
			bone3.addChild(bone4);
			bone4.cubeList.add(new ModelBox(bone4, 40, 41, -4.0F, -24.0F, -2.0F, 1, 4, 5, 0.0F, false));
			bone4.cubeList.add(new ModelBox(bone4, 28, 41, 2.0F, -24.0F, -2.0F, 1, 4, 5, 0.0F, false));
			bone4.cubeList.add(new ModelBox(bone4, 12, 41, -3.0F, -23.0F, -1.0F, 5, 2, 3, 0.0F, false));
			bone4.cubeList.add(new ModelBox(bone4, 20, 77, -1.0F, -28.0F, 0.0F, 1, 5, 1, 0.0F, false));
			bone4.cubeList.add(new ModelBox(bone4, 8, 69, -1.0F, -25.0F, 1.0F, 1, 1, 3, 0.0F, false));
			bone4.cubeList.add(new ModelBox(bone4, 36, 84, -1.0F, -26.0F, 1.0F, 1, 1, 1, 0.0F, false));
			bone4.cubeList.add(new ModelBox(bone4, 0, 41, 1.0F, -52.0F, -2.0F, 1, 4, 5, 0.0F, false));
			bone4.cubeList.add(new ModelBox(bone4, 48, 32, -3.0F, -52.0F, -2.0F, 1, 4, 5, 0.0F, false));
			bone5 = new ModelRenderer(this);
			bone5.setRotationPoint(0.0F, 24.0F, 0.0F);
			bone5.cubeList.add(new ModelBox(bone5, 24, 0, -4.0F, -36.0F, -3.0F, 7, 8, 4, 0.0F, false));
			bone5.cubeList.add(new ModelBox(bone5, 0, 73, -1.0F, -29.0F, 1.0F, 1, 1, 3, 0.0F, false));
			bone5.cubeList.add(new ModelBox(bone5, 32, 84, -1.0F, -30.0F, 1.0F, 1, 1, 1, 0.0F, false));
			bone5.cubeList.add(new ModelBox(bone5, 26, 59, -1.0F, -22.0F, 2.0F, 1, 8, 1, 0.0F, false));
			bone5.cubeList.add(new ModelBox(bone5, 0, 50, -2.0F, -14.0F, 2.0F, 3, 4, 3, 0.0F, false));
			bone5.cubeList.add(new ModelBox(bone5, 0, 0, -2.0F, -12.0F, 5.0F, 3, 5, 9, 0.0F, false));
			bone5.cubeList.add(new ModelBox(bone5, 0, 24, -3.0F, -9.0F, 14.0F, 5, 2, 6, 0.0F, false));
			bone5.cubeList.add(new ModelBox(bone5, 28, 77, 0.0F, -13.0F, 18.0F, 1, 4, 1, 0.0F, false));
			bone5.cubeList.add(new ModelBox(bone5, 24, 77, -2.0F, -13.0F, 18.0F, 1, 4, 1, 0.0F, false));
			bone5.cubeList.add(new ModelBox(bone5, 0, 69, -1.0F, -32.0F, 1.0F, 1, 1, 3, 0.0F, false));
			bone5.cubeList.add(new ModelBox(bone5, 28, 84, -1.0F, -33.0F, 1.0F, 1, 1, 1, 0.0F, false));
			bone6 = new ModelRenderer(this);
			bone6.setRotationPoint(0.0F, 24.0F, 0.0F);
			bone6.cubeList.add(new ModelBox(bone6, 24, 84, 3.0F, -36.0F, -1.0F, 1, 1, 1, 0.0F, false));
			bone6.cubeList.add(new ModelBox(bone6, 22, 59, 4.0F, -36.0F, -1.0F, 1, 9, 1, 0.0F, false));
			bone6.cubeList.add(new ModelBox(bone6, 20, 84, -5.0F, -36.0F, -1.0F, 1, 1, 1, 0.0F, false));
			bone6.cubeList.add(new ModelBox(bone6, 18, 59, -6.0F, -36.0F, -1.0F, 1, 9, 1, 0.0F, false));
			bone7 = new ModelRenderer(this);
			bone7.setRotationPoint(0.0F, 24.0F, 0.0F);
			bone7.cubeList.add(new ModelBox(bone7, 0, 32, 4.0F, -28.0F, -8.0F, 1, 1, 7, 0.0F, false));
			bone7.cubeList.add(new ModelBox(bone7, 62, 24, -6.0F, -28.0F, -8.0F, 1, 1, 7, 0.0F, false));
			bone7.cubeList.add(new ModelBox(bone7, 12, 50, 4.0F, -31.0F, -11.0F, 1, 6, 3, 0.0F, false));
			bone7.cubeList.add(new ModelBox(bone7, 32, 32, -7.0F, -29.0F, -12.0F, 4, 3, 4, 0.0F, false));
			bone7.cubeList.add(new ModelBox(bone7, 0, 64, 4.0F, -26.0F, -15.0F, 1, 1, 4, 0.0F, false));
			bone7.cubeList.add(new ModelBox(bone7, 0, 59, 4.0F, -28.0F, -15.0F, 1, 1, 4, 0.0F, false));
			bone7.cubeList.add(new ModelBox(bone7, 50, 50, 4.0F, -30.0F, -15.0F, 1, 1, 4, 0.0F, false));
			bone7.cubeList.add(new ModelBox(bone7, 40, 50, 4.0F, -32.0F, -14.0F, 1, 1, 4, 0.0F, false));
			bone8 = new ModelRenderer(this);
			bone8.setRotationPoint(0.0F, 24.0F, 0.0F);
			bone8.cubeList.add(new ModelBox(bone8, 16, 32, -2.0F, -42.0F, -3.0F, 3, 2, 5, 0.0F, false));
			bone8.cubeList.add(new ModelBox(bone8, 52, 41, -2.0F, -40.0F, -2.0F, 3, 4, 3, 0.0F, false));
			bone8.cubeList.add(new ModelBox(bone8, 40, 14, -2.0F, -49.0F, -5.0F, 3, 3, 7, 0.0F, false));
			bone8.cubeList.add(new ModelBox(bone8, 10, 59, -2.0F, -46.0F, 2.0F, 3, 5, 1, 0.0F, false));
			bone8.cubeList.add(new ModelBox(bone8, 16, 84, 0.0F, -43.0F, 0.0F, 1, 1, 1, 0.0F, false));
			bone8.cubeList.add(new ModelBox(bone8, 0, 84, -1.0F, -46.0F, -5.0F, 1, 2, 1, 0.0F, false));
			bone8.cubeList.add(new ModelBox(bone8, 12, 84, 0.0F, -43.0F, -2.0F, 1, 1, 1, 0.0F, false));
			bone8.cubeList.add(new ModelBox(bone8, 8, 84, -2.0F, -43.0F, -2.0F, 1, 1, 1, 0.0F, false));
			bone8.cubeList.add(new ModelBox(bone8, 4, 84, -2.0F, -43.0F, 0.0F, 1, 1, 1, 0.0F, false));
			bone8.cubeList.add(new ModelBox(bone8, 20, 14, -2.0F, -49.0F, 2.0F, 3, 3, 7, 0.0F, false));
			bone8.cubeList.add(new ModelBox(bone8, 32, 77, -1.0F, -42.0F, 9.0F, 1, 2, 1, 0.0F, false));
			bone8.cubeList.add(new ModelBox(bone8, 0, 14, -2.0F, -52.0F, -2.0F, 3, 3, 7, 0.0F, false));
			bone9 = new ModelRenderer(this);
			bone9.setRotationPoint(0.0F, 24.0F, 0.0F);
			bone9.cubeList.add(new ModelBox(bone9, 46, 0, -2.0F, -46.0F, 3.0F, 3, 4, 7, 0.0F, false));
			bone9.cubeList.add(new ModelBox(bone9, 28, 69, -3.0F, -57.0F, 3.0F, 1, 6, 1, 0.0F, false));
			bone9.cubeList.add(new ModelBox(bone9, 30, 50, 1.0F, -57.0F, 4.0F, 1, 1, 4, 0.0F, false));
			bone9.cubeList.add(new ModelBox(bone9, 24, 69, 1.0F, -57.0F, 3.0F, 1, 6, 1, 0.0F, false));
			bone9.cubeList.add(new ModelBox(bone9, 20, 50, -3.0F, -57.0F, 4.0F, 1, 1, 4, 0.0F, false));
			bone9.cubeList.add(new ModelBox(bone9, 0, 0, -2.0F, -53.0F, -1.0F, 3, 1, 4, 0.0F, false));
		}

		@Override
		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
			bone.render(f5);
			bone2.render(f5);
			bone3.render(f5);
			bone5.render(f5);
			bone6.render(f5);
			bone7.render(f5);
			bone8.render(f5);
			bone9.render(f5);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}
	}
}
