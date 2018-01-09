package ihm.view.main;

public interface View {

	default void initComponents() {
		System.out.println("No component initialized in: " + getClass());
	}

	default void setLayout() {
		System.out.println("No layout defined in: " + getClass());
	}

	default void bind() {
		System.out.println("No controller binded for: " + getClass());
	}

	default void load() {
		System.out.println("No data loaded by: " + getClass());
	}

	default void refresh() {
		System.out.println("Can't refresh: " + getClass());
	}

	default void build() {
		initComponents();
		setLayout();
		load();
		bind();
		System.out.println("View builded: " + getClass());
	}

	default void render() {
		build();
		System.out.println("View rendered: " + getClass());
	}
}
