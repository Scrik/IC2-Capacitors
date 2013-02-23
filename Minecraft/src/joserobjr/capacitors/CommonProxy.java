package joserobjr.capacitors;

import ic2.api.Ic2Recipes;
import ic2.api.Items;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class CommonProxy {
	public static int capacitorBlockID = 2689;
	
	public static BlockCapacitor capacitorBlock;
	
	public static ItemStack ulCapacitor = null; 
	public static ItemStack ulBtCapacitor = null;
	
	public void init(Configuration config) {
		loadConfig(config);
		initBlocks();
		initLanguage();
		initRecipes();
	}
	
	public void initRecipes() {
		Ic2Recipes.addCraftingRecipe(ulCapacitor, new Object[]{"ECE","CCC","ECE", 'E', "ingotTin", 'C', Items.getItem("tinCableItem")});
		Ic2Recipes.addCraftingRecipe(ulBtCapacitor, new Object[]{"ECE","CBC","ECE", 'E', "ingotTin", 'C', Items.getItem("tinCableItem"), 'B', Items.getItem("reBattery")});
	}

	public void loadConfig(Configuration config) {
		Property capacitorBlockID = config.getBlock("capacitors", this.capacitorBlockID);
		capacitorBlockID.comment = "BlockID for capacitors. Default is "+this.capacitorBlockID;
		this.capacitorBlockID = capacitorBlockID.getInt(this.capacitorBlockID);
		
		 config.save();
	}
	
	public void initBlocks() {
		capacitorBlock = new BlockCapacitor(capacitorBlockID);
		GameRegistry.registerBlock(capacitorBlock, ItemCapacitor.class, capacitorBlock.getBlockName());
		
		GameRegistry.registerTileEntity(TileEntityCapacitorUL.class, "UL-Capacitor");
		GameRegistry.registerTileEntity(TileEntityCapacitorULBT.class, "ULBT-Capacitor");
	}
	
	public void initLanguage() {
		LanguageRegistry.addName(ulCapacitor, "UL Capacitor");
		LanguageRegistry.addName(ulBtCapacitor, "UL Capacitor with Battery");
	}
}
