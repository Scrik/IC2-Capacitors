package joserobjr.capacitors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ic2.api.Direction;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.core.block.TileEntityBlock;

public class TileEntityTransformer extends TileEntityBlock implements IEnergySink, IEnergySource
{
	public int lowOutput;
    public int highOutput;
    public int maxStorage;
    public int energy = 0;
    public boolean redstone = false;
    public boolean addedToEnergyNet = false;

    public TileEntityTransformer(int var1, int var2, int var3)
    {
        this.lowOutput = var1;
        this.highOutput = var2;
        this.maxStorage = var3;
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        this.energy = var1.getInteger("energy");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        var1.setInteger("energy", this.energy);
    }

    public boolean canUpdate()
    {
    	try {
			return (Boolean) Class.forName("ic2.platform.Platform").getMethod("isSimulating").invoke(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        super.updateEntity();
        this.updateRedstone();

        if (this.redstone)
        {
            if (this.energy >= this.highOutput)
            {
                this.energy -= this.highOutput - EnergyNet.getForWorld(this.worldObj).emitEnergyFrom(this, this.highOutput);
            }
        }
        else
        {
            for (int var1 = 0; var1 < 4 && this.energy >= this.lowOutput; ++var1)
            {
                this.energy -= this.lowOutput - EnergyNet.getForWorld(this.worldObj).emitEnergyFrom(this, this.lowOutput);
            }
        }
    }

    public void onCreated()
    {
        super.onCreated();

        if ( canUpdate())
        {
            EnergyNet.getForWorld(this.worldObj).addTileEntity(this);
            this.addedToEnergyNet = true;
        }
    }

    /**
     * invalidates a tile entity
     */
    public void invalidate()
    {
        if ( canUpdate() && this.addedToEnergyNet)
        {
            EnergyNet.getForWorld(this.worldObj).removeTileEntity(this);
            this.addedToEnergyNet = false;
        }

        super.invalidate();
    }

    public void updateRedstone()
    {
        boolean var1 = this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord);

        if (var1 != this.redstone)
        {
            EnergyNet.getForWorld(this.worldObj).removeTileEntity(this);
            this.addedToEnergyNet = false;
            this.redstone = var1;
            EnergyNet.getForWorld(this.worldObj).addTileEntity(this);
            this.addedToEnergyNet = true;
            this.setActive(this.redstone);
        }
    }

	public boolean isAddedToEnergyNet()
    {
        return this.addedToEnergyNet;
    }

    public boolean acceptsEnergyFrom(TileEntity var1, Direction var2)
    {
        return this.redstone ? !this.facingMatchesDirection(var2) : this.facingMatchesDirection(var2);
    }

    public boolean emitsEnergyTo(TileEntity var1, Direction var2)
    {
        return this.redstone ? this.facingMatchesDirection(var2) : !this.facingMatchesDirection(var2);
    }

    public boolean facingMatchesDirection(Direction var1)
    {
        return var1.toSideValue() == this.getFacing();
    }

    public int getMaxEnergyOutput()
    {
        return this.redstone ? this.highOutput : this.lowOutput;
    }

    public int demandsEnergy()
    {
        return 1;
    }

    public int injectEnergy(Direction var1, int var2)
    {
        if ((!this.redstone || var2 <= this.lowOutput) && (this.redstone || var2 <= this.highOutput || this.highOutput == 2048))
        {
            int var3 = var2;

            if (this.energy + var2 >= this.maxStorage + this.highOutput)
            {
                var3 = this.maxStorage + this.highOutput - this.energy - 1;
            }

            this.energy += var3;
            return var2 - var3;
        }
        else
        {
        	try {
				Class.forName("mod_IC2").getMethod("explodeMachineAt", World.class, Integer.TYPE, Integer.TYPE, Integer.TYPE).invoke(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			} catch (Exception e) {
				e.printStackTrace();
			}
            //mod_IC2.explodeMachineAt(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            return 0;
        }
    }

    public boolean wrenchCanSetFacing(EntityPlayer var1, int var2)
    {
        return this.getFacing() != var2;
    }

	public void setFacing(short var1)
    {
        if (this.addedToEnergyNet)
        {
            EnergyNet.getForWorld(this.worldObj).removeTileEntity(this);
        }

        this.addedToEnergyNet = false;
        super.setFacing(var1);
        EnergyNet.getForWorld(this.worldObj).addTileEntity(this);
        this.addedToEnergyNet = true;
    }

	@Override
	public int getMaxSafeInput() {
		// TODO Auto-generated method stub
		return 0;
	}
}
