package mod.mcreator;

import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;

import net.minecraftforge.fml.common.FMLCommonHandler;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.GuiButton;

import java.util.HashMap;

public class mcreator_aTGUI extends nebulus.ModElement {

	public static int GUIID = 2;
	public static HashMap guiinventory = new HashMap();
	public static IInventory aquatict;
	public static IInventory inherited;

	public static class GuiContainerMod extends Container {

		World world;
		EntityPlayer entity;
		int x, y, z;

		public GuiContainerMod(World world, int x, int y, int z, EntityPlayer player) {
			this.world = world;
			this.entity = player;
			this.x = x;
			this.y = y;
			this.z = z;
			TileEntity ent = world.getTileEntity(new BlockPos(x, y, z));
			if (ent != null && ent instanceof mcreator_aquaticTable.TileEntityCustom)
				inherited = (IInventory) ent;
			else
				inherited = new InventoryBasic("", true, 9);
			aquatict = new InventoryBasic("aquatict", true, 255);
			guiinventory.put("aquatict", aquatict);
			this.addSlotToContainer(new Slot(inherited, 0, 8, 30) {
			});
			this.addSlotToContainer(new Slot(inherited, 1, 26, 30) {
			});
			this.addSlotToContainer(new Slot(inherited, 2, 44, 30) {
			});
			this.addSlotToContainer(new Slot(inherited, 3, 62, 30) {
			});
			this.addSlotToContainer(new Slot(inherited, 4, 80, 30) {
			});
			this.addSlotToContainer(new Slot(inherited, 5, 98, 30) {
			});
			this.addSlotToContainer(new Slot(inherited, 7, 116, 30) {
			});
			this.addSlotToContainer(new Slot(inherited, 8, 134, 30) {
			});
			this.addSlotToContainer(new Slot(inherited, 9, 152, 30) {
			});
			this.addSlotToContainer(new Slot(inherited, 10, 8, 48) {
			});
			this.addSlotToContainer(new Slot(inherited, 11, 26, 48) {
			});
			this.addSlotToContainer(new Slot(inherited, 12, 44, 48) {
			});
			this.addSlotToContainer(new Slot(inherited, 13, 62, 48) {
			});
			this.addSlotToContainer(new Slot(inherited, 14, 80, 48) {
			});
			this.addSlotToContainer(new Slot(inherited, 15, 98, 48) {
			});
			this.addSlotToContainer(new Slot(inherited, 18, 152, 48) {
			});
			this.addSlotToContainer(new Slot(inherited, 16, 116, 48) {
			});
			this.addSlotToContainer(new Slot(inherited, 17, 134, 48) {
			});
			int si;
			int sj;
			for (si = 0; si < 3; ++si)
				for (sj = 0; sj < 9; ++sj)
					this.addSlotToContainer(new Slot(player.inventory, sj + (si + 1) * 9, 0 + 8 + sj * 18, 0 + 84 + si * 18));
			for (si = 0; si < 9; ++si)
				this.addSlotToContainer(new Slot(player.inventory, si, 0 + 8 + si * 18, 0 + 142));
		}

		@Override
		public boolean canInteractWith(EntityPlayer player) {
			return true;
		}

		@Override
		public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
			ItemStack itemstack = null;
			Slot slot = (Slot) this.inventorySlots.get(index);
			if (slot != null && slot.getHasStack()) {
				ItemStack itemstack1 = slot.getStack();
				itemstack = itemstack1.copy();
				if (index < 9) {
					if (!this.mergeItemStack(itemstack1, 9, (45 - 9), true))
						return ItemStack.EMPTY;
				} else if (!this.mergeItemStack(itemstack1, 0, 9, false)) {
					return ItemStack.EMPTY;
				}
				if (itemstack1.getCount() == 0) {
					slot.putStack(ItemStack.EMPTY);
				} else {
					slot.onSlotChanged();
				}
				if (itemstack1.getCount() == itemstack.getCount()) {
					return ItemStack.EMPTY;
				}
				slot.onTake(playerIn, itemstack1);
			}
			return itemstack;
		}

		public void onContainerClosed(EntityPlayer playerIn) {
			super.onContainerClosed(playerIn);
		}
	}

	public static class GuiWindow extends GuiContainer {

		int x, y, z;
		EntityPlayer entity;

		public GuiWindow(World world, int x, int y, int z, EntityPlayer entity) {
			super(new GuiContainerMod(world, x, y, z, entity));
			this.x = x;
			this.y = y;
			this.z = z;
			this.entity = entity;
			this.xSize = 176;
			this.ySize = 166;
		}

		private static final ResourceLocation texture = new ResourceLocation("atgui.png");

		@Override
		public void drawScreen(int mouseX, int mouseY, float partialTicks) {
			this.drawDefaultBackground();
			super.drawScreen(mouseX, mouseY, partialTicks);
			this.renderHoveredToolTip(mouseX, mouseY);
		}

		@Override
		protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawDefaultBackground();
			this.mc.renderEngine.bindTexture(texture);
			int k = (this.width - this.xSize) / 2;
			int l = (this.height - this.ySize) / 2;
			this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
			zLevel = 100.0F;
		}

		@Override
		protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
			try {
				super.mouseClicked(mouseX, mouseY, mouseButton);
			} catch (Exception ignored) {
			}
		}

		@Override
		public void updateScreen() {
			super.updateScreen();
		}

		@Override
		protected void keyTyped(char typedChar, int keyCode) {
			try {
				super.keyTyped(typedChar, keyCode);
			} catch (Exception ignored) {
			}
		}

		@Override
		protected void drawGuiContainerForegroundLayer(int par1, int par2) {
			this.fontRenderer.drawString("Aquatic Chest", 7, 11, -16777216);
		}

		@Override
		public void onGuiClosed() {
			super.onGuiClosed();
			Keyboard.enableRepeatEvents(false);
		}

		@Override
		public void initGui() {
			super.initGui();
			this.guiLeft = (this.width - 176) / 2;
			this.guiTop = (this.height - 166) / 2;
			Keyboard.enableRepeatEvents(true);
			this.buttonList.clear();
		}

		@Override
		protected void actionPerformed(GuiButton button) {
			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			World world = server.getWorld(entity.dimension);
		}

		@Override
		public boolean doesGuiPauseGame() {
			return false;
		}
	}
}
