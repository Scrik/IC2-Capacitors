package joserobjr.capacitors;

import ic2.api.Direction;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileSourceEvent;
import ic2.core.block.wiring.TileEntityTransformer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

public abstract class TileEntityCapacitor extends TileEntityTransformer {
	public TileEntityCapacitor(int low, int high, int storage) {
		super(low, high, storage);
	}

	public TileEntityCapacitor(int energy, int storage) {
		this(energy, energy, storage);
	}

	public boolean acceptsEnergyFrom(TileEntity var1, Direction var2)
    {
        return !this.redstone ? !this.facingMatchesDirection(var2) : this.facingMatchesDirection(var2);
    }

	public boolean emitsEnergyTo(TileEntity var1, Direction var2)
    {
        return !this.redstone ? this.facingMatchesDirection(var2) : !this.facingMatchesDirection(var2);
    }

	public void updateEntity()
    {
        super.updateEntity();
        this.updateRedstone();

        int parts;

        if (this.redstone)
        {
        	parts = lowOutput / highOutput;
        	for (int var1 = 0; var1 < 4 && this.energy >= this.highOutput; ++var1)
            {
        		EnergyTileSourceEvent event = new EnergyTileSourceEvent(this, this.highOutput);
        		MinecraftForge.EVENT_BUS.post(event);
                this.energy -= this.highOutput - event.amount;
            }
        }
        else
        {
        	parts = highOutput / lowOutput;
            for (int var1 = 0; var1 < parts && this.energy >= this.lowOutput; ++var1)
            {
            	EnergyTileSourceEvent event = new EnergyTileSourceEvent(this, this.lowOutput);
        		MinecraftForge.EVENT_BUS.post(event);
                this.energy -= this.lowOutput - event.amount;
            }
        }
    }
}
