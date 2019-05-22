package mod.mcreator;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ActionResult;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.EnumAction;
import net.minecraft.init.Enchantments;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.Minecraft;

public class mcreator_nebulizer extends nebulus.ModElement {

	public static Item block;
	public static final int ENTITYID = 1;
	static {
		block = (new ItemgGUN());
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		EntityRegistry.registerModEntity(new ResourceLocation("nebulus:entitybulletnebulizer"), EntityArrowCustom.class, "entitybulletnebulizer",
				ENTITYID, instance, 64, 1, true);
		ForgeRegistries.ITEMS.register(block);
	}

	@Override
	public void load(FMLInitializationEvent event) {
		if (event.getSide() == Side.CLIENT) {
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
					.register(block, 0, new ModelResourceLocation("nebulus:nebulizer", "inventory"));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityArrowCustom.class, new RenderCustom(Minecraft.getMinecraft().getRenderManager()));
	}

	public static class ItemgGUN extends Item {

		public ItemgGUN() {
			super();
			setMaxDamage(100);
			maxStackSize = 1;
			setFull3D();
			setUnlocalizedName("nebulizer");
			setRegistryName("nebulizer");
			setCreativeTab(mcreator_nebulustandw.tab);
		}

		@Override
		public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityLivingBase entityLivingBase, int timeLeft) {
			if (!world.isRemote && entityLivingBase instanceof EntityPlayer) {
				EntityPlayer entity = (EntityPlayer) entityLivingBase;
				boolean flag = entity.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemstack) > 0;
				if (flag || entity.inventory.hasItemStack(new ItemStack(mcreator_nebulizingSolution.block, (int) (1)))) {
					float f = 1F;
					EntityArrowCustom entityarrow = new EntityArrowCustom(world, entity);
					entityarrow.shoot(entity.getLookVec().x, entity.getLookVec().y, entity.getLookVec().z, f * 2.0F, 0);
					entityarrow.setIsCritical(true);
					entityarrow.setDamage(5);
					entityarrow.setKnockbackStrength(5);
					itemstack.damageItem(1, entity);
					int x = (int) entity.posX;
					int y = (int) entity.posY;
					int z = (int) entity.posZ;
					world.playSound((EntityPlayer) null, (double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D,
							(net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation(
									("block.anvil.fall"))), SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					if (flag) {
						entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
					} else {
						entity.inventory.clearMatchingItems(new ItemStack(mcreator_nebulizingSolution.block, (int) (1)).getItem(), -1, 1, null);
					}
					if (!world.isRemote)
						world.spawnEntity(entityarrow);
				}
			}
		}

		@Override
		public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entity, EnumHand hand) {
			entity.setActiveHand(hand);
			return new ActionResult(EnumActionResult.SUCCESS, entity.getHeldItem(hand));
		}

		@Override
		public EnumAction getItemUseAction(ItemStack itemstack) {
			return EnumAction.BOW;
		}

		@Override
		public int getMaxItemUseDuration(ItemStack itemstack) {
			return 72000;
		}
	}

	public static class EntityArrowCustom extends EntityTippedArrow {

		public EntityArrowCustom(World a) {
			super(a);
		}

		public EntityArrowCustom(World worldIn, double x, double y, double z) {
			super(worldIn, x, y, z);
		}

		public EntityArrowCustom(World worldIn, EntityLivingBase shooter) {
			super(worldIn, shooter);
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			int x = MathHelper.floor(this.getEntityBoundingBox().minX);
			int y = MathHelper.floor(this.getEntityBoundingBox().minY);
			int z = MathHelper.floor(this.getEntityBoundingBox().minZ);
			World world = this.world;
			Entity entity = (Entity) shootingEntity;
			if (this.inGround) {
				this.world.removeEntity(this);
			}
		}
	}

	public static class RenderCustom extends Render {

		private static final ResourceLocation tex = new ResourceLocation("battle_golem.png");

		public RenderCustom(RenderManager renderManager) {
			super(renderManager);
			shadowSize = 0.1F;
		}

		public void render(EntityArrowCustom bullet, double d, double d1, double d2, float f, float f1) {
			bindEntityTexture(bullet);
			GL11.glPushMatrix();
			GL11.glTranslatef((float) d, (float) d1, (float) d2);
			GL11.glRotatef(f, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(90F - bullet.prevRotationPitch - (bullet.rotationPitch - bullet.prevRotationPitch) * f1, 1.0F, 0.0F, 0.0F);
			ModelBase model = new ModelTrackingSentry();
			model.render(bullet, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
		}

		@Override
		public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
			render((EntityArrowCustom) entity, d, d1, d2, f, f1);
		}

		@Override
		protected ResourceLocation getEntityTexture(Entity entity) {
			return tex;
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
