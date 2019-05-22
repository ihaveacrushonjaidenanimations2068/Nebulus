package mod.mcreator;

public class ClientProxynebulus extends CommonProxynebulus {

	@Override
	public void registerRenderers(nebulus instance) {
		instance.elements.forEach(element -> element.registerRenderers());
	}
}
