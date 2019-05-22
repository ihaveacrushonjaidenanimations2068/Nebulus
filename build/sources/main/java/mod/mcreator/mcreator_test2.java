package mod.mcreator;

import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.Minecraft;

import java.util.Random;
import java.util.Iterator;
import java.util.ArrayList;

public class mcreator_test2 extends nebulus.ModElement {

	public static int mobid = 4;
	public static int mobid2 = 5;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		EntityRegistry.registerModEntity(new ResourceLocation("nebulus:test2"), Entitytest2.class, "test2", mobid, instance, 64, 1, true, -16777216,
				-26368);
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
		RenderLiving customRender = new RenderLiving(Minecraft.getMinecraft().getRenderManager(), new ModelLabyrinthChestMimic(), 0) {

			protected ResourceLocation getEntityTexture(Entity par1Entity) {
				return new ResourceLocation("active.png");
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(Entitytest2.class, customRender);
	}

	public static class Entitytest2 extends EntityCreature {

		public Entitytest2(World world) {
			super(world);
			setSize(0.6f, 1.8f);
			experienceValue = 5;
			this.isImmuneToFire = true;
			setNoAI(!true);
			this.tasks.addTask(1, new EntityAIWander(this, 1));
			this.tasks.addTask(2, new EntityAITempt(this, 1, new ItemStack(mcreator_nebulizingSolution.block, (int) (1)).getItem(), false));
			this.tasks.addTask(3, new EntityAILookIdle(this));
			this.tasks.addTask(4, new EntityAISwimming(this));
			this.tasks.addTask(5, new EntityAILeapAtTarget(this, (float) 0.8));
			this.tasks.addTask(6, new EntityAIPanic(this, 1.2));
			this.targetTasks.addTask(7, new EntityAIHurtByTarget(this, false));
		}

		@Override
		public EnumCreatureAttribute getCreatureAttribute() {
			return EnumCreatureAttribute.ARTHROPOD;
		}

		@Override
		protected Item getDropItem() {
			return null;
		}

		@Override
		public net.minecraft.util.SoundEvent getAmbientSound() {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation(""));
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation(""));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation(""));
		}

		@Override
		protected float getSoundVolume() {
			return 1.0F;
		}

		@Override
		protected void applyEntityAttributes() {
			super.applyEntityAttributes();
			this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(11D);
			if (this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null)
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4D);
		}

		public void onLivingUpdate() {
			super.onLivingUpdate();
			int i = (int) this.posX;
			int j = (int) this.posY;
			int k = (int) this.posZ;
			Random random = this.rand;
			if (true)
				for (int l = 0; l < 4; ++l) {
					double d0 = (double) ((float) i + random.nextFloat());
					double d1 = (double) ((float) j + random.nextFloat());
					double d2 = (double) ((float) k + random.nextFloat());
					int i1 = random.nextInt(2) * 2 - 1;
					double d3 = ((double) random.nextFloat() - 0.5D) * 0.5D;
					double d4 = ((double) random.nextFloat() - 0.5D) * 0.5D;
					double d5 = ((double) random.nextFloat() - 0.5D) * 0.5D;
					world.spawnParticle(EnumParticleTypes.SPIT, d0, d1, d2, d3, d4, d5);
				}
		}
	}

	public static class ModelLabyrinthChestMimic extends ModelBase {

		ModelRenderer Main_Top;
		ModelRenderer Main_Bottom;
		ModelRenderer Hinge_Left;
		ModelRenderer Hinge_Right;
		ModelRenderer Mouth_Back;
		ModelRenderer Mouth_Side_Right;
		ModelRenderer Mouth_Side_Left;
		ModelRenderer Corner_Bottom_Front_Right;
		ModelRenderer Corner_Bottom_Front_Left;
		ModelRenderer Corner_Bottom_Back_Left;
		ModelRenderer Corner_Bottom_Back_Right;
		ModelRenderer Corner_Top_Back_Left;
		ModelRenderer Corner_Top_Back_Right;
		ModelRenderer Corner_Top_Front_Left;
		ModelRenderer Corner_Top_Front_Right;
		ModelRenderer Leg_Joint_Front_Left;
		ModelRenderer Leg_Joint_Front_Right;
		ModelRenderer Leg_Joint_Back_Left;
		ModelRenderer Leg_Joint_Back_Right;
		ModelRenderer Leg_Front_Right;
		ModelRenderer Leg_Front_Left;
		ModelRenderer Leg_Back_Right;
		ModelRenderer Leg_Back_Left;
		ModelRenderer Leg_2_Front_Left;
		ModelRenderer Leg_2_Front_Right;
		ModelRenderer Leg_2_Back_Left;
		ModelRenderer Leg_2_Back_Right;
		ModelRenderer Foot_Front_Left;
		ModelRenderer Foot_Front_Right;
		ModelRenderer Foot_Back_Right;
		ModelRenderer Foot_Back_Left;

		public ModelLabyrinthChestMimic() {
			textureWidth = 64;
			textureHeight = 128;
			Main_Top = new ModelRenderer(this, 4, 0);
			Main_Top.addBox(-7F, -8F, -14F, 14, 7, 14);
			Main_Top.setRotationPoint(0F, 0F, 7F);
			Main_Top.setTextureSize(64, 128);
			Main_Top.mirror = true;
			setRotation(Main_Top, 0F, 0F, 0F);
			Main_Bottom = new ModelRenderer(this, 4, 56);
			Main_Bottom.addBox(-7F, 0F, -7F, 14, 7, 14);
			Main_Bottom.setRotationPoint(0F, 0F, 0F);
			Main_Bottom.setTextureSize(64, 128);
			Main_Bottom.mirror = true;
			setRotation(Main_Bottom, 0F, 0F, 0F);
			Hinge_Left = new ModelRenderer(this, 26, 30);
			Hinge_Left.addBox(1.5F, -1.5F, -1.5F, 4, 2, 2);
			Hinge_Left.setRotationPoint(0F, 0F, 7F);
			Hinge_Left.setTextureSize(64, 128);
			Hinge_Left.mirror = true;
			setRotation(Hinge_Left, 0F, 0F, 0F);
			Hinge_Right = new ModelRenderer(this, 26, 30);
			Hinge_Right.addBox(-5.5F, -1.5F, -1.5F, 4, 2, 2);
			Hinge_Right.setRotationPoint(0F, 0F, 7F);
			Hinge_Right.setTextureSize(64, 128);
			Hinge_Right.mirror = true;
			setRotation(Hinge_Right, 0F, 0F, 0F);
			Mouth_Back = new ModelRenderer(this, 21, 34);
			Mouth_Back.addBox(-5F, -2F, 5F, 10, 3, 1);
			Mouth_Back.setRotationPoint(0F, 0F, 0F);
			Mouth_Back.setTextureSize(64, 128);
			Mouth_Back.mirror = true;
			setRotation(Mouth_Back, 0F, 0F, 0F);
			Mouth_Side_Right = new ModelRenderer(this, 12, 28);
			Mouth_Side_Right.addBox(-5F, -6F, -5F, 0, 6, 10);
			Mouth_Side_Right.setRotationPoint(0F, 0F, 0F);
			Mouth_Side_Right.setTextureSize(64, 128);
			Mouth_Side_Right.mirror = true;
			setRotation(Mouth_Side_Right, 0F, 0.1745329F, 0F);
			Mouth_Side_Left = new ModelRenderer(this, 32, 28);
			Mouth_Side_Left.addBox(5F, -6F, -5F, 0, 6, 10);
			Mouth_Side_Left.setRotationPoint(0F, 0F, 0F);
			Mouth_Side_Left.setTextureSize(64, 128);
			Mouth_Side_Left.mirror = true;
			setRotation(Mouth_Side_Left, 0F, -0.1745329F, 0F);
			Corner_Bottom_Front_Right = new ModelRenderer(this, 4, 44);
			Corner_Bottom_Front_Right.addBox(-1F, 1F, -10F, 2, 7, 5);
			Corner_Bottom_Front_Right.setRotationPoint(0F, 0F, 0F);
			Corner_Bottom_Front_Right.setTextureSize(64, 128);
			Corner_Bottom_Front_Right.mirror = true;
			setRotation(Corner_Bottom_Front_Right, 0F, 0.7853982F, 0F);
			Corner_Bottom_Front_Left = new ModelRenderer(this, 46, 44);
			Corner_Bottom_Front_Left.addBox(-1F, 1F, -10F, 2, 7, 5);
			Corner_Bottom_Front_Left.setRotationPoint(0F, 0F, 0F);
			Corner_Bottom_Front_Left.setTextureSize(64, 128);
			Corner_Bottom_Front_Left.mirror = true;
			setRotation(Corner_Bottom_Front_Left, 0F, -0.7853982F, 0F);
			Corner_Bottom_Back_Left = new ModelRenderer(this, 46, 44);
			Corner_Bottom_Back_Left.addBox(-1F, 1F, 5F, 2, 7, 5);
			Corner_Bottom_Back_Left.setRotationPoint(0F, 0F, 0F);
			Corner_Bottom_Back_Left.setTextureSize(64, 128);
			Corner_Bottom_Back_Left.mirror = true;
			setRotation(Corner_Bottom_Back_Left, 0F, 0.7853982F, 0F);
			Corner_Bottom_Back_Right = new ModelRenderer(this, 4, 44);
			Corner_Bottom_Back_Right.addBox(-1F, 1F, 5F, 2, 7, 5);
			Corner_Bottom_Back_Right.setRotationPoint(0F, 0F, 0F);
			Corner_Bottom_Back_Right.setTextureSize(64, 128);
			Corner_Bottom_Back_Right.mirror = true;
			setRotation(Corner_Bottom_Back_Right, 0F, -0.7853982F, 0F);
			Corner_Top_Back_Left = new ModelRenderer(this, 44, 21);
			Corner_Top_Back_Left.addBox(4F, -9F, -0.4F, 2, 7, 6);
			Corner_Top_Back_Left.setRotationPoint(0F, 0F, 7F);
			Corner_Top_Back_Left.setTextureSize(64, 128);
			Corner_Top_Back_Left.mirror = true;
			setRotation(Corner_Top_Back_Left, 0F, 0.7853982F, 0F);
			Corner_Top_Back_Right = new ModelRenderer(this, 4, 21);
			Corner_Top_Back_Right.addBox(-5.5F, -9F, -0.6F, 2, 7, 6);
			Corner_Top_Back_Right.setRotationPoint(0F, 0F, 7F);
			Corner_Top_Back_Right.setTextureSize(64, 128);
			Corner_Top_Back_Right.mirror = true;
			setRotation(Corner_Top_Back_Right, 0F, -0.7853982F, 0F);
			Corner_Top_Front_Left = new ModelRenderer(this, 44, 21);
			Corner_Top_Front_Left.addBox(-6F, -9F, -15.3F, 2, 7, 6);
			Corner_Top_Front_Left.setRotationPoint(0F, 0F, 7F);
			Corner_Top_Front_Left.setTextureSize(64, 128);
			Corner_Top_Front_Left.mirror = true;
			setRotation(Corner_Top_Front_Left, 0F, -0.7853982F, 0F);
			Corner_Top_Front_Right = new ModelRenderer(this, 4, 21);
			Corner_Top_Front_Right.addBox(4F, -9F, -15.3F, 2, 7, 6);
			Corner_Top_Front_Right.setRotationPoint(0F, 0F, 7F);
			Corner_Top_Front_Right.setTextureSize(64, 128);
			Corner_Top_Front_Right.mirror = true;
			setRotation(Corner_Top_Front_Right, 0F, 0.7853982F, 0F);
			Leg_Joint_Front_Left = new ModelRenderer(this, 38, 77);
			Leg_Joint_Front_Left.addBox(-1F, -3F, -1.5F, 8, 5, 3);
			Leg_Joint_Front_Left.setRotationPoint(3F, 7F, -3F);
			Leg_Joint_Front_Left.setTextureSize(64, 128);
			Leg_Joint_Front_Left.mirror = true;
			setRotation(Leg_Joint_Front_Left, 0F, 0.7853982F, 0F);
			Leg_Joint_Front_Right = new ModelRenderer(this, 4, 77);
			Leg_Joint_Front_Right.addBox(-7F, -3F, -1.5F, 8, 5, 3);
			Leg_Joint_Front_Right.setRotationPoint(-3F, 7F, -3F);
			Leg_Joint_Front_Right.setTextureSize(64, 128);
			Leg_Joint_Front_Right.mirror = true;
			setRotation(Leg_Joint_Front_Right, 0F, -0.7853982F, 0F);
			Leg_Joint_Back_Left = new ModelRenderer(this, 38, 77);
			Leg_Joint_Back_Left.addBox(-1F, -3F, -1.5F, 8, 5, 3);
			Leg_Joint_Back_Left.setRotationPoint(3F, 7F, 3F);
			Leg_Joint_Back_Left.setTextureSize(64, 128);
			Leg_Joint_Back_Left.mirror = true;
			setRotation(Leg_Joint_Back_Left, 0F, -0.7853982F, 0F);
			Leg_Joint_Back_Right = new ModelRenderer(this, 4, 77);
			Leg_Joint_Back_Right.addBox(-7F, -3F, -1.5F, 8, 5, 3);
			Leg_Joint_Back_Right.setRotationPoint(-3F, 7F, 3F);
			Leg_Joint_Back_Right.setTextureSize(64, 128);
			Leg_Joint_Back_Right.mirror = true;
			setRotation(Leg_Joint_Back_Right, 0F, 0.7853982F, 0F);
			Leg_Front_Right = new ModelRenderer(this, 5, 85);
			Leg_Front_Right.addBox(-1F, 1.5F, -13F, 2, 2, 8);
			Leg_Front_Right.setRotationPoint(-3F, 7F, -3F);
			Leg_Front_Right.setTextureSize(64, 128);
			Leg_Front_Right.mirror = true;
			setRotation(Leg_Front_Right, -0.4363323F, 0.7853982F, 0F);
			Leg_Front_Left = new ModelRenderer(this, 39, 85);
			Leg_Front_Left.addBox(-1F, 1.5F, -13F, 2, 2, 8);
			Leg_Front_Left.setRotationPoint(3F, 7F, -3F);
			Leg_Front_Left.setTextureSize(64, 128);
			Leg_Front_Left.mirror = true;
			setRotation(Leg_Front_Left, -0.4363323F, -0.7853982F, 0F);
			Leg_Back_Right = new ModelRenderer(this, 5, 85);
			Leg_Back_Right.addBox(-1F, 1.5F, 5F, 2, 2, 8);
			Leg_Back_Right.setRotationPoint(-3F, 7F, 3F);
			Leg_Back_Right.setTextureSize(64, 128);
			Leg_Back_Right.mirror = true;
			setRotation(Leg_Back_Right, 0.4363323F, -0.7853982F, 0F);
			Leg_Back_Left = new ModelRenderer(this, 39, 85);
			Leg_Back_Left.addBox(-1F, 1.5F, 5F, 2, 2, 8);
			Leg_Back_Left.setRotationPoint(3F, 7F, 3F);
			Leg_Back_Left.setTextureSize(64, 128);
			Leg_Back_Left.mirror = true;
			setRotation(Leg_Back_Left, 0.4363323F, 0.7853982F, 0F);
			Leg_2_Front_Left = new ModelRenderer(this, 39, 95);
			Leg_2_Front_Left.addBox(7.5F, -11.5F, -1.5F, 7, 3, 3);
			Leg_2_Front_Left.setRotationPoint(3F, 7F, -3F);
			Leg_2_Front_Left.setTextureSize(64, 128);
			Leg_2_Front_Left.mirror = true;
			setRotation(Leg_2_Front_Left, 0F, 0.7853982F, 0.6981317F);
			Leg_2_Front_Right = new ModelRenderer(this, 5, 95);
			Leg_2_Front_Right.addBox(-14.5F, -11.5F, -1.5F, 7, 3, 3);
			Leg_2_Front_Right.setRotationPoint(-3F, 7F, -3F);
			Leg_2_Front_Right.setTextureSize(64, 128);
			Leg_2_Front_Right.mirror = true;
			setRotation(Leg_2_Front_Right, 0F, -0.7853982F, -0.6981317F);
			Leg_2_Back_Left = new ModelRenderer(this, 39, 95);
			Leg_2_Back_Left.addBox(7.5F, -11.5F, -1.5F, 7, 3, 3);
			Leg_2_Back_Left.setRotationPoint(3F, 7F, 3F);
			Leg_2_Back_Left.setTextureSize(64, 128);
			Leg_2_Back_Left.mirror = true;
			setRotation(Leg_2_Back_Left, 0F, -0.7853982F, 0.6981317F);
			Leg_2_Back_Right = new ModelRenderer(this, 5, 95);
			Leg_2_Back_Right.addBox(-14.5F, -11.5F, -1.5F, 7, 3, 3);
			Leg_2_Back_Right.setRotationPoint(-3F, 7F, 3F);
			Leg_2_Back_Right.setTextureSize(64, 128);
			Leg_2_Back_Right.mirror = true;
			setRotation(Leg_2_Back_Right, 0F, 0.7853982F, -0.6981317F);
			Foot_Front_Left = new ModelRenderer(this, 46, 101);
			Foot_Front_Left.addBox(16F, 1F, -0.5F, 2, 3, 1);
			Foot_Front_Left.setRotationPoint(3F, 7F, -3F);
			Foot_Front_Left.setTextureSize(64, 128);
			Foot_Front_Left.mirror = true;
			setRotation(Foot_Front_Left, 0F, 0.7853982F, 0F);
			Foot_Front_Right = new ModelRenderer(this, 12, 101);
			Foot_Front_Right.addBox(-18F, 1F, -0.5F, 2, 3, 1);
			Foot_Front_Right.setRotationPoint(-3F, 7F, -3F);
			Foot_Front_Right.setTextureSize(64, 128);
			Foot_Front_Right.mirror = true;
			setRotation(Foot_Front_Right, 0F, -0.7853982F, 0F);
			Foot_Back_Right = new ModelRenderer(this, 12, 101);
			Foot_Back_Right.addBox(-18F, 1F, -0.5F, 2, 3, 1);
			Foot_Back_Right.setRotationPoint(-3F, 7F, 3F);
			Foot_Back_Right.setTextureSize(64, 128);
			Foot_Back_Right.mirror = true;
			setRotation(Foot_Back_Right, 0F, 0.7853982F, 0F);
			Foot_Back_Left = new ModelRenderer(this, 46, 101);
			Foot_Back_Left.addBox(16F, 1F, -0.5F, 2, 3, 1);
			Foot_Back_Left.setRotationPoint(3F, 7F, 3F);
			Foot_Back_Left.setTextureSize(64, 128);
			Foot_Back_Left.mirror = true;
			setRotation(Foot_Back_Left, 0F, -0.7853982F, 0F);
		}

		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
			super.render(entity, f, f1, f2, f3, f4, f5);
			setRotationAngles(f, f1, f2, f3, f4, f5, entity);
			Main_Top.render(f5);
			Main_Bottom.render(f5);
			Hinge_Left.render(f5);
			Hinge_Right.render(f5);
			Mouth_Back.render(f5);
			Mouth_Side_Right.render(f5);
			Mouth_Side_Left.render(f5);
			Corner_Bottom_Front_Right.render(f5);
			Corner_Bottom_Front_Left.render(f5);
			Corner_Bottom_Back_Left.render(f5);
			Corner_Bottom_Back_Right.render(f5);
			Corner_Top_Back_Left.render(f5);
			Corner_Top_Back_Right.render(f5);
			Corner_Top_Front_Left.render(f5);
			Corner_Top_Front_Right.render(f5);
			Leg_Joint_Front_Left.render(f5);
			Leg_Joint_Front_Right.render(f5);
			Leg_Joint_Back_Left.render(f5);
			Leg_Joint_Back_Right.render(f5);
			Leg_Front_Right.render(f5);
			Leg_Front_Left.render(f5);
			Leg_Back_Right.render(f5);
			Leg_Back_Left.render(f5);
			GlStateManager.pushMatrix();
			GlStateManager.translate(3F * f5, 7F * f5, -3F * f5);
			GlStateManager.rotate(0.7853982F * (180F / (float) Math.PI), 0, 1, 0);
			GlStateManager.rotate(0.6981317F * (180F / (float) Math.PI), 0, 0, 1);
			Leg_2_Front_Left.render(f5);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.translate(-3F * f5, 7F * f5, -3F * f5);
			GlStateManager.rotate(-0.7853982F * (180F / (float) Math.PI), 0, 1, 0);
			GlStateManager.rotate(-0.6981317F * (180F / (float) Math.PI), 0, 0, 1);
			Leg_2_Front_Right.render(f5);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.translate(3F * f5, 7F * f5, 3F * f5);
			GlStateManager.rotate(-0.7853982F * (180F / (float) Math.PI), 0, 1, 0);
			GlStateManager.rotate(0.6981317F * (180F / (float) Math.PI), 0, 0, 1);
			Leg_2_Back_Left.render(f5);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.translate(-3F * f5, 7F * f5, 3F * f5);
			GlStateManager.rotate(0.7853982F * (180F / (float) Math.PI), 0, 1, 0);
			GlStateManager.rotate(-0.6981317F * (180F / (float) Math.PI), 0, 0, 1);
			Leg_2_Back_Right.render(f5);
			GlStateManager.popMatrix();
			Foot_Front_Left.render(f5);
			Foot_Front_Right.render(f5);
			Foot_Back_Right.render(f5);
			Foot_Back_Left.render(f5);
		}

		private void setRotation(ModelRenderer model, float x, float y, float z) {
			model.rotateAngleX = x;
			model.rotateAngleY = y;
			model.rotateAngleZ = z;
		}

		public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor,
				Entity entity) {
			super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
			float leg1 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
			float leg2 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * limbSwingAmount;
			float leg3 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
			float leg4 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float) Math.PI) * 0.4F) * limbSwingAmount;
			Leg_2_Front_Left.rotateAngleY = 0.0F;
			Leg_2_Front_Left.rotateAngleZ = 0.0F;
			Leg_2_Front_Left.rotationPointX = 0.0F;
			Leg_2_Front_Left.rotationPointY = 0.0F;
			Leg_2_Front_Left.rotationPointZ = 0.0F;
			Foot_Front_Left.rotateAngleY = 0.7853982F;
			Leg_Front_Left.rotateAngleY = -0.7853982F;
			Leg_Joint_Front_Left.rotateAngleY = 0.7853982F;
			Leg_2_Front_Right.rotateAngleZ = 0.0F;
			Leg_2_Front_Right.rotateAngleY = 0.0F;
			Leg_2_Front_Right.rotationPointX = 0.0F;
			Leg_2_Front_Right.rotationPointY = 0.0F;
			Leg_2_Front_Right.rotationPointZ = 0.0F;
			Foot_Front_Right.rotateAngleY = -0.7853982F;
			Leg_Front_Right.rotateAngleY = 0.7853982F;
			Leg_Joint_Front_Right.rotateAngleY = -0.7853982F;
			Foot_Front_Right.rotateAngleZ = 0.0F;
			Leg_Front_Right.rotateAngleZ = 0.0F;
			Leg_Joint_Front_Right.rotateAngleZ = 0.0F;
			Leg_2_Back_Left.rotateAngleY = 0.0F;
			Leg_2_Back_Left.rotateAngleZ = 0.0F;
			Leg_2_Back_Left.rotationPointX = 0.0F;
			Leg_2_Back_Left.rotationPointY = 0.0F;
			Leg_2_Back_Left.rotationPointZ = 0.0F;
			Foot_Back_Left.rotateAngleY = -0.7853982F;
			Leg_Back_Left.rotateAngleY = 0.7853982F;
			Leg_Joint_Back_Left.rotateAngleY = -0.7853982F;
			Leg_2_Back_Right.rotateAngleY = 0.0F;
			Leg_2_Back_Right.rotateAngleZ = 0.0F;
			Leg_2_Back_Right.rotationPointX = 0.0F;
			Leg_2_Back_Right.rotationPointY = 0.0F;
			Leg_2_Back_Right.rotationPointZ = 0.0F;
			Foot_Back_Right.rotateAngleZ = 0.0F;
			Leg_Back_Right.rotateAngleZ = 0.0F;
			Leg_Joint_Back_Right.rotateAngleZ = 0.0F;
			Leg_2_Front_Left.rotateAngleY += leg1;
			Foot_Front_Left.rotateAngleY += leg1;
			Leg_Front_Left.rotateAngleY += leg1;
			Leg_Joint_Front_Left.rotateAngleY += leg1;
			Leg_2_Back_Left.rotateAngleY += -leg2;
			Foot_Back_Left.rotateAngleY += -leg2;
			Leg_Back_Left.rotateAngleY += -leg2;
			Leg_Joint_Back_Left.rotateAngleY += -leg2;
			Leg_2_Front_Right.rotateAngleZ += leg3;
			Foot_Front_Right.rotateAngleZ += leg3;
			Leg_Front_Right.rotateAngleZ += leg3;
			Leg_Joint_Front_Right.rotateAngleZ += leg3;
			Leg_2_Back_Right.rotateAngleZ += -leg4;
			Foot_Back_Right.rotateAngleZ += -leg4;
			Leg_Back_Right.rotateAngleZ += -leg4;
			Leg_Joint_Back_Right.rotateAngleZ += -leg4;
		}
	}
}
