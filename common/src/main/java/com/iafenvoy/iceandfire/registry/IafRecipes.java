package com.iafenvoy.iceandfire.registry;

import com.iafenvoy.iceandfire.IceAndFire;
import com.iafenvoy.iceandfire.recipe.DragonForgeRecipe;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.DispenserBlock;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryKeys;

public final class IafRecipes {
    public static final DeferredRegister<RecipeType<?>> REGISTRY = DeferredRegister.create(IceAndFire.MOD_ID, RegistryKeys.RECIPE_TYPE);
    public static final RegistrySupplier<RecipeType<DragonForgeRecipe>> DRAGON_FORGE_TYPE = REGISTRY.register("dragonforge", () -> new RecipeType<>() {
        @Override
        public String toString() {
            return "dragonforge";
        }
    });

    public static void registerDispenser() {
        DispenserBlock.registerProjectileBehavior(IafItems.STYMPHALIAN_ARROW.get());
        DispenserBlock.registerProjectileBehavior(IafItems.AMPHITHERE_ARROW.get());
        DispenserBlock.registerProjectileBehavior(IafItems.SEA_SERPENT_ARROW.get());
        DispenserBlock.registerProjectileBehavior(IafItems.DRAGONBONE_ARROW.get());
        DispenserBlock.registerProjectileBehavior(IafItems.HYDRA_ARROW.get());
        DispenserBlock.registerProjectileBehavior(IafItems.HIPPOGRYPH_EGG.get());
        DispenserBlock.registerProjectileBehavior(IafItems.ROTTEN_EGG.get());
        DispenserBlock.registerProjectileBehavior(IafItems.DEATHWORM_EGG.get());
        DispenserBlock.registerProjectileBehavior(IafItems.DEATHWORM_EGG_GIGANTIC.get());
    }

    public static void init() {
        registerDispenser();
    }
}
