package com.bgsoftware.wildloaders.nms.v1_19_R1;

import com.bgsoftware.wildloaders.api.holograms.Hologram;
import com.bgsoftware.common.remaps.Remap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftChatMessage;

@SuppressWarnings("unused")
public final class EntityHolograms extends EntityArmorStand implements Hologram {

    private static final AxisAlignedBB EMPTY_BOUND = new AxisAlignedBB(0D, 0D, 0D, 0D, 0D, 0D);

    private CraftEntity bukkitEntity;

    @Remap(classPath = "net.minecraft.world.entity.decoration.ArmorStand", name = "setInvisible", type = Remap.Type.METHOD, remappedName = "j")
    @Remap(classPath = "net.minecraft.world.entity.decoration.ArmorStand", name = "setSmall", type = Remap.Type.METHOD, remappedName = "a")
    @Remap(classPath = "net.minecraft.world.entity.decoration.ArmorStand", name = "setShowArms", type = Remap.Type.METHOD, remappedName = "r")
    @Remap(classPath = "net.minecraft.world.entity.Entity", name = "setNoGravity", type = Remap.Type.METHOD, remappedName = "e")
    @Remap(classPath = "net.minecraft.world.entity.decoration.ArmorStand", name = "setNoBasePlate", type = Remap.Type.METHOD, remappedName = "s")
    @Remap(classPath = "net.minecraft.world.entity.decoration.ArmorStand", name = "setMarker", type = Remap.Type.METHOD, remappedName = "t")
    @Remap(classPath = "net.minecraft.world.entity.Entity", name = "setCustomNameVisible", type = Remap.Type.METHOD, remappedName = "n")
    @Remap(classPath = "net.minecraft.world.entity.Entity", name = "setBoundingBox", type = Remap.Type.METHOD, remappedName = "a")
    public EntityHolograms(World world, double x, double y, double z) {
        super(world, x, y, z);
        j(true); // Invisible
        a(true); // Small
        r(false); // Arms
        e(true); // No Gravity
        s(true); // Base Plate
        t(true); // Marker
        super.collides = false;
        super.n(true); // Custom name visible
        super.a(EMPTY_BOUND);
    }

    @Remap(classPath = "net.minecraft.world.entity.Entity",
            name = "setCustomName",
            type = Remap.Type.METHOD,
            remappedName = "b")
    @Override
    public void setHologramName(String name) {
        super.b(CraftChatMessage.fromStringOrNull(name));
    }

    @Remap(classPath = "net.minecraft.world.entity.Entity",
            name = "remove",
            type = Remap.Type.METHOD,
            remappedName = "a")
    @Override
    public void removeHologram() {
        super.a(RemovalReason.b);
    }

    @Override
    public org.bukkit.entity.Entity getEntity() {
        return getBukkitEntity();
    }

    @Remap(classPath = "net.minecraft.world.entity.Entity",
            name = "tick",
            type = Remap.Type.METHOD,
            remappedName = "k")
    @Override
    public void k() {
        // Disable normal ticking for this entity.

        // Workaround to force EntityTrackerEntry to send a teleport packet immediately after spawning this entity.
        if (this.y) {
            this.y = false;
        }
    }

    @Remap(classPath = "net.minecraft.world.entity.Entity",
            name = "onGround",
            type = Remap.Type.FIELD,
            remappedName = "y")
    @Override
    public void inactiveTick() {
        // Disable normal ticking for this entity.

        // Workaround to force EntityTrackerEntry to send a teleport packet immediately after spawning this entity.
        if (this.y) {
            this.y = false;
        }
    }

    @Remap(classPath = "net.minecraft.world.entity.Entity",
            name = "addAdditionalSaveData",
            type = Remap.Type.METHOD,
            remappedName = "b")
    @Override
    public void b(NBTTagCompound nbttagcompound) {
        // Do not save NBT.
    }

    @Remap(classPath = "net.minecraft.world.entity.Entity",
            name = "saveAsPassenger",
            type = Remap.Type.METHOD,
            remappedName = "d")
    @Override
    public boolean d(NBTTagCompound nbttagcompound) {
        // Do not save NBT.
        return false;
    }

    @Remap(classPath = "net.minecraft.world.entity.Entity",
            name = "saveWithoutId",
            type = Remap.Type.METHOD,
            remappedName = "f")
    @Override
    public NBTTagCompound f(NBTTagCompound nbttagcompound) {
        // Do not save NBT.
        return nbttagcompound;
    }

    @Remap(classPath = "net.minecraft.world.entity.Entity",
            name = "readAdditionalSaveData",
            type = Remap.Type.METHOD,
            remappedName = "a")
    @Override
    public void a(NBTTagCompound nbttagcompound) {
        // Do not load NBT.
    }

    @Remap(classPath = "net.minecraft.world.entity.Entity",
            name = "load",
            type = Remap.Type.METHOD,
            remappedName = "g")
    @Override
    public void g(NBTTagCompound nbttagcompound) {
        // Do not load NBT.
    }

    @Remap(classPath = "net.minecraft.world.entity.Entity",
            name = "isInvulnerableTo",
            type = Remap.Type.METHOD,
            remappedName = "b")
    @Override
    public boolean b(DamageSource source) {
        /*
         * The field Entity.invulnerable is private.
         * It's only used while saving NBTTags, but since the entity would be killed
         * on chunk unload, we prefer to override isInvulnerable().
         */
        return true;
    }

    @Remap(classPath = "net.minecraft.world.entity.Entity",
            name = "setCustomName",
            type = Remap.Type.METHOD,
            remappedName = "b")
    @Override
    public void b(IChatBaseComponent ichatbasecomponent) {
        // Locks the custom name.
    }

    @Remap(classPath = "net.minecraft.world.entity.Entity",
            name = "setCustomNameVisible",
            type = Remap.Type.METHOD,
            remappedName = "n")
    @Override
    public void n(boolean flag) {
        // Locks the custom name.
    }

    @Remap(classPath = "net.minecraft.world.entity.decoration.ArmorStand",
            name = "interactAt",
            type = Remap.Type.METHOD,
            remappedName = "a")
    @Override
    public EnumInteractionResult a(EntityHuman human, Vec3D vec3d, EnumHand enumhand) {
        // Prevent stand being equipped
        return EnumInteractionResult.d;
    }

    @Override
    public void setItemSlot(EnumItemSlot enumitemslot, ItemStack itemstack, boolean flag) {
        // Prevent stand being equipped
    }

    public void forceSetBoundingBox(AxisAlignedBB boundingBox) {
        super.a(boundingBox);
    }

    @Remap(classPath = "net.minecraft.world.entity.Entity",
            name = "playSound",
            type = Remap.Type.METHOD,
            remappedName = "a")
    @Override
    public void a(SoundEffect soundeffect, float f, float f1) {
        // Remove sounds.
    }

    @Remap(classPath = "net.minecraft.world.entity.Entity",
            name = "remove",
            type = Remap.Type.METHOD,
            remappedName = "a")
    @Override
    public void a(RemovalReason entity_removalreason) {
        // Prevent being killed.
    }

    @Override
    public CraftEntity getBukkitEntity() {
        if (bukkitEntity == null) {
            bukkitEntity = new CraftArmorStand((CraftServer) Bukkit.getServer(), this);
        }
        return bukkitEntity;
    }

}
