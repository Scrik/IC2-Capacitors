package joserobjr.capacitors;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import ic2.core.IC2;
import ic2.core.block.BlockMultiID;
import ic2.core.block.IRareBlock;
import ic2.core.block.TileEntityBlock;

public class BlockCapacitor extends BlockMultiID implements IRareBlock {

	public BlockCapacitor(int id) {
		super(id, Material.iron);
		setHardness(1.5F);
        this.setStepSound(soundMetalFootstep);
        CommonProxy.ulCapacitor = new ItemStack(this, 1, 0);
        CommonProxy.ulBtCapacitor = new ItemStack(this, 1, 1);
        setBlockName("Capacitor");
	}
	
	@Override
	public String getTextureFile()
    {
        return "/joserobjr/capacitors/block_capacitor.png";
    }
	
	@Override
	public TileEntity getBlockEntity(int var1) {
		switch (var1)
        {
            case 0:
                return new TileEntityCapacitorUL();
                
            case 1:
                return new TileEntityCapacitorULBT();

            default:
                return null;
        }
	}
	
	@Override
	public void onBlockPlacedBy(World world, int var2, int var3, int var4, EntityLiving entityliving)
    {
        if (!IC2.platform.isSimulating()) return;
        
        TileEntityBlock var6 = (TileEntityBlock)world.getBlockTileEntity(var2, var3, var4);

        if (entityliving == null)
        {
            var6.setFacing((short)1);
        }
        else
        {
            int yaw  = MathHelper.floor_double((double)(entityliving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            int pitch = Math.round(entityliving.rotationPitch);

            if (pitch >= 65)
            {
                var6.setFacing((short)1);
            }
            else if (pitch <= -65)
            {
                var6.setFacing((short)0);
            }
            else
            {
                switch (yaw )
                {
                    case 0:
                        var6.setFacing((short)2);
                        break;

                    case 1:
                        var6.setFacing((short)5);
                        break;

                    case 2:
                        var6.setFacing((short)3);
                        break;

                    case 3:
                        var6.setFacing((short)4);
                }
            }
        }
    }
	
	@Override
	public EnumRarity getRarity(ItemStack var1)
    {
        return var1.getItemDamage() != 2 && var1.getItemDamage() != 5 ? EnumRarity.common : EnumRarity.uncommon;
    }
	
	@Override
	public int damageDropped(int par1) {
		return par1;
	}
	
	@Override
	public boolean isBlockNormalCube(World world, int x, int y, int z) {
		return false;
	}
	
	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z,
			ForgeDirection side) {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int meta) {
		switch (meta) {
	    case 0:
	      return new TileEntityCapacitorUL();
	    case 1:
	      return new TileEntityCapacitorULBT();
	    }
	    return null;
	}
	
}
