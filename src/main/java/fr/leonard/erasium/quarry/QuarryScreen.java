package fr.leonard.erasium.quarry;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.leonard.erasium.Erasium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CRenameItemPacket;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class QuarryScreen extends ContainerScreen<QuarryContainer> implements IContainerListener {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Erasium.MODID, "textures/gui/quarry.png");
    private TextFieldWidget nameField;
    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("erasium:textures/gui/button.png");
    private static final ResourceLocation BUTTON_OFF_TEXTURE = new ResourceLocation("erasium:textures/gui/button_off.png");
    public static String stock = "Drop";
    public static String onOff = "Off";

    public QuarryScreen(QuarryContainer quarryContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(quarryContainer, playerInventory, iTextComponent);
    }

    protected void init () {
        super.init();
        assert this.minecraft != null;
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.xSize = 176;
        this.ySize = 219;

        nameField = new TextFieldWidget(this.font, this.guiLeft + 70, this.guiTop -4, 98, 12, I18n.format("container.radius"));
        nameField.setCanLoseFocus(false);
        nameField.changeFocus(true);
        nameField.setTextColor(-1);
        nameField.setMaxStringLength(6);
        nameField.setDisabledTextColour(-1);
        nameField.setEnableBackgroundDrawing(false);
        nameField.setMaxStringLength(35);
        nameField.setResponder(this::func_214075_a);
        this.children.add(this.nameField);
        this.container.addListener(this);
        this.setFocusedDefault(this.nameField);
        nameField.setText(String.valueOf(QuarryTileEntity.radius));

        this.addButton(new ImageButton(this.guiLeft +8, this.guiTop -9, 20, 20, 0, 0, 20,
                BUTTON_TEXTURE, (button) -> {
            QuarryTileEntity.dropBlock = !QuarryTileEntity.dropBlock;
            if (stock.equals("Stock")) {
                stock = "Drop";
            } else if (onOff.equals("Drop")) {
                stock = "Stock";
            }
        }));
        addButton(new ImageButton(this.guiLeft +38, this.guiTop -9, 20, 20, 0, 0, 20,
                BUTTON_OFF_TEXTURE, (button2) -> {
            QuarryTileEntity.isActive = !QuarryTileEntity.isActive;
            QuarryTileEntity.tick = 0;
            if (onOff.equals("Off")) {
                onOff = "On";
            } else if (onOff.equals("On")) {
                onOff = "Off";
            }
        }));
    }

    public void resize(@NotNull Minecraft p_resize_1_, int p_resize_2_, int p_resize_3_) {
        String s = this.nameField.getText();
        this.init(p_resize_1_, p_resize_2_, p_resize_3_);
        nameField.setText(s);
    }

    public void removed() {
        super.removed();
        assert this.minecraft != null;
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.container.removeListener(this);
    }

    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (p_keyPressed_1_ == 256 || p_keyPressed_1_ == 300) {
            assert this.minecraft != null;
            assert this.minecraft.player != null;
            this.minecraft.player.closeScreen(); //Si une lettre est entrée, double vérification
        } else if (p_keyPressed_1_ > 64 && p_keyPressed_1_ < 97 || p_keyPressed_1_ > 329 && p_keyPressed_1_ < 336) {
            if (nameField != null) {
                nameField.setText(String.valueOf(QuarryTileEntity.radius));
            }
        }if (p_keyPressed_1_ > 47 && p_keyPressed_1_ < 58) {
            QuarryTileEntity.isActive = false;
        }
        assert nameField != null;
        return nameField.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_) || this.nameField.canWrite()
                || super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
    }

    @Override
    public void render(int  p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        super.render(p_render_1_, p_render_2_, p_render_3_);
        RenderSystem.disableBlend();
        nameField.render(p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredToolTip(p_render_1_, p_render_2_);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.font.drawString(this.title.getFormattedText(), 8.0f, -20.0f, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0f, 100.0f, 4210752);
        this.font.drawString(stock, 8.0f, 16.0f, 4210752);
        this.font.drawString(onOff, 40.0f, 16.0f, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.blit(x, y, 0, 0, this.xSize, this.ySize);
    }

    /**
     * update the crafting window inventory with the items in the list
     */
    @Override
    public void sendAllContents(@NotNull Container containerToSend, @NotNull NonNullList<ItemStack> itemsList) {
        this.sendSlotContents(containerToSend, 0, containerToSend.getSlot(0).getStack());
    }

    /**
     * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
     * contents of that slot.
     */
    @Override
    public void sendSlotContents(@NotNull Container containerToSend, int slotInd, @NotNull ItemStack stack) {
        if (slotInd == 0) {
            nameField.setText(nameField.getText());
            nameField.setEnabled(true);
        }
    }

    /**
     * Sends two ints to the client-side Container. Used for furnace burning time, smelting progress, brewing progress,
     * and enchanting level. Normally the first int identifies which variable to update, and the second contains the new
     * value. Both are truncated to shorts in non-local SMP.
     */
    @Override
    public void sendWindowProperty(@NotNull Container containerIn, int varToUpdate, int newValue) {

    }

    private void func_214075_a(String p_214075_1_) {
        if (!p_214075_1_.isEmpty()) {
            String s = p_214075_1_;
            Slot slot = this.container.getSlot(0);
            if (slot.getHasStack() && !slot.getStack().hasDisplayName() && p_214075_1_.equals(slot.getStack().getDisplayName().getString())) {
                s = "";
            }

            if (!s.equals("")) {
                //Si y'a pas un chiffre
                if (s.contains("a") || s.contains("b") || s.contains("c") || s.contains("d")|| s.contains("e")
                        || s.contains("f") || s.contains("g") || s.contains("h") || s.contains("i") || s.contains("j")
                        || s.contains("k") || s.contains("l") || s.contains("m") || s.contains("n") || s.contains("o")
                        || s.contains("p") || s.contains("q") || s.contains("r") || s.contains("s") || s.contains("t")
                        || s.contains("u") || s.contains("v") || s.contains("w") || s.contains("x") || s.contains("y")
                        || s.contains("&") || s.contains("é") || s.contains("\"") || s.contains("'") || s.contains("(")
                        || s.contains("-") || s.contains("è") || s.contains("_") || s.contains("ç") || s.contains("à")
                        || s.contains(")") || s.contains("=") || s.contains("~") || s.contains("#") || s.contains("{")
                        || s.contains("[") || s.contains("|") || s.contains("`") || s.contains("\\\\") || s.contains("^")
                        || s.contains("@") || s.contains("]") || s.contains("°") || s.contains("+") || s.contains("}")
                        || s.contains("/") || s.contains("*") || s.contains("€") || s.contains("¨") || s.contains("$")
                        || s.contains("£") || s.contains("z") || s.contains("¤") || s.contains("%") || s.contains("ù")
                        || s.contains("µ") || s.contains(",") || s.contains(".") || s.contains("?") || s.contains("!")
                        || s.contains(";") || s.contains("§") || s.contains("<") || s.contains(">") || s.contains("A")
                        || s.contains("B") || s.contains("C") || s.contains("D")|| s.contains("E") || s.contains("F")
                        || s.contains("G") || s.contains("H") || s.contains("I") || s.contains("J") || s.contains("K")
                        || s.contains("L") || s.contains("M") || s.contains("N") || s.contains("O") || s.contains("P")
                        || s.contains("Q") || s.contains("R") || s.contains("S") || s.contains("T") || s.contains("U")
                        || s.contains("V") || s.contains("W") || s.contains("X") || s.contains("Y") || s.contains("Z") || s.contains("²")) {
                   s = String.valueOf(QuarryTileEntity.radius);
                   nameField.setText(s);
                } else if (s.contains("1") || s.contains("2") || s.contains("3") || s.contains("4") || s.contains("5")
                        || s.contains("6") || s.contains("7") || s.contains("8") || s.contains("9") || s.contains("0")) {
                    QuarryTileEntity.radius = Integer.parseInt(s);
                } else {
                    QuarryTileEntity.radius = 3;
                }
            }
            assert this.minecraft != null;
            assert this.minecraft.player != null;
            this.minecraft.player.connection.sendPacket(new CRenameItemPacket(s));
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
