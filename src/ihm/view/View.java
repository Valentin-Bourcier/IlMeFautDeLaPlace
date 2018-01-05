package ihm.view;

public interface View {
	abstract void initComponents();

	abstract void setLayout();

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
