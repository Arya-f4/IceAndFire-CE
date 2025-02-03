package com.iafenvoy.iceandfire.world;

import com.iafenvoy.iceandfire.IceAndFire;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DragonPosWorldData extends PersistentState {
    private static final Type<DragonPosWorldData> TYPE = new Type<>(DragonPosWorldData::new, DragonPosWorldData::new, DataFixTypes.CHUNK);
    private static final String IDENTIFIER = "iceandfire_dragonPositions";
    protected final Map<UUID, BlockPos> lastDragonPositions = new HashMap<>();
    private int tickCounter;

    public DragonPosWorldData() {
    }

    public DragonPosWorldData(NbtCompound compoundTag, RegistryWrapper.WrapperLookup registryLookup) {
        this.load(compoundTag);
    }

    public static DragonPosWorldData get(World world) {
        if (world instanceof ServerWorld serverWorld) {
            PersistentStateManager storage = serverWorld.getPersistentStateManager();
            DragonPosWorldData data = storage.getOrCreate(TYPE, IDENTIFIER);
            if (data != null) data.markDirty();
            return data;
        }
        return null;
    }

    public void addDragon(UUID uuid, BlockPos pos) {
        this.lastDragonPositions.put(uuid, pos);
        this.markDirty();
    }

    public void removeDragon(UUID uuid) {
        this.lastDragonPositions.remove(uuid);
        this.markDirty();
    }

    public BlockPos getDragonPos(UUID uuid) {
        return this.lastDragonPositions.get(uuid);
    }

    public void debug() {
        IceAndFire.LOGGER.warn(this.lastDragonPositions.toString());
    }


    public void tick() {
        ++this.tickCounter;
    }

    public void load(NbtCompound nbt) {
        this.tickCounter = nbt.getInt("Tick");
        NbtList nbttaglist = nbt.getList("DragonMap", 10);
        this.lastDragonPositions.clear();
        for (int i = 0; i < nbttaglist.size(); ++i) {
            NbtCompound CompoundNBT = nbttaglist.getCompound(i);
            UUID uuid = CompoundNBT.getUuid("DragonUUID");
            BlockPos pos = new BlockPos(CompoundNBT.getInt("DragonPosX"), CompoundNBT.getInt("DragonPosY"), CompoundNBT.getInt("DragonPosZ"));
            this.lastDragonPositions.put(uuid, pos);
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound compound, RegistryWrapper.WrapperLookup registryLookup) {
        compound.putInt("Tick", this.tickCounter);
        NbtList nbttaglist = new NbtList();
        for (Map.Entry<UUID, BlockPos> pair : this.lastDragonPositions.entrySet()) {
            NbtCompound CompoundNBT = new NbtCompound();
            CompoundNBT.putUuid("DragonUUID", pair.getKey());
            CompoundNBT.putInt("DragonPosX", pair.getValue().getX());
            CompoundNBT.putInt("DragonPosY", pair.getValue().getY());
            CompoundNBT.putInt("DragonPosZ", pair.getValue().getZ());
            nbttaglist.add(CompoundNBT);
        }
        compound.put("DragonMap", nbttaglist);
        return compound;
    }
}
