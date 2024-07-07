package powersuits.client.lang;

import net.minecraft.world.item.Item;

public interface ILanguageProviderMPS {
    void addItemGroup();

    void addModularItems();

    void addModules();

    void addBlocks();

    void addGui();

    void addModuleTradeoff();

    void addKeybinds();

    void addModels();

    void addJavaModelPart(String parent, String part, String translation);

    void addPowerfistPart(String part, String translation);

    void addTradeoff(String tradeoff, String translation);

    void addOBJModelPart(String parent, String part, String translation);

    void addItemDescriptions(Item key, String description);
}




