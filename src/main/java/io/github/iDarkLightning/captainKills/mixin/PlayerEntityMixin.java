package io.github.iDarkLightning.captainKills.mixin;

import io.github.iDarkLightning.captainKills.helpers.PlayerEntityHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityHelper {

    public int captainKillCount;
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public void addCaptainKill(int amount) {
        captainKillCount += 1;
    }

    public void resetCaptainKill() {
        captainKillCount = 0;
    }

    public int getCaptainKillCount() {
        return captainKillCount;
    }

    @Inject(at = @At("RETURN"), method = "writeCustomDataToTag")
    public void writeCustomDataToTag (CompoundTag tag, CallbackInfo ci) {
        tag.putInt("captainKillCount", this.captainKillCount);
    }

    @Inject(at = @At("RETURN"), method = "readCustomDataFromTag")
    public void readCustomDataFromTag (CompoundTag tag, CallbackInfo ci) {
        this.captainKillCount = tag.getInt("captainKillCount");
    }

}
