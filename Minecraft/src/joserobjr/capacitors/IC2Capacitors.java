package joserobjr.capacitors;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="IC2Capacitors", version="%conf:VERSION%", dependencies="required-after:IC2")
@NetworkMod(clientSideRequired=true)
public class IC2Capacitors extends DummyModContainer {
	
	@Instance("IC2Capacitors")
	public static IC2Capacitors instance;
	
	public static Configuration config;
	
	@SidedProxy(clientSide = "joserobjr.capacitors.ClientProxy", serverSide = "joserobjr.capacitors.CommonProxy")
	public static CommonProxy proxy;
	
	public IC2Capacitors(ModMetadata metadata) {
		super(metadata);
		metadata.version = "%conf:VERSION%";
		metadata.authorList.add("jose.rob.jr");
	}
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		 config = new Configuration(event.getSuggestedConfigurationFile());
		 
		 proxy.init(config);
	}
}
