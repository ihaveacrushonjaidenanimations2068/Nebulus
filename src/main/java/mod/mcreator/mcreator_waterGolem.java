package mod.mcreator;

import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.BossInfo;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.Minecraft;

import java.util.Random;

public class mcreator_waterGolem extends nebulus.ModElement {

	public static int mobid = 12;
	public static int mobid2 = 13;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		EntityRegistry.registerModEntity(new ResourceLocation("nebulus:watergolem"), EntitywaterGolem.class, "watergolem", mobid, instance, 64, 1,
				true, -16711732, -16711681);
		Biome[] spawnBiomes = {mcreator_nebuForest.biome,};
		EntityRegistry.addSpawn(EntitywaterGolem.class, 20, 3, 30, EnumCreatureType.MONSTER, spawnBiomes);
	}

	@Override
	public void registerRenderers() {
		RenderLiving customRender = new RenderLiving(Minecraft.getMinecraft().getRenderManager(), new ModelBattleGolem(), 0) {

			protected ResourceLocation getEntityTexture(Entity par1Entity) {
				return new ResourceLocation("sentry_gel.png");
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(EntitywaterGolem.class, customRender);
	}

	public static class EntitywaterGolem extends EntityCreature {

		public EntitywaterGolem(World world) {
			super(world);
			setSize(0.6f, 1.8f);
			experienceValue = 5;
			this.isImmuneToFire = true;
			setNoAI(!true);
			this.tasks.addTask(1, new EntityAIWander(this, 1));
			this.tasks.addTask(2, new EntityAILookIdle(this));
			this.tasks.addTask(3, new EntityAISwimming(this));
			this.tasks.addTask(4, new EntityAILeapAtTarget(this, (float) 0.8));
			this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.2, false));
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
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("nebulus:mob.test.ambient"));
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("nebulus:mob.test.hurt"));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("nebulus:mob.test.death"));
		}

		@Override
		protected float getSoundVolume() {
			return 1.0F;
		}

		@Override
		protected void applyEntityAttributes() {
			super.applyEntityAttributes();
			this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100D);
			if (this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null)
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3D);
		}

		@Override
		public boolean isNonBoss() {
			return false;
		}

		private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(), BossInfo.Color.BLUE,
				BossInfo.Overlay.NOTCHED_20));

		@Override
		public void addTrackingPlayer(EntityPlayerMP player) {
			super.addTrackingPlayer(player);
			this.bossInfo.addPlayer(player);
		}

		@Override
		public void removeTrackingPlayer(EntityPlayerMP player) {
			super.removeTrackingPlayer(player);
			this.bossInfo.removePlayer(player);
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
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

		public void onLivingUpdate() {
			super.onLivingUpdate();
			int i = (int) this.posX;
			int j = (int) this.posY;
			int k = (int) this.posZ;
			Random random = this.rand;
			if (true)
				for (int l = 0; l < 4; ++l) {
					double d0 = (double) ((float) i + 0.5F) + (double) (random.nextFloat() - 0.5F) * 0.5D * 20;
					double d1 = ((double) ((float) j + 0.7F) + (double) (random.nextFloat() - 0.5F) * 0.5D) + 0.5D;
					double d2 = (double) ((float) k + 0.5F) + (double) (random.nextFloat() - 0.5F) * 0.5D * 20;
					world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d0 - 0.27000001072883606D, d1 + 0.2199999988079071D, d2, 0.0D, 0.0D, 0.0D);
				}
		}
	}

	public static class ModelBattleGolem extends ModelBase {

		// fields
		ModelRenderer head;
		ModelRenderer b_collar_front;
		ModelRenderer b_collar_front_left;
		ModelRenderer b_collar_front_right;
		ModelRenderer b_collar_left;
		ModelRenderer b_collar_right;
		ModelRenderer b_collar_back_left;
		ModelRenderer b_collar_back_right;
		ModelRenderer b_chest_top;
		ModelRenderer b_shoulder_left_front;
		ModelRenderer b_shoulder_left_mid;
		ModelRenderer b_shoulder_left_back;
		ModelRenderer b_shoulder_right_front;
		ModelRenderer b_shoulder_right_mid;
		ModelRenderer b_shoulder_right_back;
		ModelRenderer b_chest_main;
		ModelRenderer b_core;
		ModelRenderer b_crest_top;
		ModelRenderer b_crest_left_top;
		ModelRenderer b_crest_right_top;
		ModelRenderer b_crest_bottom;
		ModelRenderer b_crest_left;
		ModelRenderer b_crest_right;
		ModelRenderer b_chest_bottom;
		ModelRenderer b_back_top;
		ModelRenderer b_back_bottom;
		ModelRenderer b_waist;
		ModelRenderer t_mid_top;
		ModelRenderer t_mid_bottom;
		ModelRenderer t_left_cover_top;
		ModelRenderer t_left_cover_front_left;
		ModelRenderer t_left_cover_front_right;
		ModelRenderer t_left_cover_back_left;
		ModelRenderer t_left_cover_back_right;
		ModelRenderer t_left_rotor_top;
		ModelRenderer t_left_rotor_front;
		ModelRenderer t_left_rotor_back;
		ModelRenderer t_left_tread_front;
		ModelRenderer t_left_tread_back;
		ModelRenderer t_left_tread_bottom;
		ModelRenderer t_left_center_bottom;
		ModelRenderer t_left_center_mid;
		ModelRenderer t_left_center_top;
		ModelRenderer t_right_cover_top;
		ModelRenderer t_right_cover_front_left;
		ModelRenderer t_right_cover_front_right;
		ModelRenderer t_right_cover_back_left;
		ModelRenderer t_right_cover_back_right;
		ModelRenderer t_right_rotor_top;
		ModelRenderer t_right_rotor_front;
		ModelRenderer t_right_rotot_back;
		ModelRenderer t_right_tread_front;
		ModelRenderer t_right_tread_back;
		ModelRenderer t_right_tread_bottom;
		ModelRenderer t_right_center_bottom;
		ModelRenderer t_right_center_mid;
		ModelRenderer t_right_center_top;
		ModelRenderer l_a_joint;
		ModelRenderer l_a_top;
		ModelRenderer l_a_forearm;
		ModelRenderer l_a_finger_top_left;
		ModelRenderer l_a_finger_top_right;
		ModelRenderer l_a_finger_bottom_left;
		ModelRenderer l_a_finger_bottom_right;
		ModelRenderer r_a_joint;
		ModelRenderer r_a_top;
		ModelRenderer r_a_forearm;
		ModelRenderer r_a_finger_top_left;
		ModelRenderer r_a_finger_top_right;
		ModelRenderer r_a_finger_bottom_left;
		ModelRenderer r_a_finger_bottom_right;
		float throwingAngle = 1.0F;
		float restingAngle = 0.5F;
		float grabbingAngle = 1.5F;

		public ModelBattleGolem() {
			textureWidth = 128;
			textureHeight = 64;
			head = new ModelRenderer(this, 0, 0);
			head.addBox(-6F, -8F, -5F, 12, 8, 10);
			head.setRotationPoint(0F, -20F, 1.5F);
			head.setTextureSize(128, 64);
			head.mirror = true;
			setRotation(head, 0F, 0F, 0F);
			b_collar_front = new ModelRenderer(this, 0, 0);
			b_collar_front.addBox(-5F, -22F, -3.5F, 10, 5, 3);
			b_collar_front.setRotationPoint(0F, 0F, 0F);
			b_collar_front.setTextureSize(128, 64);
			b_collar_front.mirror = true;
			setRotation(b_collar_front, 0.2617994F, 0F, 0F);
			b_collar_front_left = new ModelRenderer(this, 0, 0);
			b_collar_front_left.addBox(7F, -22F, -2F, 2, 3, 8);
			b_collar_front_left.setRotationPoint(0F, 0F, 0F);
			b_collar_front_left.setTextureSize(128, 64);
			b_collar_front_left.mirror = true;
			setRotation(b_collar_front_left, 0F, 0.7853982F, 0F);
			b_collar_front_right = new ModelRenderer(this, 0, 0);
			b_collar_front_right.addBox(-9F, -22F, -2F, 2, 3, 8);
			b_collar_front_right.setRotationPoint(0F, 0F, 0F);
			b_collar_front_right.setTextureSize(128, 64);
			b_collar_front_right.mirror = true;
			setRotation(b_collar_front_right, 0F, -0.7853982F, 0F);
			b_collar_left = new ModelRenderer(this, 0, 0);
			b_collar_left.addBox(8.5F, -23F, -2.5F, 3, 6, 8);
			b_collar_left.setRotationPoint(0F, 0F, 0F);
			b_collar_left.setTextureSize(128, 64);
			b_collar_left.mirror = true;
			setRotation(b_collar_left, 0F, 0F, 0F);
			b_collar_right = new ModelRenderer(this, 0, 0);
			b_collar_right.addBox(-11.5F, -23F, -2.5F, 3, 6, 8);
			b_collar_right.setRotationPoint(0F, 0F, 0F);
			b_collar_right.setTextureSize(128, 64);
			b_collar_right.mirror = true;
			setRotation(b_collar_right, 0F, 0F, 0F);
			b_collar_back_left = new ModelRenderer(this, 0, 0);
			b_collar_back_left.addBox(9F, -22F, -4F, 2, 3, 8);
			b_collar_back_left.setRotationPoint(0F, 0F, 0F);
			b_collar_back_left.setTextureSize(128, 64);
			b_collar_back_left.mirror = true;
			setRotation(b_collar_back_left, 0F, -0.7853982F, 0F);
			b_collar_back_right = new ModelRenderer(this, 0, 0);
			b_collar_back_right.addBox(-11F, -22F, -4F, 2, 3, 8);
			b_collar_back_right.setRotationPoint(0F, 0F, 0F);
			b_collar_back_right.setTextureSize(128, 64);
			b_collar_back_right.mirror = true;
			setRotation(b_collar_back_right, 0F, 0.7853982F, 0F);
			b_chest_top = new ModelRenderer(this, 0, 0);
			b_chest_top.addBox(-9.5F, -19F, -7.5F, 19, 9, 18);
			b_chest_top.setRotationPoint(0F, 0F, 0F);
			b_chest_top.setTextureSize(128, 64);
			b_chest_top.mirror = true;
			setRotation(b_chest_top, 0F, 0F, 0F);
			b_shoulder_left_front = new ModelRenderer(this, 0, 0);
			b_shoulder_left_front.addBox(0F, -18F, -19F, 10, 14, 4);
			b_shoulder_left_front.setRotationPoint(0F, 0F, 0F);
			b_shoulder_left_front.setTextureSize(128, 64);
			b_shoulder_left_front.mirror = true;
			setRotation(b_shoulder_left_front, -0.7853982F, 0F, 0.5235988F);
			b_shoulder_left_mid = new ModelRenderer(this, 0, 0);
			b_shoulder_left_mid.addBox(-0.5F, -27F, -1F, 11, 7, 6);
			b_shoulder_left_mid.setRotationPoint(0F, 0F, 0F);
			b_shoulder_left_mid.setTextureSize(128, 64);
			b_shoulder_left_mid.mirror = true;
			setRotation(b_shoulder_left_mid, 0F, 0F, 0.5235988F);
			b_shoulder_left_back = new ModelRenderer(this, 0, 0);
			b_shoulder_left_back.addBox(-1F, -15F, 18F, 10, 14, 4);
			b_shoulder_left_back.setRotationPoint(1F, 0F, 0F);
			b_shoulder_left_back.setTextureSize(128, 64);
			b_shoulder_left_back.mirror = true;
			setRotation(b_shoulder_left_back, 0.7853982F, 0F, 0.5235988F);
			b_shoulder_right_front = new ModelRenderer(this, 0, 0);
			b_shoulder_right_front.addBox(-10F, -18F, -19F, 10, 14, 4);
			b_shoulder_right_front.setRotationPoint(0F, 0F, 0F);
			b_shoulder_right_front.setTextureSize(128, 64);
			b_shoulder_right_front.mirror = true;
			setRotation(b_shoulder_right_front, -0.7853982F, 0F, -0.5235988F);
			b_shoulder_right_mid = new ModelRenderer(this, 0, 0);
			b_shoulder_right_mid.addBox(-10.5F, -27F, -1F, 11, 7, 6);
			b_shoulder_right_mid.setRotationPoint(0F, 0F, 0F);
			b_shoulder_right_mid.setTextureSize(128, 64);
			b_shoulder_right_mid.mirror = true;
			setRotation(b_shoulder_right_mid, 0F, 0F, -0.5235988F);
			b_shoulder_right_back = new ModelRenderer(this, 0, 0);
			b_shoulder_right_back.addBox(-10F, -16F, 18F, 10, 14, 4);
			b_shoulder_right_back.setRotationPoint(0F, 0F, 0F);
			b_shoulder_right_back.setTextureSize(128, 64);
			b_shoulder_right_back.mirror = true;
			setRotation(b_shoulder_right_back, 0.7853982F, 0F, -0.5235988F);
			b_chest_main = new ModelRenderer(this, 0, 0);
			b_chest_main.addBox(-14F, -14F, -11F, 28, 11, 11);
			b_chest_main.setRotationPoint(0F, 0F, 0F);
			b_chest_main.setTextureSize(128, 64);
			b_chest_main.mirror = true;
			setRotation(b_chest_main, -0.7853982F, 0F, 0F);
			b_core = new ModelRenderer(this, 0, 0);
			b_core.addBox(-5F, -13F, -8F, 10, 9, 3);
			b_core.setRotationPoint(0F, 0F, 0F);
			b_core.setTextureSize(128, 64);
			b_core.mirror = true;
			setRotation(b_core, 0F, 0F, 0F);
			b_crest_top = new ModelRenderer(this, 0, 0);
			b_crest_top.addBox(-4.5F, -1.5F, -1F, 9, 2, 4);
			b_crest_top.setRotationPoint(0F, -13F, -8F);
			b_crest_top.setTextureSize(128, 64);
			b_crest_top.mirror = true;
			setRotation(b_crest_top, 0.9599311F, 0F, 0F);
			b_crest_left_top = new ModelRenderer(this, 0, 0);
			b_crest_left_top.addBox(-1F, -3F, -0.8F, 6, 7, 2);
			b_crest_left_top.setRotationPoint(5F, -11F, -8F);
			b_crest_left_top.setTextureSize(128, 64);
			b_crest_left_top.mirror = true;
			setRotation(b_crest_left_top, -0.2094395F, -0.5235988F, -0.6108652F);
			b_crest_right_top = new ModelRenderer(this, 0, 0);
			b_crest_right_top.addBox(-5F, -3F, -0.8F, 6, 7, 2);
			b_crest_right_top.setRotationPoint(-5F, -11F, -8F);
			b_crest_right_top.setTextureSize(128, 64);
			b_crest_right_top.mirror = true;
			setRotation(b_crest_right_top, -0.2094395F, 0.5235988F, 0.6108652F);
			b_crest_bottom = new ModelRenderer(this, 0, 0);
			b_crest_bottom.addBox(-4F, 6F, -11.8F, 8, 2, 5);
			b_crest_bottom.setRotationPoint(0F, -13F, 0F);
			b_crest_bottom.setTextureSize(128, 64);
			b_crest_bottom.mirror = true;
			setRotation(b_crest_bottom, 0.2617994F, 0F, 0F);
			b_crest_left = new ModelRenderer(this, 0, 0);
			b_crest_left.addBox(-2F, -2F, -1F, 3, 6, 2);
			b_crest_left.setRotationPoint(6F, -7F, -8F);
			b_crest_left.setTextureSize(128, 64);
			b_crest_left.mirror = true;
			setRotation(b_crest_left, 0.0872665F, -0.3490659F, 0.5235988F);
			b_crest_right = new ModelRenderer(this, 0, 0);
			b_crest_right.addBox(-1F, -2F, -1F, 3, 6, 2);
			b_crest_right.setRotationPoint(-6F, -7F, -8F);
			b_crest_right.setTextureSize(128, 64);
			b_crest_right.mirror = true;
			setRotation(b_crest_right, 0.0872665F, 0.3490659F, -0.5235988F);
			b_chest_bottom = new ModelRenderer(this, 0, 0);
			b_chest_bottom.addBox(-12F, -11F, -7.5F, 24, 7, 7);
			b_chest_bottom.setRotationPoint(0F, 0F, 0F);
			b_chest_bottom.setTextureSize(128, 64);
			b_chest_bottom.mirror = true;
			setRotation(b_chest_bottom, 0F, 0F, 0F);
			b_back_top = new ModelRenderer(this, 0, 0);
			b_back_top.addBox(-10F, -25F, 9F, 20, 20, 10);
			b_back_top.setRotationPoint(0F, 0F, 0F);
			b_back_top.setTextureSize(128, 64);
			b_back_top.mirror = true;
			setRotation(b_back_top, 0F, 0F, 0F);
			b_back_bottom = new ModelRenderer(this, 0, 0);
			b_back_bottom.addBox(-4F, -13.5F, 5F, 8, 10, 5);
			b_back_bottom.setRotationPoint(0F, 0F, 0F);
			b_back_bottom.setTextureSize(128, 64);
			b_back_bottom.mirror = true;
			setRotation(b_back_bottom, -0.6108652F, 0F, 0F);
			b_waist = new ModelRenderer(this, 0, 0);
			b_waist.addBox(-7F, -9F, -5F, 14, 12, 15);
			b_waist.setRotationPoint(0F, 0F, 0F);
			b_waist.setTextureSize(128, 64);
			b_waist.mirror = true;
			setRotation(b_waist, 0F, 0F, 0F);
			t_mid_top = new ModelRenderer(this, 0, 0);
			t_mid_top.addBox(-8F, 0F, -10F, 16, 7, 21);
			t_mid_top.setRotationPoint(0F, 3F, 2F);
			t_mid_top.setTextureSize(128, 64);
			t_mid_top.mirror = true;
			setRotation(t_mid_top, 0F, 0F, 0F);
			t_mid_bottom = new ModelRenderer(this, 0, 0);
			t_mid_bottom.addBox(-12F, 0.5F, -8.5F, 24, 8, 8);
			t_mid_bottom.setRotationPoint(0F, 3F, 2F);
			t_mid_bottom.setTextureSize(128, 64);
			t_mid_bottom.mirror = true;
			setRotation(t_mid_bottom, 0.7853982F, 0F, 0F);
			t_left_cover_top = new ModelRenderer(this, 0, 0);
			t_left_cover_top.addBox(7F, -6F, -4F, 16, 8, 10);
			t_left_cover_top.setRotationPoint(0F, 3F, 2F);
			t_left_cover_top.setTextureSize(128, 64);
			t_left_cover_top.mirror = true;
			setRotation(t_left_cover_top, 0F, 0F, 0.1745329F);
			t_left_cover_front_left = new ModelRenderer(this, 0, 0);
			t_left_cover_front_left.addBox(15F, -3.5F, -21F, 6, 5, 20);
			t_left_cover_front_left.setRotationPoint(0F, 3F, 2F);
			t_left_cover_front_left.setTextureSize(128, 64);
			t_left_cover_front_left.mirror = true;
			setRotation(t_left_cover_front_left, 0.6981317F, 0F, 0F);
			t_left_cover_front_right = new ModelRenderer(this, 0, 0);
			t_left_cover_front_right.addBox(8.5F, -4F, -21F, 6, 5, 20);
			t_left_cover_front_right.setRotationPoint(0F, 3F, 2F);
			t_left_cover_front_right.setTextureSize(128, 64);
			t_left_cover_front_right.mirror = true;
			setRotation(t_left_cover_front_right, 0.6981317F, 0.0349066F, 0.0174533F);
			t_left_cover_back_left = new ModelRenderer(this, 0, 0);
			t_left_cover_back_left.addBox(15F, -4.5F, 3F, 6, 5, 20);
			t_left_cover_back_left.setRotationPoint(0F, 3F, 2F);
			t_left_cover_back_left.setTextureSize(128, 64);
			t_left_cover_back_left.mirror = true;
			setRotation(t_left_cover_back_left, -0.6981317F, 0F, 0F);
			t_left_cover_back_right = new ModelRenderer(this, 0, 0);
			t_left_cover_back_right.addBox(8.5F, -4.5F, 3F, 6, 5, 20);
			t_left_cover_back_right.setRotationPoint(0F, 3F, 2F);
			t_left_cover_back_right.setTextureSize(128, 64);
			t_left_cover_back_right.mirror = true;
			setRotation(t_left_cover_back_right, -0.6981317F, -0.0349066F, 0.0174533F);
			t_left_rotor_top = new ModelRenderer(this, 0, 0);
			t_left_rotor_top.addBox(7.5F, 1F, -5F, 14, 5, 5);
			t_left_rotor_top.setRotationPoint(0F, 3F, 2F);
			t_left_rotor_top.setTextureSize(128, 64);
			t_left_rotor_top.mirror = true;
			setRotation(t_left_rotor_top, 0.7853982F, 0F, 0F);
			t_left_rotor_front = new ModelRenderer(this, 0, 0);
			t_left_rotor_front.addBox(7.5F, -0.4F, -25.6F, 14, 5, 5);
			t_left_rotor_front.setRotationPoint(0F, 3F, 2F);
			t_left_rotor_front.setTextureSize(128, 64);
			t_left_rotor_front.mirror = true;
			setRotation(t_left_rotor_front, 0.7853982F, 0F, 0F);
			t_left_rotor_back = new ModelRenderer(this, 0, 0);
			t_left_rotor_back.addBox(7.5F, 22F, -3F, 14, 5, 5);
			t_left_rotor_back.setRotationPoint(0F, 3F, 2F);
			t_left_rotor_back.setTextureSize(128, 64);
			t_left_rotor_back.mirror = true;
			setRotation(t_left_rotor_back, 0.7853982F, 0F, 0F);
			t_left_tread_front = new ModelRenderer(this, 0, 0);
			t_left_tread_front.addBox(8F, 1.7F, -23F, 13, 4, 20);
			t_left_tread_front.setRotationPoint(0F, 3F, 2F);
			t_left_tread_front.setTextureSize(128, 64);
			t_left_tread_front.mirror = true;
			setRotation(t_left_tread_front, 0.6981317F, 0F, 0F);
			t_left_tread_back = new ModelRenderer(this, 0, 0);
			t_left_tread_back.addBox(8F, 0.7F, 4F, 13, 4, 20);
			t_left_tread_back.setRotationPoint(0F, 3F, 2F);
			t_left_tread_back.setTextureSize(128, 64);
			t_left_tread_back.mirror = true;
			setRotation(t_left_tread_back, -0.6981317F, 0F, 0F);
			t_left_tread_bottom = new ModelRenderer(this, 0, 0);
			t_left_tread_bottom.addBox(8F, 17.3F, -15F, 13, 4, 32);
			t_left_tread_bottom.setRotationPoint(0F, 3F, 2F);
			t_left_tread_bottom.setTextureSize(128, 64);
			t_left_tread_bottom.mirror = true;
			setRotation(t_left_tread_bottom, 0F, 0F, 0F);
			t_left_center_bottom = new ModelRenderer(this, 0, 0);
			t_left_center_bottom.addBox(11F, 13.3F, -12F, 7, 4, 26);
			t_left_center_bottom.setRotationPoint(0F, 3F, 2F);
			t_left_center_bottom.setTextureSize(128, 64);
			t_left_center_bottom.mirror = true;
			setRotation(t_left_center_bottom, 0F, 0F, 0F);
			t_left_center_mid = new ModelRenderer(this, 0, 0);
			t_left_center_mid.addBox(11F, 9.3F, -8F, 7, 4, 18);
			t_left_center_mid.setRotationPoint(0F, 3F, 2F);
			t_left_center_mid.setTextureSize(128, 64);
			t_left_center_mid.mirror = true;
			setRotation(t_left_center_mid, 0F, 0F, 0F);
			t_left_center_top = new ModelRenderer(this, 0, 0);
			t_left_center_top.addBox(11F, 5.366667F, -3F, 7, 4, 8);
			t_left_center_top.setRotationPoint(0F, 3F, 2F);
			t_left_center_top.setTextureSize(128, 64);
			t_left_center_top.mirror = true;
			setRotation(t_left_center_top, 0F, 0F, 0F);
			t_right_cover_top = new ModelRenderer(this, 0, 0);
			t_right_cover_top.addBox(-23F, -6F, -4F, 16, 8, 10);
			t_right_cover_top.setRotationPoint(0F, 3F, 2F);
			t_right_cover_top.setTextureSize(128, 64);
			t_right_cover_top.mirror = true;
			setRotation(t_right_cover_top, 0F, 0F, -0.1745329F);
			t_right_cover_front_left = new ModelRenderer(this, 0, 0);
			t_right_cover_front_left.addBox(-14.5F, -4F, -21F, 6, 5, 20);
			t_right_cover_front_left.setRotationPoint(0F, 3F, 2F);
			t_right_cover_front_left.setTextureSize(128, 64);
			t_right_cover_front_left.mirror = true;
			setRotation(t_right_cover_front_left, 0.6981317F, -0.0349066F, -0.0174533F);
			t_right_cover_front_right = new ModelRenderer(this, 0, 0);
			t_right_cover_front_right.addBox(-21F, -3.5F, -21F, 6, 5, 20);
			t_right_cover_front_right.setRotationPoint(0F, 3F, 2F);
			t_right_cover_front_right.setTextureSize(128, 64);
			t_right_cover_front_right.mirror = true;
			setRotation(t_right_cover_front_right, 0.6981317F, 0F, 0F);
			t_right_cover_back_left = new ModelRenderer(this, 0, 0);
			t_right_cover_back_left.addBox(-14.5F, -4.5F, 3F, 6, 5, 20);
			t_right_cover_back_left.setRotationPoint(0F, 3F, 2F);
			t_right_cover_back_left.setTextureSize(128, 64);
			t_right_cover_back_left.mirror = true;
			setRotation(t_right_cover_back_left, -0.6981317F, 0.0349066F, -0.0174533F);
			t_right_cover_back_right = new ModelRenderer(this, 0, 0);
			t_right_cover_back_right.addBox(-21F, -4.5F, 3F, 6, 5, 20);
			t_right_cover_back_right.setRotationPoint(0F, 3F, 2F);
			t_right_cover_back_right.setTextureSize(128, 64);
			t_right_cover_back_right.mirror = true;
			setRotation(t_right_cover_back_right, -0.6981317F, 0F, 0F);
			t_right_rotor_top = new ModelRenderer(this, 0, 0);
			t_right_rotor_top.addBox(-21.5F, 1F, -5F, 14, 5, 5);
			t_right_rotor_top.setRotationPoint(0F, 3F, 2F);
			t_right_rotor_top.setTextureSize(128, 64);
			t_right_rotor_top.mirror = true;
			setRotation(t_right_rotor_top, 0.7853982F, 0F, 0F);
			t_right_rotor_front = new ModelRenderer(this, 0, 0);
			t_right_rotor_front.addBox(-21.5F, -0.4F, -25.6F, 14, 5, 5);
			t_right_rotor_front.setRotationPoint(0F, 3F, 2F);
			t_right_rotor_front.setTextureSize(128, 64);
			t_right_rotor_front.mirror = true;
			setRotation(t_right_rotor_front, 0.7853982F, 0F, 0F);
			t_right_rotot_back = new ModelRenderer(this, 0, 0);
			t_right_rotot_back.addBox(-21.5F, 22F, -3F, 14, 5, 5);
			t_right_rotot_back.setRotationPoint(0F, 3F, 2F);
			t_right_rotot_back.setTextureSize(128, 64);
			t_right_rotot_back.mirror = true;
			setRotation(t_right_rotot_back, 0.7853982F, 0F, 0F);
			t_right_tread_front = new ModelRenderer(this, 0, 0);
			t_right_tread_front.addBox(-21F, 1.7F, -23F, 13, 4, 20);
			t_right_tread_front.setRotationPoint(0F, 3F, 2F);
			t_right_tread_front.setTextureSize(128, 64);
			t_right_tread_front.mirror = true;
			setRotation(t_right_tread_front, 0.6981317F, 0F, 0F);
			t_right_tread_back = new ModelRenderer(this, 0, 0);
			t_right_tread_back.addBox(-21F, 0.7F, 4F, 13, 4, 20);
			t_right_tread_back.setRotationPoint(0F, 3F, 2F);
			t_right_tread_back.setTextureSize(128, 64);
			t_right_tread_back.mirror = true;
			setRotation(t_right_tread_back, -0.6981317F, 0F, 0F);
			t_right_tread_bottom = new ModelRenderer(this, 0, 0);
			t_right_tread_bottom.addBox(-21F, 17.3F, -15F, 13, 4, 32);
			t_right_tread_bottom.setRotationPoint(0F, 3F, 2F);
			t_right_tread_bottom.setTextureSize(128, 64);
			t_right_tread_bottom.mirror = true;
			setRotation(t_right_tread_bottom, 0F, 0F, 0F);
			t_right_center_bottom = new ModelRenderer(this, 0, 0);
			t_right_center_bottom.addBox(-17F, 13.3F, -12F, 5, 4, 26);
			t_right_center_bottom.setRotationPoint(0F, 3F, 2F);
			t_right_center_bottom.setTextureSize(128, 64);
			t_right_center_bottom.mirror = true;
			setRotation(t_right_center_bottom, 0F, 0F, 0F);
			t_right_center_mid = new ModelRenderer(this, 0, 0);
			t_right_center_mid.addBox(-17F, 9.3F, -8F, 5, 4, 18);
			t_right_center_mid.setRotationPoint(0F, 3F, 2F);
			t_right_center_mid.setTextureSize(128, 64);
			t_right_center_mid.mirror = true;
			setRotation(t_right_center_mid, 0F, 0F, 0F);
			t_right_center_top = new ModelRenderer(this, 0, 0);
			t_right_center_top.addBox(-17F, 5.4F, -3F, 5, 4, 8);
			t_right_center_top.setRotationPoint(0F, 3F, 2F);
			t_right_center_top.setTextureSize(128, 64);
			t_right_center_top.mirror = true;
			setRotation(t_right_center_top, 0F, 0F, 0F);
			l_a_joint = new ModelRenderer(this, 0, 0);
			l_a_joint.addBox(-2F, -4F, -3F, 7, 7, 7);
			l_a_joint.setRotationPoint(14F, -10F, 2F);
			l_a_joint.setTextureSize(128, 64);
			l_a_joint.mirror = true;
			setRotation(l_a_joint, 0.7853982F, 0F, 0F);
			l_a_top = new ModelRenderer(this, 0, 0);
			l_a_top.addBox(0F, 1F, -3F, 5, 11, 5);
			l_a_top.setRotationPoint(14F, -10F, 2F);
			l_a_top.setTextureSize(128, 64);
			l_a_top.mirror = true;
			setRotation(l_a_top, 0.1396263F, 0F, -0.7853982F);
			l_a_forearm = new ModelRenderer(this, 0, 0);
			l_a_forearm.addBox(11F, -8.5F, -17F, 12, 12, 22);
			l_a_forearm.setRotationPoint(14F, -10F, 2F);
			l_a_forearm.setTextureSize(128, 64);
			l_a_forearm.mirror = true;
			setRotation(l_a_forearm, 0F, -0.1745329F, 0.7853982F);
			l_a_finger_top_left = new ModelRenderer(this, 0, 0);
			l_a_finger_top_left.addBox(15F, -13.7F, -17F, 4, 2, 4);
			l_a_finger_top_left.setRotationPoint(14F, -10F, 2F);
			l_a_finger_top_left.setTextureSize(128, 64);
			l_a_finger_top_left.mirror = true;
			setRotation(l_a_finger_top_left, 0.3490659F, -0.1745329F, 0.7853982F);
			l_a_finger_top_right = new ModelRenderer(this, 0, 0);
			l_a_finger_top_right.addBox(6.6F, -5F, -23F, 2, 4, 4);
			l_a_finger_top_right.setRotationPoint(14F, -10F, 2F);
			l_a_finger_top_right.setTextureSize(128, 64);
			l_a_finger_top_right.mirror = true;
			setRotation(l_a_finger_top_right, 0.2617994F, -0.5235988F, 0.7679449F);
			l_a_finger_bottom_left = new ModelRenderer(this, 0, 0);
			l_a_finger_bottom_left.addBox(24.2F, -5.5F, -14F, 2, 4, 4);
			l_a_finger_bottom_left.setRotationPoint(14F, -10F, 2F);
			l_a_finger_bottom_left.setTextureSize(128, 64);
			l_a_finger_bottom_left.mirror = true;
			setRotation(l_a_finger_bottom_left, -0.1919862F, 0.1745329F, 0.7679449F);
			l_a_finger_bottom_right = new ModelRenderer(this, 0, 0);
			l_a_finger_bottom_right.addBox(15F, 7F, -18.8F, 4, 2, 4);
			l_a_finger_bottom_right.setRotationPoint(14F, -10F, 2F);
			l_a_finger_bottom_right.setTextureSize(128, 64);
			l_a_finger_bottom_right.mirror = true;
			setRotation(l_a_finger_bottom_right, -0.3490659F, -0.1745329F, 0.7853982F);
			r_a_joint = new ModelRenderer(this, 0, 0);
			r_a_joint.addBox(-4F, -4F, -3F, 7, 7, 7);
			r_a_joint.setRotationPoint(-14F, -10F, 2F);
			r_a_joint.setTextureSize(128, 64);
			r_a_joint.mirror = true;
			setRotation(r_a_joint, 0.7853982F, 0F, 0F);
			r_a_top = new ModelRenderer(this, 0, 0);
			r_a_top.addBox(-5F, 1F, -3F, 5, 11, 5);
			r_a_top.setRotationPoint(-14F, -10F, 2F);
			r_a_top.setTextureSize(128, 64);
			r_a_top.mirror = true;
			setRotation(r_a_top, 0.1396263F, 0F, 0.7853982F);
			r_a_forearm = new ModelRenderer(this, 0, 0);
			r_a_forearm.addBox(-8.5F, 11F, -17F, 12, 12, 22);
			r_a_forearm.setRotationPoint(-14F, -10F, 2F);
			r_a_forearm.setTextureSize(128, 64);
			r_a_forearm.mirror = true;
			setRotation(r_a_forearm, 0F, 0.1745329F, 0.7853982F);
			r_a_finger_top_left = new ModelRenderer(this, 0, 0);
			r_a_finger_top_left.addBox(-8.6F, -5F, -23F, 2, 4, 4);
			r_a_finger_top_left.setRotationPoint(-14F, -10F, 2F);
			r_a_finger_top_left.setTextureSize(128, 64);
			r_a_finger_top_left.mirror = true;
			setRotation(r_a_finger_top_left, 0.2617994F, 0.5235988F, -0.7679449F);
			r_a_finger_top_right = new ModelRenderer(this, 0, 0);
			r_a_finger_top_right.addBox(-19F, -13.5F, -17F, 4, 2, 4);
			r_a_finger_top_right.setRotationPoint(-14F, -10F, 2F);
			r_a_finger_top_right.setTextureSize(128, 64);
			r_a_finger_top_right.mirror = true;
			setRotation(r_a_finger_top_right, 0.3490659F, 0.1745329F, -0.7853982F);
			r_a_finger_bottom_left = new ModelRenderer(this, 0, 0);
			r_a_finger_bottom_left.addBox(-19F, 7.1F, -18.8F, 4, 2, 4);
			r_a_finger_bottom_left.setRotationPoint(-14F, -10F, 2F);
			r_a_finger_bottom_left.setTextureSize(128, 64);
			r_a_finger_bottom_left.mirror = true;
			setRotation(r_a_finger_bottom_left, -0.3490659F, 0.1745329F, -0.7853982F);
			r_a_finger_bottom_right = new ModelRenderer(this, 0, 0);
			r_a_finger_bottom_right.addBox(-26.2F, -5.5F, -14F, 2, 4, 4);
			r_a_finger_bottom_right.setRotationPoint(-14F, -10F, 2F);
			r_a_finger_bottom_right.setTextureSize(128, 64);
			r_a_finger_bottom_right.mirror = true;
			setRotation(r_a_finger_bottom_right, -0.1919862F, -0.1745329F, -0.7679449F);
		}

		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
			super.render(entity, f, f1, f2, f3, f4, f5);
			setRotationAngles(f, f1, f2, f3, f4, f5, entity);
			head.render(f5);
			b_collar_front.render(f5);
			b_collar_front_left.render(f5);
			b_collar_front_right.render(f5);
			b_collar_left.render(f5);
			b_collar_right.render(f5);
			b_collar_back_left.render(f5);
			b_collar_back_right.render(f5);
			b_chest_top.render(f5);
			b_shoulder_left_front.render(f5);
			b_shoulder_left_mid.render(f5);
			b_shoulder_left_back.render(f5);
			b_shoulder_right_front.render(f5);
			b_shoulder_right_mid.render(f5);
			b_shoulder_right_back.render(f5);
			b_chest_main.render(f5);
			b_core.render(f5);
			b_crest_top.render(f5);
			b_crest_left_top.render(f5);
			b_crest_right_top.render(f5);
			b_crest_bottom.render(f5);
			b_crest_left.render(f5);
			b_crest_right.render(f5);
			b_chest_bottom.render(f5);
			b_back_top.render(f5);
			b_back_bottom.render(f5);
			b_waist.render(f5);
			t_mid_top.render(f5);
			t_mid_bottom.render(f5);
			t_left_cover_top.render(f5);
			t_left_cover_front_left.render(f5);
			t_left_cover_front_right.render(f5);
			t_left_cover_back_left.render(f5);
			t_left_cover_back_right.render(f5);
			t_left_rotor_top.render(f5);
			t_left_rotor_front.render(f5);
			t_left_rotor_back.render(f5);
			t_left_tread_front.render(f5);
			t_left_tread_back.render(f5);
			t_left_tread_bottom.render(f5);
			t_left_center_bottom.render(f5);
			t_left_center_mid.render(f5);
			t_left_center_top.render(f5);
			t_right_cover_top.render(f5);
			t_right_cover_front_left.render(f5);
			t_right_cover_front_right.render(f5);
			t_right_cover_back_left.render(f5);
			t_right_cover_back_right.render(f5);
			t_right_rotor_top.render(f5);
			t_right_rotor_front.render(f5);
			t_right_rotot_back.render(f5);
			t_right_tread_front.render(f5);
			t_right_tread_back.render(f5);
			t_right_tread_bottom.render(f5);
			t_right_center_bottom.render(f5);
			t_right_center_mid.render(f5);
			t_right_center_top.render(f5);
			l_a_joint.render(f5);
			l_a_top.render(f5);
			l_a_forearm.render(f5);
			l_a_finger_top_left.render(f5);
			l_a_finger_top_right.render(f5);
			l_a_finger_bottom_left.render(f5);
			l_a_finger_bottom_right.render(f5);
			r_a_joint.render(f5);
			r_a_top.render(f5);
			r_a_forearm.render(f5);
			r_a_finger_top_left.render(f5);
			r_a_finger_top_right.render(f5);
			r_a_finger_bottom_left.render(f5);
			r_a_finger_bottom_right.render(f5);
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
