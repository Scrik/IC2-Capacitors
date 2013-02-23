package joserobjr.capacitors;

import ic2.core.block.IRareBlock;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCapacitor extends ItemBlock {

	public ItemCapacitor(int par1) {
		super(par1);
		this.setMaxDamage(0);
        this.setHasSubtypes(true);
	}
	
	public int getMetadata(int var1)
    {
        return var1;
    }

    public String getItemNameIS(ItemStack var1)
    {
        int var2 = var1.getItemDamage();

        switch (var2)
        {
        case 0:
            return "blockCapacitorUL";
            
        case 1:
            return "blockCapacitorULBT";
        
        case 2:
        	return "blockTransformerUL";
            
            default:
                return "blockCapacitorUL";
        }
    }

    @Override
    public String getItemName() {
    	return "itemCapacitor0";
    }
    
	 /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack var1)
    {
        if (Block.blocksList[var1.itemID] instanceof IRareBlock)
        {
        	return ((IRareBlock)Block.blocksList[var1.itemID]).getRarity(var1);
        }
        else
        {
            return EnumRarity.common;
        }
    }

    public int rarity(ItemStack var1)
    {
        return 0;
    }
	
}
