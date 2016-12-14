package codechicken.lib.internal.proxy;

import codechicken.lib.command.DumpClassCommand;
import codechicken.lib.model.cube.CCCubeLoader;
import codechicken.lib.render.CCRenderEventHandler;
import codechicken.lib.render.block.CCExtendedBlockRendererDispatcher;
import codechicken.lib.render.item.CCRenderItem;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.model.ModelLoaderRegistry;

/**
 * Created by covers1624 on 23/11/2016.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        CCRenderEventHandler.init();
        ModelLoaderRegistry.registerLoader(CCCubeLoader.INSTANCE);
        ClientCommandHandler.instance.registerCommand(new DumpClassCommand() {
            @Override
            public String getCommandName() {
                return "c_" + super.getCommandName();
            }
        });
    }

    @Override
    public void init() {
        CCExtendedBlockRendererDispatcher.init();
        CCRenderItem.init();
    }
}
