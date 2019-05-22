package mod.mcreator;

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
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.Minecraft;

import java.util.Iterator;
import java.util.ArrayList;

public class mcreator_hythalumTurtle extends nebulus.ModElement {

	public static int mobid = 28;
	public static int mobid2 = 29;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		EntityRegistry.registerModEntity(new ResourceLocation("nebulus:hythalumturtle"), EntityhythalumTurtle.class, "hythalumturtle", mobid,
				instance, 64, 1, true, -16711681, -16777216);
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
		RenderLiving customRender = new RenderLiving(Minecraft.getMinecraft().getRenderManager(), new ModelNebulizedTurtle(), 0) {

			protected ResourceLocation getEntityTexture(Entity par1Entity) {
				return new ResourceLocation("nebsulizedstone.png");
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(EntityhythalumTurtle.class, customRender);
	}

	public static class EntityhythalumTurtle extends EntityMob {

		public EntityhythalumTurtle(World world) {
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

	// Made with Blockbench
	// Paste this code into your mod.
	public static class ModelNebulizedTurtle extends ModelBase {

		private final ModelRenderer bone;
		private final ModelRenderer bone2;

		public ModelNebulizedTurtle() {
			textureWidth = 16;
			textureHeight = 16;
			bone = new ModelRenderer(this);
			bone.setRotationPoint(0.0F, 24.0F, 0.0F);
			bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -7.0F, -13.0F, 17, 2, 24, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 0, 0, -6.0F, -6.0F, -10.0F, 11, 4, 20, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -11.0F, -11.0F, 13, 4, 20, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 0, 0, 5.0F, -3.0F, -9.0F, 8, 1, 5, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 0, 0, -14.0F, -3.0F, -10.0F, 8, 1, 5, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 0, 0, 5.0F, -3.0F, 4.0F, 8, 1, 6, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 0, 0, -14.0F, -3.0F, 5.0F, 8, 1, 5, 0.0F, false));
			bone2 = new ModelRenderer(this);
			bone2.setRotationPoint(0.0F, 24.0F, 0.0F);
			setRotationAngle(bone2, -0.0873F, 0.0F, 0.0F);
			bone2.cubeList.add(new ModelBox(bone2, 0, 0, -2.0F, -6.0F, -22.0F, 3, 2, 10, 0.0F, false));
			bone2.cubeList.add(new ModelBox(bone2, 0, 0, -3.0F, -18.0F, -23.0F, 5, 13, 2, 0.0F, false));
			bone2.cubeList.add(new ModelBox(bone2, 0, 0, -3.0F, -18.0F, -29.0F, 5, 5, 6, 0.0F, false));
			bone2.cubeList.add(new ModelBox(bone2, 0, 0, -1.0F, -8.0F, 10.0F, 1, 1, 6, 0.0F, false));
		}

		@Override
		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
			bone.render(f5);
			bone2.render(f5);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
			super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
			this.bone2.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.bone2.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.bone.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.bone.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.bone2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 2.0F * f1 * 0.5F;
			this.bone2.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
		}
	}
}
