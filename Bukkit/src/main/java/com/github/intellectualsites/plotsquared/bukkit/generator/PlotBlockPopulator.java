package com.github.intellectualsites.plotsquared.bukkit.generator;

import com.github.intellectualsites.plotsquared.PlotSquared;
import com.github.intellectualsites.plotsquared.generator.IndependentPlotGenerator;
import com.github.intellectualsites.plotsquared.plot.object.ChunkWrapper;
import com.github.intellectualsites.plotsquared.plot.object.PlotArea;
import com.github.intellectualsites.plotsquared.queue.GlobalBlockQueue;
import com.github.intellectualsites.plotsquared.queue.LocalBlockQueue;
import com.github.intellectualsites.plotsquared.queue.ScopedLocalBlockQueue;
import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.jetbrains.annotations.NotNull;

final class BlockStatePopulator extends BlockPopulator {

    private final IndependentPlotGenerator plotGenerator;
    private LocalBlockQueue queue;

    public BlockStatePopulator(IndependentPlotGenerator plotGenerator) {
        this.plotGenerator = plotGenerator;
    }

    @Override public void populate(@NotNull final World world, @NotNull final Random random,
        @NotNull final Chunk source) {
        if (this.queue == null) {
            this.queue = GlobalBlockQueue.IMP.getNewQueue(world.getName(), false);
        }
        final PlotArea area = PlotSquared.get().getPlotArea(world.getName(), null);
        final ChunkWrapper wrap = new ChunkWrapper(area.getWorldName(), source.getX(), source.getZ());
        final ScopedLocalBlockQueue chunk = this.queue.getForChunk(wrap.x, wrap.z);
        if (this.plotGenerator.populateChunk(chunk, area)) {
            this.queue.flush();
        }
    }

}
