package com.astrazoey.lenientstacksize.mixins;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SuspiciousStewItem.class)
public class SuspiciousStewItemMixin {

    @Inject(method="finishUsing", at=@At("TAIL"), cancellable = true)
    private void handleStewStacks(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;

        cir.setReturnValue(stack);
        if (playerEntity == null || !playerEntity.getAbilities().creativeMode) {
            if (stack.getCount() <= 0) { ;
                cir.setReturnValue(new ItemStack(Items.BOWL));
            }

            if (playerEntity != null && stack.getCount() > 0) {
                playerEntity.getInventory().insertStack(new ItemStack(Items.BOWL));
            }
        }
    }

}
