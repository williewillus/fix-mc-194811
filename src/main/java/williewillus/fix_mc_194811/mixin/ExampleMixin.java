package williewillus.fix_mc_194811.mixin;

import org.spongepowered.asm.mixin.Unique;
import williewillus.fix_mc_194811.ExampleMod;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.storage.ChunkSerializer;
import net.minecraft.world.gen.feature.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ChunkSerializer.class)
public class ExampleMixin {
	@Unique
	private static boolean warnedAlready;

	@Inject(at = @At("RETURN"), method = "unpackStructureReferences")
	private static void fixNullStructures(ChunkPos pos, CompoundNBT tag, CallbackInfoReturnable<Map<Structure<?>, LongSet>> cir) {
		if (cir.getReturnValue().remove(null) != null && !warnedAlready) {
			ExampleMod.LOGGER.warn("Detected null structure while loading chunks, and **removed it from the save**. You probably removed a mod adding structures. Further messages will be suppressed.");
			warnedAlready = true;
		}
	}
}
