package ihm.view;

public interface View {
	abstract void initComponents();
	abstract void setLayout();
	default void bind() {}
	
	default void build() {
		initComponents();
		setLayout();
		bind();
	}
	
	default void render() {	    
		build();
	}
}
