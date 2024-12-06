package com.iafenvoy.iceandfire.data.component;

import com.iafenvoy.iceandfire.IceAndFire;
import com.iafenvoy.iceandfire.impl.ComponentManager;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.registry.IafWorld;
import com.iafenvoy.iceandfire.world.processor.DreadPortalProcessor;
import com.iafenvoy.iceandfire.world.util.DimensionUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class PortalData {
    private final PlayerEntity player;
    private boolean teleported = false;
    private int teleportTick = -1;

    public PortalData(PlayerEntity player) {
        this.player = player;
    }

    public void tick() {
        World world = this.player.getWorld();
        if (!this.teleported && this.teleportTick == 0 && world instanceof ServerWorld serverWorld) {
            this.teleported = true;
            MinecraftServer server = serverWorld.getServer();
            if (world.getRegistryKey().getValue().equals(IafWorld.DREAD_LAND.getValue()))
                DimensionUtil.changeDimension(this.player, server.getWorld(World.OVERWORLD), new TeleportTarget(this.player.getPos(), Vec3d.ZERO, this.player.headYaw, this.player.getPitch()));
            else {
                ServerWorld dreadLand = server.getWorld(IafWorld.DREAD_LAND);
                if (dreadLand == null) return;
                DimensionUtil.changeDimension(this.player, dreadLand, new TeleportTarget(this.player.getPos(), Vec3d.ZERO, this.player.headYaw, this.player.getPitch()));
                if (!dreadLand.getBlockState(this.player.getBlockPos()).isOf(IafBlocks.DREAD_PORTAL.get()))
                    server.getStructureTemplateManager().getTemplate(new Identifier(IceAndFire.MOD_ID, "dread_exit_portal")).ifPresent(structureTemplate -> structureTemplate.place(dreadLand, this.player.getBlockPos().subtract(new BlockPos(2, 1, 2)), BlockPos.ORIGIN, new StructurePlacementData().addProcessor(new DreadPortalProcessor()), dreadLand.random, 2));
            }
        }
        if (world.getBlockState(this.player.getBlockPos()).isOf(IafBlocks.DREAD_PORTAL.get())) {
            if (this.teleportTick > 0) this.teleportTick--;
            else if (this.teleportTick == -1) this.teleportTick = 100;
        } else {
            this.teleported = false;
            this.teleportTick = -1;
        }
    }

    public void readFromNbt(NbtCompound tag) {
        this.setTeleported(tag.getBoolean("teleported"));
        this.setTeleportTick(tag.getInt("teleport_tick"));
    }

    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("teleported", this.isTeleported());
        tag.putInt("teleport_tick", this.getTeleportTick());
    }

    public boolean isTeleported() {
        return this.teleported;
    }

    public int getTeleportTick() {
        return this.teleportTick;
    }

    public void setTeleported(boolean teleported) {
        this.teleported = teleported;
    }

    public void setTeleportTick(int teleportTick) {
        this.teleportTick = teleportTick;
    }

    public static PortalData get(PlayerEntity player) {
        return ComponentManager.getPortalData(player);
    }
}
