package exnihilocreatio.registries.registries.prefab;

import com.google.gson.Gson;
import exnihilocreatio.recipes.yaml.YamlLoader;
import exnihilocreatio.recipes.yaml.yamlRecipeClasses.YamlExNihiloRecipes;
import exnihilocreatio.registries.manager.IDefaultRecipeProvider;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRegistryList<V> extends BaseRegistry<List<V>> {

    public BaseRegistryList(Gson gson, List<? extends IDefaultRecipeProvider> defaultRecipeProviders) {
        super(gson, new ArrayList<>(), defaultRecipeProviders);
    }

    @Override
    public void clearRegistry() {
        registry.clear();
    }

    public void register(V value) {
        registry.add(value);
        YamlLoader.registerRecipeToMap(ex -> registerToYaml(ex, value));
    }

    public abstract void registerToYaml(YamlExNihiloRecipes ex, V value);
}
