package io.github.iDarkLightning.captainKills.mixin;

import io.github.iDarkLightning.captainKills.helpers.PlayerEntityHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RaiderEntity.class)
public abstract class RaidPlayerMixin extends PatrolEntity{

	public boolean isCaptain = this.isCaptain();
	protected RaidPlayerMixin(EntityType<? extends PatrolEntity> entityType, World world) {
		super(entityType, world);
	}

	public boolean isCaptain () {
		ItemStack itemStack = this.getEquippedStack(EquipmentSlot.HEAD);
		return !itemStack.isEmpty() && ItemStack.areEqual(itemStack, Raid.getOminousBanner());
	}

	@Inject(at = @At("HEAD"), method = "onDeath")
	public void onDeath(DamageSource source, CallbackInfo ci) {
		Entity attacker = source.getAttacker();

		if (attacker instanceof PlayerEntity) {

			if (isCaptain()) {
				((PlayerEntityHelper) attacker).addCaptainKill(1);
			}
		}
	}

	@Inject(at = @At("RETURN"), method = "writeCustomDataToTag")
	public void writeCustomDataToTag (CompoundTag tag, CallbackInfo ci) {
		tag.putBoolean("isCaptain", this.isCaptain());
	}

	@Inject(at = @At("RETURN"), method = "readCustomDataFromTag")
	public void readCustomDataFromTag (CompoundTag tag, CallbackInfo ci) {
		this.isCaptain = tag.getBoolean("isCaptain");
	}
}
