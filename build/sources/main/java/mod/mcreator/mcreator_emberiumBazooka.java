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
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

public class mcreator_emberiumBazooka extends nebulus.ModElement {

	public static Item block;
	public static final int ENTITYID = 34;
	static {
		block = (new ItemgGUN());
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		EntityRegistry.registerModEntity(new ResourceLocation("nebulus:entitybulletemberiumbazooka"), EntityArrowCustom.class,
				"entitybulletemberiumbazooka", ENTITYID, instance, 64, 1, true);
		ForgeRegistries.ITEMS.register(block);
	}

	@Override
	public void load(FMLInitializationEvent event) {
		if (event.getSide() == Side.CLIENT) {
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
					.register(block, 0, new ModelResourceLocation("nebulus:emberiumbazooka", "inventory"));
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
			setUnlocalizedName("emberiumbazooka");
			setRegistryName("emberiumbazooka");
			setCreativeTab(mcreator_emberrusBandtandwandrm.tab);
		}

		@Override
		public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityLivingBase entityLivingBase, int timeLeft) {
			if (!world.isRemote && entityLivingBase instanceof EntityPlayer) {
				EntityPlayer entity = (EntityPlayer) entityLivingBase;
				boolean flag = entity.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemstack) > 0;
				if (flag || entity.inventory.hasItemStack(new ItemStack(mcreator_emberiumGrenade.block, (int) (1)))) {
					float f = 1F;
					EntityArrowCustom entityarrow = new EntityArrowCustom(world, entity);
					entityarrow.shoot(entity.getLookVec().x, entity.getLookVec().y, entity.getLookVec().z, f * 2.0F, 0);
					entityarrow.setIsCritical(true);
					entityarrow.setDamage(5);
					entityarrow.setKnockbackStrength(5);
					entityarrow.setFire(100);
					itemstack.damageItem(1, entity);
					int x = (int) entity.posX;
					int y = (int) entity.posY;
					int z = (int) entity.posZ;
					world.playSound((EntityPlayer) null, (double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D,
							(net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation(
									("item.firecharge.use"))), SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					if (flag) {
						entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
					} else {
						entity.inventory.clearMatchingItems(new ItemStack(mcreator_emberiumGrenade.block, (int) (1)).getItem(), -1, 1, null);
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
			{
				java.util.HashMap<String, Object> $_dependencies = new java.util.HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				mcreator_emberiumBazookaWhileBulletFlyingTick.executeProcedure($_dependencies);
			}
			if (this.inGround) {
				{
					java.util.HashMap<String, Object> $_dependencies = new java.util.HashMap<>();
					$_dependencies.put("x", x);
					$_dependencies.put("y", y);
					$_dependencies.put("z", z);
					$_dependencies.put("world", world);
					mcreator_emberiumBazookaBulletHitsBlock.executeProcedure($_dependencies);
				}
				this.world.removeEntity(this);
			}
		}
	}

	public static class RenderCustom extends Render {

		private static final ResourceLocation tex = new ResourceLocation("emberium_grenade.png");

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
			ModelBase model = new Modelemberiumgrenade();
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

	// Made with Blockbench
	// Paste this code into your mod.
	public static class Modelemberiumgrenade extends ModelBase {

		private final ModelRenderer bone;

		public Modelemberiumgrenade() {
			textureWidth = 32;
			textureHeight = 32;
			bone = new ModelRenderer(this);
			bone.setRotationPoint(0.0F, 24.0F, 0.0F);
			bone.cubeList.add(new ModelBox(bone, 0, 6, -2.0F, -1.0F, -1.0F, 3, 1, 3, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 8, 14, -2.0F, -4.0F, -2.0F, 3, 3, 1, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 0, 10, 1.0F, -4.0F, -1.0F, 1, 3, 3, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 0, 16, -1.0F, -2.0F, 0.0F, 1, 1, 1, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 15, 13, -2.0F, -2.0F, 0.0F, 1, 1, 1, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 15, 9, -1.0F, -2.0F, -1.0F, 1, 1, 1, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 15, 0, 0.0F, -2.0F, 0.0F, 1, 1, 1, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 14, 3, -1.0F, -2.0F, 1.0F, 1, 1, 1, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 12, 1, -1.0F, -3.0F, 0.0F, 1, 1, 1, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 9, 3, -3.0F, -4.0F, -1.0F, 1, 3, 3, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 8, 10, -2.0F, -4.0F, 2.0F, 3, 3, 1, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 0, 1, -2.0F, -5.0F, -1.0F, 3, 2, 3, 0.0F, false));
			bone.cubeList.add(new ModelBox(bone, 9, 0, -1.0F, -6.0F, 0.0F, 1, 1, 1, 0.0F, false));
		}

		@Override
		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
			bone.render(f5);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}
	}
}
