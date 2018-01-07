package ihm.view;

public interface View {
	void initComponents();

	void setLayout();

	default void bind() {
	}

	default void load() {
	}

	default void refresh() {
	}

	default void build() {
		initComponents();
		setLayout();
		load();
		bind();
	}

	default void render() {
		build();
	}
}
