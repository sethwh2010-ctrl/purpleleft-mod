package com.seth.purpleaddon.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*
 * Re:Entity Outliner supplies the actual through-wall outline by using
 * Minecraft's glowing state. This small compatibility mixin only filters
 * that state based on the entity's horizontal screen position.
 *
 * It intentionally does not replace Re:Entity Outliner's renderer.
 */
@Mixin(Entity.class)
public abstract class EntityGlowMixin {
    @Inject(
        method = "isCurrentlyGlowing",
        at = @At("RETURN"),
        cancellable = true
    )
    private void purpleLeftOutliner$filterRightSide(
            CallbackInfoReturnable<Boolean> cir) {

        if (!cir.getReturnValueZ()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.gameRenderer == null) {
            return;
        }

        Entity entity = (Entity) (Object) this;
        if (entity == mc.player) {
            return;
        }

        var camera = mc.gameRenderer.getMainCamera();
        double dx = entity.getX() - camera.getPosition().x;
        double dz = entity.getZ() - camera.getPosition().z;

        double yaw = Math.toRadians(camera.getYRot());

        // Camera right vector in the horizontal plane.
        double rightX = Math.cos(yaw);
        double rightZ = Math.sin(yaw);

        // Positive = entity is to the camera's right.
        double right = dx * rightX + dz * rightZ;

        // Don't alter entities behind the camera.
        double forwardX = -Math.sin(yaw);
        double forwardZ = Math.cos(yaw);
        double forward = dx * forwardX + dz * forwardZ;

        if (forward > 0.0 && right > 0.0) {
            cir.setReturnValue(false);
        }
    }
}
