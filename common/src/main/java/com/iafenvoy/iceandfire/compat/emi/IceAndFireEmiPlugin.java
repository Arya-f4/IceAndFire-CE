package com.iafenvoy.iceandfire.compat.emi;

import com.iafenvoy.iceandfire.IceAndFire;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.util.Identifier;

@EmiEntrypoint
public class IceAndFireEmiPlugin implements EmiPlugin {
    private static final ForgeRecipeHolder FIRE = new ForgeRecipeHolder(Identifier.of(IceAndFire.MOD_ID, "fire_forge"), "fire", new EmiTexture(Identifier.of(IceAndFire.MOD_ID, "textures/gui/dragonforge_fire.png"), 3, 4, 170, 79), EmiStack.of(IafBlocks.DRAGONFORGE_FIRE_CORE.get()));
    private static final ForgeRecipeHolder ICE = new ForgeRecipeHolder(Identifier.of(IceAndFire.MOD_ID, "ice_forge"), "ice", new EmiTexture(Identifier.of(IceAndFire.MOD_ID, "textures/gui/dragonforge_ice.png"), 3, 4, 170, 79), EmiStack.of(IafBlocks.DRAGONFORGE_ICE_CORE.get()));
    private static final ForgeRecipeHolder LIGHTNING = new ForgeRecipeHolder(Identifier.of(IceAndFire.MOD_ID, "lightning_forge"), "lightning", new EmiTexture(Identifier.of(IceAndFire.MOD_ID, "textures/gui/dragonforge_lightning.png"), 3, 4, 170, 79), EmiStack.of(IafBlocks.DRAGONFORGE_LIGHTNING_CORE.get()));

    @Override
    public void register(EmiRegistry registry) {
        FIRE.register(registry);
        ICE.register(registry);
        LIGHTNING.register(registry);
    }
}
