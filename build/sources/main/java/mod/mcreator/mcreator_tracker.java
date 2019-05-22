package mod.mcreator;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
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
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.Minecraft;

import java.util.Iterator;
import java.util.ArrayList;

public class mcreator_tracker extends nebulus.ModElement {

	public static int mobid = 18;
	public static int mobid2 = 19;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		EntityRegistry.registerModEntity(new ResourceLocation("nebulus:tracker"), Entitytracker.class, "tracker", mobid, instance, 64, 1, true, -1,
				-1);
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
		RenderLiving customRender = new RenderLiving(Minecraft.getMinecraft().getRenderManager(), new ModelTrackingSentry(), 0) {

			protected ResourceLocation getEntityTexture(Entity par1Entity) {
				return new ResourceLocation("sentry_gel.png");
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(Entitytracker.class, customRender);
	}

	public static class Entitytracker extends EntityMob {

		public Entitytracker(World world) {
			super(world);
			setSize(0.6f, 1.8f);
			experienceValue = 5;
			this.isImmuneToFire = false;
			setNoAI(!true);
			this.tasks.addTask(1, new EntityAIWander(this, 1));
			this.tasks.addTask(2, new EntityAILookIdle(this));
			this.tasks.addTask(3, new EntityAISwimming(this));
			this.tasks.addTask(4, new EntityAILeapAtTarget(this, (float) 0.8));
			this.tasks.addTask(5, new EntityAIPanic(this, 1.2));
			this.targetTasks.addTask(6, new EntityAIHurtByTarget(this, false));
		}

		@Override
		public EnumCreatureAttribute getCreatureAttribute() {
			return EnumCreatureAttribute.UNDEFINED;
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
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10D);
			if (this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null)
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3D);
		}

		@Override
		public void travel(float ti, float tj, float tk) {
			Entity entity = this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
			if (this.isBeingRidden() && this.canBeSteered()) {
				this.rotationYaw = entity.rotationYaw;
				this.prevRotationYaw = this.rotationYaw;
				this.rotationPitch = entity.rotationPitch * 0.5F;
				this.setRotation(this.rotationYaw, this.rotationPitch);
				this.renderYawOffset = this.rotationYaw;
				this.rotationYawHead = this.rotationYaw;
				this.stepHeight = 1.0F;
				this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;
				if (this.canPassengerSteer()) {
					float f = (float) this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 0.225F;
					this.setAIMoveSpeed(f);
					super.travel(0.0F, 0.0F, 1.0F);
				} else {
					this.motionX = 0.0D;
					this.motionY = 0.0D;
					this.motionZ = 0.0D;
				}
				this.prevLimbSwingAmount = this.limbSwingAmount;
				double d1 = this.posX - this.prevPosX;
				double d0 = this.posZ - this.prevPosZ;
				float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
				if (f1 > 1.0F)
					f1 = 1.0F;
				this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.4F;
				this.limbSwing += this.limbSwingAmount;
			} else {
				this.stepHeight = 0.5F;
				this.jumpMovementFactor = 0.02F;
				super.travel(ti, tj, tk);
			}
		}
	}

	public static class ModelTrackingSentry extends ModelBase {

		// fields
		ModelRenderer Shape1;
		ModelRenderer Shape2;
		ModelRenderer Shape3;
		ModelRenderer Shape4;
		ModelRenderer Shape5;
		ModelRenderer Shape6;
		ModelRenderer Shape7;
		ModelRenderer Shape8;
		ModelRenderer Shape9;
		ModelRenderer Shape10;
		ModelRenderer Shape11;
		ModelRenderer body_eye;
		ModelRenderer body_crest_top;
		ModelRenderer body_crest_left_top;
		ModelRenderer body_crest_left_bottom;
		ModelRenderer body_crest_bottom;
		ModelRenderer body_crest_right_top;
		ModelRenderer body_crest_right_bottom;
		ModelRenderer body_crest_right_top2;
		ModelRenderer body_crest_right_top3;
		ModelRenderer Shape12;

		public ModelTrackingSentry() {
			this.textureWidth = 64;
			this.textureHeight = 32;
			this.Shape1 = new ModelRenderer(this, 0, 0);
			this.Shape1.addBox(-4F, 1F, -4F, 8, 3, 9);
			this.Shape1.setRotationPoint(0F, 0F, 0F);
			this.Shape1.setTextureSize(64, 32);
			this.Shape1.mirror = true;
			this.setRotation(this.Shape1, -0.1745329F, 0F, 0F);
			this.Shape2 = new ModelRenderer(this, 0, 0);
			this.Shape2.addBox(-4F, 9.5F, -6.5F, 8, 3, 9);
			this.Shape2.setRotationPoint(0F, 0F, 0F);
			this.Shape2.setTextureSize(64, 32);
			this.Shape2.mirror = true;
			this.setRotation(this.Shape2, 0.1745329F, 0F, 0F);
			this.Shape3 = new ModelRenderer(this, 0, 0);
			this.Shape3.addBox(-5.5F, 3F, -5F, 3, 8, 9);
			this.Shape3.setRotationPoint(0F, 0F, 0F);
			this.Shape3.setTextureSize(64, 32);
			this.Shape3.mirror = true;
			this.setRotation(this.Shape3, 0F, 0.1745329F, 0F);
			this.Shape4 = new ModelRenderer(this, 0, 0);
			this.Shape4.addBox(2.5F, 3F, -5F, 3, 8, 9);
			this.Shape4.setRotationPoint(0F, 0F, 0F);
			this.Shape4.setTextureSize(64, 32);
			this.Shape4.mirror = true;
			this.setRotation(this.Shape4, 0F, -0.1745329F, 0F);
			this.Shape5 = new ModelRenderer(this, 0, 0);
			this.Shape5.addBox(-3F, 4F, 3F, 6, 6, 1);
			this.Shape5.setRotationPoint(0F, 0F, 0F);
			this.Shape5.setTextureSize(64, 32);
			this.Shape5.mirror = true;
			this.setRotation(this.Shape5, 0F, 0F, 0F);
			this.Shape6 = new ModelRenderer(this, 0, 0);
			this.Shape6.addBox(-21F, -3F, -1.5F, 3, 6, 3);
			this.Shape6.setRotationPoint(0F, 7F, -2F);
			this.Shape6.setTextureSize(64, 32);
			this.Shape6.mirror = true;
			this.setRotation(this.Shape6, 0F, 0F, 0F);
			this.Shape7 = new ModelRenderer(this, 0, 0);
			this.Shape7.addBox(-19.5F, -6F, -1F, 2, 14, 2);
			this.Shape7.setRotationPoint(0F, 7F, -2F);
			this.Shape7.setTextureSize(64, 32);
			this.Shape7.mirror = true;
			this.setRotation(this.Shape7, 0F, 0F, 0.5235988F);
			this.Shape8 = new ModelRenderer(this, 0, 0);
			this.Shape8.addBox(-19.5F, -8F, -1F, 2, 14, 2);
			this.Shape8.setRotationPoint(0F, 7F, -2F);
			this.Shape8.setTextureSize(64, 32);
			this.Shape8.mirror = true;
			this.setRotation(this.Shape8, 0F, 0F, -0.5235988F);
			this.Shape9 = new ModelRenderer(this, 0, 0);
			this.Shape9.addBox(18F, -3F, -1.5F, 3, 6, 3);
			this.Shape9.setRotationPoint(0F, 7F, -2F);
			this.Shape9.setTextureSize(64, 32);
			this.Shape9.mirror = true;
			this.setRotation(this.Shape9, 0F, 0F, 0F);
			this.Shape10 = new ModelRenderer(this, 0, 0);
			this.Shape10.addBox(17.5F, -8F, -1F, 2, 14, 2);
			this.Shape10.setRotationPoint(0F, 7F, -2F);
			this.Shape10.setTextureSize(64, 32);
			this.Shape10.mirror = true;
			this.setRotation(this.Shape10, 0F, 0F, 0.5235988F);
			this.Shape11 = new ModelRenderer(this, 0, 0);
			this.Shape11.addBox(17.5F, -6F, -1F, 2, 14, 2);
			this.Shape11.setRotationPoint(0F, 7F, -2F);
			this.Shape11.setTextureSize(64, 32);
			this.Shape11.mirror = true;
			this.setRotation(this.Shape11, 0F, 0F, -0.5235988F);
			this.body_eye = new ModelRenderer(this, 0, 0);
			this.body_eye.addBox(-5F, 3F, -8F, 10, 8, 2);
			this.body_eye.setRotationPoint(0F, 0F, 0F);
			this.body_eye.setTextureSize(64, 32);
			this.body_eye.mirror = true;
			this.setRotation(this.body_eye, 0F, 0F, 0F);
			this.body_crest_top = new ModelRenderer(this, 0, 0);
			this.body_crest_top.addBox(-4F, -2F, -1F, 8, 3, 12);
			this.body_crest_top.setRotationPoint(0F, 2F, -8F);
			this.body_crest_top.setTextureSize(64, 32);
			this.body_crest_top.mirror = true;
			this.setRotation(this.body_crest_top, 0.9599311F, 0F, 0F);
			this.body_crest_left_top = new ModelRenderer(this, 0, 0);
			this.body_crest_left_top.addBox(-1F, -3F, -1F, 8, 5, 2);
			this.body_crest_left_top.setRotationPoint(5F, 4F, -8F);
			this.body_crest_left_top.setTextureSize(64, 32);
			this.body_crest_left_top.mirror = true;
			this.setRotation(this.body_crest_left_top, -0.2094395F, -0.5235988F, -0.6108652F);
			this.body_crest_left_bottom = new ModelRenderer(this, 0, 0);
			this.body_crest_left_bottom.addBox(-1.5F, -2.5F, -1.5F, 9, 3, 3);
			this.body_crest_left_bottom.setRotationPoint(6F, 8F, -8F);
			this.body_crest_left_bottom.setTextureSize(64, 32);
			this.body_crest_left_bottom.mirror = true;
			this.setRotation(this.body_crest_left_bottom, 0F, -0.4363323F, 0F);
			this.body_crest_bottom = new ModelRenderer(this, 0, 0);
			this.body_crest_bottom.addBox(-4F, -1F, -1F, 8, 3, 12);
			this.body_crest_bottom.setRotationPoint(0F, 12F, -8F);
			this.body_crest_bottom.setTextureSize(64, 32);
			this.body_crest_bottom.mirror = true;
			this.setRotation(this.body_crest_bottom, -0.9599311F, 0F, 0F);
			this.body_crest_right_top = new ModelRenderer(this, 0, 0);
			this.body_crest_right_top.addBox(-1F, -2F, -1F, 8, 5, 2);
			this.body_crest_right_top.setRotationPoint(5F, 10F, -8F);
			this.body_crest_right_top.setTextureSize(64, 32);
			this.body_crest_right_top.mirror = true;
			this.setRotation(this.body_crest_right_top, 0.2094395F, -0.5235988F, 0.6108652F);
			this.body_crest_right_bottom = new ModelRenderer(this, 0, 0);
			this.body_crest_right_bottom.addBox(-7.5F, -2.5F, -1.5F, 9, 3, 3);
			this.body_crest_right_bottom.setRotationPoint(-6F, 8F, -8F);
			this.body_crest_right_bottom.setTextureSize(64, 32);
			this.body_crest_right_bottom.mirror = true;
			this.setRotation(this.body_crest_right_bottom, 0F, 0.4363323F, 0F);
			this.body_crest_right_top2 = new ModelRenderer(this, 0, 0);
			this.body_crest_right_top2.addBox(-7F, -3F, -1F, 8, 5, 2);
			this.body_crest_right_top2.setRotationPoint(-5F, 4F, -8F);
			this.body_crest_right_top2.setTextureSize(64, 32);
			this.body_crest_right_top2.mirror = true;
			this.setRotation(this.body_crest_right_top2, -0.2094395F, 0.5235988F, 0.6108652F);
			this.body_crest_right_top3 = new ModelRenderer(this, 0, 0);
			this.body_crest_right_top3.addBox(-7F, -2F, -1F, 8, 5, 2);
			this.body_crest_right_top3.setRotationPoint(-5F, 10F, -8F);
			this.body_crest_right_top3.setTextureSize(64, 32);
			this.body_crest_right_top3.mirror = true;
			this.setRotation(this.body_crest_right_top3, 0.2094395F, 0.5235988F, -0.6108652F);
			this.Shape12 = new ModelRenderer(this, 0, 0);
			this.Shape12.addBox(-5F, 2F, -6F, 10, 10, 5);
			this.Shape12.setRotationPoint(0F, 0F, 0F);
			this.Shape12.setTextureSize(64, 32);
			this.Shape12.mirror = true;
			this.setRotation(this.Shape12, 0F, 0F, 0F);
		}

		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
			super.render(entity, f, f1, f2, f3, f4, f5);
			this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
			float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
			GL11.glPushMatrix();
			GL11.glTranslatef(0, MathHelper.sin((entity.ticksExisted + partialTicks) * 0.25F) / 2, 0);
			this.Shape1.render(f5);
			this.Shape2.render(f5);
			this.Shape3.render(f5);
			this.Shape4.render(f5);
			this.Shape5.render(f5);
			this.Shape6.render(f5);
			this.Shape7.render(f5);
			this.Shape8.render(f5);
			this.Shape9.render(f5);
			this.Shape10.render(f5);
			this.Shape11.render(f5);
			this.body_eye.render(f5);
			this.body_crest_top.render(f5);
			this.body_crest_left_top.render(f5);
			this.body_crest_left_bottom.render(f5);
			this.body_crest_bottom.render(f5);
			this.body_crest_right_top.render(f5);
			this.body_crest_right_bottom.render(f5);
			this.body_crest_right_top2.render(f5);
			this.body_crest_right_top3.render(f5);
			this.Shape12.render(f5);
			GL11.glPopMatrix();
		}

		private void setRotation(ModelRenderer model, float x, float y, float z) {
			model.rotateAngleX = x;
			model.rotateAngleY = y;
			model.rotateAngleZ = z;
		}

		public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
			super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		}
	}
}
