package joserobjr.capacitors;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;

public class ClientProxy extends CommonProxy {
	@Override
	public void init(Configuration config) {
		super.init(config);

		MinecraftForgeClient.preloadTexture("/joserobjr/capacitors/block_capacitor.png");
	}
}
