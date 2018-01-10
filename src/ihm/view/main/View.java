package ihm.view.main;

public interface View {

	/**
	 * Initialize components
	 */
	default void initComponents() {
		System.out.println("No component initialized in: " + getClass());
	}

	/**
	 * Organize components
	 */
	default void setLayout() {
		System.out.println("No layout defined in: " + getClass());
	}

	/**
	 * Bind events
	 */
	default void bind() {
		System.out.println("No controller binded for: " + getClass());
	}

	/**
	 * Load data
	 */
	default void load() {
		System.out.println("No data loaded by: " + getClass());
	}

	/**
	 * Refresh view
	 */
	default void refresh() {
		System.out.println("Can't refresh: " + getClass());
	}

	/**
	 * Build view (packing)
	 */
	default void build() {
		initComponents();
		setLayout();
		load();
		bind();
		System.out.println("View builded: " + getClass());
	}

	/**
	 * Render view
	 */
	default void render() {
		build();
		System.out.println("View rendered: " + getClass());
	}
}
